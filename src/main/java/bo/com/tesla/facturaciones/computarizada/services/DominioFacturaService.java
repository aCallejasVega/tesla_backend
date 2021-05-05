package bo.com.tesla.facturaciones.computarizada.services;

import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.facturaciones.computarizada.dto.ResponseDto;
import bo.com.tesla.useful.config.Technicalexception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DominioFacturaService implements IDominioFacturaService{

    @Value("${host.facturacion.computarizada}")
    private String hostComputarizada;

    @Autowired
    private ConexionService conexionService;

    @Override
    public ResponseDto getDominiosLst(Long entidadId, String dominio) {
        try {
            String url = this.hostComputarizada + "/api/dominios/" + dominio;

            return conexionService.getResponseMethodGet(entidadId, url);
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(),e.getCause());
        }
    }

}
