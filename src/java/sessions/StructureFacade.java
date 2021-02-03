/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.District;
import entities.Region;
import entities.Structure;
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
public class StructureFacade extends AbstractFacade<Structure> implements StructureFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StructureFacade() {
        super(Structure.class);
    }

    @Override
    public Integer nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(s.idstructure) FROM Structure s");
        Integer resultat = (Integer) query.getSingleResult();
        if (resultat == null) {
            return 1;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<Structure> findByNom(District district, String nom) {
        List<Structure> structures = null;
        try {
            Query query = em.createQuery("SELECT s FROM Structure s WHERE UPPER(s.nomFr)=UPPER(:nom) AND s.iddistrict=:district ORDER BY s.nomFr");
            query.setParameter("district", district.getIddistrict());
            query.setParameter("nom", nom);
            structures = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return structures;
    }

    @Override
    public List<Structure> findByNom(Region region, String nom) {
        List<Structure> structures = null;
        try {
            Query query = em.createQuery("SELECT s FROM Structure s WHERE UPPER(s.nomFr)=UPPER(:nom) AND s.idregion.idregion=:region ORDER BY s.nomFr");
            query.setParameter("region", region.getIdregion());
            query.setParameter("nom", nom);
            structures = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return structures;
    }

    @Override
    public List<Structure> findByDistrict(int district) {
        List<Structure> structures = null;
        try {
            Query query = em.createQuery("SELECT s FROM Structure s WHERE s.idairesante.iddistrict.iddistrict=:district ORDER BY s.nomFr");
            query.setParameter("district", district);
            structures = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return structures;
    }

    @Override
    public List<Structure> find(int district, Typestructure typestructure) throws Exception {
        List<Structure> structures = null;
        Query query = em.createQuery("SELECT s FROM Structure s WHERE s.idairesante.iddistrict.iddistrict=:district AND s.idtypestructure.idtypestructure=:typestructure ORDER BY s.nomFr");
        query.setParameter("district", district).setParameter("typestructure", typestructure.getIdtypestructure());
        structures = query.getResultList();
        return structures;
    }

    @Override
    public List<Structure> find(Region region, Integer niveauPyramide, Typestructure typestructure) throws Exception {
        List<Structure> structures = null;
        Query query = em.createQuery("SELECT s FROM Structure s WHERE s.idregion.idregion=:region AND s.idtypestructure.idtypestructure=:typestructure AND s.idniveaupyramide.idniveaupyramide=:niveau ORDER BY s.nomFr");
        query.setParameter("region", region.getIdregion()).setParameter("typestructure", typestructure.getIdtypestructure()).setParameter("niveau", niveauPyramide);
        structures = query.getResultList();
        return structures;
    }

    @Override
    public List<Structure> find(Typestructure typestructure) throws Exception {
        List<Structure> structures = null;
        Query query = em.createQuery("SELECT s FROM Structure s WHERE s.idtypestructure.idtypestructure=:typestructure ORDER BY s.nomFr");
        query.setParameter("typestructure", typestructure.getIdtypestructure());
        structures = query.getResultList();
        return structures;
    }

    @Override
    public List<Structure> findByRegionNiveau(Region region, Integer niveauPyramide) throws Exception {
        List<Structure> structures = null;
        Query query = em.createQuery("SELECT s FROM Structure s WHERE s.idregion.idregion=:region AND s.idniveaupyramide.idniveaupyramide=:niveau ORDER BY s.nomFr");
        query.setParameter("region", region.getIdregion()).setParameter("niveau", niveauPyramide);
        structures = query.getResultList();
        return structures;
    }

}
