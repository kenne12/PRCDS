/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.SessionMBean;
import entities.District;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessions.DistrictFacadeLocal;

import utilitaire.Printer;

/**
 *
 * @author kenne
 */
@ManagedBean
@ViewScoped
public class PrintpdsdController {

    /**
     * Creates a new instance of PlanoperationnelController
     */
    private Printer printer = new Printer();

    @EJB
    DistrictFacadeLocal districtFacadeLocal;
    private District district = new District();
    private List<District> districts = new ArrayList<>();

    public PrintpdsdController() {

    }

    @PostConstruct
    private void init() {
        printer = new Printer();
    }

    public void print() {
        try {
            Map parameter = new HashMap();
            parameter.put("idregion", SessionMBean.getRegion().getIdregion());
            if ("fr".equals(SessionMBean.getLangue())) {
                printer.print("/report/couverture1.jasper", parameter);
            } else {
                printer.print("/report/couverture1.jasper", parameter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void printPriorisation() {
        try {
            Map parameter = new HashMap();
            parameter.put("idregion", SessionMBean.getRegion().getIdregion());
            if ("fr".equals(SessionMBean.getLangue())) {
                printer.print("/report/priorisationpb.jasper", parameter);
            } else {
                printer.print("/report/priorisationpb.jasper", parameter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void printActivitecorrectrice() {
        try {
            Map parameter = new HashMap();
            parameter.put("idregion", SessionMBean.getRegion().getIdregion());
            if ("fr".equals(SessionMBean.getLangue())) {
                printer.print("/report/activitescorrectrice2pleniere.jasper", parameter);
            } else {
                printer.print("/report/activitescorrectrice2pleniere.jasper", parameter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void printActiviteconsolidation() {
        try {
            Map parameter = new HashMap();
            parameter.put("idregion", SessionMBean.getRegion().getIdregion());
            if ("fr".equals(SessionMBean.getLangue())) {
                printer.print("/report/activitesconsolidations2pleniere.jasper", parameter);
            } else {
                printer.print("/report/activitesconsolidations2pleniere.jasper", parameter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printBesoinEquipement() {
        try {

            Map parameter = new HashMap();
            //parameter.put("iddistrict", SessionMBean.getDistrict().getIddistrict());

            printer.print("/report/pdsd/besoininvestissementequip.jasper", parameter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printBesoinInvestissement() {
        try {

            Map parameter = new HashMap();
            //parameter.put("iddistrict", SessionMBean.getDistrict().getIddistrict());

            printer.print("/report/pdsd/besoinsenivestissement.jasper", parameter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public List<District> getDistricts() {
        districts = districtFacadeLocal.findAll();
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

}
