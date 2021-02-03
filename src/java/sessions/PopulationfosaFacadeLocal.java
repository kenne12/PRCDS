/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Annee;
import entities.District;
import entities.Populationfosa;
import entities.Structure;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdel BEIN-INFO
 */
@Local
public interface PopulationfosaFacadeLocal {

    void create(Populationfosa populationfosa);

    void edit(Populationfosa populationfosa);

    void remove(Populationfosa populationfosa);

    Populationfosa find(Object id);

    List<Populationfosa> findAll();

    List<Populationfosa> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    public List<Populationfosa> findByAnnee(int annee);

    public Populationfosa findByStructure(Structure structure) throws Exception;

    List<Populationfosa> find(Structure structure, Annee annee) throws Exception;

    List<Populationfosa> find(District district, Annee annee) throws Exception;

}
