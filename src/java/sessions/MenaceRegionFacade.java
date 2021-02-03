/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.FfomRegion;
import entities.MenaceRegion;
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
public class MenaceRegionFacade extends AbstractFacade<MenaceRegion> implements MenaceRegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MenaceRegionFacade() {
        super(MenaceRegion.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(m.idmenaceRegion) FROM MenaceRegion m");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<MenaceRegion> findByFfom(FfomRegion ffom) throws Exception {
        List<MenaceRegion> menaces = null;
        Query query = em.createQuery("SELECT m FROM MenaceRegion m WHERE m.idffomRegion.idffomRegion=:ffom");
        query.setParameter("ffom", ffom.getIdffomRegion());
        menaces = query.getResultList();
        return menaces;
    }

}
