package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.*;
import bo.com.tesla.entidades.dto.DeudasClienteDto;
import bo.com.tesla.recaudaciones.dao.IArchivoRDao;
import bo.com.tesla.recaudaciones.dao.ICobroClienteDao;
import bo.com.tesla.recaudaciones.dao.IDominioDao;
import bo.com.tesla.recaudaciones.dao.IRecaudadorDao;
import bo.com.tesla.recaudaciones.dto.ClienteDto;
import bo.com.tesla.recaudaciones.dto.DeudaClienteDto;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;
import bo.com.tesla.security.dao.ISegUsuarioDao;
import bo.com.tesla.useful.config.Technicalexception;
import bo.com.tesla.useful.config.Technicalexception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
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
    private IComprobanteCobroService iComprobanteCobroService;

    @Autowired
    private IArchivoRDao iArchivoRDao;

    @Autowired
    private IDominioDao iDominioDao;

    /*@Autowired
    private IAccionService iAccionService;*/

    @Autowired
    private IHistoricoDeudaService iHistoricoDeudaService;

    @Autowired
    private IDetalleComprobanteCobroService iDetalleComprobanteCobroService;

    @Autowired
    private IRecaudadorDao iRecaudadorDao;


    @Override
    public List<CobroClienteEntity> saveAllCobrosClientes(List<CobroClienteEntity> cobroClienteEntities) {
        return this.iCobroClienteDao.saveAll(cobroClienteEntities);
    }


    @Override
    public CobroClienteEntity loadCobroClienteEntity(DeudaClienteEntity deudaClienteEntity,
                                                     List<DeudaClienteDto> deudaClienteDtos,
                                                     Long usuarioId,
                                                     Long metodoPagoId) {

        Optional<DominioEntity> optionalDominioEntity = iDominioDao.findByDominioIdAndDominio(metodoPagoId, "metodo_pago_id");
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
        cobroClienteEntity.setDatoExtra(deudaClienteEntity.getDatoExtras());
        cobroClienteEntity.setTipoComprobante(deudaClienteEntity.getTipoComprobante());
        cobroClienteEntity.setPeriodoCabecera(deudaClienteEntity.getPeriodoCabecera());
        cobroClienteEntity.setNit(deudaClienteEntity.getNit());
        cobroClienteEntity.setDireccion(deudaClienteEntity.getDireccion());
        cobroClienteEntity.setTelefono(deudaClienteEntity.getTelefono());
        cobroClienteEntity.setEsPostpago(deudaClienteEntity.getEsPostpago());
        cobroClienteEntity.setUsuarioCreacion(usuarioId);
        cobroClienteEntity.setFechaCreacion(new Date());
        ///cobroClienteEntity.setEstado("COBRADO");
        cobroClienteEntity.setTransaccion("COBRAR");

        //control de MontoUnitarios y Subtotales por prepago
        if(!deudaClienteEntity.getEsPostpago() && deudaClienteEntity.getTipo() == 'D') {
            DeudaClienteDto deudaClienteDto = deudaClienteDtos.stream().filter(d -> d.deudaClienteId.equals(deudaClienteEntity.getDeudaClienteId())).findAny().orElse(null);
            if(deudaClienteDto != null) {
                cobroClienteEntity.setMontoUnitario(deudaClienteDto.montoUnitario);
                cobroClienteEntity.setSubTotal(deudaClienteDto.subTotal);
            }
        } else {
            cobroClienteEntity.setMontoUnitario(deudaClienteEntity.getMontoUnitario());
            cobroClienteEntity.setSubTotal(deudaClienteEntity.getSubTotal());
        }

        return  cobroClienteEntity;
    }


    //Método priniciapl para cobro de deudas
    public void postCobrarDeudas(ClienteDto clienteDto,
                                 Long usuarioId,
                                 Long metodoPagoId) throws Exception {

        /*RecaudadorEntity recaudadorEntity = this.iRecaudadorDao.findRecaudadorByUserId(usuarioId);
        if(recaudadorEntity.getComprobanteEnUno() == null) {
            throw new Technicalexception("No existe registrado la parametrización de emisión de comprobante en la Recaudadora");
        }
        if(recaudadorEntity.getComprobanteEnUno()) {
            postCobrarDeudasGlobal(clienteDto, usuarioId, metodoPagoId);
        } else {
            postCobrarDeudasIndividual(clienteDto, usuarioId, metodoPagoId);
        }*/
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void postCobrarDeudasIndividual(ClienteDto clienteDto,
                                            Long usuarioId,
                                            Long metodoPagoId) throws Exception {
        try {
            //1. Seleccionar todos registros de deudas y datos extras
            List<ServicioDeudaDto> servicioDeudaDtoList = iDeudaClienteRService.getDeudasCompletas(clienteDto.getServicioDeudaDtoList());
            if (servicioDeudaDtoList == null) {
                throw new Technicalexception("No se encontraron los registros completos de las deudas y datos extras");
            }

            //2. Por cada Agrupacion recorrer
            for (ServicioDeudaDto servicioDeudaDto : servicioDeudaDtoList) {

                //2.1. Recorrer las deudas con todos registro completos
                List<CobroClienteEntity> cobroClienteEntityList = new ArrayList<>();
                List<AccionEntity> accionEntityList = new ArrayList<>();
                List<DeudaClienteEntity> deudaClienteEntityList = new ArrayList<>();

                for (DeudaClienteEntity deudaClienteEntity : servicioDeudaDto.getDeudaClientes()) {
                    //2.1.1. Cargar Cobros Clientes
                    CobroClienteEntity cobroClienteEntity = loadCobroClienteEntity(deudaClienteEntity, servicioDeudaDto.deudaClienteDtos, usuarioId, metodoPagoId);
                    if (cobroClienteEntity == null) {
                        throw new Technicalexception("Se produjo un problema con el cargado de CobroClienteEntity para el registro de deudaclienteId: " + deudaClienteEntity.getDeudaClienteId());
                    }
                    cobroClienteEntityList.add(cobroClienteEntity);

                    //2.1.2. Actualizar Historico Deudas
                    HistoricoDeudaEntity historicoDeudaEntity = iHistoricoDeudaService.updateEstado(deudaClienteEntity.getDeudaClienteId(), "COBRADO");
                    if(historicoDeudaEntity == null) {
                        throw new Technicalexception("Se produjo un problema en la tabla HistoricoDeuda al actualizar el estado de deudaClienteId: " + deudaClienteEntity.getDeudaClienteId());
                    }

                    //2.1.3. Cargar Deudas en la lista
                    deudaClienteEntityList.add(deudaClienteEntity);
                }
                //2.2. Guardar CobroClientes en Lista
                cobroClienteEntityList = saveAllCobrosClientes(cobroClienteEntityList);
                if (cobroClienteEntityList.size() == 0) {
                    throw new Technicalexception("Se produo un problema al guardar la lista de registros de CobroClientes");
                }

                //2.3. Eliminar DeuassClientes en Lista
                Long recordDeletes = iDeudaClienteRService.deleteDeudasClientes(deudaClienteEntityList);
                if (recordDeletes == 0) {
                    throw new Technicalexception("Se produjo un problema al borrar la lista de DeudasClientes");
                }

                //2.4. Cargar Transacciones Cobros y Guardar
                TransaccionCobroEntity transaccionCobroEntity = iTransaccionCobroService.loadTransaccionCobro(servicioDeudaDto, usuarioId);
                transaccionCobroEntity = iTransaccionCobroService.saveTransaccionCobro(transaccionCobroEntity);
                if (transaccionCobroEntity == null) {
                    throw new Technicalexception("Se produjo un problema al guardar la TransaccionCobro");
                }

                //2.5. Encontrar los Lista de Cobros Clientes con tipo "D"
                List<CobroClienteEntity> cobroClienteEntityListFilterD = cobroClienteEntityList.stream()
                        .filter(c -> c.getTipo().equals('D')).collect(Collectors.toList());
                if (cobroClienteEntityListFilterD.size() == 0) {
                    throw new Technicalexception("Se produjo un problema al ibtener el Litsado de Deudas (Tipo D) de la Listra de Cobros Clientes");
                }

                //2.6 Cargar Comprobante
                Double montoTotalAgrupado = cobroClienteEntityListFilterD.stream()
                        .mapToDouble(s -> s.getMontoUnitario().doubleValue()).sum(); //Modificar a SubTotal OJO

                ComprobanteCobroEntity comprobanteCobroEntity = iComprobanteCobroService.loadComprobanteCobro(servicioDeudaDto, usuarioId, BigDecimal.valueOf(montoTotalAgrupado), clienteDto.getNombreCliente(), clienteDto.getNroDocumento());

                //2.7. Guardar comprobante
                comprobanteCobroEntity = iComprobanteCobroService.saveComprobanteCobro(comprobanteCobroEntity);
                if (comprobanteCobroEntity == null) {
                    throw new Technicalexception("Se produjo un problema al guardar el ComprobanteCobro");
                }

                //2.8. Recorrer Lista de Cobros Clientes con tipo "D"
                List<DetalleComprobanteCobroEntity> detalleComprobanteCobroEntityList = new ArrayList<>();

                for (CobroClienteEntity cobroClienteEntity : cobroClienteEntityListFilterD) {
                    //2.8.1. Cargar Detalle Comprobante Cobro
                    DetalleComprobanteCobroEntity detalleComprobanteCobroEntity = iDetalleComprobanteCobroService.loadDetalleComprobanteCobroEntity(transaccionCobroEntity, cobroClienteEntity, comprobanteCobroEntity, usuarioId);
                    detalleComprobanteCobroEntityList.add(detalleComprobanteCobroEntity);
                }

                //2.9. Guardar Lista Detalle Comprobantes
                List<DetalleComprobanteCobroEntity> detalleComprobanteCobroEntitiesResponse = iDetalleComprobanteCobroService.saveAllDetallesComprobantesCobos(detalleComprobanteCobroEntityList);
                if (detalleComprobanteCobroEntitiesResponse.size() == 0) {
                    throw new Technicalexception("Se produjo un problema al guardar el listado de DetalleComprobanteCobros");
                }
            }
        } catch (Exception e) {
           throw new Technicalexception(e.getMessage(), e.getCause());
        }


    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void postCobrarDeudasGlobal(ClienteDto clienteDto,
                                        Long usuarioId,
                                        Long metodoPagoId) throws Exception {

        try {

            //1. Seleccionar todos registros de deudas y datos extras
            List<ServicioDeudaDto> servicioDeudaDtoList = iDeudaClienteRService.getDeudasCompletas(clienteDto.getServicioDeudaDtoList());
            if (servicioDeudaDtoList == null) {
                throw new Technicalexception("No se encontraron los registros completos de las deudas y datos extras");
            }

            //Inicializar variables
            List<DetalleComprobanteCobroEntity> detalleComprobanteCobroEntityList = new ArrayList<>();
            BigDecimal sumaTotal = BigDecimal.valueOf(0);

            //2. Por cada Agrupacion recorrer
            for (ServicioDeudaDto servicioDeudaDto : servicioDeudaDtoList) {

                //2.1. Recorrer las deudas con todos registro completos
                List<CobroClienteEntity> cobroClienteEntityList = new ArrayList<>();
                List<AccionEntity> accionEntityList = new ArrayList<>();
                List<DeudaClienteEntity> deudaClienteEntityList = new ArrayList<>();

                for (DeudaClienteEntity deudaClienteEntity : servicioDeudaDto.getDeudaClientes()) {
                    //2.1.1. Cargar Cobros Clientes
                    CobroClienteEntity cobroClienteEntity = loadCobroClienteEntity(deudaClienteEntity, servicioDeudaDto.deudaClienteDtos, usuarioId, metodoPagoId);
                    if (cobroClienteEntity == null) {
                        throw new Technicalexception("Se produjo un problema con el cargado de CobroClienteEntity para el registro de deudaclienteId: " + deudaClienteEntity.getDeudaClienteId());
                    }
                    cobroClienteEntityList.add(cobroClienteEntity);

                    //2.1.2. Actualizar Historico Deudas
                    HistoricoDeudaEntity historicoDeudaEntity = iHistoricoDeudaService.updateEstado(deudaClienteEntity.getDeudaClienteId(), "COBRADO");
                    if(historicoDeudaEntity == null) {
                        throw new Technicalexception("Se produjo un problema en la tabla HistoricoDeuda al actualizar el estado de deudaClienteId: " + deudaClienteEntity.getDeudaClienteId());
                    }

                    //2.1.3. Cargar Deudas en la lista
                    deudaClienteEntityList.add(deudaClienteEntity);
                }

                //2.2. Guardar en lista
                cobroClienteEntityList = saveAllCobrosClientes(cobroClienteEntityList);
                if (cobroClienteEntityList.size() == 0) {
                    throw new Technicalexception("Se produo un problema al guardar la lista de registros de CobroClientes");
                }

                //2.4. Eliminar Deudas Clientes en todo en uno
                Long recordDeletes = iDeudaClienteRService.deleteDeudasClientes(deudaClienteEntityList);
                if (recordDeletes == 0) {
                    throw new Technicalexception("Se produjo un problema al borrar la lista de DeudasClientes");
                }

                //2.4. Cargar Transacciones Cobros y Guardar
                TransaccionCobroEntity transaccionCobroEntity = iTransaccionCobroService.loadTransaccionCobro(servicioDeudaDto, usuarioId);
                transaccionCobroEntity = iTransaccionCobroService.saveTransaccionCobro(transaccionCobroEntity);
                if (transaccionCobroEntity == null) {
                    throw new Technicalexception("Se produjo un problema al guardar la TransaccionCobro");
                }

                //2.5. Encontrar los Lista de Cobros Clientes con tipo "D"
                List<CobroClienteEntity> cobroClienteEntityListFilterD = cobroClienteEntityList.stream()
                        .filter(c -> c.getTipo().equals('D')).collect(Collectors.toList());
                if (cobroClienteEntityListFilterD.size() == 0) {
                    throw new Technicalexception("Se produjo un problema al ibtener el Litsado de Deudas (Tipo D) de la Listra de Cobros Clientes");
                }

                //2.6. Recorrer Lista de Cobros Clientes con tipo "D"
                for (CobroClienteEntity cobroClienteEntity : cobroClienteEntityListFilterD) {
                    DetalleComprobanteCobroEntity detalleComprobanteCobroEntity = iDetalleComprobanteCobroService.loadDetalleComprobanteCobroEntity(transaccionCobroEntity, cobroClienteEntity, null, usuarioId);
                    detalleComprobanteCobroEntityList.add(detalleComprobanteCobroEntity);
                }

                try {
                    Double montoTotalAgrupado = cobroClienteEntityListFilterD.stream()
                            .mapToDouble(s -> s.getSubTotal().doubleValue()).sum(); //Modificar a SubTotal OJO
                    sumaTotal = BigDecimal.valueOf(montoTotalAgrupado).add(sumaTotal);
                } catch(Exception e) {
                    throw new Technicalexception(e.getMessage(), e.getCause());
                }
            }

            //3. Cargar Comprobante
            ComprobanteCobroEntity comprobanteCobroEntity = iComprobanteCobroService.loadComprobanteCobro(servicioDeudaDtoList.get(0), usuarioId, sumaTotal, clienteDto.getNombreCliente(), clienteDto.getNroDocumento());

            //4. Guardar Comprobante
            comprobanteCobroEntity = iComprobanteCobroService.saveComprobanteCobro(comprobanteCobroEntity);
            if (comprobanteCobroEntity == null) {
                throw new Technicalexception("Se produjo un problema al guardar el ComprobanteCobro");
            }

            //5. Asignar el comporbante a todos los detalles de comporbamtes
            ComprobanteCobroEntity finalComprobanteCobroEntity = comprobanteCobroEntity;
            detalleComprobanteCobroEntityList.forEach(z -> z.setComprobanteCobroId(finalComprobanteCobroEntity));

            //6. Guardar Detalle comporbate
            List<DetalleComprobanteCobroEntity> detalleComprobanteCobroEntitiesResponse = iDetalleComprobanteCobroService.saveAllDetallesComprobantesCobos(detalleComprobanteCobroEntityList);
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(), e.getCause());
        }
   
    }

}
