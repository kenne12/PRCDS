/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.FaiblesseRegion;
import entities.FfomRegion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface FaiblesseRegionFacadeLocal {

    void create(FaiblesseRegion faiblesseRegion);

    void edit(FaiblesseRegion faiblesseRegion);

    void remove(FaiblesseRegion faiblesseRegion);

    FaiblesseRegion find(Object id);

    List<FaiblesseRegion> findAll();

    List<FaiblesseRegion> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<FaiblesseRegion> findByFfom(FfomRegion ffom) throws Exception;

}
