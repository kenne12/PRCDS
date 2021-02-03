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
import entities.Rubriquehospitalisation;
import entities.HospitalisationRegion;
import entities.Hospitalisation;
import entities.Hospitalisationdistrict;
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
import sessions.RubriquehospitalisationFacadeLocal;
import sessions.HospitalisationRegionFacadeLocal;
import sessions.HospitalisationFacadeLocal;
import sessions.HospitalisationdistrictFacadeLocal;
import sessions.StructureFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@SessionScoped
public class HospitalisationregionController {

    @EJB
    private HospitalisationRegionFacadeLocal hospitalisationRegionFacadeLocal;
    private List<HospitalisationRegion> hospitalisationRegions = new ArrayList<>();

    @EJB
    private RubriquehospitalisationFacadeLocal rubriquehospitalisationFacadeLocal;
    private List<Rubriquehospitalisation> rubriquehospitalisations = new ArrayList<>();

    @EJB
    private HospitalisationFacadeLocal hospitalisationFacadeLocal;
    private Hospitalisation hospitalisation;
    private List<Hospitalisation> hospitalisations = new ArrayList<>();

    @EJB
    private HospitalisationdistrictFacadeLocal hospitalisationdistrictFacadeLocal;

    @EJB
    private AnneeFacadeLocal anneeFacadeLocal;
    private Annee annee;
    private List<Annee> annees = new ArrayList<>();

    @EJB
    private DistrictFacadeLocal districtFacadeLocal;
    private District district = new District();
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
    public HospitalisationregionController() {
    }

    @PostConstruct
    private void init() {
        hospitalisation = new Hospitalisation();
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
            hospitalisationRegions.clear();
            structure = new Structure();
            if (hospitalisation != null) {
                if (!this.getRubriquehospitalisations().isEmpty()) {
                    for (Rubriquehospitalisation m : this.getRubriquehospitalisations()) {
                        HospitalisationRegion hospitalisationRegion = new HospitalisationRegion();
                        hospitalisationRegion.setIdrubriquehospitalisation(m);
                        hospitalisationRegion.setIdhospitalisation(hospitalisation);
                        hospitalisationRegion.setIdregion(SessionMBean.getRegion());
                        hospitalisationRegions.add(hospitalisationRegion);
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
        hospitalisationRegions.clear();
        try {
            if (hospitalisation.getIdhospitalisation() != null) {
                if (structure.getIdstructure() != null) {
                    if (!this.getRubriquehospitalisations().isEmpty()) {
                        for (Rubriquehospitalisation m : this.getRubriquehospitalisations()) {
                            List<HospitalisationRegion> temp = hospitalisationRegionFacadeLocal.find(hospitalisation, m, structure);
                            if (temp.isEmpty()) {
                                HospitalisationRegion hospitalisationRegion = new HospitalisationRegion();
                                hospitalisationRegion.setIdhospitalisationRegion(0L);
                                hospitalisationRegion.setIdrubriquehospitalisation(m);
                                hospitalisationRegion.setIdhospitalisation(hospitalisation);
                                hospitalisationRegion.setIdstructure(structure);
                                hospitalisationRegion.setIdregion(SessionMBean.getRegion());
                                hospitalisationRegion.setConsolide(false);
                                hospitalisationRegions.add(hospitalisationRegion);
                            } else {
                                hospitalisationRegions.add(temp.get(0));
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

    public void updateCommentaire() {
        try {
            List<CommentaireRegion> commentaireRegions = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 5);
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
            List<CommentaireRegion> commentaireRegions = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 5);
            if (commentaireRegions.isEmpty()) {
                commentaireRegion.setIdcommentaireRegion(commentaireRegionFacadeLocal.nextId());
                commentaireRegion.setIdregion(SessionMBean.getRegion());
                commentaireRegion.setNumerotab(5);
                commentaireRegionFacadeLocal.create(commentaireRegion);
            } else {
                commentaireRegionFacadeLocal.edit(commentaireRegion);
            }
            JsfUtil.addSuccessMessage("Opération réussie");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initHospitalisarion(Hospitalisation hospitalisation) {
        try {
            this.hospitalisation = hospitalisation;
            RequestContext.getCurrentInstance().execute("PF('MedicamentDetailDialog').show()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consolidate() {
        try {
            List<District> ds = districtFacadeLocal.findByRegion(SessionMBean.getRegion().getIdregion());
            for (Hospitalisation h : hospitalisations) {
                for (Rubriquehospitalisation r : rubriquehospitalisations) {
                    Double res = 0d;
                    for (District d : ds) {
                        List<Hospitalisationdistrict> mbd = hospitalisationdistrictFacadeLocal.find(h, r, d);
                        if (!mbd.isEmpty()) {
                            res += mbd.get(0).getValeur().doubleValue();
                        }
                    }

                    List<HospitalisationRegion> mrs = hospitalisationRegionFacadeLocal.find(h, r, SessionMBean.getRegion());
                    if (!mrs.isEmpty()) {
                        mrs.get(0).setValeur(res);
                        hospitalisationRegionFacadeLocal.edit(mrs.get(0));
                    } else {
                        HospitalisationRegion mrs1 = new HospitalisationRegion();
                        mrs1.setIdhospitalisationRegion(hospitalisationRegionFacadeLocal.nextId());
                        mrs1.setIdhospitalisation(h);
                        mrs1.setIdrubriquehospitalisation(r);
                        mrs1.setIdregion(SessionMBean.getRegion());
                        mrs1.setValeur(res);
                        hospitalisationRegionFacadeLocal.create(mrs1);
                    }
                }
            }
            JsfUtil.addSuccessMessage("Opération réussie");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String findByDistrict(District district, Rubriquehospitalisation r) {
        String resultat = "";
        try {
            if (hospitalisation != null) {
                if (r != null) {

                    List<Hospitalisationdistrict> temps = hospitalisationdistrictFacadeLocal.find(hospitalisation, r, district);
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

    public String findByStructure(Structure s, Rubriquehospitalisation r) {
        String resultat = "";
        try {
            if (hospitalisation != null) {
                if (r != null) {
                    List<HospitalisationRegion> temps = hospitalisationRegionFacadeLocal.find(hospitalisation, r, s);
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

    public void create() {
        try {

            if (!hospitalisationRegions.isEmpty()) {
                for (HospitalisationRegion m : hospitalisationRegions) {
                    if (m.getIdhospitalisationRegion() == 0) {
                        m.setIdhospitalisationRegion(hospitalisationRegionFacadeLocal.nextId());
                        m.setIdregion(SessionMBean.getRegion());
                        m.setIdstructure(structure);
                        m.setConsolide(false);
                        hospitalisationRegionFacadeLocal.create(m);
                    } else {
                        hospitalisationRegionFacadeLocal.edit(m);
                    }
                }

                List<CommentaireRegion> commentaireRegions = commentaireRegionFacadeLocal.find(SessionMBean.getRegion(), 5);
                if (commentaireRegions.isEmpty()) {
                    commentaireRegion.setIdcommentaireRegion(commentaireRegionFacadeLocal.nextId());
                    commentaireRegion.setIdregion(SessionMBean.getRegion());
                    commentaireRegion.setNumerotab(5);
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
        hospitalisation = new Hospitalisation();
        detail = true;
    }

    public String findRubriquehospitalisation(Hospitalisation hospitalisation, Rubriquehospitalisation rubriquehospitalisation) {
        String resultat = "";
        try {
            if (hospitalisation != null) {
                if (rubriquehospitalisation != null) {

                    List<HospitalisationRegion> temps = hospitalisationRegionFacadeLocal.find(hospitalisation, rubriquehospitalisation, SessionMBean.getRegion());
                    Double temp = 0d;
                    if (!temps.isEmpty()) {
                        for (HospitalisationRegion h : temps) {
                            try {
                                temp += h.getValeur();
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

    public Hospitalisation getHospitalisation() {
        return hospitalisation;
    }

    public void setHospitalisation(Hospitalisation hospitalisation) {
        this.hospitalisation = hospitalisation;
    }

    public List<Hospitalisation> getHospitalisations() {

        hospitalisations = hospitalisationFacadeLocal.findAllCode();
        return hospitalisations;
    }

    public void setHospitalisations(List<Hospitalisation> hospitalisations) {
        this.hospitalisations = hospitalisations;
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

    public List<Rubriquehospitalisation> getRubriquehospitalisations() {
        rubriquehospitalisations = rubriquehospitalisationFacadeLocal.findAllRange();
        return rubriquehospitalisations;
    }

    public List<HospitalisationRegion> getHospitalisationRegions() {
        return hospitalisationRegions;
    }

    public void setHospitalisationRegions(List<HospitalisationRegion> hospitalisationRegions) {
        this.hospitalisationRegions = hospitalisationRegions;
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
