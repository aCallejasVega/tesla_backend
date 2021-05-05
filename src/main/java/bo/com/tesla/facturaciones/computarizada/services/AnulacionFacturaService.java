package bo.com.tesla.facturaciones.computarizada.services;

import bo.com.tesla.facturaciones.computarizada.dto.AnulacionFacturaLstDto;
import bo.com.tesla.facturaciones.computarizada.dto.ResponseDto;
import bo.com.tesla.useful.config.Technicalexception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AnulacionFacturaService implements IAnulacionFacturaService {

    @Value("${host.facturacion.computarizada}")
    private String host;

    @Autowired
    private IConexionService conexionService;

    @Override
    public ResponseDto postAnulacionLst(Long entidadId, AnulacionFacturaLstDto anulacionFacturaLstDto) {
        try {

            String url = this.host + "/api/anulaciones/listas";
            return conexionService.getResponseMethodPost(entidadId, anulacionFacturaLstDto, url);

        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(),e.getCause());
        }
    }

}
