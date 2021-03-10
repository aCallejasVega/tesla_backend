package bo.com.tesla.administracion.controllers;

import bo.com.tesla.administracion.dto.EntidadAdmDto;
import bo.com.tesla.administracion.entity.LogSistemaEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.administracion.services.IEntidadAdmService;
import bo.com.tesla.security.services.ILogSistemaService;
import bo.com.tesla.security.services.ISegUsuarioService;
import bo.com.tesla.useful.config.Technicalexception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/adm/entidades")//
public class EntidadAdmController {
    private Logger logger = LoggerFactory.getLogger(EntidadAdmController.class);

    @Autowired
    private ILogSistemaService logSistemaService;

    @Autowired
    private ISegUsuarioService segUsuarioService;

    @Autowired
    private IEntidadAdmService iEntidadAService;

    @PostMapping("")
    public ResponseEntity<?> addUpdateEntidad(@Valid @RequestBody EntidadAdmDto entidadAdmDto,
                                                Authentication authentication) {
        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            Boolean esModificacion = entidadAdmDto.entidadId != null;
            entidadAdmDto = iEntidadAService.addUpdateEntidad(entidadAdmDto, usuario.getUsuarioId());
            response.put("status", true);
            response.put("message", esModificacion ? "Se realizó la actualización del registro correctamente." : "Se realizó el registro correctamente");
            response.put("result", entidadAdmDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Technicalexception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("ADMINISTRACION.ENTIDAD");
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
            response.put("message", "Ocurrió un problema en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
            response.put("code", log.getLogSistemaId()+"");
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
        LogSistemaEntity log=new LogSistemaEntity();
        log.setModulo("ADMINISTRACION.ENTIDAD");
        log.setController("api/adm/entidades");
        log.setCausa(e.getCause() != null ? e.getCause().getCause()+"" : e.getCause()+"");
        log.setMensaje(e.getMessage()+"");
        log.setUsuarioCreacion(usuario.getUsuarioId());
        log.setFechaCreacion(new Date());
        logSistemaService.save(log);
        this.logger.error("This is error", e.getMessage());
        this.logger.error("This is cause", e.getCause() != null ? e.getCause().getCause()+"" : e.getCause()+"");
        response.put("status", false);
        response.put("result", null);
        response.put("message", "Ocurrió un problema en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
        response.put("code", log.getLogSistemaId()+"");
        return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    }

    @PutMapping("/{entidadId}/{transaccion}")
    public ResponseEntity<?> setTransaccion(@PathVariable Long entidadId,
                                            @PathVariable String transaccion,
                                            Authentication authentication) {
        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            iEntidadAService.setTransaccion(entidadId, transaccion, usuario.getUsuarioId());
            response.put("status", true);
            response.put("message", "Se realizó la actualización de la transacción correctamente.");
            response.put("result", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("ADMINISTRACION.ENTIDAD");
            log.setController("api/adm/entidades/" + entidadId + "/" + transaccion);
            log.setCausa(e.getCause() != null ? e.getCause().getCause()+"" : e.getCause()+"");
            log.setMensaje(e.getMessage()+"");
            log.setUsuarioCreacion(usuario.getUsuarioId());
            log.setFechaCreacion(new Date());
            logSistemaService.save(log);
            this.logger.error("This is error", e.getMessage());
            this.logger.error("This is cause", e.getCause() != null ? e.getCause().getCause()+"" : e.getCause()+"");
            response.put("status", false);
            response.put("result", null);
            response.put("message", "Ocurrió un problema en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
            response.put("code", log.getLogSistemaId()+"");
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/listas/{transaccion}")
    public ResponseEntity<?> setLstTransaccion(@RequestBody List<Long> entidadIdLst,
                                            @PathVariable String transaccion,
                                            Authentication authentication) {
        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            iEntidadAService.setLstTransaccion(entidadIdLst, transaccion, usuario.getUsuarioId());
            response.put("status", true);
            response.put("message", "Se realizó la actualización de la transacción correctamente.");
            response.put("result", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("ADMINISTRACION.ENTIDAD");
            log.setController("api/adm/entidades/listas/" + entidadIdLst.toString() + "/" + transaccion);
            log.setCausa(e.getCause() != null ? e.getCause().getCause()+"" : e.getCause()+"");
            log.setMensaje(e.getMessage()+"");
            log.setUsuarioCreacion(usuario.getUsuarioId());
            log.setFechaCreacion(new Date());
            logSistemaService.save(log);
            this.logger.error("This is error", e.getMessage());
            this.logger.error("This is cause", e.getCause() != null ? e.getCause().getCause()+"" : e.getCause()+"");
            response.put("status", false);
            response.put("result", null);
            response.put("message", "Ocurrió un problema en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
            response.put("code", log.getLogSistemaId()+"");
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{entidadId}")
    public ResponseEntity<?> getEntidadById(@PathVariable Long entidadId,
                                          Authentication authentication) {
        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            EntidadAdmDto entidadAdmDto = iEntidadAService.getEntidadById(entidadId);
            if(entidadAdmDto != null) {
                response.put("status", true);
                response.put("message", "El registro fue encontrado.");
                response.put("result", entidadAdmDto);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("status", false);
                response.put("message", "El registro no fue encontrado.");
                response.put("result", null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("ADMINISTRACION.ENTIDAD");
            log.setController("api/entidades/" + entidadId);
            log.setCausa(e.getCause()+"");
            log.setMensaje(e.getMessage()+"");
            log.setUsuarioCreacion(usuario.getUsuarioId());
            log.setFechaCreacion(new Date());
            logSistemaService.save(log);
            this.logger.error("This is error", e.getMessage());
            this.logger.error("This is cause", e.getCause());
            response.put("status", false);
            response.put("result", null);
            response.put("message", "Ocurrió un problema en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
            response.put("code", log.getLogSistemaId()+"");
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getListEntidades(Authentication authentication) {
        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            List<EntidadAdmDto> entidadAdmDtoList = iEntidadAService.getAllEntidades();
            if(entidadAdmDtoList != null) {
                response.put("status", true);
                response.put("message", "El listado fue encontrado.");
                response.put("result", entidadAdmDtoList);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("status", false);
                response.put("message", "El listado no fue encontrado.");
                response.put("result", null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("ADMINISTRACION.ENTIDAD");
            log.setController("api/adm/entidades/");
            log.setCausa(e.getCause()+"");
            log.setMensaje(e.getMessage()+"");
            log.setUsuarioCreacion(usuario.getUsuarioId());
            log.setFechaCreacion(new Date());
            logSistemaService.save(log);
            this.logger.error("This is error", e.getMessage());
            this.logger.error("This is cause", e.getCause());
            response.put("status", false);
            response.put("result", null);
            response.put("message", "Ocurrió un problema en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
            response.put("code", log.getLogSistemaId()+"");
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
