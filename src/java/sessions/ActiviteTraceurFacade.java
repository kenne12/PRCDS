/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.ActiviteTraceur;
import entities.Interventionpnds;
import entities.Sousaxe;
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
public class ActiviteTraceurFacade extends AbstractFacade<ActiviteTraceur> implements ActiviteTraceurFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActiviteTraceurFacade() {
        super(ActiviteTraceur.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(a.idactiviteTraceur) FROM ActiviteTraceur a");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<ActiviteTraceur> findBySousaxe(Sousaxe sousaxe) throws Exception {
        List<ActiviteTraceur> activiteTraceurs = null;
        Query query = em.createQuery("SELECT a FROM ActiviteTraceur a WHERE a.idinterventionpnds.idcategorieintervention.idsousaxe.idsousaxe=:sousaxe ORDER BY a.idinterventionpnds.code");
        query.setParameter("sousaxe", sousaxe.getIdsousaxe());
        activiteTraceurs = query.getResultList();
        return activiteTraceurs;
    }

    @Override
    public List<ActiviteTraceur> findByIntervention(Interventionpnds interventionpnds) throws Exception {
        List<ActiviteTraceur> activiteTraceurs = null;
        Query query = em.createQuery("SELECT a FROM ActiviteTraceur a WHERE a.idinterventionpnds.idinterventionpnds=:intervention ORDER BY a.idinterventionpnds.code");
        query.setParameter("intervention", interventionpnds.getIdinterventionpnds());
        activiteTraceurs = query.getResultList();
        return activiteTraceurs;
    }

}
