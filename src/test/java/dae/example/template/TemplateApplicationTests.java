package dae.example.template;

import dae.example.template.entities.Data;
import dae.example.template.services.DataService;
import dae.example.template.util.CosineSimilarity;
import dae.example.template.util.OpenAIConnector;
import dae.example.template.util.VectorParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class TemplateApplicationTests {

    @Autowired
    DataService dataService;

    @Test
    public void test() {
        String question = "Wann war das Schulsilvester?";
        HashMap<String, String> map = new HashMap<>();
        String embeddingQuestion = OpenAIConnector.createEmbedding(question);
        List<Double> vectorQuestion = VectorParser.parseVector(embeddingQuestion);
        HashMap<Long, Double> results = new HashMap<>();
        for (Data data : dataService.findAll()) {
            List<Double> dataVector = VectorParser.parseVector(data.getVector());
            double calculate = CosineSimilarity.calculate(dataVector, vectorQuestion);
            results.put(data.getId(), calculate);
            map.put("assistant", data.getSummary());
        }
       //String answer = OpenAIConnector.answer(question, map);
    }
}
