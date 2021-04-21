package bo.com.tesla.administracion.controllers;

import bo.com.tesla.administracion.dto.SucursalAdmDto;
import bo.com.tesla.administracion.entity.LogSistemaEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.administracion.services.ISucursalService;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/sucursales")
public class SucursalController {
    private Logger logger = LoggerFactory.getLogger(SucursalController.class);

    @Autowired
    private ILogSistemaService logSistemaService;

    @Autowired
    private ISegUsuarioService segUsuarioService;

    @Autowired
    private ISucursalService iSucursalService;

    /*********************ABM**************************/

    @PostMapping("")
    public ResponseEntity<?> addUpdateSucursal(@RequestBody SucursalAdmDto sucursalAdmDto,
                                                 Authentication authentication) {
        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            Boolean esModificacion = sucursalAdmDto.sucursalId != null;
            sucursalAdmDto = iSucursalService.addUpdateSucursal(sucursalAdmDto, usuario.getUsuarioId());
            response.put("status", true);
            response.put("message", esModificacion ? "Se realizó la actualización del registro correctamente." : "Se realizó el registro correctamente.");
            response.put("result", sucursalAdmDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Technicalexception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("ADMINISTRACION.SUCURSAL");
            log.setController("POST: api/sucursales");
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

    @PutMapping("/{sucursalId}/{transaccion}")
    public ResponseEntity<?> setTransaccion(@PathVariable Long sucursalId,
                                            @PathVariable String transaccion,
                                            Authentication authentication) {
        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            iSucursalService.setTransaccion(sucursalId, transaccion, usuario.getUsuarioId());
            response.put("status", true);
            response.put("message", "Se realizó la actualización de la transacción correctamente.");
            response.put("result", true);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Technicalexception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("ADMINISTRACION.SUCURSAL");
            log.setController("PUT: api/sucursales/" + sucursalId + "/" + transaccion);
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
    public ResponseEntity<?> setLstTransaccion(@RequestBody List<Long> sucursalIdLst,
                                               @PathVariable String transaccion,
                                               Authentication authentication) {
        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            iSucursalService.setLstTransaccion(sucursalIdLst, transaccion, usuario.getUsuarioId());
            response.put("status", true);
            response.put("message", "Se realizó la actualización de registro(s) con el nuevo estado.");
            response.put("result", true);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Technicalexception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("ADMINISTRACION.SUCURSAL");
            log.setController("PUT: api/sucursales/listas/" + transaccion);
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

    @GetMapping("/{sucursalId}")
    public ResponseEntity<?> getSucursalById(@PathVariable Long sucursalId,
                                                Authentication authentication) {
        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            SucursalAdmDto sucursalAdmDto = iSucursalService.getSucursalById(sucursalId);
            if(sucursalAdmDto != null ) {
                response.put("status", true);
                response.put("message", "El registro fue encontrado.");
                response.put("result", sucursalAdmDto);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("status", false);
                response.put("message", "El registro no fue encontrado.");
                response.put("result", null);
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            }

        } catch (Technicalexception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("ADMINISTRACION.SUCURSAL");
            log.setController("GET: api/sucursales/" + sucursalId);
            log.setCausa(e.getCause() != null ? e.getCause().getCause()+"" : e.getCause()+"");
            log.setMensaje(e.getMessage()+"");
            log.setUsuarioCreacion(usuario.getUsuarioId());
            log.setFechaCreacion(new Date());
            logSistemaService.save(log);
            this.logger.error("This is error", e.getMessage());
            this.logger.error("This is cause", e.getCause() != null ? e.getCause().getCause()+"" : e.getCause());
            response.put("status", false);
            response.put("result", null);
            response.put("message", "Ocurrió un problema en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
            response.put("code", log.getLogSistemaId()+"");
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getListSucursales(Authentication authentication) {
        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            List<SucursalAdmDto> sucursalAdmDtoList = iSucursalService.getAllSucursales();
            if(!sucursalAdmDtoList.isEmpty()) {
                response.put("status", true);
                response.put("message", "El listado fue encontrado.");
                response.put("result", sucursalAdmDtoList);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("status", false);
                response.put("message", "El listado no fue encontrado.");
                response.put("result", null);
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            }
        } catch (Technicalexception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("ADMINISTRACION.SUCURSAL");
            log.setController("GET: api/sucursales");
            log.setCausa(e.getCause() != null ? e.getCause().getCause()+"" : e.getCause()+"");
            log.setMensaje(e.getMessage()+"");
            log.setUsuarioCreacion(usuario.getUsuarioId());
            log.setFechaCreacion(new Date());
            logSistemaService.save(log);
            this.logger.error("This is error", e.getMessage());
            this.logger.error("This is cause", e.getCause() != null ? e.getCause().getCause()+"" : e.getCause());
            response.put("status", false);
            response.put("result", null);
            response.put("message", "Ocurrió un problema en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
            response.put("code", log.getLogSistemaId()+"");
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/recaudadores/{recaudadorId}")
    public ResponseEntity<?> getListSucursalesByRecaudadorId(@PathVariable Long recaudadorId,
                                                                Authentication authentication) {
        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            List<SucursalAdmDto> sucursalAdmDtoList = iSucursalService.getListSucursalesByRecaudadora(recaudadorId);
            if(!sucursalAdmDtoList.isEmpty()) {
                response.put("status", true);
                response.put("message", "El listado fue encontrado.");
                response.put("result", sucursalAdmDtoList);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("status", false);
                response.put("message", "El listado no fue encontrado.");
                response.put("result", null);
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            }
        } catch (Technicalexception e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("ADMINISTRACION.SUCURSAL");
            log.setController("GET: api/sucursales/recaudadores/" + recaudadorId);
            log.setCausa(e.getCause() != null ? e.getCause().getCause()+"" : e.getCause()+"");
            log.setMensaje(e.getMessage()+"");
            log.setUsuarioCreacion(usuario.getUsuarioId());
            log.setFechaCreacion(new Date());
            logSistemaService.save(log);
            this.logger.error("This is error", e.getMessage());
            this.logger.error("This is cause", e.getCause() != null ? e.getCause().getCause()+"" : e.getCause());
            response.put("status", false);
            response.put("result", null);
            response.put("message", "Ocurrió un problema en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
            response.put("code", log.getLogSistemaId()+"");
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}