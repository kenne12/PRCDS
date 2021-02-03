/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.Annee;
import entities.CommentaireRegion;
import entities.PopulationRegion;
import entities.Region;
import entities.Rubriquepopulation;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessions.AnneeFacadeLocal;
import sessions.CommentaireRegionFacadeLocal;
import sessions.PopulationRegionFacadeLocal;
import sessions.RubriquepopulationFacadeLocal;
import utilitaire.Utilitaires;

/**
 *
 * @author kenne
 */
@ManagedBean
@SessionScoped
public class PopulationController {

    @EJB
    private PopulationRegionFacadeLocal populationRegionFacadeLocal;
    private List<PopulationRegion> populationRegions = new ArrayList<>();

    @EJB
    private RubriquepopulationFacadeLocal rubriquepopulationFacadeLocal;
    private List<Rubriquepopulation> rubriquepopulations = new ArrayList<>();

    @EJB
    private AnneeFacadeLocal anneeFacadeLocal;
    private Annee annee = new Annee();
    private List<Annee> annees = new ArrayList<>();

    @EJB
    private CommentaireRegionFacadeLocal commentaireRegionFacadeLocal;
    private CommentaireRegion commentairetab = new CommentaireRegion();

    private Region region = new Region();

    Double effectif = 0.0;

    private boolean detail = true;

    public PopulationController() {
    }

    @PostConstruct
    private void init() {
        annee = new Annee();
        region = SessionMBean.getRegion();
        try {
            List<CommentaireRegion> commentairetabs = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 2);
            if (!commentairetabs.isEmpty()) {
                commentairetab = commentairetabs.get(0);
                return;
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

    public void initAnnee() {
        try {
            if (annee.getIdannee() != null) {
                annee = anneeFacadeLocal.find(annee.getIdannee());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTable() {
        try {
            if (effectif != null && effectif != 0.0) {
                if (!populationRegions.isEmpty()) {
                    int i = 0;
                    for (PopulationRegion p : populationRegions) {
                        Double resultat = (effectif * p.getIdrubriquepopulation().getPourcentage()) / 100;
                        populationRegions.get(i).setValeurpopulationrubrique(Utilitaires.arrondiNDecimales(resultat, 1));
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadTable() {
        try {

            effectif = 0D;

            populationRegions.clear();
            Region region = SessionMBean.getRegion();
            if (annee != null) {

                if (SessionMBean.getRegion() != null) {
                    if (!this.getRubriquepopulations().isEmpty()) {

                        if (populationRegionFacadeLocal.find(region, annee).isEmpty()) {
                            for (Rubriquepopulation r : this.getRubriquepopulations()) {
                                PopulationRegion population = new PopulationRegion();
                                population.setIdannee(annee);
                                population.setIdregion(region);
                                population.setIdrubriquepopulation(r);

                                populationRegions.add(population);
                            }
                        } else {
                            for (Rubriquepopulation r : this.getRubriquepopulations()) {
                                List<PopulationRegion> temp = populationRegionFacadeLocal.find(region, r, annee);
                                effectif = temp.get(0).getValeurpopulationregion();
                                if (temp.isEmpty()) {

                                    PopulationRegion population = new PopulationRegion();
                                    population.setIdannee(annee);
                                    population.setIdregion(region);
                                    population.setIdrubriquepopulation(r);

                                    populationRegions.add(population);
                                } else {
                                    populationRegions.add(temp.get(0));
                                }
                            }
                        }
                    } else {
                        System.err.println("Aucune rubrique de la poulation");
                    }
                } else {
                    JsfUtil.addErrorMessage("Veuillez selectionnner un district");
                }
            } else {
                JsfUtil.addErrorMessage("Veillez selectionner une année");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCommentaire() {
        try {
            List<CommentaireRegion> commentairetabs = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 2);
            if (!commentairetabs.isEmpty()) {
                commentairetab = commentairetabs.get(0);
                return;
            }
            commentairetab = new CommentaireRegion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validComment() {
        try {
            List<CommentaireRegion> commentaireRegions = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 2);
            if (commentaireRegions.isEmpty()) {
                commentairetab.setIdcommentaireRegion(commentaireRegionFacadeLocal.nextId());
                commentairetab.setIdregion(SessionMBean.getRegion());
                commentairetab.setNumerotab(2);
                commentaireRegionFacadeLocal.create(commentairetab);
            } else {
                commentaireRegionFacadeLocal.edit(commentairetab);
            }
            JsfUtil.addSuccessMessage("Opération réussie");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            if (!populationRegions.isEmpty()) {
                for (PopulationRegion p : populationRegions) {
                    if (p.getIdpopulationRegion() == null) {
                        p.setIdpopulationRegion(populationRegionFacadeLocal.nextId());
                        p.setValeurpopulationregion(effectif);
                        populationRegionFacadeLocal.create(p);
                    } else {
                        p.setValeurpopulationregion(effectif);
                        populationRegionFacadeLocal.edit(p);
                    }
                }

                List<CommentaireRegion> commentairetabs = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 2);
                if (commentairetabs.isEmpty()) {
                    commentairetab.setIdcommentaireRegion(commentaireRegionFacadeLocal.nextId());
                    commentairetab.setIdregion(SessionMBean.getRegion());
                    commentairetab.setNumerotab(2);
                    commentaireRegionFacadeLocal.create(commentairetab);
                } else {
                    commentaireRegionFacadeLocal.edit(commentairetab);
                }
                this.updateCommentaire();
                JsfUtil.addSuccessMessage("Opération réussie");
            } else {
                JsfUtil.addErrorMessage("Le tableau est vide");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PopulationRegion findPopulation(Rubriquepopulation rubriquepopulation, Annee annee) {
        PopulationRegion population = null;
        try {
            if (annee.getIdannee() != null) {
                if (SessionMBean.getRegion() != null) {
                    if (rubriquepopulation != null) {

                        List<PopulationRegion> temps = populationRegionFacadeLocal.find(SessionMBean.getRegion(), rubriquepopulation, annee);
                        if (!temps.isEmpty()) {
                            population = temps.get(0);
                        } else {
                            population = new PopulationRegion();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return population;
    }

    public PopulationRegion findTotalPopulation(Annee annee) {
        PopulationRegion population = null;
        try {
            if (annee.getIdannee() != null) {
                if (SessionMBean.getRegion() != null) {

                    List<PopulationRegion> temps = populationRegionFacadeLocal.find(SessionMBean.getRegion(), annee);
                    if (!temps.isEmpty()) {
                        population = temps.get(0);
                    } else {
                        population = new PopulationRegion();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            population = new PopulationRegion();
        }
        return population;
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
        annees.remove(0);
        return annees;
    }

    public void setAnnees(List<Annee> annees) {
        this.annees = annees;
    }

    public CommentaireRegion getCommentairetab() {
        return commentairetab;
    }

    public void setCommentairetab(CommentaireRegion commentairetab) {
        this.commentairetab = commentairetab;
    }

    public List<PopulationRegion> getPopulationRegions() {
        return populationRegions;
    }

    public void setPopulationRegions(List<PopulationRegion> populationRegions) {
        this.populationRegions = populationRegions;
    }

    public List<Rubriquepopulation> getRubriquepopulations() {
        rubriquepopulations = rubriquepopulationFacadeLocal.findAllRange();
        return rubriquepopulations;
    }

    public void setRubriquepopulations(List<Rubriquepopulation> rubriquepopulations) {
        this.rubriquepopulations = rubriquepopulations;
    }

    public Double getEffectif() {
        return effectif;
    }

    public void setEffectif(Double effectif) {
        this.effectif = effectif;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

}
