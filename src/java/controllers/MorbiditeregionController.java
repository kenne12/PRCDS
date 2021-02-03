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
import entities.Rubriquemorbidite;
import entities.MorbiditeRegion;
import entities.Morbidite;
import entities.Morbiditedistrict;
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
import sessions.RubriquemorbiditeFacadeLocal;
import sessions.MorbiditeRegionFacadeLocal;
import sessions.MorbiditeFacadeLocal;
import sessions.MorbiditedistrictFacadeLocal;
import sessions.StructureFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@SessionScoped
public class MorbiditeregionController {

    @EJB
    private MorbiditeRegionFacadeLocal morbiditeRegionFacadeLocal;
    private List<MorbiditeRegion> morbiditeRegions = new ArrayList<>();

    @EJB
    private MorbiditedistrictFacadeLocal morbiditedistrictFacadeLocal;

    @EJB
    private RubriquemorbiditeFacadeLocal rubriquemorbiditeFacadeLocal;
    private List<Rubriquemorbidite> rubriquemorbidites = new ArrayList<>();

    @EJB
    private MorbiditeFacadeLocal morbiditeFacadeLocal;
    private Morbidite morbidite;
    private List<Morbidite> morbidites = new ArrayList<>();

    @EJB
    private AnneeFacadeLocal anneeFacadeLocal;
    private Annee annee;
    private List<Annee> annees = new ArrayList<>();

    @EJB
    private DistrictFacadeLocal districtFacadeLocal;
    private List<District> districts = new ArrayList<>();

    @EJB
    private CommentaireRegionFacadeLocal commentaireRegionFacadeLocal;
    private CommentaireRegion commentaireRegion = new CommentaireRegion();

    @EJB
    private StructureFacadeLocal structureFacadeLocal;
    private Structure structure = new Structure();
    private List<Structure> structures = new ArrayList<>();
    private List<Structure> structures1 = new ArrayList<>();

    private boolean detail = true;

    /**
     * Creates a new instance of SousaxeController
     */
    public MorbiditeregionController() {
    }

    @PostConstruct
    private void init() {
        morbidite = new Morbidite();
        annee = new Annee();
        detail = true;
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

    public void validComment() {
        try {
            List<CommentaireRegion> commentaireRegions = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 4);
            if (commentaireRegions.isEmpty()) {
                commentaireRegion.setIdcommentaireRegion(commentaireRegionFacadeLocal.nextId());
                commentaireRegion.setIdregion(SessionMBean.getRegion());
                commentaireRegion.setNumerotab(4);
                commentaireRegionFacadeLocal.create(commentaireRegion);
            } else {
                commentaireRegionFacadeLocal.edit(commentaireRegion);
            }
            JsfUtil.addSuccessMessage("Opération réussie");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStructureData() {
        morbiditeRegions.clear();
        try {
            if (morbidite.getIdmorbidite() != null) {
                if (structure.getIdstructure() != null) {
                    if (!this.getRubriquemorbidites().isEmpty()) {
                        for (Rubriquemorbidite m : this.getRubriquemorbidites()) {
                            List<MorbiditeRegion> temp = morbiditeRegionFacadeLocal.find(morbidite, m, structure);
                            if (temp.isEmpty()) {
                                MorbiditeRegion morbiditeRegion = new MorbiditeRegion();
                                morbiditeRegion.setIdmorbiditeRegion(0L);
                                morbiditeRegion.setIdrubriquemorbidite(m);
                                morbiditeRegion.setIdmorbidite(morbidite);
                                morbiditeRegion.setIdstructure(structure);
                                morbiditeRegion.setIdregion(SessionMBean.getRegion());
                                morbiditeRegion.setConsolide(false);
                                morbiditeRegions.add(morbiditeRegion);
                            } else {
                                morbiditeRegions.add(temp.get(0));
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

    public void uptadeTable() {
        try {
            structure = new Structure();
            morbiditeRegions.clear();
            if (morbidite != null) {
                if (!this.getRubriquemorbidites().isEmpty()) {
                    for (Rubriquemorbidite m : this.getRubriquemorbidites()) {
                        MorbiditeRegion morbiditeRegion = new MorbiditeRegion();
                        morbiditeRegion.setIdrubriquemorbidite(m);
                        morbiditeRegion.setIdmorbidite(morbidite);
                        morbiditeRegion.setIdregion(SessionMBean.getRegion());
                        morbiditeRegions.add(morbiditeRegion);
                    }
                }
            } else {
                JsfUtil.addErrorMessage("Veuillez selectionnner une ligne");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consolidate() {
        try {
            List<District> ds = districtFacadeLocal.findByRegion(SessionMBean.getRegion().getIdregion());
            for (Morbidite m : morbidites) {
                for (Rubriquemorbidite r : rubriquemorbidites) {
                    Double res = 0d;
                    for (District d : ds) {
                        List<Morbiditedistrict> mbd = morbiditedistrictFacadeLocal.find(m, r, d);
                        if (!mbd.isEmpty()) {
                            res += mbd.get(0).getValeur().doubleValue();
                        }
                    }

                    List<MorbiditeRegion> mrs = morbiditeRegionFacadeLocal.find(m, r, SessionMBean.getRegion());
                    if (!mrs.isEmpty()) {
                        mrs.get(0).setValeur(res);
                        morbiditeRegionFacadeLocal.edit(mrs.get(0));
                    } else {
                        MorbiditeRegion mrs1 = new MorbiditeRegion();
                        mrs1.setIdmorbiditeRegion(morbiditeRegionFacadeLocal.nextId());
                        mrs1.setIdmorbidite(m);
                        mrs1.setIdrubriquemorbidite(r);
                        mrs1.setIdregion(SessionMBean.getRegion());
                        mrs1.setValeur(res);
                        mrs1.setConsolide(true);
                        morbiditeRegionFacadeLocal.create(mrs1);
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
            List<CommentaireRegion> commentaireRegions = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 4);
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
            if (structure.getIdstructure() != null) {

                if (morbidite != null) {

                    if (!morbiditeRegions.isEmpty()) {

                        for (MorbiditeRegion m : morbiditeRegions) {
                            if (m.getIdmorbiditeRegion() == 0L) {
                                m.setIdmorbiditeRegion(morbiditeRegionFacadeLocal.nextId());
                                m.setIdregion(SessionMBean.getRegion());
                                m.setConsolide(false);
                                m.setIdstructure(structure);
                                morbiditeRegionFacadeLocal.create(m);
                            } else {
                                morbiditeRegionFacadeLocal.edit(m);
                            }
                        }

                        List<CommentaireRegion> commentaireRegions = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 4);
                        if (commentaireRegions.isEmpty()) {
                            commentaireRegion.setIdcommentaireRegion(commentaireRegionFacadeLocal.nextId());
                            commentaireRegion.setIdregion(SessionMBean.getRegion());
                            commentaireRegion.setNumerotab(4);
                            commentaireRegionFacadeLocal.create(commentaireRegion);
                        } else {
                            commentaireRegionFacadeLocal.edit(commentaireRegion);
                        }

                        JsfUtil.addSuccessMessage("Opération réussie");
                    } else {
                        JsfUtil.addErrorMessage("Le tableau est vide");
                    }
                } else {
                    JsfUtil.addErrorMessage("Aucune morbidité selectionnée");
                }
            } else {
                JsfUtil.addErrorMessage("Aucune structure selectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        morbidite = new Morbidite();
        detail = true;
    }

    public String findRubriquemorbidite(Morbidite morbidite, Rubriquemorbidite rubriquemorbidite) {
        String resultat = "";
        try {
            if (morbidite != null) {
                if (rubriquemorbidite != null) {
                    Double temp = 0d;
                    List<MorbiditeRegion> temps = morbiditeRegionFacadeLocal.find(morbidite, rubriquemorbidite, SessionMBean.getRegion());
                    if (!temps.isEmpty()) {
                        for (MorbiditeRegion m : temps) {
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

    public String findByDistrict(District district, Rubriquemorbidite rubriquemorbidite) {
        String resultat = "";
        try {
            if (morbidite != null) {
                if (rubriquemorbidite != null) {

                    List<Morbiditedistrict> temps = morbiditedistrictFacadeLocal.find(morbidite, rubriquemorbidite, district);
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

    public String findByRegion(Structure structure, Rubriquemorbidite rubriquemorbidite) {
        String resultat = "";
        try {
            if (morbidite != null) {
                if (rubriquemorbidite != null) {

                    List<MorbiditeRegion> temps = morbiditeRegionFacadeLocal.find(morbidite, rubriquemorbidite, structure);
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

    public void initMorbidite(Morbidite morbidite) {
        try {
            this.morbidite = morbidite;
            RequestContext.getCurrentInstance().execute("PF('MedicamentDetailDialog').show()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initMorbidite1(Morbidite morbidite) {
        try {
            this.morbidite = morbidite;
            uptadeTable();
            RequestContext.getCurrentInstance().execute("PF('MedicamentCreateDialog').show()");
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

    public Morbidite getMorbidite() {
        return morbidite;
    }

    public void setMorbidite(Morbidite morbidite) {
        this.morbidite = morbidite;
    }

    public List<Morbidite> getMorbidites() {
        morbidites = morbiditeFacadeLocal.findAllRangeCode();
        return morbidites;
    }

    public void setMorbidites(List<Morbidite> morbidites) {
        this.morbidites = morbidites;
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

    public List<Rubriquemorbidite> getRubriquemorbidites() {
        rubriquemorbidites = rubriquemorbiditeFacadeLocal.findAllRange();
        return rubriquemorbidites;
    }

    public List<MorbiditeRegion> getMorbiditeRegions() {
        return morbiditeRegions;
    }

    public void setMorbiditeRegions(List<MorbiditeRegion> morbiditeRegions) {
        this.morbiditeRegions = morbiditeRegions;
    }

    public CommentaireRegion getCommentaireRegion() {
        return commentaireRegion;
    }

    public void setCommentaireRegion(CommentaireRegion commentaireRegion) {
        this.commentaireRegion = commentaireRegion;
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
