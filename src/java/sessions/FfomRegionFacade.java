/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.FfomRegion;
import entities.Pilier;
import entities.Region;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author kenne
 */
@Stateless
public class FfomRegionFacade extends AbstractFacade<FfomRegion> implements FfomRegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FfomRegionFacade() {
        super(FfomRegion.class);
    }

    @Override
    public Long nextId() {
        try {
            Query query = em.createQuery("SELECT MAX(f.idffomRegion) FROM FfomRegion f ");
            List listObj = query.getResultList();
            if (!listObj.isEmpty()) {
                return ((Long) listObj.get(0)) + 1;
            } else {
                return 1L;
            }
        } catch (Exception e) {
            return 1l;
        }
    }

    @Override
    public List<FfomRegion> find(Region region, Pilier pilier) throws Exception {
        List<FfomRegion> ffoms = null;
        Query query = em.createQuery("SELECT f FROM FfomRegion f WHERE f.idregion.idregion=:region AND f.idpilier.idpilier=:pilier");
        query.setParameter("region", region.getIdregion()).setParameter("pilier", pilier.getIdpilier());
        ffoms = query.getResultList();
        return ffoms;
    }

    @Override
    public List<FfomRegion> find(Region region) throws Exception {
        List<FfomRegion> ffoms = null;
        Query query = em.createQuery("SELECT f FROM FfomRegion f WHERE f.idregion.idregion=:region");
        query.setParameter("region", region.getIdregion());
        ffoms = query.getResultList();
        return ffoms;
    }

}
