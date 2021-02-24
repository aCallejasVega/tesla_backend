package bo.com.tesla.security.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bo.com.tesla.administracion.entity.SegPrivilegioEntity;
import bo.com.tesla.security.dao.ISegPrivilegiosDao;

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
				String estado = segPrivilegiosDao.getEstadoPrivilegios(usuarioId,
						segPrivilegioSubMenu.getPrivilegiosId());
				if (estado.equals("ACTIVO")) {
					segPrivilegioSubMenuNew.add(segPrivilegioSubMenu);
				}
			}
			//segPrivilegio.setSegPrivilegioEntityList(segPrivilegioSubMenuNew);
			

		}

		return segPrivilegioSubMenuNew;

	}

}
