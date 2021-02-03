/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.Region;
import entities.Utilisateur;
import entities.UtilisateurRegion;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DualListModel;
import sessions.MouchardFacadeLocal;
import sessions.RegionFacadeLocal;
import sessions.UtilisateurFacadeLocal;
import sessions.UtilisateurRegionFacadeLocal;
import utilitaire.Utilitaires;

/**
 *
 * @author kenne gervais
 */
@ManagedBean
@ViewScoped
public class PrivilegeRegionController {

    @EJB
    private UtilisateurRegionFacadeLocal utilisateurRegionFacadeLocal;
    private UtilisateurRegion privilege = new UtilisateurRegion();
    private List<UtilisateurRegion> privileges = new ArrayList<>();
    private DualListModel<UtilisateurRegion> privilegeDualList = new DualListModel<>();

    @EJB
    private UtilisateurFacadeLocal utilisateurFacadeLocal;
    private Utilisateur utilisateur;
    private List<Utilisateur> utilisateurs = new ArrayList<>();

    @EJB
    private RegionFacadeLocal regionFacadeLocal;
    private Region region = new Region();
    private List<Region> regions = new ArrayList<>();
    private DualListModel<Region> dualList = new DualListModel<>();

    private boolean detail = true;

    @EJB
    private MouchardFacadeLocal mouchardFacadeLocal;

    /**
     * Creates a new instance of PrivilegeDistrictController
     */
    public PrivilegeRegionController() {

    }

    public void activeButton() {
        detail = false;
    }

    public void deactiveButton() {
        detail = true;
    }

    public void prepareCreate() {
        utilisateur = new Utilisateur();
        dualList.getSource().clear();
        dualList.getTarget().clear();
        privilege = new UtilisateurRegion();
        privilegeDualList.getSource().clear();
        privilegeDualList.getTarget().clear();
    }

    public void prepareEdit() {

    }

    public void save() {
        try {
            if (utilisateur != null) {

                if (!dualList.getTarget().isEmpty()) {
                    for (Region r : dualList.getTarget()) {
                        UtilisateurRegion utilisateurdistrictTemp = utilisateurRegionFacadeLocal.findByUtilisateurRegion(utilisateur.getIdutilisateur(), r.getIdregion());
                        if (utilisateurdistrictTemp == null) {
                            UtilisateurRegion u = new UtilisateurRegion();
                            u.setIdutilisateurRegion(utilisateurRegionFacadeLocal.nextId());
                            u.setIdregion(r);
                            u.setIdutilisateur(utilisateur);
                            utilisateurRegionFacadeLocal.create(u);
                            Utilitaires.saveOperation("Permission d'accès au  -> " + r.getNomFr() + " A l'utilisateur -> " + utilisateur.getNom() + "" + utilisateur.getPrenom(), SessionMBean.getUser(), mouchardFacadeLocal);
                        }
                    }
                }

                if (!dualList.getSource().isEmpty()) {
                    for (Region r : dualList.getSource()) {
                        UtilisateurRegion utilisateurdistrictTemp = utilisateurRegionFacadeLocal.findByUtilisateurRegion(utilisateur.getIdutilisateur(), r.getIdregion());
                        if (utilisateurdistrictTemp != null) {
                            utilisateurRegionFacadeLocal.remove(utilisateurdistrictTemp);
                            Utilitaires.saveOperation("Retrait d'accès au  -> " + r.getNomFr() + " A l'utilisateur -> " + utilisateur.getNom() + "" + utilisateur.getPrenom(), SessionMBean.getUser(), mouchardFacadeLocal);
                        }
                    }
                }

                JsfUtil.addSuccessMessage("Opération réussie");

            } else {
                JsfUtil.addErrorMessage("Aucun utilisateur selectionné !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit() {

    }

    public void delete() {
        try {
            if (privilege != null) {
                utilisateurRegionFacadeLocal.remove(privilege);
                JsfUtil.addSuccessMessage("Operation réussie !");
            } else {
                JsfUtil.addSuccessMessage("Aucune ligne selectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filterUtilisateurDistrict() {
        try {
            dualList.getSource().clear();
            dualList.getTarget().clear();
            if (utilisateur != null) {

                List<UtilisateurRegion> privilegeTemps = utilisateurRegionFacadeLocal.findByUtilisateur(utilisateur.getIdutilisateur());

                if (privilegeTemps.isEmpty()) {
                    dualList.setSource(regionFacadeLocal.findAll());
                    dualList.getTarget().clear();
                } else {
                    List<Region> regionTempAll = regionFacadeLocal.findAll();
                    List<Region> userRegions = new ArrayList<>();

                    for (UtilisateurRegion u : privilegeTemps) {
                        userRegions.add(u.getIdregion());
                    }

                    dualList.setTarget(userRegions);
                    dualList.setSource(regionTempAll);
                    dualList.getSource().removeAll(userRegions);

                }
            } else {
                JsfUtil.addErrorMessage("Aucun utilisateur selectionné");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filterPrivilegeRetrait() {
        privilegeDualList.getSource().clear();
        privilegeDualList.getTarget().clear();
        if (utilisateur != null) {
            privilegeDualList.setSource(utilisateurRegionFacadeLocal.findByUtilisateur(utilisateur.getIdutilisateur()));
        }
    }

    public void retraitPrivilege() {
        for (UtilisateurRegion p : privilegeDualList.getTarget()) {
            if (!privilegeDualList.getTarget().isEmpty()) {
                utilisateurRegionFacadeLocal.remove(p);
                Utilitaires.saveOperation("Retrait du privilège -> " + p.getIdregion().getNomFr() + " A l'utilisateur -> " + p.getIdutilisateur().getNom() + " " + p.getIdutilisateur().getPrenom(), SessionMBean.getUser(), mouchardFacadeLocal);
            }
        }
        JsfUtil.addSuccessMessage("Opération réussie");
    }

    public boolean isDetail() {
        return detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    public List<UtilisateurRegion> getPrivileges() {
        privileges = utilisateurRegionFacadeLocal.findAllRange();
        return privileges;
    }

    public void setPrivilges(List<UtilisateurRegion> privilges) {
        this.privileges = privilges;
    }

    public UtilisateurRegion getPrivilege() {
        return privilege;
    }

    public void setPrivilege(UtilisateurRegion privilege) {
        this.privilege = privilege;
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

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Utilisateur> getUtilisateurs() {
        utilisateurs = utilisateurFacadeLocal.findAll();
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public DualListModel<UtilisateurRegion> getPrivilegeDualList() {
        return privilegeDualList;
    }

    public void setPrivilegeDualList(DualListModel<UtilisateurRegion> privilegeDualList) {
        this.privilegeDualList = privilegeDualList;
    }

    public DualListModel<Region> getDualList() {
        return dualList;
    }

    public void setDualList(DualListModel<Region> dualList) {
        this.dualList = dualList;
    }

}
