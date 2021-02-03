/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Interventionpnds;
import entities.ObjectifOppRegion;
import entities.Region;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface ObjectifOppRegionFacadeLocal {

    void create(ObjectifOppRegion objectifOppRegion);

    void edit(ObjectifOppRegion objectifOppRegion);

    void remove(ObjectifOppRegion objectifOppRegion);

    ObjectifOppRegion find(Object id);

    List<ObjectifOppRegion> findAll();

    List<ObjectifOppRegion> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<ObjectifOppRegion> findByRegion(Region region) throws Exception;

    List<ObjectifOppRegion> findByRegion(Region region, Interventionpnds interventionpnds) throws Exception;

}
