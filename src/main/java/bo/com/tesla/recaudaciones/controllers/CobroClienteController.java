package bo.com.tesla.recaudaciones.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bo.com.tesla.recaudaciones.dto.ClienteDto;
import bo.com.tesla.recaudaciones.services.ICobroClienteService;

@RestController
@RequestMapping("api/cobros")
public class CobroClienteController {

    @Autowired
    private ICobroClienteService iCobroClienteService;

    //@Transactional
    @PostMapping("/{comprobanteEnUno}/{metodoPagoId}")
    public ResponseEntity<?> postCobrarDeudas(@RequestBody ClienteDto clienteDto,
                                              @PathVariable Boolean comprobanteEnUno, //sera de la clase de entidad
                                              @PathVariable Long metodoPagoId,
                                              Authentication authentication) throws Exception {
    	System.out.println("****************postCobrarDeudas*******************");
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

        try {
            iCobroClienteService.postCobrarDeudas(clienteDto, comprobanteEnUno, authentication.getName(), metodoPagoId);
            response.put("status", "true");
            response.put("message", "Se realizó el cobro de las deudas correctamente");
            response.put("result", "true");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            response.put("status", "false");
            response.put("result", null);
            response.put("message", "Ocurrió un error en el servidor");
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}

 