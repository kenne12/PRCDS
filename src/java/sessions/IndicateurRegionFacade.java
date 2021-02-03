/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Axe;
import entities.Indicateur;
import entities.IndicateurRegion;
import entities.Region;
import entities.Sousaxe;
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
public class IndicateurRegionFacade extends AbstractFacade<IndicateurRegion> implements IndicateurRegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IndicateurRegionFacade() {
        super(IndicateurRegion.class);
    }

    @Override
    public Long nextVal() {
        Query query = em.createQuery("SELECT MAX(d.idindicateurRegion) FROM IndicateurRegion d");
        Long result = (Long) query.getSingleResult();
        if (result != null) {
            result += 1;
        } else {
            result = 1L;
        }
        return result;
    }

    @Override
    public List<IndicateurRegion> findByIndicateur(Indicateur indicateur) throws Exception {
        List<IndicateurRegion> indicateurRegions = null;
        Query query = em.createQuery("SELECT i FROM IndicateurRegion i WHERE i.idindicateur.idindicateur=:indicateur AND i.idindicateur.idinterventionpnds.region=true");
        query.setParameter("indicateur", indicateur.getIdindicateur());
        indicateurRegions = query.getResultList();
        return indicateurRegions;
    }

    @Override
    public List<IndicateurRegion> findByIndicateur(Indicateur indicateur, Region region) throws Exception {
        List<IndicateurRegion> indicateurRegions = null;
        Query query = em.createQuery("SELECT i FROM IndicateurRegion i WHERE i.idindicateur.idindicateur=:indicateur AND i.idregion.idregion=:region");
        query.setParameter("indicateur", indicateur.getIdindicateur()).setParameter("region", region.getIdregion());
        indicateurRegions = query.getResultList();
        return indicateurRegions;
    }

    @Override
    public List<IndicateurRegion> findByRegionSousaxe(Region region, Sousaxe sousaxe) throws Exception {
        List<IndicateurRegion> indicateurRegions = null;
        Query query = em.createQuery("SELECT i FROM IndicateurRegion i WHERE i.idregion.idregion=:region AND i.idindicateur.idinterventionpnds.idcategorieintervention.idsousaxe.idsousaxe=:sousaxe AND i.idindicateur.idinterventionpnds.region=true");
        query.setParameter("region", region.getIdregion()).setParameter("sousaxe", sousaxe.getIdsousaxe());
        indicateurRegions = query.getResultList();
        return indicateurRegions;
    }

    @Override
    public List<IndicateurRegion> findByRegionAxeObservation(Region region, Axe axe, int idobservation) throws Exception {
        List<IndicateurRegion> indicateurRegions = null;
        Query query = em.createQuery("SELECT i FROM IndicateurRegion i WHERE i.idregion.idregion=:region AND i.idindicateur.idinterventionpnds.idcategorieintervention.idsousaxe.idaxe.idaxe=:axe AND i.idobservation.idobservation=:observation AND i.idindicateur.idinterventionpnds.region=true");
        query.setParameter("region", region.getIdregion()).setParameter("axe", axe.getIdaxe()).setParameter("observation", idobservation);
        indicateurRegions = query.getResultList();
        return indicateurRegions;
    }

    @Override
    public List<IndicateurRegion> findByRegionSousaxeObservation(Region region, Sousaxe sousaxe, int idobservation) throws Exception {
        List<IndicateurRegion> indicateurRegions = null;
        Query query = em.createQuery("SELECT i FROM IndicateurRegion i WHERE i.idregion.idregion=:region AND i.idindicateur.idinterventionpnds.idcategorieintervention.idsousaxe.idsousaxe=:sousaxe AND i.idobservation.idobservation=:observation AND i.idindicateur.idinterventionpnds.region=true");
        query.setParameter("region", region.getIdregion()).setParameter("sousaxe", sousaxe.getIdsousaxe()).setParameter("observation", idobservation);
        indicateurRegions = query.getResultList();
        return indicateurRegions;
    }

    @Override
    public List<IndicateurRegion> findByRegion(Region region) throws Exception {
        List<IndicateurRegion> indicateurRegions = null;
        Query query = em.createQuery("SELECT i FROM IndicateurRegion i WHERE i.idregion.idregion=:region AND i.idindicateur.idinterventionpnds.region=true");
        query.setParameter("region", region.getIdregion());
        indicateurRegions = query.getResultList();
        return indicateurRegions;
    }

    @Override
    public List<IndicateurRegion> findByRegionIndicateurObservation(Region region, Indicateur indicateur, int idobservation) throws Exception {
        List<IndicateurRegion> indicateurRegions = null;
        Query query = em.createQuery("SELECT i FROM IndicateurRegion  i WHERE i.idregion.idregion=:region AND i.idindicateur.idindicateur=:indicateur AND i.idobservation.idobservation=:observation");
        query.setParameter("region", region.getIdregion()).setParameter("indicateur", indicateur.getIdindicateur()).setParameter("observation", idobservation);
        indicateurRegions = query.getResultList();
        return indicateurRegions;
    }

}
