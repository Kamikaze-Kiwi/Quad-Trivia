package maikel.quadtrivia.services;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import maikel.quadtrivia.models.TriviaQuestionRaw;

import org.springframework.stereotype.Component;

/**
 * Service for caching trivia quiz correct answers temporarily.
 */
@Component
public class TriviaCacheService {
    private final Cache<Integer, List<String>> cache = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).build();
    private static final AtomicInteger idCounter = new AtomicInteger();

    /**
     * Saves the correct answers of a trivia quiz to the cache.
     * @param questions List of raw trivia question data, as received from the Open Trivia DB API.
     * @return Unique identifier for the cached correct answers. This is used to retrieve them later.
     */
    public int SaveToCache(List<TriviaQuestionRaw> questions){
        List<String> correctAnswers = questions.stream().map(q -> q.correct_answer).collect(Collectors.toList());
        int cacheId = idCounter.incrementAndGet();
        cache.put(cacheId, correctAnswers);
        return cacheId;
    }

    /**
     * Retrieves and removes the correct answers of a trivia quiz from the cache.
     * @param cacheId Unique identifier for the cached correct answers.
     * @return List of correct answers corresponding to the provided cacheId.
     * @throws NoSuchElementException if no cached answers are found for the provided cacheId.
     */
    public List<String> GetCorrectAnswersFromCache(int cacheId) throws NoSuchElementException {
        List<String> correctAnswers = cache.getIfPresent(cacheId);
        if (correctAnswers == null) {
            throw new NoSuchElementException("No cached answers found for the provided cacheId: " + cacheId);
        }
        cache.invalidate(cacheId);
        return correctAnswers;
    }
}