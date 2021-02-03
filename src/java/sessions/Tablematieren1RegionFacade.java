/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Region;
import entities.TablematiereN1;
import entities.Tablematieren1Region;
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
public class Tablematieren1RegionFacade extends AbstractFacade<Tablematieren1Region> implements Tablematieren1RegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Tablematieren1RegionFacade() {
        super(Tablematieren1Region.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(t.idtablematieren1Region) FROM Tablematieren1Region t");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<Tablematieren1Region> findByRegionTableniveau1(TablematiereN1 tablematiereN1, Region region) throws Exception {
        List<Tablematieren1Region> tablematieren1Regions = null;
        Query query = em.createQuery("SELECT t FROM Tablematieren1Region t WHERE t.idtablematiereN1.idtablematiereN1=:table AND t.idregion.idregion=:region ORDER BY t.idtablematiereN1.idtablematiereN1,t.numeropage");
        query.setParameter("table", tablematiereN1.getIdtablematiereN1()).setParameter("region", region.getIdregion());
        tablematieren1Regions = query.getResultList();
        return tablematieren1Regions;
    }

    @Override
    public List<Tablematieren1Region> findByTableniveau1(TablematiereN1 tablematiereN1) throws Exception {
        List<Tablematieren1Region> tablematieren1Regions = null;
        Query query = em.createQuery("SELECT t FROM Tablematieren1Region t WHERE t.idtablematiereN1.idtablematiereN1=:table");
        query.setParameter("table", tablematiereN1.getIdtablematiereN1());
        tablematieren1Regions = query.getResultList();
        return tablematieren1Regions;
    }

}
