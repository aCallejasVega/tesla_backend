package bo.com.tesla.entidades.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bo.com.tesla.entidades.dto.BusquedaReportesDto;
import bo.com.tesla.security.services.ITransaccionCobrosService;

@RestController
@RequestMapping("api/ReportEntidad")
public class ReportEntidadController {
	private Logger logger = LoggerFactory.getLogger(ReportEntidadController.class);

	@Autowired
	private ITransaccionCobrosService transaccionCobrosService;

	@PostMapping(path = "/linealChart")
	public ResponseEntity<?> linealChart(@RequestBody BusquedaReportesDto busquedaReportesDto,
			 Authentication authentication) throws Exception {

		Map<String, Object> response = new HashMap<>();
		try {

			List<Object[]> linealChart = this.transaccionCobrosService.getDeudasforDate(busquedaReportesDto.entidadId,busquedaReportesDto.recaudadorId, busquedaReportesDto.estado,
					busquedaReportesDto.fechaInicio, busquedaReportesDto.fechaFin);
			if (linealChart.isEmpty()) {
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
			}
			response.put("data", linealChart);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

	}
	
	@PostMapping(path = "/pieChart")
	public ResponseEntity<?> pieChart(@RequestBody BusquedaReportesDto busquedaReportesDto,
			 Authentication authentication) throws Exception {

		Map<String, Object> response = new HashMap<>();
		try {

			List<Object[]> linealChart = this.transaccionCobrosService.getDeudasforServicio(busquedaReportesDto.entidadId,busquedaReportesDto.recaudadorId, busquedaReportesDto.estado,
					busquedaReportesDto.fechaInicio, busquedaReportesDto.fechaFin);
			if (linealChart.isEmpty()) {
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
			}
			response.put("data", linealChart);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

	}
	@PostMapping(path = "/columnChart")
	public ResponseEntity<?> columnChart(@RequestBody BusquedaReportesDto busquedaReportesDto,
			 Authentication authentication) throws Exception {

		Map<String, Object> response = new HashMap<>();
		try {

			List<Object[]> linealChart = this.transaccionCobrosService.getDeudasforTipoServicio(busquedaReportesDto.entidadId,busquedaReportesDto.recaudadorId, busquedaReportesDto.estado,
					busquedaReportesDto.fechaInicio, busquedaReportesDto.fechaFin);
			if (linealChart.isEmpty()) {
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
			}
			response.put("data", linealChart);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

	}

}
