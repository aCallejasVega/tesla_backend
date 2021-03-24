package bo.com.tesla.recaudaciones.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import bo.com.tesla.administracion.entity.LogSistemaEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.entidades.controller.DeudaClienteController;
import bo.com.tesla.security.services.ILogSistemaService;
import bo.com.tesla.security.services.ISegUsuarioService;
import bo.com.tesla.useful.config.BusinesException;
import bo.com.tesla.useful.config.Technicalexception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bo.com.tesla.recaudaciones.dto.ClienteDto;
import bo.com.tesla.recaudaciones.services.ICobroClienteService;

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


    @PostMapping("/{metodoPagoId}")
    public ResponseEntity<?> postCobrarDeudas(@RequestBody ClienteDto clienteDto,
                                              @PathVariable Long metodoPagoId,
                                              Authentication authentication) {
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
        try {
            iCobroClienteService.postCobrarDeudas(clienteDto, usuario.getUsuarioId(), metodoPagoId);
            response.put("status", true);
            response.put("message", "Se realizó el cobro de las deudas correctamente.");
            response.put("result", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
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

 