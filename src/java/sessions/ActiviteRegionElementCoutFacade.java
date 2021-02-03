/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.ActiviteRegion;
import entities.ActiviteRegionElementCout;
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
public class ActiviteRegionElementCoutFacade extends AbstractFacade<ActiviteRegionElementCout> implements ActiviteRegionElementCoutFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActiviteRegionElementCoutFacade() {
        super(ActiviteRegionElementCout.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(a.idactiviteRegionElementCout) FROM ActiviteRegionElementCout a");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<ActiviteRegionElementCout> findByActivite(ActiviteRegion activiteRegion) throws Exception {
        List<ActiviteRegionElementCout> activiteRegionElementCouts = null;
        Query query = em.createQuery("SELECT a FROM ActiviteRegionElementCout a WHERE a.idactiviteRegion.idactiviteRegion=:activite");
        query.setParameter("activite", activiteRegion.getIdactiviteRegion());
        activiteRegionElementCouts = query.getResultList();
        return activiteRegionElementCouts;
    }

}
