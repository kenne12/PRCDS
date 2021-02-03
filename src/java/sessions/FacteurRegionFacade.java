/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Region;
import entities.FacteurRegion;
import entities.FacteurRegion;
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
public class FacteurRegionFacade extends AbstractFacade<FacteurRegion> implements FacteurRegionFacadeLocal {
    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacteurRegionFacade() {
        super(FacteurRegion.class);
    }
      @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(f.idfacteurRegion) FROM FacteurRegion f");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<FacteurRegion> findByRegion(Region region) throws Exception {
        List<FacteurRegion> facteurRegions = null;
        Query query = em.createQuery("SELECT f FROM FacteurRegion f WHERE f.idregion.idregion=:region ORDER BY f.idfacteur.idgroupefacteur.code,f.idfacteur.idtypefacteur.nomFr,f.idfacteur.nomEn");
        query.setParameter("region", region.getIdregion());
        facteurRegions = query.getResultList();
        return facteurRegions;
    }

    @Override
    public List<FacteurRegion> findByRegion(int region) {
        List<FacteurRegion> facteurs = null;
        try {
            Query query = em.createQuery("SELECT s FROM FacteurRegion s WHERE s.idregion.idregion=:region ORDER BY s.idfacteur.idgroupefacteur.code,s.idfacteur.idtypefacteur.nomFr,s.idfacteur.nomFr");
            query.setParameter("region", region);
            facteurs = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return facteurs;
    }

}
