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
import entities.Rubriquegouvernance;
import entities.GouvernanceRegion;
import entities.Structure;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessions.AnneeFacadeLocal;
import sessions.CommentaireRegionFacadeLocal;
import sessions.RubriquegouvernanceFacadeLocal;
import sessions.GouvernanceRegionFacadeLocal;
import sessions.StructureFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@SessionScoped
public class GouvernanceregionController {

    @EJB
    private GouvernanceRegionFacadeLocal gouvernanceRegionFacadeLocal;
    private List<GouvernanceRegion> gouvernanceRegions = new ArrayList<>();

    @EJB
    private RubriquegouvernanceFacadeLocal rubriquegouvernanceFacadeLocal;
    private List<Rubriquegouvernance> rubriquegouvernances = new ArrayList<>();

    @EJB
    private StructureFacadeLocal structureFacadeLocal;
    private Structure structure;
    private List<Structure> structures = new ArrayList<>();

    @EJB
    private AnneeFacadeLocal anneeFacadeLocal;
    private Annee annee;
    private List<Annee> annees = new ArrayList<>();

    @EJB
    private CommentaireRegionFacadeLocal commentaireRegionFacadeLocal;
    private CommentaireRegion commentaireRegion = new CommentaireRegion();

    private boolean detail = true;

    /**
     * Creates a new instance of SousaxeController
     */
    public GouvernanceregionController() {
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
            gouvernanceRegions.clear();

            if (structure != null) {
                if (!this.getRubriquegouvernances().isEmpty()) {
                    for (Rubriquegouvernance m : this.getRubriquegouvernances()) {
                        List<GouvernanceRegion> temp = gouvernanceRegionFacadeLocal.find(structure, m, SessionMBean.getRegion());
                        if (temp.isEmpty()) {
                            GouvernanceRegion gouvernanceRegion = new GouvernanceRegion();
                            gouvernanceRegion.setIdrubriquegouvernance(m);
                            gouvernanceRegion.setIdstructure(structure);
                            gouvernanceRegion.setIdregion(SessionMBean.getRegion());
                            gouvernanceRegions.add(gouvernanceRegion);
                        } else {
                            gouvernanceRegions.add(temp.get(0));
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

            List<CommentaireRegion> commentaireRegions = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 16);
            if (!commentaireRegions.isEmpty()) {
                commentaireRegion = commentaireRegions.get(0);
                return;
            }
            commentaireRegion = new CommentaireRegion();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            if (!gouvernanceRegions.isEmpty()) {
                for (GouvernanceRegion m : gouvernanceRegions) {
                    if (m.getIdgouvernanceRegion() == null) {
                        m.setIdgouvernanceRegion(gouvernanceRegionFacadeLocal.nextId());
                        m.setIdregion(SessionMBean.getRegion());
                        gouvernanceRegionFacadeLocal.create(m);
                    } else {
                        gouvernanceRegionFacadeLocal.edit(m);
                    }
                }

                List<CommentaireRegion> commentaireRegions = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 16);
                if (commentaireRegions.isEmpty()) {
                    commentaireRegion.setIdcommentaireRegion(commentaireRegionFacadeLocal.nextId());
                    commentaireRegion.setIdregion(SessionMBean.getRegion());
                    commentaireRegion.setNumerotab(16);
                    commentaireRegionFacadeLocal.create(commentaireRegion);
                } else {
                    commentaireRegionFacadeLocal.edit(commentaireRegion);
                }

                JsfUtil.addSuccessMessage("Opération réussie");
            } else {
                JsfUtil.addErrorMessage("Le Regionleau est vide");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String findRubriquegouvernance(Structure structure, Rubriquegouvernance rubriquegouvernance) {
        String resultat = "";
        try {

            if (structure != null) {
                if (rubriquegouvernance != null) {

                    List<GouvernanceRegion> temps = gouvernanceRegionFacadeLocal.find(structure, rubriquegouvernance, SessionMBean.getRegion());
                    if (!temps.isEmpty()) {
                        resultat = "" + temps.get(0).getValeur();
                    } else {
                        resultat = "";
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultat;
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
            /*structures.clear();
            List<Structure> tmp = structureFacadeLocal.findByRegionNiveau(SessionMBean.getRegion(), 2);
            for (Structure s : tmp) {
                if (!s.getConsolide()) {
                    structures.add(s);
                }
            }*/
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

    public List<Rubriquegouvernance> getRubriquegouvernances() {
        rubriquegouvernances = rubriquegouvernanceFacadeLocal.findAllRange();
        return rubriquegouvernances;
    }

    public void setRubriquegouvernances(List<Rubriquegouvernance> rubriquegouvernances) {
        this.rubriquegouvernances = rubriquegouvernances;
    }

    public List<GouvernanceRegion> getGouvernanceRegions() {
        return gouvernanceRegions;
    }

    public void setGouvernanceRegions(List<GouvernanceRegion> gouvernanceRegions) {
        this.gouvernanceRegions = gouvernanceRegions;
    }

    public CommentaireRegion getCommentaireRegion() {
        return commentaireRegion;
    }

    public void setCommentaireRegion(CommentaireRegion commentaireRegion) {
        this.commentaireRegion = commentaireRegion;
    }

}
