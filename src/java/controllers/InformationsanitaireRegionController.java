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
import entities.Rubriqueinfosanitaire;
import entities.InformationsanitaireRegion;
import entities.Informationsanitairedistrict;
import entities.Structure;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import sessions.AnneeFacadeLocal;
import sessions.CommentaireRegionFacadeLocal;
import sessions.RubriqueinfosanitaireFacadeLocal;
import sessions.InformationsanitaireRegionFacadeLocal;
import sessions.InformationsanitairedistrictFacadeLocal;
import sessions.StructureFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@SessionScoped
public class InformationsanitaireRegionController {

    @EJB
    private InformationsanitaireRegionFacadeLocal informationsanitaireRegionFacadeLocal;
    private List<InformationsanitaireRegion> informationsanitaireRegions = new ArrayList<>();

    @EJB
    private InformationsanitairedistrictFacadeLocal informationsanitairedistrictFacadeLocal;

    @EJB
    private RubriqueinfosanitaireFacadeLocal rubriqueinformationsanitaireFacadeLocal;
    private Rubriqueinfosanitaire rubriqueinfosanitaire = new Rubriqueinfosanitaire();
    private List<Rubriqueinfosanitaire> rubriqueinformationsanitaires = new ArrayList<>();

    @EJB
    private StructureFacadeLocal structureFacadeLocal;
    private Structure structure;
    private List<Structure> structures = new ArrayList<>();
    private List<Structure> structures1 = new ArrayList<>();

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
    public InformationsanitaireRegionController() {
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

    public void initInfosanitaire(Structure structure) {
        try {
            this.structure = structure;
            structures1 = structureFacadeLocal.findByDistrict(structure.getIddistrict().getIddistrict());
            RequestContext.getCurrentInstance().execute("PF('MedicamentDetailDialog').show()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uptadeTable() {
        try {
            informationsanitaireRegions.clear();

            if (structure != null) {
                if (!this.getRubriqueinfosanitaires().isEmpty()) {
                    for (Rubriqueinfosanitaire m : this.getRubriqueinfosanitaires()) {
                        List<InformationsanitaireRegion> temp = informationsanitaireRegionFacadeLocal.find(structure, m, SessionMBean.getRegion());
                        if (temp.isEmpty()) {
                            InformationsanitaireRegion informationsanitaireRegion = new InformationsanitaireRegion();
                            informationsanitaireRegion.setIdrubriqueinfosanitaire(m);
                            informationsanitaireRegion.setIdstructure(structure);
                            informationsanitaireRegion.setIdregion(SessionMBean.getRegion());
                            informationsanitaireRegions.add(informationsanitaireRegion);
                        } else {
                            informationsanitaireRegions.add(temp.get(0));
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

            List<CommentaireRegion> commentaireRegions = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 28);
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
            if (!informationsanitaireRegions.isEmpty()) {
                for (InformationsanitaireRegion m : informationsanitaireRegions) {
                    if (m.getIdinformationsanitaireRegion() == null) {
                        m.setIdinformationsanitaireRegion(informationsanitaireRegionFacadeLocal.nextId());
                        m.setIdregion(SessionMBean.getRegion());
                        informationsanitaireRegionFacadeLocal.create(m);
                    } else {
                        informationsanitaireRegionFacadeLocal.edit(m);
                    }
                }

                List<CommentaireRegion> commentaireRegions = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 28);
                if (commentaireRegions.isEmpty()) {
                    commentaireRegion.setIdcommentaireRegion(commentaireRegionFacadeLocal.nextId());
                    commentaireRegion.setIdregion(SessionMBean.getRegion());
                    commentaireRegion.setNumerotab(28);
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

    public void consolidate() {
        try {
            for (Rubriqueinfosanitaire r : rubriqueinformationsanitaires) {

                List<Informationsanitairedistrict> is = informationsanitairedistrictFacadeLocal.find(r, structure.getIddistrict());

                Double res = 0d;

                for (Informationsanitairedistrict i : is) {
                    try {
                        Integer temp = Integer.valueOf(i.getValeur()).intValue();
                        res += temp;
                    } catch (Exception e) {
                        e.printStackTrace();
                        res += 0;
                    }
                }

                List<Informationsanitairedistrict> is1 = informationsanitairedistrictFacadeLocal.find1(r, structure);
                if (!is1.isEmpty()) {
                    is1.get(0).setValeur("" + res);
                    informationsanitairedistrictFacadeLocal.edit(is1.get(0));
                } else {
                    Informationsanitairedistrict temp = new Informationsanitairedistrict();
                    temp.setIdinformationsanitairedistrict(informationsanitairedistrictFacadeLocal.nextId());
                    temp.setIdrubriqueinfosanitaire(r);
                    temp.setIdstructure(structure);
                    temp.setIddistrict(structure.getIddistrict());
                    temp.setPrcds(true);
                    temp.setValeur("" + res);
                    informationsanitairedistrictFacadeLocal.create(temp);
                }
            }
            JsfUtil.addSuccessMessage("Opération réussie");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String findRubriqueinformationsanitaire(Structure structure, Rubriqueinfosanitaire rubriqueinformationsanitaire) {
        String resultat = "";
        try {
            if (structure != null) {
                if (rubriqueinformationsanitaire != null) {

                    List<InformationsanitaireRegion> temps = informationsanitaireRegionFacadeLocal.find(structure , rubriqueinformationsanitaire,SessionMBean.getRegion());
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
    
    public String findRubriqueinformationsanitaire1(Structure structure, Rubriqueinfosanitaire rubriqueinformationsanitaire) {
        String resultat = "";
        try {
            if (structure != null) {
                if (rubriqueinformationsanitaire != null) {

                    List<Informationsanitairedistrict> temps = informationsanitairedistrictFacadeLocal.find1(rubriqueinformationsanitaire,structure);
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

    public List<Rubriqueinfosanitaire> getRubriqueinfosanitaires() {
        rubriqueinformationsanitaires = rubriqueinformationsanitaireFacadeLocal.findAllRange();
        return rubriqueinformationsanitaires;
    }

    public void setRubriqueinfosanitaires(List<Rubriqueinfosanitaire> rubriqueinformationsanitaires) {
        this.rubriqueinformationsanitaires = rubriqueinformationsanitaires;
    }

    public List<InformationsanitaireRegion> getInformationsanitaireRegions() {
        return informationsanitaireRegions;
    }

    public void setInformationsanitaireRegions(List<InformationsanitaireRegion> informationsanitaireRegions) {
        this.informationsanitaireRegions = informationsanitaireRegions;
    }

    public CommentaireRegion getCommentaireRegion() {
        return commentaireRegion;
    }

    public void setCommentaireRegion(CommentaireRegion commentaireRegion) {
        this.commentaireRegion = commentaireRegion;
    }

    public Rubriqueinfosanitaire getRubriqueinfosanitaire() {
        return rubriqueinfosanitaire;
    }

    public void setRubriqueinfosanitaire(Rubriqueinfosanitaire rubriqueinfosanitaire) {
        this.rubriqueinfosanitaire = rubriqueinfosanitaire;
    }

    public List<Structure> getStructures1() {
        return structures1;
    }

    public void setStructures1(List<Structure> structures1) {
        this.structures1 = structures1;
    }

}
