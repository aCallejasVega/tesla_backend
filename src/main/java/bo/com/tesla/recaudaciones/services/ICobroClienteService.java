package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.CobroClienteEntity;
import bo.com.tesla.administracion.entity.DeudaClienteEntity;
import bo.com.tesla.administracion.entity.TransaccionCobroEntity;
import bo.com.tesla.recaudaciones.dto.ClienteDto;
import bo.com.tesla.recaudaciones.dto.DeudaClienteDto;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;
import bo.com.tesla.useful.config.BusinesException;

import java.util.List;

public interface ICobroClienteService {

    public List<CobroClienteEntity> saveAllCobrosClientes(List<CobroClienteEntity> cobroClienteEntities);
    public CobroClienteEntity loadCobroClienteEntity(DeudaClienteEntity deudaClienteEntity,
                                                     List<DeudaClienteDto> deudaClienteDtos,
                                                     Long usuarioId,
                                                     Long metodoPagoId,
                                                     TransaccionCobroEntity transaccionCobroEntity);

    public  List<TransaccionCobroEntity> postCobrarDeudas(ClienteDto clienteDto,
                                 Long usuarioId,
                                 Long metodoPagoId); //throws Exception;




}
