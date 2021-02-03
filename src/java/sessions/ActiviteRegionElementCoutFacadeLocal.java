/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.ActiviteRegion;
import entities.ActiviteRegionElementCout;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface ActiviteRegionElementCoutFacadeLocal {

    void create(ActiviteRegionElementCout activiteRegionElementCout);

    void edit(ActiviteRegionElementCout activiteRegionElementCout);

    void remove(ActiviteRegionElementCout activiteRegionElementCout);

    ActiviteRegionElementCout find(Object id);

    List<ActiviteRegionElementCout> findAll();

    List<ActiviteRegionElementCout> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<ActiviteRegionElementCout> findByActivite(ActiviteRegion activiteRegion) throws Exception;

}
