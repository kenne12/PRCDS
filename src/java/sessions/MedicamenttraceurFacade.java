/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Medicamenttraceur;
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
public class MedicamenttraceurFacade extends AbstractFacade<Medicamenttraceur> implements MedicamenttraceurFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MedicamenttraceurFacade() {
        super(Medicamenttraceur.class);
    }

    @Override
    public List<Medicamenttraceur> findByNom(String nom) {
        List<Medicamenttraceur> medicamenttraceurs = null;
        try {
            Query query = em.createNamedQuery("Medicamenttraceur.findByNom");
            query.setParameter("nom", nom);
            medicamenttraceurs = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return medicamenttraceurs;
    }

    @Override
    public int nextId() {
        try {
            Query query = em.createQuery("SELECT MAX(e.idmedicamenttraceur) FROM Medicamenttraceur e");
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
    public List<Medicamenttraceur> findAllRange() {
        List<Medicamenttraceur> medicamenttraceurs = null;
        Query query = em.createQuery("SELECT m FROM Medicamenttraceur m ORDER BY m.nomFr");
        medicamenttraceurs = query.getResultList();
        return medicamenttraceurs;
    }

    @Override
    public List<Medicamenttraceur> findAllRangeRegion() {
        List<Medicamenttraceur> medicamenttraceurs = null;
        Query query = em.createQuery("SELECT m FROM Medicamenttraceur m WHERE m.prcds=true ORDER BY m.nomFr");
        medicamenttraceurs = query.getResultList();
        return medicamenttraceurs;
    }
}
