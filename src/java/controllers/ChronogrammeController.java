/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.ActiviteRegion;
import entities.Annee;
import entities.Axe;
import entities.ChronogrammeRegion;
import entities.CibleRegion;
import entities.Interventionpnds;
import entities.ProblemeRegion;
import entities.Sousaxe;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessions.ActiviteRegionFacadeLocal;
import sessions.AnneeFacadeLocal;
import sessions.AxeFacadeLocal;
import sessions.ChronogrammeRegionFacadeLocal;
import sessions.CibleRegionFacadeLocal;
import sessions.InterventionpndsFacadeLocal;
import sessions.ProblemeRegionFacadeLocal;
import sessions.SousaxeFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@SessionScoped
public class ChronogrammeController {

    @EJB
    private ChronogrammeRegionFacadeLocal chronogrammeRegionFacadeLocal;
    private ChronogrammeRegion chronogrammeRegion = new ChronogrammeRegion();
    private List<ChronogrammeRegion> chronogrammeRegions = new ArrayList<>();

    @EJB
    private ActiviteRegionFacadeLocal activiteRegionFacadeLocal;
    private ActiviteRegion activiteRegion = new ActiviteRegion();
    private List<ActiviteRegion> activiteRegions = new ArrayList<>();

    @EJB
    private InterventionpndsFacadeLocal interventionpndsFacadeLocal;
    private Interventionpnds interventionpnds = new Interventionpnds();

    @EJB
    private CibleRegionFacadeLocal cibleRegionFacadeLocal;
    private CibleRegion cibleRegion = new CibleRegion();
    private List<CibleRegion> cibleRegions = new ArrayList<>();

    @EJB
    private ProblemeRegionFacadeLocal problemeRegionFacadeLocal;
    private ProblemeRegion problemeRegion = new ProblemeRegion();
    private List<ProblemeRegion> problemeRegions = new ArrayList<>();

    @EJB
    private AxeFacadeLocal axeFacadeLocal;
    private Axe axe = new Axe();
    private List<Axe> axes = new ArrayList<>();

    @EJB
    private SousaxeFacadeLocal sousaxeFacadeLocal;
    private Sousaxe sousaxe = new Sousaxe();
    private List<Sousaxe> sousaxes = new ArrayList<>();

    @EJB
    private AnneeFacadeLocal anneeFacadeLocal;
    private Annee annee;
    private List<Annee> annees = new ArrayList<>();

    private boolean detail = true;

    /**
     * Creates a new instance of SousaxeController
     */
    public ChronogrammeController() {
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

                    activiteRegions = activiteRegionFacadeLocal.findByActivitechronogramme(sousaxe, SessionMBean.getRegion());
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

    public void updateAll() {
        try {
            if (axe != null) {
                sousaxes = sousaxeFacadeLocal.findByAxe(axe);

                if (!sousaxes.isEmpty()) {
                    sousaxe = sousaxes.get(0);
                    activiteRegions = activiteRegionFacadeLocal.findByActivitechronogramme(sousaxe, SessionMBean.getRegion());
                } else {
                    sousaxe = new Sousaxe();
                    activiteRegions.clear();
                }
            } else {
                sousaxe = new Sousaxe();
                activiteRegions.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSousaxe() {
        try {
            if (!sousaxes.isEmpty()) {

                if (sousaxe != null) {
                    activiteRegions = activiteRegionFacadeLocal.findByActivitechronogramme(sousaxe, SessionMBean.getRegion());
                }
            } else {
                sousaxe = new Sousaxe();
                activiteRegions.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ChronogrammeRegion findResult(ActiviteRegion activiteRegion, Annee annee) {
        ChronogrammeRegion chronogrammeRegion = new ChronogrammeRegion();
        try {
            List<ChronogrammeRegion> results = chronogrammeRegionFacadeLocal.findByActivite(activiteRegion, annee);
            if (!results.isEmpty()) {
                chronogrammeRegion = results.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chronogrammeRegion;
    }

    public void createChronogramme(ChronogrammeRegion chronogrammeRegion) {
        try {
            if (chronogrammeRegion.getIdchronogrammeRegion() != null) {
                if (!chronogrammeRegion.getEtat()) {
                    chronogrammeRegionFacadeLocal.remove(chronogrammeRegion);
                    uptadeTable();
                } else {

                }
            } else {
                if (chronogrammeRegion.getEtat()) {
                    chronogrammeRegion.setIdchronogrammeRegion(chronogrammeRegionFacadeLocal.nextId());
                    chronogrammeRegion.setEtat(true);
                    chronogrammeRegionFacadeLocal.create(chronogrammeRegion);
                    uptadeTable();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            if (!chronogrammeRegions.isEmpty()) {
                for (ChronogrammeRegion c : chronogrammeRegions) {
                    if (!c.getEtat()) {
                        if (c.getIdchronogrammeRegion() != null) {
                            chronogrammeRegionFacadeLocal.remove(c);
                        }
                    } else {
                        if (c.getIdchronogrammeRegion() == null) {
                            c.setIdchronogrammeRegion(chronogrammeRegionFacadeLocal.nextId());
                            c.setEtat(true);
                            chronogrammeRegionFacadeLocal.create(c);
                        }
                    }
                }
                JsfUtil.addSuccessMessage("Operation réussie");
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
                                    activiteRegions = activiteRegionFacadeLocal.findByActivitechronogramme(sousaxe, SessionMBean.getRegion());
                                    break;
                                } else {
                                    sousaxe = new Sousaxe();
                                    sousaxes.clear();
                                    activiteRegions.clear();
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
                                    activiteRegions = activiteRegionFacadeLocal.findByActivitechronogramme(sousaxe, SessionMBean.getRegion());
                                    break;
                                } else {
                                    activiteRegions.clear();
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

    public void uptadeTable() {
        try {

            chronogrammeRegions.clear();
            if (activiteRegion != null) {

                if (activiteRegion.getActiviteAppui()) {
                    interventionpnds = activiteRegion.getIdintervention();
                } else {
                    interventionpnds = activiteRegion.getIdproblemeRegion().getIdindicateurRegion().getIdindicateur().getIdinterventionpnds();
                }

                if (!this.getAnnees().isEmpty()) {

                    for (Annee a : getAnnees()) {
                        List<ChronogrammeRegion> chronogrammeRegions = chronogrammeRegionFacadeLocal.findByActivite(activiteRegion, a);
                        if (chronogrammeRegions.isEmpty()) {
                            ChronogrammeRegion chronogrammeRegion = new ChronogrammeRegion();
                            chronogrammeRegion.setEtat(false);
                            chronogrammeRegion.setIdactiviteRegion(activiteRegion);
                            chronogrammeRegion.setIdannee(a);
                            this.chronogrammeRegions.add(chronogrammeRegion);
                        } else {
                            chronogrammeRegions.get(0).setEtat(true);
                            this.chronogrammeRegions.add(chronogrammeRegions.get(0));
                        }
                    }
                } else {
                    System.err.println("Aucune annee trouvée");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            if (activiteRegion != null) {
                List<ChronogrammeRegion> chronogrammeRegions = chronogrammeRegionFacadeLocal.findByActivite(activiteRegion);

                for (ChronogrammeRegion c : chronogrammeRegions) {
                    chronogrammeRegionFacadeLocal.remove(c);
                }

                activiteRegions = activiteRegionFacadeLocal.findByActivitechronogramme(sousaxe, SessionMBean.getRegion());
                JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                JsfUtil.addErrorMessage("Aucune activité selectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Echec de l'opération");
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
        try {
            annees = anneeFacadeLocal.findByEtatChronogramme(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public ChronogrammeRegion getChronogrammeRegion() {
        return chronogrammeRegion;
    }

    public void setChronogrammeRegion(ChronogrammeRegion chronogrammeRegion) {
        this.chronogrammeRegion = chronogrammeRegion;
    }

    public List<ChronogrammeRegion> getChronogrammeRegions() {
        return chronogrammeRegions;
    }

    public void setChronogrammeRegions(List<ChronogrammeRegion> chronogrammeRegions) {
        this.chronogrammeRegions = chronogrammeRegions;
    }

    public ActiviteRegion getActiviteRegion() {
        return activiteRegion;
    }

    public void setActiviteRegion(ActiviteRegion activiteRegion) {
        this.activiteRegion = activiteRegion;
    }

    public List<ActiviteRegion> getActiviteRegions() {
        return activiteRegions;
    }

    public void setActiviteRegions(List<ActiviteRegion> activiteRegions) {
        this.activiteRegions = activiteRegions;
    }

    public CibleRegion getCibleRegion() {
        return cibleRegion;
    }

    public void setCibleRegion(CibleRegion cibleRegion) {
        this.cibleRegion = cibleRegion;
    }

    public List<CibleRegion> getCibleRegions() {
        return cibleRegions;
    }

    public void setCibleRegions(List<CibleRegion> cibleRegions) {
        this.cibleRegions = cibleRegions;
    }

    public ProblemeRegion getProblemeRegion() {
        return problemeRegion;
    }

    public void setProblemeRegion(ProblemeRegion problemeRegion) {
        this.problemeRegion = problemeRegion;
    }

    public List<ProblemeRegion> getProblemeRegions() {
        return problemeRegions;
    }

    public void setProblemeRegions(List<ProblemeRegion> problemeRegions) {
        this.problemeRegions = problemeRegions;
    }

    public Interventionpnds getInterventionpnds() {
        return interventionpnds;
    }

    public void setInterventionpnds(Interventionpnds interventionpnds) {
        this.interventionpnds = interventionpnds;
    }

}
