/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Notationproblemeregion;
import entities.ProblemeRegion;
import entities.Sousrubriquenotationprobleme;
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
public class NotationproblemeregionFacade extends AbstractFacade<Notationproblemeregion> implements NotationproblemeregionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotationproblemeregionFacade() {
        super(Notationproblemeregion.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(p.idnotationproblemeregion) FROM Notationproblemeregion p");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<Notationproblemeregion> find(ProblemeRegion problemeRegion) throws Exception {
        List<Notationproblemeregion> notationproblemeregions = null;
        Query query = em.createQuery("SELECT n FROM Notationproblemeregion n WHERE n.idproblemeRegion.idproblemeRegion=:probleme");
        query.setParameter("probleme", problemeRegion.getIdproblemeRegion());
        notationproblemeregions = query.getResultList();
        return notationproblemeregions;
    }

    @Override
    public List<Notationproblemeregion> find(ProblemeRegion problemeRegion, Sousrubriquenotationprobleme sousrubriquenotationprobleme) throws Exception {
        List<Notationproblemeregion> notationproblemeregions = null;
        Query query = em.createQuery("SELECT n FROM Notationproblemeregion n WHERE n.idproblemeRegion.idproblemeRegion=:probleme AND n.idsousrubriquenotationprobleme.idsousrubriquenotationprobleme=:sousrubrique");
        query.setParameter("probleme", problemeRegion.getIdproblemeRegion()).setParameter("sousrubrique", sousrubriquenotationprobleme.getIdsousrubriquenotationprobleme());
        notationproblemeregions = query.getResultList();
        return notationproblemeregions;
    }

}
