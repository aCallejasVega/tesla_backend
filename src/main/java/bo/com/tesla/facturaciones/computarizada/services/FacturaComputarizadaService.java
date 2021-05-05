package bo.com.tesla.facturaciones.computarizada.services;

import bo.com.tesla.administracion.entity.CobroClienteEntity;
import bo.com.tesla.administracion.entity.SucursalEntidadEntity;

import bo.com.tesla.administracion.entity.TransaccionCobroEntity;
import bo.com.tesla.facturaciones.computarizada.dto.*;
import bo.com.tesla.useful.config.Technicalexception;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturaComputarizadaService implements IFacturaComputarizadaService {

    @Value("${host.facturacion.computarizada}")
    private String host;

    @Autowired
    private IConexionService conexionService;

    @Override
    public ResponseDto postCodigoControl(CodigoControlDto codigoControlDto)  {
        try {
            String url = this.host + "/api/facturas/codigoscontroles";
            return conexionService.getResponseMethodPost(1L,codigoControlDto, url);

        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(),e.getCause());
        }

    }

    private List<FacturaDto> loadFacturasPorTransaccion(List<TransaccionCobroEntity> transaccionCobroEntityList) {
        //Cargar Facturas
        List<FacturaDto> facturaDtoList = new ArrayList<>();
        for(TransaccionCobroEntity transaccionCobroEntity : transaccionCobroEntityList) {
            FacturaDto facturaDto = new FacturaDto();
            facturaDto.montoTotal = transaccionCobroEntity.getTotalDeuda();
            facturaDto.montoBaseImporteFiscal = transaccionCobroEntity.getTotalDeuda();
            facturaDto.codigoCliente = transaccionCobroEntity.getCodigoCliente();
            facturaDto.nombreRazonSocial = transaccionCobroEntity.getNombreClientePago();
            facturaDto.numeroDocumento = transaccionCobroEntity.getNroDocumentoClientePago();
            facturaDto.montoDescuento = new BigDecimal(0);
            facturaDto.keyTeslaTransaccion = transaccionCobroEntity.getTransaccionCobroId();

            //Cargar DetalleFacturas
            facturaDto.detalleFacturaDtoList = loadDetallesFacturas(transaccionCobroEntity);

            facturaDtoList.add(facturaDto);
        }

        return facturaDtoList;
    }

    private List<FacturaDto> loadFacturaGlobal(List<TransaccionCobroEntity> transaccionCobroEntityList, BigDecimal montoTotalCobrado) {

        FacturaDto facturaDto = new FacturaDto();
        facturaDto.montoTotal = montoTotalCobrado;
        facturaDto.montoBaseImporteFiscal = montoTotalCobrado;
        facturaDto.codigoCliente = transaccionCobroEntityList.get(0).getCodigoCliente();
        facturaDto.nombreRazonSocial = transaccionCobroEntityList.get(0).getNombreClientePago();
        facturaDto.numeroDocumento = transaccionCobroEntityList.get(0).getNroDocumentoClientePago();
        facturaDto.montoDescuento = new BigDecimal(0);
        facturaDto.keyTeslaTransaccion = transaccionCobroEntityList.get(0).getTransaccionCobroId();

        //Cargar Detalle deFactura
        List<DetalleFacturaDto> detalleFacturaDtoList = new ArrayList<>();
        for(TransaccionCobroEntity transaccionCobroEntity : transaccionCobroEntityList) {
            detalleFacturaDtoList.addAll(loadDetallesFacturas(transaccionCobroEntity));
        }
        facturaDto.detalleFacturaDtoList = detalleFacturaDtoList;

        //Cargar Unica Factura
        List<FacturaDto> facturaDtoList = new ArrayList<>();
        facturaDtoList.add(facturaDto);
        return facturaDtoList;
    }

    private List<DetalleFacturaDto> loadDetallesFacturas(TransaccionCobroEntity transaccionCobroEntity) {
        List<DetalleFacturaDto> detalleFacturaDtoList = new ArrayList<>();

        List<CobroClienteEntity> cobroClienteEntityList = transaccionCobroEntity.getCobroClienteEntityList()
                .stream().filter(c -> c.getTipo().equals('D'))
                .collect(Collectors.toList());

        for(CobroClienteEntity cobroClienteEntity : cobroClienteEntityList) {
            DetalleFacturaDto detalleFacturaDto = new DetalleFacturaDto();
            detalleFacturaDto.tipoServicio = cobroClienteEntity.getTipoServicio();
            detalleFacturaDto.servicio = cobroClienteEntity.getServicio();
            detalleFacturaDto.periodo = cobroClienteEntity.getPeriodo();
            //detalleFacturaDto.codigo = null;
            //detalleFacturaDto.unidad = null;
            detalleFacturaDto.cantidad = cobroClienteEntity.getCantidad();
            detalleFacturaDto.concepto = cobroClienteEntity.getConcepto();
            detalleFacturaDto.montoUnitario = cobroClienteEntity.getMontoUnitario();
            detalleFacturaDto.montoSubtotal = cobroClienteEntity.getSubTotal();

            detalleFacturaDtoList.add(detalleFacturaDto);
        }
        return detalleFacturaDtoList;
    }

    @Override
    public ResponseDto postFacturas(SucursalEntidadEntity sucursalEntidadEntity, List<TransaccionCobroEntity> transaccionCobroEntityList, Boolean comprobanteEnUno, BigDecimal montoTotal) {
        try {

            //Cargar actividad que permite reconocer doficiacion
            if(sucursalEntidadEntity.getCodigoActividadEconomica() == null) {
                throw new Technicalexception("La sucursal no ha registrado el código de actividad económica");
            }
            FacturasLstDto facturasLstDto = new FacturasLstDto();
            facturasLstDto.codigoActividadEconomica = sucursalEntidadEntity.getCodigoActividadEconomica();


            //Cargar Facturas
            List<FacturaDto> facturaDtoList = comprobanteEnUno ? loadFacturaGlobal(transaccionCobroEntityList, montoTotal ) : loadFacturasPorTransaccion(transaccionCobroEntityList);
            facturasLstDto.facturaDtoList = facturaDtoList;

            //api factura
            String url = this.host + "/api/facturas/listas";
            ResponseDto responseDto = conexionService.getResponseMethodPost(transaccionCobroEntityList.get(0).getEntidadId().getEntidadId(), facturasLstDto, url);

            TypeReference<List<FacturaDto>> mapType = new TypeReference<List<FacturaDto>>() {};
            ObjectMapper mapper = new ObjectMapper();
            List<FacturaDto> facturaDtoListResponse = mapper.convertValue(responseDto.result, mapType);

            responseDto.result = facturaDtoListResponse;

            return responseDto;
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(),e.getCause());
        }
    }

    @Override
    public ResponseDto postFacturaLstFilter(Long entidadId, int page, FacturaDto facturaDto) {
        String url = this.host + "/api/facturas/filters";

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url).
                queryParam("page", page - 1 ).
                queryParam("size", 10);

        return conexionService.getResponseMethodPostParameter(entidadId, facturaDto, uriComponentsBuilder);
    }

    @Override
    public ResponseDto getFacturaReport(Long entidadId, Long facturaId) {
        try {
            String url = this.host + "/api/facturas/reportes/" + facturaId;
            return conexionService.getResponseMethodGet(entidadId, url);
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(),e.getCause());
        }
    }

    @Override
    public ResponseDto getLibroVentasReport(Long entidadId, FacturaDto facturaDto) {
        try {
            String url = this.host + "/api/facturas/librosventas";
            return conexionService.getResponseMethodPost(entidadId, facturaDto, url);

        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(),e.getCause());
        }
    }





}