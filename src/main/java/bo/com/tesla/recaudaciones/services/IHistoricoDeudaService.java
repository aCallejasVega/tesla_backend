package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.HistoricoDeudaEntity;

public interface IHistoricoDeudaService {

    public HistoricoDeudaEntity updateEstado(Long deudaClienteId, String estado);
}
