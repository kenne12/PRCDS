/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Annee;
import entities.CommentaireRegion;
import entities.Region;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdel BEIN-INFO
 */
@Local
public interface CommentaireRegionFacadeLocal {

    void create(CommentaireRegion commentaireRegion);

    void edit(CommentaireRegion commentaireRegion);

    void remove(CommentaireRegion commentaireRegion);

    CommentaireRegion find(Object id);

    List<CommentaireRegion> findAll();

    List<CommentaireRegion> findRange(int[] range);

    int count();
    
    Long nextId() throws Exception;

    List<CommentaireRegion> find(Region region, Integer numero) throws Exception;

    List<CommentaireRegion> find(Region region, Annee annee, Integer numero) throws Exception;

}
