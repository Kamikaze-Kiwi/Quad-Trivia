<script lang="ts">
  import type { Quiz } from "./types/quiz";
  import type { AnswerResult } from "./types/answerResult";
  let quiz: Quiz | undefined;
  let currentQuestionIndex = 0;
  let givenAnswers: string[] = [];
  let answerResults: AnswerResult[] = [];

  function getQuestionsFromApi(event: Event) {
    const form = event.target as HTMLFormElement;
    const formData = new FormData(form);
    const amount = formData.get("amount") as string;

    fetch(`https://quad-trivia-api.onrender.com/questions?amount=${amount}`)
      .then((response) => response.json())
      .then((data: Quiz) => {
        quiz = data;
      });
  }

  function submitAnswers() {
    console.log("Submitted answers:", givenAnswers);

    fetch(`https://quad-trivia-api.onrender.com/api/checkanswers?cacheId=${quiz?.id}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(givenAnswers),
    })
      .then((response) => response.json())
      .then((data: AnswerResult[]) => {
        answerResults = data;
      });
  }
</script>

<main>
  <h1>Quad Trivia</h1>
  <hr style="min-width: 80vw;">

  {#if answerResults && answerResults.length > 0}
    <div>
      <h2>Your Results:</h2>
      <h3>You got {answerResults.filter(result => result.isCorrect).length} out of {quiz?.questions.length} correct!</h3>
      <table>
        <thead>
          <tr>
            <th>Question</th>
            <th>Your Answer</th>
            <th>Correct Answer</th>
            <th>Result</th>
          </tr>
        </thead>
        <tbody>
        {#each quiz?.questions as question, index}
          <tr>
            <td>{@html question.question}</td>
            <td>{@html givenAnswers[index]}</td>
            <td>{@html answerResults[index].actualAnswer}</td>
            <td>{answerResults[index].isCorrect ? '✅' : '❌'}</td>
          </tr>
        {/each}
        </tbody>
      </table>
      <button on:click={() => {
        quiz = undefined;
        currentQuestionIndex = 0;
        givenAnswers = [];
        answerResults = [];
      }}>Take another quiz</button>
    </div>

  {:else if quiz}
    <div>
      <div style="padding-inline: 15vw;">
        <h2 >{@html quiz.questions[currentQuestionIndex].question}</h2>
        <div style="display: flex; flex-wrap: wrap; justify-content: center; gap: 10px; margin: 0 auto;">
          {#each quiz.questions[currentQuestionIndex].answers as answer}
            <button 
              on:click={() => givenAnswers[currentQuestionIndex] = answer} 
              style="width: 40%; min-width: 200px"
              class:chosen={givenAnswers[currentQuestionIndex] === answer}
            >
              {@html answer}
            </button>
          {/each}
        </div>
      </div>

      <button disabled={currentQuestionIndex <= 0} on:click={() => {currentQuestionIndex--}}>&lt;</button>
        Question {currentQuestionIndex + 1} of {quiz.questions.length}
      <button disabled={currentQuestionIndex >= quiz.questions.length - 1} on:click={() => {currentQuestionIndex++}}>&gt;</button>
    </div>

    {#if givenAnswers.length === quiz.questions.length}
      <div>
        <button on:click={submitAnswers}>Submit Answers</button>
      </div>
    {/if}
    
  {:else}
    <div>
      <form on:submit|preventDefault={getQuestionsFromApi}>
        <label for="amount">Number of questions:</label>
        <input min="1" max="50" name="amount" type="number" defaultValue="10" />
        <button type="submit">Generate new quiz</button>
      </form>
    </div>
  {/if}
</main>
