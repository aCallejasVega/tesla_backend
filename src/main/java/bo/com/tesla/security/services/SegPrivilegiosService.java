package bo.com.tesla.security.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bo.com.tesla.administracion.entity.SegPrivilegioEntity;
import bo.com.tesla.administracion.entity.SegTransicionEntity;
import bo.com.tesla.security.dao.ISegPrivilegiosDao;
import bo.com.tesla.security.dto.OperacionesDto;

@Service
public class SegPrivilegiosService implements ISegPrivilegiosService {

	@Autowired
	private ISegPrivilegiosDao segPrivilegiosDao;

	@Override
	public List<SegPrivilegioEntity> getMenuByUserId(Long usuarioId) {

		List<SegPrivilegioEntity> sePrivilegioList = this.segPrivilegiosDao.getMenuByUserId(usuarioId);
		
		List<SegPrivilegioEntity> segPrivilegioSubMenuNew = new ArrayList<>();

		for (SegPrivilegioEntity segPrivilegio : sePrivilegioList) {

			

			for (SegPrivilegioEntity segPrivilegioSubMenu : segPrivilegio.getSegPrivilegioEntityList()) {
				String estado = this.segPrivilegiosDao.getEstadoPrivilegios(usuarioId,
						segPrivilegioSubMenu.getPrivilegiosId());
				if (estado.equals("ACTIVO")) {
					segPrivilegioSubMenuNew.add(segPrivilegioSubMenu);
				}
			}
			//segPrivilegio.setSegPrivilegioEntityList(segPrivilegioSubMenuNew);
			

		}

		return segPrivilegioSubMenuNew;

	}

	@Override
	public List<OperacionesDto> getOperaciones(String login, String tabla) {
		List<Object[]> operacionesLis= this.segPrivilegiosDao.getOperaciones(login, tabla);
		List<OperacionesDto> transicionEntities=new ArrayList<>();
		for (Object[] objects : operacionesLis) {
			OperacionesDto operacion=new OperacionesDto(objects[0]+"",objects[1]+"",objects[2]+"",objects[3]+"");
			transicionEntities.add(operacion);
		}
		
		return transicionEntities;
	}

}
