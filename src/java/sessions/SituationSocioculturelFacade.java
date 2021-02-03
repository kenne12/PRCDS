/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.SituationSocioculturel;
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
public class SituationSocioculturelFacade extends AbstractFacade<SituationSocioculturel> implements SituationSocioCulturelFacadeLocal {
    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SituationSocioculturelFacade() {
        super(SituationSocioculturel.class);
    }
    
        @Override
    public int nextId() {
        try {
            Query query = em.createQuery("SELECT MAX(e.idsitutationScRegion) FROM SituationSocioculturel e");
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
    public List<SituationSocioculturel> findByRegion(int region) {
        List<SituationSocioculturel> situationSocioCulturels = null;
        try {
            Query query = em.createQuery("SELECT s FROM SituationSocioculturel s WHERE s.idregion.idregion=:region");
            query.setParameter("region", region);
            situationSocioCulturels = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return situationSocioCulturels;
    }
    
    
     @Override
    public List<SituationSocioculturel> findByEducation(String introduction) {
        List<SituationSocioculturel> situationSocioCulturels = null;
        try {
            Query query = em.createNamedQuery("SituationSocioculturel.findByEducation");
            query.setParameter("introduction", introduction);
            situationSocioCulturels = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return situationSocioCulturels;
    }
    
}
