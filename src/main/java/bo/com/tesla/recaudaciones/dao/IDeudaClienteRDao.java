package bo.com.tesla.recaudaciones.dao;

import bo.com.tesla.administracion.entity.DeudaClienteEntity;
import bo.com.tesla.recaudaciones.dto.ClienteDto;
import bo.com.tesla.recaudaciones.dto.DeudaClienteDto;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDeudaClienteRDao extends JpaRepository<DeudaClienteEntity, Long> {

    @Query(value = "select distinct new bo.com.tesla.recaudaciones.dto.ClienteDto(" +
            "d.codigoCliente, d.nombreCliente, d.nroDocumento)" +
            "from DeudaClienteEntity d " +
            "where d.archivoId.entidadId.entidadId = :pEntidadId " +
            "and (upper(d.codigoCliente) like upper(concat('%', :pDatoCliente, '%')) or upper(d.nroDocumento) like upper(concat('%', :pDatoCliente, '%')) or upper(d.nombreCliente) like upper(concat('%', :pDatoCliente, '%')) )")
    Optional<List<ClienteDto>> findByEntidadAndClienteLike(@Param("pEntidadId") Long pEntidadId, @Param("pDatoCliente") String pDatoCliente);


    @Query("select new bo.com.tesla.recaudaciones.dto.ServicioDeudaDto( "
            + " d.tipoServicio, d.servicio, d.periodo, d.archivoId.entidadId.entidadId, sum(d.subTotal)) "
            + " from DeudaClienteEntity d "
            + " where d.archivoId.entidadId.entidadId = :entidadId "
            + " and d.codigoCliente = :codigoCliente "
            + " group by d.tipoServicio, d.servicio, d.periodo, d.archivoId.entidadId.entidadId")
    Optional<List<ServicioDeudaDto>> groupByDeudasClientes(@Param("entidadId") Long entidadId , @Param("codigoCliente") String codigoCliente );


    @Query("select new  bo.com.tesla.recaudaciones.dto.DeudaClienteDto( "
            + " d.deudaClienteId, d.nroRegistro, d.cantidad, d.concepto, d.montoUnitario, d.subTotal, "
            + " d.tipo, d.datoExtras, d.tipoComprobante, d.periodoCabecera, d.codigoCliente, d.nroDocumento, d.nombreCliente) "
            + " from DeudaClienteEntity d "
            + " where d.archivoId.entidadId.entidadId = :entidadId "
            + " and d.tipoServicio= :tipoServicio "
            + " and d.servicio= :servicio "
            + " and d.periodo= :periodo"
            + " and d.codigoCliente= :codigoCliente ")
    Optional<List<DeudaClienteDto>> findByEntidadByServicios(
            @Param("entidadId") Long entidadId,
            @Param("tipoServicio") String tipoServicio,
            @Param("servicio") String servicio,
            @Param("periodo") String periodo,
            @Param("codigoCliente") String codigoCliente);


    @Query("select d "
            + " from DeudaClienteEntity d "
            + " where d.archivoId.entidadId.entidadId = :entidadId "
            + " and d.tipoServicio= :tipoServicio "
            + " and d.servicio= :servicio "
            + " and d.periodo= :periodo"
            + " and d.codigoCliente= :codigoCliente ")
    Optional<List<DeudaClienteEntity>> findAllGroupByServicio(
            @Param("entidadId") Long entidadId,
            @Param("tipoServicio") String tipoServicio,
            @Param("servicio") String servicio,
            @Param("periodo") String periodo,
            @Param("codigoCliente") String codigoCliente);


    //@Transactional
    Long deleteByDeudaClienteIdIn(List<Long> deudaClienteIdLst);
}
