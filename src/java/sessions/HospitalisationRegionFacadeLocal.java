/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Region;
import entities.Hospitalisation;
import entities.HospitalisationRegion;
import entities.Rubriquehospitalisation;
import entities.Structure;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdel BEIN-INFO
 */
@Local
public interface HospitalisationRegionFacadeLocal {

    void create(HospitalisationRegion hospitalisationRegion);

    void edit(HospitalisationRegion hospitalisationRegion);

    void remove(HospitalisationRegion hospitalisationRegion);

    HospitalisationRegion find(Object id);

    List<HospitalisationRegion> findAll();

    List<HospitalisationRegion> findRange(int[] range);

    int count();

    Long nextId() throws Exception;

    List<HospitalisationRegion> findByRegion(int region);

    List<HospitalisationRegion> find(Hospitalisation hospitalisation, Rubriquehospitalisation rubriquehospitalisation) throws Exception;

    List<HospitalisationRegion> find(Hospitalisation hospitalisation) throws Exception;

    List<HospitalisationRegion> find(Hospitalisation hospitalisation, Rubriquehospitalisation rubriquehospitalisation, Region region) throws Exception;

    List<HospitalisationRegion> find(Hospitalisation hospitalisation, Rubriquehospitalisation rubriquehospitalisation, Structure s) throws Exception;

}
