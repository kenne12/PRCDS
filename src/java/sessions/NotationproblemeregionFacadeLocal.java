/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Notationproblemeregion;
import entities.ProblemeRegion;
import entities.Sousrubriquenotationprobleme;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface NotationproblemeregionFacadeLocal {

    void create(Notationproblemeregion notationproblemeregion);

    void edit(Notationproblemeregion notationproblemeregion);

    void remove(Notationproblemeregion notationproblemeregion);

    Notationproblemeregion find(Object id);

    List<Notationproblemeregion> findAll();

    List<Notationproblemeregion> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<Notationproblemeregion> find(ProblemeRegion problemeRegion) throws Exception;

    List<Notationproblemeregion> find(ProblemeRegion problemeRegion, Sousrubriquenotationprobleme sousrubriquenotationprobleme) throws Exception;

}
