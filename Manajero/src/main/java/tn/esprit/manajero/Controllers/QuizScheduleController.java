package tn.esprit.manajero.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.manajero.Entities.QuizSchedule;
import tn.esprit.manajero.Services.QuizScheduleService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/quiz-schedules")
public class QuizScheduleController {

    @Autowired
    private QuizScheduleService quizScheduleService;


    @PostMapping("/quizschedule")
    public ResponseEntity<QuizSchedule> scheduleQuiz(@RequestParam String quizId, @RequestParam String scheduledAt) {
        LocalDateTime scheduledDateTime = LocalDateTime.parse(scheduledAt);

        // Call service method to schedule the quiz
        QuizSchedule scheduledQuiz = quizScheduleService.scheduleQuiz(quizId, scheduledDateTime);

        // Return the scheduled quiz and a 201 Created status code
        return new ResponseEntity<>(scheduledQuiz, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<QuizSchedule> getAllScheduledQuizzes() {
        return quizScheduleService.getAllScheduledQuizzes();
    }
}