package bo.com.tesla.facturaciones.computarizada.services;

import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.facturaciones.computarizada.dto.AnulacionFacturaLstDto;
import bo.com.tesla.facturaciones.computarizada.dto.ResponseDto;
import bo.com.tesla.recaudaciones.dao.IDominioDao;
import bo.com.tesla.recaudaciones.services.ITransaccionCobroService;
import bo.com.tesla.useful.config.BusinesException;
import bo.com.tesla.useful.config.Technicalexception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnulacionFacturaService implements IAnulacionFacturaService {

    @Value("${host.facturacion.computarizada}")
    private String host;

    @Autowired
    private IConexionService conexionService;

    @Autowired
    private IDominioDao dominioDao;

    @Autowired
    private ITransaccionCobroService transaccionCobroService;

    @Override
    public ResponseDto postAnulacionLst(Long entidadId, AnulacionFacturaLstDto anulacionFacturaLstDto) {
        try {

            String url = this.host + "/api/anulaciones/listas";
            return conexionService.getResponseMethodPost(entidadId, anulacionFacturaLstDto, url);

        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(),e.getCause());
        }
    }

    @Override
    public Boolean anularTransaccionConRecuperacionDeudas(Long entidadId,
                                                          AnulacionFacturaLstDto anulacionFacturaLstDto,
                                                          SegUsuarioEntity usuarioEntity) throws BusinesException {
        Optional<Long> modFactCompuOptional = dominioDao.getDominioIdByDominioAndAbreviatura("modalidad_facturacion_id", "FC");
        if(!modFactCompuOptional.isPresent()) {
            throw new Technicalexception("No existe el dominio='modalidad_facturacion_id, abreviatura='FC' para la facturación computarizada");
        }
        return transaccionCobroService.anularTransaccionConRecuperacionDeudas(entidadId,
                anulacionFacturaLstDto,
                modFactCompuOptional.get(),
                usuarioEntity);
    }

    @Override
    public Boolean anularTransaccionConCargadoErroneo(Long entidadId,
                                                          AnulacionFacturaLstDto anulacionFacturaLstDto,
                                                          SegUsuarioEntity usuarioEntity) throws BusinesException {
        Optional<Long> modFactCompuOptional = dominioDao.getDominioIdByDominioAndAbreviatura("modalidad_facturacion_id", "FC");
        if(!modFactCompuOptional.isPresent()) {
            throw new Technicalexception("No existe el dominio='modalidad_facturacion_id, abreviatura='FC' para la facturación computarizada");
        }
        return transaccionCobroService.anularTransaccionPorCargadoErroneo(entidadId,
                anulacionFacturaLstDto,
                modFactCompuOptional.get(),
                usuarioEntity);
    }



}
