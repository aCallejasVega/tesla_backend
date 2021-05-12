package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.dao.ISucursalEntidadDao;
import bo.com.tesla.administracion.entity.*;
import bo.com.tesla.administracion.services.IEntidadComisionService;
import bo.com.tesla.administracion.services.IRecaudadorComisionService;
import bo.com.tesla.entidades.dao.IArchivoDao;
import bo.com.tesla.facturaciones.computarizada.dto.FacturaDto;
import bo.com.tesla.facturaciones.computarizada.dto.ResponseDto;
import bo.com.tesla.facturaciones.computarizada.services.IFacturaComputarizadaService;
import bo.com.tesla.recaudaciones.dao.*;
import bo.com.tesla.recaudaciones.dto.ClienteDto;
import bo.com.tesla.recaudaciones.dto.DeudaClienteDto;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;
import bo.com.tesla.useful.config.Technicalexception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CobroClienteService implements ICobroClienteService {

    @Autowired
    private ICobroClienteDao iCobroClienteDao;

    @Autowired
    private IDeudaClienteRService iDeudaClienteRService;

    @Autowired
    private ITransaccionCobroService iTransaccionCobroService;

    @Autowired
    private IDominioDao iDominioDao;

    @Autowired
    private IHistoricoDeudaService iHistoricoDeudaService;

    @Autowired
    private IEntidadRDao iEntidadRDao;

    @Autowired
    private IHistoricoDeudaDao iHistoricoDeudaDao;

    @Autowired
    private IRecaudadorDao iRecaudadorDao;

    @Autowired
    private IEntidadComisionService iEntidadComisionService;

    @Autowired
    private IRecaudadorComisionService iRecaudadorComisionService;

    @Autowired
    private IArchivoDao iArchivoDao;

    @Autowired
    private IFacturaComputarizadaService facturacionComputarizadaService;

    @Autowired
    private ITransaccionCobroDao transaccionCobroDao;

    @Autowired
    private ISucursalEntidadDao sucursalEntidadDao;

    @Override
    public List<CobroClienteEntity> saveAllCobrosClientes(List<CobroClienteEntity> cobroClienteEntities) {
        return this.iCobroClienteDao.saveAll(cobroClienteEntities);
    }

    @Override
    public CobroClienteEntity loadCobroClienteEntity(DeudaClienteEntity deudaClienteEntity,
                                                     List<DeudaClienteDto> deudaClienteDtos,
                                                     Long usuarioId,
                                                     Long metodoPagoId,
                                                     TransaccionCobroEntity transaccionCobroEntity) {

        Optional<HistoricoDeudaEntity> historicoDeudaEntityOptional = iHistoricoDeudaDao.findByDeudaClienteId(deudaClienteEntity.getDeudaClienteId());
        if(!historicoDeudaEntityOptional.isPresent()) {
            throw new Technicalexception("No existe el registro historico de deudaClienteId=" + deudaClienteEntity.getDeudaClienteId());
        }

        CobroClienteEntity cobroClienteEntity = new CobroClienteEntity();
        cobroClienteEntity.setArchivoId(deudaClienteEntity.getArchivoId());
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
        cobroClienteEntity.setTransaccion("COBRAR");
        cobroClienteEntity.setTransaccionCobro(transaccionCobroEntity);
        cobroClienteEntity.setHistoricoDeuda(historicoDeudaEntityOptional.get());

        //control de MontoUnitarios y Subtotales por prepago
        if(deudaClienteEntity.getTipo() == 'D' && !deudaClienteEntity.getEsPostpago() && deudaClienteEntity.getSubTotal().compareTo(BigDecimal.ZERO) == 0) {
            DeudaClienteDto deudaClienteDto = deudaClienteDtos.stream().filter(d -> d.deudaClienteId.equals(deudaClienteEntity.getDeudaClienteId())).findAny().orElse(null);
            if(deudaClienteDto == null) {
                throw new Technicalexception("No se ha logrado encontrar la deuda enviada, deudaClienteId=" + deudaClienteEntity.getDeudaClienteId());
            }
            cobroClienteEntity.setMontoUnitario(deudaClienteDto.montoUnitario);
            cobroClienteEntity.setSubTotal(deudaClienteDto.subTotal);
            cobroClienteEntity.setMontoModificado(true);

        } else {
            cobroClienteEntity.setMontoUnitario(deudaClienteEntity.getMontoUnitario());
            cobroClienteEntity.setSubTotal(deudaClienteEntity.getSubTotal());
            cobroClienteEntity.setMontoModificado(false);
        }

        return  cobroClienteEntity;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Technicalexception.class)
    public String postCobrarDeudas(ClienteDto clienteDto,
                                    Long usuarioId,
                                    Long metodoCobroId) {
        List<TransaccionCobroEntity> transaccionesCobroList=new ArrayList<>();
        try{
            //Obtencion Datos Entidad
            Optional<EntidadEntity> entidadEntityOptional = iEntidadRDao.findByEntidadIdAndEstado(clienteDto.servicioDeudaDtoList.get(0).entidadId, "ACTIVO");
            if(!entidadEntityOptional.isPresent()) {
                throw new Technicalexception("No existe Entidad, por tanto no hay configuracion de comprobanteEnUno");
            }

            //Encontrar Sucursal que emite Factura
            Optional<SucursalEntidadEntity> sucursalEntidadEntityOptional  = sucursalEntidadDao.findByEmiteFacturaTesla(clienteDto.servicioDeudaDtoList.get(0).entidadId);
            if(!sucursalEntidadEntityOptional.isPresent()) {
                throw new Technicalexception("No se encuentra registrada la sucursal que emitirá la(s) factura(s).");
            }

            //Obtencion de Archivo
            ArchivoEntity archivoEntity = iArchivoDao.findByEstado("ACTIVO", clienteDto.servicioDeudaDtoList.get(0).entidadId);
            if(archivoEntity == null) {
                throw new Technicalexception("No existe un archivo activo o no se encontra el archivoId" + entidadEntityOptional.get().getEntidadId());
            }

            //Obtencon Datos de Recaudador
            Optional<RecaudadorEntity> recaudadorEntityOptional = iRecaudadorDao.findRecaudadorByUserId(usuarioId);
            if(!recaudadorEntityOptional.isPresent()) {
                throw new Technicalexception("El usuarioId=" + usuarioId + " no esta registrado en ninguna sucursal de recaudadción");
            }

            //Obtencion de comisión entidad
            EntidadComisionEntity entidadComisionEntity = iEntidadComisionService.getEntidadComisionActual(entidadEntityOptional.get());

            //Obtencion de comisión recaudadora
            RecaudadorComisionEntity recaudadorComisionEntity = iRecaudadorComisionService.getRecaudadorComisionActual(recaudadorEntityOptional.get());

            //Verificar el dominio metodoCobro
            Optional<DominioEntity> metodoCobroOptional = iDominioDao.getDominioEntityByDominioIdAndDominioAndEstado(metodoCobroId, "metodo_cobro_id", "ACTIVO");
            if(!metodoCobroOptional.isPresent()) {
                throw new Technicalexception("No existe el dominio='metodo_cobro_id' para dominioId=" + metodoCobroId );
            }

            //Para emision de comprobante
            boolean comprobanteEnUno = entidadEntityOptional.get().getComprobanteEnUno();

            //Recorrer las agrupaciones
            for(ServicioDeudaDto servicioDeudaDto : clienteDto.servicioDeudaDtoList){
                //Recuperar Deudas Completas por agrupacion
                List<DeudaClienteEntity> deudaClienteEntityList = iDeudaClienteRService.getAllDeudasByCliente(servicioDeudaDto.entidadId,
                        servicioDeudaDto.tipoServicio,
                        servicioDeudaDto.servicio,
                        servicioDeudaDto.periodo,
                        servicioDeudaDto.codigoCliente);
                if(deudaClienteEntityList.isEmpty()) {
                    throw new Technicalexception("No se ha encontrado el Listado de Todas las Deudas del cliente: " + servicioDeudaDto.codigoCliente);
                }

                /*
                //Verificar un solo Tipo de Comprobante por Transacción
                List<Boolean> tipoComprobanteLst = iDeudaClienteRService.getTipoComprobanteUnicos(deudaClienteEntityList);
                if(tipoComprobanteLst.size() > 1) {
                    throw new Technicalexception("Se ha identificado en el cargado de deudas, diferentes Tipos de Comprobante para la agrupacipon: " +
                             " ArchivoId=" + archivoEntity.getArchivoId() +
                            ", TipoServicio=" + servicioDeudaDto.tipoServicio +
                            ", Servicio= " + servicioDeudaDto.servicio +
                            ", Periodo=" + servicioDeudaDto.periodo +
                            ", Codigo   Cliente=" + servicioDeudaDto.codigoCliente);
                }
                */

                //Verificar un solo Código Actividad Económica por Transacción
                List<String> codActEcoList = iDeudaClienteRService.getCodigosActividadUnicos(deudaClienteEntityList);
                if(codActEcoList.size() > 1) {
                    throw new Technicalexception("Se ha identificado en el cargado de deudas, diferentes Códigos de Actividad Económica para la agrupacipon: " +
                            " ArchivoId=" + archivoEntity.getArchivoId() +
                            ", TipoServicio=" + servicioDeudaDto.tipoServicio +
                            ", Servicio= " + servicioDeudaDto.servicio +
                            ", Periodo=" + servicioDeudaDto.periodo +
                            ", CodigoCliente=" + servicioDeudaDto.codigoCliente);
                }

                //Cargar transaccion las agrupaciones
                TransaccionCobroEntity transaccionCobroEntity = iTransaccionCobroService.loadTransaccionCobro(servicioDeudaDto, entidadEntityOptional.get(), usuarioId, clienteDto.nombreCliente, clienteDto.nroDocumento,
                        entidadComisionEntity, recaudadorEntityOptional.get(), recaudadorComisionEntity, archivoEntity, metodoCobroOptional.get(),
                        entidadEntityOptional.get().getModalidadFacturacion(),
                        deudaClienteEntityList.get(0).getCodigoActividadEconomica());//Habiendo validado unico por transaccion

                List<CobroClienteEntity> cobroClienteEntityList = new ArrayList<>();
                //Recorrer cada deuda asociada a la agrupacion
                for(DeudaClienteEntity deudaClienteEntity : deudaClienteEntityList) {
                    //Cargando Cobro
                    CobroClienteEntity cobroClienteEntity = loadCobroClienteEntity(deudaClienteEntity, servicioDeudaDto.deudaClienteDtos, usuarioId, metodoCobroId, transaccionCobroEntity);
                    cobroClienteEntityList.add(cobroClienteEntity);

                }
                //Registrar transaccion  por agrupacion
                transaccionCobroEntity.setCobroClienteEntityList(cobroClienteEntityList);
                transaccionCobroEntity = iTransaccionCobroService.saveTransaccionCobro(transaccionCobroEntity);

                if(transaccionCobroEntity.getTransaccionCobroId() == null) {
                    throw new Technicalexception("No se ha registrado la transacción");
                }
                transaccionesCobroList.add(transaccionCobroEntity);

                //Actualizar Historico por agrupacion
                Integer countUpdate = iHistoricoDeudaService.updateHistoricoDeudaLst(deudaClienteEntityList);
                if(countUpdate != deudaClienteEntityList.size()) {
                    throw new Technicalexception("Se produjo un problema al actualizar Historico de la lista de DeudasClientes");
                }
                //Eliminar Deudas por agrupacion
                Long recordDeletes = iDeudaClienteRService.deleteDeudasClientes(deudaClienteEntityList);
                if (recordDeletes != deudaClienteEntityList.size()) {
                    throw new Technicalexception("Se produjo un problema al borrar la lista de DeudasClientes");
                }
            }

            /******************************************************************************************/
            //Lamar a Facturacion
            /******************************************************************************************/

            ResponseDto responseDto = generarFactura(entidadEntityOptional.get(),
                    sucursalEntidadEntityOptional.get(),
                    transaccionesCobroList,
                    comprobanteEnUno,
                    clienteDto.montoTotalCobrado);

            //*****************************************************************************************

            return responseDto.report;
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(), e.getCause());
        }
    }

    public ResponseDto generarFactura(EntidadEntity entidadEntity,
                                      SucursalEntidadEntity sucursalEntidadEntity,
                                      List<TransaccionCobroEntity> transaccionCobroEntityList,
                                      boolean comprobanteEnUno,
                                      BigDecimal montoTotalCobrado) {

        //Controlar la parametrización de las Modalidades de Facturacion
        Optional<Long> modFactCompuOptional = iDominioDao.getDominioIdByDominioAndAbreviatura("modalidad_facturacion_id", "FC");
        if(!modFactCompuOptional.isPresent()) {
            throw new Technicalexception("No existe el dominio='modalidad_facturacion_id, abreviatura='FC' para la facturación computarizada");
        }
/*Habilitar cuando se incluya demás modalidades
        Optional<Long> modFactCompuELOptional = iDominioDao.getDominioIdByDominioAndAbreviatura("modalidad_facturacion_id", "FCEL");
        if(!modFactCompuOptional.isPresent()) {
            throw new Technicalexception("No existe el dominio='modalidad_facturacion_id, abreviatura='FCEL' para la facturación computarizada en línea");
        }

        Optional<Long> modFactElectOptional = iDominioDao.getDominioIdByDominioAndAbreviatura("modalidad_facturacion_id", "FEEL");
        if(!modFactCompuOptional.isPresent()) {
            throw new Technicalexception("No existe el dominio='modalidad_facturacion_id, abreviatura='FEEL' para la facturación electrónica en línea");
        }
*/
        //Facturación computarizada
        if(entidadEntity.getModalidadFacturacion().getDominioId() == modFactCompuOptional.get()) {
            ResponseDto responseDto = facturacionComputarizadaService.postFacturas(sucursalEntidadEntity, transaccionCobroEntityList, comprobanteEnUno, montoTotalCobrado);
            if (!responseDto.status) {
                throw new Technicalexception(responseDto.message);
            }

            List<FacturaDto> facturaDtoList = (List<FacturaDto>) responseDto.result;
            iTransaccionCobroService.updateFacturas(transaccionCobroEntityList, comprobanteEnUno, facturaDtoList);

            return responseDto;
        } else { //Otra Modalidad
            throw new Technicalexception("Solo esta habilitada la Modalidad de Facturación Computarizada, verifique configuración en la tabla Entidad.");
        }
    }


}
