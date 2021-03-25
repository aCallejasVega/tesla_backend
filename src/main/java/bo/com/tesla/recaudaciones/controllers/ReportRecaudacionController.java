package bo.com.tesla.recaudaciones.controllers;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bo.com.tesla.administracion.entity.RecaudadorEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.entidades.dto.DeudasClienteDto;
import bo.com.tesla.entidades.services.IEntidadService;
import bo.com.tesla.recaudaciones.dto.BusquedaReportesRecaudacionDto;
import bo.com.tesla.recaudaciones.dto.DeudasClienteRecaudacionDto;
import bo.com.tesla.recaudaciones.services.IRecaudadoraService;
import bo.com.tesla.recaudaciones.services.IReporteRecaudacionService;
import bo.com.tesla.security.services.ILogSistemaService;
import bo.com.tesla.security.services.ISegUsuarioService;
import bo.com.tesla.useful.cross.Util;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@RequestMapping("api/ReportRecaudacion")
public class ReportRecaudacionController {
	private Logger logger = LoggerFactory.getLogger(ReportRecaudacionController.class);

	

	@Autowired
	private IReporteRecaudacionService reporteRecaudacionService;

	@Autowired
	private IRecaudadoraService recaudadoraService;

	@Autowired
	private ILogSistemaService logSistemaService;

	@Autowired
	private ISegUsuarioService segUsuarioService;

	@Autowired
	private IEntidadService entidadService;

	

	

	@Value("${tesla.path.files-report}")
	private String filesReport;



	@PostMapping(path = "/findDeudasByParameter")
	public ResponseEntity<?> findDeudasByParameter(@RequestBody BusquedaReportesRecaudacionDto busquedaReportesDto,
			Authentication authentication) throws Exception {
		
		Map<String, Object> response = new HashMap<>();
		try {

			if (busquedaReportesDto.fechaInicio == null ) {
				busquedaReportesDto.fechaInicio = Util.stringToDate("01/01/2021");
			}
			if (busquedaReportesDto.fechaFin == null) {
				busquedaReportesDto.fechaFin = Util.stringToDate("01/01/2100");
			}
		

			SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());	
			RecaudadorEntity recaudador=this.recaudadoraService.findRecaudadorByUserId(usuario.getUsuarioId());
			
			Page<DeudasClienteRecaudacionDto> deudasClienteDtoList = this.reporteRecaudacionService.findDeudasByParameter(
					busquedaReportesDto.fechaInicio, busquedaReportesDto.fechaFin, busquedaReportesDto.entidadArray,
					recaudador.getRecaudadorId(), busquedaReportesDto.estadoArray, busquedaReportesDto.paginacion - 1,
					10);
			
			if (deudasClienteDtoList.isEmpty()) {
				System.out.println("No tiene contenido");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
			}
			response.put("data", deudasClienteDtoList);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping(path = "/findDeudasByParameterForReport")
	public ResponseEntity<?> findDeudasByParameterForReport(@RequestBody BusquedaReportesRecaudacionDto busquedaReportesDto,
			Authentication authentication) throws Exception {
		Page<DeudasClienteDto> deudasClienteList;
		Map<String, Object> parameters = new HashMap<>();
		try {
			SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
			RecaudadorEntity recaudador=this.recaudadoraService.findRecaudadorByUserId(usuario.getUsuarioId());

			if (busquedaReportesDto.fechaInicio == null && busquedaReportesDto.fechaFin == null) {
				parameters.put("tituloRangoFechas", "");
			} else if (busquedaReportesDto.fechaInicio == null && busquedaReportesDto.fechaFin != null) {
				parameters.put("tituloRangoFechas",
						"REPORTE GENERADO HASTA LA FECHA : " + Util.dateToStringFormat(busquedaReportesDto.fechaFin));
			} else if (busquedaReportesDto.fechaInicio != null && busquedaReportesDto.fechaFin == null) {
				parameters.put("tituloRangoFechas", "REPORTE GENERADO A PARTIR DE LA FECHA : "
						+ Util.dateToStringFormat(busquedaReportesDto.fechaInicio));
			} else {
				parameters.put("tituloRangoFechas",
						"REPORTE GENERADO EN EL RANGO DE FECHAS : "
								+ Util.dateToStringFormat(busquedaReportesDto.fechaInicio) + "-"
								+ Util.dateToStringFormat(busquedaReportesDto.fechaFin));
			}

			if (busquedaReportesDto.fechaInicio == null) {
				busquedaReportesDto.fechaInicio = Util.stringToDate("01/01/2021");
			}
			if (busquedaReportesDto.fechaFin == null) {
				busquedaReportesDto.fechaFin = Util.stringToDate("01/01/2100");
			}

						
			parameters.put("tituloReporte", "INFORMACIÓN DE DEUDAS GENERALES" );
			parameters.put("tituloEntidadRecaudadora", recaudador.getNombre().toUpperCase() );
			parameters.put("logoTesla",filesReport+"/img/teslapng.png" );
						
			
			List<DeudasClienteRecaudacionDto> deudasClienteDtoList = this.reporteRecaudacionService.findDeudasByParameterForReport(
					busquedaReportesDto.fechaInicio, busquedaReportesDto.fechaFin, busquedaReportesDto.entidadArray,
					recaudador.getRecaudadorId(), busquedaReportesDto.estadoArray);

			
			if (deudasClienteDtoList.isEmpty()) {
				System.out.println("no tiene registro");
				return new ResponseEntity<Map<String, Object>>(HttpStatus.NO_CONTENT);
			}

			File file = ResourceUtils.getFile(filesReport+"/report_jrxml/reportes/recaudador/ListaDeudasRecaudacion.jrxml");
			JasperReport jasper = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(deudasClienteDtoList);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parameters, ds);

			byte[] report = Util.jasperExportFormat(jasperPrint, busquedaReportesDto.export, filesReport);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentLength(report.length);
			headers.setContentType(MediaType.parseMediaType("application/" + busquedaReportesDto.export));
			headers.set("Content-Disposition", "inline; filename=report." + busquedaReportesDto.export);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			return new ResponseEntity<byte[]>(report, headers, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.NOT_FOUND);
		}

	}

}
