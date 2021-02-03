/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Region;
import entities.ListetableauRegion;
import entities.ListetableauRegion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Abdel BEIN-INFO
 */
@Stateless
public class ListetableauRegionFacade extends AbstractFacade<ListetableauRegion> implements ListetableauRegionFacadeLocal {
    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ListetableauRegionFacade() {
        super(ListetableauRegion.class);
    }
      @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(m.idlistetableauRegion) FROM ListetableauRegion m");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

  @Override
    public List<ListetableauRegion> findByRegion(Region region) throws Exception {
        List<ListetableauRegion> listetableauregions = null;
        Query query = em.createQuery("SELECT f FROM ListetableauRegion f WHERE f.idregion.idregion=:region");
        query.setParameter("region", region.getIdregion());
        listetableauregions = query.getResultList();
        return listetableauregions;
    }
    
     @Override
    public List<ListetableauRegion> findByRegion(int region) {
        List<ListetableauRegion> listetableaus = null;
        try {
            Query query = em.createQuery("SELECT s FROM ListetableauRegion s WHERE s.idregion.idregion=:region");
            query.setParameter("region", region);
            listetableaus = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listetableaus;
    }

}
