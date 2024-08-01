package tn.esprit.manajero.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizWrapper {

    private String _id;
    private String title;
    private String category;
    private String userId; // Add userId field
    private int numQ;



}
