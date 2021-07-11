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
import bo.com.tesla.useful.config.BusinesException;
import bo.com.tesla.useful.config.Technicalexception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


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
                                                       DominioEntity modalidadFacturacion,
                                                       String codigoActividadEconomica) {

        TransaccionCobroEntity transaccionCobroEntity = new TransaccionCobroEntity();
        transaccionCobroEntity.setTipoServicio(servicioDeudaDto.tipoServicio);
        transaccionCobroEntity.setServicio(servicioDeudaDto.servicio);
        transaccionCobroEntity.setPeriodo(servicioDeudaDto.periodo);
        transaccionCobroEntity.setUsuarioCreacion(usuarioId);
        transaccionCobroEntity.setFechaCreacion(new Date());
        transaccionCobroEntity.setEntidadId(entidadEntity);
        transaccionCobroEntity.setTransaccion("CREAR");
        transaccionCobroEntity.setArchivoId(archivoEntity);
        transaccionCobroEntity.setCodigoCliente(servicioDeudaDto.codigoCliente);
        transaccionCobroEntity.setNombreClientePago(nombreCientePago);
        transaccionCobroEntity.setTotalDeuda(servicioDeudaDto.subTotal);
        transaccionCobroEntity.setNroDocumentoClientePago(nroDocumentoClientePago);
        transaccionCobroEntity.setRecaudador(recaudadorEntity);
        transaccionCobroEntity.setNombreClienteArchivo(servicioDeudaDto.nombreCliente);
        transaccionCobroEntity.setNroDocumentoClienteArchivo(servicioDeudaDto.nroDocumento);
        transaccionCobroEntity.setEntidadComision(entidadComisionEntity);
        transaccionCobroEntity.setRecaudadorComision(recaudadorComisionEntity);
        transaccionCobroEntity.setMetodoCobro(metodoCobro);
        transaccionCobroEntity.setModalidadFacturacion(modalidadFacturacion);
        transaccionCobroEntity.setCodigoActividadEconomica(codigoActividadEconomica);
        /*************************************************
        14/05/2021 - Segun reunión.
        Se comentas hasta incluir la comision de acuerdo a requerimiento, se asigna 1
        **************************************************
        transaccionCobroEntity.setComision(iEntidadComisionService.calcularComision(entidadComisionEntity, servicioDeudaDto.subTotal));
        transaccionCobroEntity.setComisionRecaudacion(iRecaudadorComisionService.calcularComision(recaudadorComisionEntity, servicioDeudaDto.subTotal));
        *************************************************/
        transaccionCobroEntity.setComision(new BigDecimal(1));
        transaccionCobroEntity.setComisionRecaudacion(new BigDecimal(1));
        return  transaccionCobroEntity;
    }


	@Override
	public List<TransaccionCobroEntity> findDeudasCobradasByUsuarioCreacionForGrid(Long usuarioCreacion,
			Date fechaSeleccionada,Long entidadId) {
	
		return this.iTransaccionCobroDao.findDeudasCobradasByUsuarioCreacionForGrid(usuarioCreacion, fechaSeleccionada, entidadId);
	}

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Technicalexception.class)
    public Boolean anularTransaccionConRecuperacionDeudas(Long entidadId,
                                                          AnulacionFacturaLstDto anulacionFacturaLstDto,
                                                          Long modalidadFacturacionId,
                                                          SegUsuarioEntity usuarioEntity) throws BusinesException {

        //Verificar si en la selección de facturas existen archivos que ya no esten activos
        Integer countArchivosNoActivos = iTransaccionCobroDao.countArchivosNoActivosPorListaFacturas(anulacionFacturaLstDto.facturaIdLst, modalidadFacturacionId);
        if(countArchivosNoActivos > 0) {
            throw new BusinesException("El archivo del cargado de la deuda ya no es el actual. Comuníquese con el administrador.");
        }

        try {
            //Anular Transacciones
            Integer countupdate = iTransaccionCobroDao.updateLstTransaccionByFacturas(anulacionFacturaLstDto.facturaIdLst, modalidadFacturacionId, "ANULAR", usuarioEntity.getUsuarioId());
            if (countupdate == 0) {
                throw new Technicalexception("No se ha logrado actualizar a ANULADO las transacciones.");
            }

            //Anular Cobros
            Integer countUpdateCobros = iCobroClienteDao.updateLstTransaccionByFacturas(anulacionFacturaLstDto.facturaIdLst, "ANULAR", usuarioEntity.getUsuarioId());
            if (countUpdateCobros == 0) {
                throw new Technicalexception("No se ha logrado actualizar a ANULADO los CobrosClientes.");
            }

            //Actualizar estado de históricos
            Integer countHistoricos = historicoDeudaService.updateHistoricoDeudaLstByFacturas(anulacionFacturaLstDto.facturaIdLst, "ANULADO");
            if(countHistoricos == 0) {
                throw new Technicalexception("No se ha logrado actualizar el estado de los registros históricos de deudas");
            }

            //Recuperar las deudas
            Integer countRecovery = deudaClienteRService.recoverDeudasByFacturas(anulacionFacturaLstDto.facturaIdLst);
            if(countRecovery < 1) {
                throw new Technicalexception("No se ha recuperado ninguna deuda");
            }

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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Technicalexception.class)
    public Boolean anularTransaccionPorCargadoErroneo(Long entidadId,
                                                      AnulacionFacturaLstDto anulacionFacturaLstDto,
                                                      Long modalidadFacturacionId,
                                                      SegUsuarioEntity usuarioEntity) throws BusinesException {
        //Verificar si en la selección de facturas existen archivos que ya no esten activos
        Integer countArchivosNoActivos = iTransaccionCobroDao.countArchivosNoActivosPorListaFacturas(anulacionFacturaLstDto.facturaIdLst, modalidadFacturacionId);
        if(countArchivosNoActivos > 0) {
            throw new BusinesException("El archivo del cargado de la deuda ya no es el actual. Comuníquese con el administrador.");
        }

        try {
            //Anular Transacciones
            Integer countupdate = iTransaccionCobroDao.updateLstTransaccionByFacturas(anulacionFacturaLstDto.facturaIdLst, modalidadFacturacionId, "ANULAR", usuarioEntity.getUsuarioId());
            if (countupdate == 0) {
                throw new Technicalexception("No se ha logrado actualizar a ANULADO las transacciones.");
            }

            //Anular Cobros
            Integer countUpdateCobros = iCobroClienteDao.updateLstTransaccionByFacturas(anulacionFacturaLstDto.facturaIdLst, "ANULAR", usuarioEntity.getUsuarioId());
            if (countUpdateCobros == 0) {
                throw new Technicalexception("No se ha logrado actualizar a ANULADO los CobrosClientes.");
            }

            //Actualizar estado de históricos
            Integer countHistoricos = historicoDeudaService.updateHistoricoDeudaLstByFacturas(anulacionFacturaLstDto.facturaIdLst, "ERRONEO");
            if(countHistoricos == 0) {
                throw new Technicalexception("No se ha logrado actualizar el estado de los registros históricos de deudas");
            }

            //No recupera deudas

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
    public void updateFacturasTransaccion(List<FacturaDto> facturaDtoList) {
        facturaDtoList.forEach(f -> {
            Integer countUpdate = iTransaccionCobroDao.updateFacturaTransaccion(f.transaccionIdLst, f.facturaId);
            if(f.transaccionIdLst.size() != countUpdate) {
                throw new Technicalexception("No se actualizado correctamente las facturas");
            }
        });
    }

    @Override
    public List<String> getCodigosActividadUnicos(List<TransaccionCobroEntity> transaccionCobroEntityList) {
        List<String> codActEcoList = transaccionCobroEntityList.stream().map(t -> t.getCodigoActividadEconomica())
                .collect(Collectors.toList());
        return codActEcoList.stream().distinct().collect(Collectors.toList());
    }

	@Override
	public TransaccionCobroEntity loadTransaccionCobro(ServicioDeudaDto servicioDeudaDto, EntidadEntity entidadEntity,
			Long usuarioId, String nombreCientePago, String nroDocumentoClientePago,
			EntidadComisionEntity entidadComisionEntity, RecaudadorEntity recaudadorEntity,
			RecaudadorComisionEntity recaudadorComisionEntity, ArchivoEntity archivoEntity, DominioEntity metodoCobro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> findFacturasByModalidadAndEntidadAndRecaudador(Long modalidadFacturaId,
                                                                     Long entidadId,
                                                                     Long recaudadorId) {
        return iTransaccionCobroDao.findFacturasByModalidadAndEntidadAndRecaudador(modalidadFacturaId,
                entidadId,
                recaudadorId
        );
    }

}
