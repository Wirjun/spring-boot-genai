package dae.example.template.util;

import java.util.List;

public class CosineSimilarity {

    /**
     * Calculates the cosine similarity between two vectors.
     *
     * @param vectorA the first vector
     * @param vectorB the second vector
     * @return the cosine similarity between the two vectors
     */
    public static double calculate(List<Double> vectorA, List<Double> vectorB) {
        if (vectorA.size() != vectorB.size()) {
            throw new IllegalArgumentException("Vectors must be of equal length.");
        }

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.size(); i++) {
            dotProduct += vectorA.get(i) * vectorB.get(i);
            normA += Math.pow(vectorA.get(i), 2);
            normB += Math.pow(vectorB.get(i), 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

        

}
