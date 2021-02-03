/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Unite;
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
public class UniteFacade extends AbstractFacade<Unite> implements UniteFacadeLocal {
    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UniteFacade() {
        super(Unite.class);
    }
    

    @Override
    public int nextId() {
        try {
            Query query = em.createQuery("SELECT MAX(u.idunite) FROM Unite u");
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
    public List<Unite> findByNom(String nom) {
        List<Unite> unites = null;
        try {
            Query query = em.createQuery("SELECT u FROM Unite u WHERE UPPER(u.nom)=UPPER(:nom)");
            query.setParameter("nom", nom);
            unites = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unites;
    }    
}
