/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Sousrubriquenotationprobleme;
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
public class SousrubriquenotationproblemeFacade extends AbstractFacade<Sousrubriquenotationprobleme> implements SousrubriquenotationproblemeFacadeLocal {
    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SousrubriquenotationproblemeFacade() {
        super(Sousrubriquenotationprobleme.class);
    }
    
        
    @Override
    public int nextId() {
        try {
            Query query = em.createQuery("SELECT MAX(n.idsousrubriquenotationprobleme) FROM Sousrubriquenotationprobleme n");
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
    public List<Sousrubriquenotationprobleme> findByNom(String nom) {
        List<Sousrubriquenotationprobleme> naturecontencieus = null;
        try {
            Query query = em.createNamedQuery("Sousrubriquenotationprobleme.findByNomFr");
            query.setParameter("nom", nom);
            naturecontencieus = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return naturecontencieus;
    }

}
