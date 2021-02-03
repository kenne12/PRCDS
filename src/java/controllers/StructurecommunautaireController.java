/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.CommentaireRegion;
import entities.Structurecommunautaire;
import entities.Etatfonctstructcom;
import entities.Typestructurecommunautaire;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessions.CommentaireRegionFacadeLocal;
import sessions.StructurecommunautaireFacadeLocal;
import sessions.EtatfonctstructcomFacadeLocal;

import sessions.TypestructurecommunautaireFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@SessionScoped
public class StructurecommunautaireController implements Serializable {

    @EJB
    private StructurecommunautaireFacadeLocal structurecommunautaireFacadeLocal;
    private Structurecommunautaire structurecommunautaire = new Structurecommunautaire();
    private List<Structurecommunautaire> structurecommunautaires = new ArrayList<>();

    @EJB
    private TypestructurecommunautaireFacadeLocal typestructurecommunautaireFacadeLocal;
    private Typestructurecommunautaire typestructurecommunautaire = new Typestructurecommunautaire();
    private List<Typestructurecommunautaire> typestructurecommunautaires = new ArrayList<>();

    @EJB
    private EtatfonctstructcomFacadeLocal etatfonctstructcomFacadeLocal;
    private Etatfonctstructcom etatfonctstructcom = new Etatfonctstructcom();
    private List<Etatfonctstructcom> etatfonctstructcoms = new ArrayList<>();

    @EJB
    private CommentaireRegionFacadeLocal commentairetabFacadeLocal;
    private CommentaireRegion commentairetab = new CommentaireRegion();

    private boolean detail = true;

    private String mode = "";

    /**
     * Creates a new instance of StructurecommunautaireController
     */
    public StructurecommunautaireController() {

    }

    @PostConstruct
    private void init() {
        try {
            List<CommentaireRegion> commentairetabs = commentairetabFacadeLocal.find(SessionMBean.getRegion(), 9);
            if (!commentairetabs.isEmpty()) {
                commentairetab = commentairetabs.get(0);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            structurecommunautaire = new Structurecommunautaire();
            typestructurecommunautaire = new Typestructurecommunautaire();
            etatfonctstructcom = new Etatfonctstructcom();
            structurecommunautaire.setEffectif(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareEdit() {
        mode = "Edit";
        try {
            if (structurecommunautaire != null) {
                if (structurecommunautaire.getIdtypestructurecommunautaire() != null) {
                    typestructurecommunautaire = structurecommunautaire.getIdtypestructurecommunautaire();
                }

                if (structurecommunautaire.getIdetatfonctstructcom() != null) {
                    etatfonctstructcom = structurecommunautaire.getIdetatfonctstructcom();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {

            if ("Create".equals(mode)) {
                structurecommunautaire.setIdstructurecommunautaire(structurecommunautaireFacadeLocal.nextId());

                if (typestructurecommunautaire.getIdtypestructurecommunautaire() != null) {
                    structurecommunautaire.setIdtypestructurecommunautaire(typestructurecommunautaireFacadeLocal.find(typestructurecommunautaire.getIdtypestructurecommunautaire()));
                }

                if (etatfonctstructcom.getIdetatfonctstructcom() != null) {
                    structurecommunautaire.setIdetatfonctstructcom(etatfonctstructcomFacadeLocal.find(etatfonctstructcom.getIdetatfonctstructcom()));
                }
                structurecommunautaire.setIdregion(SessionMBean.getRegion());
                structurecommunautaireFacadeLocal.create(structurecommunautaire);
                structurecommunautaire = new Structurecommunautaire();

                List<CommentaireRegion> commentairetabs = commentairetabFacadeLocal.find(SessionMBean.getRegion(), 9);
                if (commentairetabs.isEmpty()) {
                    commentairetab.setIdcommentaireRegion(commentairetabFacadeLocal.nextId());
                    commentairetab.setIdregion(SessionMBean.getRegion());
                    commentairetab.setNumerotab(9);
                    commentairetabFacadeLocal.create(commentairetab);
                } else {
                    commentairetabFacadeLocal.edit(commentairetab);
                }

                JsfUtil.addSuccessMessage("Opération réussie");
            } else {
                if (structurecommunautaire != null) {

                    if (typestructurecommunautaire.getIdtypestructurecommunautaire() != null) {
                        structurecommunautaire.setIdtypestructurecommunautaire(typestructurecommunautaireFacadeLocal.find(typestructurecommunautaire.getIdtypestructurecommunautaire()));
                    }

                    if (etatfonctstructcom.getIdetatfonctstructcom() != null) {
                        structurecommunautaire.setIdetatfonctstructcom(etatfonctstructcomFacadeLocal.find(etatfonctstructcom.getIdetatfonctstructcom()));
                    }

                    structurecommunautaireFacadeLocal.edit(structurecommunautaire);

                    List<CommentaireRegion> commentairetabs = commentairetabFacadeLocal.find(SessionMBean.getRegion(), 9);
                    if (commentairetabs.isEmpty()) {
                        commentairetab.setIdcommentaireRegion(commentairetabFacadeLocal.nextId());
                        commentairetab.setIdregion(SessionMBean.getRegion());
                        commentairetab.setNumerotab(9);
                        commentairetabFacadeLocal.create(commentairetab);
                    } else {
                        commentairetabFacadeLocal.edit(commentairetab);
                    }

                    structurecommunautaire = new Structurecommunautaire();

                    JsfUtil.addSuccessMessage("Structurecommunautaire mis à jour avec succès");
                } else {
                    JsfUtil.addErrorMessage("Aucune ligne sélectionée");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            if (structurecommunautaire != null) {
                structurecommunautaireFacadeLocal.remove(structurecommunautaire);
                structurecommunautaire = new Structurecommunautaire();
                JsfUtil.addSuccessMessage("Opération réussie");
            } else {
                JsfUtil.addErrorMessage("Aucune ligne selectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCommentaire() {
        try {
            if (commentairetab.getIdcommentaireRegion() != null) {
                if (!structurecommunautaires.isEmpty()) {
                    commentairetabFacadeLocal.edit(commentairetab);
                    JsfUtil.addSuccessMessage("Opération réussie");
                    return;
                }
                JsfUtil.addErrorMessage("Aucune données à commenter");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Structurecommunautaire getStructurecommunautaire() {
        return structurecommunautaire;
    }

    public void setStructurecommunautaire(Structurecommunautaire structurecommunautaire) {
        this.structurecommunautaire = structurecommunautaire;
    }

    public List<Structurecommunautaire> getStructurecommunautaires() {
        try {
            structurecommunautaires = structurecommunautaireFacadeLocal.findByRegion(SessionMBean.getRegion());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return structurecommunautaires;
    }

    public void setStructurecommunautaires(List<Structurecommunautaire> structurecommunautaires) {
        this.structurecommunautaires = structurecommunautaires;
    }

    public boolean isDetail() {
        return detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    public Typestructurecommunautaire getTypestructurecommunautaire() {
        return typestructurecommunautaire;
    }

    public void setTypestructurecommunautaire(Typestructurecommunautaire typestructurecommunautaire) {
        this.typestructurecommunautaire = typestructurecommunautaire;
    }

    public List<Typestructurecommunautaire> getTypestructurecommunautaires() {
        typestructurecommunautaires = typestructurecommunautaireFacadeLocal.findAllRange();
        return typestructurecommunautaires;
    }

    public void setTypestructurecommunautaires(List<Typestructurecommunautaire> typestructurecommunautaires) {
        this.typestructurecommunautaires = typestructurecommunautaires;
    }

    public CommentaireRegion getCommentairetab() {
        return commentairetab;
    }

    public void setCommentairetab(CommentaireRegion commentairetab) {
        this.commentairetab = commentairetab;
    }

    public Etatfonctstructcom getEtatfonctstructcom() {
        return etatfonctstructcom;
    }

    public void setEtatfonctstructcom(Etatfonctstructcom etatfonctstructcom) {
        this.etatfonctstructcom = etatfonctstructcom;
    }

    public List<Etatfonctstructcom> getEtatfonctstructcoms() {
        etatfonctstructcoms = etatfonctstructcomFacadeLocal.findAllRange();
        return etatfonctstructcoms;
    }

    public void setEtatfonctstructcoms(List<Etatfonctstructcom> etatfonctstructcoms) {
        this.etatfonctstructcoms = etatfonctstructcoms;
    }

}
