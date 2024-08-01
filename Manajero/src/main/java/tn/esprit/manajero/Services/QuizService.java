package tn.esprit.manajero.Services;

import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.esprit.manajero.Entities.*;
import tn.esprit.manajero.Repositories.QuestionDao;
import tn.esprit.manajero.Repositories.QuizDao;
import tn.esprit.manajero.Repositories.QuizStatisticsRepository;
import tn.esprit.manajero.Repositories.QuizSubmissionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    private static final String STATIC_USER_ID = "etudiant";

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    @Autowired
    QuizSubmissionRepository quizSubmissionRepository;
    @Autowired
    QuizStatisticsRepository quizStatisticsRepository;
    private final String baseUrl = "http://localhost:9098/quiz"; // Base URL for the quiz service API
    private RestTemplate restTemplate;

    public ResponseEntity<String> createQuiz(String category, Integer numQ, String title) {
        List<Question> questionList = questionDao.findRandomQuestionsByCategory(category, numQ);
        if (questionList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No questions found for the specified category");
        }
        String userId = STATIC_USER_ID; // Use the static user ID

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questionList);
        quiz.setCategory(category);
        quiz.setNumQ(numQ);
        quiz.setUserId(userId); // Set the user ID

        quizDao.save(quiz);

        return ResponseEntity.status(HttpStatus.CREATED).body("Quiz created successfully");
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(String _id) {
        Optional<Quiz> optionalQuiz = quizDao.findById(_id);
        if (optionalQuiz.isPresent()) {
            Quiz quiz = optionalQuiz.get();
            List<Question> questions = quiz.getQuestions();
            List<QuestionWrapper> questionWrappers = new ArrayList<>();
            for (Question q : questions) {
                QuestionWrapper questionWrapper = new QuestionWrapper(q.get_id(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
                questionWrappers.add(questionWrapper);
            }
            return ResponseEntity.ok(questionWrappers);
        } else {
            throw new EntityNotFoundException("Quiz not found with ID: " + _id);
        }
    }

    public ResponseEntity<Integer> calculateResult(String _id, List<Response> responses) {
        Quiz quiz = quizDao.findById(_id).orElseThrow(() -> new EntityNotFoundException("Quiz not found with ID: " + _id));
        List<Question> questions = quiz.getQuestions();
        int score = 0;
        for (int i = 0; i < responses.size(); i++) {
            if (responses.get(i).getResponse().equals(questions.get(i).getRightAnswer())) {
                score++;
            }
        }
        return ResponseEntity.ok(score);
    }

//    public ResponseEntity<List<QuizWrapper>> getQuizzesByCategory(String category) {
//        List<Quiz> quizzes = quizDao.findByCategory(category);
//        List<QuizWrapper> quizWrappers = new ArrayList<>();
//        for (Quiz quiz : quizzes) {
//            QuizWrapper quizWrapper = new QuizWrapper(quiz.get_id(), quiz.getTitle(), quiz.getCategory());
//            quizWrappers.add(quizWrapper);
//        }
//        return ResponseEntity.ok(quizWrappers);
//    }

    public ResponseEntity<List<QuizWrapper>> getAllQuizzes() {
        List<Quiz> quizzes = quizDao.findAll();
        List<QuizWrapper> quizWrappers = new ArrayList<>();
        for (Quiz quiz : quizzes) {
            QuizWrapper quizWrapper = new QuizWrapper(quiz.get_id(), quiz.getTitle(), quiz.getCategory(), quiz.getUserId(), quiz.getNumQ());
            quizWrappers.add(quizWrapper);
        }
        return ResponseEntity.ok(quizWrappers);
    }

    public Quiz updateQuiz(String _id, Quiz updatedQuiz) {
        Optional<Quiz> quizOptional = quizDao.findById(_id);
        if (quizOptional.isPresent()) {
            Quiz existingQuiz = quizOptional.get();
            existingQuiz.setTitle(updatedQuiz.getTitle());
            existingQuiz.setCategory(updatedQuiz.getCategory());
            existingQuiz.setNumQ(updatedQuiz.getNumQ());
            return quizDao.save(existingQuiz);
        } else {
            throw new RuntimeException("Quiz not found with id: " + _id);
        }
    }

    public ResponseEntity<Integer> submitQuiz(String userId, String _id, List<Response> responses) {
        // Calculate score using calculateResult method
        ResponseEntity<Integer> resultResponse = calculateResult(_id, responses);
        int score = resultResponse.getBody();

        // Save quiz submission along with user ID and score
        saveQuizSubmission(userId, _id, score);

        return ResponseEntity.ok(score);
    }

    public void saveQuizSubmission(String userId, String _id, int score) {
        // Create a new instance of QuizSubmission
        QuizSubmission quizSubmission = new QuizSubmission();
        quizSubmission.setUserId(userId);
        quizSubmission.setQuizId(_id);
        quizSubmission.setScore(score);

        // Save the quiz submission to your database using your repository
        quizSubmissionRepository.save(quizSubmission);
    }

    public Quiz getQuizById(String _id) {
        Optional<Quiz> optionalQuiz = quizDao.findById(_id);
        return optionalQuiz.orElse(null);

    }

    public QuizService(QuizSubmissionRepository quizSubmissionRepository) {
        this.quizSubmissionRepository = quizSubmissionRepository;
    }

    public ResponseEntity<Double> getAverageScore() {
        List<QuizSubmission> submissions = quizSubmissionRepository.findAll();
        if (submissions.isEmpty()) {
            return ResponseEntity.badRequest().body(0.0); // Return 0 if no submissions found
        }
        double totalScore = submissions.stream().mapToInt(QuizSubmission::getScore).sum();
        double averageScore = totalScore / submissions.size();
        return ResponseEntity.ok(averageScore);
    }

    // Method to get highest score among all quiz submissions
    public ResponseEntity<Integer> getHighestScore() {
        List<QuizSubmission> submissions = quizSubmissionRepository.findAll();
        if (submissions.isEmpty()) {
            return ResponseEntity.badRequest().body(0); // Return 0 if no submissions found
        }
        int highestScore = submissions.stream().mapToInt(QuizSubmission::getScore).max().orElse(0);
        return ResponseEntity.ok(highestScore);
    }

    // Method to get lowest score among all quiz submissions
    public ResponseEntity<Integer> getLowestScore() {
        List<QuizSubmission> submissions = quizSubmissionRepository.findAll();
        if (submissions.isEmpty()) {
            return ResponseEntity.badRequest().body(0); // Return 0 if no submissions found
        }
        int lowestScore = submissions.stream().mapToInt(QuizSubmission::getScore).min().orElse(0);
        return ResponseEntity.ok(lowestScore);
    }

    // Method to get total number of quiz submissions
    public ResponseEntity<Long> getTotalSubmissions() {
        long totalSubmissions = quizSubmissionRepository.count();
        return ResponseEntity.ok(totalSubmissions);
    }

    public QuizStatistics calculateQuizStatistics(String quizId) {
        List<QuizSubmission> submissions = quizSubmissionRepository.findByQuizId(quizId);

        if (submissions.isEmpty()) {
            return null; // Handle case with no submissions
        }

        int minScore = submissions.stream().mapToInt(QuizSubmission::getScore).min().getAsInt();
        int maxScore = submissions.stream().mapToInt(QuizSubmission::getScore).max().getAsInt();
        double averageScore = submissions.stream().mapToInt(QuizSubmission::getScore).average().orElse(0.0);

        return new QuizStatistics(minScore, maxScore, averageScore);
    }
    public QuizStatistics getQuizStatistics(String quizId) {
        String url = baseUrl + "/" + quizId + "/statistics";
        return restTemplate.getForObject(url, QuizStatistics.class);
    }


}

