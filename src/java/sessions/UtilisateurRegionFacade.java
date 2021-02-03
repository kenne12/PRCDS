/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.UtilisateurRegion;
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
public class UtilisateurRegionFacade extends AbstractFacade<UtilisateurRegion> implements UtilisateurRegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtilisateurRegionFacade() {
        super(UtilisateurRegion.class);
    }

    @Override
    public Long nextId() {
        Query query = em.createQuery("SELECT MAX(u.idutilisateurRegion) FROM UtilisateurRegion u");
        Long resultat = (Long) query.getSingleResult();
        if (resultat != null) {
            return resultat + 1;
        } else {
            return 1L;
        }
    }

    @Override
    public UtilisateurRegion findByUtilisateurRegion(int utilisateur, int region) {
        UtilisateurRegion utilisateurRegion = null;
        try {
            List<UtilisateurRegion> utilisateurRegions = null;
            Query query = em.createQuery("SELECT u FROM UtilisateurRegion u WHERE u.idutilisateur.idutilisateur=:utilisateur AND u.idregion.idregion=:region");
            query.setParameter("utilisateur", utilisateur).setParameter("region", region);
            utilisateurRegions = query.getResultList();
            if (!utilisateurRegions.isEmpty()) {
                utilisateurRegion = utilisateurRegions.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return utilisateurRegion;
    }

    @Override
    public List<UtilisateurRegion> get() {
        List<UtilisateurRegion> utilisateurRegions = new ArrayList<>();
        try {
            Query query = em.createQuery("SELECT u FROM UtilisateurRegion u");
            utilisateurRegions = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return utilisateurRegions;
    }

    @Override
    public List<UtilisateurRegion> findByUtilisateur(int utilisateur) {
        List<UtilisateurRegion> utilisateurRegions = null;
        try {
            Query query = em.createQuery("SELECT u FROM UtilisateurRegion u WHERE u.idutilisateur.idutilisateur=:utilisateur");
            query.setParameter("utilisateur", utilisateur);
            utilisateurRegions = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return utilisateurRegions;
    }

    @Override
    public List<UtilisateurRegion> findAllRange() {
        List<UtilisateurRegion> utilisateurRegions = null;
        Query query = em.createQuery("SELECT u FROM UtilisateurRegion u ORDER BY u.idutilisateur.nom , u.idutilisateur.prenom , u.idregion.nomEn");
        utilisateurRegions = query.getResultList();
        return utilisateurRegions;
    }

}
