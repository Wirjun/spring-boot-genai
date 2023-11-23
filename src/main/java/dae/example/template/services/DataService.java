package dae.example.template.services;

import dae.example.template.entities.Data;
import dae.example.template.repos.DataRepo;
import dae.example.template.util.CosineSimilarity;
import dae.example.template.util.OpenAIConnector;
import dae.example.template.util.VectorParser;
import org.apache.commons.text.StringEscapeUtils;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DataService {

    @Autowired
    private DataRepo expansionRepo;

    public List<Data> findAll() {
        return expansionRepo.findAll();
    }

    @Transactional
    public Data saveOrUpdate(Data data){
        return expansionRepo.save(data);
    }

    public Data findById(final Long id) {
        return expansionRepo.findById(id).orElseThrow();
    }

    public String predictAnswer(final String input) {
        String embeddingQuestion = OpenAIConnector.createEmbedding(input);
        List<Double> vectorQuestion = VectorParser.parseVector(embeddingQuestion);
        HashMap<Long, Double> results = new HashMap<>();
        for (Data data : findAll()) {
            List<Double> dataVector = VectorParser.parseVector(data.getVector());
            double calculate = CosineSimilarity.calculate(dataVector, vectorQuestion);
            results.put(data.getId(), calculate);
        }

        //Find best suited entry
        Optional<Map.Entry<Long, Double>> min = results.entrySet().stream().max(Map.Entry.comparingByValue());
        Data data = findById(min.get().getKey());

        String answer = OpenAIConnector.answer(input, Map.of("system", data.getSummary()));
        return answer;
    }
}
