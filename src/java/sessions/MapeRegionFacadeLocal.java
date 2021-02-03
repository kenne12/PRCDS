/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Annee;
import entities.Region;
import entities.Mape;
import entities.MapeRegion;
import entities.Structure;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdel BEIN-INFO
 */
@Local
public interface MapeRegionFacadeLocal {

    void create(MapeRegion mapeRegion);

    void edit(MapeRegion mapeRegion);

    void remove(MapeRegion mapeRegion);

    MapeRegion find(Object id);

    List<MapeRegion> findAll();

    List<MapeRegion> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<MapeRegion> find(Mape mape) throws Exception;

    List<MapeRegion> find(Mape mape, Annee annee) throws Exception;

    List<MapeRegion> findByRegion(int region);

    List<MapeRegion> find(Mape mape, Annee annee, Region region) throws Exception;

    List<MapeRegion> find(Mape mape, Annee annee, Structure structure) throws Exception;

}
