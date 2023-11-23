package dae.example.template.util;

import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Named
public class Formatters {

    public String formatLocalDateTime(final LocalDateTime localDateTime) {
        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd MMM uuuu HH:mm");
        return dTF.format(localDateTime);
    }
}
