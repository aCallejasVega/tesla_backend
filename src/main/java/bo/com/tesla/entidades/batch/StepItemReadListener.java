package bo.com.tesla.entidades.batch;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.beans.factory.annotation.Autowired;

import bo.com.tesla.administracion.entity.DeudaClienteEntity;
import bo.com.tesla.entidades.services.IDeudaClienteService;

public class StepItemReadListener implements  ItemReadListener<DeudaClienteEntity>{
	
	private Long  archivoId;	
	
	@Autowired
	private IDeudaClienteService deudaClienteService;

	public StepItemReadListener(Long archivoId) {	
		this.archivoId = archivoId;
	}

	@Override
	public void beforeRead() {				
	}

	@Override
	public void afterRead(DeudaClienteEntity item) {		
		
	}

	@Override
	public void onReadError(Exception ex) {
		//Todo delet en caso de encontrar un error.
		System.out.println("*****************onReadError********************");
		System.out.println(ex.getMessage());
		
		System.out.println("eliminando registros con id ="+this.archivoId);
		this.deudaClienteService.deletByArchivoId(this.archivoId);
		System.out.println("Termion de eliminar");
		
	}

	

}
