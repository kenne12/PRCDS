/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.Region;
import entities.ListetableauRegion;
import entities.Listetableau;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DualListModel;
import sessions.RegionFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.ListetableauRegionFacadeLocal;
import sessions.ListetableauFacadeLocal;
import utilitaire.Utilitaires;

/**
 *
 * @author kenne gervais
 */
@ManagedBean
@ViewScoped
public class ListetableauregionController {

    /**
     * Creates a new instance of ListetableauRegionController
     */
    @EJB
    private ListetableauRegionFacadeLocal listetableauregionFacadeLocal;
    @EJB
    private MouchardFacadeLocal mouchardFacadeLocal;

    private ListetableauRegion listetableauregion;
    private List<ListetableauRegion> listetableauregions = new ArrayList<>();
    private List<ListetableauRegion> listetableauregionTest = new ArrayList<>();

    private Boolean detail = true;
    @EJB
    private ListetableauFacadeLocal listetableauFacadeLocal;
    private Listetableau listetableau;
    private List<Listetableau> listetableaus = new ArrayList<>();

    @EJB
    private RegionFacadeLocal regionFacadeLocal;
    private Region region;
    private List<Region> regions = new ArrayList<>();
    private String mode = "";

    private DualListModel<Listetableau> dualList = new DualListModel<>();

    public ListetableauregionController() {

    }

    @PostConstruct
    private void init() {
        listetableauregion = new ListetableauRegion();
        listetableau = new Listetableau();

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
            listetableaus.clear();
            listetableauregionTest.clear();

            handleRegionCharge();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareEdit() {
        try {
            listetableaus = listetableauFacadeLocal.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // methode qui raffraichit la pickLKist
    public void handleRegionCharge() {
        try {

            dualList.getTarget().clear();

            List<Listetableau> listetableaus = listetableauFacadeLocal.findAll();
            List<ListetableauRegion> acteurregions = listetableauregionFacadeLocal.findByRegion(SessionMBean.getRegion());

            if (acteurregions.isEmpty()) {
                if (!listetableaus.isEmpty()) {
                    dualList.setSource(listetableaus);
                }
            } else {

                dualList.getSource().clear();
                //les acteur que le region possede
                List<Listetableau> acteurs1 = new ArrayList<>();

                for (ListetableauRegion a : acteurregions) {
                    acteurs1.add(a.getIdlistetableau());
                }

                for (Listetableau a : listetableaus) {
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
                List<Listetableau> test = dualList.getTarget();
                for (int i = 0; i < test.size(); i++) {
                    ListetableauRegion test1 = new ListetableauRegion();
                    test1.setIdlistetableau(test.get(i));
                    test1.setIdregion(SessionMBean.getRegion());
                    test1.setNumpage(0);
                    listetableauregionTest.add(test1);
                }
            } else {
                JsfUtil.addErrorMessage("Aucune Listetableau  sélectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            if (!listetableauregionTest.isEmpty()) {
                for (ListetableauRegion e : listetableauregionTest) {
                    e.setIdlistetableauRegion(listetableauregionFacadeLocal.nextId());
                    e.setIdregion(SessionMBean.getRegion());
                    listetableauregionFacadeLocal.create(e);
                }
                JsfUtil.addSuccessMessage("Opération réussie");
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit() {
        try {
            if (listetableauregion != null) {
                listetableauregionFacadeLocal.edit(listetableauregion);
                Utilitaires.saveOperation("Modification de la valeur listetableau explotation : " + listetableauregion.getIdlistetableauRegion(), SessionMBean.getUser(), mouchardFacadeLocal);
                JsfUtil.addSuccessMessage(" mis à jour reussi !");
            } else {
                JsfUtil.addErrorMessage("Aucune ligne sélectionée ! ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteListetableauRegion() {
        try {
            if (listetableauregion != null) {

                listetableauregionFacadeLocal.remove(listetableauregion);
                Utilitaires.saveOperation("Suppression du listetableauregion -> " + listetableauregion.getNumpage(), SessionMBean.getUser(), mouchardFacadeLocal);

            } else {
                JsfUtil.addErrorMessage("Aucun listetableauregion selectionné !");
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

    public ListetableauRegion getListetableauRegion() {
        return listetableauregion;
    }

    public void setListetableauRegion(ListetableauRegion listetableauregion) {
        this.listetableauregion = listetableauregion;
    }

    public List<ListetableauRegion> getListetableauRegions() {
        try {
            listetableauregions = listetableauregionFacadeLocal.findByRegion(SessionMBean.getRegion().getIdregion());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listetableauregions;
    }

    public void setListetableauRegions(List<ListetableauRegion> listetableauregions) {
        this.listetableauregions = listetableauregions;
    }

    public Boolean getDetail() {
        return detail;
    }

    public void setDetail(Boolean detail) {
        this.detail = detail;
    }

    public Listetableau getListetableau() {
        return listetableau;
    }

    public void setListetableau(Listetableau listetableau) {
        this.listetableau = listetableau;
    }

    public List<Listetableau> getListetableaus() {

        listetableaus = listetableauFacadeLocal.findAll();

        return listetableaus;
    }

    public void setListetableaus(List<Listetableau> listetableaus) {
        this.listetableaus = listetableaus;
    }

    public DualListModel<Listetableau> getDualList() {
        return dualList;
    }

    public void setDualList(DualListModel<Listetableau> dualList) {
        this.dualList = dualList;
    }

    public List<ListetableauRegion> getListetableauRegionTest() {
        listetableauregions = listetableauregionFacadeLocal.findAll();
        return listetableauregionTest;
    }

    public void setListetableauRegionTest(List<ListetableauRegion> listetableauregionTest) {
        this.listetableauregionTest = listetableauregionTest;
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
