package bo.com.tesla.pagos.batch;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.beans.factory.annotation.Autowired;

import bo.com.tesla.administracion.entity.DeudaClienteEntity;
import bo.com.tesla.entidades.services.IDeudaClienteService;
import bo.com.tesla.pagos.dto.PBeneficiarioDto;
import bo.com.tesla.useful.config.BusinesException;


public class StepPagosItemReadListener implements  ItemReadListener<PBeneficiarioDto>{
	
	
	
	public StepPagosItemReadListener() {
		
	}
	
	

	@Override
	public void beforeRead() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterRead(PBeneficiarioDto item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReadError(Exception ex) {
		// TODO Auto-generated method stub
		
	}
	
		
	
	
	

}
