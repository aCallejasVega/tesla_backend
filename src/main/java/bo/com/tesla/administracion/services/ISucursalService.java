package bo.com.tesla.administracion.services;

import bo.com.tesla.administracion.dto.RecaudadorAdmDto;
import bo.com.tesla.administracion.dto.SucursalAdmDto;
import bo.com.tesla.administracion.entity.DominioEntity;
import bo.com.tesla.administracion.entity.SucursalEntity;
import bo.com.tesla.useful.config.Technicalexception;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ISucursalService {

    public SucursalAdmDto addUpdateSucursal(SucursalAdmDto sucursalAdmDto, Long usuarioId) ;
    public void setTransaccion(Long sucursalId, String transaccion, Long usuarioId);
    public void setLstTransaccion(List<Long> sucursalIdLst, String transaccion, Long usuarioId);
    public SucursalAdmDto getSucursalById(Long sucursalId);
    public List<SucursalAdmDto> getAllSucursales();
    public List<SucursalAdmDto> getListSucursalesByRecaudadora(Long recaudadorId);
}
