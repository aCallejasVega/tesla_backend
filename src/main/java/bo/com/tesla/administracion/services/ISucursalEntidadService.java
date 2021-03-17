package bo.com.tesla.administracion.services;

import bo.com.tesla.administracion.dto.SucursalEntidadAdmDto;
import bo.com.tesla.useful.config.Technicalexception;

import java.util.List;

public interface ISucursalEntidadService {
    public SucursalEntidadAdmDto addUpdateSucursalEntidad(SucursalEntidadAdmDto sucursalEntidadAdmDto, Long usuarioId);
    public void setTransaccionSucursalEntidad(Long sucursalEntidadId, String transaccion, Long usuarioId);
    public void setLstTransaccion(List<Long> sucursalEntidadIdLst, String transaccion, Long usuarioId);
    public SucursalEntidadAdmDto getSucursalEntidadById(Long sucursalEntidadId);
    public List<SucursalEntidadAdmDto> getAllSucursalEntidades();
    public List<SucursalEntidadAdmDto> getLisSucursalEntidadesByEntidadId(Long entidadId);
}

