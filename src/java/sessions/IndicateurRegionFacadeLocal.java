/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Axe;
import entities.Indicateur;
import entities.IndicateurRegion;
import entities.Region;
import entities.Sousaxe;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface IndicateurRegionFacadeLocal {

    void create(IndicateurRegion indicateurRegion);

    void edit(IndicateurRegion indicateurRegion);

    void remove(IndicateurRegion indicateurRegion);

    IndicateurRegion find(Object id);

    List<IndicateurRegion> findAll();

    List<IndicateurRegion> findRange(int[] range);

    int count();

    Long nextVal();

    List<IndicateurRegion> findByIndicateur(Indicateur indicateur) throws Exception;

    List<IndicateurRegion> findByIndicateur(Indicateur indicateur, Region region) throws Exception;

    List<IndicateurRegion> findByRegionSousaxe(Region region, Sousaxe sousaxe) throws Exception;

    List<IndicateurRegion> findByRegionAxeObservation(Region region, Axe axe, int idobservation) throws Exception;

    List<IndicateurRegion> findByRegionSousaxeObservation(Region region, Sousaxe sousaxe, int idobservation) throws Exception;

    List<IndicateurRegion> findByRegion(Region region) throws Exception;

    List<IndicateurRegion> findByRegionIndicateurObservation(Region region, Indicateur indicateur, int idobservation) throws Exception;

}
