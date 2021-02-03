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
import entities.CibleRegion;
import entities.District;
import entities.IndicateurRegion;
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
import sessions.CibleRegionFacadeLocal;
import sessions.IndicateurRegionFacadeLocal;
import sessions.ProblemeRegionFacadeLocal;
import sessions.SousaxeFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@SessionScoped
public class CibleController {

    @EJB
    private CibleRegionFacadeLocal cibleRegionRegionFacadeLocal;
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
    private List<Annee> annees = new ArrayList<>();

    @EJB
    private ActiviteRegionFacadeLocal activiteRegionFacadeLocal;
    private ActiviteRegion activiteRegion = new ActiviteRegion();

    @EJB
    private IndicateurRegionFacadeLocal indicateurRegionFacadeLocal;
    private IndicateurRegion indicateurRegion;
    private List<IndicateurRegion> indicateurRegions = new ArrayList<>();

    private District district = new District();

    private boolean detail = true;

    /**
     * Creates a new instance of SousaxeController
     */
    public CibleController() {
    }

    @PostConstruct
    private void init() {
        axes = axeFacadeLocal.findAllRangeByCode();
        district = SessionMBean.getDistrict();

        try {
            district = SessionMBean.getDistrict();
            if (!axes.isEmpty()) {

                axe = axes.get(0);
                sousaxes = sousaxeFacadeLocal.findByAxe(axe);

                if (!sousaxes.isEmpty()) {
                    sousaxe = sousaxes.get(0);
                    indicateurRegions = indicateurRegionFacadeLocal.findByRegionSousaxe(SessionMBean.getRegion(), sousaxe);
                    //problemeRegions = problemeRegionFacadeLocal.find(SessionMBean.getDistrict(), sousaxe, true);
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
                    //problemeRegions = problemeRegionFacadeLocal.find(SessionMBean.getDistrict(), sousaxe, true);
                    indicateurRegions = indicateurRegionFacadeLocal.findByRegionSousaxe(SessionMBean.getRegion(), sousaxe);
                } else {
                    sousaxe = new Sousaxe();
                    problemeRegions.clear();
                }
            } else {
                sousaxe = new Sousaxe();
                problemeRegions.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSousaxe() {
        try {
            if (!sousaxes.isEmpty()) {

                if (sousaxe != null) {
                    indicateurRegions = indicateurRegionFacadeLocal.findByRegionSousaxe(SessionMBean.getRegion(), sousaxe);
                    //problemeRegions = problemeRegionFacadeLocal.find(SessionMBean.getDistrict(), sousaxe, true);
                }
            } else {
                sousaxe = new Sousaxe();
                indicateurRegions.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CibleRegion findResult(IndicateurRegion indicateurRegion, Annee annee) {
        CibleRegion cibleRegion = new CibleRegion();
        try {
            List<CibleRegion> results = cibleRegionRegionFacadeLocal.find(indicateurRegion, annee);
            if (!results.isEmpty()) {
                cibleRegion = results.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cibleRegion;
    }

    public void create() {
        try {
            if (!cibleRegions.isEmpty()) {
                for (CibleRegion c : cibleRegions) {
                    if (c.getIdcibleRegion() == null) {
                        c.setIdcibleRegion(cibleRegionRegionFacadeLocal.nextId());
                        cibleRegionRegionFacadeLocal.create(c);
                    } else {
                        cibleRegionRegionFacadeLocal.edit(c);
                    }
                }

                //problemeRegionFacadeLocal.edit(problemeRegion);
                JsfUtil.addSuccessMessage("Opération réussie");
            } else {
                JsfUtil.addErrorMessage("Le tableau est vide");
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
                                    indicateurRegions = indicateurRegionFacadeLocal.findByRegionSousaxe(SessionMBean.getRegion(), sousaxe);
                                    //problemeRegions = problemeRegionFacadeLocal.find(SessionMBean.getDistrict(), sousaxe, true);
                                    break;
                                } else {
                                    sousaxe = new Sousaxe();
                                    sousaxes.clear();
                                    indicateurRegions.clear();
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
                                    indicateurRegions = indicateurRegionFacadeLocal.findByRegionSousaxe(SessionMBean.getRegion(), sousaxe);
                                    //problemeRegions = problemeRegionFacadeLocal.find(SessionMBean.getDistrict(), sousaxe, true);
                                    break;
                                } else {
                                    indicateurRegions.clear();
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
            cibleRegions.clear();
            activiteRegion = new ActiviteRegion();

            if (indicateurRegion != null) {
                if (!this.getAnnees().isEmpty()) {

                    if (cibleRegionRegionFacadeLocal.find(indicateurRegion).isEmpty()) {

                        for (Annee a : this.getAnnees()) {
                            CibleRegion c = new CibleRegion();
                            c.setIdannee(a);
                            c.setIdregion(SessionMBean.getRegion());
                            c.setIdindicateur(indicateurRegion.getIdindicateur());
                            cibleRegions.add(c);
                        }
                    } else {

                        for (Annee a : this.getAnnees()) {
                            List<CibleRegion> temp = cibleRegionRegionFacadeLocal.find(indicateurRegion, a);
                            if (temp.isEmpty()) {
                                CibleRegion c = new CibleRegion();
                                c.setIdannee(a);
                                c.setIdregion(SessionMBean.getRegion());
                                c.setIdindicateur(indicateurRegion.getIdindicateur());
                                cibleRegions.add(c);

                            } else {
                                cibleRegions.add(temp.get(0));
                            }
                        }
                    }
                } else {
                    System.err.println("Aucune annee trouvée");
                }
                
                /*List<ActiviteRegion> activiteRegions = activiteRegionFacadeLocal.findByProbleme(problemeRegion);
                if (!activiteRegions.isEmpty()) {
                    activiteRegion = activiteRegions.get(0);
                }*/
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

    public List<Annee> getAnnees() {
        annees = anneeFacadeLocal.findByEtatCibles(true);
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

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public ActiviteRegion getActiviteRegion() {
        return activiteRegion;
    }

    public void setActiviteRegion(ActiviteRegion activiteRegion) {
        this.activiteRegion = activiteRegion;
    }

    public IndicateurRegion getIndicateurRegion() {
        return indicateurRegion;
    }

    public void setIndicateurRegion(IndicateurRegion indicateurRegion) {
        this.indicateurRegion = indicateurRegion;
    }

    public List<IndicateurRegion> getIndicateurRegions() {
        return indicateurRegions;
    }

    public void setIndicateurRegions(List<IndicateurRegion> indicateurRegions) {
        this.indicateurRegions = indicateurRegions;
    }

}
