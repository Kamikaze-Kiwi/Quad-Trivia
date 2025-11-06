package maikel.quadtrivia.models;

import java.util.Collections;
import java.util.List;

/**
 * Model representing a trivia question with its details and possible answers.
 */
public class TriviaQuestion {
    public String type;
    public String difficulty;
    public String category;
    public String question;
    public List<String> answers;

    /**
     * Constructs a TriviaQuestion from a raw question data model.
     * @param questionRaw The raw trivia question data, as received from the Open Trivia DB API.
     */
    public TriviaQuestion(TriviaQuestionRaw questionRaw) {
        this.type = questionRaw.type;
        this.difficulty = questionRaw.difficulty;
        this.category = questionRaw.category;
        this.question = questionRaw.question;
        this.answers = questionRaw.incorrect_answers;
        this.answers.add(questionRaw.correct_answer);
        Collections.shuffle(answers);
    }
}