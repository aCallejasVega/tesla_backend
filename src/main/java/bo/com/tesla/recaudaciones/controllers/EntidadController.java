package bo.com.tesla.recaudaciones.controllers;

import bo.com.tesla.recaudaciones.dto.ClienteDto;
import bo.com.tesla.recaudaciones.dto.DominioDto;
import bo.com.tesla.recaudaciones.dto.EntidadDto;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;
import bo.com.tesla.recaudaciones.services.IDeudaClienteRService;
import bo.com.tesla.recaudaciones.services.IEntidadRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/entidades")
public class EntidadController {

    @Autowired
    private IDeudaClienteRService iDeudaClienteRService;

    @Autowired
    private IEntidadRService iEntidadRService;

    @GetMapping("/{entidadId}/clientes/{datoCliente}")
    public ResponseEntity<?> getAllClientesByEntidadId(@PathVariable Long entidadId,
                                                       @PathVariable String datoCliente) {
        Map<String, Object> response = new HashMap<>();

        if(entidadId <= 0 || entidadId == null) {
            response.put("status", "false");
            response.put("messege", "La entidad es obligatoria");
            response.put("result", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        if(datoCliente == null || datoCliente.length() == 0 ) {
            response.put("status", "false");
            response.put("messege", "El dato del cliente es obligatorio");
            response.put("result", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        try{
            Optional<List<ClienteDto>> optionalClienteDtos = iDeudaClienteRService.getByEntidadAndClienteLike(entidadId, datoCliente);
            if(optionalClienteDtos.isPresent()) {
                response.put("status", "true");
                response.put("result", optionalClienteDtos.get());
                response.put("messege", "Encontrado");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("mensaje", "No hay clientes");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch(Exception e) {
            response.put("status", "false");
            response.put("result", null);
            response.put("messege", e.getMessage());//modificar por mensaje
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{entidadId}/clientes/{codigoCliente}/deudas")
    public ResponseEntity<?> getDeudasByCliente(@PathVariable Long entidadId,
                                                        @PathVariable String codigoCliente) {
        Map<String, Object> response = new HashMap<>();

        if(entidadId <= 0 || entidadId == null) {
            response.put("status", "false");
            response.put("messege", "La entidad es obligatoria");
            response.put("result", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        if(codigoCliente == null || codigoCliente.length() == 0 ) {
            response.put("status", "false");
            response.put("messege", "El código del cliente es obligatorio");
            response.put("result", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        try{
            List<ServicioDeudaDto> servicioDeudaDtos = iDeudaClienteRService.getDeudasByCliente(entidadId, codigoCliente);
            if(!servicioDeudaDtos.isEmpty()) {
                response.put("status", "true");
                response.put("result", servicioDeudaDtos);
                response.put("messege", "Encontrado");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("status", "false");
            response.put("result", null);
            response.put("messege", "No encotrado");//modificar por mensaje
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.put("status", "false");
            response.put("result", null);
            response.put("messege", e.getMessage());//modificar por mensaje
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



    /////////////////////

    @GetMapping("/tipos")
    public ResponseEntity<?> getEntidadesByTipo(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<List<DominioDto>> optionalDominioDtos = iEntidadRService.getTipoEntidadByRecaudadorA(authentication.getName());
            if(optionalDominioDtos.isPresent()) {
                response.put("status", "true");
                response.put("result", optionalDominioDtos.get());
                response.put("messege", "Tipos Entidades encontrados");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("status", "false");
            response.put("result", null);
            response.put("messege", "No existe Tipos Entidades encontrados");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(Exception e) {
            response.put("status", "false");
            response.put("result", null);
            response.put("messege", e.getMessage());//modificar por mensaje
            return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tipos/{tipoEntidadId}")
    public ResponseEntity<?> getEntidadesByTipoEntidad(@PathVariable Long tipoEntidadId, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        if(tipoEntidadId <= 0 || tipoEntidadId == null) {
            response.put("status", "false");
            response.put("messege", "El tipo de entidad es obligatoria");
            response.put("result", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        try {
            Optional<List<EntidadDto>> optionalEntidadDtos =  iEntidadRService.getByRecaudadoraIdAndTipoEntidadIdA(tipoEntidadId,authentication.getName());
            if(optionalEntidadDtos.isPresent()){
                response.put("status", "true");
                response.put("result", optionalEntidadDtos.get());
                response.put("messege", "Entidades por Tipo Entidad encontrado");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("status", "false");
            response.put("result", null);
            response.put("messege", "No existe");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(Exception e) {
            response.put("status", "false");
            response.put("result", null);
            response.put("messege", e.getMessage());//modificar por mensaje
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getEntidades(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<List<EntidadDto>> optionalEntidadDtos =  iEntidadRService.getAllByRecaudadoraIdA(authentication.getName());
            if(optionalEntidadDtos.isPresent()){
                response.put("status", "true");
                response.put("result", optionalEntidadDtos.get());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("status", "false");
            response.put("result", null);
            response.put("messege", "No encontrado");//modificar por mensaje
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(Exception e) {
            response.put("status", "false");
            response.put("result", null);
            response.put("messege", e.getMessage());//modificar por mensaje
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
