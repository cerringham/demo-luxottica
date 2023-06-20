package it.bitrock.demoluxottica.mapper;

import it.bitrock.demoluxottica.exception.InvalidPatientException;
import it.bitrock.demoluxottica.model.avro.PatientRecord;
import it.bitrock.demoluxottica.models.PatientEntity;
import it.bitrock.demoluxottica.models.dto.AddPatientDTO;
import it.bitrock.demoluxottica.models.dto.AddressDTO;
import it.bitrock.demoluxottica.models.dto.PatientDTO;
import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.r4.model.Address;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointSystem;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    List<PatientDTO> patientToPatientDTO(List<Patient> patients);

    default PatientRecord patientToPatientRecord(Patient patient) {
        PatientRecord patientRecord = new PatientRecord();
        patientRecord.setId(patient.getIdPart());
        HumanName name = patient.getName().get(0);
        patientRecord.setFirstName(name.getGivenAsSingleString());
        patientRecord.setLastName(name.getFamily());
        patientRecord.setBirthDate(dateToLocalDate(patient.getBirthDate()).toEpochDay());
        Optional<String> phoneOptional = patient.getTelecom().stream().filter(t -> t.getSystem().equals(ContactPointSystem.PHONE)).map(ContactPoint::getValue).findFirst();
        String phoneNumber = phoneOptional.orElse(null);
        patientRecord.setPhoneNumber(phoneNumber);
        Optional<String> emailOptional = patient.getTelecom().stream().filter(t -> t.getSystem().equals(ContactPointSystem.EMAIL)).map(ContactPoint::getValue).findFirst();
        String email = emailOptional.orElse(null);
        patientRecord.setEmail(email);
        Address address = patient.getAddress().get(0);
        patientRecord.setAddressCountry(address.getCountry());
        patientRecord.setAddressDistrict(address.getDistrict());
        patientRecord.setAddressCity(address.getCity());
        patientRecord.setAddressStreet(address.getLine().get(0).getValue());
        patientRecord.setAddressNumber(address.getLine().get(1).getValue());
        return patientRecord;
    }

    default UserRepresentation patientToUserRepresentation(Patient patient) {
        HumanName patientName = patient.getName().get(0);
        UserRepresentation user = new UserRepresentation();
        patient.getTelecom().stream()
                .filter(t -> t.getSystem().equals(ContactPointSystem.EMAIL))
                .map(ContactPoint::getValue)
                .findFirst()
                .ifPresentOrElse(user::setEmail, () -> {throw new InvalidPatientException("patient has no email");});
        user.setId(patient.getIdPart());
        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(patientName.getGivenAsSingleString());
        user.setCredentials(List.of(credentials));
        user.setFirstName(patientName.getGivenAsSingleString());
        user.setLastName(patientName.getFamily());
        user.setUsername(patientName.getGivenAsSingleString() + patientName.getFamily());
        user.setEnabled(patient.getActive());
        return user;
    }

    default PatientDTO patientToPatientDTO(Patient patient) {
        String id = patient.getIdPart();
        HumanName name = patient.getName().get(0);
        String firstName = name.getGivenAsSingleString();
        String lastName = name.getFamily();
        LocalDate birthDate = dateToLocalDate(patient.getBirthDate());
        Optional<String> phoneOptional = patient.getTelecom().stream().filter(t -> t.getSystem().equals(ContactPointSystem.PHONE)).map(ContactPoint::getValue).findFirst();
        String phoneNumber = phoneOptional.orElse(null);
        Optional<String> emailOptional = patient.getTelecom().stream().filter(t -> t.getSystem().equals(ContactPointSystem.EMAIL)).map(ContactPoint::getValue).findFirst();
        String email = emailOptional.orElse(null);
        AddressDTO address = addressToAddressDTO(patient.getAddress());
        return new PatientDTO(id, firstName, lastName, birthDate, phoneNumber, email, address);
    }

    default Patient addPatientDTOToPatient(AddPatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.addName()
                .addGiven(patientDTO.firstName())
                .setFamily(patientDTO.lastName());
        patient.setBirthDate(localDateToDate(patientDTO.birthDate()));
        if (StringUtils.isNotBlank(patientDTO.phoneNumber())) {
            patient.addTelecom()
                    .setSystem(ContactPointSystem.PHONE)
                    .setValue(patientDTO.phoneNumber());
        }
        if (StringUtils.isNotBlank(patientDTO.email())) {
            patient.addTelecom()
                    .setSystem(ContactPointSystem.EMAIL)
                    .setValue(patientDTO.email());
        }
        patient.addAddress(addressDTOToAddress(patientDTO.address()));
        return patient;
    }

    default PatientEntity patientToPatientEntity(Patient patient) {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patient.getIdPart());
        HumanName name = patient.getName().get(0);
        patientEntity.setFirstName(name.getGivenAsSingleString());
        patientEntity.setLastName(name.getFamily());
        patientEntity.setBirthDate(dateToLocalDate(patient.getBirthDate()));
        Optional<String> phoneOptional = patient.getTelecom().stream().filter(t -> t.getSystem().equals(ContactPointSystem.PHONE)).map(ContactPoint::getValue).findFirst();
        String phoneNumber = phoneOptional.orElse(null);
        patientEntity.setPhoneNumber(phoneNumber);
        Optional<String> emailOptional = patient.getTelecom().stream().filter(t -> t.getSystem().equals(ContactPointSystem.EMAIL)).map(ContactPoint::getValue).findFirst();
        String email = emailOptional.orElse(null);
        patientEntity.setEmail(email);
        Address address = patient.getAddress().get(0);
        patientEntity.setAddressCountry(address.getCountry());
        patientEntity.setAddressDistrict(address.getDistrict());
        patientEntity.setAddressCity(address.getCity());
        patientEntity.setAddressStreet(address.getLine().get(0).getValue());
        patientEntity.setAddressNumber(address.getLine().get(1).getValue());
        patientEntity.setActive(patient.getActive());
        return patientEntity;
    }

    default AddressDTO addressToAddressDTO(List<Address> addressList) {
        Address address = addressList.get(0);
        return new AddressDTO(
                address.getCountry(),
                address.getDistrict(),
                address.getCity(),
                address.getLine().get(0).getValue(),
                address.getLine().get(1).getValue());
    }

    default Address addressDTOToAddress(AddressDTO addressDTO) {
        return new Address()
                .setCountry(addressDTO.country())
                .setDistrict(addressDTO.district())
                .setCity(addressDTO.city())
                .addLine(addressDTO.street())
                .addLine(addressDTO.number());
    }

    default LocalDate dateToLocalDate(Date date) {
        return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    default Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().toInstant(ZoneOffset.UTC));
    }

}
