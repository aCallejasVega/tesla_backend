package bo.com.tesla.pagos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bo.com.tesla.administracion.entity.PTransaccionPagoEntity;
@Repository
public interface IPTransaccionPagoDao  extends JpaRepository<PTransaccionPagoEntity, Long>  {
	
	
	
	@Query(value =" select nextval('tesla.secuencia_transaccion_seq') ",nativeQuery = true)
	public Long getSecuencialTransaccion();
	

}
