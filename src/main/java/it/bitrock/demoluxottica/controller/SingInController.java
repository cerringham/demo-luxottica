package it.bitrock.demoluxottica.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.bitrock.demoluxottica.dto.RegistrationDTO;
import it.bitrock.demoluxottica.model.Role;
import it.bitrock.demoluxottica.service.SingInService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/sing_in")
@Tag(name = "Sing in Controller", description = "")
@Slf4j
public class SingInController {

    @Autowired
    SingInService singInService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> singIn(@Valid @RequestBody RegistrationDTO registrationDTO){
        registrationDTO.setRole(Role.SUPER_USER);
        log.info("valori: {}, {}", registrationDTO.getName(), registrationDTO.getSurname());
        return singInService.singIn(registrationDTO);
    }

    //Ho fatto sta cafonata perché non mi prendeva il metodo con la RequestBody =), mo funziona
    @PostMapping(value = "/param")
    public ResponseEntity<?> singIn2(@RequestParam String username,
                                     @RequestParam String nome,
                                     @RequestParam String cognome,
                                     @RequestParam String email
                                     ){
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setRole(Role.SUPER_USER);
        registrationDTO.setUsername(username);
        registrationDTO.setName(nome);
        registrationDTO.setSurname(cognome);
        registrationDTO.setEmail(email);
        return singInService.singIn(registrationDTO);
    }

}
