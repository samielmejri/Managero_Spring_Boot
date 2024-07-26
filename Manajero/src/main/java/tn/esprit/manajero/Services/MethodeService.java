package tn.esprit.manajero.Services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.manajero.Entities.Methode;
import tn.esprit.manajero.Repositories.MethodeRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MethodeService implements IMethodeService {
    MethodeRepository methodeRepository;
    @Override
    public Methode addMethode(Methode methode) {
        return methodeRepository.save(methode);


    }
    @Override
    public List<Methode> getAllMethods() {
        return (List<Methode>) methodeRepository.findAll();
    }
    @Override
    public Methode getMethodeById(Long idMethode) {
        return methodeRepository.findById(idMethode).get();
    }
    @Override
    public void deleteMethode(Long idMethode) {
        methodeRepository.deleteById(idMethode);
    }

    public Methode updateMethode(Long id, Methode newMethode) {
        return methodeRepository.findById(id)
                .map(methode -> {
                    // Update all fields as needed
                    if (newMethode.getIntroduction() != null) {
                        methode.setIntroduction(newMethode.getIntroduction());
                    }
                    if (newMethode.getAdvantages() != null) {
                        methode.setAdvantages(newMethode.getAdvantages());
                    }
                    if (newMethode.getWhat() != null) {
                        methode.setWhat(newMethode.getWhat());
                    }
                    if (newMethode.getHow() != null) {
                        methode.setHow(newMethode.getHow());
                    }
                    if (newMethode.getWhatif() != null) {
                        methode.setWhatif(newMethode.getWhatif());
                    }
                    if (newMethode.getConclusion() != null) {
                        methode.setConclusion(newMethode.getConclusion());
                    }
                    // Add other fields here
                    return methodeRepository.save(methode);
                })
                .orElse(null);
    }

}
