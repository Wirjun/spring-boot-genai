package dae.example.template.services;

import dae.example.template.entities.Data;
import dae.example.template.repos.DataRepo;
import org.apache.commons.text.StringEscapeUtils;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DataService {

    private static final String SUMMARY_API_URL = "https://prototype101.openai.azure.com/openai/deployments/example1/chat/completions?api-version=2023-07-01-preview";
    private static final String EMBEDDING_API_URL = "https://prototype101.openai.azure.com/openai/deployments/TeamTiGital/embeddings?api-version=2023-07-01-preview";
    private static final String API_KEY = "298dda2a50db47608e2436939d8d62ca";

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

    public String createSummary(String content) {
        try {
            URL url = new URL(SUMMARY_API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("api-key", API_KEY);
            con.setRequestProperty("Ocp-Apim-Subscription-Key", API_KEY);
            con.setDoOutput(true);

            String jsonInputString = "{\"messages\": [" +
                    "{\"role\": \"system\", \"content\": \"You will generate a bulletpoint list of the information you will be provided. You will always respond with the bullet points and no other statements. You are only allowed to generate information from the input provided.\"}," +
                    "{\"role\": \"user\", \"content\": \"" + content + "\"}" +
                    "], \"temperature\": 0.7, \"top_p\": 0.95, \"frequency_penalty\": 0, \"presence_penalty\": 0, \"max_tokens\": 800, \"stop\": null}";

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            StringBuilder response = new StringBuilder();
            try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // Parse JSON and extract the message content
            JSONObject jsonResponse = new JSONObject(response.toString());
            String summary = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");

            return summary;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while generating summary";
        }
    }

    public String createEmbedding(String summary) {
        try {
            URL url = new URL(EMBEDDING_API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("api-key", API_KEY);
            con.setRequestProperty("Ocp-Apim-Subscription-Key", API_KEY);
            con.setDoOutput(true);

            String jsonInputString = "{\"input\": \"" + StringEscapeUtils.escapeJava(summary) + "\"}";

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            StringBuilder response = new StringBuilder();
            try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // Parse JSON and extract the embedding
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray embeddingArray = jsonResponse.getJSONArray("data").getJSONObject(0).getJSONArray("embedding");

            // Convert the JSONArray to a string representation of a vector
            StringBuilder embeddingVector = new StringBuilder("[");
            for (int i = 0; i < embeddingArray.length(); i++) {
                embeddingVector.append(embeddingArray.getDouble(i));
                if (i < embeddingArray.length() - 1) {
                    embeddingVector.append(", ");
                }
            }
            embeddingVector.append("]");

            return embeddingVector.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while generating embedding";
        }
    }


}
