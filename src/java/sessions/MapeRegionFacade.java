/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Annee;
import entities.Region;
import entities.Mape;
import entities.MapeRegion;
import entities.MapeRegion;
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
public class MapeRegionFacade extends AbstractFacade<MapeRegion> implements MapeRegionFacadeLocal {
    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MapeRegionFacade() {
        super(MapeRegion.class);
    }
      @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(m.idmapeRegion) FROM MapeRegion m");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<MapeRegion> find(Mape mape, Annee annee) throws Exception {
        List<MapeRegion> mapeRegions = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM MapeRegion m WHERE m.idmape.idmape=:mape AND m.idannee.idannee=:annee");
        query.setParameter("mape", mape.getIdmape()).setParameter("annee", annee.getIdannee());
        if (!query.getResultList().isEmpty()) {
            mapeRegions = query.getResultList();
        }
        return mapeRegions;
    }

    @Override
    public List<MapeRegion> find(Mape mape) throws Exception {
        List<MapeRegion> mapeRegions = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM MapeRegion m WHERE m.idmape.idmape=:mape");
        query.setParameter("mape", mape.getIdmape());
        if (!query.getResultList().isEmpty()) {
            mapeRegions = query.getResultList();
        }
        return mapeRegions;
    }

    @Override
    public List<MapeRegion> findByRegion(int region) {
        List<MapeRegion> mapeRegions = null;
        try {
            Query query = em.createQuery("SELECT s FROM MapeRegion s WHERE s.idregion.idregion=:region");
            query.setParameter("region", region);
            mapeRegions = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapeRegions;
    }
    
    @Override
    public List<MapeRegion> find(Mape mape, Annee annee, Region region) throws Exception {
        List<MapeRegion> mapeRegions = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM MapeRegion m WHERE m.idregion.idregion=:region AND m.idmape.idmape=:mape AND m.idannee.idannee=:annee");
        query.setParameter("mape", mape.getIdmape()).setParameter("annee", annee.getIdannee()).setParameter("region", region.getIdregion());
        if (!query.getResultList().isEmpty()) {
            mapeRegions = query.getResultList();
        }
        return mapeRegions;
    }
    
    @Override
    public List<MapeRegion> find(Mape mape, Annee annee,  Structure structure) throws Exception{
        List<MapeRegion> mapeRegions = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM MapeRegion m WHERE m.idstructure.idstructure=:structure AND m.idmape.idmape=:mape AND m.idannee.idannee=:annee");
        query.setParameter("mape", mape.getIdmape()).setParameter("annee", annee.getIdannee()).setParameter("structure", structure.getIdstructure());
        if (!query.getResultList().isEmpty()) {
            mapeRegions = query.getResultList();
        }
        return mapeRegions;
    }
}
