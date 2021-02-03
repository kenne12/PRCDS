/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.Region;
import entities.SituationSocioculturel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessions.RegionFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.SituationSocioCulturelFacadeLocal;


/**
 *
 * @author kenne gervais
 */
@ManagedBean
@SessionScoped
public class SituationSocioCulturelleController {

    /**
     * Creates a new instance of SituationSocioculturelController
     */
    @EJB
    private MouchardFacadeLocal mouchardFacadeLocal;

    @EJB
    private SituationSocioCulturelFacadeLocal situationSocioCulturelFacadeLocal;
    private SituationSocioculturel situationSocioCulturel;
    private List<SituationSocioculturel> situationSocioCulturels = new ArrayList<>();
    private Boolean detail = true;

    @EJB
    private RegionFacadeLocal regionFacadeLocal;
    private Region region;
    private List<Region> regions = new ArrayList<>();
    private String msg = "";
    private boolean bouton = true;
    private boolean showPrintForm = true;
    private boolean selectModel = true;
    private Date date;
    
    private boolean isRegistred = false;

    public SituationSocioCulturelleController() {

    }

    @PostConstruct
    private void init() {

        situationSocioCulturel = new SituationSocioculturel();
        region = new Region();
        try {
            situationSocioCulturels = situationSocioCulturelFacadeLocal.findByRegion(SessionMBean.getRegion().getIdregion());
            if (!situationSocioCulturels.isEmpty()) {
                isRegistred = true;
                situationSocioCulturel = situationSocioCulturels.get(0);
            } else {
                isRegistred = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveSituationSocioculturel() {
        try {
            if (isRegistred) {
                situationSocioCulturelFacadeLocal.edit(situationSocioCulturel);

            } else {
                situationSocioCulturel.setIdsitutationScRegion(situationSocioCulturelFacadeLocal.nextId());
                situationSocioCulturel.setIdregion(SessionMBean.getRegion());
                situationSocioCulturelFacadeLocal.create(situationSocioCulturel);
                isRegistred = true;
                utilitaire.Utilitaires.saveOperation("Enregistrement de la situation SocioCulturel -> " + situationSocioCulturel.getEducation(), SessionMBean.getUser(), mouchardFacadeLocal);
                JsfUtil.addSuccessMessage("Operation réussie");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SituationSocioculturel getSituationSocioculturel() {
        return situationSocioCulturel;
    }

    public void setSituationSocioculturel(SituationSocioculturel situationSocioCulturel) {
        this.situationSocioCulturel = situationSocioCulturel;
    }

    public List<SituationSocioculturel> getSituationSocioculturels() {
        try {
            situationSocioCulturels = situationSocioCulturelFacadeLocal.findByRegion(SessionMBean.getRegion().getIdregion());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return situationSocioCulturels;
    }

    public void setSituationSocioculturels(List<SituationSocioculturel> situationSocioCulturels) {
        this.situationSocioCulturels = situationSocioCulturels;
    }

    public Boolean getDetail() {
        return detail;
    }

    public void setDetail(Boolean detail) {
        this.detail = detail;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isShowPrintForm() {
        return showPrintForm;
    }

    public void setShowPrintForm(boolean showPrintForm) {
        this.showPrintForm = showPrintForm;
    }

    public boolean isSelectModel() {
        return selectModel;
    }

    public void setSelectModel(boolean selectModel) {
        this.selectModel = selectModel;
    }

    public boolean isBouton() {
        return bouton;
    }

    public void setBouton(boolean bouton) {
        this.bouton = bouton;
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
