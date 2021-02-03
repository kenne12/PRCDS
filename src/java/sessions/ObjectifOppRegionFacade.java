/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Interventionpnds;
import entities.ObjectifOppRegion;
import entities.Region;
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
public class ObjectifOppRegionFacade extends AbstractFacade<ObjectifOppRegion> implements ObjectifOppRegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ObjectifOppRegionFacade() {
        super(ObjectifOppRegion.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(o.idobjectifOppRegion) FROM ObjectifOppRegion o");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<ObjectifOppRegion> findByRegion(Region region) throws Exception {
        List<ObjectifOppRegion> objectifOppRegions = null;
        Query query = em.createQuery("SELECT o FROM ObjectifOppRegion o WHERE o.idregion.idregion=:region ORDER BY o.idintervention.idcategorieintervention.idsousaxe.idaxe.code,o.idintervention.idcategorieintervention.idsousaxe.code,o.idintervention.code");
        query.setParameter("region", region.getIdregion());
        objectifOppRegions = query.getResultList();
        return objectifOppRegions;
    }

    @Override
    public List<ObjectifOppRegion> findByRegion(Region region, Interventionpnds interventionpnds) throws Exception {
        List<ObjectifOppRegion> objectifOppRegions = null;
        Query query = em.createQuery("SELECT o FROM ObjectifOppRegion o WHERE o.idregion.idregion=:region AND o.idintervention.idinterventionpnds=:intervention ");
        query.setParameter("region", region.getIdregion()).setParameter("intervention", interventionpnds.getIdinterventionpnds());
        objectifOppRegions = query.getResultList();
        return objectifOppRegions;
    }

}
