package bo.com.tesla.administracion.services;

import bo.com.tesla.administracion.dto.EntidadAdmDto;

import java.util.List;

public interface IEntidadAdmService {

    public EntidadAdmDto addUpdateEntidad(EntidadAdmDto entidadAdmDto, Long usuarioId);
    public void setTransaccion(Long entidadId, String transaccion, Long usuarioId);
    public void setLstTransaccion(List<Long> entidadIdLst, String transaccion, Long usuarioId);
    public EntidadAdmDto getEntidadById(Long entidadId);
    public List<EntidadAdmDto> getAllEntidades();
}
