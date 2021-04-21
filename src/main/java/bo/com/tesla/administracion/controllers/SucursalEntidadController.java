package bo.com.tesla.administracion.controllers;

import bo.com.tesla.administracion.dto.SucursalEntidadAdmDto;
import bo.com.tesla.administracion.entity.LogSistemaEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.administracion.services.ISucursalEntidadService;
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
@RequestMapping("api/sucursalesentidades")
public class SucursalEntidadController {
    private Logger logger = LoggerFactory.getLogger(SucursalEntidadController.class);

    @Autowired
    private ISucursalEntidadService iSucursalEntidadService;

    @Autowired
    private ILogSistemaService logSistemaService;

    @Autowired
    private ISegUsuarioService segUsuarioService;

    /*********************ABM**************************/

    @PostMapping("")
    public ResponseEntity<?> addUpdateSucursalEntidad(@Valid @RequestBody SucursalEntidadAdmDto sucursalEntidadAdmDto,
                                                        Authentication authentication) {
        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            Boolean esModificacion = sucursalEntidadAdmDto.sucursalEntidadId != null;
            sucursalEntidadAdmDto = iSucursalEntidadService.addUpdateSucursalEntidad(sucursalEntidadAdmDto, usuario.getUsuarioId());
            response.put("status", true);
            response.put("message", esModificacion ? "Se realizó la actualización del registro correctamente." : "Se realizó el registro correctamente");
            response.put("result", sucursalEntidadAdmDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Technicalexception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("ADMINISTRACION.SUCURSALENTIDAD");
            log.setController("api/adm/sucursalesentidades");
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

    @PutMapping("/{sucursalEntidadId}/{transaccion}")
    public ResponseEntity<?> setTransaccion(@PathVariable Long sucursalEntidadId,
                                            @PathVariable String transaccion,
                                           Authentication authentication) {
        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            iSucursalEntidadService.setTransaccionSucursalEntidad(sucursalEntidadId, transaccion, usuario.getUsuarioId());
            response.put("status", true);
            response.put("message", "Se realizó la actualización de la transacción correctamente.");
            response.put("result", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Technicalexception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("ADMINISTRACION.SUCURSALENTIDAD");
            log.setController("api/sucursalesentidades/" + sucursalEntidadId + "/" + transaccion);
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

    @PutMapping("/listas/{transaccion}")
    public ResponseEntity<?> setTransaccion(@RequestBody List<Long> sucursalEntidadIdLst,
                                            @PathVariable String transaccion,
                                            Authentication authentication) {
        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            iSucursalEntidadService.setLstTransaccion(sucursalEntidadIdLst, transaccion, usuario.getUsuarioId());
            response.put("status", true);
            response.put("message", "Se realizó la actualización de la transacción correctamente.");
            response.put("result", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Technicalexception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("ADMINISTRACION.SUCURSALENTIDAD");
            log.setController("api/sucursalesentidades/listas/"+ transaccion);
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

    @GetMapping("/{sucursalEntidadId}")
    public ResponseEntity<?> getSucursalEntidadById(@PathVariable Long sucursalEntidadId,
                                            Authentication authentication) {
        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            SucursalEntidadAdmDto sucursalEntidadAdmDto = iSucursalEntidadService.getSucursalEntidadById(sucursalEntidadId);
            if(sucursalEntidadAdmDto != null) {
                response.put("status", true);
                response.put("message", "El registro fue encontrado.");
                response.put("result", sucursalEntidadAdmDto);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("status", false);
                response.put("message", "El registro no fue encontrado.");
                response.put("result", null);
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("ADMINISTRACION.SUCURSALENDTIDAD");
            log.setController("api/sucursalesentidades/" + sucursalEntidadId);
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
    public ResponseEntity<?> getListSucursalesEntidades(Authentication authentication) {
        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            List<SucursalEntidadAdmDto> sucursalEntidadAdmDtos = iSucursalEntidadService.getAllSucursalEntidades();
            if(!sucursalEntidadAdmDtos.isEmpty()) {
                response.put("status", true);
                response.put("message", "El listado fue encontrado.");
                response.put("result", sucursalEntidadAdmDtos);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("status", false);
                response.put("message", "El listado no fue encontrado.");
                response.put("result", null);
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("ADMINISTRACION.SUCURSALENTIDAD");
            log.setController("api/sucursalentidades");
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

    @GetMapping("/entidades/{entidadId}")
    public ResponseEntity<?> getListSucursalesEntidades(@PathVariable Long entidadId,
                                                        Authentication authentication) {
        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            List<SucursalEntidadAdmDto> sucursalEntidadAdmDtos = iSucursalEntidadService.getLisSucursalEntidadesByEntidadId(entidadId);
            if(!sucursalEntidadAdmDtos.isEmpty()) {
                response.put("status", true);
                response.put("message", "El listado fue encontrado.");
                response.put("result", sucursalEntidadAdmDtos);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("status", false);
                response.put("message", "El listado no fue encontrado.");
                response.put("result", null);
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("ADMINISTRACION.SUCURSALENTIDAD");
            log.setController("api/sucursalentidades");
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