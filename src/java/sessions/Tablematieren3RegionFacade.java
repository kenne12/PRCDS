/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.District;
import entities.Region;
import entities.TablematiereN3;
import entities.Tablematieren3District;
import entities.Tablematieren3Region;
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
public class Tablematieren3RegionFacade extends AbstractFacade<Tablematieren3Region> implements Tablematieren3RegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Tablematieren3RegionFacade() {
        super(Tablematieren3Region.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(t.idtablematieren3Region) FROM Tablematieren3Region t");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<Tablematieren3Region> findByRegionTableniveau3(TablematiereN3 tablematiereN3, Region region) throws Exception {
        List<Tablematieren3Region> tablematieren3Regions = null;
        Query query = em.createQuery("SELECT t FROM Tablematieren3Region t WHERE t.idtablematiereN3.idtablematiereN3=:table AND t.idregion.idregion=:region ORDER BY t.idtablematiereN3.idtablematiereN2.idtablematiereN1.idtablematiereN1,t.idtablematiereN3.idtablematiereN2.idtablematiereN2,t.idtablematiereN3.idtablematiereN3,t.numeropage");
        query.setParameter("table", tablematiereN3.getIdtablematiereN3()).setParameter("region", region.getIdregion());
        tablematieren3Regions = query.getResultList();
        return tablematieren3Regions;
    }

    @Override
    public List<Tablematieren3Region> findByTableniveau3(TablematiereN3 tablematiereN3) throws Exception {
        List<Tablematieren3Region> tablematieren3Regions = null;
        Query query = em.createQuery("SELECT t FROM Tablematieren3Region t WHERE t.idtablematiereN3.idtablematiereN3=:table");
        query.setParameter("table", tablematiereN3.getIdtablematiereN3());
        tablematieren3Regions = query.getResultList();
        return tablematieren3Regions;
    }

}
