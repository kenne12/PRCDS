/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Annee;
import entities.CibleRegion;
import entities.IndicateurRegion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface CibleRegionFacadeLocal {

    void create(CibleRegion cibleRegion);

    void edit(CibleRegion cibleRegion);

    void remove(CibleRegion cibleRegion);

    CibleRegion find(Object id);

    List<CibleRegion> findAll();

    List<CibleRegion> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<CibleRegion> find(IndicateurRegion indicateurRegion, Annee annee) throws Exception;

    List<CibleRegion> find(IndicateurRegion indicateurRegion) throws Exception;

}
