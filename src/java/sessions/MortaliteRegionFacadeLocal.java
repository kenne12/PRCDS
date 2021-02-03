/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Mortalite;
import entities.MortaliteRegion;
import entities.Region;
import entities.Rubriquemortalite;
import entities.Structure;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdel BEIN-INFO
 */
@Local
public interface MortaliteRegionFacadeLocal {

    void create(MortaliteRegion mortaliteRegion);

    void edit(MortaliteRegion mortaliteRegion);

    void remove(MortaliteRegion mortaliteRegion);

    MortaliteRegion find(Object id);

    List<MortaliteRegion> findAll();

    List<MortaliteRegion> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<MortaliteRegion> findByRegion(int region);

    List<MortaliteRegion> find(Mortalite mortalite, Rubriquemortalite rubriquemortalite) throws Exception;

    List<MortaliteRegion> find(Mortalite mortalite) throws Exception;

    List<MortaliteRegion> find(Mortalite mortalite, Rubriquemortalite rubriquemortalite, Region region) throws Exception;

    List<MortaliteRegion> find(Mortalite mortalite, Rubriquemortalite rubriquemortalite, Structure structure) throws Exception;

}
