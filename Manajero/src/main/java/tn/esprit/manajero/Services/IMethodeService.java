package tn.esprit.manajero.Services;

import tn.esprit.manajero.Entities.Methode;

import java.util.List;

public interface IMethodeService {
    Methode addMethode(Methode methode);

    List<Methode> getAllMethods();

    Methode getMethodeById(Long idMethode);

    void deleteMethode(Long idMethode);

    public Methode updateMethode(Long id, Methode methode);
}
