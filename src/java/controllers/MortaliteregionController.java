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
import entities.District;
import entities.Rubriquemortalite;
import entities.MortaliteRegion;
import entities.Mortalite;
import entities.Mortalitedistrict;
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
import sessions.DistrictFacadeLocal;
import sessions.RubriquemortaliteFacadeLocal;
import sessions.MortaliteRegionFacadeLocal;
import sessions.MortaliteFacadeLocal;
import sessions.MortalitedistrictFacadeLocal;
import sessions.StructureFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@SessionScoped
public class MortaliteregionController {

    @EJB
    private MortaliteRegionFacadeLocal mortaliteRegionFacadeLocal;
    private List<MortaliteRegion> mortaliteRegions = new ArrayList<>();

    @EJB
    private RubriquemortaliteFacadeLocal rubriquemortaliteFacadeLocal;
    private List<Rubriquemortalite> rubriquemortalites = new ArrayList<>();

    @EJB
    private MortaliteFacadeLocal mortaliteFacadeLocal;
    private Mortalite mortalite = new Mortalite();
    private List<Mortalite> mortalites = new ArrayList<>();

    @EJB
    private AnneeFacadeLocal anneeFacadeLocal;
    private Annee annee = new Annee();
    private List<Annee> annees = new ArrayList<>();

    @EJB
    private DistrictFacadeLocal districtFacadeLocal;
    private District district = new District();
    private List<District> districts = new ArrayList<>();

    @EJB
    private StructureFacadeLocal structureFacadeLocal;
    private Structure structure = new Structure();
    private List<Structure> structures = new ArrayList<>();
    private List<Structure> structures1 = new ArrayList<>();

    @EJB
    private MortalitedistrictFacadeLocal mortalitedistrictFacadeLocal;

    @EJB
    private CommentaireRegionFacadeLocal commentaireRegionFacadeLocal;
    private CommentaireRegion commentaireRegion = new CommentaireRegion();

    private boolean detail = true;

    /**
     * Creates a new instance of SousaxeController
     */
    public MortaliteregionController() {
    }

    @PostConstruct
    private void init() {
        mortalite = new Mortalite();
        annee = new Annee();
        this.updateCommentaire();
        try {
            districts = districtFacadeLocal.findByRegion(SessionMBean.getRegion().getIdregion());
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

    public void uptadeTable() {
        try {
            structure = new Structure();
            mortaliteRegions.clear();
            if (mortalite != null) {
                if (!this.getRubriquemortalites().isEmpty()) {
                    for (Rubriquemortalite m : this.getRubriquemortalites()) {
                        MortaliteRegion mortaliteRegion = new MortaliteRegion();
                        mortaliteRegion.setIdrubriquemortalite(m);
                        mortaliteRegion.setIdmortalite(mortalite);
                        mortaliteRegion.setIdregion(SessionMBean.getRegion());
                        mortaliteRegions.add(mortaliteRegion);
                    }
                }
            } else {
                JsfUtil.addErrorMessage("Veuillez selectionnner une ligne");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStructureData() {
        mortaliteRegions.clear();
        try {
            if (mortalite.getIdmortalite() != null) {
                if (structure.getIdstructure() != null) {
                    if (!this.getRubriquemortalites().isEmpty()) {
                        for (Rubriquemortalite m : this.getRubriquemortalites()) {
                            List<MortaliteRegion> temp = mortaliteRegionFacadeLocal.find(mortalite, m, structure);
                            if (temp.isEmpty()) {
                                MortaliteRegion mortaliteRegion = new MortaliteRegion();
                                mortaliteRegion.setIdmortaliteRegion(0L);
                                mortaliteRegion.setIdrubriquemortalite(m);
                                mortaliteRegion.setIdmortalite(mortalite);
                                mortaliteRegion.setIdstructure(structure);
                                mortaliteRegion.setIdregion(SessionMBean.getRegion());
                                mortaliteRegion.setConsolide(false);
                                mortaliteRegions.add(mortaliteRegion);
                            } else {
                                mortaliteRegions.add(temp.get(0));
                            }
                        }
                    }
                } else {
                    System.err.println("structure nulle");
                }
            } else {
                System.err.println("morbidité null");
                JsfUtil.addErrorMessage("Veuillez selectionnner une ligne");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initMortalite(Mortalite mortalite) {
        try {
            this.mortalite = mortalite;
            RequestContext.getCurrentInstance().execute("PF('MedicamentDetailDialog').show()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consolidate() {
        try {
            for (Mortalite m : mortalites) {
                for (Rubriquemortalite r : rubriquemortalites) {
                    Double res = 0d;
                    for (District d : districts) {
                        List<Mortalitedistrict> mbd = mortalitedistrictFacadeLocal.find(m, r, d);
                        if (!mbd.isEmpty()) {
                            res += mbd.get(0).getValeur().doubleValue();
                        }
                    }

                    List<MortaliteRegion> mrs = mortaliteRegionFacadeLocal.find(m, r, SessionMBean.getRegion());
                    if (!mrs.isEmpty()) {
                        mrs.get(0).setValeur(res);
                        mortaliteRegionFacadeLocal.edit(mrs.get(0));
                    } else {
                        MortaliteRegion mrs1 = new MortaliteRegion();
                        mrs1.setIdmortaliteRegion(mortaliteRegionFacadeLocal.nextId());
                        mrs1.setIdmortalite(m);
                        mrs1.setIdrubriquemortalite(r);
                        mrs1.setIdregion(SessionMBean.getRegion());
                        mrs1.setValeur(res);
                        mrs1.setConsolide(true);
                        mortaliteRegionFacadeLocal.create(mrs1);
                    }
                }
            }
            JsfUtil.addSuccessMessage("Opération réussie");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCommentaire() {
        try {

            List<CommentaireRegion> commentaireRegions = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 7);
            if (!commentaireRegions.isEmpty()) {
                commentaireRegion = commentaireRegions.get(0);
                return;
            }
            commentaireRegion = new CommentaireRegion();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validComment() {
        try {
            List<CommentaireRegion> commentaireRegions = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 7);
            if (commentaireRegions.isEmpty()) {
                commentaireRegion.setIdcommentaireRegion(commentaireRegionFacadeLocal.nextId());
                commentaireRegion.setIdregion(SessionMBean.getRegion());
                commentaireRegion.setNumerotab(7);
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
            if (!mortaliteRegions.isEmpty()) {
                for (MortaliteRegion m : mortaliteRegions) {
                    if (m.getIdmortaliteRegion() == 0) {
                        m.setIdmortaliteRegion(mortaliteRegionFacadeLocal.nextId());
                        m.setIdregion(SessionMBean.getRegion());
                        m.setIdstructure(structure);
                        m.setConsolide(false);
                        mortaliteRegionFacadeLocal.create(m);
                    } else {
                        mortaliteRegionFacadeLocal.edit(m);
                    }
                }

                List<CommentaireRegion> commentaireRegions = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 7);
                if (commentaireRegions.isEmpty()) {
                    commentaireRegion.setIdcommentaireRegion(commentaireRegionFacadeLocal.nextId());
                    commentaireRegion.setIdregion(SessionMBean.getRegion());
                    commentaireRegion.setNumerotab(7);
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

    public String findRubriquemortalite(Mortalite mortalite, Rubriquemortalite rubriquemortalite) {
        String resultat = "";
        try {
            if (mortalite != null) {
                if (rubriquemortalite != null) {
                    List<MortaliteRegion> temps = mortaliteRegionFacadeLocal.find(mortalite, rubriquemortalite, SessionMBean.getRegion());
                    Double temp = 0d;
                    if (!temps.isEmpty()) {
                        for (MortaliteRegion m : temps) {
                            try {
                                temp += m.getValeur();
                            } catch (Exception e) {
                            }
                        }
                        resultat = "" + temp;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultat;
    }

    public String findByDistrict(District district, Rubriquemortalite r) {
        String resultat = "";
        try {

            if (mortalite != null) {
                if (r != null) {

                    List<Mortalitedistrict> temps = mortalitedistrictFacadeLocal.find(mortalite, r, district);
                    if (!temps.isEmpty()) {
                        resultat = "" + temps.get(0).getValeur();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultat;
    }

    public String findByStructure(Structure structure, Rubriquemortalite r) {
        String resultat = "";
        try {

            if (mortalite != null) {
                if (r != null) {
                    List<MortaliteRegion> temps = mortaliteRegionFacadeLocal.find(mortalite, r, structure);
                    if (!temps.isEmpty()) {
                        resultat = "" + temps.get(0).getValeur();
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

    public Mortalite getMortalite() {
        return mortalite;
    }

    public void setMortalite(Mortalite mortalite) {
        this.mortalite = mortalite;
    }

    public List<Mortalite> getMortalites() {

        mortalites = mortaliteFacadeLocal.findAllRangeCode();
        return mortalites;
    }

    public void setMortalites(List<Mortalite> mortalites) {
        this.mortalites = mortalites;
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

    public List<Rubriquemortalite> getRubriquemortalites() {
        rubriquemortalites = rubriquemortaliteFacadeLocal.findAllRange();
        return rubriquemortalites;
    }

    public List<MortaliteRegion> getMortaliteRegions() {
        return mortaliteRegions;
    }

    public void setMortaliteRegions(List<MortaliteRegion> mortaliteRegions) {
        this.mortaliteRegions = mortaliteRegions;
    }

    public CommentaireRegion getCommentaireRegion() {
        return commentaireRegion;
    }

    public void setCommentaireRegion(CommentaireRegion commentaireRegion) {
        this.commentaireRegion = commentaireRegion;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public List<Structure> getStructures() {
        this.structures.clear();
        try {
            List<Structure> structures = structureFacadeLocal.findByRegionNiveau(SessionMBean.getRegion(), 2);
            for (Structure s : structures) {
                if (!s.getConsolide()) {
                    this.structures.add(s);
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

    public List<Structure> getStructures1() {
        return structures1;
    }

    public void setStructures1(List<Structure> structures1) {
        this.structures1 = structures1;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

}
