package bo.com.tesla.security.dao;

import bo.com.tesla.administracion.entity.SegTransicionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface ISegTransicionDao extends JpaRepository<SegTransicionEntity, Long> {

    @Query(value = "SELECT count(s) " +
            "FROM SegTransicionEntity s " +
            "WHERE s.segTransaccionEntity.estado = 'ACTIVO' " +
            //"AND s.segTransaccionEntity.segTablaEntity.tablaId = :tabla " +
            //"AND s.segTransaccionEntity.segTablaEntity.estado = 'ACTIVO' " +
            "AND s.segTransicionEntityPK.transaccionId = :transaccion " +
            "AND s.segTransicionEntityPK.tablaId = :tabla " +
            "AND s.segTransicionEntityPK.estadoInicial = :estadoInicial " +
            "AND s.estado = 'ACTIVO'")
    Long countByTablaAndTransaccion(@Param("tabla") String tabla, @Param("transaccion") String transaccion, @Param("estadoInicial") String estadoInicial);

}
