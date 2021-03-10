package bo.com.tesla.recaudaciones.controllers;

import bo.com.tesla.administracion.entity.LogSistemaEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.recaudaciones.dto.ClienteDto;
import bo.com.tesla.recaudaciones.dto.DominioDto;
import bo.com.tesla.recaudaciones.dto.EntidadDto;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;
import bo.com.tesla.recaudaciones.services.IDeudaClienteRService;
import bo.com.tesla.recaudaciones.services.IEntidadRService;
import bo.com.tesla.security.services.ILogSistemaService;
import bo.com.tesla.security.services.ISegUsuarioService;
import bo.com.tesla.useful.config.Technicalexception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("api/entidades")
public class EntidadController {
    private Logger logger = LoggerFactory.getLogger(EntidadController.class);

    @Autowired
    private IDeudaClienteRService iDeudaClienteRService;

    @Autowired
    private IEntidadRService iEntidadRService;

    @Autowired
    private ILogSistemaService logSistemaService;

    @Autowired
    private ISegUsuarioService segUsuarioService;

    @GetMapping("/{entidadId}/clientes/{datoCliente}")
    public ResponseEntity<?> getAllClientesByEntidadId(@PathVariable Long entidadId,
                                                       @PathVariable String datoCliente,
                                                       Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        if(entidadId <= 0 || entidadId == null) {
            response.put("status", false);
            response.put("message", "La entidad es obligatoria.");
            response.put("result", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        if(datoCliente == null || datoCliente.length() == 0 ) {
            response.put("status", false);
            response.put("message", "El dato del cliente es obligatorio");
            response.put("result", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        try{
            List<ClienteDto> clienteDtos = iDeudaClienteRService.getByEntidadAndClienteLike(entidadId, datoCliente);
            if(clienteDtos.isEmpty()) {
                response.put("status", false);
                response.put("result", null);
                response.put("message", "No hay cliente(s) asociados a los parámetros de búsqueda");
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            }
            response.put("status", true);
            response.put("result", clienteDtos);
            response.put("message", "Cliente(s) encontrado(s)");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Technicalexception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("RECAUDACIONES.ENTIDADES");
            log.setController("api/entidades/" + entidadId + "/clientes/" + datoCliente);
            log.setCausa(e.getCause() != null ? e.getCause().getCause()+"" : e.getCause()+"");
            log.setMensaje(e.getMessage()+"");
            log.setUsuarioCreacion(usuario.getUsuarioId());
            log.setFechaCreacion(new Date());
            logSistemaService.save(log);
            this.logger.error("This is error", e.getMessage());
            this.logger.error("This is cause", e.getCause() != null ? e.getCause().getCause()+"" : e.getCause()+"");
            response.put("status", false);
            response.put("result", null);
            response.put("message", "Ocurrió un error en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
            response.put("code", log.getLogSistemaId()+"");
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{entidadId}/clientes/{codigoCliente}/deudas")
    public ResponseEntity<?> getDeudasByCliente(@PathVariable Long entidadId,
                                                @PathVariable String codigoCliente,
                                                Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        if(entidadId <= 0 || entidadId == null) {
            response.put("status", "false");
            response.put("message", "La entidad es obligatoria");
            response.put("result", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        if(codigoCliente == null || codigoCliente.length() == 0 ) {
            response.put("status", "false");
            response.put("message", "El código del cliente es obligatorio");
            response.put("result", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        try{
            List<ServicioDeudaDto> servicioDeudaDtos = iDeudaClienteRService.getDeudasByCliente(entidadId, codigoCliente);
            if(servicioDeudaDtos.isEmpty()) {
                response.put("status", false);
                response.put("result", null);
                response.put("message", "No se encontraron deudas");
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            }
            response.put("status", true);
            response.put("result", servicioDeudaDtos);
            response.put("message", "Deudas encontradas para cliente: " + codigoCliente);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Technicalexception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("RECAUDACIONES.ENTIDADES");
            log.setController("api/entidades/" + entidadId + "/clientes/" + codigoCliente + "/deudas");
            log.setCausa(e.getCause() != null ? e.getCause().getCause()+"" : e.getCause()+"");
            log.setMensaje(e.getMessage()+"");
            log.setUsuarioCreacion(usuario.getUsuarioId());
            log.setFechaCreacion(new Date());
            logSistemaService.save(log);
            this.logger.error("This is error", e.getMessage());
            this.logger.error("This is cause", e.getCause() != null ? e.getCause().getCause()+"" : e.getCause()+"");
            response.put("status", false);
            response.put("result", null);
            response.put("message", "Ocurrió un error en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
            response.put("code", log.getLogSistemaId()+"");
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tipos")
    public ResponseEntity<?> getEntidadesByTipo(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        try {
            List<DominioDto> dominioDtos = iEntidadRService.getTipoEntidadByRecaudador(usuario);
            if(dominioDtos.isEmpty()) {
                response.put("status", false);
                response.put("result", null);
                response.put("message", "No existe Tipos Entidades encontrados");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            response.put("status", true);
            response.put("result", dominioDtos);
            response.put("message", "Tipos Entidades encontrados");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Technicalexception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("RECAUDACIONES.ENTIDADES");
            log.setController("api/entidades/tipos");
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

    @GetMapping("/tipos/{tipoEntidadId}")
    public ResponseEntity<?> getEntidadesByTipoEntidad(@PathVariable Long tipoEntidadId, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        if(tipoEntidadId <= 0 || tipoEntidadId == null) {
            response.put("status", false);
            response.put("message", "El tipo de entidad es obligatoria");
            response.put("result", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        try {
            List<EntidadDto> entidadDtos =  iEntidadRService.getEntidadesByTipoEntidad(tipoEntidadId,usuario);
            if(entidadDtos.isEmpty()) {
                response.put("status", false);
                response.put("result", null);
                response.put("message", "No se encontraron Entidades por Tipo de Entidad.");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            response.put("status", true);
            response.put("result", entidadDtos);
            response.put("message", "Se encontraron Entidades por Tipo");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Technicalexception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("RECAUDACIONES.ENTIDADES");
            log.setController("api/entidades/tipos/"+ tipoEntidadId);
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

    @GetMapping("")
    public ResponseEntity<?> getEntidadesByRecaudadora(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        try {
            List<EntidadDto> entidadDtos =  iEntidadRService.getByRecaudadoraId(usuario);
            if(entidadDtos.isEmpty()) {
                response.put("status", false);
                response.put("result", null);
                response.put("message", "Empresas no encontradas.");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            response.put("status", true);
            response.put("result", entidadDtos);
            response.put("message", "Se encontraron Empresas.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Technicalexception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("RECAUDACIONES.ENTIDADES");
            log.setController("api/entidades");
            log.setCausa(e.getCause() != null ? e.getCause().getCause()+"" : e.getCause()+"");
            log.setMensaje(e.getMessage()+"");
            log.setUsuarioCreacion(usuario.getUsuarioId());
            log.setFechaCreacion(new Date());
            logSistemaService.save(log);
            this.logger.error("This is error", e.getMessage());
            this.logger.error("This is cause", e.getCause() != null ? e.getCause().getCause()+"" : e.getCause()+"");
            response.put("status", false);
            response.put("result", null);
            response.put("message", "Ocurrió un error en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
            response.put("code", log.getLogSistemaId()+"");
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
