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
    public List<ServicioDeudaDto> getDeudasByCliente(Long entidadId, String codigoCliente) {
        try {
            List<ServicioDeudaDto> servicioDeudaDtos = iDeudaClienteRDao.groupByDeudasClientes(entidadId, codigoCliente);
            if (servicioDeudaDtos.isEmpty()) {
                return new ArrayList<ServicioDeudaDto>();//el controller procedera con la captura del mensaje
            }
            Long key = 0L;
            for (ServicioDeudaDto servicioDeuda : servicioDeudaDtos) {
                servicioDeuda.key = key;
                List<DeudaClienteDto> deudaClienteDtosDeudas = iDeudaClienteRDao.findByEntidadByServiciosDeudas(servicioDeuda.entidadId,
                        servicioDeuda.tipoServicio,
                        servicioDeuda.servicio,
                        servicioDeuda.periodo,
                        codigoCliente);

                if (deudaClienteDtosDeudas.isEmpty()) {
                    throw new Technicalexception("No existen DEUDAS para EntidadId=" + entidadId + " y CodigoCliente=" + codigoCliente);
                }
                servicioDeuda.deudaClienteDtos = deudaClienteDtosDeudas;
                servicioDeuda.nombreCliente = deudaClienteDtosDeudas.get(0).nombreCliente;
                servicioDeuda.codigoCliente = deudaClienteDtosDeudas.get(0).codigoCliente;
                servicioDeuda.nroDocumento = deudaClienteDtosDeudas.get(0).nroDocumento;
                //Para la edición verificar
                Boolean esEditable = deudaClienteDtosDeudas.stream().anyMatch(d -> !d.esPostpago && d.subTotal.compareTo(BigDecimal.ZERO) == 0);
                servicioDeuda.editable = esEditable;
                servicioDeuda.editando = false;
                servicioDeuda.plantilla = deudaClienteDtosDeudas.get(0).tipoComprobante ? "FACTURA" : "RECIBO";
                key++;
            }
            return servicioDeudaDtos;
        } catch (Exception e) {
            throw new Technicalexception(e.getMessage(), e.getCause());
        }
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

    @Override
    public Integer recoverDeudasByFacturas(List<Long> facturaIdLst) {
        return iDeudaClienteRDao.recoverDeudasByFacturas(facturaIdLst);
    }

    @Override
    public Integer recoverDeudasByFactura(Long facturaIdLst) {
        return iDeudaClienteRDao.recoverDeudasByFactura(facturaIdLst);
    }

    @Override
    public List<String> getCodigosActividadUnicos(List<DeudaClienteEntity> deudaClienteEntityList) {
        List<String> codActEcoList = deudaClienteEntityList.stream().map(d -> d.getCodigoActividadEconomica())
                .collect(Collectors.toList());
        return codActEcoList.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Boolean> getTipoComprobanteUnicos(List<DeudaClienteEntity> deudaClienteEntityList) {
        List<Boolean> tipoComprobantes = deudaClienteEntityList.stream().map(d -> d.getTipoComprobante())
                .collect(Collectors.toList());
        return tipoComprobantes.stream().distinct().collect(Collectors.toList());
    }
}
