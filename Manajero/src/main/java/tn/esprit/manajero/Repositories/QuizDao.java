package tn.esprit.manajero.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.manajero.Entities.Quiz;

import java.util.List;

public interface QuizDao extends MongoRepository<Quiz, String> {
    List<Quiz> findByCategory(String category);

}
