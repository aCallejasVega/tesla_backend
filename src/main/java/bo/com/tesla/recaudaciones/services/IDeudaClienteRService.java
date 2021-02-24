package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.DeudaClienteEntity;
import bo.com.tesla.recaudaciones.dto.ClienteDto;
import bo.com.tesla.recaudaciones.dto.DeudaClienteDto;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;

import java.util.List;
import java.util.Optional;

public interface IDeudaClienteRService {

    public Optional<List<ClienteDto>> getByEntidadAndClienteLike(Long entidadId, String datoCliente);
    public List<ServicioDeudaDto> getDeudasByCliente(Long entidadId, String codigoCliente);
    public Optional<List<DeudaClienteEntity>> getAllDeudasByCliente(Long entidadId,
                                                          String tipoServicio,
                                                          String servicio,
                                                          String periodo,
                                                          String codigoCliente);
    public List<ServicioDeudaDto> getDeudasCompletas(List<ServicioDeudaDto> servicioDeudaDtos) throws Exception;
    public Long deleteDeudasClientes(List<DeudaClienteEntity> deudaClienteEntities);
}
