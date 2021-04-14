package bo.com.tesla.pagos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import bo.com.tesla.administracion.entity.EntidadEntity;
import bo.com.tesla.administracion.entity.PBeneficiariosEntity;
import bo.com.tesla.entidades.dao.IEntidadDao;
import bo.com.tesla.pagos.dao.IBeneficiarioDao;
import bo.com.tesla.pagos.dto.PBeneficiarioDto;
import bo.com.tesla.recaudaciones.services.IRecaudadoraService;

@Service
public class PAbonoClienteService implements IPAbonoClienteService {

	@Autowired
	private IBeneficiarioDao abonoClienteDao;
	
	@Autowired
	private IEntidadDao  entidadDao;
	

	@Override
	public Page<PBeneficiarioDto> groupByAbonosClientes(Long archivoId, String paramBusqueda, int page, int size) {
		Page<PBeneficiarioDto> deudaClienteList;
		Pageable paging = PageRequest.of(page, size);
		deudaClienteList = this.abonoClienteDao.groupByAbonosClientes(archivoId, paramBusqueda, paging);

		for (PBeneficiarioDto pAbonoClienteDto : deudaClienteList) {
			
			
			List<PBeneficiarioDto> abonoList = this.abonoClienteDao.findByCodigoAndArchivoId(archivoId,
					pAbonoClienteDto.codigoCliente,pAbonoClienteDto.periodo);
			if (!abonoList.isEmpty()) {
				pAbonoClienteDto.key = abonoList.get(0).nroRegistro;
				pAbonoClienteDto.cantidad = abonoList.get(0).cantidad;
				pAbonoClienteDto.concepto = abonoList.get(0).concepto;
				pAbonoClienteDto.extencionDocumento = abonoList.get(0).extencionDocumento;
				pAbonoClienteDto.fechaNacimientoCliente = abonoList.get(0).fechaNacimientoCliente;
				pAbonoClienteDto.genero = abonoList.get(0).genero;
				pAbonoClienteDto.montoUnitario = abonoList.get(0).montoUnitario;
				pAbonoClienteDto.nombreCliente = abonoList.get(0).nombreCliente;
				pAbonoClienteDto.nroDocumentoCliente = abonoList.get(0).nroDocumentoCliente;
				pAbonoClienteDto.nroRegistro = abonoList.get(0).nroRegistro;
				pAbonoClienteDto.periodo = abonoList.get(0).periodo;
				pAbonoClienteDto.tipoDocumentoId = abonoList.get(0).tipoDocumentoId;
				pAbonoClienteDto.archivoId = abonoList.get(0).archivoId;
				pAbonoClienteDto.abonosClientesList = abonoList;

			}
		}
		return deudaClienteList;

	}

	@Override
	public void deletByArchivoId(Long archivoId) {
		this.abonoClienteDao.deletByArchivoId(archivoId);		
	}

	@Override
	public List<PBeneficiarioDto> getAbonosParaPagar(Long servicioProductoId,Long recaudadorId,String paramBusqueda) {
		List<Long> entidadIdList=new ArrayList<>();
		List<EntidadEntity> entidadList= this.entidadDao.findEntidadByRecaudacionId(recaudadorId);
		for (EntidadEntity entidadEntity : entidadList) {
			entidadIdList.add(entidadEntity.getEntidadId());
		}
		
		return this.abonoClienteDao.getAbonosParaPagar(servicioProductoId, entidadIdList,paramBusqueda);
	}

	@Override
	public List<PBeneficiarioDto> getBeneficiarioPagos(Long archivoId,String codigoCliente, String nroDocumentoCliente) {		
		List<PBeneficiarioDto> abonadoList= this.abonoClienteDao.getBeneficiario(archivoId,codigoCliente, nroDocumentoCliente);
		
		for (PBeneficiarioDto pAbonoClienteDto : abonadoList) {
			List<PBeneficiarioDto> abonadoDetalle=this.abonoClienteDao.getBeneficiarioDetalle(
					archivoId,pAbonoClienteDto.codigoCliente, pAbonoClienteDto.nroDocumentoCliente,pAbonoClienteDto.periodo);
			if (!abonadoDetalle.isEmpty()) {
				
				if(pAbonoClienteDto.archivoId == null) {
					pAbonoClienteDto.key = abonadoDetalle.get(0).nroRegistro;
					pAbonoClienteDto.cantidad = abonadoDetalle.get(0).cantidad;
					pAbonoClienteDto.concepto = abonadoDetalle.get(0).concepto;
					pAbonoClienteDto.extencionDocumento = abonadoDetalle.get(0).extencionDocumento;
					pAbonoClienteDto.fechaNacimientoCliente = abonadoDetalle.get(0).fechaNacimientoCliente;
					pAbonoClienteDto.genero = abonadoDetalle.get(0).genero;
					pAbonoClienteDto.montoUnitario = abonadoDetalle.get(0).montoUnitario;
					pAbonoClienteDto.nombreCliente = abonadoDetalle.get(0).nombreCliente;
					pAbonoClienteDto.nroDocumentoCliente = abonadoDetalle.get(0).nroDocumentoCliente;
					pAbonoClienteDto.nroRegistro = abonadoDetalle.get(0).nroRegistro;
					pAbonoClienteDto.periodo = abonadoDetalle.get(0).periodo;
					pAbonoClienteDto.tipoDocumentoId = abonadoDetalle.get(0).tipoDocumentoId;
					pAbonoClienteDto.archivoId = abonadoDetalle.get(0).archivoId;	
				}
				
				pAbonoClienteDto.abonosClientesList = abonadoDetalle;
			}
	
		}
				
		return abonadoList;
	}

}
