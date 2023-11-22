public class CosineSimilarity {

    /**
     * Calculates the cosine similarity between two vectors.
     *
     * @param vectorA the first vector
     * @param vectorB the second vector
     * @return the cosine similarity between the two vectors
     */
    public static double calculate(double[] vectorA, double[] vectorB) {
        if (vectorA.length != vectorB.length) {
            throw new IllegalArgumentException("Vectors must be of equal length.");
        }

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

        

}
