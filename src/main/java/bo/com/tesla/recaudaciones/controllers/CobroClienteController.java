package bo.com.tesla.recaudaciones.controllers;

import bo.com.tesla.recaudaciones.dto.ClienteDto;
import bo.com.tesla.recaudaciones.dto.ServicioDeudaDto;
import bo.com.tesla.recaudaciones.services.ICobroClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/cobros")
public class CobroClienteController {

    @Autowired
    private ICobroClienteService iCobroClienteService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @PostMapping("/{comprobanteEnUno}/{metodoPagoId}")
    public ResponseEntity<?> postCobrarDeudas(@RequestBody ClienteDto clienteDto,
                                              @PathVariable Boolean comprobanteEnUno, //sera de la clase de entidad
                                              @PathVariable Long metodoPagoId,
                                              Authentication authentication) throws Exception {
        Map<String, Object> response = new HashMap<>();
        if(clienteDto == null || clienteDto.getNombreCliente() == null || clienteDto.getNroDocumento() == null || clienteDto.getCodigoCliente() == null) {
            response.put("status", "false");
            response.put("message", "Ocurrió un error en el servidor, por favor verifique selecciona de deudas o datos de clientes");
            response.put("result", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
        if(metodoPagoId == null || metodoPagoId <= 0 || comprobanteEnUno == null) {
            response.put("status", "false");
            response.put("message", "Ocurrió un error en el servidor, por favor verifique parametros");
            response.put("result", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        if(comprobanteEnUno == null) {
            response.put("status", "false");
            response.put("message", "Ocurrió un error en el servidor, por favor verifique parametros");
            response.put("result", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        try {
            iCobroClienteService.postCobrarDeudas(clienteDto, comprobanteEnUno, authentication.getName(), metodoPagoId);
            response.put("status", "true");
            response.put("message", "Se realizó el cobro de las deudas correctamente");
            response.put("result", "true");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "false");
            response.put("result", null);
            response.put("message", "Ocurrió un error en el servidor");
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}

 