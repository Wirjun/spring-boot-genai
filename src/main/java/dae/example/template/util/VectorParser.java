package dae.example.template.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VectorParser {

    public static List<Double> parseVector(String vectorString) {
        // Remove the brackets and split the string by commas
        String[] numberStrings = vectorString.replace("[", "").replace("]", "").split(",");

        // Convert each string in the array to a double
        return Arrays.stream(numberStrings)
                .map(String::trim)  // Remove any leading or trailing whitespace
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }
}
