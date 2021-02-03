/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.ActiviteRegion;
import entities.Annee;
import entities.ChronogrammeRegion;
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
public class ChronogrammeRegionFacade extends AbstractFacade<ChronogrammeRegion> implements ChronogrammeRegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChronogrammeRegionFacade() {
        super(ChronogrammeRegion.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(c.idchronogrammeRegion) FROM ChronogrammeRegion c");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<ChronogrammeRegion> findByActivite(ActiviteRegion activite) throws Exception {
        List<ChronogrammeRegion> chronogrammeRegions = null;
        Query query = em.createQuery("SELECT c FROM ChronogrammeRegion c WHERE c.idactiviteRegion.idactiviteRegion=:activite");
        query.setParameter("activite", activite.getIdactiviteRegion());
        chronogrammeRegions = query.getResultList();
        return chronogrammeRegions;
    }

    @Override
    public List<ChronogrammeRegion> findByActivite(ActiviteRegion activite, Annee annee) throws Exception {
        List<ChronogrammeRegion> chronogrammeRegions = null;
        Query query = em.createQuery("SELECT c FROM ChronogrammeRegion c WHERE c.idactiviteRegion.idactiviteRegion=:activite AND c.idannee.idannee=:annee");
        query.setParameter("activite", activite.getIdactiviteRegion()).setParameter("annee", annee.getIdannee());
        chronogrammeRegions = query.getResultList();
        return chronogrammeRegions;
    }

}
