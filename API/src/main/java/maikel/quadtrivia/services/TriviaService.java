package maikel.quadtrivia.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import maikel.quadtrivia.models.TriviaResponse;

/**
 * Service for fetching trivia questions from the Open Trivia DB API.
 */
@Component
public class TriviaService {
    private HttpClient client = HttpClient.newHttpClient();
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Fetches trivia questions from the Open Trivia DB API.
     * @param amount Number of trivia questions to fetch.
     * @return TriviaResponse containing the fetched trivia questions.
     */
    public TriviaResponse GetQuestions(Integer amount) throws IOException, InterruptedException{
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://opentdb.com/api.php?amount=" + amount))
            .GET()
            .build();

        HttpResponse<InputStream> response = client.send(request, BodyHandlers.ofInputStream());

        return mapper.readValue(response.body(), TriviaResponse.class);
    }
}
