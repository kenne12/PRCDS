/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import controllers.util.SessionMBean;
import entities.Compteutilisateur;
import entities.District;

import entities.Menu;
import entities.Mouchard;
import entities.PartiehauteRegion;
import entities.Privilege;
import entities.Region;
import entities.Utilisateur;
import entities.UtilisateurRegion;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import sessions.CompteutilisateurFacadeLocal;

import sessions.MenuFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.PartiehauteRegionFacadeLocal;
import sessions.PrivilegeFacadeLocal;
import sessions.UtilisateurFacadeLocal;
import sessions.UtilisateurRegionFacadeLocal;
import utilitaire.Utilitaires;

@ManagedBean
@SessionScoped
public class LoginController implements Serializable {

    //private District district = new District();
    private Region region = new Region();

    @EJB
    private PartiehauteRegionFacadeLocal partiehauteRegionFacadeLocal;

    private String cartedistrict = "img-1.png";

    @EJB
    private CompteutilisateurFacadeLocal compteutilisateurFacadeLocal;
    private Compteutilisateur compteutilisateur = new Compteutilisateur();

    @EJB
    private UtilisateurFacadeLocal utilisateurFacade;
    private Utilisateur utilisateur = new Utilisateur();
    String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

    @EJB
    private MouchardFacadeLocal mouchardFacadeLocal;
    private Mouchard mouchard = new Mouchard();

    @EJB
    private PrivilegeFacadeLocal privilegeFacadeLocal;
    private Privilege privilege = new Privilege();
    public static List<String> privilegeUser = new ArrayList<>();
    public static List<String> privilegeTotal = new ArrayList<>();
    private static List<Privilege> privileges = new ArrayList<>();

    @EJB
    private MenuFacadeLocal menuFacadeLocal;
    private static MenuFacadeLocal menuFacadeLocal1;
    private Menu menu = new Menu();
    public static Menu menu1 = new Menu();

    private String language = "fr";

    @EJB
    private UtilisateurRegionFacadeLocal utilisateurRegionFacadeLocal;
    private UtilisateurRegion utilisateurRegion = new UtilisateurRegion();
    private List<UtilisateurRegion> utilisateurRegions = new ArrayList<>();

    private boolean viewSession = true;

    //private 
    public LoginController() {

    }

    @PostConstruct
    private void init() {
        menu1 = new Menu();
    }

    public void validateUsernamePassword() {
        try {

            String password = "";

            password = org.apache.commons.codec.digest.DigestUtils.md5Hex(compteutilisateur.getPassword());

            Compteutilisateur usr = compteutilisateurFacadeLocal.login(compteutilisateur.getLogin(), password);
            if (usr != null) {
                System.err.println("user trouvée");

                if (usr.getEtat()) {
                    utilisateur = usr.getIdutilisateur();
                    HttpSession session = SessionMBean.getSession();

                    session.setAttribute("login", utilisateur.getNom());
                    session.setAttribute("user", utilisateur);
                    session.setAttribute("langue", language);

                    Utilitaires.saveOperation("connexion ", utilisateur, mouchardFacadeLocal);
                    setPrivilegeAll();
                    utilisateurRegions = utilisateurRegionFacadeLocal.findByUtilisateur(utilisateur.getIdutilisateur());
                    setPrivilegeUser();

                    FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/faces/index.xhtml");
                } else {
                    Utilitaires.saveOperation("tentative de connection avec un compte bloqué", usr.getIdutilisateur(), mouchardFacadeLocal);
                    JsfUtil.addErrorMessage("Votre compte est bloqué");
                }

            } else {
                System.err.println("echec d'authentification");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login et mot de passe incorrets", "Please enter correct username and Password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPrivilegeAll() {
        List<Menu> menus = menuFacadeLocal.findAll();
        for (Menu m : menus) {
            privilegeTotal.add(m.getRessource());
        }
    }

    public void setPrivilegeUser() {
        if (SessionMBean.getUser() != null) {
            privileges = privilegeFacadeLocal.findByGroupeUser(SessionMBean.getUser().getIdgroupeutilisateur().getIdgroupeutilisateur(), true, true);
            if (privileges.isEmpty()) {
                privilegeUser = new ArrayList<>();
            } else {
                privilegeUser.clear();
                for (Privilege p : privileges) {
                    privilegeUser.add(p.getIdmenu().getRessource());
                }
            }
        } else {
            privilegeUser = new ArrayList<>();
        }
    }

    //logout event, invalidate session
    public void logout() {
        HttpSession session = SessionMBean.getSession();
        Utilisateur usr = SessionMBean.getUser();
        session.invalidate();

        String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

        try {
            Utilitaires.saveOperation("Déconnexion", usr, mouchardFacadeLocal);
            FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/faces/login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void watcher() {
        try {
            if (SessionMBean.getUser() == null) {
                String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/faces/login.xhtml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void findMenu(String ressource) {
        try {
            menu1 = menuFacadeLocal1.findByRessource(ressource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initSession() {
        try {
            if (utilisateurRegion.getIdutilisateurRegion() != null) {
                utilisateurRegion = utilisateurRegionFacadeLocal.find(utilisateurRegion.getIdutilisateurRegion());
                HttpSession session = SessionMBean.getSession();
                session.setAttribute("region", utilisateurRegion.getIdregion());
                List<PartiehauteRegion> partiehaute = partiehauteRegionFacadeLocal.findByRegion(utilisateurRegion.getIdregion().getIdregion());
                if (partiehaute.isEmpty()) {
                    cartedistrict = "Tulips.jpg";
                } else if (partiehaute.get(0).getCarte() != null) {

                    File f = new File(Utilitaires.path + "/report/images/" + partiehaute.get(0).getCarte());
                    if (f.exists()) {
                        cartedistrict = "" + partiehaute.get(0).getCarte();
                    } else {
                        cartedistrict = "Tulips.jpg";
                    }
                } else {
                    cartedistrict = "Tulips.jpg";
                }
                viewSession = false;
            } else {
                JsfUtil.addErrorMessage("Aucune région selectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String switchFr() {
        language = "fr";
        return null;
    }

    public String switchEn() {
        language = "en";
        return null;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Compteutilisateur getCompteutilisateur() {
        return compteutilisateur;
    }

    public void setCompteutilisateur(Compteutilisateur compteutilisateur) {
        this.compteutilisateur = compteutilisateur;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isViewSession() {
        return viewSession;
    }

    public void setViewSession(boolean viewSession) {
        this.viewSession = viewSession;
    }

    public String getCartedistrict() {
        return cartedistrict;
    }

    public UtilisateurRegion getUtilisateurRegion() {
        return utilisateurRegion;
    }

    public void setUtilisateurRegion(UtilisateurRegion utilisateurRegion) {
        this.utilisateurRegion = utilisateurRegion;
    }

    public List<UtilisateurRegion> getUtilisateurRegions() {
        return utilisateurRegions;
    }

    public void setUtilisateurRegions(List<UtilisateurRegion> utilisateurRegions) {
        this.utilisateurRegions = utilisateurRegions;
    }

    public void setCartedistrict(String cartedistrict) {
        this.cartedistrict = cartedistrict;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
    
    

}
