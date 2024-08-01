package tn.esprit.manajero.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.manajero.Entities.QuizStatistics;

@Repository
public interface QuizStatisticsRepository extends MongoRepository<QuizStatistics, String> {
    // You can define custom query methods here if needed
}