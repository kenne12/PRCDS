/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Region;
import entities.Hospitalisation;
import entities.HospitalisationRegion;
import entities.Rubriquehospitalisation;
import entities.Structure;
import java.util.ArrayList;
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
public class HospitalisationRegionFacade extends AbstractFacade<HospitalisationRegion> implements HospitalisationRegionFacadeLocal {
    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HospitalisationRegionFacade() {
        super(HospitalisationRegion.class);
    }
      @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(m.idhospitalisationRegion) FROM HospitalisationRegion m");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<HospitalisationRegion> find(Hospitalisation hospitalisation, Rubriquehospitalisation rubriquehospitalisation) throws Exception {
        List<HospitalisationRegion> hospitalisationRegions = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM HospitalisationRegion m WHERE m.idhospitalisation.idhospitalisation=:hospitalisation AND m.idrubriquehospitalisation.idrubriquehospitalisation=:rubriquehospitalisation");
        query.setParameter("hospitalisation", hospitalisation.getIdhospitalisation()).setParameter("rubriquehospitalisation", rubriquehospitalisation.getIdrubriquehospitalisation());
        if (!query.getResultList().isEmpty()) {
            hospitalisationRegions = query.getResultList();
        }
        return hospitalisationRegions;
    }

    @Override
    public List<HospitalisationRegion> find(Hospitalisation hospitalisation) throws Exception {
        List<HospitalisationRegion> hospitalisationRegions = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM HospitalisationRegion m WHERE m.idhospitalisation.idhospitalisation=:hospitalisation");
        query.setParameter("hospitalisation", hospitalisation.getIdhospitalisation());
        if (!query.getResultList().isEmpty()) {
            hospitalisationRegions = query.getResultList();
        }
        return hospitalisationRegions;
    }

    @Override
    public List<HospitalisationRegion> findByRegion(int region) {
        List<HospitalisationRegion> hospitalisationRegions = null;
        try {
            Query query = em.createQuery("SELECT s FROM HospitalisationRegion s WHERE s.idregion.idregion=:region");
            query.setParameter("region", region);
            hospitalisationRegions = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hospitalisationRegions;
    }

    @Override
    public List<HospitalisationRegion> find(Hospitalisation hospitalisation, Rubriquehospitalisation rubriquehospitalisation, Region region) throws Exception {
        List<HospitalisationRegion> hospitalisationRegions = new ArrayList<>();
        //Query query = em.createQuery("SELECT m FROM HospitalisationRegion m WHERE m.idregion.idregion=:region AND m.idhospitalisation.idhospitalisation=:hospitalisation AND m.idrubriquehospitalisation.idrubriquehospitalisation=:rubriquehospitalisation and m.consolide=true");
        Query query = em.createQuery("SELECT m FROM HospitalisationRegion m WHERE m.idregion.idregion=:region AND m.idhospitalisation.idhospitalisation=:hospitalisation AND m.idrubriquehospitalisation.idrubriquehospitalisation=:rubriquehospitalisation");
        query.setParameter("hospitalisation", hospitalisation.getIdhospitalisation()).setParameter("rubriquehospitalisation", rubriquehospitalisation.getIdrubriquehospitalisation()).setParameter("region", region.getIdregion());
        if (!query.getResultList().isEmpty()) {
            hospitalisationRegions = query.getResultList();
        }
        return hospitalisationRegions;
    }
    
    @Override
    public List<HospitalisationRegion> find(Hospitalisation hospitalisation, Rubriquehospitalisation rubriquehospitalisation, Structure s) throws Exception {
        List<HospitalisationRegion> hospitalisationRegions = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM HospitalisationRegion m WHERE m.idstructure.idstructure=:structure AND m.idhospitalisation.idhospitalisation=:hospitalisation AND m.idrubriquehospitalisation.idrubriquehospitalisation=:rubriquehospitalisation");
        query.setParameter("hospitalisation", hospitalisation.getIdhospitalisation()).setParameter("rubriquehospitalisation", rubriquehospitalisation.getIdrubriquehospitalisation()).setParameter("structure", s.getIdstructure());
        if (!query.getResultList().isEmpty()) {
            hospitalisationRegions = query.getResultList();
        }
        return hospitalisationRegions;
    }

}
