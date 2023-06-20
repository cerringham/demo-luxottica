package it.bitrock.demoluxottica.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;

@Entity
public class PatientEntity {
    @Id
    private String id;

    @Column(nullable = false)
    @Size(max = 100)
    private String firstName;

    @Column(nullable = false)
    @Size(max = 100)
    private String lastName;

    @Column(nullable = false)
    private LocalDate birthDate;

    private String phoneNumber;

    private String email;

    private String addressCountry;

    private String addressDistrict;

    private String addressCity;

    private String addressStreet;

    private String addressNumber;

    @Column(nullable = false)
    private boolean active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getAddressDistrict() {
        return addressDistrict;
    }

    public void setAddressDistrict(String addressDistrict) {
        this.addressDistrict = addressDistrict;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PatientEntity that = (PatientEntity) o;

        return new EqualsBuilder()
                .append(active, that.active)
                .append(id, that.id)
                .append(firstName, that.firstName)
                .append(lastName, that.lastName)
                .append(birthDate, that.birthDate)
                .append(phoneNumber, that.phoneNumber)
                .append(email, that.email)
                .append(addressCountry, that.addressCountry)
                .append(addressDistrict, that.addressDistrict)
                .append(addressCity, that.addressCity)
                .append(addressStreet, that.addressStreet)
                .append(addressNumber, that.addressNumber)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id).append(firstName).append(lastName).append(birthDate).append(phoneNumber).append(email)
                .append(addressCountry).append(addressDistrict).append(addressCity).append(addressStreet)
                .append(addressNumber).append(active).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("birthDate", birthDate)
                .append("phoneNumber", phoneNumber)
                .append("email", email)
                .append("addressCountry", addressCountry)
                .append("addressDistrict", addressDistrict)
                .append("addressCity", addressCity)
                .append("addressStreet", addressStreet)
                .append("addressNumber", addressNumber)
                .append("isActive", active)
                .toString();
    }
}
