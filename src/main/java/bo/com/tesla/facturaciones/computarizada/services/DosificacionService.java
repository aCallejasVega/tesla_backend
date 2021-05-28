package bo.com.tesla.facturaciones.computarizada.services;

import bo.com.tesla.administracion.entity.EntidadEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.facturaciones.computarizada.dto.DosificacionDto;
import bo.com.tesla.facturaciones.computarizada.dto.FacturaDto;
import bo.com.tesla.facturaciones.computarizada.dto.ResponseDto;
import bo.com.tesla.useful.config.Technicalexception;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class DosificacionService implements IDosificacionService {

    @Value("${host.facturacion.computarizada}")
    private String hostComputarizada;

    @Autowired
    private ConexionService conexionService;

    @Override
    public ResponseDto saveDosificacion(Long entidadId, DosificacionDto dosificacionDto) {
        try {
            String url = this.hostComputarizada + "/api/dosificaciones";
            return conexionService.getResponseMethodPost(entidadId, dosificacionDto, url);

        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(),e.getCause());
        }
    }

    @Override
    public ResponseDto getDosificacionesLst(Long entidadId) {
        try {
            String url = this.hostComputarizada + "/api/dosificaciones";


            ResponseDto responseDto = conexionService.getResponseMethodGet(entidadId,url);

            return responseDto;
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(),e.getCause());
        }
    }

    @Override
    public ResponseDto getDosificacionById(Long entidadId, Long dosificacionId) {
        try {
            String url = this.hostComputarizada + "/api/dosificaciones/" + dosificacionId;

            return conexionService.getResponseMethodGet(entidadId,url);
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(),e.getCause());
        }
    }

    @Override
    public ResponseDto putTransaccion(Long entidadId, Long dosificacionId, String transaccion) {
        try {
            String url = this.hostComputarizada + "/api/dosificaciones/" + dosificacionId+ "/transacciones/" + transaccion;
            return conexionService.getResponseMethodPut(entidadId, null, url);

        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(),e.getCause());
        }
    }

    @Override
    public ResponseDto getDosificacionesLstAlerta(Long entidadId) {
        try {
            String url = this.hostComputarizada + "/api/dosificaciones/alertas";
            ResponseDto responseDto = conexionService.getResponseMethodGet(entidadId,url);

            TypeReference<List<DosificacionDto>> mapType = new TypeReference<List<DosificacionDto>>() {};
            ObjectMapper mapper = new ObjectMapper();
            List<DosificacionDto> dosificacionDtoList = mapper.convertValue(responseDto.result, mapType);
            responseDto.result = dosificacionDtoList;

            return responseDto;
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(),e.getCause());
        }
    }


}
