/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.FfomRegion;
import entities.MenaceRegion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface MenaceRegionFacadeLocal {

    void create(MenaceRegion menaceRegion);

    void edit(MenaceRegion menaceRegion);

    void remove(MenaceRegion menaceRegion);

    MenaceRegion find(Object id);

    List<MenaceRegion> findAll();

    List<MenaceRegion> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<MenaceRegion> findByFfom(FfomRegion ffom) throws Exception;

}
