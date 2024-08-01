package tn.esprit.manajero.Services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.manajero.Entities.Question;
import tn.esprit.manajero.Repositories.QuestionDao;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionDao questionDao;

    public List<Question> getAllQuestions() {
        try {
            return questionDao.findAll();
        } catch (Exception e) {
        }
        return null;
    }

    public List<Question> getQuestionsByCategory(String category) {
        try {
            return questionDao.findByCategory(category);
        } catch (Exception e) {
        }
        return null;
    }

    public void addQuestion(Question question) {
        try {
            questionDao.save(question);
        } catch (Exception e) {
        }
    }

    public Question updateQuestion(String _id, Question updatedQuestion) {
        Optional<Question> questionOptional = questionDao.findById(_id);
        if (questionOptional.isPresent()) {
            Question existingQuestion = questionOptional.get();
            existingQuestion.setQuestionTitle(updatedQuestion.getQuestionTitle());
            existingQuestion.setOption1(updatedQuestion.getOption1());
            existingQuestion.setOption2(updatedQuestion.getOption2());
            existingQuestion.setOption3(updatedQuestion.getOption3());
            existingQuestion.setOption4(updatedQuestion.getOption4());
            existingQuestion.setRightAnswer(updatedQuestion.getRightAnswer());
            existingQuestion.setDifficultyLevel(updatedQuestion.getDifficultyLevel());
            existingQuestion.setCategory(updatedQuestion.getCategory());
            return questionDao.save(existingQuestion);
        } else {
            throw new RuntimeException("Question not found with id: " + _id);
        }
    }
    public void deleteQuestion(String _id) {
        if (!questionDao.existsById(_id)) {
            throw new EntityNotFoundException("Question with ID " + _id + " not found");
        }
        try {
            questionDao.deleteById(_id);
        } catch (Exception e) {
        }
    }

    public List<Question> getAllQuestion() {
        return questionDao.findAll();
    }



}

