/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Annee;
import entities.PopulationRegion;
import entities.Region;
import entities.Rubriquepopulation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface PopulationRegionFacadeLocal {

    void create(PopulationRegion populationRegion);

    void edit(PopulationRegion populationRegion);

    void remove(PopulationRegion populationRegion);

    PopulationRegion find(Object id);

    List<PopulationRegion> findAll();

    List<PopulationRegion> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<PopulationRegion> find(Region region, Rubriquepopulation rubriquepopulation, Annee annee) throws Exception;

    List<PopulationRegion> find(Region region, Annee annee) throws Exception;

}
