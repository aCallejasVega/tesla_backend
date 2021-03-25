package bo.com.tesla.recaudaciones.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bo.com.tesla.administracion.entity.LogSistemaEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.administracion.entity.TransaccionCobroEntity;
import bo.com.tesla.entidades.controller.DeudaClienteController;
import bo.com.tesla.security.services.ILogSistemaService;
import bo.com.tesla.security.services.ISegUsuarioService;
import bo.com.tesla.useful.config.BusinesException;
import bo.com.tesla.useful.config.Technicalexception;
import bo.com.tesla.useful.cross.Util;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bo.com.tesla.recaudaciones.dto.ClienteDto;
import bo.com.tesla.recaudaciones.dto.DeudasCobradasFacturaDto;
import bo.com.tesla.recaudaciones.services.ICobroClienteService;
import bo.com.tesla.recaudaciones.services.IHistoricoDeudaService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/cobros")
public class CobroClienteController {

    private Logger logger = LoggerFactory.getLogger(CobroClienteController.class);

    @Autowired
    private ICobroClienteService iCobroClienteService;

    @Autowired
    private ISegUsuarioService segUsuarioService;

    @Autowired
    private ILogSistemaService logSistemaService;
    
    @Autowired
    private IHistoricoDeudaService historicoDeudaService;

	@Value("${tesla.path.files-report}")
	private String filesReport;

    @PostMapping("/{metodoPagoId}")
    public ResponseEntity<?> postCobrarDeudas(@RequestBody ClienteDto clienteDto,
                                              @PathVariable Long metodoPagoId,
                                              Authentication authentication)  throws Exception {
    	System.out.println("****************postCobrarDeudas*******************");
        Map<String, Object> response = new HashMap<>();
        if(clienteDto == null || clienteDto.nombreCliente == null || clienteDto.nroDocumento == null || clienteDto.codigoCliente == null) {
            response.put("status", false);
            response.put("message", "Ocurrió un error en el servidor, por favor verifique selecciona de deudas o datos de clientes");
            response.put("result", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        if(metodoPagoId == null || metodoPagoId <= 0) {
            response.put("status", false);
            response.put("message", "Ocurrió un error en el servidor, por favor verifique parametros");
            response.put("result", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        List<TransaccionCobroEntity> transaccionesCobroList=new ArrayList<>();
        List<Long> transaccionesCobrosIds=new ArrayList<>();
        Map<String, Object> parameters = new HashMap<>();
        
        try {
        	transaccionesCobroList=  iCobroClienteService.postCobrarDeudas(clienteDto, usuario.getUsuarioId(), metodoPagoId);
            for (TransaccionCobroEntity transaccionCobroEntity : transaccionesCobroList) {
            	transaccionesCobrosIds.add(transaccionCobroEntity.getTransaccionCobroId());
            	
			}
            
            List<DeudasCobradasFacturaDto> deudasCobradasList= historicoDeudaService.findDeudasCobrasForFactura(transaccionesCobrosIds);
            
            parameters.put("logoTesla",filesReport+"/img/teslablanco.png" ); 
            
            File file = ResourceUtils.getFile(filesReport+"/report_jrxml/reportes/recaudador/factura.jrxml");
			JasperReport jasper = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(deudasCobradasList);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parameters, ds);

			byte[] report = Util.jasperExportFormat(jasperPrint, "pdf", filesReport);
			System.out.println("****************************************  Todo Bien");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentLength(report.length);
			headers.setContentType(MediaType.parseMediaType("application/pdf" ));
			headers.set("Content-Disposition", "inline; filename=report.pdf" );
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			return new ResponseEntity<byte[]>(report, headers, HttpStatus.OK);
            
           /* response.put("status", true);
            response.put("message", "Se realizó el cobro de las deudas correctamente.");
            response.put("result", true);
            return new ResponseEntity<>(response, HttpStatus.OK);*/
        } catch (Technicalexception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("RECAUDACION.COBROS");
            log.setController("POST: api/cobros/" + metodoPagoId);
            log.setCausa(e.getCause() != null ? e.getCause().getCause()+"" : e.getCause()+"");
            log.setMensaje(e.getMessage()+"");
            log.setUsuarioCreacion(usuario.getUsuarioId());
            log.setFechaCreacion(new Date());
            logSistemaService.save(log);
            this.logger.error("This is error", e.getMessage());
            this.logger.error("This is cause", e.getCause() != null ? e.getCause().getCause()+"" : e.getCause()+"");
        	e.printStackTrace();
            response.put("status", false);
            response.put("result", null);
            response.put("message", "Ocurrió un error en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
            response.put("code", log.getLogSistemaId()+"");
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}

 