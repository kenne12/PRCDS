/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Region;
import entities.Morbidite;
import entities.MorbiditeRegion;
import entities.MorbiditeRegion;
import entities.Rubriquemorbidite;
import entities.Structure;
import java.util.ArrayList;
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
public class MorbiditeRegionFacade extends AbstractFacade<MorbiditeRegion> implements MorbiditeRegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MorbiditeRegionFacade() {
        super(MorbiditeRegion.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(m.idmorbiditeRegion) FROM MorbiditeRegion m");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<MorbiditeRegion> find(Morbidite morbidite, Rubriquemorbidite rubriquemorbidite) throws Exception {
        List<MorbiditeRegion> morbiditeRegions = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM MorbiditeRegion m WHERE m.idmorbidite.idmorbidite=:morbidite AND m.idrubriquemorbidite.idrubriquemorbidite=:rubriquemorbidite");
        query.setParameter("morbidite", morbidite.getIdmorbidite()).setParameter("rubriquemorbidite", rubriquemorbidite.getIdrubriquemorbidite());
        if (!query.getResultList().isEmpty()) {
            morbiditeRegions = query.getResultList();
        }
        return morbiditeRegions;
    }

    @Override
    public List<MorbiditeRegion> find(Morbidite morbidite) throws Exception {
        List<MorbiditeRegion> morbiditeRegions = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM MorbiditeRegion m WHERE m.idmorbidite.idmorbidite=:morbidite");
        query.setParameter("morbidite", morbidite.getIdmorbidite());
        if (!query.getResultList().isEmpty()) {
            morbiditeRegions = query.getResultList();
        }
        return morbiditeRegions;
    }

    @Override
    public List<MorbiditeRegion> findByRegion(int region) {
        List<MorbiditeRegion> morbiditeRegions = null;
        try {
            Query query = em.createQuery("SELECT s FROM MorbiditeRegion s WHERE s.idregion.idregion=:region");
            query.setParameter("region", region);
            morbiditeRegions = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return morbiditeRegions;
    }

    @Override
    public List<MorbiditeRegion> find(Morbidite morbidite, Rubriquemorbidite rubriquemorbidite, Region region) throws Exception {
        List<MorbiditeRegion> morbiditeRegions = new ArrayList<>();
        //Query query = em.createQuery("SELECT m FROM MorbiditeRegion m WHERE m.idregion.idregion=:region AND m.idmorbidite.idmorbidite=:morbidite AND m.idrubriquemorbidite.idrubriquemorbidite=:rubriquemorbidite AND m.consolide=true");
        Query query = em.createQuery("SELECT m FROM MorbiditeRegion m WHERE m.idregion.idregion=:region AND m.idmorbidite.idmorbidite=:morbidite AND m.idrubriquemorbidite.idrubriquemorbidite=:rubriquemorbidite");
        query.setParameter("morbidite", morbidite.getIdmorbidite()).setParameter("rubriquemorbidite", rubriquemorbidite.getIdrubriquemorbidite()).setParameter("region", region.getIdregion());
        if (!query.getResultList().isEmpty()) {
            morbiditeRegions = query.getResultList();
        }
        return morbiditeRegions;
    }

    @Override
    public List<MorbiditeRegion> find(Morbidite morbidite, Rubriquemorbidite rubriquemorbidite, Structure structure) throws Exception {
        List<MorbiditeRegion> morbiditeRegions = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM MorbiditeRegion m WHERE m.idstructure.idstructure=:structure AND m.idmorbidite.idmorbidite=:morbidite AND m.idrubriquemorbidite.idrubriquemorbidite=:rubriquemorbidite");
        query.setParameter("morbidite", morbidite.getIdmorbidite()).setParameter("rubriquemorbidite", rubriquemorbidite.getIdrubriquemorbidite()).setParameter("structure", structure.getIdstructure());
        if (!query.getResultList().isEmpty()) {
            morbiditeRegions = query.getResultList();
        }
        return morbiditeRegions;
    }

}
