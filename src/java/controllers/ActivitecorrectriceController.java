/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.ActiviteDefault;
import entities.ActiviteRegion;
import entities.ActiviteRegionElementCout;
import entities.ActiviteStructureRegion;
import entities.Annee;
import entities.Axe;
import entities.ChronogrammeRegion;
import entities.CoastingDefault;
import entities.District;
import entities.ElementCout;
import entities.Indicateur;
import entities.IndicateurRegion;
import entities.ObjectifOppRegion;
import entities.ProblemeRegion;
import entities.ResultatAttenduRegion;
import entities.Sourcefinancement;
import entities.Sousaxe;
import entities.Structure;
import entities.Typestructure;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessions.ActiviteDefaultFacadeLocal;
import sessions.ActiviteRegionElementCoutFacadeLocal;
import sessions.ActiviteRegionFacadeLocal;
import sessions.ActiviteStructureRegionFacadeLocal;
import sessions.AnneeFacadeLocal;
import sessions.AxeFacadeLocal;
import sessions.ChronogrammeRegionFacadeLocal;
import sessions.CoastingDefaultFacadeLocal;
import sessions.ElementCoutFacadeLocal;
import sessions.IndicateurFacadeLocal;
import sessions.IndicateurRegionFacadeLocal;
import sessions.ObjectifOppRegionFacadeLocal;
import sessions.ProblemeRegionFacadeLocal;
import sessions.ResultatAttenduRegionFacadeLocal;
import sessions.SourcefinancementFacadeLocal;
import sessions.SousaxeFacadeLocal;
import sessions.StructureFacadeLocal;
import sessions.TypestructureFacadeLocal;

/**
 *
 * @author kenne
 */
@ManagedBean
@SessionScoped
public class ActivitecorrectriceController {

    @EJB
    private ActiviteRegionFacadeLocal activiteRegionFacadeLocal;
    private ActiviteRegion activiteRegion = new ActiviteRegion();
    private List<ActiviteRegion> activiteRegions = new ArrayList<>();

    @EJB
    private IndicateurFacadeLocal indicateurFacadeLocal;
    private Indicateur indicateur = new Indicateur();
    private List<Indicateur> indicateurs = new ArrayList<>();

    @EJB
    private ActiviteStructureRegionFacadeLocal activiteStructureRegionFacadeLocal;

    @EJB
    private StructureFacadeLocal structureFacadeLocal;

    @EJB
    private TypestructureFacadeLocal typestructureFacadeLocal;
    private Typestructure typestructure = new Typestructure();
    private List<Typestructure> typestructures = new ArrayList<>();

    @EJB
    private SourcefinancementFacadeLocal sourcefinancementFacadeLocal;
    private Sourcefinancement sourcefinancement = new Sourcefinancement();
    private List<Sourcefinancement> sourcefinancements = new ArrayList<>();

    @EJB
    private ProblemeRegionFacadeLocal problemeRegionFacadeLocal;
    private ProblemeRegion problemeRegion = new ProblemeRegion();
    private List<ProblemeRegion> problemeRegions = new ArrayList<>();

    @EJB
    private AxeFacadeLocal axeFacadeLocal;
    private Axe axe = new Axe();
    private List<Axe> axes = new ArrayList<>();

    @EJB
    private SousaxeFacadeLocal sousaxeFacadeLocal;
    private Sousaxe sousaxe = new Sousaxe();
    private List<Sousaxe> sousaxes = new ArrayList<>();

    @EJB
    private AnneeFacadeLocal anneeFacadeLocal;
    private Annee annee;
    private List<Annee> annees = new ArrayList<>();

    @EJB
    private ResultatAttenduRegionFacadeLocal resultatAttenduRegionFacadeLocal;
    private ResultatAttenduRegion resultatAttenduRegion = new ResultatAttenduRegion();
    private List<ResultatAttenduRegion> resultatAttenduRegions = new ArrayList<>();

    @EJB
    private ChronogrammeRegionFacadeLocal chronogrammeRegionFacadeLocal;

    @EJB
    private ObjectifOppRegionFacadeLocal objectifOppRegionFacadeLocal;
    private ObjectifOppRegion objectifOppRegion = new ObjectifOppRegion();
    private List<ObjectifOppRegion> objectifOppRegions = new ArrayList<>();

    @EJB
    private ActiviteDefaultFacadeLocal activiteDefaultFacadeLocal;
    private ActiviteDefault activiteDefault = new ActiviteDefault();
    private List<ActiviteDefault> activiteDefaults = new ArrayList<>();

    @EJB
    private ElementCoutFacadeLocal elementCoutFacadeLocal;
    private ElementCout elementCout = new ElementCout();
    private List<ElementCout> elementCouts = new ArrayList<>();

    @EJB
    private ActiviteRegionElementCoutFacadeLocal activiteRegionElementCoutFacadeLocal;
    private ActiviteRegionElementCout activiteRegionElementCout = new ActiviteRegionElementCout();
    private List<ActiviteRegionElementCout> activiteRegionElementCouts = new ArrayList<>();

    @EJB
    private CoastingDefaultFacadeLocal coastingDefaultFacadeLocal;
    private CoastingDefault coastingDefault = new CoastingDefault();
    private List<CoastingDefault> coastingDefaults = new ArrayList<>();

    private Double total = 0d;

    @EJB
    private IndicateurRegionFacadeLocal indicateurRegionFacadeLocal;

    private District district = new District();

    private boolean detail = true;

    private boolean showTypestructure = false;

    private boolean showIndicateur = false;
    private boolean showProbleme = false;

    private Double pourcentageAxe = 0d;
    private Double pourcentageSousAxe = 0d;

    private boolean showSelectActivite = true;
    private boolean showSelector = true;

    private boolean isCoasted = true;

    private String mode = "";

    /**
     * Creates a new instance of SousaxeController
     */
    public ActivitecorrectriceController() {
    }

    @PostConstruct
    private void init() {
        annee = new Annee();
        axes = axeFacadeLocal.findAllRangeByCode();
        district = SessionMBean.getDistrict();

        try {
            if (!axes.isEmpty()) {

                axe = axes.get(0);

                List<ProblemeRegion> pbs = problemeRegionFacadeLocal.findByAxeRegion(axe, SessionMBean.getRegion(), 2);
                if (pbs.isEmpty()) {
                    pourcentageAxe = 0d;
                } else {
                    Integer conteur = 0;
                    for (ProblemeRegion p : pbs) {

                        List<ActiviteRegion> act = activiteRegionFacadeLocal.findByProbleme(p);
                        if (!act.isEmpty()) {
                            conteur += 1;
                        }
                    }
                    if (conteur != 0) {
                        pourcentageAxe = (conteur.doubleValue() / pbs.size()) * 100;
                    } else {
                        pourcentageAxe = 0d;
                    }
                }

                sousaxes = sousaxeFacadeLocal.findByAxe(axe);

                if (!sousaxes.isEmpty()) {
                    sousaxe = sousaxes.get(0);
                    activiteRegions = activiteRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);

                    List<ProblemeRegion> pbs1 = problemeRegionFacadeLocal.findBySousAxeRegion(sousaxe, SessionMBean.getRegion(), 2);
                    if (!pbs1.isEmpty()) {
                        if (!activiteRegions.isEmpty()) {
                            Integer compteur1 = activiteRegions.size();
                            pourcentageSousAxe = (compteur1.doubleValue() / pbs1.size()) * 100;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProbleme() {
        try {
            problemeRegions.clear();

            if (indicateur.getIdindicateur() != null) {

                indicateur = indicateurFacadeLocal.find(indicateur.getIdindicateur());

                activiteDefaults = activiteDefaultFacadeLocal.findByIndicateur(indicateur);

                problemeRegion = new ProblemeRegion();
                if (sousaxe != null) {
                    problemeRegions = problemeRegionFacadeLocal.find(indicateur, SessionMBean.getRegion(), 2);

                    if (problemeRegions.isEmpty()) {
                        JsfUtil.addErrorMessage("Cet indicateur n'est pas evalué faible");
                    }
                }

                resultatAttenduRegions = resultatAttenduRegionFacadeLocal.findByIndicateur(indicateur, SessionMBean.getRegion());
                if (resultatAttenduRegions.isEmpty()) {
                    JsfUtil.addErrorMessage("Cet indicateur ne comporte pas de résultat attendu");
                }

                objectifOppRegions = objectifOppRegionFacadeLocal.findByRegion(SessionMBean.getRegion(), indicateur.getIdinterventionpnds());
                if (objectifOppRegions.isEmpty()) {
                    JsfUtil.addErrorMessage("Cet indicateur ne comporte pas d'objectif opérationnel");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        try {
            if (problemeRegion.getIdproblemeRegion() != null) {
                problemeRegion = problemeRegionFacadeLocal.find(problemeRegion.getIdproblemeRegion());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCoasting() {
        try {
            total = 0d;
            for (ActiviteRegionElementCout a : activiteRegionElementCouts) {
                total += (a.getCoutunitaire() * a.getNbreJr() * a.getQte());
            }
            activiteDefault.setCoutUnitaire(total);
            if (typestructure.getIdtypestructure() != null) {
                activiteDefault.setCoutUnitaire(total);
            } else {
                //int size = structureFacadeLocal.find(SessionMBean.getRegion(), 2, typestructure).size();
                //if (size != 0) {
                //    total = (total * size);
                //}
                total = (total * 1);
            }
            activiteRegion.setCoutglobal(total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelCosting() {
        activiteRegionElementCouts.clear();
        isCoasted = false;
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
        showProbleme = false;
        showTypestructure = false;
        sourcefinancement = new Sourcefinancement();
        typestructure = new Typestructure();
        activiteRegion = new ActiviteRegion();
        indicateur = new Indicateur();
        problemeRegion = new ProblemeRegion();
        resultatAttenduRegion = new ResultatAttenduRegion();
        objectifOppRegion = new ObjectifOppRegion();
        resultatAttenduRegions.clear();
        objectifOppRegions.clear();
        showSelector = true;
        showSelectActivite = true;
        activiteDefault = new ActiviteDefault();
        activiteDefaults.clear();
        activiteDefaults.clear();
        activiteRegionElementCouts.clear();
        activiteRegion.setResponsable("-");
        activiteRegion.setCoutglobal(0d);
        activiteRegion.setCoutunitaire(0d);
        isCoasted = false;
        try {

            indicateurs.clear();

            List<Indicateur> indicateurs = indicateurFacadeLocal.findBySousAxeNiveauCollecte(sousaxe, 1);
            if (!indicateurs.isEmpty()) {
                for (Indicateur i : indicateurs) {
                    List<IndicateurRegion> ids = indicateurRegionFacadeLocal.findByRegionIndicateurObservation(SessionMBean.getRegion(), i, 2);
                    if (!ids.isEmpty()) {
                        this.indicateurs.add(i);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareEdit() {
        mode = "Edit";
        showIndicateur = true;
        showProbleme = true;
        showTypestructure = true;
        showSelector = false;
        showSelectActivite = false;
        activiteRegionElementCouts.clear();

        isCoasted = false;
        this.uptadeTable();
    }

    public void updateOther() {
        try {
            if (activiteDefault.getIdactiviteDefault() != null) {

                activiteDefault = activiteDefaultFacadeLocal.find(activiteDefault.getIdactiviteDefault());
                if ("fr".equals(SessionMBean.getLangue())) {
                    activiteRegion.setNom(activiteDefault.getNomFr());
                } else {
                    activiteRegion.setNom(activiteDefault.getNomEn());
                }
                activiteRegion.setCoutunitaire(activiteDefault.getCoutUnitaire());

                if (activiteDefault.getIdsourcefi() != null) {
                    sourcefinancement = activiteDefault.getIdsourcefi();
                }

                if (activiteDefault.getIdtypestructure() != null) {
                    typestructure = activiteDefault.getIdtypestructure();
                }
                this.updateCoutglobal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSelect() {

    }

    public void updateAll() {
        try {
            if (axe != null) {
                sousaxes = sousaxeFacadeLocal.findByAxe(axe);

                if (!sousaxes.isEmpty()) {
                    sousaxe = sousaxes.get(0);
                    activiteRegions = activiteRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);
                } else {
                    sousaxe = new Sousaxe();
                    activiteRegions.clear();
                }
            } else {
                sousaxe = new Sousaxe();
                activiteRegions.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSousaxe() {
        try {
            if (!sousaxes.isEmpty()) {

                if (sousaxe != null) {
                    activiteRegions = activiteRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);

                    List<ProblemeRegion> pbs = problemeRegionFacadeLocal.findBySousAxeRegion(sousaxe, SessionMBean.getRegion(), 2);

                    if (!pbs.isEmpty()) {

                        Integer conteur = 0;
                        for (ProblemeRegion pb : pbs) {
                            List<ActiviteRegion> acts = activiteRegionFacadeLocal.findByProbleme(pb);
                            if (!acts.isEmpty()) {
                                conteur += 1;
                            }
                        }
                        if (conteur != 0) {
                            pourcentageSousAxe = (conteur.doubleValue() / pbs.size()) * 100;
                        } else {
                            pourcentageSousAxe = 0d;
                        }
                    }
                }
            } else {
                sousaxe = new Sousaxe();
                activiteRegions.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ActiviteRegion findResult(ProblemeRegion problemeRegion) {
        ActiviteRegion activiteRegion = new ActiviteRegion();
        try {
            List<ActiviteRegion> activites = activiteRegionFacadeLocal.findByProbleme(problemeRegion);
            if (!activites.isEmpty()) {
                activiteRegion = activites.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activiteRegion;
    }

    public void prepareCoasting() {
        try {
            if ("Create".equals(mode)) {
                if (!isCoasted) {

                    if (activiteDefault != null) {
                        activiteRegionElementCouts.clear();
                        List<CoastingDefault> coastingDefaults = coastingDefaultFacadeLocal.findByActivite(activiteDefault);
                        if (!coastingDefaults.isEmpty()) {
                            for (CoastingDefault c : coastingDefaults) {
                                ActiviteRegionElementCout a = new ActiviteRegionElementCout();
                                a.setCoutunitaire(c.getCoutunitaire());
                                a.setIdelementcout(c.getIdelementcout());
                                a.setNbreJr(c.getNbreJr().doubleValue());
                                a.setQte(c.getQte().doubleValue());
                                activiteRegionElementCouts.add(a);
                            }
                        } else {
                            activiteRegionElementCouts.clear();
                            elementCouts = elementCoutFacadeLocal.findAll();
                            if (!elementCouts.isEmpty()) {
                                for (ElementCout e : elementCouts) {
                                    ActiviteRegionElementCout a = new ActiviteRegionElementCout();
                                    a.setCoutunitaire(e.getDefaultCu());
                                    a.setIdelementcout(e);
                                    a.setNbreJr(e.getDefaultNbreJr());
                                    a.setQte(e.getDefaultQte());
                                    activiteRegionElementCouts.add(a);
                                }
                            }
                        }
                    } else {
                        activiteRegionElementCouts.clear();
                        elementCouts = elementCoutFacadeLocal.findAll();
                        if (!elementCouts.isEmpty()) {
                            for (ElementCout e : elementCouts) {
                                ActiviteRegionElementCout a = new ActiviteRegionElementCout();
                                a.setCoutunitaire(e.getDefaultCu());
                                a.setIdelementcout(e);
                                a.setNbreJr(e.getDefaultNbreJr());
                                a.setQte(e.getDefaultQte());
                                activiteRegionElementCouts.add(a);
                            }
                        }
                    }
                }
            } else {
                if (!isCoasted) {
                    total = activiteRegion.getCoutunitaire();
                    activiteRegionElementCouts = activiteRegionElementCoutFacadeLocal.findByActivite(activiteRegion);
                    List<ElementCout> elementCouts = elementCoutFacadeLocal.findAll();
                    List<ElementCout> elementCouts1 = new ArrayList<>();
                    for (ActiviteRegionElementCout a : activiteRegionElementCouts) {
                        elementCouts1.add(a.getIdelementcout());
                    }
                    for (ElementCout e : elementCouts) {
                        if (!elementCouts1.contains(e)) {
                            ActiviteRegionElementCout a = new ActiviteRegionElementCout();
                            a.setCoutunitaire(e.getDefaultCu());
                            a.setIdelementcout(e);
                            a.setNbreJr(e.getDefaultNbreJr());
                            a.setIdactiviteRegion(activiteRegion);
                            a.setQte(e.getDefaultQte());
                            activiteRegionElementCouts.add(a);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void coastActivity() {
        try {
            isCoasted = true;
            total = 0d;
            for (ActiviteRegionElementCout a : activiteRegionElementCouts) {
                total += (a.getCoutunitaire() * a.getNbreJr() * a.getQte());
            }
            activiteRegion.setCoutunitaire(total);
            if (typestructure.getIdtypestructure() != null) {
                activiteRegion.setCoutunitaire(total);
                //int size = structureFacadeLocal.find(SessionMBean.getRegion(), 2, typestructure).size();
                //if (size != 0) {
                //activiteRegion.setCoutglobal(size * total);
                //}
                activiteRegion.setCoutglobal(1 * total);
            } else {
                activiteRegion.setCoutunitaire(total);
                activiteRegion.setCoutglobal(total);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            if ("Edit".equals(mode)) {

                activiteRegion.setIdproblemeRegion(problemeRegion);
                if(sourcefinancement.getIdsourcefi()!=null){
                    sourcefinancement = sourcefinancementFacadeLocal.find(sourcefinancement.getIdsourcefi());
                }
                activiteRegion.setIdsourcefi(sourcefinancement);
                activiteRegion.setIdtypestructure(typestructure);

                resultatAttenduRegion = resultatAttenduRegionFacadeLocal.find(resultatAttenduRegion.getIdresultatAttenduRegion());

                activiteRegion.setIdresultatattendu(resultatAttenduRegion);

                objectifOppRegion = objectifOppRegionFacadeLocal.find(objectifOppRegion.getIdobjectifOppRegion());
                activiteRegion.setIdidobjectifOpp(objectifOppRegion);

                ActiviteRegion activite1 = activiteRegionFacadeLocal.find(activiteRegion.getIdactiviteRegion());

                if (activite1.getCoutunitaire() != activiteRegion.getCoutunitaire()) {
                    List<Structure> structures = structureFacadeLocal.find(SessionMBean.getRegion(), 2, typestructure);

                    if (!structures.isEmpty()) {
                        if (structures.size() > 1) {
                            //activiteRegion.setCoutglobal(activiteRegion.getCoutunitaire() * structures.size());
                            activiteRegion.setCoutglobal(activiteRegion.getCoutunitaire() * 1);
                        } else {
                            activiteRegion.setCoutglobal(activiteRegion.getCoutunitaire());
                        }

                        for (ActiviteStructureRegion a : activiteStructureRegionFacadeLocal.find(activiteRegion)) {
                            activiteStructureRegionFacadeLocal.edit(a);
                        }
                    }
                }
                activiteRegion.setActiviteAppui(false);

                activiteRegion.setIdregion(SessionMBean.getRegion().getIdregion());
                activiteRegion.setIdintervention(objectifOppRegion.getIdintervention());

                activiteRegionFacadeLocal.edit(activiteRegion);

                if (isCoasted) {
                    if (!activiteRegionElementCouts.isEmpty()) {
                        for (ActiviteRegionElementCout a : activiteRegionElementCouts) {
                            if (a.getIdactiviteRegionElementCout() != null) {
                                activiteRegionElementCoutFacadeLocal.edit(a);
                            } else {
                                a.setIdactiviteRegionElementCout(activiteRegionElementCoutFacadeLocal.nextId());
                                activiteRegionElementCoutFacadeLocal.create(a);
                            }
                        }
                    }
                }
                isCoasted = false;

                JsfUtil.addSuccessMessage("Opération réussie");
                activiteRegions = activiteRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);

            } else {

                //on est en mode ajout
                List<Structure> structures = structureFacadeLocal.find(SessionMBean.getRegion(), 2, typestructure);

                if (!structures.isEmpty()) {
                    if (structures.size() > 1) {
                        activiteRegion.setCoutglobal(activiteRegion.getCoutunitaire() * 1);
                    } else {
                        activiteRegion.setCoutglobal(activiteRegion.getCoutunitaire());
                    }
                }

                activiteRegion.setIdactiviteRegion(activiteRegionFacadeLocal.nextId());
                activiteRegion.setIdproblemeRegion(problemeRegion);
                activiteRegion.setIdsourcefi(sourcefinancement);
                activiteRegion.setIdtypestructure(typestructure);

                activiteRegion.setIdresultatattendu(resultatAttenduRegion);
                activiteRegion.setIdidobjectifOpp(objectifOppRegion);
                activiteRegion.setActiviteAppui(false);

                activiteRegion.setIdregion(SessionMBean.getRegion().getIdregion());
                activiteRegion.setIdintervention(objectifOppRegion.getIdintervention());

                activiteRegionFacadeLocal.create(activiteRegion);

                if (!structures.isEmpty()) {
                    List<Annee> annees = anneeFacadeLocal.findByEtatprojection(true);
                    for (Structure s : structures) {
                        for (Annee a : annees) {
                            ActiviteStructureRegion activiteStructure = new ActiviteStructureRegion();
                            activiteStructure.setIdactiviteStructureRegion(activiteStructureRegionFacadeLocal.nextId());
                            activiteStructure.setIdstructure(s);
                            activiteStructure.setIdannee(a);
                            activiteStructure.setPrograme(false);
                            activiteStructure.setIdactiviteRegion(activiteRegion);
                            activiteStructure.setCout(activiteRegion.getCoutunitaire());
                            activiteStructureRegionFacadeLocal.create(activiteStructure);
                        }
                    }
                }
                if (isCoasted) {
                    if (!activiteRegionElementCouts.isEmpty()) {
                        for (ActiviteRegionElementCout a : activiteRegionElementCouts) {
                            a.setIdactiviteRegionElementCout(activiteRegionElementCoutFacadeLocal.nextId());
                            a.setIdactiviteRegion(activiteRegion);
                            activiteRegionElementCoutFacadeLocal.create(a);
                        }
                    }
                }
                JsfUtil.addSuccessMessage("Opération réussie");
                activiteRegions = activiteRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);
                isCoasted = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObjectifOppRegion findObjectif(ActiviteRegion activite) {
        ObjectifOppRegion objectifOppRegion = new ObjectifOppRegion();
        try {
            List<ObjectifOppRegion> objectifOppRegions = objectifOppRegionFacadeLocal.findByRegion(SessionMBean.getRegion(), activite.getIdproblemeRegion().getIdindicateurRegion().getIdindicateur().getIdinterventionpnds());
            if (!objectifOppRegions.isEmpty()) {
                objectifOppRegion = objectifOppRegions.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectifOppRegion;
    }

    public void delete() {
        try {
            if (activiteRegion != null) {

                List<ChronogrammeRegion> chrs = chronogrammeRegionFacadeLocal.findByActivite(activiteRegion);
                for (ChronogrammeRegion c : chrs) {
                    chronogrammeRegionFacadeLocal.remove(c);
                }

                List<ActiviteStructureRegion> activiteStructures = activiteStructureRegionFacadeLocal.find(activiteRegion);
                if (!activiteStructures.isEmpty()) {
                    for (ActiviteStructureRegion a : activiteStructures) {
                        activiteStructureRegionFacadeLocal.remove(a);
                    }
                }

                List<ActiviteRegionElementCout> activiteElementCouts = activiteRegionElementCoutFacadeLocal.findByActivite(activiteRegion);
                if (activiteElementCouts.isEmpty()) {
                    for (ActiviteRegionElementCout a : activiteElementCouts) {
                        activiteRegionElementCoutFacadeLocal.remove(a);
                    }
                }
                activiteRegionFacadeLocal.remove(activiteRegion);
                activiteRegions = activiteRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);
                JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                JsfUtil.addErrorMessage("Aucune activite selectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Echec suppression");
        }
    }

    public void nextAxe() {
        try {
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

                                List<ProblemeRegion> pbs = problemeRegionFacadeLocal.findByAxeRegion(axe, SessionMBean.getRegion(), 2);
                                if (pbs.isEmpty()) {
                                    pourcentageAxe = 0d;
                                } else {
                                    Integer conteur = 0;
                                    for (ProblemeRegion p : pbs) {
                                        List<ActiviteRegion> acts = activiteRegionFacadeLocal.findByProbleme(p);
                                        if (!acts.isEmpty()) {
                                            conteur += 1;
                                        }
                                    }
                                    if (conteur != 0) {
                                        pourcentageAxe = (conteur.doubleValue() / pbs.size()) * 100;
                                    } else {
                                        pourcentageAxe = 0d;
                                    }
                                }

                                sousaxes = sousaxeFacadeLocal.findByAxe(axe);

                                if (!sousaxes.isEmpty()) {
                                    sousaxe = sousaxes.get(0);
                                    activiteRegions = activiteRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);

                                    List<ProblemeRegion> pbs1 = problemeRegionFacadeLocal.findBySousAxeRegion(sousaxe, SessionMBean.getRegion(), 2);

                                    if (!pbs1.isEmpty()) {

                                        Integer conteur = 0;
                                        for (ProblemeRegion id : pbs1) {
                                            List<ActiviteRegion> acts = activiteRegionFacadeLocal.findByProbleme(id);
                                            if (!acts.isEmpty()) {
                                                conteur += 1;
                                            }
                                        }
                                        if (conteur != 0) {
                                            pourcentageSousAxe = (conteur.doubleValue() / pbs1.size()) * 100;
                                        } else {
                                            pourcentageSousAxe = 0d;
                                        }
                                    }

                                    break;
                                } else {
                                    sousaxe = new Sousaxe();
                                    sousaxes.clear();
                                    activiteRegions.clear();
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
            if (!axes.isEmpty()) {
                if (axes.size() > 1) {
                    int i = 0;
                    for (Axe a : axes) {
                        if (a.equals(axe)) {
                            if (i == 0) {
                                break;
                            } else {
                                axe = axes.get(i - 1);

                                List<ProblemeRegion> pbs = problemeRegionFacadeLocal.findByAxeRegion(axe, SessionMBean.getRegion(), 2);
                                if (pbs.isEmpty()) {
                                    pourcentageAxe = 0d;
                                } else {
                                    Integer conteur = 0;
                                    for (ProblemeRegion pb : pbs) {
                                        List<ActiviteRegion> acts = activiteRegionFacadeLocal.findByProbleme(pb);
                                        if (!acts.isEmpty()) {
                                            conteur += 1;
                                        }
                                    }
                                    if (conteur != 0) {
                                        pourcentageAxe = (conteur.doubleValue() / pbs.size()) * 100;
                                    } else {
                                        pourcentageAxe = 0d;
                                    }
                                }

                                sousaxes = sousaxeFacadeLocal.findByAxe(axe);
                                if (!sousaxes.isEmpty()) {
                                    sousaxe = sousaxes.get(0);
                                    activiteRegions = activiteRegionFacadeLocal.find(SessionMBean.getRegion(), sousaxe, 2);

                                    List<ProblemeRegion> pbs1 = problemeRegionFacadeLocal.findBySousAxeRegion(sousaxe, SessionMBean.getRegion(), 2);

                                    if (!pbs1.isEmpty()) {

                                        Integer conteur = 0;
                                        for (ProblemeRegion pb : pbs1) {
                                            List<ActiviteRegion> acts = activiteRegionFacadeLocal.findByProbleme(pb);
                                            if (!acts.isEmpty()) {
                                                conteur += 1;
                                            }
                                        }
                                        if (conteur != 0) {
                                            pourcentageSousAxe = (conteur.doubleValue() / pbs1.size()) * 100;
                                        } else {
                                            pourcentageSousAxe = 0d;
                                        }
                                    }

                                    break;
                                } else {
                                    activiteRegions.clear();
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

    public void uptadeTable() {
        try {

            indicateurs = indicateurFacadeLocal.findBySousAxeNiveauCollecte(sousaxe, 1);

            if ("Edit".equals(mode)) {
                if (activiteRegion != null) {

                    if (activiteRegion.getIdtypestructure() != null) {
                        typestructure = activiteRegion.getIdtypestructure();
                    }

                    if (activiteRegion.getIdresultatattendu() != null) {
                        resultatAttenduRegion = activiteRegion.getIdresultatattendu();
                    }

                    if (activiteRegion.getIdidobjectifOpp() != null) {
                        objectifOppRegion = activiteRegion.getIdidobjectifOpp();
                    }

                    try {
                        sourcefinancement = activiteRegion.getIdsourcefi();
                    } catch (Exception e) {

                    }

                    indicateur = activiteRegion.getIdproblemeRegion().getIdindicateurRegion().getIdindicateur();

                    problemeRegions = problemeRegionFacadeLocal.find(indicateur, SessionMBean.getRegion(), 2);

                    resultatAttenduRegions = resultatAttenduRegionFacadeLocal.findByIndicateur(indicateur);

                    objectifOppRegions = objectifOppRegionFacadeLocal.findByRegion(SessionMBean.getRegion(), indicateur.getIdinterventionpnds());

                    problemeRegion = activiteRegion.getIdproblemeRegion();

                } else {
                    showTypestructure = false;
                }
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCoutglobal() {
        try {
            if (activiteRegion.getCoutunitaire() != null) {
                activiteRegion.setCoutglobal(activiteRegion.getCoutunitaire() * 1);
            } else {
                activiteRegion.setCoutglobal(0.0);
                activiteRegion.setCoutunitaire(0.0);
            }
            /*if (typestructure.getIdtypestructure() != null) {
             List<Structure> structures = structureFacadeLocal.find(SessionMBean.getRegion(), 2, typestructure);
             if (structures.isEmpty()) {
             if (activiteRegion.getCoutunitaire() != null) {
             activiteRegion.setCoutglobal(activiteRegion.getCoutunitaire());
             } else {
             activiteRegion.setCoutunitaire(0.0);
             activiteRegion.setCoutglobal(0.0);
             }
             } else {
             if (activiteRegion.getCoutunitaire() != null) {
             activiteRegion.setCoutglobal(activiteRegion.getCoutunitaire() * structures.size());
             } else {
             activiteRegion.setCoutglobal(0.0);
             activiteRegion.setCoutunitaire(0.0);
             }
             }
             }*/
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

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public List<Annee> getAnnees() {
        annees = anneeFacadeLocal.findAllRange();
        annees.remove(0);
        return annees;
    }

    public void setAnnees(List<Annee> annees) {
        this.annees = annees;
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

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
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

    public ResultatAttenduRegion getResultatAttenduRegion() {
        return resultatAttenduRegion;
    }

    public void setResultatAttenduRegion(ResultatAttenduRegion resultatAttenduRegion) {
        this.resultatAttenduRegion = resultatAttenduRegion;
    }

    public List<ResultatAttenduRegion> getResultatAttenduRegions() {
        return resultatAttenduRegions;
    }

    public void setResultatAttenduRegions(List<ResultatAttenduRegion> resultatAttenduRegions) {
        this.resultatAttenduRegions = resultatAttenduRegions;
    }

    public Typestructure getTypestructure() {
        return typestructure;
    }

    public void setTypestructure(Typestructure typestructure) {
        this.typestructure = typestructure;
    }

    public List<Typestructure> getTypestructures() {
        typestructures = typestructureFacadeLocal.findByRegional();
        return typestructures;
    }

    public void setTypestructures(List<Typestructure> typestructures) {
        this.typestructures = typestructures;
    }

    public Sourcefinancement getSourcefinancement() {
        return sourcefinancement;
    }

    public void setSourcefinancement(Sourcefinancement sourcefinancement) {
        this.sourcefinancement = sourcefinancement;
    }

    public List<Sourcefinancement> getSourcefinancements() {
        sourcefinancements = sourcefinancementFacadeLocal.findAll();
        return sourcefinancements;
    }

    public void setSourcefinancements(List<Sourcefinancement> sourcefinancements) {
        this.sourcefinancements = sourcefinancements;
    }

    public boolean isShowTypestructure() {
        return showTypestructure;
    }

    public void setShowTypestructure(boolean showTypestructure) {
        this.showTypestructure = showTypestructure;
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

    public boolean isShowProbleme() {
        return showProbleme;
    }

    public void setShowProbleme(boolean showProbleme) {
        this.showProbleme = showProbleme;
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

    public ActiviteRegion getActiviteRegion() {
        return activiteRegion;
    }

    public void setActiviteRegion(ActiviteRegion activiteRegion) {
        this.activiteRegion = activiteRegion;
    }

    public List<ActiviteRegion> getActiviteRegions() {
        return activiteRegions;
    }

    public void setActiviteRegions(List<ActiviteRegion> activiteRegions) {
        this.activiteRegions = activiteRegions;
    }

    public ObjectifOppRegion getObjectifOppRegion() {
        return objectifOppRegion;
    }

    public void setObjectifOppRegion(ObjectifOppRegion objectifOppRegion) {
        this.objectifOppRegion = objectifOppRegion;
    }

    public List<ObjectifOppRegion> getObjectifOppRegions() {
        return objectifOppRegions;
    }

    public void setObjectifOppRegions(List<ObjectifOppRegion> objectifOppRegions) {
        this.objectifOppRegions = objectifOppRegions;
    }

    public ElementCout getElementCout() {
        return elementCout;
    }

    public void setElementCout(ElementCout elementCout) {
        this.elementCout = elementCout;
    }

    public List<ActiviteRegionElementCout> getActiviteRegionElementCouts() {
        return activiteRegionElementCouts;
    }

    public void setActiviteRegionElementCouts(List<ActiviteRegionElementCout> activiteRegionElementCouts) {
        this.activiteRegionElementCouts = activiteRegionElementCouts;
    }

    public CoastingDefault getCoastingDefault() {
        return coastingDefault;
    }

    public void setCoastingDefault(CoastingDefault coastingDefault) {
        this.coastingDefault = coastingDefault;
    }

    public ActiviteDefault getActiviteDefault() {
        return activiteDefault;
    }

    public void setActiviteDefault(ActiviteDefault activiteDefault) {
        this.activiteDefault = activiteDefault;
    }

    public List<ActiviteDefault> getActiviteDefaults() {
        return activiteDefaults;
    }

    public void setActiviteDefaults(List<ActiviteDefault> activiteDefaults) {
        this.activiteDefaults = activiteDefaults;
    }

    public boolean isShowSelectActivite() {
        return showSelectActivite;
    }

    public void setShowSelectActivite(boolean showSelectActivite) {
        this.showSelectActivite = showSelectActivite;
    }

    public boolean isShowSelector() {
        return showSelector;
    }

    public void setShowSelector(boolean showSelector) {
        this.showSelector = showSelector;
    }

    public List<ElementCout> getElementCouts() {
        return elementCouts;
    }

    public void setElementCouts(List<ElementCout> elementCouts) {
        this.elementCouts = elementCouts;
    }

    public List<CoastingDefault> getCoastingDefaults() {
        return coastingDefaults;
    }

    public void setCoastingDefaults(List<CoastingDefault> coastingDefaults) {
        this.coastingDefaults = coastingDefaults;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}
