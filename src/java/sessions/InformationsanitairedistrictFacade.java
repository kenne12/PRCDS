/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.District;
import entities.Informationsanitairedistrict;
import entities.Rubriqueinfosanitaire;
import entities.Structure;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author hp
 */
@Stateless
public class InformationsanitairedistrictFacade extends AbstractFacade<Informationsanitairedistrict> implements InformationsanitairedistrictFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InformationsanitairedistrictFacade() {
        super(Informationsanitairedistrict.class);
    }

    @Override
    public Integer nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(m.idinformationsanitairedistrict) FROM Informationsanitairedistrict m");
        Integer resultat = (Integer) query.getSingleResult();
        if (resultat == null) {
            return 1;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<Informationsanitairedistrict> find(Structure structure, Rubriqueinfosanitaire rubriqueinfosanitaire, District district) throws Exception {
        List<Informationsanitairedistrict> informationsanitairedistricts = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM Informationsanitairedistrict m WHERE m.idstructure.iddistrict.iddistrict=:district AND m.idstructure.idstructure=:structure AND m.idrubriqueinfosanitaire.idrubriqueinfosanitaire=:rubriqueinfosanitaire");
        query.setParameter("district", district.getIddistrict()).setParameter("structure", structure.getIdstructure()).setParameter("rubriqueinfosanitaire", rubriqueinfosanitaire.getIdrubriqueinfosanitaire());
        if (!query.getResultList().isEmpty()) {
            informationsanitairedistricts = query.getResultList();
        }
        return informationsanitairedistricts;
    }

    @Override
    public List<Informationsanitairedistrict> find(Rubriqueinfosanitaire rubriqueinfosanitaire, District district) throws Exception {
        List<Informationsanitairedistrict> informationsanitairedistricts = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM Informationsanitairedistrict m WHERE m.idstructure.idairesante.iddistrict.iddistrict=:district AND m.idrubriqueinfosanitaire.idrubriqueinfosanitaire=:rubriqueinfosanitaire");
        query.setParameter("district", district.getIddistrict()).setParameter("rubriqueinfosanitaire", rubriqueinfosanitaire.getIdrubriqueinfosanitaire());
        if (!query.getResultList().isEmpty()) {
            informationsanitairedistricts = query.getResultList();
        }
        return informationsanitairedistricts;
    }

    @Override
    public List<Informationsanitairedistrict> find1(Rubriqueinfosanitaire rubriqueinfosanitaire, Structure s) throws Exception {
        List<Informationsanitairedistrict> informationsanitairedistricts = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM Informationsanitairedistrict m WHERE m.idstructure.idstructure=:structure AND m.idrubriqueinfosanitaire.idrubriqueinfosanitaire=:rubriqueinfosanitaire");
        query.setParameter("structure", s.getIdstructure()).setParameter("rubriqueinfosanitaire", rubriqueinfosanitaire.getIdrubriqueinfosanitaire());
        if (!query.getResultList().isEmpty()) {
            informationsanitairedistricts = query.getResultList();
        }
        return informationsanitairedistricts;
    }

}
