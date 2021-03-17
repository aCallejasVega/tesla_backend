package bo.com.tesla.entidades.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bo.com.tesla.administracion.entity.ArchivoEntity;
import bo.com.tesla.administracion.entity.EntidadEntity;
import bo.com.tesla.administracion.entity.LogSistemaEntity;
import bo.com.tesla.administracion.entity.RecaudadorEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.entidades.dto.BusquedaReportesDto;
import bo.com.tesla.entidades.dto.DeudasClienteDto;
import bo.com.tesla.entidades.services.IArchivoService;
import bo.com.tesla.entidades.services.IEntidadService;

import bo.com.tesla.entidades.services.IReporteEntidadesService;
import bo.com.tesla.recaudaciones.dto.RecaudadoraDto;
import bo.com.tesla.recaudaciones.services.IHistoricoDeudaService;
import bo.com.tesla.recaudaciones.services.IRecaudadoraService;
import bo.com.tesla.security.services.ILogSistemaService;
import bo.com.tesla.security.services.ISegUsuarioService;
import bo.com.tesla.security.services.ITransaccionCobrosService;
import bo.com.tesla.useful.config.Technicalexception;
import bo.com.tesla.useful.cross.Util;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@RequestMapping("api/ReportEntidad")
public class ReportEntidadController {
	private Logger logger = LoggerFactory.getLogger(ReportEntidadController.class);

	@Autowired
	private ITransaccionCobrosService transaccionCobrosService;

	@Autowired
	private IReporteEntidadesService reporteEntidadesService;

	@Autowired
	private IRecaudadoraService recaudadoraService;

	@Autowired
	private ILogSistemaService logSistemaService;

	@Autowired
	private ISegUsuarioService segUsuarioService;

	@Autowired
	private IEntidadService entidadService;

	@Autowired
	private IHistoricoDeudaService historicoDeudaService;

	@Autowired
	private IArchivoService archivoService;

	@Value("${tesla.path.files-report}")
	private String filesReport;

	@PostMapping(path = "/linealChart")
	public ResponseEntity<?> linealChart(@RequestBody BusquedaReportesDto busquedaReportesDto,
			Authentication authentication) throws Exception {

		Map<String, Object> response = new HashMap<>();
		try {

			List<Object[]> linealChart = this.transaccionCobrosService.getDeudasforDate(busquedaReportesDto.entidadId,
					busquedaReportesDto.recaudadorId, busquedaReportesDto.estado, busquedaReportesDto.fechaInicio,
					busquedaReportesDto.fechaFin);
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

			List<Object[]> linealChart = this.transaccionCobrosService.getDeudasforServicio(
					busquedaReportesDto.entidadId, busquedaReportesDto.recaudadorId, busquedaReportesDto.estado,
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

			List<Object[]> linealChart = this.transaccionCobrosService.getDeudasforTipoServicio(
					busquedaReportesDto.entidadId, busquedaReportesDto.recaudadorId, busquedaReportesDto.estado,
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

	@PostMapping(path = "/deudasPagadas")
	public ResponseEntity<?> deudasPagadas(@RequestBody BusquedaReportesDto busquedaReportesDto,
			Authentication authentication) {
		SegUsuarioEntity usuario = new SegUsuarioEntity();
		Map<String, Object> parameters = new HashMap<>();
		Map<String, Object> response = new HashMap<>();

		try {

			usuario = this.segUsuarioService.findByLogin(authentication.getName());
			EntidadEntity entidad = this.entidadService.findEntidadByUserId(usuario.getUsuarioId());
			if (busquedaReportesDto.recaudadorId == null || busquedaReportesDto.recaudadorId == "") {
				busquedaReportesDto.recaudadorId = "%";
			} else {
				RecaudadorEntity recaudador = this.recaudadoraService
						.findByRecaudadorId(new Long(busquedaReportesDto.recaudadorId));
				parameters.put("recaudadora", recaudador.getNombre());
			}
			parameters.put("fechaInicio", Util.dateToStringFormat(busquedaReportesDto.fechaInicio));
			parameters.put("fechaFin", Util.dateToStringFormat(busquedaReportesDto.fechaFin));

			List<DeudasClienteDto> linealChart = this.reporteEntidadesService.findDeudasPagadasByParameter(
					entidad.getEntidadId(), busquedaReportesDto.recaudadorId, busquedaReportesDto.estado,
					busquedaReportesDto.fechaInicio, busquedaReportesDto.fechaFin);
			if (linealChart.isEmpty()) {
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
			}

			File file = ResourceUtils.getFile("classpath:reportes_entidad/deudas_pagadas.jrxml");
			JasperReport jasper = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(linealChart);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parameters, ds);

			byte[] report = Util.jasperExportFormat(jasperPrint, busquedaReportesDto.export, filesReport);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentLength(report.length);
			headers.setContentType(MediaType.parseMediaType("application/" + busquedaReportesDto.export));
			headers.set("Content-Disposition", "inline; filename=report.pdf");
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			return new ResponseEntity<byte[]>(report, headers, HttpStatus.OK);

		} catch (Technicalexception e) {

			LogSistemaEntity log = new LogSistemaEntity();
			log.setModulo("ENTIDADES");
			log.setController("api/ReportEntidad");
			log.setMensaje(e.getMessage());
			log.setUsuarioCreacion(usuario.getUsuarioId());
			log.setFechaCreacion(new Date());
			this.logSistemaService.save(log);
			this.logger.error("This is cause", e.getMessage());

			response.put("mensaje",
					"Ocurrió un error en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
			response.put("codigo", log.getLogSistemaId() + "");
			response.put("status", false);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			LogSistemaEntity log = new LogSistemaEntity();
			log.setModulo("ENTIDADES");
			log.setController("api/ReportEntidad");
			log.setMensaje(e.getMessage());
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

	@GetMapping(path = "/findDeudasByArchivoIdAndEstado/{archivoId}/{recaudadorId}/{export}/{estado}")
	public ResponseEntity<?> findDeudasByArchivoIdAndEstado(@PathVariable("archivoId") Long archivoId,
			@PathVariable("recaudadorId") Long recaudadorId, @PathVariable("export") String export,
			@PathVariable("estado") String estado, Authentication authentication) throws Exception {
		Map<String, Object> parameters = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		BigDecimal montoTotal = new BigDecimal(0);
		ArchivoEntity archivo = new ArchivoEntity();
		List<DeudasClienteDto> deudasClienteList = new ArrayList<>();
		String estadoTitulo = "";
		try {

			archivo = this.archivoService.findById(archivoId);
			montoTotal = this.historicoDeudaService.getMontoTotalCobrados(archivoId, recaudadorId);
			if (estado == "All") {
				estado = "%";
			}

			if (estado == "ACTIVO") {
				estadoTitulo = "DEUDAS POR PAGAR";
			} else if (estado == "COBRADO") {
				estadoTitulo = "DEUDAS COBRADAS";
			}
			parameters.put("estado", estado);

			parameters.put("fecha_creacion", Util.dateToStringFormat(archivo.getFechaCreacion()));
			parameters.put("monto_total", montoTotal);
			parameters.put("prima", new BigDecimal(3));
			parameters.put("estadoTitulo", estadoTitulo);
			if (recaudadorId == 0) {
				deudasClienteList = this.historicoDeudaService.findDeudasByArchivoIdAndEstado(archivoId, estado);
			} else {
				deudasClienteList = this.historicoDeudaService.findDeudasByArchivoIdAndRecaudadorIdAndEstado(archivoId,
						recaudadorId, estado);
			}

			if (deudasClienteList.isEmpty()) {
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
			}

			if (montoTotal != null) {
				parameters.put("comision", montoTotal.multiply(new BigDecimal(3)).divide(new BigDecimal(100)));
				// parameters.put("comision", deudasClienteList.size()*3);

			}

			List<RecaudadoraDto> recaudadorList = this.historicoDeudaService.getMontoTotalPorRecaudadora(archivoId);
			parameters.put("recaudadorList", recaudadorList);

			File file = ResourceUtils.getFile("classpath:reportes_entidad/deudas_estado.jrxml");
			JasperReport jasper = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(deudasClienteList);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parameters, ds);

			byte[] report = Util.jasperExportFormat(jasperPrint, export, filesReport);

			HttpHeaders headers = new HttpHeaders();

			headers.setContentLength(report.length);
			headers.setContentType(MediaType.parseMediaType("application/" + export));
			headers.set("Content-Disposition", "inline; filename=report.pdf");
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			return new ResponseEntity<byte[]>(report, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping(path = "/findDeudasByParameter")
	public ResponseEntity<?> findDeudasByParameter(@RequestBody BusquedaReportesDto busquedaReportesDto,
			Authentication authentication) throws Exception {
		Page<DeudasClienteDto> deudasClienteList;
		Map<String, Object> response = new HashMap<>();
		try {

			if (busquedaReportesDto.fechaInicio == null ) {
				busquedaReportesDto.fechaInicio = Util.stringToDate("01/01/2021");
			}
			if (busquedaReportesDto.fechaFin == null) {
				busquedaReportesDto.fechaFin = Util.stringToDate("01/01/2100");
			}
			if (busquedaReportesDto.recaudadorId.equals("All") || busquedaReportesDto.recaudadorId == null) {
				busquedaReportesDto.recaudadorId = "%";
			}
			if (busquedaReportesDto.estado.equals("All") || busquedaReportesDto.estado == null) {
				busquedaReportesDto.estado = "%";
			}

			SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
			EntidadEntity entidad = this.entidadService.findEntidadByUserId(usuario.getUsuarioId());
			
			System.out.println("fechaInicio---" + busquedaReportesDto.fechaInicio);
			System.out.println("fechaFin---" + busquedaReportesDto.fechaFin);
			System.out.println("recaudadorId---" + busquedaReportesDto.recaudadorId);
			System.out.println("estado---" + busquedaReportesDto.estado);

			Page<DeudasClienteDto> deudasClienteDtoList = this.reporteEntidadesService.findDeudasByParameter(
					busquedaReportesDto.fechaInicio, busquedaReportesDto.fechaFin, entidad.getEntidadId(),
					busquedaReportesDto.recaudadorId, busquedaReportesDto.estado, busquedaReportesDto.paginacion - 1,
					10);

			if (deudasClienteDtoList.isEmpty()) {
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
	public ResponseEntity<?> findDeudasByParameterForReport(@RequestBody BusquedaReportesDto busquedaReportesDto,
			Authentication authentication) throws Exception {
		Page<DeudasClienteDto> deudasClienteList;
		Map<String, Object> parameters = new HashMap<>();
		try {
			SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
			EntidadEntity entidad = this.entidadService.findEntidadByUserId(usuario.getUsuarioId());

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

			if (busquedaReportesDto.recaudadorId.equals("All") || busquedaReportesDto.recaudadorId == null) {
				busquedaReportesDto.recaudadorId = "%";
				parameters.put("tituloEntidadRecaudadora", "TODAS LAS RECAUDADORAS");
			} else {
				RecaudadorEntity recaudador = this.recaudadoraService
						.findByRecaudadorId(new Long(busquedaReportesDto.recaudadorId));
				parameters.put("tituloEntidadRecaudadora", "RECAUDADORA : " + recaudador.getNombre());
			}

			if (busquedaReportesDto.estado.equals("All") || busquedaReportesDto.estado == null) {
				busquedaReportesDto.estado = "%";
				parameters.put("tituloReporte", "INFORMACIÓN DE TODAS LAS DEUDAS");
			} else if (busquedaReportesDto.estado.equals("ACTIVO") ) {
				parameters.put("tituloReporte", "INFORMACIÓN DE LAS DEUDAS POR PAGAR ");
			} else if (busquedaReportesDto.estado.equals("COBRADO")) {
				parameters.put("tituloReporte", "INFORMACIÓN DE LAS DEUDAS COBRADAS ");
			}

			
			
			List<DeudasClienteDto> deudasClienteDtoList = this.reporteEntidadesService.findDeudasByParameterForReport(
					busquedaReportesDto.fechaInicio, busquedaReportesDto.fechaFin, entidad.getEntidadId(),
					busquedaReportesDto.recaudadorId, busquedaReportesDto.estado);

			if (deudasClienteDtoList.isEmpty()) {
				return new ResponseEntity<Map<String, Object>>(HttpStatus.NO_CONTENT);
			}

			File file = ResourceUtils.getFile("classpath:reportes/entidades/ListaDeudas.jrxml");
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
