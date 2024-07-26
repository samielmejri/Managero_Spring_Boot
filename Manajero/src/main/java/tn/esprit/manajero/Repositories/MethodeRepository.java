package tn.esprit.manajero.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.manajero.Entities.Methode;

@Repository

public interface MethodeRepository extends MongoRepository<Methode,Long> {
}
