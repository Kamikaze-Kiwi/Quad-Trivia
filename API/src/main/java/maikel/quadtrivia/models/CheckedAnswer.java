package maikel.quadtrivia.models;
/**
 * Model representing the result of checking a user's answer to a trivia question.
 * Includes whether the answer was correct and the actual correct answer.
 */
public class CheckedAnswer {
    public boolean isCorrect;
    public String actualAnswer;

    public CheckedAnswer(boolean isCorrect, String actualAnswer) {
        this.isCorrect = isCorrect;
        this.actualAnswer = actualAnswer;
    }
}
