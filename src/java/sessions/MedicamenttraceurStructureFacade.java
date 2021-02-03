/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Annee;
import entities.District;
import entities.Medicamenttraceur;
import entities.MedicamenttraceurStructure;
import entities.Structure;
import java.util.ArrayList;
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
public class MedicamenttraceurStructureFacade extends AbstractFacade<MedicamenttraceurStructure> implements MedicamenttraceurStructureFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MedicamenttraceurStructureFacade() {
        super(MedicamenttraceurStructure.class);
    }

    @Override
    public Integer nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(m.idmedicamenttraceurStructure) FROM MedicamenttraceurStructure m");
        Integer resultat = (Integer) query.getSingleResult();
        if (resultat == null) {
            return 1;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<MedicamenttraceurStructure> find(Structure structure, Medicamenttraceur medicamenttraceur, Annee annee) throws Exception {
        List<MedicamenttraceurStructure> medicamenttraceurStructures = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM MedicamenttraceurStructure m WHERE m.idstructure.idstructure=:structure AND m.idmedicamenttraceur.idmedicamenttraceur=:medicament AND m.idannee.idannee=:annee");
        query.setParameter("structure", structure.getIdstructure()).setParameter("medicament", medicamenttraceur.getIdmedicamenttraceur()).setParameter("annee", annee.getIdannee());
        if (!query.getResultList().isEmpty()) {
            medicamenttraceurStructures = query.getResultList();
        }
        return medicamenttraceurStructures;
    }

    @Override
    public List<MedicamenttraceurStructure> find(District district, Medicamenttraceur medicamenttraceur, Annee annee) throws Exception {
        List<MedicamenttraceurStructure> medicamenttraceurStructures = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM MedicamenttraceurStructure m WHERE m.idstructure.idairesante.iddistrict.iddistrict=:district AND m.idmedicamenttraceur.idmedicamenttraceur=:medicament AND m.idannee.idannee=:annee");
        query.setParameter("district", district.getIddistrict()).setParameter("medicament", medicamenttraceur.getIdmedicamenttraceur()).setParameter("annee", annee.getIdannee());
        if (!query.getResultList().isEmpty()) {
            medicamenttraceurStructures = query.getResultList();
        }
        return medicamenttraceurStructures;
    }

    @Override
    public List<MedicamenttraceurStructure> find(Structure structure, Annee annee) throws Exception {
        List<MedicamenttraceurStructure> medicamenttraceurStructures = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM MedicamenttraceurStructure m WHERE m.idstructure.idstructure=:structure AND m.idannee.idannee=:annee");
        query.setParameter("structure", structure.getIdstructure()).setParameter("annee", annee.getIdannee());
        if (!query.getResultList().isEmpty()) {
            medicamenttraceurStructures = query.getResultList();
        }
        return medicamenttraceurStructures;
    }

}
