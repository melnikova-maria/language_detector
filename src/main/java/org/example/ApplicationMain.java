package org.example;

import java.io.IOException;
import java.util.List;
import org.example.cybozu.CommandLineInterface;


public class ApplicationMain
{
    public static void main( String[] args ) throws IOException {
        CommandLineInterface Command_Line_Interface = new CommandLineInterface();
        LanguageDetector detector = Command_Line_Interface.makeDetector();

        List<DetectedLanguage> result = detector.getProbabilities("Ciao, amo i gatti.");
        DetectedLanguage best = result.get(0);
        System.out.println(best);
    }
}
