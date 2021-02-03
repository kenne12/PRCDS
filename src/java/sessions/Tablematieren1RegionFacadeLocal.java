/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Region;
import entities.TablematiereN1;
import entities.Tablematieren1Region;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface Tablematieren1RegionFacadeLocal {

    void create(Tablematieren1Region tablematieren1Region);

    void edit(Tablematieren1Region tablematieren1Region);

    void remove(Tablematieren1Region tablematieren1Region);

    Tablematieren1Region find(Object id);

    List<Tablematieren1Region> findAll();

    List<Tablematieren1Region> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<Tablematieren1Region> findByRegionTableniveau1(TablematiereN1 tablematiereN1, Region region) throws Exception;

    List<Tablematieren1Region> findByTableniveau1(TablematiereN1 tablematiereN1) throws Exception;

}
