package tn.esprit.manajero.Entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "quiz_schedules")
@Data
public class QuizSchedule {

    @Id
    private String _id;
    private String quizId;
    private LocalDateTime scheduledAt;
    private LocalDateTime reminderAt;
    private LocalDateTime completedAt;
    private String userId;
    private String status;

    public static final String STATUS_SCHEDULED = "SCHEDULED";
    public static final String STATUS_COMPLETED = "COMPLETED";
    // getters and setters

}
