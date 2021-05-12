package bo.com.tesla.security.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bo.com.tesla.administracion.entity.SegPrivilegioEntity;
import bo.com.tesla.security.dao.ISegPrivilegiosDao;
import bo.com.tesla.security.dto.OperacionesDto;

@Service
public class SegPrivilegiosService implements ISegPrivilegiosService {

	@Autowired
	private ISegPrivilegiosDao segPrivilegiosDao;

	@Override
	public List<SegPrivilegioEntity> getMenuByUserId(Long usuarioId) {
		List<SegPrivilegioEntity> sePrivilegioList = new ArrayList<>();
		try {
			 sePrivilegioList = this.segPrivilegiosDao.getMenuByUserId(usuarioId);		
		} catch (Exception e) {
			e.printStackTrace();

		}

		return sePrivilegioList;

	}

	@Override
	public List<SegPrivilegioEntity> getSubMenuByUserId(Long usuarioId) {
		List<SegPrivilegioEntity> segPrivilegioSubMenu = new ArrayList<>();
		List<SegPrivilegioEntity> segPrivilegioSubMenuNew = new ArrayList<>();
		
		SegPrivilegioEntity privilegioPadre=new SegPrivilegioEntity();
		SegPrivilegioEntity privilegiosNenu=new SegPrivilegioEntity();
		try {
			List<SegPrivilegioEntity> sePrivilegioList = this.segPrivilegiosDao.getSubMenuByUserId(usuarioId);
			

		
				
				for (SegPrivilegioEntity segPrivilegioN1 : sePrivilegioList) {
					privilegiosNenu=new SegPrivilegioEntity();			
					segPrivilegioSubMenuNew = new ArrayList<>();
					for (SegPrivilegioEntity segPrivilegioN3 : segPrivilegioN1.getSegPrivilegioEntityList()) {
						String estado = this.segPrivilegiosDao.getEstadoPrivilegios(usuarioId,
								segPrivilegioN3.getPrivilegiosId());

						if (estado != null) {
							if (estado.equals("ACTIVO")) {
								
								segPrivilegioSubMenuNew.add(segPrivilegioN3);
							}
						}
					}
					if(!segPrivilegioSubMenuNew.isEmpty()) {
						privilegiosNenu.setDescripcion(segPrivilegioN1.getDescripcion()); 
						privilegiosNenu.setIcono(segPrivilegioN1.getIcono());
						privilegiosNenu.setLink(segPrivilegioN1.getLink());
						privilegiosNenu.setOrden(segPrivilegioN1.getOrden());
						privilegiosNenu.setSegPrivilegioEntityList(segPrivilegioSubMenuNew);
						segPrivilegioSubMenu.add(privilegiosNenu);
					}
					
				}
			

			
			
			 
		} catch (Exception e) {
			e.printStackTrace();

		}

		return segPrivilegioSubMenu;

	}

	@Override
	public List<OperacionesDto> getOperaciones(String login, String tabla) {
		List<Object[]> operacionesLis = this.segPrivilegiosDao.getOperaciones(login, tabla);
		List<OperacionesDto> transicionEntities = new ArrayList<>();
		for (Object[] objects : operacionesLis) {
			OperacionesDto operacion = new OperacionesDto(objects[0] + "", objects[1] + "", objects[2] + "",
					objects[3] + "");
			transicionEntities.add(operacion);
		}

		return transicionEntities;
	}

	@Override
	public List<OperacionesDto> getOperacionesByEstadoInicial(String login, String tabla, String estadoInicial) {
		List<Object[]> operacionesLis = this.segPrivilegiosDao.getOperacionesByEstadoInicial(login, tabla,
				estadoInicial);
		List<OperacionesDto> transicionEntities = new ArrayList<>();
		for (Object[] objects : operacionesLis) {
			OperacionesDto operacion = new OperacionesDto(objects[0] + "", objects[1] + "", objects[2] + "",
					objects[3] + "");
			transicionEntities.add(operacion);
		}

		return transicionEntities;
	}

}
