/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.CommentaireRegion;
import entities.Etatinfrastructure;
import entities.Infrastructure;
import entities.Structure;
import entities.TypeinfraTypestruc;
import entities.Typestructure;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessions.CommentaireRegionFacadeLocal;
import sessions.EtatinfrastructureFacadeLocal;
import sessions.InfrastructureFacadeLocal;
import sessions.StructureFacadeLocal;
import sessions.TypeinfraTypestrucFacadeLocal;
import sessions.TypestructureFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@ViewScoped
public class EtatlieuxinfrastructureController implements Serializable {
    
    @EJB
    private TypestructureFacadeLocal typestructureFacadeLocal;
    private Typestructure typestructure;
    private List<Typestructure> typestructures = new ArrayList<>();
    
    @EJB
    private StructureFacadeLocal structureFacadeLocal;
    private Structure structure;
    private List<Structure> structures = new ArrayList<>();
    
    @EJB
    private TypeinfraTypestrucFacadeLocal typeinfraTypestrucFacadeLocal;
    private TypeinfraTypestruc typeinfraTypestruc;
    private List<TypeinfraTypestruc> typeinfraTypestrucs = new ArrayList<>();
    
    @EJB
    private InfrastructureFacadeLocal infrastructureFacadeLocal;
    private List<Infrastructure> infrastructures = new ArrayList<>();
    
    @EJB
    private EtatinfrastructureFacadeLocal etatinfrastructureFacadeLocal;
    private List<Etatinfrastructure> etatinfrastructures = new ArrayList<>();
    
    @EJB
    private CommentaireRegionFacadeLocal commentairetabFacadeLocal;
    private CommentaireRegion commentairetab = new CommentaireRegion();
    
    private boolean detail = true;
    
    private String mode = "";

    /**
     * Creates a new instance of SousaxeController
     */
    public EtatlieuxinfrastructureController() {
    }
    
    @PostConstruct
    private void init() {
        typestructure = new Typestructure();
        structure = new Structure();
        typeinfraTypestruc = new TypeinfraTypestruc();
        try {
            this.updateCommentaire();
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
        mode = "Create";
    }
    
    public void uptadeTable() {
        try {
            infrastructures.clear();
            
            if (structure != null) {
                if (!this.getTypeinfraTypestrucs().isEmpty()) {
                    
                    if (infrastructureFacadeLocal.find(structure).isEmpty()) {
                        for (TypeinfraTypestruc t : this.typeinfraTypestrucs) {
                            Infrastructure infrastructure = new Infrastructure();
                            infrastructure.setIdstructure(structure);
                            infrastructure.setIdtypeinfraTypestruc(t);
                            infrastructures.add(infrastructure);
                        }
                    } else {
                        for (TypeinfraTypestruc t : this.typeinfraTypestrucs) {
                            List<Infrastructure> temp = infrastructureFacadeLocal.find(structure, t);
                            if (temp.isEmpty()) {
                                Infrastructure infrastructure = new Infrastructure();
                                infrastructure.setIdstructure(structure);
                                infrastructure.setIdtypeinfraTypestruc(t);
                                infrastructures.add(infrastructure);
                            } else {
                                infrastructures.add(temp.get(0));
                            }
                        }
                    }
                } else {
                    System.err.println("aucun profil");
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
            List<CommentaireRegion> commentairetabs = commentairetabFacadeLocal.find(SessionMBean.getRegion(), 10);
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
            List<CommentaireRegion> commentaireRegions = commentairetabFacadeLocal.find(SessionMBean.getRegion(), 10);
            if (commentaireRegions.isEmpty()) {
                commentairetab.setIdcommentaireRegion(commentairetabFacadeLocal.nextId());
                commentairetab.setIdregion(SessionMBean.getRegion());
                commentairetab.setNumerotab(10);
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
            if (!infrastructures.isEmpty()) {
                for (Infrastructure i : infrastructures) {
                    if (i.getIdinfrastructure() == null) {
                        i.setIdinfrastructure(infrastructureFacadeLocal.nextId());
                        i.setNumero(0);
                        infrastructureFacadeLocal.create(i);
                    } else {
                        infrastructureFacadeLocal.edit(i);
                    }
                }
                
                List<CommentaireRegion> commentairetabs = commentairetabFacadeLocal.find(SessionMBean.getRegion(), 10);
                if (commentairetabs.isEmpty()) {
                    commentairetab.setIdcommentaireRegion(commentairetabFacadeLocal.nextId());
                    commentairetab.setIdregion(SessionMBean.getRegion());
                    commentairetab.setNumerotab(10);
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
    
    public void update() {
        try {
            if (typestructure.getIdtypestructure() != null) {
                typestructure = typestructureFacadeLocal.find(typestructure.getIdtypestructure());
                structures = structureFacadeLocal.find(SessionMBean.getRegion(), 2, typestructure);
                typeinfraTypestrucs = typeinfraTypestrucFacadeLocal.findByTypestructure(typestructure);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Infrastructure findInfrastructure(Structure structure, TypeinfraTypestruc typeinfraTypestruc) {
        Infrastructure infrastructure = null;
        try {
            if (typestructure.getIdtypestructure() != null) {
                if (structure != null) {
                    if (typeinfraTypestruc != null) {
                        
                        List<Infrastructure> temp = infrastructureFacadeLocal.find(structure, typeinfraTypestruc);
                        if (!temp.isEmpty()) {
                            infrastructure = temp.get(0);
                        } else {
                            infrastructure = new Infrastructure();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return infrastructure;
    }
    
    public void deleteRow(Structure structure) {
        try {
            List<Infrastructure> infrastructures = infrastructureFacadeLocal.find(structure);
            for (Infrastructure i : infrastructures) {
                infrastructureFacadeLocal.remove(i);
            }
            JsfUtil.addSuccessMessage("operation supprimé");
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
        typestructures = typestructureFacadeLocal.findByRegional();
        return typestructures;
    }
    
    public void setTypestructures(List<Typestructure> typestructures) {
        this.typestructures = typestructures;
    }
    
    public TypeinfraTypestruc getTypeinfraTypestruc() {
        return typeinfraTypestruc;
    }
    
    public void setTypeinfraTypestruc(TypeinfraTypestruc typeinfraTypestruc) {
        this.typeinfraTypestruc = typeinfraTypestruc;
    }
    
    public List<TypeinfraTypestruc> getTypeinfraTypestrucs() {
        return typeinfraTypestrucs;
    }
    
    public void setTypeinfraTypestrucs(List<TypeinfraTypestruc> typeinfraTypestrucs) {
        this.typeinfraTypestrucs = typeinfraTypestrucs;
    }
    
    public List<Etatinfrastructure> getEtatinfrastructures() {
        etatinfrastructures = etatinfrastructureFacadeLocal.findAll();
        return etatinfrastructures;
    }
    
    public void setEtatinfrastructures(List<Etatinfrastructure> etatinfrastructures) {
        this.etatinfrastructures = etatinfrastructures;
    }
    
    public List<Infrastructure> getInfrastructures() {
        return infrastructures;
    }
    
    public void setInfrastructures(List<Infrastructure> infrastructures) {
        this.infrastructures = infrastructures;
    }
    
    public CommentaireRegion getCommentairetab() {
        return commentairetab;
    }
    
    public void setCommentairetab(CommentaireRegion commentairetab) {
        this.commentairetab = commentairetab;
    }
    
}
