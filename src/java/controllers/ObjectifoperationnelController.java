/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.Interventionpnds;
import entities.ObjectifOppRegion;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessions.InterventionpndsFacadeLocal;
import sessions.ObjectifOppRegionFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@ViewScoped
public class ObjectifoperationnelController {

    @EJB
    private ObjectifOppRegionFacadeLocal objectifOppRegionFacadeLocal;
    private ObjectifOppRegion objectifOppRegion = new ObjectifOppRegion();
    private List<ObjectifOppRegion> objectifOppRegions = new ArrayList<>();

    @EJB
    private InterventionpndsFacadeLocal interventionpndsFacadeLocal;
    private Interventionpnds interventionpnds = new Interventionpnds();
    private List<Interventionpnds> interventionpndses = new ArrayList<>();

    private boolean detail = true;

    private boolean imported = true;

    private String mode = "";

    /**
     * Creates a new instance of AxeController
     */
    public ObjectifoperationnelController() {
    }

    @PostConstruct
    private void init() {

    }

    public void activeButton() {
        detail = false;
    }

    public void deactiveButton() {
        detail = true;
    }

    public void prepareCreate() {
        mode = "Create";
        try {
            interventionpnds = new Interventionpnds();
            objectifOppRegion = new ObjectifOppRegion();

            interventionpndses.clear();

            List<Interventionpnds> ints = interventionpndsFacadeLocal.findAllRangeCode();
            List<Interventionpnds> ints1 = new ArrayList<>();

            List<ObjectifOppRegion> objectifOppRegions = objectifOppRegionFacadeLocal.findByRegion(SessionMBean.getRegion());

            for (ObjectifOppRegion o : objectifOppRegions) {
                ints1.add(o.getIdintervention());
            }

            for (Interventionpnds i : ints) {
                if (!ints1.contains(i)) {
                    interventionpndses.add(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareEdit() {
        mode = "Edit";
    }

    public void create() {
        try {
            objectifOppRegion.setIdobjectifOppRegion(objectifOppRegionFacadeLocal.nextId());
            objectifOppRegion.setIdregion(SessionMBean.getRegion());
            objectifOppRegion.setIdintervention(interventionpnds);
            objectifOppRegionFacadeLocal.create(objectifOppRegion);
            JsfUtil.addSuccessMessage("Opération réussie");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            if (objectifOppRegion != null) {
                objectifOppRegionFacadeLocal.remove(objectifOppRegion);
                JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                JsfUtil.addErrorMessage("Aucune ligne selectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importer() {
        try {
            interventionpndses = interventionpndsFacadeLocal.findAllRange();
            for (Interventionpnds i : interventionpndses) {
                ObjectifOppRegion o1 = new ObjectifOppRegion();
                o1.setIdobjectifOppRegion(objectifOppRegionFacadeLocal.nextId());
                o1.setIdregion(SessionMBean.getRegion());
                o1.setIdintervention(i);

                if ("fr".equals(SessionMBean.getLangue())) {
                    o1.setObjectif(i.getObjectifOpFr());
                } else {
                    o1.setObjectif(i.getObjectifOpEn());
                }
                objectifOppRegionFacadeLocal.create(o1);
            }
            JsfUtil.addSuccessMessage("Données importées avec succès");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateObjectif() {
        try {
            if (interventionpnds.getIdinterventionpnds() != null) {
                interventionpnds = interventionpndsFacadeLocal.find(interventionpnds.getIdinterventionpnds());
                if ("en".equals(SessionMBean.getLangue())) {
                    objectifOppRegion.setObjectif(interventionpnds.getObjectifOpEn());
                } else {
                    objectifOppRegion.setObjectif(interventionpnds.getObjectifOpFr());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit() {
        try {
            if (objectifOppRegion != null) {
                objectifOppRegionFacadeLocal.edit(objectifOppRegion);
                JsfUtil.addSuccessMessage("Opération réussie");
            } else {
                JsfUtil.addErrorMessage("Aucune ligne selectionnnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isDetail() {
        return detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    public boolean isImported() {
        return imported;
    }

    public void setImported(boolean imported) {
        this.imported = imported;
    }

    public Interventionpnds getInterventionpnds() {
        return interventionpnds;
    }

    public void setInterventionpnds(Interventionpnds interventionpnds) {
        this.interventionpnds = interventionpnds;
    }

    public List<Interventionpnds> getInterventionpndses() {
        return interventionpndses;
    }

    public void setInterventionpndses(List<Interventionpnds> interventionpndses) {
        this.interventionpndses = interventionpndses;
    }

    public ObjectifOppRegion getObjectifOppRegion() {
        return objectifOppRegion;
    }

    public void setObjectifOppRegion(ObjectifOppRegion objectifOppRegion) {
        this.objectifOppRegion = objectifOppRegion;
    }

    public List<ObjectifOppRegion> getObjectifOppRegions() {
        try {
            objectifOppRegions = objectifOppRegionFacadeLocal.findByRegion(SessionMBean.getRegion());
            if (objectifOppRegions.isEmpty()) {
                imported = false;
            } else {
                imported = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectifOppRegions;
    }

    public void setObjectifOppRegions(List<ObjectifOppRegion> objectifOppRegions) {
        this.objectifOppRegions = objectifOppRegions;
    }

}
