/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Region;
import entities.TablematiereN2;
import entities.Tablematieren2Region;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface Tablematieren2RegionFacadeLocal {

    void create(Tablematieren2Region tablematieren2Region);

    void edit(Tablematieren2Region tablematieren2Region);

    void remove(Tablematieren2Region tablematieren2Region);

    Tablematieren2Region find(Object id);

    List<Tablematieren2Region> findAll();

    List<Tablematieren2Region> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<Tablematieren2Region> findByRegionTableniveau2(TablematiereN2 tablematiereN2, Region region) throws Exception;

    List<Tablematieren2Region> findByTableniveau2(TablematiereN2 tablematiereN2) throws Exception;

}
