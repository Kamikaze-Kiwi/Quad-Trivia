package maikel.quadtrivia;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import maikel.quadtrivia.services.*;
import maikel.quadtrivia.models.*;
import maikel.quadtrivia.controllers.*;

@SpringBootTest
class QuadtriviaApplicationTests {
	// Mock TriviaResponse for testing purposes
	private final TriviaResponse triviaResponse = new TriviaResponse(){
		{
			response_code = 0;
			results = java.util.Arrays.asList(
				new TriviaQuestionRaw(){
					{
						category = "Science & Nature";
						type = "multiple";
						difficulty = "easy";
						question = "What is the chemical symbol for the element Oxygen?";
						correct_answer = "O";
						incorrect_answers = java.util.Arrays.asList("Ox", "Og", "On");
					}
				},
				new TriviaQuestionRaw(){
					{
						category = "Geography";
						type = "multiple";
						difficulty = "medium";
						question = "Which country has the longest coastline in the world?";
						correct_answer = "Canada";
						incorrect_answers = java.util.Arrays.asList("Russia", "Indonesia", "Australia");
					}
				}
			);
		}
	};

	@Test
	void contextLoads() {
	}

	@Test
	void CacheReturnsCorrectValues() {
		TriviaCacheService cacheService = new TriviaCacheService();
		int cacheId = cacheService.SaveToCache(triviaResponse.results);
		List<String> correctAnswers = cacheService.GetCorrectAnswersFromCache(cacheId);
		assert(correctAnswers.size() == 2);
		assert(correctAnswers.get(0).equals("O"));
		assert(correctAnswers.get(1).equals("Canada"));
	}

	@Test
	void CacheIsProperlyDisposedWhenRead() {
		TriviaCacheService cacheService = new TriviaCacheService();
		int cacheId = cacheService.SaveToCache(triviaResponse.results);
		cacheService.GetCorrectAnswersFromCache(cacheId);

		boolean exceptionThrown = false;
		try {
			cacheService.GetCorrectAnswersFromCache(cacheId);
		} catch (java.util.NoSuchElementException e) {
			exceptionThrown = true;
		}
		assert(exceptionThrown);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, -1, 51, 100})
	void QuestionApiDoesNotAllowInvalidAmount(int amount) {
		TriviaController controller = new TriviaController();
		ResponseEntity<TriviaQuiz> response = controller.GetQuiz(amount);
		assert(response.getStatusCode() == HttpStatus.BAD_REQUEST);
	}
}
