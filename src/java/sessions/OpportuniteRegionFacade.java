/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.FfomRegion;
import entities.OpportuniteRegion;
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
public class OpportuniteRegionFacade extends AbstractFacade<OpportuniteRegion> implements OpportuniteRegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OpportuniteRegionFacade() {
        super(OpportuniteRegion.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(o.idopportuniteRegion) FROM OpportuniteRegion o");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<OpportuniteRegion> findByFfom(FfomRegion ffom) throws Exception {
        List<OpportuniteRegion> opportunites = null;
        Query query = em.createQuery("SELECT o FROM OpportuniteRegion o WHERE o.idffomRegion.idffomRegion=:ffom");
        query.setParameter("ffom", ffom.getIdffomRegion());
        opportunites = query.getResultList();
        return opportunites;
    }

}
