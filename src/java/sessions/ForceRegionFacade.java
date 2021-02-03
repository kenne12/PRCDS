/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.FfomRegion;
import entities.ForceRegion;
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
public class ForceRegionFacade extends AbstractFacade<ForceRegion> implements ForceRegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ForceRegionFacade() {
        super(ForceRegion.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(f.idforceRegion) FROM ForceRegion f");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<ForceRegion> findByFfom(FfomRegion ffom) throws Exception {
        List<ForceRegion> forces = null;
        Query query = em.createQuery("SELECT f FROM ForceRegion f WHERE f.idffomRegion.idffomRegion=:ffom");
        query.setParameter("ffom", ffom.getIdffomRegion());
        forces = query.getResultList();
        return forces;
    }

}
