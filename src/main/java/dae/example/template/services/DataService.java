package dae.example.template.services;

import dae.example.template.entities.Data;
import dae.example.template.repos.DataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
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

            String jsonInputString = "{\"input\": \"" + summary + "\"}";

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while generating embedding";
        }
    }


}
