/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Ffom;
import entities.Menace;
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
public class MenaceFacade extends AbstractFacade<Menace> implements MenaceFacadeLocal {

    @PersistenceContext(unitName = "PRCDSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MenaceFacade() {
        super(Menace.class);
    }

    @Override
    public Long nextId() throws Exception {
        Query query = em.createQuery("SELECT MAX(m.idmenace) FROM Menace m");
        Long resultat = (Long) query.getSingleResult();
        if (resultat == null) {
            return 1L;
        } else {
            return resultat + 1;
        }
    }

    @Override
    public List<Menace> findByFfom(Ffom ffom) throws Exception {
        List<Menace> menaces = null;
        Query query = em.createQuery("SELECT m FROM Menace m WHERE m.idffom.idffom=:ffom");
        query.setParameter("ffom", ffom.getIdffom());
        menaces = query.getResultList();
        return menaces;
    }

}
