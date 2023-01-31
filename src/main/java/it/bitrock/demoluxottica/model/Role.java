package it.bitrock.demoluxottica.model;

public enum Role {
    ADMIN_ORG("Admin Organization"),
    ADMIN_SHOP("Admin Shop"),
    WORKER("Worker"),
    DOCTOR("Doctor"),
    TECH("Technician"),
    USER("User"),
    SUPPORT_USER("Support User"),
    SUPER_USER("Super User");

    String type;

    private Role(String type){
        this.type = type;
    }
}
