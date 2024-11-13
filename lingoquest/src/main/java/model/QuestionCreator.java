package lingo_quest;
/**
 * A utility class to create different types of questions based on the complexity of a word within a lesson.
 */

public class QuestionCreator {
    /**
     * Constructor for creating an instance of QuestionCreator.
     * This constructor does not perform any operations.
     */

    // Constructor
    public QuestionCreator() {
    }
    /**
     * Creates a question based on the complexity of a word in the given lesson. The type of question
     * generated depends on the point value of the word, where different thresholds lead to different
     * types of questions being created.
     *
     * @param lesson The lesson from which a word is chosen to form a question.
     * @return A Question object, the type of which is determined by the word's point value.
     * @throws IllegalArgumentException If the word's point value does not match any expected thresholds.
     */
    public Question createQuestion(Lesson lesson) {
        Word word = lesson.getRandomWord();  // Retrieve a random word from the lesson.
        // attempt to fix issue with practicelowunderstanding
        if(word == null) {
            System.out.println("No further questions");
            return null;
        }
        int wordPoint = word.getPoints();    // Get the point value of the word.
        
        // Determine the type of question to create based on the word's point value.
        if (wordPoint >= 300) {
            // High complexity word generates a Matching question.
            return new Matching(lesson.getLanguageName(), lesson.getWords(word, 4));
        } else if (wordPoint >= 200) {
            // Medium complexity word generates a FillInTheBlank question.
            return new FillInTheBlank(lesson.getLanguageName(), word);
        } else if (wordPoint == 100) {
            // Lower complexity word generates a MultipleChoice question.
            return new MultipleChoice(lesson.getLanguageName(), lesson.getWords(word, 4), word);
        } else if (wordPoint <= 0) {
            // No points or negative points generate a TrueOrFalse question.
            Word anotherWord;
            do {
                anotherWord = lesson.getRandomWord();  // Ensure the second word is different from the first.
            } while (anotherWord.equals(word));
            return new TrueOrFalse(lesson.getLanguageName(), word, anotherWord);
        }
        
        // Throw an exception if the word's point value does not match any of the defined thresholds.
        throw new IllegalArgumentException("Unsupported wordPoint value: " + wordPoint);
    }
}
