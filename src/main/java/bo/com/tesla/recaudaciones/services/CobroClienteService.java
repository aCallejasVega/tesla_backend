package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.*;
import bo.com.tesla.recaudaciones.dao.IArchivoRDao;
import bo.com.tesla.recaudaciones.dao.ICobroClienteDao;
import bo.com.tesla.recaudaciones.dao.IDominioDao;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;
import bo.com.tesla.security.dao.ISegUsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CobroClienteService implements ICobroClienteService {

    @Autowired
    private ICobroClienteDao iCobroClienteDao;

    @Autowired
    private IDeudaClienteRService iDeudaClienteRService;

    @Autowired
    private ITransaccionCobroService iTransaccionCobroService;

    @Autowired
    private ISegUsuarioDao usuarioDao;

    @Autowired
    private IComprobanteCobroService iComprobanteCobroService;

    @Autowired
    private IArchivoRDao iArchivoRDao;

    @Autowired
    private IDominioDao iDominioDao;

    @Autowired
    private IAccionService iAccionService;

    @Autowired
    private IDetalleComprobanteCobroService iDetalleComprobanteCobroService;


    @Override
    public List<CobroClienteEntity> saveAllCobrosClientes(List<CobroClienteEntity> cobroClienteEntities) {
        return iCobroClienteDao.saveAll(cobroClienteEntities);
    }

    @Override
    public CobroClienteEntity loadCobroClienteEntity(DeudaClienteEntity deudaClienteEntity,
                                                     Long usuarioId,
                                                     Long metodoPagoId) {

        Optional<DominioEntity> optionalDominioEntity = iDominioDao.findByDominioId(metodoPagoId);
        if(!optionalDominioEntity.isPresent())
            return null;

        CobroClienteEntity cobroClienteEntity = new CobroClienteEntity();
        cobroClienteEntity.setArchivoId(deudaClienteEntity.getArchivoId());
        cobroClienteEntity.setMetodoCobroId(optionalDominioEntity.get());
        cobroClienteEntity.setNroRegistro(deudaClienteEntity.getNroRegistro());
        cobroClienteEntity.setCodigoCliente(deudaClienteEntity.getCodigoCliente());
        cobroClienteEntity.setNombreCliente(deudaClienteEntity.getNombreCliente());
        cobroClienteEntity.setNroDocumento(deudaClienteEntity.getNroDocumento());
        cobroClienteEntity.setTipoServicio(deudaClienteEntity.getTipoServicio());
        cobroClienteEntity.setServicio(deudaClienteEntity.getServicio());
        cobroClienteEntity.setPeriodo(deudaClienteEntity.getPeriodo());
        cobroClienteEntity.setTipo(deudaClienteEntity.getTipo());
        cobroClienteEntity.setCantidad(deudaClienteEntity.getCantidad());
        cobroClienteEntity.setConcepto(deudaClienteEntity.getConcepto());
        cobroClienteEntity.setMontoUnitario(deudaClienteEntity.getMontoUnitario());
        cobroClienteEntity.setDatoExtra(deudaClienteEntity.getDatoExtras());
        cobroClienteEntity.setTipoComprobante(deudaClienteEntity.getTipoComprobante());
        cobroClienteEntity.setPeriodoCabecera(deudaClienteEntity.getPeriodoCabecera());
        cobroClienteEntity.setUsuarioCreacion(usuarioId);
        cobroClienteEntity.setFechaCreacion(new Date());
        cobroClienteEntity.setEstado("COBRADO");
        //cobroClienteEntity.setTransaccion("COBRAR");

        return  cobroClienteEntity;
    }


    //Método priniciapl para cobro de deudas
    @Transactional(rollbackFor = {Exception.class})
    public void postCobrarDeudas(List<ServicioDeudaDto> servicioDeudaDtos,
                                              Boolean comprobanteEnUno,
                                              String login,
                                              Long metodoPagoId) throws Exception {
        if(comprobanteEnUno) {
            postCobrarDeudasGlobal(servicioDeudaDtos, login, metodoPagoId);
        } else {
            postCobrarDeudasIndividual(servicioDeudaDtos, login, metodoPagoId);
        }
    }

    public void postCobrarDeudasIndividual(List<ServicioDeudaDto> servicioDeudaDtos,
                                    String login,
                                    Long metodoPagoId) throws Exception {
        //Boolean response = false;
        SegUsuarioEntity usuario = this.usuarioDao.findByLogin(login);

        //1. Reemplazar por agrupacion todos registros de deudas y datos extras
        List<ServicioDeudaDto> servicioDeudaDtoList = iDeudaClienteRService.getDeudasCompletas(servicioDeudaDtos);
        if(servicioDeudaDtoList == null) {
            throw new Exception();
        }

        //2. Por cada Agrupacion recorrer
        for(ServicioDeudaDto servicioDeudaDto : servicioDeudaDtoList) {

            //2.1. Recorrer las deudas con todos registro completos
            List<CobroClienteEntity> cobroClienteEntityList = new ArrayList<>();
            List<AccionEntity> accionEntityList = new ArrayList<>();

            for(DeudaClienteEntity deudaClienteEntity : servicioDeudaDto.getDeudaClientes()) {
                //2.1.1. Cargar Cobros Clientes
                CobroClienteEntity cobroClienteEntity = loadCobroClienteEntity(deudaClienteEntity, usuario.getUsuarioId(), metodoPagoId);
                if(cobroClienteEntity == null) {
                    throw new Exception();
                }
                cobroClienteEntityList.add(cobroClienteEntity);

                //2.1.2. Cargar Acciones
                AccionEntity accionEntity = iAccionService.loadAccion(deudaClienteEntity, "COBRADO", usuario.getUsuarioId());
                if(accionEntity == null) {
                    throw new Exception();
                }
                accionEntityList.add(accionEntity);
            }
            //2.2. Guardar CobroClientes en Lista
            cobroClienteEntityList = saveAllCobrosClientes(cobroClienteEntityList);
            if(cobroClienteEntityList.size() == 0) {
                throw new Exception();
            }

            //2.3. Guardar Acciones en Lista
            accionEntityList = iAccionService.saveAllAcciones(accionEntityList);
            if(accionEntityList.size() == 0) {
                throw new Exception();
            }

            //2.4. Cargar Transacciones Cobros y Guardar
            TransaccionCobroEntity transaccionCobroEntity = iTransaccionCobroService.loadTransaccionCobro(servicioDeudaDto, usuario.getUsuarioId());
            transaccionCobroEntity = iTransaccionCobroService.saveTransaccionCobro(transaccionCobroEntity);
            if(transaccionCobroEntity == null) {
                throw new Exception();
            }

            //2.5. Encontrar los Lista de Cobros Clientes con tipo "D"
            List<CobroClienteEntity> cobroClienteEntityListFilterD = cobroClienteEntityList.stream()
                                                                        .filter(c -> c.getTipo().equals('D')).collect(Collectors.toList());
            if(cobroClienteEntityListFilterD.size() == 0 ) {
                throw new Exception();
            }

            //2.6 Cargar Comprobante
            Double montoTotalAgrupado = cobroClienteEntityListFilterD.stream()
                                                .mapToDouble(s -> s.getMontoUnitario().doubleValue()).sum(); //Modificar a SubTotal OJO
            ComprobanteCobroEntity comprobanteCobroEntity = iComprobanteCobroService.loadComprobanteCobro(servicioDeudaDto, usuario.getUsuarioId(), BigDecimal.valueOf(montoTotalAgrupado));
            //2.7. Guardar comprobante
            comprobanteCobroEntity = iComprobanteCobroService.saveComprobanteCobro(comprobanteCobroEntity);
            if(comprobanteCobroEntity == null) {
                throw new Exception();
            }

            //2.8. Recorrer Lista de Cobros Clientes con tipo "D"
            List<DetalleComprobanteCobroEntity> detalleComprobanteCobroEntityList = new ArrayList<>();

            for(CobroClienteEntity cobroClienteEntity : cobroClienteEntityListFilterD) {
                //2.8.1. Cargar Detalle Comprobante Cobro
                DetalleComprobanteCobroEntity detalleComprobanteCobroEntity = iDetalleComprobanteCobroService.loadDetalleComprobanteCobroEntity(transaccionCobroEntity, cobroClienteEntity, comprobanteCobroEntity, usuario.getUsuarioId());
                detalleComprobanteCobroEntityList.add(detalleComprobanteCobroEntity);
            }

            //2.9. Guardar Lista Detalle Comprobantes
            List<DetalleComprobanteCobroEntity> detalleComprobanteCobroEntitiesResponse = iDetalleComprobanteCobroService.saveAllDetallesComprobantesCobos(detalleComprobanteCobroEntityList);
            if(detalleComprobanteCobroEntitiesResponse.size() == 0) {
                throw new Exception();
            }
        }

        //3. Encontrar todas las deudas mas datos extras
        List<DeudaClienteEntity> deudaClienteEntitiesDelete = servicioDeudaDtoList.stream()
                .map(ServicioDeudaDto::getDeudaClientes).findFirst().get();

        //4. Eliminar DeuassClientes en Lista
        Long recordDeletes = iDeudaClienteRService.deleteDeudasClientes(deudaClienteEntitiesDelete);
        if(recordDeletes == 0) {
            throw new Exception();
        }

    }

    public void postCobrarDeudasGlobal(List<ServicioDeudaDto> servicioDeudaDtos,
                                    String login,
                                    Long metodoPagoId) throws Exception {

        SegUsuarioEntity usuario = this.usuarioDao.findByLogin(login);

        //1. Reemplazar por agrupacion todos registros de deudas y datos extras
        List<ServicioDeudaDto> servicioDeudaDtoList = iDeudaClienteRService.getDeudasCompletas(servicioDeudaDtos);
        if(servicioDeudaDtoList == null) {
            throw new Exception();
        }

        //Inicializar variables
        List<DetalleComprobanteCobroEntity> detalleComprobanteCobroEntityList = new ArrayList<>();
        BigDecimal sumaTotal = BigDecimal.valueOf(0);

        //2. Por cada Agrupacion recorrer
        for(ServicioDeudaDto servicioDeudaDto : servicioDeudaDtoList) {

            //2.1. Recorrer las deudas con todos registro completos
            List<CobroClienteEntity> cobroClienteEntityList = new ArrayList<>();
            List<AccionEntity> accionEntityList = new ArrayList<>();

            for(DeudaClienteEntity deudaClienteEntity : servicioDeudaDto.getDeudaClientes()) {
                //2.1.1. Cargar Cobros Clientes
                CobroClienteEntity cobroClienteEntity = loadCobroClienteEntity(deudaClienteEntity, usuario.getUsuarioId(), metodoPagoId);
                if(cobroClienteEntity == null) {
                    throw new Exception();
                }
                cobroClienteEntityList.add(cobroClienteEntity);

                //2.1.2. Cargar Acciones
                AccionEntity accionEntity = iAccionService.loadAccion(deudaClienteEntity, "COBRADO", usuario.getUsuarioId());
                if(accionEntity == null) {
                    throw new Exception();
                }
                accionEntityList.add(accionEntity);
            }
            //2.2. Guardar en lista
            cobroClienteEntityList = saveAllCobrosClientes(cobroClienteEntityList);
            if(cobroClienteEntityList.size() == 0) {
                throw new Exception();
            }

            //2.3. Guardar Acciones en Lista
            accionEntityList = iAccionService.saveAllAcciones(accionEntityList);
            if(accionEntityList.size() == 0) {
                throw new Exception();
            }

            //2.4. Cargar Transacciones Cobros y Guardar
            TransaccionCobroEntity transaccionCobroEntity = iTransaccionCobroService.loadTransaccionCobro(servicioDeudaDto, usuario.getUsuarioId());
            transaccionCobroEntity = iTransaccionCobroService.saveTransaccionCobro(transaccionCobroEntity);
            if(transaccionCobroEntity == null) {
                throw new Exception();
            }

            //2.5. Encontrar los Lista de Cobros Clientes con tipo "D"
            List<CobroClienteEntity> cobroClienteEntityListFilterD = cobroClienteEntityList.stream()
                                                                        .filter(c -> c.getTipo().equals('D')).collect(Collectors.toList());
            if(cobroClienteEntityListFilterD.size() == 0 ) {
                throw new Exception();
            }

            //2.6. Recorrer Lista de Cobros Clientes con tipo "D"
            for(CobroClienteEntity cobroClienteEntity : cobroClienteEntityListFilterD) {
                DetalleComprobanteCobroEntity detalleComprobanteCobroEntity = iDetalleComprobanteCobroService.loadDetalleComprobanteCobroEntity(transaccionCobroEntity, cobroClienteEntity, null,usuario.getUsuarioId());
                detalleComprobanteCobroEntityList.add(detalleComprobanteCobroEntity);
            }

            Double montoTotalAgrupado = cobroClienteEntityListFilterD.stream()
                                                .mapToDouble(s -> s.getMontoUnitario().doubleValue()).sum(); //Modificar a SubTotal OJO
            sumaTotal = BigDecimal.valueOf(montoTotalAgrupado).add(sumaTotal);
        }

        //3. Cargar Comprobante
        ComprobanteCobroEntity comprobanteCobroEntity = iComprobanteCobroService.loadComprobanteCobro(servicioDeudaDtoList.get(0), usuario.getUsuarioId(), sumaTotal);

        //4. Guardar Comprobante
        comprobanteCobroEntity = iComprobanteCobroService.saveComprobanteCobro(comprobanteCobroEntity);
        if(comprobanteCobroEntity == null) {
            throw new Exception();
        }
        //5. Asignar el comporbante a todos los detalles de comporbamtes
        ComprobanteCobroEntity finalComprobanteCobroEntity = comprobanteCobroEntity;
        detalleComprobanteCobroEntityList.forEach(z -> z.setComprobanteCobroId(finalComprobanteCobroEntity));
        //detalleComprobanteCobroEntityList.forEach();
        //5. Guardar Detalle comporbate
        List<DetalleComprobanteCobroEntity> detalleComprobanteCobroEntitiesResponse = iDetalleComprobanteCobroService.saveAllDetallesComprobantesCobos(detalleComprobanteCobroEntityList);

        //5. Encontrar todas las deudas mas datos extras
        List<DeudaClienteEntity> deudaClienteEntitiesDelete = servicioDeudaDtoList.stream()
                                                                .map(ServicioDeudaDto::getDeudaClientes).findFirst().get();

        //6. Eliminar DeuassClientes en Lista
        Long recordDeletes = iDeudaClienteRService.deleteDeudasClientes(deudaClienteEntitiesDelete);
        if(recordDeletes == 0) {
            throw new Exception();
        }

    }

}