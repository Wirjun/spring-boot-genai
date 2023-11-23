package dae.example.template.entities;

import java.time.LocalDateTime;

public class Message {

    private String message;

    private LocalDateTime localDateTime;

    private Role role;

    public Message(String message, Role role) {
        this.message = message;
        this.role = role;
        this.localDateTime = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public Role getRole() {
        return role;
    }
}
