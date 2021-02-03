/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Region;
import entities.InformationsanitaireRegion;
import entities.InformationsanitaireRegion;
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
 * @author Abdel BEIN-INFO
 */
@Stateless
public class InformationsanitaireRegionFacade extends AbstractFacade<InformationsanitaireRegion> implements InformationsanitaireRegionFacadeLocal {
    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InformationsanitaireRegionFacade() {
        super(InformationsanitaireRegion.class);
    }
     @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(m.idinformationsanitaireRegion) FROM InformationsanitaireRegion m");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<InformationsanitaireRegion> find(Structure structure, Rubriqueinfosanitaire rubriqueinfosanitaire, Region region) throws Exception {
        List<InformationsanitaireRegion> informationsanitaireRegions = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM InformationsanitaireRegion m WHERE m.idregion.idregion=:region AND m.idstructure.idstructure=:structure AND m.idrubriqueinfosanitaire.idrubriqueinfosanitaire=:rubriqueinfosanitaire");
        query.setParameter("region",region.getIdregion()).setParameter("structure", structure.getIdstructure()).setParameter("rubriqueinfosanitaire",rubriqueinfosanitaire.getIdrubriqueinfosanitaire());
        if (!query.getResultList().isEmpty()) {
            informationsanitaireRegions = query.getResultList();
        }
        return informationsanitaireRegions;
    }
}
