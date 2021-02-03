/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.ActeurRegion;
import entities.Region;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdel BEIN-INFO
 */
@Local
public interface ActeurRegionFacadeLocal {

    void create(ActeurRegion acteurRegion);

    void edit(ActeurRegion acteurRegion);

    void remove(ActeurRegion acteurRegion);

    ActeurRegion find(Object id);

    List<ActeurRegion> findAll();

    List<ActeurRegion> findRange(int[] range);

    int count();
     Long nextId() throws Exception;

    List<ActeurRegion> findByRegion(Region district) throws Exception;

    List<ActeurRegion> findByRegion(int district);

}
