/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.ActiviteRegion;
import entities.ActiviteStructureRegion;
import entities.Annee;
import entities.Recette;
import entities.Structure;
import entities.Typestructure;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessions.ActiviteRegionFacadeLocal;
import sessions.ActiviteStructureRegionFacadeLocal;
import sessions.AnneeFacadeLocal;
import sessions.RecetteFacadeLocal;
import sessions.StructureFacadeLocal;
import sessions.TypestructureFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@ViewScoped
public class CadragebudgetaireController implements Serializable {

    @EJB
    private ActiviteStructureRegionFacadeLocal activiteStructureRegionFacadeLocal;
    private ActiviteStructureRegion activiteStructureRegion = new ActiviteStructureRegion();
    private List<ActiviteStructureRegion> activiteStructureRegions = new ArrayList<>();

    @EJB
    private TypestructureFacadeLocal typestructureFacadeLocal;
    private Typestructure typestructure = new Typestructure();
    private List<Typestructure> typestructures = new ArrayList<>();

    @EJB
    private StructureFacadeLocal structureFacadeLocal;
    private Structure structure = new Structure();
    private List<Structure> structures = new ArrayList<>();

    @EJB
    private AnneeFacadeLocal anneeFacadeLocal;
    private Annee annee = new Annee();
    private List<Annee> annees = new ArrayList<>();

    @EJB
    private ActiviteRegionFacadeLocal activiteRegionFacadeLocal;

    @EJB
    private RecetteFacadeLocal recetteFacadeLocal;

    private Double totalRecette = 0d;

    private boolean detail = true;

    private String mode = "";

    /**
     * Creates a new instance of SousaxeController
     */
    public CadragebudgetaireController() {
    }

    @PostConstruct
    private void init() {
        typestructure = new Typestructure();
        structure = new Structure();
    }

    public void activeButton() {
        detail = false;
    }

    public void deactiveButton() {
        detail = true;
    }

    public void prepareCreate() {
        mode = "Create";
    }

    public void uptadeTable() {
        try {
            if (structure.getIdstructure() != null) {
                structure = structureFacadeLocal.find(structure.getIdstructure());
                if (annee.getIdannee() != null) {

                    annee = anneeFacadeLocal.find(annee.getIdannee());
                    if (!activiteStructureRegions.isEmpty()) {
                        int i = 0;
                        for (ActiviteStructureRegion a : activiteStructureRegions) {

                            if (!a.getPrograme()) {
                                activiteStructureRegions.get(i).setCout(a.getIdactiviteRegion().getCoutunitaire());
                            }
                            i++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createCadrage(ActiviteStructureRegion activiteStructureRegion) {
        try {
            activiteStructureRegionFacadeLocal.edit(activiteStructureRegion);
            uptadeTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            if (!activiteStructureRegions.isEmpty()) {
                for (ActiviteStructureRegion a : activiteStructureRegions) {
                    activiteStructureRegionFacadeLocal.edit(a);
                }

                ActiviteRegion activiteRegion = activiteStructureRegions.get(0).getIdactiviteRegion();
                List<ActiviteStructureRegion> activiteStructureRegions = activiteStructureRegionFacadeLocal.find(activiteRegion);
                Double somme = 0d;
                for (ActiviteStructureRegion a : activiteStructureRegions) {
                    somme += a.getCout();
                }
                activiteRegion.setCoutglobal(somme);
                activiteRegionFacadeLocal.edit(activiteRegion);

                this.activiteStructureRegions = activiteStructureRegionFacadeLocal.find(structure, annee);
                findTotalRecette();
                JsfUtil.addSuccessMessage("Opération réussie");
            } else {
                JsfUtil.addErrorMessage("Le tableau est vide");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        try {
            findTotalRecette();
            activiteStructureRegions.clear();
            if (structure.getIdstructure() != null) {
                if (annee.getIdannee() != null) {
                    activiteStructureRegions = activiteStructureRegionFacadeLocal.find(structure, annee);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findTotalRecette() {
        try {
            if (annee.getIdannee() != null) {
                annee = anneeFacadeLocal.find(annee.getIdannee());
                if (structure.getIdstructure() != null) {
                    List<Recette> recettes = recetteFacadeLocal.find(structure, annee);
                    if (!recettes.isEmpty()) {
                        for (Recette r : recettes) {
                            totalRecette += r.getValeur().doubleValue();
                        }
                    } else {
                        totalRecette = 0d;
                    }
                } else {
                    totalRecette = 0d;
                }
            } else {
                totalRecette = 0d;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Double findTotal() {
        Double total = 0d;
        try {
            if (!activiteStructureRegions.isEmpty()) {
                for (ActiviteStructureRegion a : activiteStructureRegions) {
                    if (a.getCout() != null) {
                        total += a.getCout();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
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
            List<Structure> tmp = structureFacadeLocal.findByRegionNiveau(SessionMBean.getRegion(), 2);
            for (Structure s : tmp) {
                if (!s.getConsolide()) {
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

    public ActiviteStructureRegion getActiviteStructureRegion() {
        return activiteStructureRegion;
    }

    public void setActiviteStructureRegion(ActiviteStructureRegion activiteStructureRegion) {
        this.activiteStructureRegion = activiteStructureRegion;
    }

    public List<ActiviteStructureRegion> getActiviteStructureRegions() {
        return activiteStructureRegions;
    }

    public void setActiviteStructureRegions(List<ActiviteStructureRegion> activiteStructureRegions) {
        this.activiteStructureRegions = activiteStructureRegions;
    }

    public Double getTotalRecette() {
        return totalRecette;
    }

    public void setTotalRecette(Double totalRecette) {
        this.totalRecette = totalRecette;
    }

}
