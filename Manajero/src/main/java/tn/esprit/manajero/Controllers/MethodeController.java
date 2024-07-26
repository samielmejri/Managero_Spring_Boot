package tn.esprit.manajero.Controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.manajero.Entities.Methode;
import tn.esprit.manajero.Services.IMethodeService;

import java.util.List;

@RestController
@RequestMapping("/methodes")
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class MethodeController {
    private IMethodeService methodeService;

    @PostMapping("/add")
    public Methode addingMethode(@RequestBody Methode methode){
        return methodeService.addMethode(methode);
    }

    @GetMapping("/getAll")
    public List<Methode> gettingAllMethods(){
        return methodeService.getAllMethods();
    }

    @GetMapping("/get/{idMethode}")
    public Methode gettingMethode(@PathVariable("idMethode") Long idMethode){
        return methodeService.getMethodeById(idMethode);
    }

    @DeleteMapping("/delete/{idMethode}")
    public void deletingMethode(@PathVariable("idMethode") Long idMethode){
        methodeService.deleteMethode(idMethode);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Methode> updateMethode(@PathVariable("id") Long id, @RequestBody Methode methode) {
        Methode updatedMethode = methodeService.updateMethode(id, methode);
        if (updatedMethode != null) {
            return ResponseEntity.ok(updatedMethode);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

