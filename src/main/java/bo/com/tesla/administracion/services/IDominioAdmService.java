package bo.com.tesla.administracion.services;

import bo.com.tesla.recaudaciones.dto.DominioDto;

import java.util.List;

public interface IDominioAdmService {
    public List<DominioDto> getListDominios(String dominio);
}
