package bo.com.tesla.pagos.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import bo.com.tesla.administracion.entity.PPagoClienteEntity;
import bo.com.tesla.administracion.entity.PTransaccionPagoEntity;
import bo.com.tesla.administracion.entity.RecaudadorEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.pagos.dto.PBeneficiarioDto;
import bo.com.tesla.pagos.services.IPAbonoClienteService;
import bo.com.tesla.pagos.services.IPPagoClienteService;
import bo.com.tesla.recaudaciones.services.IRecaudadoraService;
import bo.com.tesla.security.services.ISegUsuarioService;
import bo.com.tesla.useful.cross.Util;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@RestController
@RequestMapping("api/recaudadoraPagos")
public class RecaudadoraPagosController {
	private Logger logger = LoggerFactory.getLogger(RecaudadoraPagosController.class);

	@Autowired
	private ISegUsuarioService segUsuarioService;

	@Autowired
	private IRecaudadoraService recaudadoraService;

	@Autowired
	private IPAbonoClienteService abonoClienteService;

	@Autowired
	private IPPagoClienteService pagoClienteService;

	@Autowired
	private DataSource dataSource;

	@Value("${tesla.path.files-report}")
	private String filesReport;

	@GetMapping(path = { "/getBeneficiariosParaPagar/{servicioProductoId}/",
			"/getBeneficiariosParaPagar/{servicioProductoId}/{paramBusqueda}", }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getAbonosParaPagar(@PathVariable("servicioProductoId") Long servicioProductoId,
			@PathVariable("paramBusqueda") Optional<String> paramBusqueda, Authentication authentication)
			throws Exception {
		Map<String, Object> response = new HashMap<>();
		List<PBeneficiarioDto> abonosClientesList = new ArrayList<>();
		String newParamBusqueda = "";
		try {
			if (paramBusqueda.isPresent()) {
				newParamBusqueda = paramBusqueda.get();
			}

			SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
			RecaudadorEntity recaudador = this.recaudadoraService.findRecaudadorByUserId(usuario.getUsuarioId());
			abonosClientesList = this.abonoClienteService.getAbonosParaPagar(servicioProductoId,
					recaudador.getRecaudadorId(), newParamBusqueda);
			if (abonosClientesList.isEmpty()) {
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
			}
			response.put("data", abonosClientesList);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/getBeneficiario/{archivoId}/{codigoCliente}/{nroDocumentoCliente}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getAbonado(@PathVariable("archivoId") Long archivoId,
			@PathVariable("codigoCliente") String codigoCliente,
			@PathVariable("nroDocumentoCliente") String nroDocumentoCliente, Authentication authentication)
			throws Exception {
		Map<String, Object> response = new HashMap<>();
		List<PBeneficiarioDto> abonosClientesList = new ArrayList<>();
		try {
			abonosClientesList = this.abonoClienteService.getBeneficiarioPagos(archivoId, codigoCliente,
					nroDocumentoCliente);
			if (abonosClientesList == null) {
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
			}
			response.put("data", abonosClientesList);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping(path = "/realizarPago")
	public ResponseEntity<?> realizarPago(@RequestBody List<PBeneficiarioDto> abonoClienteList,
			Authentication authentication) throws Exception {
		Map<String, Object> parameters = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		SegUsuarioEntity usuario = new SegUsuarioEntity();
		PTransaccionPagoEntity transaccionPago = new PTransaccionPagoEntity();
		List<PPagoClienteEntity> pagoClienteList = new ArrayList<>();
		try {
			usuario = this.segUsuarioService.findByLogin(authentication.getName());

			transaccionPago = this.pagoClienteService.realizarPago(abonoClienteList, usuario.getUsuarioId());

			System.out.println("----------------------------- trasanccionid "+transaccionPago.getTransaccionPagoId());
			
			parameters.put("montoLiteral", Util.translate(transaccionPago.getTotal() + ""));
			parameters.put("transaccionPagoId", transaccionPago.getTransaccionPagoId());

			File file = ResourceUtils
					.getFile(filesReport + "/report_jrxml/reportes/recaudador/comprobanteMaestro.jrxml");

			JasperReport jasper = JasperCompileManager.compileReport(file.getAbsolutePath());

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parameters, dataSource.getConnection());

			byte[] report = Util.jasperExportFormat(jasperPrint, "pdf", filesReport);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentLength(report.length);
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			headers.set("Content-Disposition", "inline; filename=report.pdf");
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			return new ResponseEntity<byte[]>(report, headers, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

	}

}
