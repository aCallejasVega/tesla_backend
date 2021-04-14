package bo.com.tesla.pagos.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bo.com.tesla.administracion.entity.ArchivoEntity;
import bo.com.tesla.administracion.entity.PPagoClienteEntity;
import bo.com.tesla.administracion.entity.PTitularPagoEntity;
import bo.com.tesla.administracion.entity.PTransaccionPagoEntity;
import bo.com.tesla.entidades.dao.IArchivoDao;
import bo.com.tesla.pagos.dao.IBeneficiarioDao;
import bo.com.tesla.pagos.dao.IPHistoricoAbonoClienteDao;
import bo.com.tesla.pagos.dao.IPPagoClienteDao;
import bo.com.tesla.pagos.dao.PTitularPagoDao;
import bo.com.tesla.pagos.dto.PBeneficiarioDto;

@Service
public class PPagoClienteService implements IPPagoClienteService {

	@Autowired
	private IPPagoClienteDao pagoClienteDao;

	@Autowired
	private IArchivoDao archivoDao;

	@Autowired
	private IPHistoricoAbonoClienteDao historicoAbonoClienteDao;

	@Autowired
	private IBeneficiarioDao abonoClienteDao;

	@Autowired
	private IPTransaccionPagoService transaccionPagoService;
	
	
	
	
	@Override
	public PPagoClienteEntity save(PPagoClienteEntity entity) {
		return this.pagoClienteDao.save(entity);
	}

	@Transactional
	@Override
	public PTransaccionPagoEntity realizarPago(List<PBeneficiarioDto> abonoCliente, Long usuarioId) {
		
		List<PPagoClienteEntity> pagoClienteList=new ArrayList<>();

		PTransaccionPagoEntity transaccionPago = this.transaccionPagoService.saveForPagoAbonado(abonoCliente,
				usuarioId);
		

		for (PBeneficiarioDto pAbonoClienteDto : abonoCliente) {

			ArchivoEntity archivo = this.archivoDao.findById(pAbonoClienteDto.archivoId).get();

			List<PBeneficiarioDto> abonosClientesList = this.abonoClienteDao.getBeneficiarioDetalle(
					pAbonoClienteDto.archivoId, pAbonoClienteDto.codigoCliente, pAbonoClienteDto.nroDocumentoCliente,
					pAbonoClienteDto.periodo);

			for (PBeneficiarioDto abonoClienteDetalle : abonosClientesList) {
				PPagoClienteEntity pagosCliente = new PPagoClienteEntity();
				pagosCliente.setArchivoId(archivo);
				pagosCliente.setCantidad(abonoClienteDetalle.cantidad);
				pagosCliente.setCodigoCliente(abonoClienteDetalle.codigoCliente);
				pagosCliente.setConcepto(abonoClienteDetalle.concepto);
				pagosCliente.setExtencionDocumentoId(abonoClienteDetalle.extencionDocumento);
				pagosCliente.setFechaNacimientoCliente(abonoClienteDetalle.fechaNacimientoCliente);
				pagosCliente.setGenero(abonoClienteDetalle.genero);
				pagosCliente.setMontoUnitario(abonoClienteDetalle.montoUnitario);
				pagosCliente.setNombreCliente(abonoClienteDetalle.nombreCliente);
				pagosCliente.setNroDocumentoCliente(abonoClienteDetalle.nroDocumentoCliente);
				pagosCliente.setNroRegistro(abonoClienteDetalle.nroRegistro);
				pagosCliente.setPeriodo(abonoClienteDetalle.periodo);
				pagosCliente.setTipoDocumentoId(abonoClienteDetalle.tipoDocumentoId);
				pagosCliente.setTransaccionPagoId(transaccionPago);
				pagosCliente.setEstado("PAGADO");
				pagosCliente.setTransaccion("PAGAR");
				pagosCliente.setUsuarioCreacion(usuarioId);
				pagosCliente.setFechaCreacion(new Date());

				this.pagoClienteDao.save(pagosCliente);
			}

			this.historicoAbonoClienteDao.updateByArchivoId(pAbonoClienteDto.archivoId, pAbonoClienteDto.codigoCliente,
					pAbonoClienteDto.nroDocumentoCliente, usuarioId, new Date(), pAbonoClienteDto.periodo);

			this.abonoClienteDao.deleteAbonados(pAbonoClienteDto.archivoId, pAbonoClienteDto.codigoCliente,
					pAbonoClienteDto.nroDocumentoCliente, pAbonoClienteDto.periodo);
		}
		
		//pagoClienteList=this.pagoClienteDao.findByTransaccionPagoId(transaccionPago.getTransaccionPagoId());

		return transaccionPago;
	}

}
