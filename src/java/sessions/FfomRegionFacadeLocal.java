/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.FfomRegion;
import entities.Pilier;
import entities.Region;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface FfomRegionFacadeLocal {

    void create(FfomRegion ffomRegion);

    void edit(FfomRegion ffomRegion);

    void remove(FfomRegion ffomRegion);

    FfomRegion find(Object id);

    List<FfomRegion> findAll();

    List<FfomRegion> findRange(int[] range);

    int count();

    Long nextId();

    List<FfomRegion> find(Region region, Pilier pilier) throws Exception;

    List<FfomRegion> find(Region region) throws Exception;

}
