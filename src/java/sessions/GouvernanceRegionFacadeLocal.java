/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Region;
import entities.GouvernanceRegion;
import entities.Rubriquegouvernance;
import entities.Structure;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdel BEIN-INFO
 */
@Local
public interface GouvernanceRegionFacadeLocal {

    void create(GouvernanceRegion gouvernanceRegion);

    void edit(GouvernanceRegion gouvernanceRegion);

    void remove(GouvernanceRegion gouvernanceRegion);

    GouvernanceRegion find(Object id);

    List<GouvernanceRegion> findAll();

    List<GouvernanceRegion> findRange(int[] range);

    int count();
    
     public Long nextId() throws Exception;
    
    public List<GouvernanceRegion> find(Structure structure, Rubriquegouvernance rubriquegouvernance, Region region) throws Exception ;

    
}
