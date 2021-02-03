/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.ActeurRegion;
import entities.Region;
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
public class ActeurRegionFacade extends AbstractFacade<ActeurRegion> implements ActeurRegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActeurRegionFacade() {
        super(ActeurRegion.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(a.idacteurRegion) FROM ActeurRegion a");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<ActeurRegion> findByRegion(Region region) throws Exception {
        List<ActeurRegion> acteurRegions = null;
        Query query = em.createQuery("SELECT a FROM ActeurRegion a WHERE a.idregion.idregion=:region");
        query.setParameter("region", region.getIdregion());
        acteurRegions = query.getResultList();
        return acteurRegions;
    }

    @Override
    public List<ActeurRegion> findByRegion(int region) {
        List<ActeurRegion> acteurs = null;
        try {
            Query query = em.createQuery("SELECT s FROM ActeurRegion s WHERE s.idregion.idregion=:region");
            query.setParameter("region", region);
            acteurs = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return acteurs;
    }

}
