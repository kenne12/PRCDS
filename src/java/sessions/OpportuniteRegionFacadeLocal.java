/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.FfomRegion;
import entities.OpportuniteRegion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface OpportuniteRegionFacadeLocal {

    void create(OpportuniteRegion opportuniteRegion);

    void edit(OpportuniteRegion opportuniteRegion);

    void remove(OpportuniteRegion opportuniteRegion);

    OpportuniteRegion find(Object id);

    List<OpportuniteRegion> findAll();

    List<OpportuniteRegion> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<OpportuniteRegion> findByFfom(FfomRegion ffom) throws Exception;

}
