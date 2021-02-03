/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.Indicateur;
import entities.Interventionpnds;
import entities.ObjectifOppRegion;
import entities.ResultatAttenduRegion;
import entities.Resultatattendu;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessions.IndicateurFacadeLocal;
import sessions.InterventionpndsFacadeLocal;
import sessions.ObjectifOppRegionFacadeLocal;
import sessions.ResultatAttenduRegionFacadeLocal;
import sessions.ResultatattenduFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@ViewScoped
public class ResultatAttenduRegionController implements Serializable {

    @EJB
    private ResultatAttenduRegionFacadeLocal resultatAttenduRegionFacadeLocal;
    private ResultatAttenduRegion resultatAttenduRegion = new ResultatAttenduRegion();
    private List<ResultatAttenduRegion> resultatAttenduRegions = new ArrayList<>();

    @EJB
    private InterventionpndsFacadeLocal interventionpndsFacadeLocal;
    private Interventionpnds interventionpnds = new Interventionpnds();
    private List<Interventionpnds> interventionpndses = new ArrayList<>();

    @EJB
    private IndicateurFacadeLocal indicateurFacadeLocal;
    private Indicateur indicateur = new Indicateur();
    private List<Indicateur> indicateurs = new ArrayList<>();

    @EJB
    private ResultatattenduFacadeLocal resultatattenduFacadeLocal;
    private Resultatattendu resultatattendu = new Resultatattendu();
    private List<Resultatattendu> resultatattendus = new ArrayList<>();

    @EJB
    private ObjectifOppRegionFacadeLocal objectifOppRegionFacadeLocal;
    private ObjectifOppRegion objectifOppRegion = new ObjectifOppRegion();

    private boolean detail = true;

    private boolean imported = true;

    private String mode = "";

    /**
     * Creates a new instance of AxeController
     */
    public ResultatAttenduRegionController() {
    }

    @PostConstruct
    private void init() {
        resultatAttenduRegion = new ResultatAttenduRegion();
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
            resultatAttenduRegion = new ResultatAttenduRegion();
            indicateur = new Indicateur();
            interventionpnds = new Interventionpnds();
            indicateurs.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareEdit() {
        mode = "Edit";
        try {
            objectifOppRegion = new ObjectifOppRegion();
            if (resultatAttenduRegion != null) {
                List<ObjectifOppRegion> objectifOppRegions = objectifOppRegionFacadeLocal.findByRegion(SessionMBean.getRegion(), resultatAttenduRegion.getIdindicateur().getIdinterventionpnds());
                if (!objectifOppRegions.isEmpty()) {
                    objectifOppRegion = objectifOppRegions.get(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {

            resultatAttenduRegion.setIdresultatAttenduRegion(resultatAttenduRegionFacadeLocal.nextId());
            resultatAttenduRegion.setIdindicateur(indicateur);
            resultatAttenduRegion.setIdregion(SessionMBean.getRegion());
            resultatAttenduRegionFacadeLocal.create(resultatAttenduRegion);
            resultatAttenduRegion = new ResultatAttenduRegion();

            JsfUtil.addSuccessMessage("Opération réussie");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            if (resultatAttenduRegion != null) {
                resultatAttenduRegionFacadeLocal.remove(resultatAttenduRegion);
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
            List<Resultatattendu> resultatattendus = resultatattenduFacadeLocal.findAllRange(1);
            resultatAttenduRegions.clear();
            for (Resultatattendu r : resultatattendus) {
                ResultatAttenduRegion r1 = new ResultatAttenduRegion();
                r1.setIdresultatAttenduRegion(resultatAttenduRegionFacadeLocal.nextId());
                r1.setIdindicateur(r.getIdindicateur());
                r1.setIdregion(SessionMBean.getRegion());
                if ("fr".equals(SessionMBean.getLangue())) {
                    r1.setResultat(r.getResultatFr());
                } else {
                    r1.setResultat(r.getResultatEn());
                }
                resultatAttenduRegionFacadeLocal.create(r1);
            }
            JsfUtil.addSuccessMessage("Données importées avec succès");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObjectifOppRegion findObjectif(ResultatAttenduRegion rad) {
        ObjectifOppRegion objectifOppRegion = new ObjectifOppRegion();
        try {
            List<ObjectifOppRegion> objectifOppRegions = objectifOppRegionFacadeLocal.findByRegion(SessionMBean.getRegion(), rad.getIdindicateur().getIdinterventionpnds());
            if (!objectifOppRegions.isEmpty()) {
                objectifOppRegion = objectifOppRegions.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectifOppRegion;
    }

    public void updateIndicateur() {
        try {
            indicateurs.clear();
            if (interventionpnds.getIdinterventionpnds() != null) {

                objectifOppRegion = new ObjectifOppRegion();
                interventionpnds = interventionpndsFacadeLocal.find(interventionpnds.getIdinterventionpnds());

                List<Indicateur> indi = indicateurFacadeLocal.findByIntervention(interventionpnds, 1);
                List<Indicateur> indi1 = new ArrayList<>();
                List<Indicateur> indi2 = new ArrayList<>();

                if (!indi.isEmpty()) {

                    List<Resultatattendu> resultats = resultatattenduFacadeLocal.findAllRange(1);
                    if (!resultats.isEmpty()) {
                        for (Resultatattendu r : resultats) {
                            if (r.getIdindicateur().getIdinterventionpnds().equals(interventionpnds) && r.getIdindicateur().getIdniveaucollecte().getIdniveaucollecte().equals(1) ) {
                                indi1.add(r.getIdindicateur());
                            }
                        }
                    }

                    List<ResultatAttenduRegion> rats = resultatAttenduRegionFacadeLocal.findByRegion(SessionMBean.getRegion());
                    if (!rats.isEmpty()) {
                        for (ResultatAttenduRegion r1 : rats) {
                            indi2.add(r1.getIdindicateur());
                        }
                    }

                    for (Indicateur i : indi1) {
                        if (!indi2.contains(i)) {
                            if (!indicateurs.contains(i)) {
                                indicateurs.add(i);
                            }
                        }
                    }

                    List<ObjectifOppRegion> objectifOppRegions = objectifOppRegionFacadeLocal.findByRegion(SessionMBean.getRegion(), interventionpnds);
                    if (!objectifOppRegions.isEmpty()) {
                        objectifOppRegion = objectifOppRegions.get(0);
                    }

                } else {
                    JsfUtil.addErrorMessage("Cette intervention n'a aucun indicateur");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateResultat() {
        try {
            if (indicateur.getIdindicateur() != null) {
                indicateur = indicateurFacadeLocal.find(indicateur.getIdindicateur());
                List<Resultatattendu> rs = resultatattenduFacadeLocal.findByIndicateur(indicateur);
                if (!rs.isEmpty()) {
                    if ("en".equals(SessionMBean.getLangue())) {
                        resultatAttenduRegion.setResultat(rs.get(0).getResultatEn());
                    } else {
                        resultatAttenduRegion.setResultat(rs.get(0).getResultatFr());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit() {
        try {
            if (resultatAttenduRegion != null) {
                resultatAttenduRegionFacadeLocal.edit(resultatAttenduRegion);
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

    public Resultatattendu getResultatattendu() {
        return resultatattendu;
    }

    public void setResultatattendu(Resultatattendu resultatattendu) {
        this.resultatattendu = resultatattendu;
    }

    public List<Resultatattendu> getResultatattendus() {
        return resultatattendus;
    }

    public void setResultatattendus(List<Resultatattendu> resultatattendus) {
        this.resultatattendus = resultatattendus;
    }

    public ResultatAttenduRegion getResultatAttenduRegion() {
        return resultatAttenduRegion;
    }

    public void setResultatAttenduRegion(ResultatAttenduRegion resultatAttenduRegion) {
        this.resultatAttenduRegion = resultatAttenduRegion;
    }

    public List<ResultatAttenduRegion> getResultatAttenduRegions() {
        try {
            resultatAttenduRegions = resultatAttenduRegionFacadeLocal.findByRegion(SessionMBean.getRegion());
            if (resultatAttenduRegions.isEmpty()) {
                imported = false;
            } else {
                imported = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultatAttenduRegions;
    }

    public void setResultatAttenduRegions(List<ResultatAttenduRegion> resultatAttenduRegions) {
        this.resultatAttenduRegions = resultatAttenduRegions;
    }

    public boolean isImported() {
        return imported;
    }

    public void setImported(boolean imported) {
        this.imported = imported;
    }

    public Indicateur getIndicateur() {
        return indicateur;
    }

    public void setIndicateur(Indicateur indicateur) {
        this.indicateur = indicateur;
    }

    public List<Indicateur> getIndicateurs() {
        return indicateurs;
    }

    public void setIndicateurs(List<Indicateur> indicateurs) {
        this.indicateurs = indicateurs;
    }

    public Interventionpnds getInterventionpnds() {
        return interventionpnds;
    }

    public void setInterventionpnds(Interventionpnds interventionpnds) {
        this.interventionpnds = interventionpnds;
    }

    public List<Interventionpnds> getInterventionpndses() {
        try {
            interventionpndses = interventionpndsFacadeLocal.findAllRangeCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

}
