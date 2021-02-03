/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Annee;
import entities.CommentaireRegion;
import entities.CommentaireRegion;
import entities.Region;
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
public class CommentaireRegionFacade extends AbstractFacade<CommentaireRegion> implements CommentaireRegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommentaireRegionFacade() {
        super(CommentaireRegion.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(m.idcommentaireRegion) FROM CommentaireRegion m");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<CommentaireRegion> find(Region region, Integer numero) throws Exception {
        List<CommentaireRegion> commentaireRegions = null;
        Query query = em.createQuery("SELECT c FROM CommentaireRegion c WHERE c.idregion.idregion=:region AND c.numerotab=:numero");
        query.setParameter("region", region.getIdregion()).setParameter("numero", numero);
        commentaireRegions = query.getResultList();
        return commentaireRegions;
    }

    @Override
    public List<CommentaireRegion> find(Region region, Annee annee, Integer numero) throws Exception {
        List<CommentaireRegion> commentaireRegions = null;
        Query query = em.createQuery("SELECT c FROM CommentaireRegion c WHERE c.idregion.idregion=:region AND c.idannee.idannee=:annee AND c.numerotab=:numero");
        query.setParameter("region", region.getIdregion()).setParameter("annee", annee.getIdannee()).setParameter("numero", numero);
        commentaireRegions = query.getResultList();
        return commentaireRegions;
    }

}
