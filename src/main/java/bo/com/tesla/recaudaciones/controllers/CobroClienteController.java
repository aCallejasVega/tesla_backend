package bo.com.tesla.recaudaciones.controllers;

import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;
import bo.com.tesla.recaudaciones.services.ICobroClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/cobros")
public class CobroClienteController {

    @Autowired
    private ICobroClienteService iCobroClienteService;

    @Transactional
    @PostMapping("/{comprobanteEnUno}/{metodoPagoId}")
    public ResponseEntity<?> postCobrarDeudas(@RequestBody List<ServicioDeudaDto> servicioDeudaDtos,//Debe pasar aqui el nuevo valor de Nombrecliente y NIT
                                              @PathVariable Boolean comprobanteEnUno, //sera de la clase de entidad
                                              @PathVariable Long metodoPagoId,
                                              Authentication authentication) throws Exception {
        Map<String, Object> response = new HashMap<>();
        if(servicioDeudaDtos.size() <= 0 || metodoPagoId == null || metodoPagoId <= 0 ) {
            response.put("status", "false");
            response.put("messege", "Ocurrió un error en el servidor, por favor verifique los parametros de ingreso.");
            response.put("result", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        try {
            iCobroClienteService.postCobrarDeudas(servicioDeudaDtos, comprobanteEnUno, authentication.getName(), metodoPagoId);
            response.put("status", "true");
            response.put("messege", "Se realizó el cobro correctamente");
            response.put("result", "true");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "false");
            response.put("result", null);
            response.put("messege", e.getMessage());//modificar por mensaje
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}

