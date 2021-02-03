/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.TablematiereN1;
import entities.Tablematieren1Region;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessions.TablematiereN1FacadeLocal;
import sessions.Tablematieren1RegionFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@ViewScoped
public class Tablematiere_n1_regionController {

    @EJB
    private Tablematieren1RegionFacadeLocal tablematieren1RegionFacadeLocal;
    private Tablematieren1Region tablematieren1Region = new Tablematieren1Region();
    private List<Tablematieren1Region> tablematieren1Regions = new ArrayList<>();

    @EJB
    private TablematiereN1FacadeLocal tablematiereN1FacadeLocal;
    private TablematiereN1 tablematiereN1 = new TablematiereN1();
    private List<TablematiereN1> tablematiereN1s = new ArrayList<>();

    private boolean detail = true;

    private String mode = "";

    /**
     * Creates a new instance of AxeController
     */
    public Tablematiere_n1_regionController() {

    }

    @PostConstruct
    private void init() {
        tablematiereN1 = new TablematiereN1();
        this.loadData();
    }

    public void prepare() {

    }

    public void activeButton() {
        detail = false;
    }

    public void deactiveButton() {
        detail = true;
    }

    public void prepareCreate() {
        mode = "Create";
        try {
            tablematiereN1 = new TablematiereN1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareEdit() {
        mode = "Edit";
    }

    public void create() {
        try {
            if (!tablematieren1Regions.isEmpty()) {
                for (Tablematieren1Region t : tablematieren1Regions) {
                    if (t.getIdtablematieren1Region() != null) {
                        tablematieren1RegionFacadeLocal.edit(t);
                    } else {
                        t.setIdtablematieren1Region(tablematieren1RegionFacadeLocal.nextId());
                        tablematieren1RegionFacadeLocal.create(t);
                    }
                }
                this.loadData();
                JsfUtil.addSuccessMessage("Opération réussie");
            } else {
                JsfUtil.addErrorMessage("Le tableau est vide");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            if (tablematiereN1 != null) {
                if (tablematiereN1.getTablematiereN2List().isEmpty()) {
                    tablematiereN1FacadeLocal.remove(tablematiereN1);
                }
                JsfUtil.addSuccessMessage("Opération réussie");
            } else {
                JsfUtil.addErrorMessage("Aucune ligne selectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        try {
            tablematieren1Regions.clear();
            if (SessionMBean.getRegion() != null) {
                List<TablematiereN1> temp = getTablematiereN1s();
                if (!this.getTablematiereN1s().isEmpty()) {
                    for (TablematiereN1 t : temp) {
                        List<Tablematieren1Region> result = tablematieren1RegionFacadeLocal.findByRegionTableniveau1(t, SessionMBean.getRegion());
                        if (!result.isEmpty()) {
                            tablematieren1Regions.add(result.get(0));
                        } else {
                            Tablematieren1Region value = new Tablematieren1Region();
                            value.setIdregion(SessionMBean.getRegion());
                            if (t.getDefaultnumpage() != null) {
                                value.setNumeropage(t.getDefaultnumpage());
                            }
                            value.setIdtablematiereN1(t);
                            tablematieren1Regions.add(value);
                        }
                    }
                    if (tablematieren1Regions.isEmpty()) {
                        detail = true;
                    } else {
                        detail = false;
                    }
                }
            }
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

    public TablematiereN1 getTablematiereN1() {
        return tablematiereN1;
    }

    public void setTablematiereN1(TablematiereN1 tablematiereN1) {
        this.tablematiereN1 = tablematiereN1;
    }

    public List<TablematiereN1> getTablematiereN1s() {
        try {
            tablematiereN1s = tablematiereN1FacadeLocal.findAllRange(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tablematiereN1s;
    }

    public void setTablematiereN1s(List<TablematiereN1> tablematiereN1s) {
        this.tablematiereN1s = tablematiereN1s;
    }

    public Tablematieren1Region getTablematieren1Region() {
        return tablematieren1Region;
    }

    public void setTablematieren1Region(Tablematieren1Region tablematieren1Region) {
        this.tablematieren1Region = tablematieren1Region;
    }

    public List<Tablematieren1Region> getTablematieren1Regions() {
        return tablematieren1Regions;
    }

    public void setTablematieren1Regions(List<Tablematieren1Region> tablematieren1Regions) {
        this.tablematieren1Regions = tablematieren1Regions;
    }

}
