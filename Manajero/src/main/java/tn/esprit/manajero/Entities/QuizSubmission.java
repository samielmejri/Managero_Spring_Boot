package tn.esprit.manajero.Entities;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "QuizSubmission ")
@Data
public class QuizSubmission {
    @Id
    private String _id;

    private String userId;
    private String quizId;
    private int score;

}
