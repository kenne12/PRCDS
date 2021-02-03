/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.District;
import entities.Indicateur;
import entities.Region;
import entities.ResultatAttenduRegion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface ResultatAttenduRegionFacadeLocal {

    void create(ResultatAttenduRegion resultatAttenduRegion);

    void edit(ResultatAttenduRegion resultatAttenduRegion);

    void remove(ResultatAttenduRegion resultatAttenduRegion);

    ResultatAttenduRegion find(Object id);

    List<ResultatAttenduRegion> findAll();

    List<ResultatAttenduRegion> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<ResultatAttenduRegion> findByRegion(Region region) throws Exception;

    List<ResultatAttenduRegion> findByIndicateur(Indicateur indicateteur) throws Exception;

    List<ResultatAttenduRegion> findByIndicateur(Indicateur indicateteur, Region region) throws Exception;

}
