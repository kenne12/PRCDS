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
import entities.Depense;
import entities.Naturedepense;
import entities.Recette;
import entities.Sourcefinancement;
import entities.Structure;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import sessions.AnneeFacadeLocal;
import sessions.CommentaireRegionFacadeLocal;
import sessions.DepenseFacadeLocal;
import sessions.NaturedepenseFacadeLocal;
import sessions.StructureFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@ViewScoped
public class DepenseController {
    
    @EJB
    private DepenseFacadeLocal depenseFacadeLocal;
    private List<Depense> depenses = new ArrayList<>();
    
    @EJB
    private NaturedepenseFacadeLocal naturedepenseFacadeLocal;
    private Naturedepense naturedepense = new Naturedepense();
    private List<Naturedepense> naturedepenses = new ArrayList<>();
    
    @EJB
    private StructureFacadeLocal structureFacadeLocal;
    private Structure structure = new Structure();
    private List<Structure> structures = new ArrayList<>();
    private List<Structure> structures1 = new ArrayList<>();
    
    @EJB
    private AnneeFacadeLocal anneeFacadeLocal;
    private Annee annee = new Annee();
    private List<Annee> annees = new ArrayList<>();
    
    @EJB
    private CommentaireRegionFacadeLocal commentairetabFacadeLocal;
    private CommentaireRegion commentairetab = new CommentaireRegion();
    
    private boolean detail = true;

    /**
     * Creates a new instance of SousaxeController
     */
    public DepenseController() {
    }
    
    @PostConstruct
    private void init() {
        structure = new Structure();
        structure.setConsolide(false);
        annee = new Annee();
        naturedepense = new Naturedepense();
        this.updateCommentaire();
    }
    
    public void activeButton() {
        detail = false;
    }
    
    public void deactiveButton() {
        detail = true;
    }
    
    public void initStructure() {
        try {
            if (structure.getIdstructure() != null) {
                structure = structureFacadeLocal.find(structure.getIdstructure());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void initNature(Naturedepense naturedepense) {
        try {
            this.naturedepense = naturedepense;
            
            structures1 = structureFacadeLocal.findByDistrict(structure.getIddistrict().getIddistrict());
            
            RequestContext.getCurrentInstance().execute("PF('MedicamentDetailDialog').show()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteRow(Naturedepense naturedepense) {
        try {
            if (structure.getIdstructure() != null) {
                for (Annee a : annees) {
                    List<Depense> depenses = depenseFacadeLocal.find(structure, naturedepense, a);
                    for (Depense d : depenses) {
                        depenseFacadeLocal.remove(d);
                    }
                }
                JsfUtil.addSuccessMessage("Opération réussie");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteAll() {
        try {
            if (structure.getIdstructure() != null) {
                List<Depense> depenses = depenseFacadeLocal.find(structure);
                for (Depense d : depenses) {
                    depenseFacadeLocal.remove(d);
                }
                JsfUtil.addSuccessMessage("Opération réussie");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void consolidate() {
        try {
            for (Naturedepense n : naturedepenses) {
                
                for (Annee a : annees) {
                    Double resultat = 0d;
                    List<Depense> depenses = depenseFacadeLocal.find(structure.getIddistrict(), n, a);
                    for (Depense d : depenses) {
                        resultat += d.getValeur();
                    }
                    
                    List<Depense> temp = depenseFacadeLocal.find(structure, n, a);
                    if (temp.isEmpty()) {
                        Depense d1 = new Depense();
                        d1.setIddepense(depenseFacadeLocal.nextId());
                        d1.setIdannee(a);
                        d1.setIdnaturedepense(n);
                        d1.setIdstructure(structure);
                        d1.setPrcds(true);
                        d1.setValeur(resultat);
                        depenseFacadeLocal.create(d1);
                    } else {
                        temp.get(0).setValeur(resultat);
                        depenseFacadeLocal.edit(temp.get(0));
                    }
                }
            }
            JsfUtil.addSuccessMessage("Opération réussie");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Depense findByStructure(Structure structure, Annee annee) {
        try {
            if (naturedepense != null) {
                List<Depense> depenses = depenseFacadeLocal.find(structure, naturedepense, annee);
                if (!depenses.isEmpty()) {
                    return depenses.get(0);
                } else {
                    return new Depense();
                }
            } else {
                return new Depense();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return new Depense();
        }
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
    
    public void loadTable() {
        try {
            depenses.clear();
            if (structure != null) {
                if (!this.getNaturedepenses().isEmpty()) {
                    for (Naturedepense n : this.getNaturedepenses()) {
                        Depense depense = new Depense();
                        depense.setIdnaturedepense(n);
                        depense.setIdstructure(structure);
                        depenses.add(depense);
                    }
                } else {
                    System.err.println("Aucune nature de dépenses");
                }
            } else {
                JsfUtil.addErrorMessage("Veuillez selectionnner une structure");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void uptadeTable() {
        try {
            depenses.clear();
            if (annee.getIdannee() != null) {
                
                if (structure != null) {
                    if (!this.getNaturedepenses().isEmpty()) {
                        
                        if (depenseFacadeLocal.find(structure, annee).isEmpty()) {
                            for (Naturedepense n : this.getNaturedepenses()) {
                                Depense depense = new Depense();
                                depense.setIdannee(annee);
                                depense.setIdnaturedepense(n);
                                depense.setIdstructure(structure);
                                depenses.add(depense);
                            }
                        } else {
                            for (Naturedepense n : this.getNaturedepenses()) {
                                List<Depense> temp = depenseFacadeLocal.find(structure, n, annee);
                                if (temp.isEmpty()) {
                                    Depense depense = new Depense();
                                    depense.setIdannee(annee);
                                    depense.setIdnaturedepense(n);
                                    depense.setIdstructure(structure);
                                    depenses.add(depense);
                                } else {
                                    depenses.add(temp.get(0));
                                }
                            }
                        }
                    } else {
                        System.err.println("aucun profil");
                    }
                } else {
                    JsfUtil.addErrorMessage("Veuillez selectionnner une structure");
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
            List<CommentaireRegion> commentairetabs = commentairetabFacadeLocal.find(SessionMBean.getRegion(), 13);
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
            List<CommentaireRegion> commentaireRegions = commentairetabFacadeLocal.find(SessionMBean.getRegion(), 13);
            if (commentaireRegions.isEmpty()) {
                commentairetab.setIdcommentaireRegion(commentairetabFacadeLocal.nextId());
                commentairetab.setIdregion(SessionMBean.getRegion());
                commentairetab.setNumerotab(13);
                commentairetabFacadeLocal.create(commentairetab);
            } else {
                commentairetabFacadeLocal.edit(commentairetab);
            }
            JsfUtil.addSuccessMessage("Opération réussie");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void create() {
        try {
            if (!depenses.isEmpty()) {
                for (Depense d : depenses) {
                    if (d.getIddepense() == null) {
                        d.setIddepense(depenseFacadeLocal.nextId());
                        depenseFacadeLocal.edit(d);
                    } else {
                        depenseFacadeLocal.edit(d);
                    }
                }
                
                List<CommentaireRegion> commentairetabs = commentairetabFacadeLocal.find(SessionMBean.getRegion(), 13);
                if (commentairetabs.isEmpty()) {
                    commentairetab.setIdcommentaireRegion(commentairetabFacadeLocal.nextId());
                    commentairetab.setIdregion(SessionMBean.getRegion());
                    commentairetab.setNumerotab(13);
                    commentairetabFacadeLocal.create(commentairetab);
                } else {
                    commentairetabFacadeLocal.edit(commentairetab);
                }
                
                JsfUtil.addSuccessMessage("Opération réussie");
            } else {
                JsfUtil.addErrorMessage("Le tableau est vide");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Depense findDepense(Naturedepense naturedepense, Annee annee) {
        Depense depense = null;
        try {
            if (annee.getIdannee() != null) {
                if (structure != null) {
                    if (naturedepense != null) {
                        
                        List<Depense> temps = depenseFacadeLocal.find(structure, naturedepense, annee);
                        if (!temps.isEmpty()) {
                            depense = temps.get(0);
                        } else {
                            depense = new Depense();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return depense;
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
            structures = structureFacadeLocal.findByRegionNiveau(SessionMBean.getRegion(), 2);
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
            annees = anneeFacadeLocal.findByEtatDepense(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return annees;
    }
    
    public void setAnnees(List<Annee> annees) {
        this.annees = annees;
    }
    
    public List<Depense> getDepenses() {
        return depenses;
    }
    
    public void setDepenses(List<Depense> depenses) {
        this.depenses = depenses;
    }
    
    public List<Naturedepense> getNaturedepenses() {
        naturedepenses = naturedepenseFacadeLocal.findAll();
        return naturedepenses;
    }
    
    public void setNaturedepenses(List<Naturedepense> naturedepenses) {
        this.naturedepenses = naturedepenses;
    }
    
    public Naturedepense getNaturedepense() {
        return naturedepense;
    }
    
    public void setNaturedepense(Naturedepense naturedepense) {
        this.naturedepense = naturedepense;
    }
    
    public CommentaireRegion getCommentairetab() {
        return commentairetab;
    }
    
    public void setCommentairetab(CommentaireRegion commentairetab) {
        this.commentairetab = commentairetab;
    }
    
    public List<Structure> getStructures1() {
        return structures1;
    }
    
    public void setStructures1(List<Structure> structures1) {
        this.structures1 = structures1;
    }
    
}
