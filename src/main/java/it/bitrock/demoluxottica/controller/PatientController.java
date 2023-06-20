package it.bitrock.demoluxottica.controller;

import it.bitrock.demoluxottica.mapper.PatientMapper;
import it.bitrock.demoluxottica.models.dto.AddPatientDTO;
import it.bitrock.demoluxottica.models.dto.PatientDTO;
import it.bitrock.demoluxottica.service.PatientService;
import org.hl7.fhir.r4.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/patient")
public class PatientController {
    private static final Logger log = LoggerFactory.getLogger(PatientController.class);

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    public PatientController(PatientService patientService, PatientMapper patientMapper) {
        this.patientService = patientService;
        this.patientMapper = patientMapper;
    }

    @PostMapping
    public PatientDTO add(@RequestBody AddPatientDTO patientDTO) {
        Patient patient = patientMapper.addPatientDTOToPatient(patientDTO);
        Patient savedPatient = patientService.add(patient);
        return patientMapper.patientToPatientDTO(savedPatient);
    }

    @GetMapping
    public List<PatientDTO> findAll() {
        List<Patient> patients = patientService.findAll();
        return patientMapper.patientToPatientDTO(patients);
    }

    @GetMapping("/{id}")
    public PatientDTO findById(@PathVariable String id) {
        Patient patient = patientService.findById(id);
        return patientMapper.patientToPatientDTO(patient);
    }

    @PutMapping("/{id}")
    public PatientDTO update(@PathVariable String id, @RequestBody AddPatientDTO patientDTO) {
        Patient patient = patientMapper.addPatientDTOToPatient(patientDTO);
        patient.setId(id);
        Patient updatedPatient = patientService.update(patient);
        return patientMapper.patientToPatientDTO(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        patientService.delete(id);
    }
}
