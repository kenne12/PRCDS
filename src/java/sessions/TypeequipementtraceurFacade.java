/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Typeequipementtraceur;
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
public class TypeequipementtraceurFacade extends AbstractFacade<Typeequipementtraceur> implements TypeequipementtraceurFacadeLocal {
    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypeequipementtraceurFacade() {
        super(Typeequipementtraceur.class);
    }
       @Override
    public int nextId() {
        try {
            Query query = em.createQuery("SELECT MAX(n.idtypeequipementtraceur) FROM Typeequipementtraceur n");
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
    public List<Typeequipementtraceur> findByNom(String nom) {
        List<Typeequipementtraceur> naturecontencieus = null;
        try {
            Query query = em.createNamedQuery("Typeequipementtraceur.findByNomFr");
            query.setParameter("nom", nom);
            naturecontencieus = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return naturecontencieus;
    }
}
