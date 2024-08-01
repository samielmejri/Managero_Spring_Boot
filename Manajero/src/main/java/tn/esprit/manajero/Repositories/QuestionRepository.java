package tn.esprit.manajero.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.manajero.Entities.Question;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {

}
