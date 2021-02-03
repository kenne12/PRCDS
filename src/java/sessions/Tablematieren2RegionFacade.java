/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Region;
import entities.TablematiereN2;
import entities.Tablematieren2Region;
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
public class Tablematieren2RegionFacade extends AbstractFacade<Tablematieren2Region> implements Tablematieren2RegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Tablematieren2RegionFacade() {
        super(Tablematieren2Region.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(t.idtablematieren2Region) FROM Tablematieren2Region t");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<Tablematieren2Region> findByRegionTableniveau2(TablematiereN2 tablematiereN2, Region region) throws Exception {
        List<Tablematieren2Region> tablematieren2Regions = null;
        Query query = em.createQuery("SELECT t FROM Tablematieren2Region t WHERE t.idtablematiereN2.idtablematiereN2=:table AND t.idregion.idregion=:region ORDER BY t.idtablematiereN2.idtablematiereN1.idtablematiereN1,t.idtablematiereN2.idtablematiereN2,t.numeropage");
        query.setParameter("table", tablematiereN2.getIdtablematiereN2()).setParameter("region", region.getIdregion());
        tablematieren2Regions = query.getResultList();
        return tablematieren2Regions;
    }

    @Override
    public List<Tablematieren2Region> findByTableniveau2(TablematiereN2 tablematiereN2) throws Exception {
        List<Tablematieren2Region> tablematieren2Regions = null;
        Query query = em.createQuery("SELECT t FROM Tablematieren2Region t WHERE t.idtablematiereN2.idtablematiereN2=:table");
        query.setParameter("table", tablematiereN2.getIdtablematiereN2());
        tablematieren2Regions = query.getResultList();
        return tablematieren2Regions;
    }

}
