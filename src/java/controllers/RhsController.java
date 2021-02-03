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
import entities.Profilpersonnel;
import entities.Rhs;
import entities.Structure;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import sessions.MouchardFacadeLocal;
import sessions.AnneeFacadeLocal;
import sessions.CommentaireRegionFacadeLocal;
import sessions.ProfilpersonnelFacadeLocal;
import sessions.RhsFacadeLocal;
import sessions.StructureFacadeLocal;

/**
 *
 * @author kenne gervais
 */
@ManagedBean
@SessionScoped
public class RhsController implements Serializable {

    /**
     * Creates a new instance of AnneeController
     */
    @EJB
    private RhsFacadeLocal rhsFacadeLocal;
    private List<Rhs> rhses = new ArrayList<>();

    @EJB
    private AnneeFacadeLocal anneeFacadeLocal;
    private Annee annee = new Annee();
    private List<Annee> annees = new ArrayList<>();

    @EJB
    private StructureFacadeLocal structureFacadeLocal;
    private Structure structure = new Structure();
    private List<Structure> structures = new ArrayList<>();
    private List<Structure> structures1 = new ArrayList<>();

    @EJB
    private ProfilpersonnelFacadeLocal profilpersonnelFacadeLocal;
    private List<Profilpersonnel> profilpersonnels = new ArrayList<>();

    @EJB
    private CommentaireRegionFacadeLocal commentairetabFacadeLocal;
    private CommentaireRegion commentairetab = new CommentaireRegion();

    @EJB
    private MouchardFacadeLocal mouchardFacadeLocal;

    private Boolean detail = true;

    public RhsController() {

    }

    @PostConstruct
    private void init() {
        annee = new Annee();
        this.updateCommentaire();
    }

    public void activeButton() {
        detail = false;
    }

    public void deactiveButton() {
        detail = true;
    }

    public void consolidate(Structure structure) {
        try {

            for (Profilpersonnel p : profilpersonnels) {

                List<Rhs> rhes = rhsFacadeLocal.find(structure.getIddistrict(), p, annee);
                Double resultat = 0d;

                for (Rhs r : rhes) {
                    resultat += r.getValeur().doubleValue();
                }

                List<Rhs> temp = rhsFacadeLocal.find(structure, p, annee);
                if (temp.isEmpty()) {
                    Rhs rh = new Rhs();
                    rh.setIdrhs(rhsFacadeLocal.nextId());
                    rh.setIdannee(annee);
                    rh.setIdprofilpersonnel(p);
                    rh.setIdstructure(structure);
                    rh.setPrcds(true);
                    rh.setValeur(resultat.intValue());
                    rhsFacadeLocal.create(rh);
                } else {
                    temp.get(0).setValeur(resultat.intValue());
                    rhsFacadeLocal.edit(temp.get(0));
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
                List<Rhs> rhses = rhsFacadeLocal.find(structure, annee);
                for (Rhs r : rhses) {
                    rhsFacadeLocal.remove(r);
                }
                JsfUtil.addSuccessMessage("Opération réussie");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initDetail(Structure structure) {
        try {

            if (annee.getIdannee() != null) {
                structures1 = structureFacadeLocal.findByDistrict(structure.getIddistrict().getIddistrict());
                RequestContext.getCurrentInstance().execute("PF('MedicamentDetailDialog').show()");
            } else {
                JsfUtil.addErrorMessage("Veuillez selectionner une année");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Rhs findByStructure(Structure structure, Profilpersonnel profilpersonnel) {
        try {
            if (annee.getIdannee() != null) {
                List<Rhs> rhses = rhsFacadeLocal.find(structure, profilpersonnel, annee);
                if (!rhses.isEmpty()) {
                    return rhses.get(0);
                } else {
                    return new Rhs();
                }
            } else {
                return new Rhs();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Rhs();
        }
    }

    public void uptadeTable() {
        try {
            rhses.clear();
            if (annee.getIdannee() != null) {
                annee = anneeFacadeLocal.find(annee.getIdannee());
                if (structure != null) {
                    if (!this.getProfilpersonnels().isEmpty()) {

                        if (rhsFacadeLocal.find(structure, annee).isEmpty()) {
                            for (Profilpersonnel p : this.getProfilpersonnels()) {
                                Rhs rhs = new Rhs();
                                rhs.setIdannee(annee);
                                rhs.setIdprofilpersonnel(p);
                                rhs.setIdstructure(structure);
                                rhs.setValeur(0);
                                rhses.add(rhs);
                            }
                        } else {
                            for (Profilpersonnel p : this.getProfilpersonnels()) {
                                List<Rhs> temp = rhsFacadeLocal.find(structure, p, annee);
                                if (temp.isEmpty()) {
                                    Rhs rhs = new Rhs();
                                    rhs.setIdannee(annee);
                                    rhs.setIdprofilpersonnel(p);
                                    rhs.setIdstructure(structure);
                                    rhs.setValeur(0);
                                    rhses.add(rhs);
                                } else {
                                    rhses.add(temp.get(0));
                                }
                            }
                        }
                    } else {
                        System.err.println("aucun profil");
                    }
                } else {
                    JsfUtil.addErrorMessage("Veuillez selectionnner une ligne");
                }

                List<CommentaireRegion> commentairetabs = commentairetabFacadeLocal.find(SessionMBean.getRegion(), 8);
                if (!commentairetabs.isEmpty()) {
                    commentairetab = commentairetabs.get(0);
                    return;
                }
                commentairetab = new CommentaireRegion();

            } else {
                JsfUtil.addErrorMessage("Veillez selectionner une année");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCommentaire() {
        try {
            List<CommentaireRegion> commentaireRegions = commentairetabFacadeLocal.find(SessionMBean.getRegion(), 8);
            if (!commentaireRegions.isEmpty()) {
                commentairetab = commentaireRegions.get(0);
                return;
            }
            commentairetab = new CommentaireRegion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validComment() {
        try {
            List<CommentaireRegion> commentaireRegions = commentairetabFacadeLocal.find(SessionMBean.getRegion(), 8);
            if (commentaireRegions.isEmpty()) {
                commentairetab.setIdcommentaireRegion(commentairetabFacadeLocal.nextId());
                commentairetab.setIdregion(SessionMBean.getRegion());
                commentairetab.setNumerotab(8);
                commentairetabFacadeLocal.create(commentairetab);
            } else {
                commentairetabFacadeLocal.edit(commentairetab);
            }
            JsfUtil.addSuccessMessage("Opération réussie");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            if (!rhses.isEmpty()) {
                for (Rhs r : rhses) {
                    if (r.getIdrhs() == null) {
                        r.setIdrhs(rhsFacadeLocal.nextId());
                        rhsFacadeLocal.create(r);
                    } else {
                        rhsFacadeLocal.edit(r);
                    }
                }

                List<CommentaireRegion> commentaireRegions = commentairetabFacadeLocal.find(SessionMBean.getRegion(), 8);
                if (commentaireRegions.isEmpty()) {
                    commentairetab.setIdcommentaireRegion(commentairetabFacadeLocal.nextId());
                    commentairetab.setIdregion(SessionMBean.getRegion());
                    commentairetab.setNumerotab(8);
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

    public String findRhs(Structure structure, Profilpersonnel profilpersonnel) {
        String resultat = "";
        try {
            if (annee.getIdannee() != null) {
                annee = anneeFacadeLocal.find(annee.getIdannee());

                if (structure != null) {
                    if (profilpersonnel != null) {

                        List<Rhs> rhses = rhsFacadeLocal.find(structure, profilpersonnel, annee);
                        if (!rhses.isEmpty()) {
                            resultat = "" + rhses.get(0).getValeur();
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

    public MouchardFacadeLocal getMouchardFacadeLocal() {
        return mouchardFacadeLocal;
    }

    public void setMouchardFacadeLocal(MouchardFacadeLocal mouchardFacadeLocal) {
        this.mouchardFacadeLocal = mouchardFacadeLocal;
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

    public Boolean getDetail() {
        return detail;
    }

    public void setDetail(Boolean detail) {
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
            if (SessionMBean.getSession() != null) {
                structures = structureFacadeLocal.findByRegionNiveau(SessionMBean.getRegion(), 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return structures;
    }

    public void setStructures(List<Structure> structures) {
        this.structures = structures;
    }

    public List<Profilpersonnel> getProfilpersonnels() {
        profilpersonnels = profilpersonnelFacadeLocal.findAllRange();
        return profilpersonnels;
    }

    public void setProfilpersonnels(List<Profilpersonnel> profilpersonnels) {
        this.profilpersonnels = profilpersonnels;
    }

    public List<Rhs> getRhses() {
        return rhses;
    }

    public void setRhses(List<Rhs> rhses) {
        this.rhses = rhses;
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
