package bo.com.tesla.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bo.com.tesla.administracion.entity.SegPrivilegioEntity;
import bo.com.tesla.security.dao.ISegPrivilegiosDao;

@Service
public class SegPrivilegiosService  implements ISegPrivilegiosService{
	
	@Autowired
	private ISegPrivilegiosDao SegPrivilegiosDao;

	@Override
	public List<SegPrivilegioEntity> getMenuByUserId(Long usuarioId) {
		
		
		return this.SegPrivilegiosDao.getMenuByUserId(usuarioId);
		
	}
	
	

}
