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
import entities.Medicamenttraceur;
import entities.MedicamenttraceurStructure;
import entities.Sourceapprovisionnement;
import entities.Structure;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessions.AnneeFacadeLocal;
import sessions.CommentaireRegionFacadeLocal;
import sessions.MedicamenttraceurFacadeLocal;
import sessions.MedicamenttraceurStructureFacadeLocal;
import sessions.SourceapprovisionnementFacadeLocal;
import sessions.StructureFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@SessionScoped
public class MedicamenttraceurStructureController {
    
    @EJB
    private MedicamenttraceurStructureFacadeLocal medicamenttraceurStructureFacadeLocal;
    private List<MedicamenttraceurStructure> medicamenttraceurStructures = new ArrayList<>();
    
    @EJB
    private MedicamenttraceurFacadeLocal medicamenttraceurFacadeLocal;
    private List<Medicamenttraceur> medicamenttraceurs = new ArrayList<>();
    
    @EJB
    private StructureFacadeLocal structureFacadeLocal;
    private Structure structure = new Structure();
    private List<Structure> structures = new ArrayList<>();
    
    @EJB
    private AnneeFacadeLocal anneeFacadeLocal;
    private Annee annee = new Annee();
    private List<Annee> annees = new ArrayList<>();
    
    @EJB
    private SourceapprovisionnementFacadeLocal sourceapprovisionnementFacadeLocal;
    private List<Sourceapprovisionnement> sourceapprovisionnements = new ArrayList<>();
    
    @EJB
    private CommentaireRegionFacadeLocal commentairetabFacadeLocal;
    private CommentaireRegion commentairetab = new CommentaireRegion();
    
    private boolean detail = true;

    /**
     * Creates a new instance of SousaxeController
     */
    public MedicamenttraceurStructureController() {
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
            medicamenttraceurStructures.clear();
            if (annee.getIdannee() != null) {
                annee = anneeFacadeLocal.find(annee.getIdannee());
                if (structure != null) {
                    if (!this.getMedicamenttraceurs().isEmpty()) {
                        
                        if (medicamenttraceurStructureFacadeLocal.find(structure, annee).isEmpty()) {
                            for (Medicamenttraceur m : this.getMedicamenttraceurs()) {
                                MedicamenttraceurStructure medicamenttraceurStructure = new MedicamenttraceurStructure();
                                medicamenttraceurStructure.setIdannee(annee);
                                medicamenttraceurStructure.setIdmedicamenttraceur(m);
                                medicamenttraceurStructure.setIdstructure(structure);
                                medicamenttraceurStructures.add(medicamenttraceurStructure);
                            }
                        } else {
                            for (Medicamenttraceur m : this.getMedicamenttraceurs()) {
                                List<MedicamenttraceurStructure> temp = medicamenttraceurStructureFacadeLocal.find(structure, m, annee);
                                if (temp.isEmpty()) {
                                    MedicamenttraceurStructure medicamenttraceurStructure = new MedicamenttraceurStructure();
                                    medicamenttraceurStructure.setIdannee(annee);
                                    medicamenttraceurStructure.setIdmedicamenttraceur(m);
                                    medicamenttraceurStructure.setIdstructure(structure);
                                    medicamenttraceurStructures.add(medicamenttraceurStructure);
                                } else {
                                    medicamenttraceurStructures.add(temp.get(0));
                                }
                            }
                        }
                    } else {
                        System.err.println("aucun medicament");
                    }
                } else {
                    JsfUtil.addErrorMessage("Veuillez selectionnner une ligne");
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
            List<CommentaireRegion> commentairetabs = commentairetabFacadeLocal.find(SessionMBean.getRegion(), 14);
            if (!commentairetabs.isEmpty()) {
                commentairetab = commentairetabs.get(0);
                return;
            }
            commentairetab = new CommentaireRegion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void create() {
        try {
            if (!medicamenttraceurStructures.isEmpty()) {
                for (MedicamenttraceurStructure m : medicamenttraceurStructures) {
                    if (m.getIdmedicamenttraceurStructure() == null) {
                        m.setIdmedicamenttraceurStructure(medicamenttraceurStructureFacadeLocal.nextId());
                        medicamenttraceurStructureFacadeLocal.create(m);
                    } else {
                        medicamenttraceurStructureFacadeLocal.edit(m);
                    }
                }
                
                List<CommentaireRegion> commentairetabs = commentairetabFacadeLocal.find(SessionMBean.getRegion(), 14);
                if (commentairetabs.isEmpty()) {
                    commentairetab.setIdcommentaireRegion(commentairetabFacadeLocal.nextId());
                    commentairetab.setIdregion(SessionMBean.getRegion());
                    commentairetab.setNumerotab(14);
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
    
    public String findMedicamenttraceur(Structure structure, Medicamenttraceur medicamenttraceur) {
        String resultat = "";
        try {
            if (annee.getIdannee() != null) {
                annee = anneeFacadeLocal.find(annee.getIdannee());
                
                if (structure != null) {
                    if (medicamenttraceur != null) {
                        
                        List<MedicamenttraceurStructure> temps = medicamenttraceurStructureFacadeLocal.find(structure, medicamenttraceur, annee);
                        if (!temps.isEmpty()) {
                            resultat = "" + temps.get(0).getNbrejrrupturestock();
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
    
    public void consolidate(Structure structure) {
        try {
            for (Medicamenttraceur m : medicamenttraceurs) {
                
                List<MedicamenttraceurStructure> mes = medicamenttraceurStructureFacadeLocal.find(structure.getIddistrict(), m, annee);
                Double resultat = 0d;
                
                for (MedicamenttraceurStructure temp : mes) {
                    resultat += temp.getNbrejrrupturestock().doubleValue();
                }
                
                List<MedicamenttraceurStructure> temp = medicamenttraceurStructureFacadeLocal.find(structure, m, annee);
                if (temp.isEmpty()) {
                    MedicamenttraceurStructure med = new MedicamenttraceurStructure();
                    med.setIdmedicamenttraceurStructure(medicamenttraceurStructureFacadeLocal.nextId());
                    med.setIdannee(annee);
                    med.setIdstructure(structure);
                    med.setIdmedicamenttraceur(m);
                    med.setIdsourceapprovisionnement(new Sourceapprovisionnement(1));
                    med.setPrcds(true);
                    med.setNbrejrrupturestock(resultat.intValue());
                    medicamenttraceurStructureFacadeLocal.create(med);
                } else {
                    temp.get(0).setNbrejrrupturestock(resultat.intValue());
                    medicamenttraceurStructureFacadeLocal.edit(temp.get(0));
                }
            }
            JsfUtil.addSuccessMessage("Opération réussie");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteRow(Structure structure) {
        try {
            if (annee.getIdannee() != null) {
                List<MedicamenttraceurStructure> mes = medicamenttraceurStructureFacadeLocal.find(structure, annee);
                for (MedicamenttraceurStructure m : mes) {
                    medicamenttraceurStructureFacadeLocal.remove(m);
                }
                JsfUtil.addSuccessMessage("Opération réussie");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("echec");
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
            //structures = structureFacadeLocal.findByRegionNiveau(SessionMBean.getRegion(), 2);
            structures.clear();
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
    
    public List<Medicamenttraceur> getMedicamenttraceurs() {
        medicamenttraceurs = medicamenttraceurFacadeLocal.findAllRangeRegion();
        return medicamenttraceurs;
    }
    
    public void setMedicamenttraceurs(List<Medicamenttraceur> medicamenttraceurs) {
        this.medicamenttraceurs = medicamenttraceurs;
    }
    
    public List<Sourceapprovisionnement> getSourceapprovisionnements() {
        sourceapprovisionnements = sourceapprovisionnementFacadeLocal.findAllRange();
        return sourceapprovisionnements;
    }
    
    public void setSourceapprovisionnements(List<Sourceapprovisionnement> sourceapprovisionnements) {
        this.sourceapprovisionnements = sourceapprovisionnements;
    }
    
    public List<MedicamenttraceurStructure> getMedicamenttraceurStructures() {
        return medicamenttraceurStructures;
    }
    
    public void setMedicamenttraceurStructures(List<MedicamenttraceurStructure> medicamenttraceurStructures) {
        this.medicamenttraceurStructures = medicamenttraceurStructures;
    }
    
    public CommentaireRegion getCommentairetab() {
        return commentairetab;
    }
    
    public void setCommentairetab(CommentaireRegion commentairetab) {
        this.commentairetab = commentairetab;
    }
    
}
