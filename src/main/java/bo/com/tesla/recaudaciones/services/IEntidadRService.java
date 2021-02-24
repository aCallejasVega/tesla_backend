package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.RecaudadorEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.recaudaciones.dto.DominioDto;
import bo.com.tesla.recaudaciones.dto.EntidadDto;

import java.util.List;
import java.util.Optional;

public interface IEntidadRService {
/*
    public Optional<List<EntidadDto>> getByRecaudadoraIdAndTipoEntidadId(Long recaudadoraId, Long tipoEntidadId);
    public Optional<List<EntidadDto>> getAllByRecaudadoraId(Long recaudadoraId);
    public Optional<List<DominioDto>> getTipoEntidadByRecaudador(Long pTipoRecaudadorId);
*/
    public Optional<List<EntidadDto>> getByRecaudadoraIdAndTipoEntidadId(Long tipoEntidadId, String login);
    public Optional<List<EntidadDto>> getByRecaudadoraId(String login);
    public Optional<List<DominioDto>> getTipoEntidadByRecaudador(String login);
}