/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.ActiviteRegion;
import entities.ActiviteStructureRegion;
import entities.Annee;
import entities.ProblemeRegion;
import entities.Structure;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface ActiviteStructureRegionFacadeLocal {

    void create(ActiviteStructureRegion activiteStructureRegion);

    void edit(ActiviteStructureRegion activiteStructureRegion);

    void remove(ActiviteStructureRegion activiteStructureRegion);

    ActiviteStructureRegion find(Object id);

    List<ActiviteStructureRegion> findAll();

    List<ActiviteStructureRegion> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<ActiviteStructureRegion> find(ProblemeRegion problemeRegion) throws Exception;

    List<ActiviteStructureRegion> find(ActiviteRegion activiteRegion) throws Exception;

    List<ActiviteStructureRegion> find(Structure structure, Annee annee) throws Exception;

    List<ActiviteStructureRegion> find(Structure structure, Boolean etat) throws Exception;

    List<ActiviteStructureRegion> find(Structure structure, ActiviteRegion activiteRegion) throws Exception;

}
