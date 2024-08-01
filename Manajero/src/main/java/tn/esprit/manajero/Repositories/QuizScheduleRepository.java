package tn.esprit.manajero.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.manajero.Entities.QuizSchedule;

import java.util.List;

@Repository
public interface QuizScheduleRepository extends MongoRepository<QuizSchedule, String> {

    List<QuizSchedule> findByStatus(String status);

}
