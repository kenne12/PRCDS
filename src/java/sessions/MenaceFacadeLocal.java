/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Ffom;
import entities.Menace;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface MenaceFacadeLocal {

    void create(Menace menace);

    void edit(Menace menace);

    void remove(Menace menace);

    Menace find(Object id);

    List<Menace> findAll();

    List<Menace> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<Menace> findByFfom(Ffom ffom) throws Exception;

}
