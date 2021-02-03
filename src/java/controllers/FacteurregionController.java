/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.Region;
import entities.FacteurRegion;
import entities.Facteur;
import entities.Groupefacteur;
import entities.Mouchard;
import entities.Typefacteur;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DualListModel;
import sessions.RegionFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.FacteurRegionFacadeLocal;
import sessions.FacteurFacadeLocal;
import sessions.GroupefacteurFacadeLocal;
import sessions.TypefacteurFacadeLocal;
import utilitaire.Utilitaires;

/**
 *
 * @author kenne gervais
  */
@ManagedBean
@ViewScoped
public class FacteurregionController {

    /**
     * Creates a new instance of FacteurRegionController
      */
    @EJB
    private FacteurRegionFacadeLocal facteurRegionFacadeLocal;
    @EJB
    private MouchardFacadeLocal mouchardFacadeLocal;
    private Mouchard mouchard;

    private FacteurRegion facteurRegion;
    private List<FacteurRegion> facteurRegions = new ArrayList<>();
    private List<FacteurRegion> facteurRegionTest = new ArrayList<>();

    private Boolean detail = true;
    @EJB
    private FacteurFacadeLocal facteurFacadeLocal;
    private Facteur facteur;
    private List<Facteur> facteurs = new ArrayList<>();

    @EJB
    private TypefacteurFacadeLocal typefacteurFacadeLocal;
    private Typefacteur typefacteur;
    private List<Typefacteur> typefacteurs = new ArrayList<>();

    @EJB
    private GroupefacteurFacadeLocal groupefacteurFacadeLocal;
    private Groupefacteur groupefacteur = new Groupefacteur();
    private List<Groupefacteur> groupefacteurs = new ArrayList<>();

    @EJB
    private RegionFacadeLocal regionFacadeLocal;
    private Region region;
    private List<Region> regions = new ArrayList<>();
    private String mode = "";

    
    private DualListModel<Facteur> dualList = new DualListModel<>();

    public FacteurregionController() {

    }

    @PostConstruct
    private void init() {
        facteurRegion = new FacteurRegion();
        facteur = new Facteur();
        groupefacteur =  new Groupefacteur();
        typefacteur =  new  Typefacteur();
        typefacteurs = typefacteurFacadeLocal.findAll();
        groupefacteurs =  groupefacteurFacadeLocal.findAll();

    }

    public void activeButton() {
        detail = false;
    }

    public void deactiveButton() {
        detail = true;
    }

    public void prepareCreate() {
        try {
            dualList = new DualListModel<>();
            facteurs.clear();
            facteurRegionTest.clear();
            
            handleRegionCharge();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareEdit() {
       mode = "Edit";
        try {
            if (facteurRegion != null) {
                if (facteurRegion.getIdfacteur() != null) {
                    facteur = facteurRegion.getIdfacteur();
                }
                if (facteurRegion.getIdfacteur().getIdgroupefacteur() != null) {
                    groupefacteur = facteurRegion.getIdfacteur().getIdgroupefacteur();
                }

                 if (facteurRegion.getIdfacteur().getIdtypefacteur() != null) {
                    typefacteur = facteurRegion.getIdfacteur().getIdtypefacteur();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

          
    }

    // methode qui raffraichit la pickLKist
    public void handleRegionCharge() {
        try {

            
            dualList.getTarget().clear();

            List<Facteur> facteurs = facteurFacadeLocal.findAll();
            List<FacteurRegion> acteurregions = facteurRegionFacadeLocal.findByRegion(SessionMBean.getRegion());

            if (acteurregions.isEmpty()) {
                if (!facteurs.isEmpty()) {
                    dualList.setSource(facteurs);
                }
            } else {

                dualList.getSource().clear();
                //les acteur que le region possede
                List<Facteur> acteurs1 = new ArrayList<>();

                for (FacteurRegion a : acteurregions) {
                    acteurs1.add(a.getIdfacteur());
                }

                for (Facteur a : facteurs) {
                    if (!acteurs1.contains(a)) {
                        dualList.getSource().add(a);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //methode qui transfert les données de la pickList vers le tableau
    public void transfertEtape() {
        try {
            if (!dualList.getTarget().isEmpty()) {
                List<Facteur> test = dualList.getTarget();
                for (int i = 0; i < test.size(); i++) {
                    FacteurRegion test1 = new FacteurRegion();
                    test1.setIdfacteur(test.get(i));
                    test1.setIdregion(SessionMBean.getRegion());
                    test1.setObservation("");
                    facteurRegionTest.add(test1);
                }
            } else {
                JsfUtil.addErrorMessage("Aucune Facteur  sélectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            if (!facteurRegionTest.isEmpty()) {
                for (FacteurRegion e : facteurRegionTest) {
                    e.setIdfacteurRegion(facteurRegionFacadeLocal.nextId());
                    e.setIdregion(SessionMBean.getRegion());
                    facteurRegionFacadeLocal.create(e);
                }
                JsfUtil.addSuccessMessage("Opération réussie");
            } else {
                JsfUtil.addWarningMessage("Tablau vide");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

      public void edit() {
        try {
            if (facteurRegion.getIdfacteurRegion() != null) {
                FacteurRegion r = facteurRegionFacadeLocal.find(facteurRegion.getIdfacteurRegion());
                facteurRegionFacadeLocal.edit(facteurRegion);
                mouchard.setAction("Modification du region ->  " + r.getObservation()+ " -> " + facteurRegion.getObservation());
                mouchard.setIdutilisateur(SessionMBean.getUser());
                mouchard.setDateaction(new Date());
                mouchardFacadeLocal.create(mouchard);

                JsfUtil.addSuccessMessage("Le region a été mis à jour");
            } else {
                JsfUtil.addErrorMessage("Veuillez selectionner une ligne");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.getRegions();
        }
    }
   /* public void edit() {
        try {
            if (facteurRegion != null) {
                facteurRegionFacadeLocal.edit(facteurRegion);
                Utilitaires.saveOperation("Modification de la valeur facteur explotation : " + facteurRegion.getIdfacteurRegion(), SessionMBean.getUser(), mouchardFacadeLocal);
                JsfUtil.addSuccessMessage(" mis à jour reussi !");
            } else {
                JsfUtil.addErrorMessage("Aucune ligne sélectionée ! ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
     */

    public void deleteFacteurRegion() {
        try {
            if (facteurRegion != null) {

                facteurRegionFacadeLocal.remove(facteurRegion);
                Utilitaires.saveOperation("Suppression du facteurRegion -> " + facteurRegion.getObservation(), SessionMBean.getUser(), mouchardFacadeLocal);

            } else {
                JsfUtil.addErrorMessage("Aucun facteurRegion selectionné !");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MouchardFacadeLocal getMouchardFacadeLocal() {
        return mouchardFacadeLocal;
    }

    public void setMouchardFacadeLocal(MouchardFacadeLocal mouchardFacadeLocal) {
        this.mouchardFacadeLocal = mouchardFacadeLocal;
    }

    public FacteurRegion getFacteurRegion() {
        return facteurRegion;
    }

    public void setFacteurRegion(FacteurRegion facteurRegion) {
        this.facteurRegion = facteurRegion;
    }

    public List<FacteurRegion> getFacteurRegions() {
        try {
            facteurRegions = facteurRegionFacadeLocal.findByRegion(SessionMBean.getRegion().getIdregion());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return facteurRegions;
    }

    public void setFacteurRegions(List<FacteurRegion> facteurRegions) {
        this.facteurRegions = facteurRegions;
    }

    public Boolean getDetail() {
        return detail;
    }

    public void setDetail(Boolean detail) {
        this.detail = detail;
    }

    public Facteur getFacteur() {
        return facteur;
    }

    public void setFacteur(Facteur facteur) {
        this.facteur = facteur;
    }

    public List<Facteur> getFacteurs() {

        facteurs = facteurFacadeLocal.findAll();

        return facteurs;
    }

    public void setFacteurs(List<Facteur> facteurs) {
        this.facteurs = facteurs;
    }

    public DualListModel<Facteur> getDualList() {
        return dualList;
    }

    public void setDualList(DualListModel<Facteur> dualList) {
        this.dualList = dualList;
    }

    public List<FacteurRegion> getFacteurRegionTest() {
        facteurRegions = facteurRegionFacadeLocal.findAll();
        return facteurRegionTest;
    }

    public void setFacteurRegionTest(List<FacteurRegion> facteurRegionTest) {
        this.facteurRegionTest = facteurRegionTest;
    }

    public Typefacteur getTypefacteur() {
        return typefacteur;
    }

    public void setTypefacteur(Typefacteur typefacteur) {
        this.typefacteur = typefacteur;
    }

    public List<Typefacteur> getTypefacteurs() {
        typefacteurs = typefacteurFacadeLocal.findAll();
        return typefacteurs;
    }

    public void setTypefacteurs(List<Typefacteur> typefacteurs) {
        this.typefacteurs = typefacteurs;
    }

    public Groupefacteur getGroupefacteur() {
        return groupefacteur;
    }

    public void setGroupefacteur(Groupefacteur groupefacteur) {
        this.groupefacteur = groupefacteur;
    }

    public List<Groupefacteur> getGroupefacteurs() {
        groupefacteurs = groupefacteurFacadeLocal.findAll();
        return groupefacteurs;
    }

    public void setGroupefacteurs(List<Groupefacteur> groupefacteurs) {
        this.groupefacteurs = groupefacteurs;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

}
