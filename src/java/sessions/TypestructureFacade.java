/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Typestructure;
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
public class TypestructureFacade extends AbstractFacade<Typestructure> implements TypestructureFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypestructureFacade() {
        super(Typestructure.class);
    }

    @Override
    public int nextId() {
        try {
            Query query = em.createQuery("SELECT MAX(n.idtypestructure) FROM Typestructure n");
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
    public List<Typestructure> findByNom(String nom) {
        List<Typestructure> typestructures = null;
        try {
            Query query = em.createNamedQuery("Typestructure.findByNom");
            query.setParameter("nom", nom);
            typestructures = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return typestructures;
    }

    @Override
    public List<Typestructure> findByDistrict() {
        List<Typestructure> typestructures = null;
        Query query = em.createQuery("SELECT t FROM Typestructure t WHERE t.district=true ORDER BY t.nomFr");
        typestructures = query.getResultList();
        return typestructures;
    }

    @Override
    public List<Typestructure> findByRegional() {
        List<Typestructure> typestructures = null;
        Query query = em.createQuery("SELECT t FROM Typestructure t WHERE t.regional=true ORDER BY t.nomFr");
        typestructures = query.getResultList();
        return typestructures;
    }

    @Override
    public List<Typestructure> findByCentral() {
        List<Typestructure> typestructures = null;
        Query query = em.createQuery("SELECT t FROM Typestructure t WHERE t.central=true ORDER BY t.nomFr");
        typestructures = query.getResultList();
        return typestructures;
    }
}
