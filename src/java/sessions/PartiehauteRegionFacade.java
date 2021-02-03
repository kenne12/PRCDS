/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.PartiehauteRegion;
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
public class PartiehauteRegionFacade extends AbstractFacade<PartiehauteRegion> implements PartiehauteRegionFacadeLocal {
    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PartiehauteRegionFacade() {
        super(PartiehauteRegion.class);
    }
       @Override
    public int nextId() {
        try {
            Query query = em.createQuery("SELECT MAX(e.idpartiehauteRegion) FROM PartiehauteRegion e");
            List listObj = query.getResultList();
            if (!listObj.isEmpty()) {
                return ((Integer) listObj.get(0)) + 1;
            } else {
                return 1;
            }
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public List<PartiehauteRegion> findByRegion(int region) {
        List<PartiehauteRegion> partiehauteregions = null;
        try {
            Query query = em.createQuery("SELECT s FROM PartiehauteRegion s WHERE s.idregion.idregion=:region");
            query.setParameter("region", region);
            partiehauteregions = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return partiehauteregions;
    }


    @Override
    public List<PartiehauteRegion> findByIntroduction(String introduction) {
        List<PartiehauteRegion> partiehauteregions = null;
        try {
            Query query = em.createNamedQuery("PartiehauteRegion.findByIntroduction");
            query.setParameter("introduction", introduction);
            partiehauteregions = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return partiehauteregions;
    }
}
