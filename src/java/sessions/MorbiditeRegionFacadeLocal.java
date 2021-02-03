/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Region;
import entities.Morbidite;
import entities.MorbiditeRegion;
import entities.Rubriquemorbidite;
import entities.Structure;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdel BEIN-INFO
 */
@Local
public interface MorbiditeRegionFacadeLocal {

    void create(MorbiditeRegion morbiditeRegion);

    void edit(MorbiditeRegion morbiditeRegion);

    void remove(MorbiditeRegion morbiditeRegion);

    MorbiditeRegion find(Object id);

    List<MorbiditeRegion> findAll();

    List<MorbiditeRegion> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<MorbiditeRegion> find(Morbidite morbidite, Rubriquemorbidite rubriquemorbidite) throws Exception;

    List<MorbiditeRegion> find(Morbidite morbidite) throws Exception;

    List<MorbiditeRegion> findByRegion(int region);

    List<MorbiditeRegion> find(Morbidite morbidite, Rubriquemorbidite rubriquemorbidite, Region region) throws Exception;

    List<MorbiditeRegion> find(Morbidite morbidite, Rubriquemorbidite rubriquemorbidite, Structure structure) throws Exception;
}
