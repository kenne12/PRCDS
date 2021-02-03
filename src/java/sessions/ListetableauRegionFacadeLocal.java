/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Region;
import entities.ListetableauRegion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdel BEIN-INFO
 */
@Local
public interface ListetableauRegionFacadeLocal {

    void create(ListetableauRegion listetableauRegion);

    void edit(ListetableauRegion listetableauRegion);

    void remove(ListetableauRegion listetableauRegion);

    ListetableauRegion find(Object id);

    List<ListetableauRegion> findAll();

    List<ListetableauRegion> findRange(int[] range);

    int count();
    
    public Long nextId() throws Exception;

    public List<ListetableauRegion> findByRegion(Region region) throws Exception ;

    public List<ListetableauRegion> findByRegion(int region);
}
