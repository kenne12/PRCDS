/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.ActiviteRegion;
import entities.ProblemeRegion;
import entities.Region;
import entities.Sousaxe;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface ActiviteRegionFacadeLocal {

    void create(ActiviteRegion activiteRegion);

    void edit(ActiviteRegion activiteRegion);

    void remove(ActiviteRegion activiteRegion);

    ActiviteRegion find(Object id);

    List<ActiviteRegion> findAll();

    List<ActiviteRegion> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<ActiviteRegion> findAllRange();

    //recherche les activités par problème
    List<ActiviteRegion> findByProbleme(ProblemeRegion problemeRegion) throws Exception;

    //recherche les activité de la region par sous axe
    List<ActiviteRegion> findBySousAxe(Sousaxe sousaxe, Region region) throws Exception;

    //recherche les activité de consolidation et correctrices
    List<ActiviteRegion> find(Region region, Sousaxe sousaxe, int observation) throws Exception;

    //recherche toutes las activités de la region
    List<ActiviteRegion> findByRegion(Region region) throws Exception;

    //recherche les activités d appui
    List<ActiviteRegion> findByRegionSousaxe(Region region, Sousaxe sousaxe , boolean appui) throws Exception;
    
    //recupere les activités d appui et ceux de la region
    List<ActiviteRegion> findByActivitechronogramme(Sousaxe sousaxe, Region region) throws Exception;

}
