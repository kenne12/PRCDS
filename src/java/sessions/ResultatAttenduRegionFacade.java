/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.District;
import entities.Indicateur;
import entities.Region;
import entities.ResultatAttenduRegion;
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
public class ResultatAttenduRegionFacade extends AbstractFacade<ResultatAttenduRegion> implements ResultatAttenduRegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ResultatAttenduRegionFacade() {
        super(ResultatAttenduRegion.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(r.idresultatAttenduRegion) FROM ResultatAttenduRegion r");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<ResultatAttenduRegion> findByRegion(Region region) throws Exception {
        List<ResultatAttenduRegion> resultatAttenduRegions = null;
        Query query = em.createQuery("SELECT r FROM ResultatAttenduRegion r WHERE r.idregion.idregion=:region ORDER BY r.idindicateur.idinterventionpnds.code , r.idindicateur.nomFr");
        query.setParameter("region", region.getIdregion());
        resultatAttenduRegions = query.getResultList();
        return resultatAttenduRegions;
    }

    @Override
    public List<ResultatAttenduRegion> findByIndicateur(Indicateur indicateur) throws Exception {
        List<ResultatAttenduRegion> resultatAttenduRegions = null;
        Query query = em.createQuery("SELECT r FROM ResultatAttenduRegion r WHERE r.idindicateur.idindicateur=:indicateur  ORDER BY r.idindicateur.idinterventionpnds.code , r.idindicateur.nomFr");
        query.setParameter("indicateur", indicateur.getIdindicateur());
        resultatAttenduRegions = query.getResultList();
        return resultatAttenduRegions;
    }

    @Override
    public List<ResultatAttenduRegion> findByIndicateur(Indicateur indicateteur, Region region) throws Exception {
        List<ResultatAttenduRegion> resultatAttenduRegions = null;
        Query query = em.createQuery("SELECT r FROM ResultatAttenduRegion r WHERE r.idindicateur.idindicateur=:indicateur AND r.idregion.idregion=:region  ORDER BY r.idindicateur.idinterventionpnds.code , r.idindicateur.nomFr");
        query.setParameter("indicateur", indicateteur.getIdindicateur()).setParameter("region", region.getIdregion());
        resultatAttenduRegions = query.getResultList();
        return resultatAttenduRegions;
    }

}
