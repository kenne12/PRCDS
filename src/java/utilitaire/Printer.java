/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaire;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author kenne gervais
 */
public class Printer {

    private ResourceBundle rs = ResourceBundle.getBundle("Langues", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    private JasperPrint jasperPrint;
    private static String user = "postgres";
    private static String password = "batrapi";
    public static String driver = "org.postgresql.Driver";
    private static String url = "jdbc:postgresql://localhost:5432/pdsd_prcds";
    //private static String url = "jdbc:postgresql://192.168.1.1:5432/pdsd_prcds";
    public static Connection con;

    public Printer() {

    }

    /**
     * ****************************** print notma
     *
     * @param pathl
     *
     * @param parameters
     *
     * @throws
     * java.lang.Exception******************************************************
     */
    public void print(String path, Map parameters) throws Exception {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(path);

            parameters.put("REPORT_LOCALE", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            parameters.put("REPORT_RESOURCE_BUNDLE", rs);
            jasperPrint = JasperFillManager.fillReport(reportPath, parameters, con);

            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + path.substring(path.lastIndexOf("/"), path.lastIndexOf(".")) + ".pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {
            throw new IOException(e);
        } catch (JRException e) {
            throw new JRException(e);
        }
    }

}
