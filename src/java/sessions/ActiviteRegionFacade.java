/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.ActiviteRegion;
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
public class ActiviteRegionFacade extends AbstractFacade<ActiviteRegion> implements ActiviteRegionFacadeLocal {
    
    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ActiviteRegionFacade() {
        super(ActiviteRegion.class);
    }
    
    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(a.idactiviteRegion) FROM ActiviteRegion a");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }
    
    @Override
    public List<ActiviteRegion> findAllRange() {
        List<ActiviteRegion> activiteRegions = null;
        Query query = em.createQuery("SELECT a FROM ActiviteRegion a ORDER BY a.nom ,a.coutglobal");
        activiteRegions = query.getResultList();
        return activiteRegions;
    }
    
    @Override
    public List<ActiviteRegion> findByProbleme(ProblemeRegion problemeRegion) throws Exception {
        List<ActiviteRegion> activiteRegions = null;
        Query query = em.createQuery("SELECT a FROM ActiviteRegion a WHERE a.idproblemeRegion.idproblemeRegion=:probleme");
        query.setParameter("probleme", problemeRegion.getIdproblemeRegion());
        activiteRegions = query.getResultList();
        return activiteRegions;
    }
    
    @Override
    public List<ActiviteRegion> findBySousAxe(Sousaxe sousaxe, Region region) throws Exception {
        List<ActiviteRegion> activiteRegions = null;
        Query query = em.createQuery("SELECT a FROM ActiviteRegion a WHERE a.idproblemeRegion.idindicateurRegion.idindicateur.idinterventionpnds.idcategorieintervention.idsousaxe.idsousaxe=:sousaxe AND a.idproblemeRegion.idindicateurRegion.idregion.idregion=:region ORDER BY a.idproblemeRegion.idindicateurRegion.idindicateur.idinterventionpnds.code");
        query.setParameter("sousaxe", sousaxe.getIdsousaxe()).setParameter("region", region.getIdregion());
        activiteRegions = query.getResultList();
        return activiteRegions;
    }
    
    @Override
    public List<ActiviteRegion> find(Region region, Sousaxe sousaxe, int observation) throws Exception {
        List<ActiviteRegion> activiteRegions = null;
        Query query = em.createQuery("SELECT a FROM ActiviteRegion a WHERE a.idproblemeRegion.idindicateurRegion.idregion.idregion=:region AND a.idproblemeRegion.idindicateurRegion.idindicateur.idinterventionpnds.idcategorieintervention.idsousaxe.idsousaxe=:sousaxe AND a.idproblemeRegion.idindicateurRegion.idobservation.idobservation=:observation ORDER BY a.idproblemeRegion.totalpoint ASC");
        query.setParameter("sousaxe", sousaxe.getIdsousaxe()).setParameter("region", region.getIdregion()).setParameter("observation", observation);
        activiteRegions = query.getResultList();
        return activiteRegions;
    }
    
    @Override
    public List<ActiviteRegion> findByRegion(Region region) throws Exception {
        List<ActiviteRegion> activiteRegions = null;
        Query query = em.createQuery("SELECT a FROM ActiviteRegion a WHERE a.idproblemeRegion.idindicateurRegion.idregion.idregion=:region");
        query.setParameter("region", region.getIdregion());
        activiteRegions = query.getResultList();
        return activiteRegions;
    }
    
    @Override
    public List<ActiviteRegion> findByRegionSousaxe(Region region, Sousaxe sousaxe, boolean appui) throws Exception {
        List<ActiviteRegion> activiteRegions = null;
        Query query = em.createQuery("SELECT a FROM ActiviteRegion a WHERE a.idregion=:region AND a.idproblemeRegion.idindicateurRegion.idindicateur.idinterventionpnds.idcategorieintervention.idsousaxe.idsousaxe=:sousaxe AND a.activiteAppui=:appui ORDER BY a.idintervention.code");
        query.setParameter("region", region.getIdregion()).setParameter("sousaxe", sousaxe.getIdsousaxe()).setParameter("appui", appui);
        activiteRegions = query.getResultList();
        return activiteRegions;
    }
    
    @Override
    public List<ActiviteRegion> findByActivitechronogramme(Sousaxe sousaxe, Region region) throws Exception {        
        List<ActiviteRegion> activiteRegions = null;
        //Query query = em.createQuery("SELECT a FROM ActiviteRegion a WHERE a.idregion=:region AND a.idintervention.idcategorieintervention.idsousaxe.idsousaxe=:sousaxe ORDER BY a.idintervention.code");
        Query query = em.createQuery("SELECT a FROM ActiviteRegion a WHERE a.idregion=:region AND a.idproblemeRegion.idindicateurRegion.idindicateur.idinterventionpnds.idcategorieintervention.idsousaxe.idsousaxe=:sousaxe ORDER BY a.idproblemeRegion.idindicateurRegion.idindicateur.idinterventionpnds.code");
        //Query query = em.createQuery("SELECT a FROM ActiviteRegion a WHERE (a.idproblemeRegion.idindicateurRegion.idindicateur.idinterventionpnds.idcategorieintervention.idsousaxe.idsousaxe=:sousaxe AND a.idproblemeRegion.idindicateurRegion.idregion.idregion=:region) OR (a.idregion=:region AND a.idintervention.idcategorieintervention.idsousaxe.idsousaxe=:sousaxe) ORDER BY a.idproblemeRegion.idindicateurRegion.idindicateur.idinterventionpnds.code");
        query.setParameter("sousaxe", sousaxe.getIdsousaxe()).setParameter("region", region.getIdregion());        
        activiteRegions = query.getResultList();
        return activiteRegions;        
    }
    
}
