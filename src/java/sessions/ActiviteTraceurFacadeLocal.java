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
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface ActiviteTraceurFacadeLocal {

    void create(ActiviteTraceur activiteTraceur);

    void edit(ActiviteTraceur activiteTraceur);

    void remove(ActiviteTraceur activiteTraceur);

    ActiviteTraceur find(Object id);

    List<ActiviteTraceur> findAll();

    List<ActiviteTraceur> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<ActiviteTraceur> findBySousaxe(Sousaxe sousaxe) throws Exception;

    List<ActiviteTraceur> findByIntervention(Interventionpnds interventionpnds) throws Exception;

}
