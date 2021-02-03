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
import entities.Indicateur;
import entities.IndicateurRegion;
import entities.Notationproblemeregion;
import entities.ProblemeRegion;
import entities.Sousaxe;
import entities.Sousrubriquenotationprobleme;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessions.AnneeFacadeLocal;
import sessions.AxeFacadeLocal;
import sessions.IndicateurFacadeLocal;
import sessions.IndicateurRegionFacadeLocal;
import sessions.NotationproblemeregionFacadeLocal;
import sessions.ProblemeRegionFacadeLocal;
import sessions.SousaxeFacadeLocal;
import sessions.SousrubriquenotationproblemeFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@SessionScoped
public class NotationproblemeController implements Serializable {

    @EJB
    private NotationproblemeregionFacadeLocal notationproblemeregionFacadeLocal;
    private Notationproblemeregion notationproblemeregion = new Notationproblemeregion();
    private List<Notationproblemeregion> notationproblemeregions = new ArrayList<>();

    @EJB
    private SousrubriquenotationproblemeFacadeLocal sousrubriquenotationproblemeFacadeLocal;
    private Sousrubriquenotationprobleme sousrubriquenotationprobleme = new Sousrubriquenotationprobleme();
    private List<Sousrubriquenotationprobleme> sousrubriquenotationproblemes = new ArrayList<>();

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
    private IndicateurFacadeLocal indicateurFacadeLocal;
    private Indicateur indicateur = new Indicateur();
    private List<Indicateur> indicateurs = new ArrayList<>();

    @EJB
    private IndicateurRegionFacadeLocal indicateurRegionFacadeLocal;
    private IndicateurRegion indicateurRegion = new IndicateurRegion();

    @EJB
    private AnneeFacadeLocal anneeFacadeLocal;
    private Annee annee = new Annee();
    private List<Annee> annees = new ArrayList<>();

    private boolean detail = true;

    /**
     * Creates a new instance of SousaxeController
     */
    public NotationproblemeController() {
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
                    problemeRegions = problemeRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);
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
                    problemeRegions = problemeRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);
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
                    problemeRegions = problemeRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);
                }
            } else {
                sousaxe = new Sousaxe();
                problemeRegions.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Notationproblemeregion findResult(ProblemeRegion problemeRegion, Sousrubriquenotationprobleme sousrubriquenotationprobleme) {
        Notationproblemeregion notationproblemeregion = new Notationproblemeregion();
        try {
            List<Notationproblemeregion> results = notationproblemeregionFacadeLocal.find(problemeRegion, sousrubriquenotationprobleme);
            if (!results.isEmpty()) {
                notationproblemeregion = results.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notationproblemeregion;
    }

    public void create() {
        try {
            if (!notationproblemeregions.isEmpty()) {
                Double totalpoint = 0d;
                Double a = 0d;
                Double b = 0d;
                Double c = 0d;
                Double d = 0d;

                for (Notationproblemeregion n : notationproblemeregions) {

                    totalpoint += n.getValeur();
                    if (n.getIdsousrubriquenotationprobleme().getIdsousrubriquenotationprobleme() == 1) {
                        a = n.getValeur();
                    } else if (n.getIdsousrubriquenotationprobleme().getIdsousrubriquenotationprobleme() == 2) {
                        b = n.getValeur();
                    } else if (n.getIdsousrubriquenotationprobleme().getIdsousrubriquenotationprobleme() == 3) {
                        c = n.getValeur();
                    } else {
                        d = n.getValeur();
                    }
                    if (n.getIdnotationproblemeregion() == 0L) {
                        n.setIdnotationproblemeregion(notationproblemeregionFacadeLocal.nextId());
                        notationproblemeregionFacadeLocal.create(n);
                    } else {
                        notationproblemeregionFacadeLocal.edit(n);
                    }
                }
                totalpoint = (a + b) * c * d;
                problemeRegion.setTotalpoint(totalpoint);
                problemeRegionFacadeLocal.edit(problemeRegion);

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
                                    problemeRegions = problemeRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);
                                } else {
                                    sousaxe = new Sousaxe();
                                    sousaxes.clear();
                                    problemeRegions.clear();
                                }
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

                                sousaxes = sousaxeFacadeLocal.findByAxe(axe);
                                if (!sousaxes.isEmpty()) {
                                    sousaxe = sousaxes.get(0);
                                    problemeRegions = problemeRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);
                                } else {
                                    sousaxe = new Sousaxe();
                                    problemeRegions.clear();
                                }
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

    public void uptadeTable() {
        try {
            notationproblemeregions.clear();

            if (problemeRegion != null) {
                if (!this.getSousrubriquenotationproblemes().isEmpty()) {
                    List<Notationproblemeregion> temp = notationproblemeregionFacadeLocal.find(problemeRegion);
                    if (temp.isEmpty()) {
                        for (Sousrubriquenotationprobleme s : this.getSousrubriquenotationproblemes()) {
                            notationproblemeregion = new Notationproblemeregion();
                            notationproblemeregion.setIdnotationproblemeregion(0L);
                            notationproblemeregion.setIdproblemeRegion(problemeRegion);
                            notationproblemeregion.setIdsousrubriquenotationprobleme(s);
                            notationproblemeregions.add(notationproblemeregion);
                        }
                    } else {
                        for (Sousrubriquenotationprobleme s : this.getSousrubriquenotationproblemes()) {
                            
                            for(Notationproblemeregion n :temp){
                                if(n.getIdsousrubriquenotationprobleme().equals(s)){
                                    notationproblemeregions.add(n);
                                    break;
                                }
                            }                            
                        }
                    }
                } else {
                    System.err.println("Aucune rubrique trouvée");
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

    public Sousrubriquenotationprobleme getSousrubriquenotationprobleme() {
        return sousrubriquenotationprobleme;
    }

    public void setSousrubriquenotationprobleme(Sousrubriquenotationprobleme sousrubriquenotationprobleme) {
        this.sousrubriquenotationprobleme = sousrubriquenotationprobleme;
    }

    public List<Sousrubriquenotationprobleme> getSousrubriquenotationproblemes() {
        sousrubriquenotationproblemes = sousrubriquenotationproblemeFacadeLocal.findAll();
        return sousrubriquenotationproblemes;
    }

    public void setSousrubriquenotationproblemes(List<Sousrubriquenotationprobleme> sousrubriquenotationproblemes) {
        this.sousrubriquenotationproblemes = sousrubriquenotationproblemes;
    }

    public Notationproblemeregion getNotationproblemeregion() {
        return notationproblemeregion;
    }

    public void setNotationproblemeregion(Notationproblemeregion notationproblemeregion) {
        this.notationproblemeregion = notationproblemeregion;
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

    public IndicateurRegion getIndicateurRegion() {
        return indicateurRegion;
    }

    public void setIndicateurRegion(IndicateurRegion indicateurRegion) {
        this.indicateurRegion = indicateurRegion;
    }

    public List<Notationproblemeregion> getNotationproblemeregions() {
        return notationproblemeregions;
    }

    public void setNotationproblemeregions(List<Notationproblemeregion> notationproblemeregions) {
        this.notationproblemeregions = notationproblemeregions;
    }

}
