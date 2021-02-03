/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.Region;
import entities.ActeurRegion;
import entities.Acteur;
import entities.Groupeacteur;
import entities.Mouchard;
import entities.Typeacteur;
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
import sessions.ActeurRegionFacadeLocal;
import sessions.ActeurFacadeLocal;
import sessions.GroupeacteurFacadeLocal;
import sessions.TypeacteurFacadeLocal;
import utilitaire.Utilitaires;

/**
 *
 * @author kenne gervais
 */
@ManagedBean
@ViewScoped
public class ActeurregionController {

    /**
     * Creates a new instance of ActeurRegionController
     */
    @EJB
    private ActeurRegionFacadeLocal acteurRegionFacadeLocal;

    private ActeurRegion acteurRegion = new ActeurRegion();
    private List<ActeurRegion> acteurRegions = new ArrayList<>();
    private List<ActeurRegion> acteurRegionTest = new ArrayList<>();

    @EJB
    private MouchardFacadeLocal mouchardFacadeLocal;
    private Mouchard mouchard = new Mouchard();

    private Boolean detail = true;

    @EJB
    private GroupeacteurFacadeLocal groupeacteurFacadeLocal;
    private Groupeacteur groupeacteur = new Groupeacteur();
    private List<Groupeacteur> groupeacteurs = new ArrayList<>();

    @EJB
    private TypeacteurFacadeLocal typeacteurFacadeLocal;
    private Typeacteur typeacteur = new Typeacteur();
    private List<Typeacteur> typeacteurs = new ArrayList<>();

    @EJB
    private RegionFacadeLocal regionFacadeLocal;
    private Region region;
    private List<Region> regions = new ArrayList<>();

    @EJB
    private ActeurFacadeLocal acteurFacadeLocal;
    private Acteur acteur;
    private List<Acteur> acteurs = new ArrayList<>();

    private String mode = "";

    private DualListModel<Acteur> dualList = new DualListModel<>();

    public ActeurregionController() {

    }

    @PostConstruct
    private void init() {
        acteurRegion = new ActeurRegion();
        region = new Region();
        acteur = new Acteur();
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
            region = new Region();
            acteurs.clear();
            acteurRegionTest.clear();
            handleRegionCharge();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareEdit() {
        try {
            acteurs = acteurFacadeLocal.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // methode qui raffraichit la pickLKist
    public void handleRegionCharge() {
        try {
            acteurRegionTest.clear();

            dualList.getTarget().clear();
            List<Acteur> acteurs = acteurFacadeLocal.findAll();
            List<ActeurRegion> acteurRegions = acteurRegionFacadeLocal.findByRegion(SessionMBean.getRegion());

            if (acteurRegions.isEmpty()) {
                if (!acteurs.isEmpty()) {
                    dualList.setSource(acteurs);
                }
            } else {

                dualList.getSource().clear();
                //les acteur que le region possede
                List<Acteur> acteurs1 = new ArrayList<>();

                for (ActeurRegion a : acteurRegions) {
                    acteurs1.add(a.getIdacteur());
                }

                for (Acteur a : acteurs) {
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
                List<Acteur> test = dualList.getTarget();
                for (int i = 0; i < test.size(); i++) {
                    ActeurRegion test1 = new ActeurRegion();
                    test1.setIdregion(SessionMBean.getRegion());
                    test1.setIdacteur(test.get(i));
                    test1.setObservation("");
                    acteurRegionTest.add(test1);
                }
            } else {
                JsfUtil.addErrorMessage("Aucune Acteur  sélectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            if (!acteurRegionTest.isEmpty()) {
                for (ActeurRegion e : acteurRegionTest) {
                    e.setIdacteurRegion(acteurRegionFacadeLocal.nextId());
                    acteurRegionFacadeLocal.create(e);
                }
                JsfUtil.addSuccessMessage("Opération réussie");
            } else {
                JsfUtil.addWarningMessage("Tableau vide");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit() {
        try {
            if (acteurRegion.getIdacteurRegion() != null) {
                ActeurRegion r = acteurRegionFacadeLocal.find(acteurRegion.getIdacteurRegion());
                acteurRegionFacadeLocal.edit(acteurRegion);
                mouchard.setIdoperation(mouchardFacadeLocal.nextId());
                mouchard.setAction("Modification du facteur region ->  " + r.getObservation() + " -> " + acteurRegion.getObservation());
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
     if (acteurRegion != null) {
     acteurRegionFacadeLocal.edit(acteurRegion);
     Utilitaires.saveOperation("Modification de la valeur acteur explotation : " + acteurRegion.getIdacteurRegion(), SessionMBean.getUser(), mouchardFacadeLocal);
     JsfUtil.addSuccessMessage(" mis à jour reussi !");
     } else {
     JsfUtil.addErrorMessage("Aucune ligne sélectionée ! ");
     }
     } catch (Exception e) {
     e.printStackTrace();
     }
     }
     */
    public void deleteActeurRegion() {
        try {
            if (acteurRegion != null) {

                acteurRegionFacadeLocal.remove(acteurRegion);
                Utilitaires.saveOperation("Suppression du acteurRegion -> " + acteurRegion.getObservation(), SessionMBean.getUser(), mouchardFacadeLocal);

            } else {
                JsfUtil.addErrorMessage("Aucun acteurRegion selectionné !");
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

    public ActeurRegion getActeurRegion() {
        return acteurRegion;
    }

    public void setActeurRegion(ActeurRegion acteurRegion) {
        this.acteurRegion = acteurRegion;
    }

    public List<ActeurRegion> getActeurRegions() {
        try {
            acteurRegions = acteurRegionFacadeLocal.findByRegion(SessionMBean.getRegion());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return acteurRegions;
    }

    public void setActeurRegions(List<ActeurRegion> acteurRegions) {
        this.acteurRegions = acteurRegions;
    }

    public Boolean getDetail() {
        return detail;
    }

    public void setDetail(Boolean detail) {
        this.detail = detail;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Region> getRegions() {
        regions = regionFacadeLocal.findAll();
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public Acteur getActeur() {
        return acteur;
    }

    public void setActeur(Acteur acteur) {
        this.acteur = acteur;
    }

    public List<Acteur> getActeurs() {

        acteurs = acteurFacadeLocal.findAll();

        return acteurs;
    }

    public void setActeurs(List<Acteur> acteurs) {
        this.acteurs = acteurs;
    }

    public DualListModel<Acteur> getDualList() {
        return dualList;
    }

    public void setDualList(DualListModel<Acteur> dualList) {
        this.dualList = dualList;
    }

    public List<ActeurRegion> getActeurRegionTest() {
        acteurRegions = acteurRegionFacadeLocal.findAll();
        return acteurRegionTest;
    }

    public void setActeurRegionTest(List<ActeurRegion> acteurRegionTest) {
        this.acteurRegionTest = acteurRegionTest;
    }

    public Groupeacteur getGroupeacteur() {
        return groupeacteur;
    }

    public void setGroupeacteur(Groupeacteur groupeacteur) {
        this.groupeacteur = groupeacteur;
    }

    public List<Groupeacteur> getGroupeacteurs() {
        groupeacteurs = groupeacteurFacadeLocal.findAll();
        return groupeacteurs;
    }

    public void setGroupeacteurs(List<Groupeacteur> groupeacteurs) {
        this.groupeacteurs = groupeacteurs;
    }

    public Typeacteur getTypeacteur() {
        return typeacteur;
    }

    public void setTypeacteur(Typeacteur typeacteur) {
        this.typeacteur = typeacteur;
    }

    public List<Typeacteur> getTypeacteurs() {
        typeacteurs = typeacteurFacadeLocal.findAll();
        return typeacteurs;
    }

    public void setTypeacteurs(List<Typeacteur> typeacteurs) {
        this.typeacteurs = typeacteurs;
    }

}
