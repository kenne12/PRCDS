/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Activite;
import entities.ActiviteTraceur;
import entities.ActiviteTraceurPdsd;
import entities.Interventionpnds;
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
public class ActiviteTraceurPdsdFacade extends AbstractFacade<ActiviteTraceurPdsd> implements ActiviteTraceurPdsdFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActiviteTraceurPdsdFacade() {
        super(ActiviteTraceurPdsd.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(a.idactiviteTraceurPdsd) FROM ActiviteTraceurPdsd a");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<ActiviteTraceurPdsd> findByActivitetraceur(ActiviteTraceur activiteTraceur) throws Exception {
        List<ActiviteTraceurPdsd> activiteTraceurPdsds = null;
        Query query = em.createQuery("SELECT a FROM ActiviteTraceurPdsd a WHERE a.idactiviteTraceur.idactiviteTraceur=:activitetraceur ");
        query.setParameter("activitetraceur", activiteTraceur.getIdactiviteTraceur());
        activiteTraceurPdsds = query.getResultList();
        return activiteTraceurPdsds;
    }

    @Override
    public ActiviteTraceurPdsd findByTraceurPdsd(ActiviteTraceur activiteTraceur, Activite activite) throws Exception {
        List<ActiviteTraceurPdsd> activiteTraceurPdsds = null;

        Query query = em.createQuery("SELECT a FROM ActiviteTraceurPdsd a WHERE a.idactiviteTraceur.idactiviteTraceur=:traceur AND a.idactivitePdsd.idactivite=:activite");
        query.setParameter("traceur", activiteTraceur.getIdactiviteTraceur()).setParameter("activite", activite.getIdactivite());

        activiteTraceurPdsds = query.getResultList();

        if (!activiteTraceurPdsds.isEmpty()) {
            return activiteTraceurPdsds.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<ActiviteTraceurPdsd> findByIntervention(Interventionpnds interventionpnds) throws Exception {
        List<ActiviteTraceurPdsd> activiteTraceurPdsds = null;
        Query query = em.createQuery("SELECT a FROM ActiviteTraceurPdsd a WHERE a.idactiviteTraceur.idinterventionpnds.idinterventionpnds=:intervention");
        query.setParameter("intervention", interventionpnds.getIdinterventionpnds());
        activiteTraceurPdsds = query.getResultList();
        return activiteTraceurPdsds;
    }

}
