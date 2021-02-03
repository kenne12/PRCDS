/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.Acteur;
import entities.ActeurRegion;
import entities.Acteursfacteurs;
import entities.Facteur;
import entities.FacteurRegion;
import entities.FaiblesseRegion;
import entities.FfomRegion;
import entities.ForceRegion;
import entities.MenaceRegion;
import entities.OpportuniteRegion;
import entities.Pilier;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessions.ActeurFacadeLocal;
import sessions.ActeurRegionFacadeLocal;
import sessions.ActeursfacteursFacadeLocal;
import sessions.FacteurFacadeLocal;
import sessions.FacteurRegionFacadeLocal;
import sessions.FaiblesseRegionFacadeLocal;
import sessions.FfomRegionFacadeLocal;
import sessions.ForceRegionFacadeLocal;
import sessions.MenaceRegionFacadeLocal;
import sessions.OpportuniteRegionFacadeLocal;
import sessions.PilierFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@SessionScoped
public class FfomEquipementController implements Serializable {

    @EJB
    private FfomRegionFacadeLocal ffomRegionFacadeLocal;
    private FfomRegion ffomRegion = new FfomRegion();

    @EJB
    PilierFacadeLocal pilierFacadeLocal;
    private Pilier pilier = new Pilier();
    private List<Pilier> piliers = new ArrayList<>();

    @EJB
    private ForceRegionFacadeLocal forceFacadeLocal;
    private ForceRegion force = new ForceRegion();
    private List<ForceRegion> forces = new ArrayList<>();

    @EJB
    private FaiblesseRegionFacadeLocal faiblesseFacadeLocal;
    private FaiblesseRegion faiblesse = new FaiblesseRegion();
    private List<FaiblesseRegion> faiblesses = new ArrayList<>();

    @EJB
    private OpportuniteRegionFacadeLocal opportuniteFacadeLocal;
    private OpportuniteRegion opportunite = new OpportuniteRegion();
    private List<OpportuniteRegion> opportunites = new ArrayList<>();

    @EJB
    private MenaceRegionFacadeLocal menaceFacadeLocal;
    private MenaceRegion menace = new MenaceRegion();
    private List<MenaceRegion> menaces = new ArrayList<>();

    @EJB
    private ActeursfacteursFacadeLocal acteursfacteursFacadeLocal;
    private Acteursfacteurs acteursfacteurs = new Acteursfacteurs();
    private List<Acteursfacteurs> acteursfacteurss = new ArrayList<>();

    @EJB
    private ActeurFacadeLocal acteurFacadeLocal;
    private Acteur acteur = new Acteur();
    private List<Acteur> acteurs = new ArrayList<>();

    @EJB
    private FacteurFacadeLocal facteurFacadeLocal;
    private Facteur facteur = new Facteur();
    private List<Facteur> facteurs = new ArrayList<>();

    @EJB
    private ActeurRegionFacadeLocal acteurRegionFacadeLocal;

    @EJB
    private FacteurRegionFacadeLocal facteurRegionFacadeLocal;

    private boolean detail = true;
    private boolean showLoadButton = true;

    private boolean showActeur = false;
    private boolean showFacteur = false;

    private boolean showForce = true;
    private boolean showFaiblesse = true;
    private boolean showOpportunite = true;
    private boolean showMenace = true;

    private String mode = "";

    /**
     * Creates a new instance of StructurecommunautaireController
     */
    public FfomEquipementController() {

    }

    @PostConstruct
    private void init() {
        try {
            pilier = pilierFacadeLocal.find(2);
            List<FfomRegion> ffoms = ffomRegionFacadeLocal.find(SessionMBean.getRegion(), pilier);
            if (!ffoms.isEmpty()) {
                ffomRegion = ffoms.get(0);
                showLoadButton = true;

                showForce = false;
                showFaiblesse = false;
                showOpportunite = false;
                showMenace = false;
            } else {
                showLoadButton = false;

                showForce = true;
                showFaiblesse = true;
                showOpportunite = true;
                showMenace = true;
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

    // partie faiblesse
    public void prepareCreateForce() {
        mode = "Create";
        force = new ForceRegion();
        menace = new MenaceRegion();
        opportunite = new OpportuniteRegion();
        faiblesse = new FaiblesseRegion();
        acteur = new Acteur();
        acteursfacteurs = new Acteursfacteurs();
        facteur = new Facteur();
        showFacteur = false;
        showActeur = false;
        detail = false;
    }

    public void update() {
        try {
            acteur = new Acteur();
            facteur = new Facteur();
            menace = new MenaceRegion();
            opportunite = new OpportuniteRegion();
            faiblesse = new FaiblesseRegion();
            force = new ForceRegion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareEditForce() {
        mode = "Edit";
        try {
            if (force.getIdfacteur() != null) {
                facteur = force.getIdfacteur();
                acteursfacteurs = acteursfacteursFacadeLocal.find(2);
                showFacteur = true;
                showActeur = false;
            } else if (force.getIdacteur() != null) {
                acteur = force.getIdacteur();
                acteursfacteurs = acteursfacteursFacadeLocal.find(1);
                showActeur = true;
                showFacteur = false;
            } else {
                acteursfacteurs = acteursfacteursFacadeLocal.find(3);
                showActeur = false;
                showFacteur = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateActeurFacteur() {
        try {

            if (acteursfacteurs.getIdacteursfacteurs() != null) {
                if (acteursfacteurs.getIdacteursfacteurs() == 1) {
                    showActeur = true;
                    showFacteur = false;
                    force.setFacteur(false);
                    faiblesse.setFacteur(false);
                    opportunite.setFacteur(false);
                    menace.setFacteur(false);
                } else if (acteursfacteurs.getIdacteursfacteurs() == 2) {
                    showActeur = false;
                    showFacteur = true;
                    force.setFacteur(true);
                    faiblesse.setFacteur(true);
                    opportunite.setFacteur(true);
                    menace.setFacteur(true);
                } else {
                    showActeur = false;
                    showFacteur = false;
                    force.setFacteur(false);
                    faiblesse.setFacteur(false);
                    opportunite.setFacteur(false);
                    menace.setFacteur(false);
                }
            } else {
                showActeur = false;
                showFacteur = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createForce() {
        try {
            if ("Create".equals(mode)) {

                if (ffomRegion != null) {

                    force.setIdforceRegion(forceFacadeLocal.nextId());
                    if (acteursfacteurs.getIdacteursfacteurs() == 1) {
                        force.setIdacteur(acteur);
                        force.setFacteur(false);
                    } else if (acteursfacteurs.getIdacteursfacteurs() == 2) {
                        force.setIdfacteur(facteur);
                        force.setFacteur(true);
                    }
                    force.setIdffomRegion(ffomRegion);
                    forceFacadeLocal.create(force);
                    this.updateForce();
                    this.prepareCreateForce();
                }
            } else {

                if (force != null) {
                    if (acteursfacteurs.getIdacteursfacteurs() == 1) {
                        force.setIdacteur(acteurFacadeLocal.find(acteur.getIdacteur()));
                        force.setIdfacteur(null);
                        force.setFacteur(false);
                    } else if (acteursfacteurs.getIdacteursfacteurs() == 2) {
                        force.setIdacteur(null);
                        force.setIdfacteur(facteurFacadeLocal.find(facteur.getIdfacteur()));
                        force.setFacteur(true);
                    } else {
                        force.setIdacteur(null);
                        force.setIdfacteur(null);
                        force.setFacteur(false);
                    }
                    forceFacadeLocal.edit(force);
                    this.updateForce();
                    JsfUtil.addSuccessMessage("Opération réussie");
                } else {
                    JsfUtil.addErrorMessage("Aucune ligne selectionnée");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateForce() throws Exception {

        List<ForceRegion> temp = forceFacadeLocal.findByFfom(ffomRegion);
        String chaine = "";
        if (!temp.isEmpty()) {

            int i = 0;
            for (ForceRegion f : temp) {

                if (i == 0) {

                    if (f.getIdfacteur() != null) {
                        if ("fr".equals(SessionMBean.getLangue())) {
                            chaine += "- " + f.getIdfacteur().getNomFr() + "";
                        } else {
                            chaine += "- " + f.getIdfacteur().getNomEn() + "";
                        }
                    } else if (f.getIdacteur() != null) {
                        if ("fr".equals(SessionMBean.getLangue())) {
                            chaine += "" + f.getIdacteur().getNomFr() + "";
                        } else {
                            chaine += "" + f.getIdacteur().getNomEn() + "";
                        }
                    } else {
                        chaine += "-";
                    }
                    chaine += "\n     - " + f.getNom();

                } else {

                    if (f.getIdfacteur() != null) {
                        if ("fr".equals(SessionMBean.getLangue())) {
                            chaine += "\n- " + f.getIdfacteur().getNomFr() + "";
                        } else {
                            chaine += "\n- " + f.getIdfacteur().getNomEn() + "";
                        }
                    } else if (f.getIdacteur() != null) {
                        if ("fr".equals(SessionMBean.getLangue())) {
                            chaine += "\n- " + f.getIdacteur().getNomFr() + "";
                        } else {
                            chaine += "\n- " + f.getIdacteur().getNomEn() + "";
                        }
                    } else {
                        chaine += "\n - ";
                    }
                    chaine += "\n     - " + f.getNom();
                }

                i++;
            }

        }
        ffomRegion.setForce(chaine);
        ffomRegionFacadeLocal.edit(ffomRegion);
    }

    public void updateFaiblesse() throws Exception {

        List<FaiblesseRegion> temp = faiblesseFacadeLocal.findByFfom(ffomRegion);
        String chaine = "";
        if (!temp.isEmpty()) {

            int i = 0;
            for (FaiblesseRegion f : temp) {

                if (i == 0) {

                    if (f.getIdfacteur() != null) {
                        if ("fr".equals(SessionMBean.getLangue())) {
                            chaine += " " + f.getIdfacteur().getNomFr() + "";
                        } else {
                            chaine += " " + f.getIdfacteur().getNomEn() + "";
                        }
                    } else if (f.getIdacteur() != null) {
                        if ("fr".equals(SessionMBean.getLangue())) {
                            chaine += " " + f.getIdacteur().getNomFr() + "";
                        } else {
                            chaine += " " + f.getIdacteur().getNomEn() + "";
                        }
                    } else {
                        chaine += "  ";
                    }
                    chaine += "\n      " + f.getNom();
                } else {

                    if (f.getIdfacteur() != null) {
                        if ("fr".equals(SessionMBean.getLangue())) {
                            chaine += "\n " + f.getIdfacteur().getNomFr() + "";
                        } else {
                            chaine += "\n " + f.getIdfacteur().getNomEn() + "";
                        }
                    } else if (f.getIdacteur() != null) {
                        if ("fr".equals(SessionMBean.getLangue())) {
                            chaine += "\n " + f.getIdacteur().getNomFr() + "";
                        } else {
                            chaine += "\n " + f.getIdacteur().getNomEn() + "";
                        }
                    } else {
                        chaine += "\n - ";
                    }
                    chaine += "\n      " + f.getNom();
                }

                i++;
            }

        }
        ffomRegion.setFaiblesse(chaine);
        ffomRegionFacadeLocal.edit(ffomRegion);
    }

    public void updateOpportunite() throws Exception {
        List<OpportuniteRegion> temp = opportuniteFacadeLocal.findByFfom(ffomRegion);

        if (!temp.isEmpty()) {
            String chaine = "";
            int i = 0;
            for (OpportuniteRegion o : temp) {
                if (i == 0) {

                    if (o.getIdfacteur() != null) {
                        if ("fr".equals(SessionMBean.getLangue())) {
                            chaine += " " + o.getIdfacteur().getNomFr() + " ";
                        } else {
                            chaine += " " + o.getIdfacteur().getNomEn() + " ";
                        }
                    } else if (o.getIdacteur() != null) {
                        if ("fr".equals(SessionMBean.getLangue())) {
                            chaine += " " + o.getIdacteur().getNomFr() + " ";
                        } else {
                            chaine += " " + o.getIdacteur().getNomEn() + " ";
                        }
                    } else {
                        chaine += " - ";
                    }
                    chaine += "\n     - " + o.getNom();
                } else {

                    if (o.getIdfacteur() != null) {
                        if ("fr".equals(SessionMBean.getLangue())) {
                            chaine += "\n " + o.getIdfacteur().getNomFr() + " ";
                        } else {
                            chaine += "\n " + o.getIdfacteur().getNomEn() + " ";
                        }
                    } else if (o.getIdacteur() != null) {
                        if ("fr".equals(SessionMBean.getLangue())) {
                            chaine += "\n " + o.getIdacteur().getNomFr() + " ";
                        } else {
                            chaine += "\n " + o.getIdacteur().getNomEn() + " ";
                        }
                    } else {
                        chaine += "\n - ";
                    }
                    chaine += "\n     - " + o.getNom();
                }
                i++;
            }
            ffomRegion.setOpportunite(chaine);
            ffomRegionFacadeLocal.edit(ffomRegion);
        }
    }

    public void updateMenace() throws Exception {

        List<MenaceRegion> temp = menaceFacadeLocal.findByFfom(ffomRegion);
        String chaine = "";
        if (!temp.isEmpty()) {
            int i = 0;
            for (MenaceRegion m : temp) {

                if (i == 0) {

                    if (m.getIdfacteur() != null) {
                        if ("fr".equals(SessionMBean.getLangue())) {
                            chaine += " " + m.getIdfacteur().getNomFr() + " ";
                        } else {
                            chaine += " " + m.getIdfacteur().getNomEn() + " ";
                        }
                    } else if (m.getIdacteur() != null) {
                        if ("fr".equals(SessionMBean.getLangue())) {
                            chaine += " " + m.getIdacteur().getNomFr() + " ";
                        } else {
                            chaine += " " + m.getIdacteur().getNomEn() + " ";
                        }
                    } else {
                        chaine += " - ";
                    }
                    chaine += "\n     - " + m.getNom();

                } else {

                    if (m.getIdfacteur() != null) {
                        if ("fr".equals(SessionMBean.getLangue())) {
                            chaine += "\n " + m.getIdfacteur().getNomFr() + " ";
                        } else {
                            chaine += "\n " + m.getIdfacteur().getNomEn() + " ";
                        }
                    } else if (m.getIdacteur() != null) {
                        if ("fr".equals(SessionMBean.getLangue())) {
                            chaine += "\n " + m.getIdacteur().getNomFr() + " ";
                        } else {
                            chaine += "\n " + m.getIdacteur().getNomEn() + " ";
                        }
                    } else {
                        chaine += "\n - ";
                    }
                    chaine += "\n     - " + m.getNom();
                }
                i++;
            }
        }
        ffomRegion.setMenace(chaine);
        ffomRegionFacadeLocal.edit(ffomRegion);
    }

    public void deleteForce() {
        try {
            if (force != null) {

                forceFacadeLocal.remove(force);
                if (ffomRegion != null) {
                    this.updateForce();
                }
                JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                JsfUtil.addErrorMessage("Aucune ligne selectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // patie des faiblesse
    public void prepareCreateFaiblesse() {
        mode = "Create";
        faiblesse = new FaiblesseRegion();
        showFacteur = false;
        showActeur = false;
        detail = false;
        acteur = new Acteur();
        facteur = new Facteur();
        acteursfacteurs = new Acteursfacteurs();
    }

    public void prepareEditFaiblesse() {
        mode = "Edit";
        try {
            if (faiblesse.getIdfacteur() != null) {
                facteur = faiblesse.getIdfacteur();
                acteursfacteurs = acteursfacteursFacadeLocal.find(2);
                showFacteur = true;
                showActeur = false;
            } else if (faiblesse.getIdacteur() != null) {
                acteur = faiblesse.getIdacteur();
                acteursfacteurs = acteursfacteursFacadeLocal.find(1);
                showFacteur = false;
                showActeur = true;
            } else {
                acteursfacteurs = acteursfacteursFacadeLocal.find(3);
                showFacteur = false;
                showActeur = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createFaiblesse() {
        try {
            if ("Create".equals(mode)) {
                if (ffomRegion != null) {

                    faiblesse.setIdfaiblesseRegion(faiblesseFacadeLocal.nextId());
                    if (acteursfacteurs.getIdacteursfacteurs() == 1) {
                        faiblesse.setIdacteur(acteur);
                        faiblesse.setIdfacteur(null);
                        faiblesse.setFacteur(false);
                    } else if (acteursfacteurs.getIdacteursfacteurs() == 2) {
                        faiblesse.setIdfacteur(facteur);
                        faiblesse.setFacteur(true);
                    } else {
                        faiblesse.setIdacteur(null);
                        faiblesse.setIdfacteur(null);
                    }

                    faiblesse.setIdffomRegion(ffomRegion);
                    faiblesseFacadeLocal.create(faiblesse);

                    JsfUtil.addSuccessMessage("Opération réussie");
                    this.updateFaiblesse();
                    this.prepareCreateForce();
                }
            } else {
                if (faiblesse != null) {
                    if (acteursfacteurs.getIdacteursfacteurs() == 1) {
                        faiblesse.setIdacteur(acteurFacadeLocal.find(acteur.getIdacteur()));
                        faiblesse.setIdfacteur(null);
                        faiblesse.setFacteur(false);
                    } else if (acteursfacteurs.getIdacteursfacteurs() == 2) {
                        faiblesse.setIdacteur(null);
                        faiblesse.setIdfacteur(facteurFacadeLocal.find(facteur.getIdfacteur()));
                        faiblesse.setFacteur(true);
                    } else {
                        faiblesse.setIdacteur(null);
                        faiblesse.setFacteur(false);
                        faiblesse.setIdfacteur(null);
                    }
                    faiblesseFacadeLocal.edit(faiblesse);
                    this.updateFaiblesse();
                    JsfUtil.addSuccessMessage("Opération réussie");
                } else {
                    JsfUtil.addErrorMessage("Aucune ligne selectionnée");
                }
            }
            acteur = new Acteur();
            facteur = new Facteur();
            acteursfacteurs = new Acteursfacteurs();
            showActeur = false;
            showFacteur = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFaiblesse() {
        try {
            if (faiblesse != null) {
                faiblesseFacadeLocal.remove(faiblesse);
                if (ffomRegion != null) {
                    this.updateFaiblesse();
                }
                JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                JsfUtil.addErrorMessage("Aucune ligne selectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // patie des faiblesse
    public void prepareCreateOpportunite() {
        mode = "Create";
        opportunite = new OpportuniteRegion();
        acteur = new Acteur();
        facteur = new Facteur();
        acteursfacteurs = new Acteursfacteurs();
        showFacteur = false;
        showActeur = false;
        detail = false;
    }

    public void prepareEditOpportunite() {
        mode = "Edit";
        try {
            if (opportunite.getIdfacteur() != null) {
                facteur = opportunite.getIdfacteur();
                acteursfacteurs = acteursfacteursFacadeLocal.find(2);

                showFacteur = true;
                showActeur = false;

            } else if (opportunite.getIdacteur() != null) {

                acteur = opportunite.getIdacteur();
                acteursfacteurs = acteursfacteursFacadeLocal.find(1);

                showFacteur = false;
                showActeur = true;

            } else {
                acteursfacteurs = acteursfacteursFacadeLocal.find(3);
                showFacteur = false;
                showActeur = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createOpportunite() {
        try {
            if ("Create".equals(mode)) {

                if (ffomRegion != null) {

                    opportunite.setIdopportuniteRegion(opportuniteFacadeLocal.nextId());
                    if (acteursfacteurs.getIdacteursfacteurs() == 1) {
                        opportunite.setIdacteur(acteur);
                    } else if (acteursfacteurs.getIdacteursfacteurs() == 2) {
                        opportunite.setIdfacteur(facteur);
                    }
                    opportunite.setIdffomRegion(ffomRegion);
                    opportuniteFacadeLocal.create(opportunite);

                    this.updateOpportunite();
                    JsfUtil.addSuccessMessage("Opération réussie");
                    this.prepareCreateForce();
                }
            } else {
                if (opportunite != null) {

                    if (acteursfacteurs.getIdacteursfacteurs() == 1) {
                        opportunite.setIdacteur(acteurFacadeLocal.find(acteur.getIdacteur()));
                        opportunite.setIdfacteur(null);
                        opportunite.setFacteur(false);
                    } else if (acteursfacteurs.getIdacteursfacteurs() == 2) {
                        opportunite.setIdacteur(null);
                        opportunite.setIdfacteur(facteurFacadeLocal.find(facteur.getIdfacteur()));
                        opportunite.setFacteur(true);
                    } else {
                        opportunite.setIdacteur(null);
                        opportunite.setIdfacteur(null);
                        opportunite.setFacteur(false);
                    }
                    opportuniteFacadeLocal.edit(opportunite);
                    this.updateOpportunite();
                    JsfUtil.addSuccessMessage("Opération réussie");
                } else {
                    JsfUtil.addErrorMessage("Aucune ligne selectionnée");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteOpportunite() {
        try {

            if (opportunite != null) {
                opportuniteFacadeLocal.remove(opportunite);
                if (ffomRegion != null) {
                    this.updateOpportunite();
                }
                JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                JsfUtil.addErrorMessage("Aucune ligne selectionnée");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //partie menace
    public void prepareCreateMenace() {
        mode = "Create";
        menace = new MenaceRegion();
        acteur = new Acteur();
        facteur = new Facteur();
        acteursfacteurs = new Acteursfacteurs();
        showFacteur = false;
        showActeur = false;
        detail = false;
    }

    public void prepareEditMenace() {
        mode = "Edit";
        try {
            if (menace.getIdfacteur() != null) {
                facteur = menace.getIdfacteur();
                acteursfacteurs = acteursfacteursFacadeLocal.find(2);
                showFacteur = true;
                showActeur = false;
            } else if (menace.getIdacteur() != null) {
                acteur = menace.getIdacteur();
                acteursfacteurs = acteursfacteursFacadeLocal.find(1);
                showFacteur = false;
                showActeur = true;
            } else {
                acteursfacteurs = acteursfacteursFacadeLocal.find(3);
                showFacteur = false;
                showActeur = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createMenace() {
        try {
            if ("Create".equals(mode)) {
                if (ffomRegion != null) {

                    menace.setIdmenaceRegion(menaceFacadeLocal.nextId());
                    if (acteursfacteurs.getIdacteursfacteurs() == 1) {
                        menace.setIdacteur(acteur);
                    } else if (acteursfacteurs.getIdacteursfacteurs() == 2) {
                        menace.setIdfacteur(facteur);
                    }
                    menace.setIdffomRegion(ffomRegion);
                    menaceFacadeLocal.create(menace);

                    this.updateMenace();
                    this.prepareCreateForce();
                }
            } else {
                if (menace != null) {
                    if (acteursfacteurs.getIdacteursfacteurs() == 1) {
                        menace.setIdacteur(acteurFacadeLocal.find(acteur.getIdacteur()));
                        menace.setIdfacteur(null);
                        menace.setFacteur(false);
                    } else if (acteursfacteurs.getIdacteursfacteurs() == 2) {
                        menace.setIdacteur(null);
                        menace.setIdfacteur(facteurFacadeLocal.find(facteur.getIdfacteur()));
                        menace.setFacteur(true);
                    } else {
                        menace.setIdacteur(null);
                        menace.setIdfacteur(null);
                        menace.setFacteur(false);
                    }
                    menaceFacadeLocal.edit(menace);
                    this.updateMenace();
                    JsfUtil.addSuccessMessage("Opération réussie");
                } else {
                    JsfUtil.addErrorMessage("Aucune ligne selectionnée");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteMenace() {
        try {

            if (menace != null) {
                menaceFacadeLocal.remove(menace);
                if (ffomRegion != null) {
                    this.updateMenace();
                }
                JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                JsfUtil.addSuccessMessage("Opération réussie");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createFfom() {
        try {

            if (ffomRegion != null) {
                if (pilier != null) {
                    ffomRegion.setIdffomRegion(ffomRegionFacadeLocal.nextId());
                    ffomRegion.setIdpilier(pilier);
                    ffomRegion.setIdregion(SessionMBean.getRegion());
                    ffomRegion.setFaiblesse("");
                    ffomRegion.setForce("");
                    ffomRegion.setOpportunite("");
                    ffomRegion.setMenace("");
                    ffomRegionFacadeLocal.create(ffomRegion);

                    showForce = false;
                    showFaiblesse = false;
                    showOpportunite = false;
                    showMenace = false;
                    JsfUtil.addSuccessMessage("FFOM initilisé avec succès");
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

    public FfomRegion getFfom() {
        return ffomRegion;
    }

    public void setFfom(FfomRegion ffom) {
        this.ffomRegion = ffom;
    }

    public Pilier getPilier() {
        return pilier;
    }

    public void setPilier(Pilier pilier) {
        this.pilier = pilier;
    }

    public boolean isShowLoadButton() {
        return showLoadButton;
    }

    public void setShowLoadButton(boolean showLoadButton) {
        this.showLoadButton = showLoadButton;
    }

    public ForceRegion getForce() {
        return force;
    }

    public void setForce(ForceRegion force) {
        this.force = force;
    }

    public List<Pilier> getPiliers() {
        return piliers;
    }

    public void setPiliers(List<Pilier> piliers) {
        this.piliers = piliers;
    }

    public Acteur getActeur() {
        return acteur;
    }

    public void setActeur(Acteur acteur) {
        this.acteur = acteur;
    }

    public List<Acteur> getActeurs() {
        try {
            acteurs.clear();
            List<ActeurRegion> acteurRegions = acteurRegionFacadeLocal.findByRegion(SessionMBean.getRegion());
            if (!acteurRegions.isEmpty()) {
                for (ActeurRegion f : acteurRegions) {
                    acteurs.add(f.getIdacteur());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return acteurs;
    }

    public void setActeurs(List<Acteur> acteurs) {
        this.acteurs = acteurs;
    }

    public Acteursfacteurs getActeursfacteurs() {
        return acteursfacteurs;
    }

    public void setActeursfacteurs(Acteursfacteurs acteursfacteurs) {
        this.acteursfacteurs = acteursfacteurs;
    }

    public List<Acteursfacteurs> getActeursfacteurss() {
        acteursfacteurss = acteursfacteursFacadeLocal.findAllRange();
        return acteursfacteurss;
    }

    public void setActeursfacteurss(List<Acteursfacteurs> acteursfacteurss) {
        this.acteursfacteurss = acteursfacteurss;
    }

    public Facteur getFacteur() {
        return facteur;
    }

    public void setFacteur(Facteur facteur) {
        this.facteur = facteur;
    }

    public List<Facteur> getFacteurs() {
        try {
            facteurs.clear();
            List<FacteurRegion> facteurRegions = facteurRegionFacadeLocal.findByRegion(SessionMBean.getRegion());
            if (!facteurRegions.isEmpty()) {
                for (FacteurRegion f : facteurRegions) {
                    facteurs.add(f.getIdfacteur());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return facteurs;
    }

    public void setFacteurs(List<Facteur> facteurs) {
        this.facteurs = facteurs;
    }

    public List<ForceRegion> getForces() {
        try {
            if (ffomRegion != null) {
                forces = forceFacadeLocal.findByFfom(ffomRegion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return forces;
    }

    public void setForces(List<ForceRegion> forces) {
        this.forces = forces;
    }

    public boolean isShowActeur() {
        return showActeur;
    }

    public void setShowActeur(boolean showActeur) {
        this.showActeur = showActeur;
    }

    public boolean isShowFacteur() {
        return showFacteur;
    }

    public void setShowFacteur(boolean showFacteur) {
        this.showFacteur = showFacteur;
    }

    public FaiblesseRegion getFaiblesse() {
        return faiblesse;
    }

    public void setFaiblesse(FaiblesseRegion faiblesse) {
        this.faiblesse = faiblesse;
    }

    public List<FaiblesseRegion> getFaiblesses() {
        try {
            if (ffomRegion != null) {
                faiblesses = faiblesseFacadeLocal.findByFfom(ffomRegion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return faiblesses;
    }

    public void setFaiblesses(List<FaiblesseRegion> faiblesses) {
        this.faiblesses = faiblesses;
    }

    public OpportuniteRegion getOpportunite() {
        return opportunite;
    }

    public void setOpportunite(OpportuniteRegion opportunite) {
        this.opportunite = opportunite;
    }

    public List<OpportuniteRegion> getOpportunites() {
        try {
            if (ffomRegion != null) {
                opportunites = opportuniteFacadeLocal.findByFfom(ffomRegion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return opportunites;
    }

    public void setOpportunites(List<OpportuniteRegion> opportunites) {
        this.opportunites = opportunites;
    }

    public MenaceRegion getMenace() {
        return menace;
    }

    public void setMenace(MenaceRegion menace) {
        this.menace = menace;
    }

    public List<MenaceRegion> getMenaces() {
        try {
            if (ffomRegion != null) {
                menaces = menaceFacadeLocal.findByFfom(ffomRegion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menaces;
    }

    public void setMenaces(List<MenaceRegion> menaces) {
        this.menaces = menaces;
    }

    public boolean isShowForce() {
        return showForce;
    }

    public boolean isShowFaiblesse() {
        return showFaiblesse;
    }

    public boolean isShowOpportunite() {
        return showOpportunite;
    }

    public boolean isShowMenace() {
        return showMenace;
    }

}
