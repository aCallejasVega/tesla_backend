package bo.com.tesla.security.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import bo.com.tesla.administracion.dao.ISegRolDao;
//import bo.com.tesla.administracion.dao.ISegUsuarioDao;
import bo.com.tesla.administracion.entity.SegRolEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.security.dao.ISegRolDao;
import bo.com.tesla.security.dao.ISegUsuarioDao;

@Service
public class SegUsuarioService implements ISegUsuarioService,UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(SegUsuarioService.class);
	@Autowired
	private ISegUsuarioDao segUsuarioDao;
	@Autowired
	private ISegRolDao segRolDao;
	
	
	@Override
	public SegUsuarioEntity save(SegUsuarioEntity entity) {		
		return this.segUsuarioDao.save(entity);
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		List<GrantedAuthority> authorities = new ArrayList<>();
		SegUsuarioEntity usuarios = segUsuarioDao.findByLogin(login);
		if (usuarios == null) {	
			logger.error("Error en el login: no existe el usuario " + login + " en el sistema");
			throw new UsernameNotFoundException("Error en el Login: no existe el usuario");
		}
		List<SegRolEntity> segRoles=segRolDao.findRolesByUsuarioLogin(login);
		
		if(!segRoles.isEmpty()) {			
			authorities=segRoles.stream().map(role -> new SimpleGrantedAuthority(role.getRol()))
					.peek(authority -> logger.info("Role :" + authority.getAuthority())).collect(Collectors.toList());
		}
		
		return new User(usuarios.getLogin(), usuarios.getPassword(), usuarios.getEstado().equals("ACTIVO") ? true : false,
				true, true, true, authorities);
		
	}

	@Override
	@Transactional(readOnly = true)
	public SegUsuarioEntity findByLogin(String login) {		
		return this.segUsuarioDao.findByLogin(login);
	}

	

}
