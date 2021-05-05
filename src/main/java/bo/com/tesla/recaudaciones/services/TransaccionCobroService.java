package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.*;
import bo.com.tesla.administracion.services.IEntidadComisionService;
import bo.com.tesla.administracion.services.IRecaudadorComisionService;
import bo.com.tesla.facturaciones.computarizada.dto.AnulacionFacturaLstDto;
import bo.com.tesla.facturaciones.computarizada.dto.FacturaDto;
import bo.com.tesla.facturaciones.computarizada.dto.ResponseDto;
import bo.com.tesla.facturaciones.computarizada.services.FacturaComputarizadaService;
import bo.com.tesla.facturaciones.computarizada.services.IAnulacionFacturaService;
import bo.com.tesla.facturaciones.computarizada.services.IFacturaComputarizadaService;
import bo.com.tesla.recaudaciones.dao.ICobroClienteDao;
import bo.com.tesla.recaudaciones.dao.IHistoricoDeudaDao;
import bo.com.tesla.recaudaciones.dao.ITransaccionCobroDao;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;
import bo.com.tesla.useful.config.Technicalexception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class TransaccionCobroService implements ITransaccionCobroService {

    @Autowired
    private ITransaccionCobroDao iTransaccionCobroDao;

    @Autowired
    private IEntidadComisionService iEntidadComisionService;

    @Autowired
    private IRecaudadorComisionService iRecaudadorComisionService;

    @Autowired
    private ICobroClienteDao iCobroClienteDao;

    @Autowired
    private IAnulacionFacturaService anulacionFacturaService;

    @Autowired
    private IDeudaClienteRService deudaClienteRService;

    @Autowired
    private IHistoricoDeudaService historicoDeudaService;

    @Override
    public TransaccionCobroEntity saveTransaccionCobro(TransaccionCobroEntity transaccionCobroEntity) {
        //return iTransaccionCobroDao.save(transaccionCobroEntity);
        return iTransaccionCobroDao.saveAndFlush(transaccionCobroEntity);
    }

    @Override
    public List<TransaccionCobroEntity> saveAllTransaccionesCobros(List<TransaccionCobroEntity> transaccionCobroEntities) {
        return iTransaccionCobroDao.saveAll(transaccionCobroEntities);
    }

    @Transactional(readOnly = true)
    @Override
    public TransaccionCobroEntity loadTransaccionCobro(ServicioDeudaDto servicioDeudaDto, EntidadEntity entidadEntity, Long usuarioId, String nombreCientePago, String nroDocumentoClientePago,
                                                       EntidadComisionEntity entidadComisionEntity, RecaudadorEntity recaudadorEntity, RecaudadorComisionEntity recaudadorComisionEntity,
                                                       ArchivoEntity archivoEntity, DominioEntity metodoCobro,
                                                       DominioEntity modalidadFacturacion) {

        TransaccionCobroEntity transaccionCobroEntity = new TransaccionCobroEntity();
        transaccionCobroEntity.setTipoServicio(servicioDeudaDto.tipoServicio);
        transaccionCobroEntity.setServicio(servicioDeudaDto.servicio);
        transaccionCobroEntity.setPeriodo(servicioDeudaDto.periodo);
        transaccionCobroEntity.setUsuarioCreacion(usuarioId);
        transaccionCobroEntity.setFechaCreacion(new Date());
        transaccionCobroEntity.setEntidadId(entidadEntity);
        //transaccionCobroEntity.setTransaccion("COBRAR");
        transaccionCobroEntity.setTransaccion("CREAR");
        transaccionCobroEntity.setArchivoId(archivoEntity);
        transaccionCobroEntity.setCodigoCliente(servicioDeudaDto.codigoCliente);
        transaccionCobroEntity.setNombreClientePago(nombreCientePago);
        transaccionCobroEntity.setTotalDeuda(servicioDeudaDto.subTotal);
        transaccionCobroEntity.setNroDocumentoClientePago(nroDocumentoClientePago);
        transaccionCobroEntity.setComision(iEntidadComisionService.calcularComision(entidadComisionEntity, servicioDeudaDto.subTotal));
        transaccionCobroEntity.setRecaudador(recaudadorEntity);
        transaccionCobroEntity.setNombreClienteArchivo(servicioDeudaDto.nombreCliente);
        transaccionCobroEntity.setNroDocumentoClienteArchivo(servicioDeudaDto.nroDocumento);
        transaccionCobroEntity.setEntidadComision(entidadComisionEntity);
        transaccionCobroEntity.setComisionRecaudacion(iRecaudadorComisionService.calcularComision(recaudadorComisionEntity, servicioDeudaDto.subTotal));
        transaccionCobroEntity.setRecaudadorComision(recaudadorComisionEntity);
        transaccionCobroEntity.setMetodoCobro(metodoCobro);
        transaccionCobroEntity.setModalidadFacturacion(modalidadFacturacion);
        return  transaccionCobroEntity;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Technicalexception.class)
    public Boolean AnularTransaccion(Long entidadId,
                                     AnulacionFacturaLstDto anulacionFacturaLstDto,
                                     SegUsuarioEntity usuarioEntity) {
        try {
            //Anular Transacciones
            Integer countupdate = iTransaccionCobroDao.updateLstTransaccionByFacturas(anulacionFacturaLstDto.facturaIdLst, "ANULAR", usuarioEntity.getUsuarioId());
            if (countupdate == 0) {
                throw new Technicalexception("La Anulación ha producido un inconveniente o se ha producido un nuevo cargado de archivo");
            }

            //Anular Cobros
            Integer countUpdateCobros = iCobroClienteDao.updateLstTransaccionByFacturas(anulacionFacturaLstDto.facturaIdLst, "ANULAR", usuarioEntity.getUsuarioId());
            if (countUpdateCobros == 0) {
                throw new Technicalexception("La Anulación ha producido un inconveniente");
            }

            Integer countHistoricos = historicoDeudaService.updateHistoricoDeudaLstByFacturas(anulacionFacturaLstDto.facturaIdLst, "ANULADO");
            if(countHistoricos == 0) {
                throw new Technicalexception("No se ha logrado actualizar el estado de los registros históricos de deudas");
            }

            //Recuperar las deudas
            deudaClienteRService.recoverDeudasByFacturas(anulacionFacturaLstDto.facturaIdLst);

            /***********************************************************************/
            //Anulación de Facturas
            ResponseDto responseDto = anulacionFacturaService.postAnulacionLst(entidadId, anulacionFacturaLstDto);
            if(!responseDto.status) {
                throw new Technicalexception(responseDto.message);
            }
            /***********************************************************************/

            return true;
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void updateFacturas(List<TransaccionCobroEntity> transaccionCobroEntityList,
                               boolean comprobanteEnUno,
                               List<FacturaDto> facturaDtoList) {
        facturaDtoList.forEach(f -> {
            if(!comprobanteEnUno) {
                Integer updateCountFactura = iTransaccionCobroDao.updateFactura(f.keyTeslaTransaccion, f.facturaId);
                if (updateCountFactura != 1) {
                    throw new Technicalexception("No se ha actualizado la factura en la Transacción");
                }
            } else {
                transaccionCobroEntityList.forEach(t -> {
                    Integer update = iTransaccionCobroDao.updateFactura(t.getTransaccionCobroId(), f.facturaId);
                    if (update != 1) {
                        throw new Technicalexception("No se ha actualizado la factura en la Transacción");
                    }
                });
            }
        });
    }

}
