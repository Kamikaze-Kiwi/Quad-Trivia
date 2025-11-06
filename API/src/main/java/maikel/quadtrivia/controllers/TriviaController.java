package maikel.quadtrivia.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import maikel.quadtrivia.models.CheckedAnswer;
import maikel.quadtrivia.models.TriviaQuiz;
import maikel.quadtrivia.models.TriviaResponse;
import maikel.quadtrivia.services.TriviaCacheService;
import maikel.quadtrivia.services.TriviaService;

@CrossOrigin(origins = "http://localhost:5137")
@RestController
public class TriviaController {
    @Autowired
    private TriviaService triviaService;

    @Autowired
    private TriviaCacheService triviaCache;

    @GetMapping("/questions")
    public ResponseEntity<TriviaQuiz> GetQuiz(@RequestParam Integer amount) {
        if (amount == null || amount <= 0 || amount > 50) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            TriviaResponse triviaResponse = triviaService.GetQuestions(amount);
            int cacheId = triviaCache.SaveToCache(triviaResponse.results);
            TriviaQuiz quiz = new TriviaQuiz(cacheId, triviaResponse.results);
            return ResponseEntity.ok(quiz);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/checkanswers")
    public ResponseEntity<List<CheckedAnswer>> postMethodName(@RequestBody List<String> answers, @RequestParam int cacheId) {
        try {
            List<String> correctAnswers = triviaCache.GetCorrectAnswersFromCache(cacheId);
            List<CheckedAnswer> checkedAnswers = new ArrayList<>();

            for (int i = 0; i < answers.size(); i++){
                checkedAnswers.add(new CheckedAnswer(
                    Objects.requireNonNullElse(answers.get(i), "").equals(correctAnswers.get(i)), 
                    correctAnswers.get(i)));
            }

            return ResponseEntity.ok(checkedAnswers);
            
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);   
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}