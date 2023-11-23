package dae.example.template.entities;

public enum Role {

    BOT("Chatbot Charly"),
    CUSTOMER("Unknown User");


    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
