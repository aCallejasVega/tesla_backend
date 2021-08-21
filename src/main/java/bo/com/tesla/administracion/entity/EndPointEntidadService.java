package bo.com.tesla.administracion.entity;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import bo.com.tesla.recaudaciones.dao.EndPointEntidadDao;
import bo.com.tesla.recaudaciones.services.IEndPointEntidadService;

@Service
public class EndPointEntidadService implements IEndPointEntidadService {
	
	
	@Autowired
	private EndPointEntidadDao endPointEntidadDao;
	
	@Autowired
	private RestTemplate clienteRest;

	@Override
	public Boolean endPointLaRazon(Long entidadId,Map<String, String> map) {	
		String input = "US";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("id_aviso", map.get("id_aviso"));
		headers.add("fecha", "2021-06-26 18:26:50");
		
		HttpEntity<String> request = new HttpEntity<String>(input, headers);
		
		EndPointEntidadEntity endPoint=  this.endPointEntidadDao.findByEntidadId(entidadId);
		
		ResponseEntity<Void> response=clienteRest.postForEntity(endPoint.getRuta(), request,Void.class);
		
		if(response.getStatusCode()==HttpStatus.OK) {
			System.out.println("Entro por ok");
			return true;
		}else {
			System.out.println("Entro por else");
			return false;
		}
		
	}
	


}
