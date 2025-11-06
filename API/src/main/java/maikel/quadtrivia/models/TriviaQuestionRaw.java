package maikel.quadtrivia.models;

import java.util.List;

/**
 * Model representing the raw trivia question data as received from the Open Trivia DB API, part of TriviaResponse. 
 * @see TriviaResponse
 */
public class TriviaQuestionRaw {
    public String type;
    public String difficulty;
    public String category;
    public String question;
    public String correct_answer;
    public List<String> incorrect_answers;
}