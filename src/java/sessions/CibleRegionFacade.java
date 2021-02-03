/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Annee;
import entities.CibleRegion;
import entities.IndicateurRegion;
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
public class CibleRegionFacade extends AbstractFacade<CibleRegion> implements CibleRegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CibleRegionFacade() {
        super(CibleRegion.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(c.idcibleRegion) FROM CibleRegion c");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<CibleRegion> find(IndicateurRegion indicateurRegion, Annee annee) throws Exception {
        List<CibleRegion> cibleRegions = null;
        Query query = em.createQuery("SELECT c FROM CibleRegion c WHERE c.idindicateur.idindicateur=:indicateur AND c.idregion.idregion=:region AND c.idannee.idannee=:annee");
        query.setParameter("indicateur", indicateurRegion.getIdindicateur().getIdindicateur());
        query.setParameter("region", indicateurRegion.getIdregion().getIdregion());
        query.setParameter("annee", annee.getIdannee());
        cibleRegions = query.getResultList();
        return cibleRegions;
    }

    @Override
    public List<CibleRegion> find(IndicateurRegion indicateurRegion) throws Exception {
        List<CibleRegion> cibles = null;
        Query query = em.createQuery("SELECT c FROM CibleRegion c WHERE c.idindicateur.idindicateur=:indicateur AND c.idregion.idregion=:region");
        query.setParameter("indicateur", indicateurRegion.getIdindicateur().getIdindicateur());
        query.setParameter("region", indicateurRegion.getIdregion().getIdregion());
        cibles = query.getResultList();
        return cibles;
    }

}
