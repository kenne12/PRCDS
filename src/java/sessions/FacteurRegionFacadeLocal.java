/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.FacteurRegion;
import entities.Region;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdel BEIN-INFO
 */
@Local
public interface FacteurRegionFacadeLocal {

    void create(FacteurRegion facteurRegion);

    void edit(FacteurRegion facteurRegion);

    void remove(FacteurRegion facteurRegion);

    FacteurRegion find(Object id);

    List<FacteurRegion> findAll();

    List<FacteurRegion> findRange(int[] range);

    int count();
    Long nextId() throws Exception;

    List<FacteurRegion> findByRegion(Region region) throws Exception;

    List<FacteurRegion> findByRegion(int region);
}
