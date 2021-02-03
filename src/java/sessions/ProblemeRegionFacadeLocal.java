/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Axe;
import entities.Indicateur;
import entities.IndicateurRegion;
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
public interface ProblemeRegionFacadeLocal {

    void create(ProblemeRegion problemeRegion);

    void edit(ProblemeRegion problemeRegion);

    void remove(ProblemeRegion problemeRegion);

    ProblemeRegion find(Object id);

    List<ProblemeRegion> findAll();

    List<ProblemeRegion> findRange(int[] range);

    int count();

    Integer nextId() throws Exception;

    List<ProblemeRegion> find(Indicateur indicateur, Region region, Sousaxe sousaxe) throws Exception;

    List<ProblemeRegion> find(Region region, Sousaxe sousaxe, int observation) throws Exception;

    List<ProblemeRegion> find(Region region, Sousaxe sousaxe) throws Exception;

    List<ProblemeRegion> find(IndicateurRegion indicateurRegion) throws Exception;

    List<ProblemeRegion> find(Indicateur indicateur, Region region, int observation) throws Exception;

    List<ProblemeRegion> findByAxeRegion(Axe axe, Region region, int observation) throws Exception;

    List<ProblemeRegion> findBySousAxeRegion(Sousaxe sousaxe, Region region, int observation) throws Exception;

}
