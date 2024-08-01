package tn.esprit.manajero.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.manajero.Entities.Question;

import java.util.List;

@Repository
public interface QuestionDao extends MongoRepository<Question, String> {
    List<Question> findByCategory(String category);

    @Query("{ 'category' : ?0 }")
    List<Question> findRandomQuestionsByCategory(String category,int numQ);

}
