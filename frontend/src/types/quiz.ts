export type Quiz = {
  id: number;
  questions: Question[];
};

export type Question = {
  type: "multiple" | "boolean" | string;
  difficulty: "easy" | "medium" | "hard" | string;
  category: string;
  question: string;
  answers: string[];
};