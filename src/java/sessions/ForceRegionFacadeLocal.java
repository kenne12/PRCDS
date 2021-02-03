/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.FfomRegion;
import entities.ForceRegion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface ForceRegionFacadeLocal {

    void create(ForceRegion forceRegion);

    void edit(ForceRegion forceRegion);

    void remove(ForceRegion forceRegion);

    ForceRegion find(Object id);

    List<ForceRegion> findAll();

    List<ForceRegion> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<ForceRegion> findByFfom(FfomRegion ffom) throws Exception;

}
