/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Region;
import entities.TablematiereN3;
import entities.Tablematieren3Region;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface Tablematieren3RegionFacadeLocal {

    void create(Tablematieren3Region tablematieren3Region);

    void edit(Tablematieren3Region tablematieren3Region);

    void remove(Tablematieren3Region tablematieren3Region);

    Tablematieren3Region find(Object id);

    List<Tablematieren3Region> findAll();

    List<Tablematieren3Region> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<Tablematieren3Region> findByRegionTableniveau3(TablematiereN3 tablematiereN3, Region region) throws Exception;

    List<Tablematieren3Region> findByTableniveau3(TablematiereN3 tablematiereN3) throws Exception;

}
