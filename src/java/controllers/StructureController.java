/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.Adresse;
import entities.Airesante;
import entities.District;
import entities.Institution;
import entities.Structure;
import entities.Mouchard;
import entities.Niveaupyramide;
import entities.Quartier;
import entities.Region;
import entities.Rue;
import entities.Statutstructure;
import entities.Typestructure;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessions.AdresseFacadeLocal;
import sessions.AiresanteFacadeLocal;
import sessions.DepenseFacadeLocal;
import sessions.DistrictFacadeLocal;
import sessions.EquipementtraceurFacadeLocal;
import sessions.InfrastructureFacadeLocal;
import sessions.InstitutionFacadeLocal;
import sessions.StructureFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.NiveaupyramideFacadeLocal;
import sessions.QuartierFacadeLocal;
import sessions.RecetteFacadeLocal;
import sessions.RegionFacadeLocal;
import sessions.RhsFacadeLocal;
import sessions.RueFacadeLocal;
import sessions.StatutstructureFacadeLocal;
import sessions.TypestructureFacadeLocal;

/**
 *
 * @author kenne gervais
 */
@ManagedBean
@ViewScoped
public class StructureController {
    
    @EJB
    private StructureFacadeLocal structureFacadeLocal;
    private Structure structure = new Structure();
    private Structure selected = new Structure();
    private List<Structure> structures = new ArrayList<>();
    
    @EJB
    private AdresseFacadeLocal adresseFacadeLocal;
    private Adresse adresse = new Adresse();
    
    @EJB
    private DistrictFacadeLocal districtFacadeLocal;
    private District district = new District();
    private List<District> districts = new ArrayList<>();
    
    @EJB
    private TypestructureFacadeLocal typestructureFacadeLocal;
    private Typestructure typestructure = new Typestructure();
    private List<Typestructure> typestructures = new ArrayList<>();
    
    @EJB
    private AiresanteFacadeLocal airesanteFacadeLocal;
    private Airesante airesante = new Airesante();
    private List<Airesante> airesantes = new ArrayList<>();
    
    @EJB
    private StatutstructureFacadeLocal statutstructureFacadeLocal;
    private Statutstructure statutstructure = new Statutstructure();
    private List<Statutstructure> statutstructures = new ArrayList<>();
    
    @EJB
    private QuartierFacadeLocal quartierFacadeLocal;
    private Quartier quartier = new Quartier();
    private List<Quartier> quartiers = new ArrayList<>();
    
    @EJB
    private InstitutionFacadeLocal institutionFacadeLocal;
    private Institution institution = new Institution();
    private List<Institution> institutions = new ArrayList<>();
    
    @EJB
    private RueFacadeLocal rueFacadeLocal;
    private List<Rue> rues = new ArrayList<>();
    private Rue rue = new Rue();
    
    @EJB
    private MouchardFacadeLocal mouchardFacadeLocal;
    private Mouchard mouchard = new Mouchard();
    
    @EJB
    private RegionFacadeLocal regionFacadeLocal;
    private Region region = new Region();
    private List<Region> regions = new ArrayList<>();
    
    @EJB
    private NiveaupyramideFacadeLocal niveaupyramideFacadeLocal;
    private Niveaupyramide niveaupyramide = new Niveaupyramide();
    private List<Niveaupyramide> niveaupyramides = new ArrayList<>();
    
    private boolean detail = true;
    private String mode = "";
    
    @EJB
    private RhsFacadeLocal rhsFacadeLocal;
    @EJB
    private DepenseFacadeLocal depenseFacadeLocal;
    @EJB
    private RecetteFacadeLocal recetteFacadeLocal;
    
    @EJB
    private InfrastructureFacadeLocal infrastructureFacadeLocal;
    
    @EJB
    private EquipementtraceurFacadeLocal equipementtraceurFacadeLocal;
    
    public StructureController() {
        
    }
    
    @PostConstruct
    private void init() {
        statutstructure = new Statutstructure();        
    }
    
    public void activeButton() {
        detail = false;
    }
    
    public void deactiveButton() {
        detail = true;
    }
    
    public void resetParents() {
        
    }
    
    public void prepareCreate() {
        structure = new Structure();
        structure.setDistance(0d);
        structure.setConsolide(false);
        adresse = new Adresse();
        adresse.setBp("-");
        adresse.setEmail("-");
        adresse.setContact("-");
        adresse.setSiteWeb("-");
        structure.setLeader(false);
        institution = new Institution();
        mode = "Create";
        region = SessionMBean.getRegion();
        niveaupyramide = niveaupyramides.get(1);
        districts = districtFacadeLocal.findByRegion(region.getIdregion());
        district = new District();
    }
    
    public void prepareEdit() {
        mode = "Edit";
        try {
            if (structure != null) {
                
                region = structure.getIdregion();
                
                districts = districtFacadeLocal.findByRegion(region.getIdregion());
                
                if (structure.getIdstatutstructure() != null) {
                    statutstructure = structure.getIdstatutstructure();
                }
                
                if (structure.getIdtypestructure() != null) {
                    typestructure = structure.getIdtypestructure();
                }
                
                if (structure.getIdadresse() != null) {
                    adresse = structure.getIdadresse();
                }
                
                institution = new Institution();
                if (structure.getIdinstitution() != null) {
                    institution = structure.getIdinstitution();
                }
                
                if (structure.getIddistrict() != null) {
                    district = structure.getIddistrict();
                }
                
                if (structure.getIdniveaupyramide() != null) {
                    niveaupyramide = structure.getIdniveaupyramide();
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void save() {
        try {
            if ("Create".equals(mode)) {
                List<Structure> test = structureFacadeLocal.findByNom(SessionMBean.getRegion(), structure.getNomFr());
                if (test.isEmpty()) {
                    
                    adresse.setIdAdresse(adresseFacadeLocal.nextId());
                    
                    if (statutstructure.getIdstatutstructure() != null) {
                        structure.setIdstatutstructure(statutstructure);
                    }
                    
                    if (typestructure.getIdtypestructure() != null) {
                        structure.setIdstatutstructure(statutstructure);
                    }
                    
                    if (institution.getIdinstitution() != null) {
                        structure.setIdinstitution(institution);
                    }
                    
                    if (niveaupyramide.getIdniveaupyramide() != null) {
                        structure.setIdniveaupyramide(niveaupyramide);
                    }
                    
                    if (region.getIdregion() != null) {
                        structure.setIdregion(region);
                    }
                    
                    if (district.getIddistrict() != null) {
                        structure.setIddistrict(district);
                    }
                    
                    adresseFacadeLocal.create(adresse);
                    structure.setIdadresse(adresse);
                    structure.setActive(true);
                    
                    structure.setIdstructure(structureFacadeLocal.nextId());
                    
                    structureFacadeLocal.create(structure);
                    mouchard.setIdoperation(mouchardFacadeLocal.nextId());
                    mouchard.setAction("Enregistrement de la structure  -> " + structure.getNomFr());
                    mouchard.setIdutilisateur(SessionMBean.getUser());
                    mouchard.setDateaction(new Date());
                    mouchardFacadeLocal.create(mouchard);
                    
                    structure = new Structure();
                    
                    JsfUtil.addSuccessMessage("Enregistrement effectué avec succès");
                } else {
                    JsfUtil.addErrorMessage("Une formation portant ce nom existe déjà !");
                }
            } else {
                if (structure != null) {
                    Structure u = structureFacadeLocal.find(structure.getIdstructure());
                    adresseFacadeLocal.edit(adresse);
                    
                    if (district.getIddistrict() != null) {
                        district = districtFacadeLocal.find(district.getIddistrict());
                        structure.setIddistrict(district);
                    }
                    
                    if (statutstructure.getIdstatutstructure() != null) {
                        statutstructure = statutstructureFacadeLocal.find(statutstructure.getIdstatutstructure());
                        structure.setIdstatutstructure(statutstructure);
                    }
                    
                    if (typestructure.getIdtypestructure() != null) {
                        typestructure = typestructureFacadeLocal.find(typestructure.getIdtypestructure());
                        structure.setIdstatutstructure(statutstructure);
                    }
                    
                    if (institution.getIdinstitution() != null) {
                        institution = institutionFacadeLocal.find(institution.getIdinstitution());
                        structure.setIdinstitution(institution);
                    }
                    
                    if (niveaupyramide.getIdniveaupyramide() != null) {
                        structure.setIdniveaupyramide(niveaupyramideFacadeLocal.find(niveaupyramide.getIdniveaupyramide()));
                    }
                    
                    if (region.getIdregion() != null) {
                        region = regionFacadeLocal.find(region.getIdregion());
                        structure.setIdregion(regionFacadeLocal.find(region.getIdregion()));
                    }
                    
                    structureFacadeLocal.edit(structure);
                    mouchard.setIdoperation(mouchardFacadeLocal.nextId());
                    mouchard.setAction("Modification de la formation sanitaire ->  " + u.getNomFr() + " -> " + structure.getNomFr());
                    mouchard.setIdutilisateur(SessionMBean.getUser());
                    mouchard.setDateaction(new Date());
                    mouchardFacadeLocal.create(mouchard);
                    detail = true;
                    JsfUtil.addSuccessMessage("La formation sanitaire a été mise à jour");
                } else {
                    JsfUtil.addErrorMessage("Aucune formation sanitaire sélectionnée");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void edit() {
        try {
            if (structure != null) {
                Structure u = structureFacadeLocal.find(structure.getIdstructure());
                
                adresseFacadeLocal.edit(adresse);
                structureFacadeLocal.edit(selected);
                
                mouchard.setIdoperation(mouchardFacadeLocal.nextId());
                mouchard.setAction("Modification de la formation sanitaire ->  " + u.getNomFr() + " -> " + structure.getNomFr());
                mouchard.setIdutilisateur(SessionMBean.getUser());
                mouchard.setDateaction(new Date());
                mouchardFacadeLocal.create(mouchard);
                
                detail = true;
                JsfUtil.addSuccessMessage("La formation sanitaire a été mise à jour");
            } else {
                JsfUtil.addErrorMessage("Aucune formation sanitaire sélectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.getStructures();
        }
    }
    
    public void delete() {
        try {
            if (structure != null) {
                if (structure.getActiviteStructureList().isEmpty()) {
                    if (depenseFacadeLocal.find(structure).isEmpty()) {
                        if (equipementtraceurFacadeLocal.find(structure).isEmpty()) {
                            if (infrastructureFacadeLocal.find(structure).isEmpty()) {
                                if (recetteFacadeLocal.find(structure).isEmpty()) {
                                    if (structure.getRhsList().isEmpty()) {
                                        if (structure.getGouvernancedistrictList().isEmpty()) {
                                            if (structure.getInformationsanitairedistrictList().isEmpty()) {
                                                structureFacadeLocal.remove(structure);
                                                mouchard.setIdoperation(mouchardFacadeLocal.nextId());
                                                mouchard.setAction("Suppression de la formation sanitaire -> " + structure.getNomFr());
                                                mouchard.setIdutilisateur(SessionMBean.getUser());
                                                mouchard.setDateaction(new Date());
                                                mouchardFacadeLocal.create(mouchard);
                                                detail = true;
                                                JsfUtil.addSuccessMessage("Suppression effectuée avec succès");
                                            } else {
                                                JsfUtil.addErrorMessage("Cette formation sanitaire contient plusieurs données sur l'information sanitaire");
                                            }
                                        } else {
                                            JsfUtil.addErrorMessage("Cette formation sanitaire contient plusieurs informations sur la gouvernance");
                                        }
                                        
                                    } else {
                                        JsfUtil.addErrorMessage("Cette formation sanitaire est liée à plusieurs info de rhs");
                                    }
                                } else {
                                    JsfUtil.addErrorMessage("Cette formation sanitaire est liée à plusieurs recette");
                                }
                            } else {
                                JsfUtil.addErrorMessage("Cette formation sanitaire est liée à plusieurs infrastructures");
                            }
                        } else {
                            JsfUtil.addErrorMessage("Cette formation sanitaire est liée à plusieurs equipements");
                        }
                        
                    } else {
                        JsfUtil.addErrorMessage("Cette formation sanitaire est liée à plusieurs dépenses");
                    }
                } else {
                    JsfUtil.addErrorMessage("Cette formation sanitaire est liée à plusieurs activités");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.getStructures();
        }
    }
    
    public void filterByRegion() {
        try {
            districts.clear();
            if (region.getIdregion() != null) {
                districts = districtFacadeLocal.findByRegion(region.getIdregion());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void filterAire() {
        try {
            if (district.getIddistrict() != null) {
                district = districtFacadeLocal.find(district.getIddistrict());
                airesantes = airesanteFacadeLocal.find(district);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void filterRueByQuartier() {
        try {
            rues.clear();
            if (quartier != null) {
                rues = quartier.getRueList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public Structure getStructure() {
        return structure;
    }
    
    public void setStructure(Structure structure) {
        this.structure = structure;
    }
    
    public Structure getSelected() {
        return selected;
    }
    
    public void setSelected(Structure selected) {
        this.selected = selected;
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
    
    public Airesante getAiresante() {
        return airesante;
    }
    
    public void setAiresante(Airesante airesante) {
        this.airesante = airesante;
    }
    
    public List<Airesante> getAiresantes() {
        return airesantes;
    }
    
    public void setAiresantes(List<Airesante> airesantes) {
        this.airesantes = airesantes;
    }
    
    public Adresse getAdresse() {
        return adresse;
    }
    
    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }
    
    public boolean isDetail() {
        return detail;
    }
    
    public void setDetail(boolean detail) {
        this.detail = detail;
    }
    
    public Statutstructure getStatutstructure() {
        return statutstructure;
    }
    
    public void setStatutstructure(Statutstructure statutstructure) {
        this.statutstructure = statutstructure;
    }
    
    public List<Statutstructure> getStatutstructures() {
        statutstructures = statutstructureFacadeLocal.findAllRange();
        return statutstructures;
    }
    
    public void setStatutstructures(List<Statutstructure> statutstructures) {
        this.statutstructures = statutstructures;
    }
    
    public Quartier getQuartier() {
        return quartier;
    }
    
    public void setQuartier(Quartier quartier) {
        this.quartier = quartier;
    }
    
    public List<Quartier> getQuartiers() {
        quartiers = quartierFacadeLocal.findAll();
        return quartiers;
    }
    
    public void setQuartiers(List<Quartier> quartiers) {
        this.quartiers = quartiers;
    }
    
    public List<Rue> getRues() {
        rues = rueFacadeLocal.findAll();
        return rues;
    }
    
    public void setRues(List<Rue> rues) {
        this.rues = rues;
    }
    
    public Rue getRue() {
        return rue;
    }
    
    public void setRue(Rue rue) {
        this.rue = rue;
    }
    
    public Institution getInstitution() {
        return institution;
    }
    
    public void setInstitution(Institution institution) {
        this.institution = institution;
    }
    
    public List<Institution> getInstitutions() {
        institutions = institutionFacadeLocal.findAll();
        return institutions;
    }
    
    public void setInstitutions(List<Institution> institutions) {
        this.institutions = institutions;
    }
    
    public Region getRegion() {
        return region;
    }
    
    public void setRegion(Region region) {
        this.region = region;
    }
    
    public List<Region> getRegions() {
        regions = regionFacadeLocal.findAllRange();
        return regions;
    }
    
    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }
    
    public Niveaupyramide getNiveaupyramide() {
        return niveaupyramide;
    }
    
    public void setNiveaupyramide(Niveaupyramide niveaupyramide) {
        this.niveaupyramide = niveaupyramide;
    }
    
    public List<Niveaupyramide> getNiveaupyramides() {
        niveaupyramides = niveaupyramideFacadeLocal.findAll();
        return niveaupyramides;
    }
    
    public void setNiveaupyramides(List<Niveaupyramide> niveaupyramides) {
        this.niveaupyramides = niveaupyramides;
    }
    
}
