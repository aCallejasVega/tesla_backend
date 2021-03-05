package bo.com.tesla.security.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bo.com.tesla.entidades.dao.ITransaccionCobrosDao;

@Service
public class TransaccionCobrosService implements ITransaccionCobrosService {

	@Autowired
	private ITransaccionCobrosDao transaccionCobrosDao;

	@Override
	public List<Object[]> getDeudasforDate(Long entidadId,String recaudadorId, String estado, Date fechaInicio, Date fechaFin) {
		String[] cabeceraString= {"Dia","Cantidad"};
		Object[] cabecera =cabeceraString;		
		
		List<Object[]> listChart=this.transaccionCobrosDao.getDeudasforDate(entidadId,recaudadorId, estado, fechaInicio, fechaFin);		
		listChart.add(0, cabecera);		
		return listChart;
	}

	@Override
	public List<Object[]> getDeudasforServicio(Long entidadId, String recaudadorId, String estado, Date fechaInicio,
			Date fechaFin) {
		String[] cabeceraString= {"Servicio","Cantidad"};
		Object[] cabecera =cabeceraString;
		List<Object[]> listChart=this.transaccionCobrosDao.getDeudasforServicio(entidadId, recaudadorId, estado, fechaInicio, fechaFin);
		listChart.add(0, cabecera);		
		return listChart;
	}

	@Override
	public List<Object[]> getDeudasforTipoServicio(Long entidadId, String recaudadorId, String estado,
			Date fechaInicio, Date fechaFin) {
		String[] cabeceraString= {"Tipo Servicio","Cantidad"};
		Object[] cabecera =cabeceraString;
		List<Object[]> listColumnChart=this.transaccionCobrosDao.getDeudasforTipoServicio(entidadId, recaudadorId, estado, fechaInicio, fechaFin);
		listColumnChart.add(0, cabecera);		
		return listColumnChart;
	}

}
