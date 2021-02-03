/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Annee;
import entities.PopulationRegion;
import entities.Region;
import entities.Rubriquepopulation;
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
public class PopulationRegionFacade extends AbstractFacade<PopulationRegion> implements PopulationRegionFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PopulationRegionFacade() {
        super(PopulationRegion.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(p.idpopulationRegion) FROM PopulationRegion p");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<PopulationRegion> find(Region region, Rubriquepopulation rubriquepopulation, Annee annee) throws Exception {
        List<PopulationRegion> populationRegions = new ArrayList<>();
        Query query = em.createQuery("SELECT p FROM PopulationRegion p WHERE p.idregion.idregion=:region AND p.idrubriquepopulation.idrubriquepopulation=:rubrique AND p.idannee.idannee=:annee");
        query.setParameter("region", region.getIdregion()).setParameter("rubrique", rubriquepopulation.getIdrubriquepopulation()).setParameter("annee", annee.getIdannee());
        if (!query.getResultList().isEmpty()) {
            populationRegions = query.getResultList();
        }
        return populationRegions;
    }

    @Override
    public List<PopulationRegion> find(Region region, Annee annee) throws Exception {
        List<PopulationRegion> populationRegions = new ArrayList<>();
        Query query = em.createQuery("SELECT p FROM PopulationRegion p WHERE p.idregion.idregion=:region AND p.idannee.idannee=:annee");
        query.setParameter("region", region.getIdregion()).setParameter("annee", annee.getIdannee());
        if (!query.getResultList().isEmpty()) {
            populationRegions = query.getResultList();
        }
        return populationRegions;
    }

}
