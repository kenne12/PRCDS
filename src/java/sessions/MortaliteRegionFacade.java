/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Region;
import entities.Mortalite;
import entities.MortaliteRegion;
import entities.Rubriquemortalite;
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
public class MortaliteRegionFacade extends AbstractFacade<MortaliteRegion> implements MortaliteRegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MortaliteRegionFacade() {
        super(MortaliteRegion.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(m.idmortaliteRegion) FROM MortaliteRegion m");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<MortaliteRegion> find(Mortalite mortalite, Rubriquemortalite rubriquemortalite) throws Exception {
        List<MortaliteRegion> mortaliteRegions = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM MortaliteRegion m WHERE m.idmortalite.idmortalite=:mortalite AND m.idrubriquemortalite.idrubriquemortalite=:rubriquemortalite");
        query.setParameter("mortalite", mortalite.getIdmortalite()).setParameter("rubriquemortalite", rubriquemortalite.getIdrubriquemortalite());
        if (!query.getResultList().isEmpty()) {
            mortaliteRegions = query.getResultList();
        }
        return mortaliteRegions;
    }

    @Override
    public List<MortaliteRegion> find(Mortalite mortalite) throws Exception {
        List<MortaliteRegion> mortaliteRegions = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM MortaliteRegion m WHERE m.idmortalite.idmortalite=:mortalite");
        query.setParameter("mortalite", mortalite.getIdmortalite());
        if (!query.getResultList().isEmpty()) {
            mortaliteRegions = query.getResultList();
        }
        return mortaliteRegions;
    }

    @Override
    public List<MortaliteRegion> findByRegion(int region) {
        List<MortaliteRegion> mortaliteRegions = null;
        try {
            Query query = em.createQuery("SELECT s FROM MortaliteRegion s WHERE s.idregion.idregion=:region");
            query.setParameter("region", region);
            mortaliteRegions = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mortaliteRegions;
    }

    @Override
    public List<MortaliteRegion> find(Mortalite mortalite, Rubriquemortalite rubriquemortalite, Region region) throws Exception {
        List<MortaliteRegion> mortaliteRegions = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM MortaliteRegion m WHERE m.idregion.idregion=:region AND m.idmortalite.idmortalite=:mortalite AND m.idrubriquemortalite.idrubriquemortalite=:rubriquemortalite");
        query.setParameter("mortalite", mortalite.getIdmortalite()).setParameter("rubriquemortalite", rubriquemortalite.getIdrubriquemortalite()).setParameter("region", region.getIdregion());
        if (!query.getResultList().isEmpty()) {
            mortaliteRegions = query.getResultList();
        }
        return mortaliteRegions;
    }

    @Override
    public List<MortaliteRegion> find(Mortalite mortalite, Rubriquemortalite rubriquemortalite, Structure structure) throws Exception {
        List<MortaliteRegion> mortaliteRegions = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM MortaliteRegion m WHERE m.idstructure.idstructure=:structure AND m.idmortalite.idmortalite=:mortalite AND m.idrubriquemortalite.idrubriquemortalite=:rubriquemortalite");
        query.setParameter("mortalite", mortalite.getIdmortalite()).setParameter("rubriquemortalite", rubriquemortalite.getIdrubriquemortalite()).setParameter("structure", structure.getIdstructure());
        if (!query.getResultList().isEmpty()) {
            mortaliteRegions = query.getResultList();
        }
        return mortaliteRegions;
    }
}
