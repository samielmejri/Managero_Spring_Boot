package tn.esprit.manajero.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@Document(collection = "Quiz")
public class Quiz {

    @Id
    private String _id;
    private String title;
    private String category;
    private String userId;
    private int numQ;
    @DBRef
    @JsonIgnore
    private List<Question> questions;
}