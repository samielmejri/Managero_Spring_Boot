package tn.esprit.manajero.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.manajero.Entities.Quiz;

@Repository
public interface QuizRepository extends MongoRepository<Quiz, String> {

}
