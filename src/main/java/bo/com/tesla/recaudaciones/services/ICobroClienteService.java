package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.CobroClienteEntity;
import bo.com.tesla.administracion.entity.DeudaClienteEntity;
import bo.com.tesla.recaudaciones.dto.ClienteDto;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;

import java.util.List;

public interface ICobroClienteService {

    public List<CobroClienteEntity> saveAllCobrosClientes(List<CobroClienteEntity> cobroClienteEntities);
    public CobroClienteEntity loadCobroClienteEntity(DeudaClienteEntity deudaClienteEntity,
                                                     Long usuarioId,
                                                     Long metodoPagoId);
    public void postCobrarDeudas(ClienteDto clienteDto,
                                 Boolean comprobanteEnUno,
                                 String login,
                                 Long metodoPagoId) throws Exception;

}
