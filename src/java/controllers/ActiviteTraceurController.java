/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.Activite;
import entities.ActiviteTraceur;
import entities.ActiviteTraceurPdsd;
import entities.Axe;
import entities.Interventionpnds;
import entities.Sousaxe;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DualListModel;
import sessions.ActiviteFacadeLocal;
import sessions.ActiviteTraceurFacadeLocal;
import sessions.ActiviteTraceurPdsdFacadeLocal;
import sessions.AxeFacadeLocal;
import sessions.InterventionpndsFacadeLocal;
import sessions.SousaxeFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@SessionScoped
public class ActiviteTraceurController {

    @EJB
    private ActiviteTraceurFacadeLocal activiteTraceurFacadeLocal;
    private ActiviteTraceur activiteTraceur = new ActiviteTraceur();
    private ActiviteTraceur activiteTraceur1 = new ActiviteTraceur();
    private List<ActiviteTraceur> activiteTraceurs = new ArrayList<>();

    @EJB
    private ActiviteTraceurPdsdFacadeLocal activiteTraceurPdsdFacadeLocal;
    private ActiviteTraceurPdsd activiteTraceurPdsd = new ActiviteTraceurPdsd();
    private List<ActiviteTraceurPdsd> activiteTraceurPdsds = new ArrayList<>();

    @EJB
    private ActiviteFacadeLocal activiteFacadeLocal;
    private DualListModel<Activite> dualList = new DualListModel<>();
    private List<Activite> activites = new ArrayList<>();

    @EJB
    private AxeFacadeLocal axeFacadeLocal;
    private Axe axe = new Axe();
    private List<Axe> axes = new ArrayList<>();

    @EJB
    private SousaxeFacadeLocal sousaxeFacadeLocal;
    private Sousaxe sousaxe = new Sousaxe();
    private List<Sousaxe> sousaxes = new ArrayList<>();

    @EJB
    private InterventionpndsFacadeLocal interventionpndsFacadeLocal;
    private Interventionpnds interventionpnds = new Interventionpnds();
    private List<Interventionpnds> interventionpndses = new ArrayList<>();

    private boolean detail = true;

    private boolean showSelectActivite = true;
    private boolean showSaisiActivite = true;
    private boolean showSelector = true;
    private boolean showSelectIntervention = true;

    private String mode = "";

    private Double pourcentageAxe = 0d;
    private Double pourcentageSousAxe = 0d;

    private Double total = 0d;

    public ActiviteTraceurController() {
    }

    @PostConstruct
    private void init() {

        axes = axeFacadeLocal.findAllRangeByCode();

        try {
            if (!axes.isEmpty()) {

                axe = axes.get(0);
                
                sousaxes = sousaxeFacadeLocal.findByAxe(axe);

                if (!sousaxes.isEmpty()) {
                    sousaxe = sousaxes.get(0);
                    activiteTraceurs = activiteTraceurFacadeLocal.findBySousaxe(sousaxe);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDetail(ActiviteTraceur activiteTraceur) {
        try {
            List<ActiviteTraceurPdsd> activiteTraceurPdsds = activiteTraceurPdsdFacadeLocal.findByActivitetraceur(activiteTraceur);
            activites.clear();
            for (ActiviteTraceurPdsd a : activiteTraceurPdsds) {
                activites.add(a.getIdactivitePdsd());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProbleme() {

    }

    public void update() {

    }

    public void updateSelect() {
        try {
            if (showSelectActivite) {
                showSaisiActivite = false;
                activiteTraceur = new ActiviteTraceur();
                if (interventionpnds.getIdinterventionpnds() != null) {
                    activiteTraceurs = activiteTraceurFacadeLocal.findByIntervention(interventionpnds);
                }
                updateOther1();
            } else {
                showSaisiActivite = true;
                activiteTraceur = new ActiviteTraceur();
                dualList = new DualListModel<>();
                activiteTraceurs.clear();
                updateOther1();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateOther1() {
        try {

            dualList = new DualListModel<>();

            if (interventionpnds.getIdinterventionpnds() != null) {
                activiteTraceurs = activiteTraceurFacadeLocal.findByIntervention(interventionpnds);
            }

            if ("Create".equals(mode)) {

                if (activiteTraceur.getIdactiviteTraceur() == null) {

                    dualList = new DualListModel<>();
                    if (interventionpnds.getIdinterventionpnds() != null) {

                        List<ActiviteTraceurPdsd> activiteTraceurPdsds = activiteTraceurPdsdFacadeLocal.findByIntervention(interventionpnds);

                        List<Activite> activites = new ArrayList<>();

                        if (!activiteTraceurPdsds.isEmpty()) {
                            for (ActiviteTraceurPdsd a : activiteTraceurPdsds) {
                                activites.add(a.getIdactivitePdsd());
                            }
                        }

                        List<Activite> activites1 = activiteFacadeLocal.findByRegion(SessionMBean.getRegion(), interventionpnds);

                        if (activites1 != null) {
                            dualList.getSource().addAll(activites1);
                            if (!activites.isEmpty()) {
                                dualList.getSource().removeAll(activites);
                            }
                        }
                    }
                } else {

                    if (activiteTraceur.getIdactiviteTraceur() != null) {

                        activiteTraceur = activiteTraceurFacadeLocal.find(activiteTraceur.getIdactiviteTraceur());

                        List<ActiviteTraceurPdsd> activiteTraceurPdsds = activiteTraceurPdsds = activiteTraceurPdsdFacadeLocal.findByIntervention(activiteTraceur.getIdinterventionpnds());

                        List<Activite> activites = new ArrayList<>();

                        if (!activiteTraceurPdsds.isEmpty()) {
                            for (ActiviteTraceurPdsd a : activiteTraceurPdsds) {
                                if (a.getIdactiviteTraceur().getIdactiviteTraceur().equals(activiteTraceur.getIdactiviteTraceur())) {
                                    activites.add(a.getIdactivitePdsd());
                                }
                            }
                        }

                        List<Activite> activites2 = new ArrayList<>();

                        if (!activiteTraceurPdsds.isEmpty()) {
                            for (ActiviteTraceurPdsd a : activiteTraceurPdsds) {
                                if (!a.getIdactiviteTraceur().getIdactiviteTraceur().equals(activiteTraceur.getIdactiviteTraceur()));
                                activites2.add(a.getIdactivitePdsd());
                            }
                        }

                        List<Activite> activites1 = activiteFacadeLocal.findByRegion(SessionMBean.getRegion(), interventionpnds);

                        if (activites1 != null) {
                            dualList.getSource().addAll(activites1);
                            if (!activites2.isEmpty()) {
                                dualList.getSource().removeAll(activites2);
                            }
                        }

                        if (!activites.isEmpty()) {
                            dualList.getTarget().addAll(activites);
                        }

                    }
                }
            } else {

                if (activiteTraceur1.getIdactiviteTraceur() != null) {

                    List<ActiviteTraceurPdsd> activiteTraceurPdsds = activiteTraceurPdsds = activiteTraceurPdsdFacadeLocal.findByIntervention(activiteTraceur1.getIdinterventionpnds());

                    List<Activite> activites = new ArrayList<>();

                    if (!activiteTraceurPdsds.isEmpty()) {
                        for (ActiviteTraceurPdsd a : activiteTraceurPdsds) {
                            if (a.getIdactiviteTraceur().getIdactiviteTraceur().equals(activiteTraceur1.getIdactiviteTraceur())) {
                                activites.add(a.getIdactivitePdsd());
                            }
                        }
                    }

                    List<Activite> activites2 = new ArrayList<>();

                    if (!activiteTraceurPdsds.isEmpty()) {
                        for (ActiviteTraceurPdsd a : activiteTraceurPdsds) {
                            if (!a.getIdactiviteTraceur().getIdactiviteTraceur().equals(activiteTraceur1.getIdactiviteTraceur()));
                            activites2.add(a.getIdactivitePdsd());
                        }
                    }

                    List<Activite> activites1 = activiteFacadeLocal.findByRegion(SessionMBean.getRegion(), interventionpnds);

                    if (activites1 != null) {
                        dualList.getSource().addAll(activites1);
                        if (!activites2.isEmpty()) {
                            dualList.getSource().removeAll(activites2);
                        }
                    }

                    if (!activites.isEmpty()) {
                        dualList.getTarget().addAll(activites);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void activeButton() {
        detail = false;
    }

    public void deactiveButton() {
        detail = true;
    }

    public void prepareCreate() {
        mode = "Create";
        showSelectActivite = false;
        showSaisiActivite = true;
        showSelector = true;
        showSelectIntervention = false;

        activiteTraceur = new ActiviteTraceur();
        activiteTraceur1 = new ActiviteTraceur();
        dualList = new DualListModel<>();
        interventionpnds = new Interventionpnds();

        try {
            interventionpndses = interventionpndsFacadeLocal.findBySousaxeDistrict(sousaxe);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareEdit() {
        mode = "Edit";
        showSelectActivite = false;
        showSelector = false;

        showSaisiActivite = true;

        activiteTraceur = activiteTraceur1;
        showSelectIntervention = true;

        try {

            interventionpndses = interventionpndsFacadeLocal.findBySousaxe(sousaxe);

            if (activiteTraceur1 != null) {

                interventionpnds = activiteTraceur1.getIdinterventionpnds();

                updateOther1();

            } else {
                JsfUtil.addErrorMessage("Veuillez selectionner une ligne");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAll() {
        try {
            if (axe != null) {
                sousaxes = sousaxeFacadeLocal.findByAxe(axe);

                if (!sousaxes.isEmpty()) {
                    sousaxe = sousaxes.get(0);
                    activiteTraceurs = activiteTraceurFacadeLocal.findBySousaxe(sousaxe);
                } else {
                    sousaxe = new Sousaxe();
                    activiteTraceurs.clear();
                }
            } else {
                sousaxe = new Sousaxe();
                activiteTraceurs.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSousaxe() {
        try {
            if (!sousaxes.isEmpty()) {
                if (sousaxe != null) {
                    activiteTraceurs = activiteTraceurFacadeLocal.findBySousaxe(sousaxe);
                }
            } else {
                sousaxe = new Sousaxe();
                activiteTraceurs.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            if ("Edit".equals(mode)) {

                // on est en mode edit
                activiteTraceurFacadeLocal.edit(activiteTraceur);

                if (activiteTraceur1 != null) {

                    if (!dualList.getTarget().isEmpty()) {
                        for (Activite a : dualList.getTarget()) {

                            ActiviteTraceurPdsd atp = activiteTraceurPdsdFacadeLocal.findByTraceurPdsd(activiteTraceur, a);

                            if (atp == null) {
                                ActiviteTraceurPdsd act = new ActiviteTraceurPdsd();

                                act.setIdactiviteTraceurPdsd(activiteTraceurPdsdFacadeLocal.nextId());
                                act.setIdactivitePdsd(a);
                                act.setIdactiviteTraceur(activiteTraceur);

                                activiteTraceurPdsdFacadeLocal.create(act);
                            }
                        }
                    }

                    if (!dualList.getSource().isEmpty()) {
                        for (Activite a : dualList.getSource()) {
                            ActiviteTraceurPdsd atp = activiteTraceurPdsdFacadeLocal.findByTraceurPdsd(activiteTraceur, a);

                            if (atp != null) {
                                activiteTraceurPdsdFacadeLocal.remove(atp);
                            }
                        }
                    }

                    JsfUtil.addSuccessMessage("Operation réussie");

                } else {
                    JsfUtil.addErrorMessage("Aucune activité traceur selectionnée");
                }
            } else {

                //on est en mode ajout
                if (activiteTraceur.getIdactiviteTraceur() == null) {

                    activiteTraceur.setIdactiviteTraceur(activiteTraceurFacadeLocal.nextId());
                    activiteTraceur.setIdinterventionpnds(interventionpnds);
                    activiteTraceurFacadeLocal.create(activiteTraceur);

                    for (Activite a : dualList.getTarget()) {
                        ActiviteTraceurPdsd act = new ActiviteTraceurPdsd();

                        act.setIdactiviteTraceurPdsd(activiteTraceurPdsdFacadeLocal.nextId());
                        act.setIdactivitePdsd(a);
                        act.setIdactiviteTraceur(activiteTraceur);

                        activiteTraceurPdsdFacadeLocal.create(act);
                    }

                } else {

                    if (!dualList.getTarget().isEmpty()) {
                        for (Activite a : dualList.getTarget()) {

                            ActiviteTraceurPdsd atp = activiteTraceurPdsdFacadeLocal.findByTraceurPdsd(activiteTraceur, a);

                            if (atp == null) {
                                ActiviteTraceurPdsd act = new ActiviteTraceurPdsd();

                                act.setIdactiviteTraceurPdsd(activiteTraceurPdsdFacadeLocal.nextId());
                                act.setIdactivitePdsd(a);
                                act.setIdactiviteTraceur(activiteTraceur);
                                activiteTraceurPdsdFacadeLocal.create(act);
                            }
                        }
                    }

                    if (!dualList.getSource().isEmpty()) {
                        for (Activite a : dualList.getSource()) {
                            ActiviteTraceurPdsd atp = activiteTraceurPdsdFacadeLocal.findByTraceurPdsd(activiteTraceur, a);

                            if (atp != null) {
                                activiteTraceurPdsdFacadeLocal.remove(atp);
                            }
                        }
                    }
                }

                JsfUtil.addSuccessMessage("Operation réussie");

            }
            activiteTraceurs = activiteTraceurFacadeLocal.findBySousaxe(sousaxe);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            if (activiteTraceur1 != null) {

                List<ActiviteTraceurPdsd> activiteTraceurPdsds = activiteTraceurPdsdFacadeLocal.findByActivitetraceur(activiteTraceur1);

                if (!activiteTraceurPdsds.isEmpty()) {

                    for (ActiviteTraceurPdsd a : activiteTraceurPdsds) {
                        activiteTraceurPdsdFacadeLocal.remove(a);
                    }
                }

                activiteTraceurFacadeLocal.remove(activiteTraceur1);
                activiteTraceurs = activiteTraceurFacadeLocal.findBySousaxe(sousaxe);
                JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                JsfUtil.addErrorMessage("Aucune activite selectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nextAxe() {
        try {
            if (!axes.isEmpty()) {
                if (axes.size() > 1) {
                    int i = 0;
                    for (Axe a : axes) {
                        if (a.equals(axe)) {
                            if (i <= axes.size()) {

                                if (i + 1 == axes.size()) {
                                    break;
                                }

                                axe = axes.get(i + 1);

                                sousaxes = sousaxeFacadeLocal.findByAxe(axe);

                                if (!sousaxes.isEmpty()) {
                                    sousaxe = sousaxes.get(0);

                                    activiteTraceurs = activiteTraceurFacadeLocal.findBySousaxe(sousaxe);

                                    break;
                                } else {
                                    sousaxe = new Sousaxe();
                                    sousaxes.clear();
                                    activiteTraceurs.clear();
                                    break;
                                }
                            }
                        }
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void precAxe() {
        try {
            if (!axes.isEmpty()) {
                if (axes.size() > 1) {
                    int i = 0;
                    for (Axe a : axes) {
                        if (a.equals(axe)) {
                            if (i == 0) {
                                break;
                            } else {
                                axe = axes.get(i - 1);

                                sousaxes = sousaxeFacadeLocal.findByAxe(axe);
                                if (!sousaxes.isEmpty()) {
                                    sousaxe = sousaxes.get(0);

                                    activiteTraceurs = activiteTraceurFacadeLocal.findBySousaxe(sousaxe);

                                    break;
                                } else {
                                    activiteTraceurs.clear();
                                    break;
                                }
                            }
                        }
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nextSousaxe() {
        try {
            if (!sousaxes.isEmpty()) {
                if (sousaxes.size() > 1) {
                    int i = 0;
                    for (Sousaxe s : sousaxes) {
                        if (s.equals(sousaxe)) {
                            if (i <= axes.size()) {

                                if (i + 1 == sousaxes.size()) {
                                    return;
                                }
                                sousaxe = sousaxes.get(i + 1);
                                this.updateSousaxe();
                                break;
                            }
                        }
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void precSousAxe() {

        try {
            if (!sousaxes.isEmpty()) {
                if (sousaxes.size() > 1) {
                    int i = 0;
                    for (Sousaxe s : sousaxes) {
                        if (s.equals(sousaxe)) {
                            if (i == 0) {
                                break;
                            } else {
                                sousaxe = sousaxes.get(i - 1);
                                this.updateSousaxe();
                                break;
                            }
                        }
                        i++;
                    }
                }
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

    public Axe getAxe() {
        return axe;
    }

    public void setAxe(Axe axe) {
        this.axe = axe;
    }

    public List<Axe> getAxes() {
        return axes;
    }

    public void setAxes(List<Axe> axes) {
        this.axes = axes;
    }

    public Sousaxe getSousaxe() {
        return sousaxe;
    }

    public void setSousaxe(Sousaxe sousaxe) {
        this.sousaxe = sousaxe;
    }

    public List<Sousaxe> getSousaxes() {
        return sousaxes;
    }

    public void setSousaxes(List<Sousaxe> sousaxes) {
        this.sousaxes = sousaxes;
    }

    public Double getPourcentageAxe() {
        return pourcentageAxe;
    }

    public void setPourcentageAxe(Double pourcentageAxe) {
        this.pourcentageAxe = pourcentageAxe;
    }

    public Double getPourcentageSousAxe() {
        return pourcentageSousAxe;
    }

    public void setPourcentageSousAxe(Double pourcentageSousAxe) {
        this.pourcentageSousAxe = pourcentageSousAxe;
    }

    public boolean isShowSelectActivite() {
        return showSelectActivite;
    }

    public void setShowSelectActivite(boolean showSelectActivite) {
        this.showSelectActivite = showSelectActivite;
    }

    public boolean isShowSelector() {
        return showSelector;
    }

    public void setShowSelector(boolean showSelector) {
        this.showSelector = showSelector;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public ActiviteTraceur getActiviteTraceur() {
        return activiteTraceur;
    }

    public void setActiviteTraceur(ActiviteTraceur activiteTraceur) {
        this.activiteTraceur = activiteTraceur;
    }

    public List<ActiviteTraceur> getActiviteTraceurs() {
        return activiteTraceurs;
    }

    public void setActiviteTraceurs(List<ActiviteTraceur> activiteTraceurs) {
        this.activiteTraceurs = activiteTraceurs;
    }

    public ActiviteTraceurPdsd getActiviteTraceurPdsd() {
        return activiteTraceurPdsd;
    }

    public void setActiviteTraceurPdsd(ActiviteTraceurPdsd activiteTraceurPdsd) {
        this.activiteTraceurPdsd = activiteTraceurPdsd;
    }

    public List<ActiviteTraceurPdsd> getActiviteTraceurPdsds() {
        return activiteTraceurPdsds;
    }

    public void setActiviteTraceurPdsds(List<ActiviteTraceurPdsd> activiteTraceurPdsds) {
        this.activiteTraceurPdsds = activiteTraceurPdsds;
    }

    public DualListModel<Activite> getDualList() {
        return dualList;
    }

    public void setDualList(DualListModel<Activite> dualList) {
        this.dualList = dualList;
    }

    public boolean isShowSaisiActivite() {
        return showSaisiActivite;
    }

    public void setShowSaisiActivite(boolean showSaisiActivite) {
        this.showSaisiActivite = showSaisiActivite;
    }

    public ActiviteTraceur getActiviteTraceur1() {
        return activiteTraceur1;
    }

    public List<Activite> getActivites() {
        return activites;
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

    public void setActivites(List<Activite> activites) {
        this.activites = activites;
    }

    public void setActiviteTraceur1(ActiviteTraceur activiteTraceur1) {
        this.activiteTraceur1 = activiteTraceur1;
    }

    public boolean isShowSelectIntervention() {
        return showSelectIntervention;
    }

    public void setShowSelectIntervention(boolean showSelectIntervention) {
        this.showSelectIntervention = showSelectIntervention;
    }

}
