package bo.com.tesla.pagos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import bo.com.tesla.administracion.entity.PTransaccionPagoEntity;

public interface IPTransaccionPagoDao  extends JpaRepository<PTransaccionPagoEntity, Long>  {

}
