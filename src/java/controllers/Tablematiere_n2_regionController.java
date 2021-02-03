/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.TablematiereN2;
import entities.Tablematieren2Region;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessions.TablematiereN2FacadeLocal;
import sessions.Tablematieren2RegionFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@ViewScoped
public class Tablematiere_n2_regionController {

    @EJB
    private Tablematieren2RegionFacadeLocal tablematieren2RegionFacadeLocal;
    private List<Tablematieren2Region> tablematieren2Regions = new ArrayList<>();

    @EJB
    private TablematiereN2FacadeLocal tablematiereN2FacadeLocal;
    private List<TablematiereN2> tablematiereN2s = new ArrayList<>();

    private boolean detail = true;

    private String mode = "";

    /**
     * Creates a new instance of AxeController
     */
    public Tablematiere_n2_regionController() {
    }

    @PostConstruct
    private void init() {
        try {
            tablematiereN2s = tablematiereN2FacadeLocal.findByAllRange(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    }

    public void prepareEdit() {
        mode = "Edit";
    }

    public void create() {
        try {
            if (!tablematieren2Regions.isEmpty()) {
                for (Tablematieren2Region t : tablematieren2Regions) {
                    if (t.getIdtablematieren2Region() != null) {
                        tablematieren2RegionFacadeLocal.edit(t);
                    } else {
                        t.setIdtablematieren2Region(tablematieren2RegionFacadeLocal.nextId());
                        tablematieren2RegionFacadeLocal.create(t);
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
            tablematieren2Regions.clear();
            if (SessionMBean.getRegion() != null) {
                List<TablematiereN2> temp = tablematiereN2s;
                if (!temp.isEmpty()) {
                    for (TablematiereN2 t : temp) {
                        List<Tablematieren2Region> result = tablematieren2RegionFacadeLocal.findByRegionTableniveau2(t, SessionMBean.getRegion());
                        if (!result.isEmpty()) {
                            tablematieren2Regions.add(result.get(0));
                        } else {
                            Tablematieren2Region value = new Tablematieren2Region();
                            value.setIdregion(SessionMBean.getRegion());
                            if (t.getDefaultnumpage() != null) {
                                value.setNumeropage(t.getDefaultnumpage());
                            }
                            value.setIdtablematiereN2(t);
                            tablematieren2Regions.add(value);
                        }
                    }
                    if (tablematieren2Regions.isEmpty()) {
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

    public List<TablematiereN2> getTablematiereN2s() {
        return tablematiereN2s;
    }

    public void setTablematiereN2s(List<TablematiereN2> tablematiereN2s) {
        this.tablematiereN2s = tablematiereN2s;
    }

    public List<Tablematieren2Region> getTablematieren2Regions() {
        return tablematieren2Regions;
    }

    public void setTablematieren2Regions(List<Tablematieren2Region> tablematieren2Regions) {
        this.tablematieren2Regions = tablematieren2Regions;
    }

}
