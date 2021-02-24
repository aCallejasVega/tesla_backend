package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.DeudaClienteEntity;
import bo.com.tesla.recaudaciones.dao.IDeudaClienteRDao;
import bo.com.tesla.recaudaciones.dto.ClienteDto;
import bo.com.tesla.recaudaciones.dto.DeudaClienteDto;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeudaClienteRService implements IDeudaClienteRService {

    @Autowired
    private IDeudaClienteRDao iDeudaClienteRDao;

    @Transactional(readOnly = true)
    @Override
    public Optional<List<ClienteDto>> getByEntidadAndClienteLike(Long entidadId, String datoCliente) {
        return iDeudaClienteRDao.findByEntidadAndClienteLike(entidadId, datoCliente);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ServicioDeudaDto> getDeudasByCliente(Long entidadId, String codigoCliente) {
        Optional<List<ServicioDeudaDto>> optionalServicioDeudaDtos = iDeudaClienteRDao.groupByDeudasClientes(entidadId, codigoCliente);
        if(!optionalServicioDeudaDtos.isPresent()) {
            return null;
        }

        Long key = 0L;
        for (ServicioDeudaDto servicioDeuda : optionalServicioDeudaDtos.get()) {
            servicioDeuda.key = key;
            Optional<List<DeudaClienteDto>> optionalDeudaClienteDtos = iDeudaClienteRDao.findByEntidadByServicios(servicioDeuda.entidadId,
                                                                            servicioDeuda.tipoServicio,
                                                                            servicioDeuda.servicio,
                                                                            servicioDeuda.periodo,
                                                                            codigoCliente);
            if(!optionalDeudaClienteDtos.isPresent()){
                return null;
            }
            List<DeudaClienteDto> deudaClienteDtos =  optionalDeudaClienteDtos.get().stream()
                                                        .filter(d -> d.tipo == 'D').collect(Collectors.toList());
            if(!deudaClienteDtos.isEmpty()) {
            	 servicioDeuda.deudaClienteDtos = deudaClienteDtos;
                 servicioDeuda.nombreCliente = deudaClienteDtos.get(0).nombreCliente;
                 servicioDeuda.codigoCliente = deudaClienteDtos.get(0).codigoCliente;
                // servicioDeuda.nombreCliente = deudaClienteDtos.get(0).nombreCliente;
            }

           
            key++;
        }

        return optionalServicioDeudaDtos.get();
    }

    @Override
    public Optional<List<DeudaClienteEntity>> getAllDeudasByCliente(Long entidadId,
                                                          String tipoServicio,
                                                          String servicio,
                                                          String periodo,
                                                          String codigoCliente) {
        return iDeudaClienteRDao.findAllGroupByServicio(entidadId,
                                                        tipoServicio,
                                                        servicio,
                                                        periodo,
                                                        codigoCliente);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ServicioDeudaDto> getDeudasCompletas(List<ServicioDeudaDto> servicioDeudaDtos) {
        for(ServicioDeudaDto servicioDeudaDto : servicioDeudaDtos) {
            Optional<List<DeudaClienteEntity>> optionalDeudaClienteEntities = getAllDeudasByCliente(servicioDeudaDto.entidadId,
                    servicioDeudaDto.tipoServicio,
                    servicioDeudaDto.servicio,
                    servicioDeudaDto.periodo,
                    servicioDeudaDto.codigoCliente);
            if(!optionalDeudaClienteEntities.isPresent()) {
                return null;
            }
            servicioDeudaDto.setDeudaClientes(optionalDeudaClienteEntities.get());
        }
        return servicioDeudaDtos;
    }


    @Override
    public Long deleteDeudasClientes(List<DeudaClienteEntity> deudaClienteEntities) {
        List<Long> deudaClienteIdLst = deudaClienteEntities.stream()
                                            .mapToLong(d -> d.getDeudaClienteId()).boxed()
                                            .collect(Collectors.toList());

        return iDeudaClienteRDao.deleteByDeudaClienteIdIn(deudaClienteIdLst);
    }

}
