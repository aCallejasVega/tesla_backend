package bo.com.tesla.administracion.controllers;

import java.io.File;
import java.util.Date;
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

import bo.com.tesla.administracion.dto.BusquedaReportesAdmDto;
import bo.com.tesla.administracion.dto.DeudasClienteAdmDto;
import bo.com.tesla.administracion.entity.LogSistemaEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.administracion.services.IReporteAdminService;
import bo.com.tesla.security.services.ILogSistemaService;
import bo.com.tesla.security.services.ISegUsuarioService;
import bo.com.tesla.useful.cross.Util;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@RequestMapping("api/ReportAdmin")
public class ReportAdminController {
	private Logger logger = LoggerFactory.getLogger(ReportAdminController.class);

	@Autowired
	private IReporteAdminService reporteAdminService;

	@Autowired
	private ISegUsuarioService segUsuarioService;
	
	@Autowired
	private ILogSistemaService logSistemaService;

	@Value("${tesla.path.files-report}")
	private String filesReport;

	@PostMapping(path = "/findDeudasByParameter")
	public ResponseEntity<?> findDeudasByParameter(@RequestBody BusquedaReportesAdmDto busquedaReportesDto,
			Authentication authentication) throws Exception {
		SegUsuarioEntity usuario = new SegUsuarioEntity();
		Map<String, Object> response = new HashMap<>();
		try {
			usuario = this.segUsuarioService.findByLogin(authentication.getName());
			if (busquedaReportesDto.fechaInicio == null) {
				busquedaReportesDto.fechaInicio = Util.stringToDate("01/01/2021");
			}
			if (busquedaReportesDto.fechaFin == null) {
				busquedaReportesDto.fechaFin = Util.stringToDate("01/01/2100");
			}
			if (busquedaReportesDto.entidadId.equals("All") || busquedaReportesDto.entidadId == null) {
				busquedaReportesDto.entidadId = "%";
			}
			if (busquedaReportesDto.recaudadorId.equals("All") || busquedaReportesDto.entidadId == null) {
				busquedaReportesDto.recaudadorId = "%";
			}
			if (busquedaReportesDto.estado.equals("All") || busquedaReportesDto.estado == null) {
				busquedaReportesDto.estado = "%";
			}			
			
			Page<DeudasClienteAdmDto> deudasClienteDtoList = this.reporteAdminService.findDeudasByParameter(
					busquedaReportesDto.fechaInicio, busquedaReportesDto.fechaFin, busquedaReportesDto.entidadId,
					busquedaReportesDto.recaudadorId, busquedaReportesDto.estado, busquedaReportesDto.paginacion - 1,
					10);

			if (deudasClienteDtoList.isEmpty()) {
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
			}
			response.put("data", deudasClienteDtoList);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (Exception e) {
			LogSistemaEntity log = new LogSistemaEntity();
			log.setModulo("ENTIDADES");
			log.setController("api/ReportAdmin/findDeudasByParameter");
			log.setMensaje(e.getMessage());
			log.setCausa(e.getCause().toString());
			log.setUsuarioCreacion(usuario.getUsuarioId());
			log.setFechaCreacion(new Date());
			this.logSistemaService.save(log);
			this.logger.error("This is cause", e.getMessage());

			response.put("mensaje",
					"Ocurrió un error en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
			response.put("codigo", log.getLogSistemaId() + "");
			response.put("status", false);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping(path = "/findDeudasByParameterForReport")
	public ResponseEntity<?> findDeudasByParameterForReport(@RequestBody BusquedaReportesAdmDto busquedaReportesDto,
			Authentication authentication) throws Exception {
		SegUsuarioEntity usuario = new SegUsuarioEntity();
		
		Map<String, Object> response = new HashMap<>();
		Map<String, Object> parameters = new HashMap<>();
		try {
			usuario = this.segUsuarioService.findByLogin(authentication.getName());
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

			if (busquedaReportesDto.entidadId.equalsIgnoreCase("All")) {
				busquedaReportesDto.entidadId = "%";
			}
			if (busquedaReportesDto.recaudadorId.equalsIgnoreCase("All")) {
				busquedaReportesDto.recaudadorId = "%";
			}

			if (busquedaReportesDto.estado.equals("All") || busquedaReportesDto.estado == null) {
				busquedaReportesDto.estado = "%";
				parameters.put("tituloReporte", "INFORMACIÓN DE TODAS LAS DEUDAS");
			} else if (busquedaReportesDto.estado.equals("ACTIVO")) {
				parameters.put("tituloReporte", "INFORMACIÓN DE LAS DEUDAS POR PAGAR ");
			} else if (busquedaReportesDto.estado.equals("COBRADO")) {
				parameters.put("tituloReporte", "INFORMACIÓN DE LAS DEUDAS COBRADAS ");
			}
			parameters.put("logoTesla", filesReport + "/img/teslapng.png");

			List<DeudasClienteAdmDto> deudasClienteDtoList = this.reporteAdminService.findDeudasByParameterForReport(
					busquedaReportesDto.fechaInicio, busquedaReportesDto.fechaFin, busquedaReportesDto.entidadId,
					busquedaReportesDto.recaudadorId, busquedaReportesDto.estado);

			if (deudasClienteDtoList.isEmpty()) {
				return new ResponseEntity<Map<String, Object>>(HttpStatus.NO_CONTENT);
			}

			File file = ResourceUtils
					.getFile(filesReport + "/report_jrxml/reportes/administracion/ListaDeudasAdmin.jrxml");
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
			LogSistemaEntity log = new LogSistemaEntity();
			log.setModulo("ENTIDADES");
			log.setController("api/ReportAdmin/findDeudasByParameterForReport");
			log.setMensaje(e.getMessage());
			log.setCausa(e.getCause().toString());
			log.setUsuarioCreacion(usuario.getUsuarioId());
			log.setFechaCreacion(new Date());
			this.logSistemaService.save(log);
			this.logger.error("This is cause", e.getMessage());

			response.put("mensaje",
					"Ocurrió un error en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
			response.put("codigo", log.getLogSistemaId() + "");
			response.put("status", false);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

	}

}
