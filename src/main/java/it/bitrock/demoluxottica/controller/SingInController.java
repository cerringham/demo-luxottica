package it.bitrock.demoluxottica.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.bitrock.demoluxottica.dto.RegistrazioneDTO;
import it.bitrock.demoluxottica.model.Role;
import it.bitrock.demoluxottica.service.SingInService;
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
    public ResponseEntity<?> singIn(@RequestBody RegistrazioneDTO registrazioneDTO){
        registrazioneDTO.setRuolo(Role.SUPER_USER);
        log.info("valori: {}, {}", registrazioneDTO.getNome(), registrazioneDTO.getCognome());
        return singInService.singIn(registrazioneDTO);
    }

    @PostMapping(value = "/param")
    public ResponseEntity<?> singIn2(@RequestParam String username,
                                     @RequestParam String nome,
                                     @RequestParam String cognome,
                                     @RequestParam String email
                                     ){
        RegistrazioneDTO registrazioneDTO = new RegistrazioneDTO();
        registrazioneDTO.setRuolo(Role.SUPER_USER);
        registrazioneDTO.setUsername(username);
        registrazioneDTO.setNome(nome);
        registrazioneDTO.setCognome(cognome);
        registrazioneDTO.setEmail(email);
        return singInService.singIn(registrazioneDTO);
    }

}
