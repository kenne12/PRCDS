/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.Airesante;
import entities.Annee;
import entities.CommentaireRegion;
import entities.Populationfosa;
import entities.Statutstructure;
import entities.Structure;
import entities.Typestructure;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import sessions.AiresanteFacadeLocal;
import sessions.AnneeFacadeLocal;
import sessions.CommentaireRegionFacadeLocal;
import sessions.PopulationfosaFacadeLocal;
import sessions.StatutstructureFacadeLocal;
import sessions.StructureFacadeLocal;
import sessions.TypestructureFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@SessionScoped
public class PopulationfosaController {

    @EJB
    private PopulationfosaFacadeLocal populationfosaFacadeLocal;
    private List<Populationfosa> populationfosas = new ArrayList<>();

    @EJB
    private AnneeFacadeLocal anneeFacadeLocal;
    private Annee annee;
    private List<Annee> annees = new ArrayList<>();

    @EJB
    private StructureFacadeLocal structureFacadeLocal;
    private Structure structure;
    private List<Structure> structures = new ArrayList<>();
    private List<Structure> structures1 = new ArrayList<>();

    @EJB
    private AiresanteFacadeLocal airesanteFacadeLocal;
    private Airesante airesante;
    private List<Airesante> airesantes = new ArrayList<>();

    @EJB

    private TypestructureFacadeLocal typestructureFacadeLocal;
    private Typestructure typestructure;
    private List<Typestructure> typestructures = new ArrayList<>();

    @EJB
    private StatutstructureFacadeLocal statutstructurFacadeLocal;
    private Statutstructure statutstructure;
    private List<Statutstructure> statutstructures = new ArrayList<>();

    @EJB
    private CommentaireRegionFacadeLocal commentaireRegionFacadeLocal;
    private CommentaireRegion commentaireRegion = new CommentaireRegion();

    private boolean detail = true;

    /**
     * Creates a new instance of SousaxeController
     */
    public PopulationfosaController() {
    }

    @PostConstruct
    private void init() {
        structure = new Structure();
        annee = new Annee();
        this.updateCommentaire();
    }

    public void activeButton() {
        detail = false;
    }

    public void deactiveButton() {
        detail = true;
    }

    public void uptadeTable() {
        try {
            populationfosas.clear();
            if (structure != null) {
                if (!this.getAnnees().isEmpty()) {
                    for (Annee m : this.getAnnees()) {
                        List<Populationfosa> temp = populationfosaFacadeLocal.find(structure, m);
                        if (temp.isEmpty()) {
                            Populationfosa populationfosa = new Populationfosa();
                            populationfosa.setIdannee(m);
                            populationfosa.setIdstructure(structure);
                            populationfosas.add(populationfosa);
                        } else {
                            populationfosas.add(temp.get(0));
                        }
                    }
                }
            } else {
                JsfUtil.addErrorMessage("Veuillez selectionnner une ligne");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCommentaire() {
        try {
            List<CommentaireRegion> commentairetabs = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 1);
            if (!commentairetabs.isEmpty()) {
                commentaireRegion = commentairetabs.get(0);
                return;
            }
            commentaireRegion = new CommentaireRegion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void validComment() {
        try {
            List<CommentaireRegion> commentaireRegions = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 1);
            if (commentaireRegions.isEmpty()) {
                commentaireRegion.setIdcommentaireRegion(commentaireRegionFacadeLocal.nextId());
                commentaireRegion.setIdregion(SessionMBean.getRegion());
                commentaireRegion.setNumerotab(1);
                commentaireRegionFacadeLocal.create(commentaireRegion);
            } else {
                commentaireRegionFacadeLocal.edit(commentaireRegion);
            }
            JsfUtil.addSuccessMessage("Opération réussie");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            if (!populationfosas.isEmpty()) {

                for (Populationfosa m : populationfosas) {
                    if (m.getIdpopulationfosa() == null) {
                        m.setIdpopulationfosa(populationfosaFacadeLocal.nextId());
                        m.setPrcds(true);
                        populationfosaFacadeLocal.create(m);
                    } else {
                        populationfosaFacadeLocal.edit(m);
                    }
                }

                List<CommentaireRegion> commentairetabs = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 1);
                if (commentairetabs.isEmpty()) {
                    commentaireRegion.setIdcommentaireRegion(commentaireRegionFacadeLocal.nextId());
                    commentaireRegion.setIdregion(SessionMBean.getRegion());
                    commentaireRegion.setNumerotab(1);
                    commentaireRegionFacadeLocal.create(commentaireRegion);
                } else {
                    commentaireRegionFacadeLocal.edit(commentaireRegion);
                }

                JsfUtil.addSuccessMessage("Opération réussie");
            } else {
                JsfUtil.addErrorMessage("Le tableau est vide");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void commentTable() {
        try {
            List<CommentaireRegion> commentairetabs = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 1);
            if (commentairetabs.isEmpty()) {
                commentaireRegion.setIdcommentaireRegion(commentaireRegionFacadeLocal.nextId());
                commentaireRegion.setIdregion(SessionMBean.getRegion());
                commentaireRegion.setNumerotab(1);
                commentaireRegionFacadeLocal.create(commentaireRegion);
            } else {
                commentaireRegionFacadeLocal.edit(commentaireRegion);
            }
            JsfUtil.addSuccessMessage("Opération réussie");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String findAnnee(Structure structure, Annee annee) {
        String resultat = "";
        try {
            if (annee.getIdannee() != null) {
                annee = anneeFacadeLocal.find(annee.getIdannee());

                if (structure != null) {
                    if (annee != null) {

                        List<Populationfosa> temps = populationfosaFacadeLocal.find(structure, annee);
                        if (!temps.isEmpty()) {
                            resultat = "" + temps.get(0).getPopulationcouverte();
                        } else {
                            resultat = "";
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultat;
    }

    public void initPopulation(Structure structure) {
        try {
            this.structure = structure;
            structures1 = structureFacadeLocal.findByDistrict(structure.getIddistrict().getIddistrict());
            RequestContext.getCurrentInstance().execute("PF('MedicamentDetailDialog').show()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consolidate(Structure structure) {
        try {
            for (Annee a : annees) {

                List<Populationfosa> populationfosas = populationfosaFacadeLocal.find(structure.getIddistrict(), a);
                Double resultat = 0d;

                for (Populationfosa p : populationfosas) {
                    resultat += p.getPopulationcouverte().doubleValue();
                }

                List<Populationfosa> pop1 = populationfosaFacadeLocal.find(structure, a);
                if (pop1.isEmpty()) {
                    Populationfosa pf = new Populationfosa();
                    pf.setIdpopulationfosa(populationfosaFacadeLocal.nextId());
                    pf.setIdannee(a);
                    pf.setIdstructure(structure);
                    pf.setPrcds(true);
                    pf.setPopulationcouverte(BigInteger.valueOf(resultat.longValue()));
                    populationfosaFacadeLocal.create(pf);
                } else {
                    pop1.get(0).setPopulationcouverte(BigInteger.valueOf(resultat.longValue()));
                    populationfosaFacadeLocal.edit(pop1.get(0));
                }
            }
            JsfUtil.addSuccessMessage("Opération réussie");
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

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public List<Structure> getStructures() {
        try {
            structures.clear();
            List<Structure> tmp = structureFacadeLocal.findByRegionNiveau(SessionMBean.getRegion(), 2);
            for (Structure s : tmp) {
                if (s.getConsolide()) {
                    structures.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return structures;
    }

    public void setStructures(List<Structure> structures) {
        this.structures = structures;
    }

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public List<Annee> getAnnees() {
        try {
            annees = anneeFacadeLocal.findByEtatPopulationfosa(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return annees;
    }

    public void setAnnees(List<Annee> annees) {
        this.annees = annees;
    }

    public List<Populationfosa> getPopulationfosas() {
        return populationfosas;
    }

    public void setPopulationfosas(List<Populationfosa> populationfosas) {
        this.populationfosas = populationfosas;
    }

    public CommentaireRegion getCommentaireRegion() {
        return commentaireRegion;
    }

    public void setCommentaireRegion(CommentaireRegion commentaireRegion) {
        this.commentaireRegion = commentaireRegion;
    }

    public Airesante getAiresante() {
        return airesante;
    }

    public void setAiresante(Airesante airesante) {
        this.airesante = airesante;
    }

    public List<Airesante> getAiresantes() {
        airesantes = airesanteFacadeLocal.findAllRange();
        return airesantes;
    }

    public void setAiresantes(List<Airesante> airesantes) {
        this.airesantes = airesantes;
    }

    public Typestructure getTypestructure() {
        return typestructure;
    }

    public void setTypestructure(Typestructure typestructure) {
        this.typestructure = typestructure;
    }

    public List<Typestructure> getTypestructures() {
        typestructures = typestructureFacadeLocal.findAll();
        return typestructures;
    }

    public void setTypestructures(List<Typestructure> typestructures) {
        this.typestructures = typestructures;
    }

    public Statutstructure getStatutstructure() {
        return statutstructure;
    }

    public void setStatutstructure(Statutstructure statutstructure) {
        this.statutstructure = statutstructure;
    }

    public List<Statutstructure> getStatutstructures() {
        statutstructures = statutstructurFacadeLocal.findAll();
        return statutstructures;
    }

    public void setStatutstructures(List<Statutstructure> statutstructures) {
        this.statutstructures = statutstructures;
    }

    public List<Structure> getStructures1() {
        return structures1;
    }

    public void setStructures1(List<Structure> structures1) {
        this.structures1 = structures1;
    }

}
