/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.ActiviteRegion;
import entities.Annee;
import entities.ChronogrammeRegion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface ChronogrammeRegionFacadeLocal {

    void create(ChronogrammeRegion chronogrammeRegion);

    void edit(ChronogrammeRegion chronogrammeRegion);

    void remove(ChronogrammeRegion chronogrammeRegion);

    ChronogrammeRegion find(Object id);

    List<ChronogrammeRegion> findAll();

    List<ChronogrammeRegion> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<ChronogrammeRegion> findByActivite(ActiviteRegion activite) throws Exception;

    List<ChronogrammeRegion> findByActivite(ActiviteRegion activite, Annee annee) throws Exception;

}
