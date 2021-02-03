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
import entities.Recette;
import entities.Sourcefinancement;
import entities.Structure;
import entities.Typerecette;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import sessions.AnneeFacadeLocal;
import sessions.CommentaireRegionFacadeLocal;
import sessions.RecetteFacadeLocal;
import sessions.SourcefinancementFacadeLocal;
import sessions.StructureFacadeLocal;
import sessions.TyperecetteFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@SessionScoped
public class RecetteController {
    
    @EJB
    private RecetteFacadeLocal recetteFacadeLocal;
    private List<Recette> recettes = new ArrayList<>();
    
    @EJB
    private SourcefinancementFacadeLocal sourcefinancementFacadeLocal;
    private Sourcefinancement sourcefinancement = new Sourcefinancement();
    private List<Sourcefinancement> sourcefinancements = new ArrayList<>();
    
    @EJB
    private TyperecetteFacadeLocal typerecetteFacadeLocal;
    private List<Typerecette> typerecettes = new ArrayList<>();
    
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
    public RecetteController() {
    }
    
    @PostConstruct
    private void init() {
        structure = new Structure();
        structure.setConsolide(false);
        annee = new Annee();
        sourcefinancement = new Sourcefinancement();
        
        try {
            List<CommentaireRegion> commentairetabs = commentairetabFacadeLocal.find(SessionMBean.getRegion(), 12);
            if (!commentairetabs.isEmpty()) {
                commentairetab = commentairetabs.get(0);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void initSource(Sourcefinancement sourcefinancement) {
        try {
            this.sourcefinancement = sourcefinancement;
            
            structures1 = structureFacadeLocal.findByDistrict(structure.getIddistrict().getIddistrict());
            
            RequestContext.getCurrentInstance().execute("PF('MedicamentDetailDialog').show()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteRow(Sourcefinancement sourcefinancement) {
        try {
            if (structure.getIdstructure() != null) {
                for (Annee a : annees) {
                    List<Recette> recettes = recetteFacadeLocal.find(structure, sourcefinancement, a);
                    for (Recette r : recettes) {
                        recetteFacadeLocal.remove(r);
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
                List<Recette> recettes = recetteFacadeLocal.find(structure);
                for (Recette r : recettes) {
                    recetteFacadeLocal.remove(r);
                }
                JsfUtil.addSuccessMessage("Opération réussie");
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
    
    public void initStructure() {
        try {
            if (structure.getIdstructure() != null) {
                structure = structureFacadeLocal.find(structure.getIdstructure());
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    
    public void consolidate() {
        try {
            for (Sourcefinancement s : sourcefinancements) {
                
                for (Annee a : annees) {
                    Double resultat = 0d;
                    List<Recette> recettes = recetteFacadeLocal.find(structure.getIddistrict(), s, a);
                    for (Recette r : recettes) {
                        resultat += r.getValeur().doubleValue();
                    }
                    
                    List<Recette> temp = recetteFacadeLocal.find(structure, s, a);
                    if (temp.isEmpty()) {
                        Recette r1 = new Recette();
                        r1.setIdrecette(recetteFacadeLocal.nextId());
                        r1.setIdannee(a);
                        r1.setIdsourcefi(s);
                        r1.setIdstructure(structure);
                        r1.setPrcds(true);
                        r1.setValeur(BigInteger.valueOf(resultat.longValue()));
                        recetteFacadeLocal.create(r1);
                    } else {
                        temp.get(0).setValeur(BigInteger.valueOf(resultat.longValue()));
                        recetteFacadeLocal.edit(temp.get(0));
                    }
                    
                }
            }
            JsfUtil.addSuccessMessage("Opération réussie");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadTable() {
        try {
            annee = new Annee();
            recettes.clear();
            if (structure != null) {
                if (!this.getSourcefinancements().isEmpty()) {
                    for (Sourcefinancement s : this.getSourcefinancements()) {
                        Recette recette = new Recette();
                        recette.setIdsourcefi(s);
                        recette.setIdstructure(structure);
                        recettes.add(recette);
                    }
                } else {
                    System.err.println("Aucune source de financement");
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
            recettes.clear();
            if (annee.getIdannee() != null) {
                
                if (structure != null) {
                    if (!this.getSourcefinancements().isEmpty()) {
                        
                        if (recetteFacadeLocal.find(structure, annee).isEmpty()) {
                            for (Sourcefinancement s : this.getSourcefinancements()) {
                                Recette recette = new Recette();
                                recette.setIdannee(annee);
                                recette.setIdsourcefi(s);
                                recette.setIdstructure(structure);
                                recettes.add(recette);
                            }
                        } else {
                            for (Sourcefinancement s : this.getSourcefinancements()) {
                                List<Recette> temp = recetteFacadeLocal.find(structure, s, annee);
                                if (temp.isEmpty()) {
                                    Recette recette = new Recette();
                                    recette.setIdannee(annee);
                                    recette.setIdsourcefi(s);
                                    recette.setIdstructure(structure);
                                    recettes.add(recette);
                                } else {
                                    recettes.add(temp.get(0));
                                }
                            }
                        }
                    } else {
                        System.err.println("Aucune source de financement");
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
            List<CommentaireRegion> commentairetabs = commentairetabFacadeLocal.find(SessionMBean.getRegion(), 12);
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
            List<CommentaireRegion> commentaireRegions = commentairetabFacadeLocal.find(SessionMBean.getRegion(), 12);
            if (commentaireRegions.isEmpty()) {
                commentairetab.setIdcommentaireRegion(commentairetabFacadeLocal.nextId());
                commentairetab.setIdregion(SessionMBean.getRegion());
                commentairetab.setNumerotab(12);
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
            if (!recettes.isEmpty()) {
                for (Recette r : recettes) {
                    if (r.getIdrecette() == null) {
                        r.setIdrecette(recetteFacadeLocal.nextId());
                        recetteFacadeLocal.create(r);
                    } else {
                        recetteFacadeLocal.edit(r);
                    }
                }
                
                List<CommentaireRegion> commentairetabs = commentairetabFacadeLocal.find(SessionMBean.getRegion(), 12);
                if (commentairetabs.isEmpty()) {
                    commentairetab.setIdcommentaireRegion(commentairetabFacadeLocal.nextId());
                    commentairetab.setIdregion(SessionMBean.getRegion());
                    commentairetab.setNumerotab(12);
                    commentairetabFacadeLocal.create(commentairetab);
                } else {
                    commentairetabFacadeLocal.edit(commentairetab);
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
    
    public Recette findRecette(Sourcefinancement sourcefinancement, Annee annee) {
        Recette recette = null;
        try {
            if (annee.getIdannee() != null) {
                if (structure != null) {
                    if (sourcefinancement != null) {
                        
                        List<Recette> temps = recetteFacadeLocal.find(structure, sourcefinancement, annee);
                        if (!temps.isEmpty()) {
                            recette = temps.get(0);
                        } else {
                            recette = new Recette();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recette;
    }
    
    public Recette findByStructure(Structure structure, Annee annee) {
        try {
            if (sourcefinancement != null) {
                List<Recette> recettes = recetteFacadeLocal.find(structure, sourcefinancement, annee);
                if (!recettes.isEmpty()) {
                    return recettes.get(0);
                } else {
                    return new Recette();
                }
            } else {
                return new Recette();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Recette();
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
            annees = anneeFacadeLocal.findByEtatRecette(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return annees;
    }
    
    public void setAnnees(List<Annee> annees) {
        this.annees = annees;
    }
    
    public List<Recette> getRecettes() {
        return recettes;
    }
    
    public void setRecettes(List<Recette> recettes) {
        this.recettes = recettes;
    }
    
    public List<Sourcefinancement> getSourcefinancements() {
        sourcefinancements = sourcefinancementFacadeLocal.findAllRange();
        return sourcefinancements;
    }
    
    public void setSourcefinancements(List<Sourcefinancement> sourcefinancements) {
        this.sourcefinancements = sourcefinancements;
    }
    
    public List<Typerecette> getTyperecettes() {
        typerecettes = typerecetteFacadeLocal.findAllRange();
        return typerecettes;
    }
    
    public void setTyperecettes(List<Typerecette> typerecettes) {
        this.typerecettes = typerecettes;
    }
    
    public Sourcefinancement getSourcefinancement() {
        return sourcefinancement;
    }
    
    public void setSourcefinancement(Sourcefinancement sourcefinancement) {
        this.sourcefinancement = sourcefinancement;
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
