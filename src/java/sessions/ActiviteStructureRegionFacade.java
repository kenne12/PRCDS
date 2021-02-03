/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Activite;
import entities.ActiviteRegion;
import entities.ActiviteStructure;
import entities.ActiviteStructureRegion;
import entities.Annee;
import entities.Probleme;
import entities.ProblemeRegion;
import entities.Structure;
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
public class ActiviteStructureRegionFacade extends AbstractFacade<ActiviteStructureRegion> implements ActiviteStructureRegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActiviteStructureRegionFacade() {
        super(ActiviteStructureRegion.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(a.idactiviteStructureRegion) FROM ActiviteStructureRegion a");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<ActiviteStructureRegion> find(ProblemeRegion problemeRegion) throws Exception {
        List<ActiviteStructureRegion> activiteStructureRegions = null;
        Query query = em.createQuery("SELECT a FROM  ActiviteStructureRegion a WHERE a.idactiviteRegion.idproblemeRegion.idproblemeRegion=:probleme");
        query.setParameter("probleme", problemeRegion.getIdproblemeRegion());
        activiteStructureRegions = query.getResultList();
        return activiteStructureRegions;
    }

    @Override
    public List<ActiviteStructureRegion> find(ActiviteRegion activiteRegion) throws Exception {
        List<ActiviteStructureRegion> activiteStructureRegions = null;
        Query query = em.createQuery("SELECT a FROM  ActiviteStructureRegion   a WHERE a.idactiviteRegion.idactiviteRegion=:activite");
        query.setParameter("activite", activiteRegion.getIdactiviteRegion());
        activiteStructureRegions = query.getResultList();
        return activiteStructureRegions;
    }

    @Override
    public List<ActiviteStructureRegion> find(Structure structure, Annee annee) throws Exception {
        List<ActiviteStructureRegion> activiteStructureRegions = null;
        Query query = em.createQuery("SELECT a FROM  ActiviteStructureRegion a WHERE a.idannee.idannee=:annee AND a.idstructure.idstructure=:structure");
        query.setParameter("annee", annee.getIdannee()).setParameter("structure", structure.getIdstructure());
        activiteStructureRegions = query.getResultList();
        return activiteStructureRegions;
    }

    @Override
    public List<ActiviteStructureRegion> find(Structure structure, Boolean etat) throws Exception {
        List<ActiviteStructureRegion> activiteStructureRegions = null;
        Query query = em.createQuery("SELECT a FROM  ActiviteStructureRegion a WHERE a.idstructure.idstructure=:structure AND a.programe=true ORDER BY a.idactiviteRegion.nom");
        query.setParameter("structure", structure.getIdstructure());
        activiteStructureRegions = query.getResultList();
        return activiteStructureRegions;
    }

    @Override
    public List<ActiviteStructureRegion> find(Structure structure, ActiviteRegion activiteRegion) throws Exception {
        List<ActiviteStructureRegion> activiteStructureRegions = null;
        Query query = em.createQuery("SELECT a FROM ActiviteStructure a WHERE a.idactivite.idactivite=:activite AND a.idstructure.idstructure=:structure");
        query.setParameter("activite", activiteRegion.getIdactiviteRegion()).setParameter("structure", structure.getIdstructure());
        activiteStructureRegions = query.getResultList();
        return activiteStructureRegions;
    }

}
