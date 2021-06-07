package bo.com.tesla.recaudaciones.controllers;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bo.com.tesla.administracion.entity.LogSistemaEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.administracion.entity.TransaccionCobroEntity;
import bo.com.tesla.recaudaciones.dto.ClienteDto;
import bo.com.tesla.recaudaciones.dto.DeudasCobradasFacturaDto;
import bo.com.tesla.recaudaciones.services.ICobroClienteService;
import bo.com.tesla.recaudaciones.services.IHistoricoDeudaService;
import bo.com.tesla.security.services.ILogSistemaService;
import bo.com.tesla.security.services.ISegUsuarioService;
import bo.com.tesla.useful.config.Technicalexception;
import bo.com.tesla.useful.cross.Util;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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

        try {
            String facturaBase64 = iCobroClienteService.postCobrarDeudas(clienteDto, usuario.getUsuarioId(), metodoPagoId);

            byte[] facturaByteArray = Base64.getDecoder().decode(facturaBase64);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(facturaByteArray.length);
            headers.setContentType(MediaType.parseMediaType("application/pdf" ));
            headers.set("Content-Disposition", "inline; filename=report.pdf" );
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(facturaByteArray, headers, HttpStatus.OK);

        } catch (Technicalexception e) {
            e.printStackTrace();
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

            return new ResponseEntity<>("Error " + log.getLogSistemaId() + ": Ocurrio un error en el servidor, por favor intente la operacion mas tarde o consulte con su administrador." , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

 