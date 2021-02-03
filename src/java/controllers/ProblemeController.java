/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.Axe;
import entities.Indicateur;
import entities.IndicateurRegion;
import entities.ProblemeRegion;
import entities.Sousaxe;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessions.AxeFacadeLocal;
import sessions.IndicateurFacadeLocal;
import sessions.IndicateurRegionFacadeLocal;
import sessions.NotationproblemeFacadeLocal;
import sessions.ProblemeRegionFacadeLocal;
import sessions.SousaxeFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@SessionScoped
public class ProblemeController implements Serializable{

    @EJB
    private IndicateurFacadeLocal indicateurFacadeLocal;
    private Indicateur indicateur = new Indicateur();
    private List<Indicateur> indicateurs = new ArrayList<>();

    @EJB
    private ProblemeRegionFacadeLocal problemeRegionFacadeLocal;
    private ProblemeRegion problemeRegion = new ProblemeRegion();
    private List<ProblemeRegion> problemeRegions = new ArrayList<>();

    @EJB
    private NotationproblemeFacadeLocal notationproblemeFacadeLocal;

    @EJB
    private AxeFacadeLocal axeFacadeLocal;
    private Axe axe = new Axe();
    private List<Axe> axes = new ArrayList<>();

    @EJB
    private SousaxeFacadeLocal sousaxeFacadeLocal;
    private Sousaxe sousaxe = new Sousaxe();
    private List<Sousaxe> sousaxes = new ArrayList<>();

    @EJB
    private IndicateurRegionFacadeLocal indicateurRegionFacadeLocal;
    private IndicateurRegion indicateurRegion = new IndicateurRegion();

    private Double pourcentageAxe = 0d;
    private Double pourcentageSousAxe = 0d;

    private boolean detail = true;

    private boolean showIndicateur = false;

    private String mode = "";

    /**
     * Creates a new instance of SousaxeController
     */
    public ProblemeController() {
    }

    @PostConstruct
    private void init() {
        axes = axeFacadeLocal.findAllRangeByCode();
        problemeRegion = new ProblemeRegion();

        try {
            if (!axes.isEmpty()) {

                axe = axes.get(0);

                List<IndicateurRegion> ids = indicateurRegionFacadeLocal.findByRegionAxeObservation(SessionMBean.getRegion(), axe, 2);
                if (ids.isEmpty()) {
                    pourcentageAxe = 0d;
                } else {
                    Integer conteur = 0;
                    for (IndicateurRegion i : ids) {
                        List<ProblemeRegion> pbs = problemeRegionFacadeLocal.find(i);
                        if (!pbs.isEmpty()) {
                            conteur += 1;
                        }
                    }
                    if (conteur != 0) {
                        pourcentageAxe = (conteur.doubleValue() / ids.size()) * 100;
                    } else {
                        pourcentageAxe = 0d;
                    }
                }

                sousaxes = sousaxeFacadeLocal.findByAxe(axe);

                if (!sousaxes.isEmpty()) {
                    sousaxe = sousaxes.get(0);
                    problemeRegions = problemeRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);

                    List<IndicateurRegion> ids1 = indicateurRegionFacadeLocal.findByRegionSousaxeObservation(SessionMBean.getRegion(), sousaxe, 2);

                    if (!ids1.isEmpty()) {
                        if (!problemeRegions.isEmpty()) {
                            Integer compteur1 = problemeRegions.size();
                            pourcentageSousAxe = (compteur1.doubleValue() / ids1.size()) * 100;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateValues() {
        try {
            if (indicateur.getIdindicateur() != null) {
                indicateur = indicateurFacadeLocal.find(indicateur.getIdindicateur());
                List<IndicateurRegion> temp = indicateurRegionFacadeLocal.findByIndicateur(indicateur, SessionMBean.getRegion());
                if (temp.isEmpty()) {
                    JsfUtil.addErrorMessage("La valeur de l'indicateur pour cette region n'est pas encore renseignée");
                    indicateurRegion = new IndicateurRegion();
                } else {
                    indicateurRegion = temp.get(0);
                }
            } else {
                indicateur = new Indicateur();
                indicateurRegion = new IndicateurRegion();
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
        showIndicateur = false;

        indicateur = new Indicateur();
        indicateurRegion = new IndicateurRegion();
        problemeRegion = new ProblemeRegion();
        problemeRegion.setCause("-");
        indicateurs.clear();
        try {
            indicateurs = indicateurFacadeLocal.findBySousAxeNiveauCollecte(sousaxe, 1);
            System.err.println("sous axe "+sousaxe.getIdsousaxe());
            if (!indicateurs.isEmpty()) {
                List<Indicateur> temp = new ArrayList<>();
                for (Indicateur i : indicateurs) {
                    List<IndicateurRegion> indicateurRegions = indicateurRegionFacadeLocal.findByIndicateur(i, SessionMBean.getRegion());
                    if (!indicateurRegions.isEmpty()) {
                        if (indicateurRegions.get(0).getIdobservation().getIdobservation().equals(2)) {
                            temp.add(indicateurRegions.get(0).getIdindicateur());
                        }
                    }
                }
                indicateurs = temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareEdit() {
        mode = "Edit";
        showIndicateur = true;
        try {
            indicateurs = indicateurFacadeLocal.findBySousAxeNiveauCollecte(sousaxe, 1);
            if (problemeRegion != null) {
                indicateur = problemeRegion.getIdindicateurRegion().getIdindicateur();
                indicateurRegion = problemeRegion.getIdindicateurRegion();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAll() {
        try {
            if (axe != null) {
                sousaxes = sousaxeFacadeLocal.findByAxe(axe);

                if (!sousaxes.isEmpty()) {
                    sousaxe = sousaxes.get(0);
                    problemeRegions = problemeRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);
                } else {
                    sousaxe = new Sousaxe();
                    problemeRegions.clear();
                }

            } else {
                sousaxe = new Sousaxe();
                problemeRegions.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSousaxe() {
        try {
            if (!sousaxes.isEmpty()) {
                pourcentageSousAxe = 0d;
                if (sousaxe != null) {

                    problemeRegions = problemeRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);

                    List<IndicateurRegion> ids1 = indicateurRegionFacadeLocal.findByRegionSousaxeObservation(SessionMBean.getRegion(), sousaxe, 2);

                    if (!ids1.isEmpty()) {

                        Integer conteur = 0;
                        for (IndicateurRegion id : ids1) {
                            List<ProblemeRegion> pbs = problemeRegionFacadeLocal.find(id);
                            if (!pbs.isEmpty()) {
                                conteur += 1;
                            }
                        }
                        if (conteur != 0) {
                            pourcentageSousAxe = (conteur.doubleValue() / ids1.size()) * 100;
                        } else {
                            pourcentageSousAxe = 0d;
                        }
                    }
                }
            } else {
                sousaxe = new Sousaxe();
                problemeRegions.clear();
                pourcentageSousAxe = 0d;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            if ("Edit".equals(mode)) {

                //on est en mode ajout
                if (problemeRegion != null) {
                    problemeRegionFacadeLocal.edit(problemeRegion);

                    List<ProblemeRegion> temps = problemeRegionFacadeLocal.find(indicateurRegion);
                    String chaine = "";
                    String chaine1 = "";
                    int i = 0;
                    for (ProblemeRegion p : temps) {
                        if (i == 0) {
                            chaine += " - " + p.getNom();
                            chaine1 += " - " + p.getCause();
                        } else {
                            chaine += "\n - " + p.getNom();
                            chaine1 += "\n - " + p.getCause();
                        }
                        i++;
                    }
                    indicateurRegion.setProbleme(chaine);
                    indicateurRegion.setCause(chaine1);
                    indicateurRegionFacadeLocal.edit(indicateurRegion);

                    problemeRegions = problemeRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);
                    JsfUtil.addSuccessMessage("Operation réussie");
                } else {
                    JsfUtil.addErrorMessage("Aucune ligne selectionnée");
                }

            } else {
                //on est en mode ajout
                if (indicateurRegion != null) {
                    problemeRegion.setIdproblemeRegion(problemeRegionFacadeLocal.nextId());
                    problemeRegion.setIdindicateurRegion(indicateurRegion);
                    problemeRegion.setTotalpoint(0d);

                    if (indicateurRegion.getIdobservation().getIdobservation().equals(1)) {
                        problemeRegion.setFaible(false);
                        problemeRegionFacadeLocal.create(problemeRegion);
                    } else {
                        problemeRegion.setFaible(true);
                        problemeRegionFacadeLocal.create(problemeRegion);
                    }

                    List<ProblemeRegion> temps = problemeRegionFacadeLocal.find(indicateurRegion);
                    String chaine = "";
                    String chaine1 = "";
                    int i = 0;
                    for (ProblemeRegion p : temps) {
                        if (i == 0) {
                            chaine += " - " + p.getNom();
                            chaine1 += " - " + p.getCause();
                        } else {
                            chaine += "\n - " + p.getNom();
                            chaine1 += "\n - " + p.getCause();
                        }
                        i++;
                    }
                    indicateurRegion.setCause(chaine1);
                    indicateurRegion.setProbleme(chaine);
                    indicateurRegionFacadeLocal.edit(indicateurRegion);
                }
                problemeRegions = problemeRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);
                JsfUtil.addSuccessMessage("Opération réussie");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            if (problemeRegion != null) {
                /*if (notationproblemeFacadeLocal.find(problemeRegion).isEmpty()) {*/
                problemeRegionFacadeLocal.remove(problemeRegion);

                indicateurRegion = problemeRegion.getIdindicateurRegion();
                List<ProblemeRegion> temps = problemeRegionFacadeLocal.find(indicateurRegion);

                String chaine = "";
                String chaine1 = "";
                int i = 0;
                for (ProblemeRegion p : temps) {
                    if (i == 0) {
                        chaine += " - " + p.getNom();
                        chaine1 += " - " + p.getCause();
                    } else {
                        chaine += "\n - " + p.getNom();
                        chaine1 += "\n - " + p.getCause();
                    }
                    i++;
                }
                indicateurRegion.setCause(chaine1);
                indicateurRegion.setProbleme(chaine);
                indicateurRegionFacadeLocal.edit(indicateurRegion);

                problemeRegions = problemeRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);
                JsfUtil.addSuccessMessage("Opération réussie");
                /*} else {
                 JsfUtil.addErrorMessage("Ce probleme contient les données de priroisation et ne peut etre supprimé");
                 }*/
            } else {
                JsfUtil.addErrorMessage("Aucune problème selectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nextAxe() {
        try {
            pourcentageAxe = 0d;
            pourcentageSousAxe = 0d;
            if (!axes.isEmpty()) {
                if (axes.size() > 1) {
                    int i = 0;
                    for (Axe a : axes) {
                        if (a.equals(axe)) {
                            if (i <= axes.size()) {

                                if (i + 1 == axes.size()) {
                                    break;
                                }

                                axe = axes.get(i + 1);

                                List<IndicateurRegion> ids = indicateurRegionFacadeLocal.findByRegionAxeObservation(SessionMBean.getRegion(), axe, 2);
                                if (ids.isEmpty()) {
                                    pourcentageAxe = 0d;
                                } else {
                                    Integer conteur = 0;
                                    for (IndicateurRegion id : ids) {
                                        List<ProblemeRegion> pbs = problemeRegionFacadeLocal.find(id);
                                        if (!pbs.isEmpty()) {
                                            conteur += 1;
                                        }
                                    }
                                    if (conteur != 0) {
                                        pourcentageAxe = (conteur.doubleValue() / ids.size()) * 100;
                                    } else {
                                        pourcentageAxe = 0d;
                                    }
                                }

                                sousaxes = sousaxeFacadeLocal.findByAxe(axe);

                                if (!sousaxes.isEmpty()) {
                                    sousaxe = sousaxes.get(0);
                                    problemeRegions = problemeRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);

                                    List<IndicateurRegion> ids1 = indicateurRegionFacadeLocal.findByRegionSousaxeObservation(SessionMBean.getRegion(), sousaxe, 2);

                                    if (!ids1.isEmpty()) {

                                        Integer conteur = 0;
                                        for (IndicateurRegion id : ids1) {
                                            List<ProblemeRegion> pbs = problemeRegionFacadeLocal.find(id);
                                            if (!pbs.isEmpty()) {
                                                conteur += 1;
                                            }
                                        }
                                        if (conteur != 0) {
                                            pourcentageSousAxe = (conteur.doubleValue() / ids1.size()) * 100;
                                        } else {
                                            pourcentageSousAxe = 0d;
                                        }
                                    }
                                    break;
                                } else {
                                    sousaxe = new Sousaxe();
                                    sousaxes.clear();
                                    problemeRegions.clear();

                                    break;
                                }
                            }
                        }
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void precAxe() {
        try {
            pourcentageAxe = 0d;
            pourcentageSousAxe = 0d;
            if (!axes.isEmpty()) {
                if (axes.size() > 1) {
                    int i = 0;
                    for (Axe a : axes) {
                        if (a.equals(axe)) {
                            if (i == 0) {
                                break;
                            } else {
                                axe = axes.get(i - 1);

                                List<IndicateurRegion> ids = indicateurRegionFacadeLocal.findByRegionAxeObservation(SessionMBean.getRegion(), axe, 2);
                                if (ids.isEmpty()) {
                                    pourcentageAxe = 0d;
                                } else {
                                    Integer conteur = 0;
                                    for (IndicateurRegion id : ids) {
                                        List<ProblemeRegion> pbs = problemeRegionFacadeLocal.find(id);
                                        if (!pbs.isEmpty()) {
                                            conteur += 1;
                                        }
                                    }
                                    if (conteur != 0) {
                                        pourcentageAxe = (conteur.doubleValue() / ids.size()) * 100;
                                    } else {
                                        pourcentageAxe = 0d;
                                    }
                                }

                                sousaxes = sousaxeFacadeLocal.findByAxe(axe);
                                if (!sousaxes.isEmpty()) {
                                    sousaxe = sousaxes.get(0);
                                    problemeRegions = problemeRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);

                                    List<IndicateurRegion> ids1 = indicateurRegionFacadeLocal.findByRegionSousaxeObservation(SessionMBean.getRegion(), sousaxe, 2);

                                    if (!ids1.isEmpty()) {

                                        Integer conteur = 0;
                                        for (IndicateurRegion id : ids1) {
                                            List<ProblemeRegion> pbs = problemeRegionFacadeLocal.find(id);
                                            if (!pbs.isEmpty()) {
                                                conteur += 1;
                                            }
                                        }
                                        if (conteur != 0) {
                                            pourcentageSousAxe = (conteur.doubleValue() / ids1.size()) * 100;
                                        } else {
                                            pourcentageSousAxe = 0d;
                                        }
                                    }

                                    break;
                                } else {
                                    problemeRegions.clear();
                                    break;
                                }
                            }
                        }
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nextSousaxe() {
        try {
            if (!sousaxes.isEmpty()) {
                if (sousaxes.size() > 1) {
                    int i = 0;
                    for (Sousaxe s : sousaxes) {
                        if (s.equals(sousaxe)) {
                            if (i <= axes.size()) {

                                if (i + 1 == sousaxes.size()) {
                                    return;
                                }
                                sousaxe = sousaxes.get(i + 1);
                                this.updateSousaxe();
                                break;
                            }
                        }
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void precSousAxe() {

        try {
            if (!sousaxes.isEmpty()) {
                if (sousaxes.size() > 1) {
                    int i = 0;
                    for (Sousaxe s : sousaxes) {
                        if (s.equals(sousaxe)) {
                            if (i == 0) {
                                break;
                            } else {
                                sousaxe = sousaxes.get(i - 1);
                                this.updateSousaxe();
                                break;
                            }
                        }
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String findBackColor(ProblemeRegion probleme) {
        String color = "";
        try {
            if (probleme.getIdindicateurRegion().getIdindicateur().getInverse()) {
                if (probleme.getIdindicateurRegion().getValeur() <= probleme.getIdindicateurRegion().getIdindicateur().getCiblenationale()) {
                    color = "blue";
                } else {
                    color = "red";
                }
            } else {
                if (probleme.getIdindicateurRegion().getValeur() <= probleme.getIdindicateurRegion().getIdindicateur().getCiblenationale()) {
                    color = "red";
                } else {
                    color = "blue";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return color;
    }

    public String findColor(ProblemeRegion probleme) {
        String color = "";
        try {
            if (probleme.getIdindicateurRegion().getIdindicateur().getInverse()) {
                if (probleme.getIdindicateurRegion().getValeur() <= probleme.getIdindicateurRegion().getIdindicateur().getCiblenationale()) {
                    color = "white";
                } else {
                    color = "yellow";
                }
            } else {
                if (probleme.getIdindicateurRegion().getValeur() <= probleme.getIdindicateurRegion().getIdindicateur().getCiblenationale()) {
                    color = "white";
                } else {
                    color = "white";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return color;
    }

    public boolean isDetail() {
        return detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    public Axe getAxe() {
        return axe;
    }

    public void setAxe(Axe axe) {
        this.axe = axe;
    }

    public List<Axe> getAxes() {
        return axes;
    }

    public void setAxes(List<Axe> axes) {
        this.axes = axes;
    }

    public Sousaxe getSousaxe() {
        return sousaxe;
    }

    public void setSousaxe(Sousaxe sousaxe) {
        this.sousaxe = sousaxe;
    }

    public List<Sousaxe> getSousaxes() {
        return sousaxes;
    }

    public void setSousaxes(List<Sousaxe> sousaxes) {
        this.sousaxes = sousaxes;
    }

    public ProblemeRegion getProblemeRegion() {
        return problemeRegion;
    }

    public void setProblemeRegion(ProblemeRegion problemeRegion) {
        this.problemeRegion = problemeRegion;
    }

    public List<ProblemeRegion> getProblemeRegions() {
        return problemeRegions;
    }

    public void setProblemeRegions(List<ProblemeRegion> problemeRegions) {
        this.problemeRegions = problemeRegions;
    }

    public IndicateurRegion getIndicateurRegion() {
        return indicateurRegion;
    }

    public void setIndicateurRegion(IndicateurRegion indicateurRegion) {
        this.indicateurRegion = indicateurRegion;
    }

    public Indicateur getIndicateur() {
        return indicateur;
    }

    public void setIndicateur(Indicateur indicateur) {
        this.indicateur = indicateur;
    }

    public List<Indicateur> getIndicateurs() {
        return indicateurs;
    }

    public void setIndicateurs(List<Indicateur> indicateurs) {
        this.indicateurs = indicateurs;
    }

    public boolean isShowIndicateur() {
        return showIndicateur;
    }

    public void setShowIndicateur(boolean showIndicateur) {
        this.showIndicateur = showIndicateur;
    }

    public Double getPourcentageAxe() {
        return pourcentageAxe;
    }

    public void setPourcentageAxe(Double pourcentageAxe) {
        this.pourcentageAxe = pourcentageAxe;
    }

    public Double getPourcentageSousAxe() {
        return pourcentageSousAxe;
    }

    public void setPourcentageSousAxe(Double pourcentageSousAxe) {
        this.pourcentageSousAxe = pourcentageSousAxe;
    }

}
