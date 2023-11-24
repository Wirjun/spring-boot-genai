package dae.example.template.util;

import org.apache.commons.text.StringEscapeUtils;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class OpenAIConnector {

    private static final String SUMMARY_API_URL = "https://prototype101.openai.azure.com/openai/deployments/example1/chat/completions?api-version=2023-07-01-preview";

    private static final String EMBEDDING_API_URL = "https://prototype101.openai.azure.com/openai/deployments/Codigators/embeddings?api-version=2023-07-01-preview";

    private static final String API_KEY = "298dda2a50db47608e2436939d8d62ca";

    public static String createSummary(String content) {
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
                    "{\"role\": \"user\", \"content\": \"" + StringEscapeUtils.escapeJava(content) + "\"}" +
                    "], \"temperature\": 0.7, \"top_p\": 0.95, \"frequency_penalty\": 0, \"presence_penalty\": 0, \"max_tokens\": 800, \"stop\": null}";

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            StringBuilder response = new StringBuilder();
            try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // Parse JSON and extract the message content
            JSONObject jsonResponse = new JSONObject(response.toString());

            return jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while generating summary";
        }
    }

    public static String createEmbedding(String summary) {
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
            try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
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

    public static String answer(String question, Map<String, String> context) {
        try {
            URL url = new URL(SUMMARY_API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("api-key", API_KEY);
            con.setRequestProperty("Ocp-Apim-Subscription-Key", API_KEY);
            con.setDoOutput(true);

            // Build the messages JSON array
            JSONArray messages = new JSONArray();
            messages.put(new JSONObject()
                    .put("role", "system")
                    .put("content", "You will answer the question according to the knowledge provided. If the word \"unknown\" is provided, you will answer that no information was found in the knowledge data. When information from the context is available, you will answer the question to the user!"));

            // Add context messages
            for (Map.Entry<String, String> entry : context.entrySet()) {
                messages.put(new JSONObject()
                        .put("role", entry.getKey())
                        .put("content", entry.getValue()));
            }

            // Add the user question
            messages.put(new JSONObject()
                    .put("role", "user")
                    .put("content", question));

            // Create the request body
            JSONObject requestBody = new JSONObject();
            requestBody.put("messages", messages);
            requestBody.put("temperature", 0.7);
            requestBody.put("top_p", 0.95);
            requestBody.put("frequency_penalty", 0);
            requestBody.put("presence_penalty", 0);
            requestBody.put("max_tokens", 800);
            requestBody.put("stop", JSONObject.NULL);

            // Send the request
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read the response
            StringBuilder response = new StringBuilder();
            try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // Parse JSON and extract the answer
            JSONObject jsonResponse = new JSONObject(response.toString());

            return jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while retrieving answer";
        }
    }

}
