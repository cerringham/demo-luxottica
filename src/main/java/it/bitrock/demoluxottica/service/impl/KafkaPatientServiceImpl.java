package it.bitrock.demoluxottica.service.impl;

import it.bitrock.demoluxottica.config.KafkaConfiguration;
import it.bitrock.demoluxottica.mapper.PatientMapper;
import it.bitrock.demoluxottica.model.avro.PatientRecord;
import it.bitrock.demoluxottica.service.KafkaPatientService;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaPatientServiceImpl implements KafkaPatientService {
    private final KafkaTemplate<String, PatientRecord> patientTemplate;
    private final PatientMapper patientMapper;

    public KafkaPatientServiceImpl(KafkaTemplate<String, PatientRecord> patientTemplate, PatientMapper patientMapper) {
        this.patientTemplate = patientTemplate;
        this.patientMapper = patientMapper;
    }

    @Override
    public void patientAdded(Patient patient) {
        PatientRecord patientRecord = patientMapper.patientToPatientRecord(patient);
        sendRecord(KafkaConfiguration.ADDED_TOPIC, patientRecord.getId().toString(), patientRecord);
    }

    @Override
    public void patientUpdated(Patient patient) {
        PatientRecord patientRecord = patientMapper.patientToPatientRecord(patient);
        sendRecord(KafkaConfiguration.UPDATED_TOPIC, patientRecord.getId().toString(), patientRecord);

    }

    @Override
    public void patientRemoved(String id) {
        sendRecord(KafkaConfiguration.REMOVED_TOPIC, id, null);
    }

    private void sendRecord(String topic, String id, PatientRecord record) {
        patientTemplate.send(topic, id, record);
    }
}
