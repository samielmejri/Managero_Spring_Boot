package tn.esprit.manajero.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.manajero.Entities.QuizSchedule;
import tn.esprit.manajero.Repositories.QuizDao;
import tn.esprit.manajero.Repositories.QuizScheduleRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizScheduleService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuizScheduleRepository quizScheduleRepository;
    @Autowired
    private static final String STATIC_USER_ID = "etudiant";
    public static final String STATUS_SCHEDULED = "SCHEDULED";
    public static final String STATUS_COMPLETED = "COMPLETED";

    public QuizSchedule scheduleQuiz(String quizId, LocalDateTime scheduledAt) {
        String userId = STATIC_USER_ID; // Use the static user ID

        QuizSchedule quizSchedule = new QuizSchedule();
        quizSchedule.setQuizId(quizId);
        quizSchedule.setScheduledAt(scheduledAt);
        quizSchedule.setUserId(userId);
        quizSchedule.setStatus(QuizSchedule.STATUS_SCHEDULED);

        QuizSchedule savedQuizSchedule = quizScheduleRepository.save(quizSchedule);

        // Schedule a reminder for the user
        LocalDateTime reminderAt = scheduledAt.minus(Duration.ofHours(1));
        scheduleReminder();

        return savedQuizSchedule;
    }

    // Helper method to schedule a reminder
    @Scheduled(cron = "0 0 * * * ?")
    public void scheduleReminder() {
        // Iterate through scheduled quizzes
        List<QuizSchedule> scheduledQuizzes = quizScheduleRepository.findByStatus(STATUS_SCHEDULED);
        for (QuizSchedule quizSchedule : scheduledQuizzes) {
            LocalDateTime now = LocalDateTime.now();

            // Check if scheduled time has passed
            if (now.isAfter(quizSchedule.getScheduledAt())) {
                // Update status to Completed
                quizSchedule.setStatus(STATUS_COMPLETED);
                quizScheduleRepository.save(quizSchedule);


            }
        }
    }

    public List<QuizSchedule> getAllScheduledQuizzes() {
        return quizScheduleRepository.findAll();
    }
}