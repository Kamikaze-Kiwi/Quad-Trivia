package maikel.quadtrivia.models;

import java.util.List;

/**
 * Model representing the response from the Open Trivia DB API.
 */
public class TriviaResponse {
    public int response_code;
    public List<TriviaQuestionRaw> results;
}