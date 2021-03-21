package bo.com.tesla.recaudaciones.services;

import bo.com.tesla.administracion.entity.DeudaClienteEntity;
import bo.com.tesla.recaudaciones.dao.IDeudaClienteRDao;
import bo.com.tesla.recaudaciones.dto.ClienteDto;
import bo.com.tesla.recaudaciones.dto.DeudaClienteDto;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;
import bo.com.tesla.useful.config.Technicalexception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeudaClienteRService implements IDeudaClienteRService {

    @Autowired
    private IDeudaClienteRDao iDeudaClienteRDao;

    @Transactional(readOnly = true)
    @Override
    public List<ClienteDto> getByEntidadAndClienteLike(Long entidadId, String datoCliente) {
        return iDeudaClienteRDao.findByEntidadAndClienteLike(entidadId, datoCliente);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ServicioDeudaDto> getDeudasByCliente(Long entidadId, String codigoCliente) throws Technicalexception{
        List<ServicioDeudaDto> servicioDeudaDtos = iDeudaClienteRDao.groupByDeudasClientes(entidadId, codigoCliente);
        if(servicioDeudaDtos.isEmpty()) {
            return new ArrayList<ServicioDeudaDto>();//el controller procedera con l captura del mensaje
        }
        Long key = 0L;
        for (ServicioDeudaDto servicioDeuda : servicioDeudaDtos) {
            servicioDeuda.key = key;
            List<DeudaClienteDto> deudaClienteDtosDeudas = iDeudaClienteRDao.findByEntidadByServiciosDeudas(servicioDeuda.entidadId,
                                                                            servicioDeuda.tipoServicio,
                                                                            servicioDeuda.servicio,
                                                                            servicioDeuda.periodo,
                                                                            codigoCliente);
            /*if(deudaClienteDtos.isEmpty()){
                throw new Technicalexception("No existen registros en deudas para agrupación");
            }
            List<DeudaClienteDto> deudaClienteDtosDeudas =  deudaClienteDtos.stream()
                                                                .filter(d -> d.tipo == 'D')
                                                                .collect(Collectors
                                                                .toList());*/
            if(deudaClienteDtosDeudas.isEmpty()) {
                throw new Technicalexception("No existen DEUDAS para EntidadId=" + entidadId + " y CodigoCliente=" + codigoCliente);
            }
            servicioDeuda.deudaClienteDtos = deudaClienteDtosDeudas;
            servicioDeuda.nombreCliente = deudaClienteDtosDeudas.get(0).nombreCliente;
            servicioDeuda.codigoCliente = deudaClienteDtosDeudas.get(0).codigoCliente;
            servicioDeuda.nroDocumento = deudaClienteDtosDeudas.get(0).nroDocumento;
            //servicioDeuda.archivoId = deudaClienteDtosDeudas.get(0).archivoId;
            //Para la edición verificar
            Boolean esEditable = deudaClienteDtosDeudas.stream().anyMatch(d -> !d.esPostpago && d.subTotal.compareTo(BigDecimal.ZERO) == 0);
            servicioDeuda.editable = esEditable;
            servicioDeuda.editando = false;
            servicioDeuda.plantilla = deudaClienteDtosDeudas.get(0).tipoComprobante ? "FACTURA" : "RECIBO";
            key++;
        }
        return servicioDeudaDtos;
    }

    @Transactional(readOnly = true)
    @Override
    public List<DeudaClienteEntity> getAllDeudasByCliente(Long entidadId,
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

    @Override
    public Long deleteDeudasClientes(List<DeudaClienteEntity> deudaClienteEntities) {
        List<Long> deudaClienteIdLst = deudaClienteEntities.stream()
                                            .mapToLong(d -> d.getDeudaClienteId()).boxed()
                                            .collect(Collectors.toList());

        return iDeudaClienteRDao.deleteByDeudaClienteIdIn(deudaClienteIdLst);
    }

}
