/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Region;
import entities.GouvernanceRegion;
import entities.GouvernanceRegion;
import entities.Rubriquegouvernance;
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
public class GouvernanceRegionFacade extends AbstractFacade<GouvernanceRegion> implements GouvernanceRegionFacadeLocal {
    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GouvernanceRegionFacade() {
        super(GouvernanceRegion.class);
    }
    
      @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(m.idgouvernanceRegion) FROM GouvernanceRegion m");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<GouvernanceRegion> find(Structure structure, Rubriquegouvernance rubriquegouvernance,Region region) throws Exception {
        List<GouvernanceRegion> gouvernanceRegions = new ArrayList<>();
        Query query = em.createQuery("SELECT m FROM GouvernanceRegion m WHERE  m.idregion.idregion=:region AND m.idstructure.idstructure=:structure AND m.idrubriquegouvernance.idrubriquegouvernance=:rubriquegouvernance");
        query.setParameter("region",region.getIdregion()).setParameter("structure", structure.getIdstructure()).setParameter("rubriquegouvernance",rubriquegouvernance.getIdrubriquegouvernance());
        if (!query.getResultList().isEmpty()) {
            gouvernanceRegions = query.getResultList();
        }     
        return gouvernanceRegions;
    }



}
