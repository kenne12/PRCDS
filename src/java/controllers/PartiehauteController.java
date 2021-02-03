/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.Region;
import entities.PartiehauteRegion;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import sessions.RegionFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.PartiehauteRegionFacadeLocal;
import utilitaire.Utilitaires;

/**
 *
 * @author kenne gervais
 */
@ManagedBean
@SessionScoped
public class PartiehauteController {

    /**
     * Creates a new instance of PartiehauteRegionController
     */
    @EJB
    private MouchardFacadeLocal mouchardFacadeLocal;

    @EJB
    private PartiehauteRegionFacadeLocal partiehauteregionFacadeLocal;
    private PartiehauteRegion partiehauteregion;
    private List<PartiehauteRegion> partiehauteregions = new ArrayList<>();
    private Boolean detail = true;

    @EJB
    private RegionFacadeLocal regionFacadeLocal;
    private Region region;
    private List<Region> regions = new ArrayList<>();
    private String carte = "";

    private String fichier_carte = null;
    private String msg = "";
    private boolean bouton = true;
    private boolean showPrintForm = true;
    private boolean selectModel = true;
    UploadedFile file;
    private final String destination = Utilitaires.path + "/report/images/";
    //variable qui contient la visibilité d'un bouton
    private boolean printable = true;

    private boolean isRegistred = false;

    private String repertoire = Utilitaires.path + "/" + Utilitaires.repertoireDefautVehicule;
    private String fichier = Utilitaires.nomFichierParDefautListeVehicule;

    public PartiehauteController() {

    }

    @PostConstruct
    private void init() {

        partiehauteregion = new PartiehauteRegion();
        region = new Region();
        try {
            partiehauteregions = partiehauteregionFacadeLocal.findByRegion(SessionMBean.getRegion().getIdregion());
            if (!partiehauteregions.isEmpty()) {
                isRegistred = true;
                partiehauteregion = partiehauteregions.get(0);
                carte = partiehauteregion.getCarte();
            } else {
                isRegistred = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savePartiehauteRegion() {
        try {
            if (isRegistred) {
                partiehauteregionFacadeLocal.edit(partiehauteregion);
            } else {
                partiehauteregion.setIdpartiehauteRegion(partiehauteregionFacadeLocal.nextId());
                if ("".equals(carte)) {
                    carte = Utilitaires.carAvatar;
                }
                partiehauteregion.setCarte(carte);
                partiehauteregion.setIdregion(SessionMBean.getRegion());
                partiehauteregionFacadeLocal.create(partiehauteregion);
                isRegistred = true;
                utilitaire.Utilitaires.saveOperation("Enregistrement de la partiehauteregion -> " + partiehauteregion.getIntroduction(), SessionMBean.getUser(), mouchardFacadeLocal);
                JsfUtil.addSuccessMessage("Operation réussie");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        this.file = event.getFile();
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
            carte = event.getFile().getFileName();

            if (isRegistred) {
                partiehauteregion.setCarte(carte);
                partiehauteregionFacadeLocal.edit(partiehauteregion);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyFile(String fileName, InputStream in) {
        try {
            // write the inputStream to a FileOutputStream
            OutputStream output = new FileOutputStream(new File(destination + fileName));

            int read = 0;
            byte[] bytes = new byte[128];
            while ((read = in.read(bytes)) != -1) {
                output.write(bytes, 0, read);
            }

            in.close();
            output.flush();
            output.close();

            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public PartiehauteRegion getPartiehauteRegion() {
        return partiehauteregion;
    }

    public void setPartiehauteRegion(PartiehauteRegion partiehauteregion) {
        this.partiehauteregion = partiehauteregion;
    }

    public List<PartiehauteRegion> getPartiehauteRegions() {
        try {
            partiehauteregions = partiehauteregionFacadeLocal.findByRegion(SessionMBean.getRegion().getIdregion());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return partiehauteregions;
    }

    public void setPartiehauteRegions(List<PartiehauteRegion> partiehauteregions) {
        this.partiehauteregions = partiehauteregions;
    }

    public Boolean getDetail() {
        return detail;
    }

    public void setDetail(Boolean detail) {
        this.detail = detail;
    }

    public String getCarte() {
        return carte;
    }

    public void setCarte(String carte) {
        this.carte = carte;
    }

    public String getFichier_carte() {
        return fichier_carte;
    }

    public void setFichier_carte(String fichier_carte) {
        this.fichier_carte = fichier_carte;
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public boolean isPrintable() {
        return printable;
    }

    public void setPrintable(boolean printable) {
        this.printable = printable;
    }

    public String getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(String repertoire) {
        this.repertoire = repertoire;
    }

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
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
