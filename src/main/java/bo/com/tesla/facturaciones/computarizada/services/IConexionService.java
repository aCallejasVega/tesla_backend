package bo.com.tesla.facturaciones.computarizada.services;

import bo.com.tesla.facturaciones.computarizada.dto.ResponseDto;
import org.codehaus.jettison.json.JSONException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;

public interface IConexionService {
    HashMap<String, String> getCredencialesByEntidadId(Long entidadId);
    HttpHeaders getHeaderToken(Long entidadId);
    <T> ResponseDto getResponseMethodPost(Long entidadId, T body, String url) throws Exception;
    <T> ResponseDto getResponseMethodPostParameter(Long entidadId, T body, UriComponentsBuilder uriComponentsBuilder);
    ResponseDto getResponseMethodGet(Long entidadId, String url) throws Exception;
    ResponseDto getResponseMethodGetParams(Long entidadId, UriComponentsBuilder uriComponentsBuilder) throws Exception;
    <T> ResponseDto getResponseMethodPut(Long entidadId, T body, String url) throws Exception;
    <T> ResponseDto getResponseMethodPutParameter(Long entidadId, T body, UriComponentsBuilder uriComponentsBuilder) throws Exception;

}
