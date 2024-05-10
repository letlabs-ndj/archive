package org.travel.packmanagement.api;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.travel.packmanagement.model.Ville;
import org.travel.packmanagement.service.VilleService;

@RestController
@RequestMapping(value = "/villes")
@AllArgsConstructor
public class VilleController {
    private VilleService villeService;

    @GetMapping(value = "/all")
    public ResponseEntity<Iterable<Ville>> getAllVilles(){
        return new ResponseEntity<>(
                villeService.getAllVilles(),
                HttpStatus.OK
        );
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Ville> saveVille(@RequestBody Ville ville){
        return new ResponseEntity<>(
                villeService.saveVille(ville),
                HttpStatus.CREATED
        );
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<Ville> getVilleById(@PathVariable("id") Long idVille){
        return new ResponseEntity<>(
                villeService.getVilleById(idVille),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/nom/{nomVille}")
    public ResponseEntity<Iterable<Ville>> getVilleByName(@PathVariable("nomVille") String nomVille){
        return new ResponseEntity<>(
                villeService.getVilleByName(nomVille),
                HttpStatus.OK
        );
    }

    @PutMapping(value = "/update/{idVille}")
    public ResponseEntity<Ville> updateVille(
            @RequestBody Ville villeUpdate,
            @PathVariable("idVille") Long idVille){

        Ville existingVille = villeService.getVilleById(idVille);
        existingVille.setNom(villeUpdate.getNom());
        existingVille.setPays(villeUpdate.getPays());
        return new ResponseEntity<>(
                villeService.updateVille(existingVille),
                HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping(value = "delete/{idVille}")
    public void deleteVilleById(@PathVariable("idVille") Long idVille){
        villeService.deleteVilleById(idVille);
    }
}
