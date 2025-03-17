// QuestionLoader.java
package org.vaadin.numerosity.Subsystems;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuestionLoader {

    // String directory = "Database/Bank/AlgebraOne/Easy";
    // String questionId = "q1";  //No need for this here

    private final String AlgebraOneEasy = "Database/Bank/AlgebraOne/Easy";
    private final String AlgebraOneMedium = "Database/Bank/AlgebraOne/Medium";
    private final String AlgebraOneHard = "Database/Bank/AlgebraOne/Hard";
    private final String AlgebraTwoEasy = "Database/Bank/AlgebraTwo/Easy";
    private final String AlgebraTwoMedium = "Database/Bank/AlgebraTwo/Medium";
    private final String AlgebraTwoHard = "Database/Bank/AlgebraTwo/Hard";
    private final String GeometryEasy = "Database/Bank/Geometry/Easy";
    private final String GeometryMedium = "Database/Bank/Geometry/Medium";
    private final String GeometryHard = "Database/Bank/Geometry/Hard";
    private final String PrecalculusEasy = "Database/Bank/Precalculus/Easy";
    private final String PrecalculusMedium = "Database/Bank/Precalculus/Medium";
    private final String PrecalculusHard = "Database/Bank/Precalculus/Hard";
    private final String CalculusEasy = "Database/Bank/Calculus/Easy";
    private final String CalculusMedium = "Database/Bank/Calculus/Medium";
    private final String CalculusHard = "Database/Bank/Calculus/Hard";
    private final String DailyEasy = "Database/Bank/Daily/Easy";
    private final String DailyMedium = "Database/Bank/Daily/Medium";
    private final String DailyHard = "Database/Bank/Daily/Hard";
    private final String DailyChallenge = "Database/Bank/Daily/Challenge";

    private String directory = AlgebraOneEasy; // temporary solution to choose directory.  Make this configurable later
    private final String[] directories = {AlgebraOneEasy, AlgebraOneMedium, AlgebraOneHard, AlgebraTwoEasy, AlgebraTwoMedium,
            AlgebraTwoHard, GeometryEasy, GeometryMedium, GeometryHard, PrecalculusEasy, PrecalculusMedium,
            PrecalculusHard, CalculusEasy, CalculusMedium, CalculusHard, DailyEasy, DailyMedium, DailyHard,
            DailyChallenge};

    private final String[] questions = {"q1", "q2", "q3"}; //Ideally read these from the directory

    //No longer needs LocalDatabaseHandler
    //@Autowired  //Not needed now
    //public QuestionLoader(LocalDatabaseHandler localDb) {
    //    this.localDb = localDb;
    //}

    //These loadAsText and loadAsLatex were moved to a more appropriate class

    // load random question from the database with the known paths
    public String getRandomDirectory() {
        Random random = new Random();
        int randomIndex = random.nextInt(directories.length);
        return directories[randomIndex];
    }

    // load random question
    public String getRandomQuestion() {
        Random random = new Random();
        int randomIndex = random.nextInt(questions.length);
        return questions[randomIndex];
    }

    public String getDirectory() {
        return directory;
    }
}
