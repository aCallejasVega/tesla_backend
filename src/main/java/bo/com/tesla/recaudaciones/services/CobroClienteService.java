package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.*;
import bo.com.tesla.administracion.services.IEntidadComisionService;
import bo.com.tesla.administracion.services.IRecaudadorComisionService;
import bo.com.tesla.entidades.dao.IArchivoDao;
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

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Technicalexception.class)
    public List<TransaccionCobroEntity> postCobrarDeudas(ClienteDto clienteDto,
                                  Long usuarioId,
                                  Long metodoCobroId) throws Technicalexception {
    	List<TransaccionCobroEntity> transaccionesCobroList=new ArrayList<>();
        try{
            //Obtencion Datos Entidad
            Optional<EntidadEntity> entidadEntityOptional = iEntidadRDao.findByEntidadIdAndEstado(clienteDto.servicioDeudaDtoList.get(0).entidadId, "ACTIVO");
            if(!entidadEntityOptional.isPresent()) {
                throw new Technicalexception("No existe Entidad, por tanto no hay configuracion de comprobanteEnUno");
            }

            //Obtencion de Archivo
            ArchivoEntity archivoEntity = iArchivoDao.findByEstado("ACTIVO", clienteDto.servicioDeudaDtoList.get(0).entidadId);
            if(archivoEntity == null) {
                throw new Technicalexception("No se encontró la archivoId" + entidadEntityOptional.get().getEntidadId());
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

            //Verificar el dominio metodoPagoId
            Optional<DominioEntity> metodoCobroOptional = iDominioDao.getDominioEntityByDominioIdAndDominioAndEstado(metodoCobroId, "metodo_cobro_id", "ACTIVO");
            if(!metodoCobroOptional.isPresent()) {
                throw new Technicalexception("No existe el dominio='metodo_cobro_id' para dominioId=" + metodoCobroId );
            }

            //Para emision de comprobante
            boolean comprobanteEnUno = entidadEntityOptional.get().getComprobanteEnUno();
            /******************************************************************************************/
            //Lamar a Facturacion o Recibo
            /******************************************************************************************/

            
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

                //Cargar transaccion las agrupaciones
                TransaccionCobroEntity transaccionCobroEntity = iTransaccionCobroService.loadTransaccionCobro(servicioDeudaDto, entidadEntityOptional.get(), usuarioId, clienteDto.nombreCliente, clienteDto.nroDocumento,
                        entidadComisionEntity, recaudadorEntityOptional.get(), recaudadorComisionEntity, archivoEntity, metodoCobroOptional.get());

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
            
           
            
            
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(), e.getCause());
        }
        return transaccionesCobroList;
    }
}
