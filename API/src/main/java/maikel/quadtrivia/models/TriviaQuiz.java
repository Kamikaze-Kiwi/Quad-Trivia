package maikel.quadtrivia.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Model representing a trivia quiz, containing one or more trivia questions.
 */
public class TriviaQuiz {
    /**
     * Unique identifier for the quiz. Used to store and retrieve the correct answers.
     */
    public int id;
    public List<TriviaQuestion> questions = new ArrayList<>();

    /**
     * Constructs a TriviaQuiz from a list of raw questions.
     * @param id Unique identifier for the quiz.
     * @param questionsRaw List of raw trivia question data, as received from the Open Trivia DB API.
     */
    public TriviaQuiz(int id, List<TriviaQuestionRaw> questionsRaw) {
        this.id = id;

        for (TriviaQuestionRaw questionRaw : questionsRaw) {
            this.questions.add(new TriviaQuestion(questionRaw));
        }
    }
}
