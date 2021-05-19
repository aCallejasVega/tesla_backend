package bo.com.tesla.facturaciones.computarizada.controllers;

import bo.com.tesla.administracion.entity.EntidadEntity;
import bo.com.tesla.administracion.entity.LogSistemaEntity;
import bo.com.tesla.administracion.entity.SegUsuarioEntity;
import bo.com.tesla.entidades.services.IEntidadService;
import bo.com.tesla.facturaciones.computarizada.dto.*;
import bo.com.tesla.facturaciones.computarizada.services.IAnulacionFacturaService;
import bo.com.tesla.facturaciones.computarizada.services.IFacturaComputarizadaService;
import bo.com.tesla.recaudaciones.services.ITransaccionCobroService;
import bo.com.tesla.security.services.ILogSistemaService;
import bo.com.tesla.security.services.ISegUsuarioService;
import bo.com.tesla.useful.config.BusinesException;
import bo.com.tesla.useful.config.Technicalexception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/facturas")
public class FacturaController {

    private Logger logger = LoggerFactory.getLogger(FacturaController.class);

    @Autowired
    private ILogSistemaService logSistemaService;

    @Autowired
    private ISegUsuarioService segUsuarioService;

    @Autowired
    private IFacturaComputarizadaService facturacionComputarizadaService;

    @Autowired
    private ITransaccionCobroService transaccionCobroService;

    @Autowired
    private IEntidadService entidadService;

    @Autowired
    private IAnulacionFacturaService anulacionFacturaService;

    @PostMapping("/codigoscontroles")
    public ResponseEntity<?> getCodigoControl(@RequestBody CodigoControlDto codigoControlDto,
                                              Authentication authentication)  {

        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            EntidadEntity entidad = this.entidadService.findEntidadByUserId(usuario.getUsuarioId());
            if (entidad == null) {
                throw new BusinesException("El usuario debe pertenecer a una Entidad.");
            }
            ResponseDto responseDto = facturacionComputarizadaService.postCodigoControl(codigoControlDto, entidad.getEntidadId());
            response.put("status", true);
            response.put("message", responseDto.message);
            response.put("result", responseDto.result);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Technicalexception e) {
            LogSistemaEntity log = new LogSistemaEntity();
            log.setModulo("FACTURAS");
            log.setController("api/facturas/codigoscontroles");
            log.setCausa(e.getCause() + "");
            log.setMensaje(e.getMessage() + "");
            log.setUsuarioCreacion(usuario.getUsuarioId());
            log.setFechaCreacion(new Date());
            logSistemaService.save(log);
            this.logger.error("This is error", e.getMessage());
            this.logger.error("This is cause", e.getCause());
            response.put("status", false);
            response.put("result", null);
            response.put("message", "Ocurrió un problema en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
            response.put("code", log.getLogSistemaId() + "");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (BusinesException e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("FACTURAS");
            log.setController("api/facturas/filters");
            log.setMensaje(e.getMessage());
            log.setUsuarioCreacion(usuario.getUsuarioId());
            log.setFechaCreacion(new Date());
            this.logSistemaService.save(log);
            this.logger.error("This is cause", e.getMessage());
            response.put("status", false);
            response.put("message", e.getMessage());
            response.put("code", log.getLogSistemaId()+"");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = {"/entidades/{entidadId}/filters/{page}", "/filters/{page}" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> postListFacturaFilter(@RequestBody FacturaDto facturaDto,
                                                          @PathVariable(name = "entidadId", required = false) Optional<Long> entidadId,
                                                          @PathVariable int page,
                                                          Authentication authentication)  {

        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            ResponseDto responseDto = new ResponseDto();
            if(!entidadId.isPresent()) {
                EntidadEntity entidad = this.entidadService.findEntidadByUserId(usuario.getUsuarioId());
                if(entidad == null) {
                    throw new BusinesException("El usuario debe pertenecer a una Entidad.");
                }
                responseDto = facturacionComputarizadaService.postFacturaLstFilter(entidad.getEntidadId(), page, facturaDto);
            } else {
                responseDto = facturacionComputarizadaService.postFacturaLstFilter(entidadId.get(), page, facturaDto);
            }
            if(responseDto != null) {

                response.put("status", true);
                response.put("message", responseDto.message);
                response.put("result", responseDto.result);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Technicalexception e) {
            LogSistemaEntity log = new LogSistemaEntity();
            log.setModulo("FACTURAS");
            log.setController("api/facturas/filters");
            log.setCausa(e.getCause() + "");
            log.setMensaje(e.getMessage() + "");
            log.setUsuarioCreacion(usuario.getUsuarioId());
            log.setFechaCreacion(new Date());
            logSistemaService.save(log);
            this.logger.error("This is error", e.getMessage());
            this.logger.error("This is cause", e.getCause());
            response.put("status", false);
            response.put("result", null);
            response.put("message", "Ocurrió un problema en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
            response.put("code", log.getLogSistemaId() + "");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (BusinesException e) {
            LogSistemaEntity log=new LogSistemaEntity();
            log.setModulo("FACTURAS");
            log.setController("api/facturas/filters");
            log.setMensaje(e.getMessage());
            log.setUsuarioCreacion(usuario.getUsuarioId());
            log.setFechaCreacion(new Date());
            this.logSistemaService.save(log);
            this.logger.error("This is cause", e.getMessage());
            response.put("status", false);
            response.put("message", e.getMessage());
            response.put("code", log.getLogSistemaId()+"");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/entidades/{entidadId}/reportes/{facturaId}")
    public ResponseEntity<?> getReportFactura(@PathVariable Long entidadId,
                                              @PathVariable Long facturaId,
                                              Authentication authentication)  {

        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {

            ResponseDto responseDto = facturacionComputarizadaService.getFacturaReport(entidadId, facturaId);
            byte[] facturaByteArray = Base64.getDecoder().decode(responseDto.report);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(facturaByteArray.length);
            headers.setContentType(MediaType.parseMediaType("application/pdf" ));
            headers.set("Content-Disposition", "inline; filename=report.pdf" );
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(facturaByteArray, headers, HttpStatus.OK);

        } catch (Technicalexception e) {
            LogSistemaEntity log = new LogSistemaEntity();
            log.setModulo("FACTURAS");
            log.setModulo("FACTURAS");
            log.setController("api/facturas/filters");
            log.setCausa(e.getCause() + "");
            log.setMensaje(e.getMessage() + "");
            log.setUsuarioCreacion(usuario.getUsuarioId());
            log.setFechaCreacion(new Date());
            logSistemaService.save(log);
            this.logger.error("This is error", e.getMessage());
            this.logger.error("This is cause", e.getCause());
            response.put("status", false);
            response.put("result", null);
            response.put("message", "Ocurrió un problema en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
            response.put("code", log.getLogSistemaId() + "");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/entidades/{entidadId}/anulaciones/listas")
    public ResponseEntity<?> postListFacturaAnulacion(@PathVariable Long entidadId,
                                                      @RequestBody AnulacionFacturaLstDto anulacionFacturaLstDto,
                                                        Authentication authentication)  {

        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            Boolean respuesta  = anulacionFacturaService.anularTransaccion(entidadId, anulacionFacturaLstDto, usuario);
            response.put("message", "Se ha realizado la Anulación de la factura.");
            response.put("result", respuesta);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Technicalexception e) {
            LogSistemaEntity log = new LogSistemaEntity();
            log.setModulo("ANULACION FACTURA");
            log.setController("api/anulaciones/listas");
            log.setCausa(e.getCause() + "");
            log.setMensaje(e.getMessage() + "");
            log.setUsuarioCreacion(usuario.getUsuarioId());
            log.setFechaCreacion(new Date());
            logSistemaService.save(log);
            this.logger.error("This is error", e.getMessage());
            this.logger.error("This is cause", e.getCause());
            response.put("status", false);
            response.put("result", null);
            response.put("message", "Ocurrió un problema en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
            response.put("code", log.getLogSistemaId() + "");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/librosventas")
    public ResponseEntity<?> postLibroVentasReport(@RequestBody FacturaDto facturaDto,
                                                   Authentication authentication)  {

        SegUsuarioEntity usuario = this.segUsuarioService.findByLogin(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        try {
            EntidadEntity entidad = this.entidadService.findEntidadByUserId(usuario.getUsuarioId());
            if(entidad == null) {
                throw new Technicalexception("El usuario debe pertenecer a una Entidad.");
            }

            ResponseDto responseDto = facturacionComputarizadaService.getLibroVentasReport(entidad.getEntidadId(), facturaDto);
            byte[] facturaByteArray = Base64.getDecoder().decode(responseDto.report);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(facturaByteArray.length);
            headers.setContentType(MediaType.parseMediaType("application/" + facturaDto.formatFile ));
            headers.set("Content-Disposition", "inline; filename=report." + facturaDto.formatFile );
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(facturaByteArray, headers, HttpStatus.OK);

        } catch (Technicalexception e) {
            LogSistemaEntity log = new LogSistemaEntity();
            log.setModulo("FACTURAS");
            log.setController("api/facturas/librosventas");
            log.setCausa(e.getCause() + "");
            log.setMensaje(e.getMessage() + "");
            log.setUsuarioCreacion(usuario.getUsuarioId());
            log.setFechaCreacion(new Date());
            logSistemaService.save(log);
            this.logger.error("This is error", e.getMessage());
            this.logger.error("This is cause", e.getCause());
            response.put("status", false);
            response.put("result", null);
            response.put("message", "Ocurrió un problema en el servidor, por favor intente la operación más tarde o consulte con su administrador.");
            response.put("code", log.getLogSistemaId() + "");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }







}
