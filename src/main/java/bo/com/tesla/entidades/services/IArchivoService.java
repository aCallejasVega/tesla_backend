package bo.com.tesla.entidades.services;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import bo.com.tesla.administracion.entity.ArchivoEntity;

public interface IArchivoService {

	public ArchivoEntity save(ArchivoEntity entity);

	public ArchivoEntity findById(Long archivoId);

	public Map<String, Object>  upload(MultipartFile file, String login);

	public Map<String, Object> process(Long archivoId, String login);

}
