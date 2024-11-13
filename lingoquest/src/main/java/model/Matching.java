package lingo_quest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
/**
 * Represents a matching question where the user is asked to match a word with its translation or definition.
 * This class extends the abstract Question class and implements specific behaviors for matching questions.
 */
public class Matching extends Question {
    String language;
    //private HashMap<Word,Word> answerMap;
    private ArrayList<Word> choices;
    private ArrayList<Word> shuffledChoices;
    private Word userAnswer;
   
    /**
     * Constructs a Matching question with a specific language and a list of possible answer choices.
     * 
     * @param language The language for which the question is being created.
     * @param answerChoices A list of Word objects that are the possible choices for the question.
     */

    public Matching(String language, ArrayList<Word> answerChoices) {
        super(language);
        this.choices = answerChoices;
        shuffledChoices = shuffle(answerChoices);
    }
   
    private ArrayList<Word> shuffle(ArrayList<Word> answerChoices) {
        shuffledChoices = new ArrayList<Word>();
        for(Word w : answerChoices) {
            shuffledChoices.add(w);
        }
        Collections.shuffle(shuffledChoices);
        return shuffledChoices;
    }


    public void setUserAnswer(Word userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean isMatchCorrect(Word aWord, User user) {
        if(aWord.equals(userAnswer)) {
            user.getUserDictionary().getWordByUUID(aWord.getWordUUID()).wordPresented(true);
            return true;
        } else {
            user.getUserDictionary().getWordByUUID(aWord.getWordUUID()).wordPresented(false);
            return false;
        }
    }

   
    /**
     * Provides a formatted string representing the question and its choices.
     * 
     * @return A string that lists the word to match along with all possible choices.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Match the English word " + words.get(answerIndex).getEnglishVersion() + " to one of the following words\n");
        for(int i = 0; i < words.size();i++) {
            result.append(i+1).append(". ").append(words.get(i).getWordinLanguage()).append("\n");
        }
        //Collections.shuffle(words);
        /*for(int i = 0; i < words.size();i++) {
            //result.append(i+5).append(". ").append(words.get(i).getEnglishVersion()).append("\n");
            
        }*/
        return result.toString();
    }
    /**
     * This method is supposed to determine the correctness of the user's overall interaction in a more complex scenario.
     * It is currently overridden to always return true, but should ideally contain logic to verify user answers.
     * 
     * @param user The user object to which the game progress is attributed.
     * @return Always returns true in the current implementation.
     */
   
}
