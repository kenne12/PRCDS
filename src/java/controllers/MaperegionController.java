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
import entities.MapeRegion;
import entities.Mape;
import entities.Mapedistrict;
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
import sessions.MapeRegionFacadeLocal;
import sessions.MapeFacadeLocal;
import sessions.MapedistrictFacadeLocal;
import sessions.StructureFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@SessionScoped
public class MaperegionController {

    @EJB
    private MapeRegionFacadeLocal mapeRegionFacadeLocal;
    private List<MapeRegion> mapeRegions = new ArrayList<>();

    @EJB
    private MapeFacadeLocal mapeFacadeLocal;
    private Mape mape;
    private List<Mape> mapes = new ArrayList<>();

    @EJB
    private AnneeFacadeLocal anneeFacadeLocal;
    private Annee annee;
    private List<Annee> annees = new ArrayList<>();

    @EJB
    private CommentaireRegionFacadeLocal commentaireRegionFacadeLocal;
    private CommentaireRegion commentaireRegion = new CommentaireRegion();

    @EJB
    private DistrictFacadeLocal districtFacadeLocal;
    private District district = new District();
    private List<District> districts = new ArrayList<>();

    @EJB
    private MapedistrictFacadeLocal mapedistrictFacadeLocal;

    @EJB
    private StructureFacadeLocal structureFacadeLocal;
    private Structure structure = new Structure();
    private List<Structure> structures = new ArrayList<>();
    private List<Structure> structures1 = new ArrayList<>();

    private boolean detail = true;

    /**
     * Creates a new instance of SousaxeController
     */
    public MaperegionController() {
    }

    @PostConstruct
    private void init() {
        mape = new Mape();
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
            mapeRegions.clear();
            structure = new Structure();
            if (mape != null) {
                if (!this.getAnnees().isEmpty()) {
                    for (Annee m : this.getAnnees()) {
                        MapeRegion mapeRegion = new MapeRegion();
                        mapeRegion.setIdannee(m);
                        mapeRegion.setIdmape(mape);
                        mapeRegion.setIdregion(SessionMBean.getRegion());
                        mapeRegions.add(mapeRegion);
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
        mapeRegions.clear();
        try {
            if (mape.getIdmape() != null) {
                if (structure.getIdstructure() != null) {
                    if (!this.getAnnees().isEmpty()) {
                        for (Annee a : this.getAnnees()) {
                            List<MapeRegion> temp = mapeRegionFacadeLocal.find(mape, a, structure);
                            if (temp.isEmpty()) {
                                MapeRegion mapeRegion = new MapeRegion();
                                mapeRegion.setIdmapeRegion(0L);
                                mapeRegion.setIdannee(a);
                                mapeRegion.setIdmape(mape);
                                mapeRegion.setIdstructure(structure);
                                mapeRegion.setIdregion(SessionMBean.getRegion());
                                mapeRegion.setConsolide(false);
                                mapeRegions.add(mapeRegion);
                            } else {
                                mapeRegions.add(temp.get(0));
                            }
                        }
                    }
                } else {
                    System.err.println("structure nulle");
                }
            } else {
                System.err.println("hospitalisation null");
                JsfUtil.addErrorMessage("Veuillez selectionnner une ligne");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initMape(Mape mape) {
        try {
            this.mape = mape;
            RequestContext.getCurrentInstance().execute("PF('MedicamentDetailDialog').show()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String findByDistrict(District district, Annee a) {
        String resultat = "";
        try {

            if (a != null) {
                if (mape != null) {

                    List<Mapedistrict> temps = mapedistrictFacadeLocal.find(mape, a, district);
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

    public String findByStructure(Structure structure, Annee a) {
        String resultat = "";
        try {
            if (a != null) {
                if (mape != null) {

                    List<MapeRegion> temps = mapeRegionFacadeLocal.find(mape, a, structure);
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

    public void consolidate() {
        try {
            for (Mape m : mapes) {
                for (Annee a : annees) {
                    Double res = 0d;
                    for (District d : districts) {
                        List<Mapedistrict> mbd = mapedistrictFacadeLocal.find(m, a, d);
                        if (!mbd.isEmpty()) {
                            res += mbd.get(0).getValeur().doubleValue();
                        }
                    }

                    List<MapeRegion> mrs = mapeRegionFacadeLocal.find(m, a, SessionMBean.getRegion());
                    if (!mrs.isEmpty()) {
                        mrs.get(0).setValeur(res);
                        mapeRegionFacadeLocal.edit(mrs.get(0));
                    } else {
                        MapeRegion mrs1 = new MapeRegion();
                        mrs1.setIdmapeRegion(mapeRegionFacadeLocal.nextId());
                        mrs1.setIdmape(m);
                        mrs1.setIdannee(a);
                        mrs1.setIdregion(SessionMBean.getRegion());
                        mrs1.setValeur(res);
                        mrs1.setConsolide(true);
                        mapeRegionFacadeLocal.create(mrs1);
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

            List<CommentaireRegion> commentaireRegions = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 6);
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
            List<CommentaireRegion> commentaireRegions = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 6);
            if (commentaireRegions.isEmpty()) {
                commentaireRegion.setIdcommentaireRegion(commentaireRegionFacadeLocal.nextId());
                commentaireRegion.setIdregion(SessionMBean.getRegion());
                commentaireRegion.setNumerotab(6);
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
            if (!mapeRegions.isEmpty()) {
                for (MapeRegion m : mapeRegions) {
                    if (m.getIdmapeRegion() == 0) {
                        m.setIdmapeRegion(mapeRegionFacadeLocal.nextId());
                        m.setIdregion(SessionMBean.getRegion());
                        m.setConsolide(false);
                        m.setIdstructure(structure);
                        mapeRegionFacadeLocal.create(m);
                    } else {
                        mapeRegionFacadeLocal.edit(m);
                    }
                }

                List<CommentaireRegion> commentaireRegions = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 6);
                if (commentaireRegions.isEmpty()) {
                    commentaireRegion.setIdcommentaireRegion(commentaireRegionFacadeLocal.nextId());
                    commentaireRegion.setIdregion(SessionMBean.getRegion());
                    commentaireRegion.setNumerotab(6);
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
        mape = new Mape();
        detail = true;
    }

    public String findAnnee(Mape mape, Annee annee) {
        String resultat = "";
        try {

            if (mape != null) {
                if (annee != null) {

                    List<MapeRegion> temps = mapeRegionFacadeLocal.find(mape, annee, SessionMBean.getRegion());
                    Double temp = 0d;
                    if (!temps.isEmpty()) {
                        for (MapeRegion m : temps) {
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

    public boolean isDetail() {
        return detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    public Mape getMape() {
        return mape;
    }

    public void setMape(Mape mape) {
        this.mape = mape;
    }

    public List<Mape> getMapes() {
        mapes = mapeFacadeLocal.findAllCode();
        return mapes;
    }

    public void setMapes(List<Mape> mapes) {
        this.mapes = mapes;
    }

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public List<Annee> getAnnees() {
        try {
            annees = anneeFacadeLocal.findByEtatMape(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return annees;
    }

    public void setAnnees(List<Annee> annees) {
        this.annees = annees;
    }

    public List<MapeRegion> getMapeRegions() {
        return mapeRegions;
    }

    public void setMapeRegions(List<MapeRegion> mapeRegions) {
        this.mapeRegions = mapeRegions;
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

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public List<Structure> getStructures1() {
        return structures1;
    }

    public void setStructures1(List<Structure> structures1) {
        this.structures1 = structures1;
    }

}
