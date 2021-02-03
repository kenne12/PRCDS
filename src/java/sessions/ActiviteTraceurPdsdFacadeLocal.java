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
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface ActiviteTraceurPdsdFacadeLocal {

    void create(ActiviteTraceurPdsd activiteTraceurPdsd);

    void edit(ActiviteTraceurPdsd activiteTraceurPdsd);

    void remove(ActiviteTraceurPdsd activiteTraceurPdsd);

    ActiviteTraceurPdsd find(Object id);

    List<ActiviteTraceurPdsd> findAll();

    List<ActiviteTraceurPdsd> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<ActiviteTraceurPdsd> findByActivitetraceur(ActiviteTraceur activiteTraceur) throws Exception;

    ActiviteTraceurPdsd findByTraceurPdsd(ActiviteTraceur activiteTraceur, Activite activite) throws Exception;

    List<ActiviteTraceurPdsd> findByIntervention(Interventionpnds interventionpnds) throws Exception;

}
