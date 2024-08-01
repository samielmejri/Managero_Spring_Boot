package tn.esprit.manajero.Entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "QuizStatistics")
@Data
public class QuizStatistics {
    @Id
    private String _id;

    private String quizId;
    private double averageScore;
    private int maxScore;
    private int minScore;
    public QuizStatistics(int minScore, int maxScore, double averageScore) {
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.averageScore = averageScore;
    }
}
