package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.RecaudadorEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.recaudaciones.dto.DominioDto;
import bo.com.tesla.recaudaciones.dto.EntidadDto;

import java.util.List;
import java.util.Optional;

public interface IEntidadRService {
    public List<EntidadDto> getEntidadesByTipoEntidad(Long tipoEntidadId, SegUsuarioEntity usuario);
    public List<EntidadDto> getByRecaudadoraId(SegUsuarioEntity usuario);
    public List<DominioDto> getTipoEntidadByRecaudador(SegUsuarioEntity usuario);
}