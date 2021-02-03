/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Region;
import entities.InformationsanitaireRegion;
import entities.Rubriqueinfosanitaire;
import entities.Structure;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdel BEIN-INFO
 */
@Local
public interface InformationsanitaireRegionFacadeLocal {

    void create(InformationsanitaireRegion informationsanitaireRegion);

    void edit(InformationsanitaireRegion informationsanitaireRegion);

    void remove(InformationsanitaireRegion informationsanitaireRegion);

    InformationsanitaireRegion find(Object id);

    List<InformationsanitaireRegion> findAll();

    List<InformationsanitaireRegion> findRange(int[] range);

    int count();
    public Long nextId() throws Exception;

   
    public List<InformationsanitaireRegion> find(Structure structure, Rubriqueinfosanitaire rubriqueinfosanitaire, Region region) throws Exception;

}
