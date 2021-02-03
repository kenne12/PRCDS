/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.TablematiereN3;
import entities.Tablematieren1District;
import entities.Tablematieren2District;
import entities.Tablematieren3Region;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessions.TablematiereN3FacadeLocal;
import sessions.Tablematieren1DistrictFacadeLocal;
import sessions.Tablematieren3DistrictFacadeLocal;
import sessions.Tablematieren3RegionFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@ViewScoped
public class Tablematiere_n3_regionController {

    @EJB
    private Tablematieren3RegionFacadeLocal tablematieren3RegionFacadeLocal;
    private Tablematieren3Region tablematieren3Region = new Tablematieren3Region();
    private List<Tablematieren3Region> tablematieren3Regions = new ArrayList<>();

    @EJB
    private Tablematieren3DistrictFacadeLocal tablematierenDistrictFacadeLocal;
    private Tablematieren2District tablematieren2District = new Tablematieren2District();
    private List<Tablematieren2District> tablematieren2Districts = new ArrayList<>();

    @EJB
    private Tablematieren1DistrictFacadeLocal tablematieren1DistrictFacadeLocal;
    private Tablematieren1District tablematieren1District = new Tablematieren1District();
    private List<Tablematieren1District> tablematieren1Districts = new ArrayList<>();

    @EJB
    private TablematiereN3FacadeLocal tablematiereN3FacadeLocal;
    private TablematiereN3 tablematiereN3 = new TablematiereN3();
    private List<TablematiereN3> tablematiereN3s = new ArrayList<>();

    private boolean detail = true;

    private String mode = "";

    /**
     * Creates a new instance of AxeController
     */
    public Tablematiere_n3_regionController() {
    }

    @PostConstruct
    private void init() {
        tablematiereN3 = new TablematiereN3();
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
            tablematiereN3 = new TablematiereN3();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareEdit() {
        mode = "Edit";
    }

    public void create() {
        try {
            if (!tablematieren3Regions.isEmpty()) {
                for (Tablematieren3Region t : tablematieren3Regions) {
                    if (t.getIdtablematieren3Region() != null) {
                        tablematieren3RegionFacadeLocal.edit(t);
                    } else {
                        t.setIdtablematieren3Region(tablematieren3RegionFacadeLocal.nextId());
                        tablematieren3RegionFacadeLocal.create(t);
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

    }

    public void loadData() {
        try {
            tablematieren3Regions.clear();
            if (SessionMBean.getRegion() != null) {
                List<TablematiereN3> temp = getTablematiereN3s();
                if (!temp.isEmpty()) {
                    for (TablematiereN3 t : temp) {
                        List<Tablematieren3Region> result = tablematieren3RegionFacadeLocal.findByRegionTableniveau3(t, SessionMBean.getRegion());
                        if (!result.isEmpty()) {
                            tablematieren3Regions.add(result.get(0));
                        } else {
                            Tablematieren3Region value = new Tablematieren3Region();
                            value.setIdregion(SessionMBean.getRegion());
                            if (t.getDefaultnumpage() != null) {
                                value.setNumeropage(t.getDefaultnumpage());
                            }
                            value.setIdtablematiereN3(t);
                            tablematieren3Regions.add(value);
                        }
                    }
                    if (tablematieren3Regions.isEmpty()) {
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

    public TablematiereN3 getTablematiereN3() {
        return tablematiereN3;
    }

    public void setTablematiereN3(TablematiereN3 tablematiereN3) {
        this.tablematiereN3 = tablematiereN3;
    }

    public List<TablematiereN3> getTablematiereN3s() {
        try {
            tablematiereN3s = tablematiereN3FacadeLocal.findByAllRange(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tablematiereN3s;
    }

    public void setTablematiereN3s(List<TablematiereN3> tablematiereN3s) {
        this.tablematiereN3s = tablematiereN3s;
    }

    public Tablematieren1District getTablematieren1District() {
        return tablematieren1District;
    }

    public void setTablematieren1District(Tablematieren1District tablematieren1District) {
        this.tablematieren1District = tablematieren1District;
    }

    public List<Tablematieren1District> getTablematieren1Districts() {
        return tablematieren1Districts;
    }

    public void setTablematieren1Districts(List<Tablematieren1District> tablematieren1Districts) {
        this.tablematieren1Districts = tablematieren1Districts;
    }

    public Tablematieren2District getTablematieren2District() {
        return tablematieren2District;
    }

    public void setTablematieren2District(Tablematieren2District tablematieren2District) {
        this.tablematieren2District = tablematieren2District;
    }

    public List<Tablematieren2District> getTablematieren2Districts() {
        return tablematieren2Districts;
    }

    public void setTablematieren2Districts(List<Tablematieren2District> tablematieren2Districts) {
        this.tablematieren2Districts = tablematieren2Districts;
    }

    public Tablematieren3Region getTablematieren3Region() {
        return tablematieren3Region;
    }

    public void setTablematieren3Region(Tablematieren3Region tablematieren3Region) {
        this.tablematieren3Region = tablematieren3Region;
    }

    public List<Tablematieren3Region> getTablematieren3Regions() {
        return tablematieren3Regions;
    }

    public void setTablematieren3Regions(List<Tablematieren3Region> tablematieren3Regions) {
        this.tablematieren3Regions = tablematieren3Regions;
    }

}
