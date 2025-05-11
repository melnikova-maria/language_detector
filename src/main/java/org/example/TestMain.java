package org.example;

import org.example.cybozu.CommandLineInterface;

import java.io.IOException;
import java.util.List;

public class TestMain {
    public static void main(String[] args) {
        try {
            CommandLineInterface Command_Line_Interface = new CommandLineInterface();
            LanguageDetector detector = Command_Line_Interface.makeDetector();

            List<TestData> testData = TestData.getTestSamples();

            int correct = 0;
            int total = testData.size();

            System.out.println("Running language detection tests...");
            System.out.println("=================================");

            for (TestData data : testData) {
                List<DetectedLanguage> result = detector.getProbabilities(data.getText());
                DetectedLanguage best = result.get(0);
                boolean isCorrect = best.toString_cls().equals(data.getExpectedLanguage());

                if (isCorrect) {
                    correct++;
                }

                System.out.printf("Text: '%s'\n", data.getText());
                System.out.printf("Expected: %s, Detected: %s - %s\n\n",
                        data.getExpectedLanguage(),
                        best.toString_cls(),
                        isCorrect ? "✓" : "✗");
            }

            double accuracy = (double) correct / total * 100;
            System.out.println("=================================");
            System.out.printf("Test results: %d/%d correct (%.2f%% accuracy)\n",
                    correct, total, accuracy);

        } catch (IOException e) {
            System.err.println("Failed to initialize language detector: " + e.getMessage());
        }
    }
}