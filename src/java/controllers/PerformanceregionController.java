/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.Annee;
import entities.Axe;
import entities.Categorieintervention;
import entities.Indicateur;
import entities.IndicateurRegion;
import entities.ProblemeRegion;
import entities.Sousaxe;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessions.AnneeFacadeLocal;
import sessions.AxeFacadeLocal;
import sessions.CategorieinterventionFacadeLocal;
import sessions.IndicateurFacadeLocal;
import sessions.IndicateurRegionFacadeLocal;
import sessions.NiveaucollecteFacadeLocal;
import sessions.ObservationFacadeLocal;
import sessions.ProblemeRegionFacadeLocal;
import sessions.SousaxeFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@SessionScoped
public class PerformanceregionController implements Serializable{

    @EJB
    private AxeFacadeLocal axeFacadeLocal;
    private Axe axe = new Axe();
    private List<Axe> axes = new ArrayList<>();

    @EJB
    private SousaxeFacadeLocal sousaxeFacadeLocal;
    private Sousaxe sousaxe = new Sousaxe();
    private List<Sousaxe> sousaxes = new ArrayList<>();

    @EJB
    private CategorieinterventionFacadeLocal categorieinterventionFacadeLocal;
    private Categorieintervention categorieintervention = new Categorieintervention();
    private List<Categorieintervention> categorieinterventions = new ArrayList<>();

    @EJB
    private IndicateurFacadeLocal indicateurFacadeLocal;
    private Indicateur indicateur = new Indicateur();
    private List<Indicateur> indicateurs = new ArrayList<>();

    @EJB
    private NiveaucollecteFacadeLocal niveaucollecteFacadeLocal;

    @EJB
    private IndicateurRegionFacadeLocal indicateurRegionFacadeLocal;
    private IndicateurRegion indicateurRegion = new IndicateurRegion();

    @EJB
    private ObservationFacadeLocal observationFacadeLocal;

    @EJB
    private AnneeFacadeLocal anneeFacadeLocal;
    private Annee annee;
    private List<Annee> annees = new ArrayList<>();

    @EJB
    private ProblemeRegionFacadeLocal problemeRegionFacadeLocal;
    private ProblemeRegion problemeRegion = new ProblemeRegion();

    private boolean detail = true;
    private boolean drapeau = false;

    /**
     * Creates a new instance of SousaxeController
     */
    public PerformanceregionController() {
    }

    @PostConstruct
    private void init() {

        annee = new Annee();
        axes = axeFacadeLocal.findAllRangeByCode();

        try {
            if (!axes.isEmpty()) {

                axe = axes.get(0);

                sousaxes = sousaxeFacadeLocal.findByAxe(axe);

                if (!sousaxes.isEmpty()) {
                    sousaxe = sousaxes.get(0);

                    categorieinterventions = categorieinterventionFacadeLocal.find(sousaxe);

                    if (!categorieinterventions.isEmpty()) {
                        categorieintervention = categorieinterventions.get(0);
                        indicateurs = indicateurFacadeLocal.findByCategorieIntervention(categorieintervention, niveaucollecteFacadeLocal.find(1));
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
        try {
            if (indicateur != null) {
                List<IndicateurRegion> temp = indicateurRegionFacadeLocal.findByIndicateur(indicateur, SessionMBean.getRegion());
                annee = new Annee();
                if (temp.isEmpty()) {
                    indicateurRegion = new IndicateurRegion();
                    indicateurRegion.setCause("-");
                    indicateurRegion.setObservation("-");
                    drapeau = false;
                    indicateurRegion.setProbleme(indicateur.getModeleprobleme());
                } else {
                    if (temp.get(0).getIdannee() != null) {
                        annee = temp.get(0).getIdannee();
                    }
                    indicateurRegion = temp.get(0);
                    drapeau = true;
                }
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

                    categorieinterventions = categorieinterventionFacadeLocal.find(sousaxe);

                    if (!categorieinterventions.isEmpty()) {
                        categorieintervention = categorieinterventions.get(0);
                        indicateurs = indicateurFacadeLocal.findByCategorieIntervention(categorieintervention, niveaucollecteFacadeLocal.find(1));
                    }
                } else {
                    categorieintervention = new Categorieintervention();
                    sousaxe = new Sousaxe();
                    indicateurs.clear();
                }
            } else {
                categorieintervention = new Categorieintervention();
                sousaxe = new Sousaxe();
                indicateurs.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSousaxe() {
        try {
            if (!sousaxes.isEmpty()) {

                if (sousaxe != null) {

                    categorieinterventions = categorieinterventionFacadeLocal.find(sousaxe);

                    if (!categorieinterventions.isEmpty()) {
                        categorieintervention = categorieinterventions.get(0);
                        indicateurs = indicateurFacadeLocal.findByCategorieIntervention(categorieintervention, niveaucollecteFacadeLocal.find(1));
                    } else {
                        indicateurs.clear();
                        categorieinterventions.clear();
                        categorieintervention = new Categorieintervention();
                    }
                }
            } else {
                categorieintervention = new Categorieintervention();
                sousaxe = new Sousaxe();
                indicateurs.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCategorie() {
        try {
            if (!sousaxes.isEmpty()) {

                if (sousaxe != null) {

                    categorieinterventions = categorieinterventionFacadeLocal.find(sousaxe);

                    if (!categorieinterventions.isEmpty()) {
                        categorieintervention = categorieinterventions.get(0);
                    } else {
                        indicateurs.clear();
                        categorieinterventions.clear();
                    }
                }
            } else {
                categorieintervention = new Categorieintervention();
                sousaxe = new Sousaxe();
                indicateurs.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateIndicateur() {
        try {

            if (categorieintervention != null) {
                indicateurs = indicateurFacadeLocal.findByCategorieIntervention(categorieintervention, niveaucollecteFacadeLocal.find(1));
            } else {
                indicateurs.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IndicateurRegion findResult(Indicateur indicateur) {
        IndicateurRegion indicateurRegion = new IndicateurRegion();
        try {
            if (indicateur != null) {
                if (SessionMBean.getRegion() != null) {
                    List<IndicateurRegion> results = indicateurRegionFacadeLocal.findByIndicateur(indicateur, SessionMBean.getRegion());
                    if (!results.isEmpty()) {
                        indicateurRegion = results.get(0);
                        indicateurRegion.getValeur();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return indicateurRegion;
    }

    public void create() {
        try {
            if (!drapeau) {

                indicateurRegion.setIdindicateurRegion(indicateurRegionFacadeLocal.nextVal());
                indicateurRegion.setIdindicateur(indicateur);
                indicateurRegion.setIdregion(SessionMBean.getRegion());

                if (annee.getIdannee() != null) {
                    indicateurRegion.setIdannee(annee);
                }

                if (indicateur.getInverse()) {
                    if (indicateurRegion.getValeur() <= indicateur.getCiblenationale()) {

                        indicateurRegion.setIdobservation(observationFacadeLocal.find(1));
                        //indicateurRegion.setIdobservation(new Observation(1));
                        indicateurRegionFacadeLocal.create(indicateurRegion);

                        problemeRegion = new ProblemeRegion();
                        problemeRegion.setIdproblemeRegion(problemeRegionFacadeLocal.nextId());
                        problemeRegion.setNom("R A S");
                        problemeRegion.setCause("R A S");
                        problemeRegion.setObjectif("R A S");
                        
                        problemeRegion.setIdindicateurRegion(indicateurRegion);
                        problemeRegion.setFaible(false);
                        problemeRegion.setTotalpoint(0d);
                        problemeRegionFacadeLocal.create(problemeRegion);

                    } else {
                        //indicateurRegion.setIdobservation(new Observation(2));
                        indicateurRegion.setIdobservation(observationFacadeLocal.find(2));
                        indicateurRegionFacadeLocal.create(indicateurRegion);
                    }

                } else {
                    if (indicateurRegion.getValeur() < indicateur.getCiblenationale()) {
                        //indicateurRegion.setIdobservation(new Observation(2));
                        indicateurRegion.setIdobservation(observationFacadeLocal.find(2));
                        indicateurRegionFacadeLocal.create(indicateurRegion);
                    } else {
                        indicateurRegion.setIdobservation(observationFacadeLocal.find(1));
                        //indicateurRegion.setIdobservation(new Observation(1));
                        indicateurRegionFacadeLocal.create(indicateurRegion);
                        problemeRegion = new ProblemeRegion();
                        problemeRegion.setIdproblemeRegion(problemeRegionFacadeLocal.nextId());
                        problemeRegion.setNom("R A S");
                        problemeRegion.setCause("R A S");
                        problemeRegion.setObjectif("R A S");
                        problemeRegion.setIdindicateurRegion(indicateurRegion);
                        problemeRegion.setFaible(false);
                        problemeRegion.setTotalpoint(0d);
                        problemeRegionFacadeLocal.create(problemeRegion);
                    }
                }
                JsfUtil.addSuccessMessage("Opération réussie");
            } else {

                if (annee.getIdannee() != null) {
                    indicateurRegion.setIdannee(anneeFacadeLocal.find(annee.getIdannee()));
                }

                if (indicateurRegion.getIdindicateur().getInverse()) {
                    if (indicateurRegion.getValeur() <= indicateur.getCiblenationale()) {
                        indicateurRegion.setIdobservation(observationFacadeLocal.find(1));
                    } else {
                        indicateurRegion.setIdobservation(observationFacadeLocal.find(2));
                    }
                } else {
                    if (indicateurRegion.getValeur() < indicateur.getCiblenationale()) {
                        //indicateurRegion.setIdobservation(new Observation(2));
                        indicateurRegion.setIdobservation(observationFacadeLocal.find(2));
                    } else {
                        //indicateurRegion.setIdobservation(new Observation(1));
                        indicateurRegion.setIdobservation(observationFacadeLocal.find(1));
                    }
                }
                indicateurRegionFacadeLocal.edit(indicateurRegion);
                JsfUtil.addSuccessMessage("Opération réussie");
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
                                    return;
                                }
                                axe = axes.get(i + 1);
                                this.updateAll();
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
                                this.updateAll();
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

    public void nextCategorie() {
        try {
            if (!categorieinterventions.isEmpty()) {
                if (categorieinterventions.size() > 1) {
                    int i = 0;
                    for (Categorieintervention c : categorieinterventions) {

                        if (c.equals(categorieintervention)) {
                            if (i <= categorieinterventions.size()) {

                                if (i + 1 == categorieinterventions.size()) {
                                    return;
                                }
                                categorieintervention = categorieinterventions.get(i + 1);
                                this.updateIndicateur();
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

    public void precCategorie() {
        try {
            if (!categorieinterventions.isEmpty()) {
                if (categorieinterventions.size() > 1) {
                    int i = 0;
                    for (Categorieintervention c : categorieinterventions) {
                        if (c.equals(categorieintervention)) {

                            if (i == 0) {
                                categorieintervention = categorieinterventions.get(i);
                                this.updateIndicateur();
                                break;
                            } else {
                                categorieintervention = categorieinterventions.get(i - 1);
                                this.updateIndicateur();
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

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public List<Annee> getAnnees() {
        annees = anneeFacadeLocal.findAllRange();
        return annees;
    }

    public void setAnnees(List<Annee> annees) {
        this.annees = annees;
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

    public Categorieintervention getCategorieintervention() {
        return categorieintervention;
    }

    public void setCategorieintervention(Categorieintervention categorieintervention) {
        this.categorieintervention = categorieintervention;
    }

    public List<Categorieintervention> getCategorieinterventions() {
        return categorieinterventions;
    }

    public void setCategorieinterventions(List<Categorieintervention> categorieinterventions) {
        this.categorieinterventions = categorieinterventions;
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

    public IndicateurRegion getIndicateurRegion() {
        return indicateurRegion;
    }

    public void setIndicateurRegion(IndicateurRegion indicateurRegion) {
        this.indicateurRegion = indicateurRegion;
    }

}
