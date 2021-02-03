/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Axe;
import entities.Indicateur;
import entities.IndicateurRegion;
import entities.ProblemeRegion;
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
public class ProblemeRegionFacade extends AbstractFacade<ProblemeRegion> implements ProblemeRegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProblemeRegionFacade() {
        super(ProblemeRegion.class);
    }

    @Override
    public Integer nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(p.idproblemeRegion) FROM ProblemeRegion p");
        Integer resultat = (Integer) query.getSingleResult();
        if (resultat == null) {
            return 1;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<ProblemeRegion> find(Indicateur indicateur, Region region, Sousaxe sousaxe) throws Exception {
        List<ProblemeRegion> problemeRegions = null;
        Query query = em.createQuery("SELECT p FROM ProblemeRegion p WHERE p.idindicateurRegion.idregion.idregion=:region AND p.idindicateurRegion.idindicateur.idinterventionpnds.idcategorieintervention.idsousaxe.idsousaxe=:sousaxe AND p.idindicateurRegion.idindicateur.idindicateur=:indicateur");
        query.setParameter("sousaxe", sousaxe.getIdsousaxe()).setParameter("region", region.getIdregion());
        problemeRegions = query.getResultList();
        return problemeRegions;
    }

    @Override
    public List<ProblemeRegion> find(Region region, Sousaxe sousaxe, int observation) throws Exception {
        List<ProblemeRegion> problemeRegions = null;
        Query query = em.createQuery("SELECT p FROM ProblemeRegion p WHERE p.idindicateurRegion.idregion.idregion=:region AND p.idindicateurRegion.idindicateur.idinterventionpnds.idcategorieintervention.idsousaxe.idsousaxe=:sousaxe AND p.idindicateurRegion.idobservation.idobservation=:observation ORDER BY p.totalpoint DESC");
        query.setParameter("sousaxe", sousaxe.getIdsousaxe()).setParameter("region", region.getIdregion()).setParameter("observation", observation);
        problemeRegions = query.getResultList();
        return problemeRegions;
    }

    @Override
    public List<ProblemeRegion> find(Region region, Sousaxe sousaxe) throws Exception {
        List<ProblemeRegion> problemeRegions = null;
        Query query = em.createQuery("SELECT p FROM ProblemeRegion p WHERE p.idindicateurRegion.idregion.idregion=:region AND p.idindicateurRegion.idindicateur.idinterventionpnds.idcategorieintervention.idsousaxe.idsousaxe=:sousaxe ORDER BY p.totalpoint DESC");
        query.setParameter("sousaxe", sousaxe.getIdsousaxe()).setParameter("region", region.getIdregion());
        problemeRegions = query.getResultList();
        return problemeRegions;
    }

    @Override
    public List<ProblemeRegion> find(IndicateurRegion indicateurRegion) throws Exception {
        List<ProblemeRegion> problemeRegions = null;
        Query query = em.createQuery("SELECT p FROM ProblemeRegion p WHERE p.idindicateurRegion.idindicateurRegion=:indicateur");
        query.setParameter("indicateur", indicateurRegion.getIdindicateurRegion());
        problemeRegions = query.getResultList();
        return problemeRegions;
    }

    @Override
    public List<ProblemeRegion> find(Indicateur indicateur, Region region, int observation) throws Exception {
        List<ProblemeRegion> problemeRegions = null;
        Query query = em.createQuery("SELECT p FROM ProblemeRegion p WHERE p.idindicateurRegion.idregion.idregion=:region AND p.idindicateurRegion.idindicateur.idindicateur=:indicateur AND p.idindicateurRegion.idobservation.idobservation=:observation ORDER BY p.totalpoint DESC");
        query.setParameter("indicateur", indicateur.getIdindicateur()).setParameter("region", region.getIdregion()).setParameter("observation", observation);
        problemeRegions = query.getResultList();
        return problemeRegions;
    }

    @Override
    public List<ProblemeRegion> findByAxeRegion(Axe axe, Region region, int observation) throws Exception {
        List<ProblemeRegion> problemeRegions = null;
        Query query = em.createQuery("SELECT p FROM ProblemeRegion p WHERE p.idindicateurRegion.idindicateur.idinterventionpnds.idcategorieintervention.idsousaxe.idaxe.idaxe=:axe AND p.idindicateurRegion.idregion.idregion=:region AND p.idindicateurRegion.idobservation.idobservation=:observation");
        query.setParameter("axe", axe.getIdaxe()).setParameter("region", region.getIdregion()).setParameter("observation", observation);
        problemeRegions = query.getResultList();
        return problemeRegions;
    }

    @Override
    public List<ProblemeRegion> findBySousAxeRegion(Sousaxe sousaxe, Region region, int observation) throws Exception {
        List<ProblemeRegion> problemeRegions = null;
        Query query = em.createQuery("SELECT p FROM ProblemeRegion p WHERE p.idindicateurRegion.idindicateur.idinterventionpnds.idcategorieintervention.idsousaxe.idsousaxe=:sousaxe AND p.idindicateurRegion.idregion.idregion=:region AND p.idindicateurRegion.idobservation.idobservation=:observation");
        query.setParameter("sousaxe", sousaxe.getIdsousaxe()).setParameter("region", region.getIdregion()).setParameter("observation", observation);
        problemeRegions = query.getResultList();
        return problemeRegions;
    }

}
