/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.PartiehauteRegion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdel BEIN-INFO
 */
@Local
public interface PartiehauteRegionFacadeLocal {

    void create(PartiehauteRegion partiehauteRegion);

    void edit(PartiehauteRegion partiehauteRegion);

    void remove(PartiehauteRegion partiehauteRegion);

    PartiehauteRegion find(Object id);

    List<PartiehauteRegion> findAll();

    List<PartiehauteRegion> findRange(int[] range);

    int count();
    
     public int nextId();
     
     public List<PartiehauteRegion> findByRegion(int region);
     
      public List<PartiehauteRegion> findByIntroduction(String introduction);
    
}
