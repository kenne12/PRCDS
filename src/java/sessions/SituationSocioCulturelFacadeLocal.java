/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.SituationSocioculturel;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdel BEIN-INFO
 */
@Local
public interface SituationSocioCulturelFacadeLocal {

    void create(SituationSocioculturel situationSocioCulturel);

    void edit(SituationSocioculturel situationSocioCulturel);

    void remove(SituationSocioculturel situationSocioCulturel);

    SituationSocioculturel find(Object id);

    List<SituationSocioculturel> findAll();

    List<SituationSocioculturel> findRange(int[] range);

    int count();
    public int nextId();

    public List<SituationSocioculturel> findByEducation(String introduction);

    public List<SituationSocioculturel> findByRegion(int region);

}
