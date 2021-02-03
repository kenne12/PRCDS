/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kenne
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rubriquenotationprobleme.findAll", query = "SELECT r FROM Rubriquenotationprobleme r"),
    @NamedQuery(name = "Rubriquenotationprobleme.findByIdrubriquenotationprobleme", query = "SELECT r FROM Rubriquenotationprobleme r WHERE r.idrubriquenotationprobleme = :idrubriquenotationprobleme"),
    @NamedQuery(name = "Rubriquenotationprobleme.findByNomFr", query = "SELECT r FROM Rubriquenotationprobleme r WHERE r.nomFr = :nomFr"),
    @NamedQuery(name = "Rubriquenotationprobleme.findByNomEn", query = "SELECT r FROM Rubriquenotationprobleme r WHERE r.nomEn = :nomEn")})
public class Rubriquenotationprobleme implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idrubriquenotationprobleme;
    @Size(max = 254)
    @Column(name = "nom_fr")
    private String nomFr;
    @Size(max = 2147483647)
    @Column(name = "nom_en")
    private String nomEn;
    @OneToMany(mappedBy = "idrubriquenotationprobleme", fetch = FetchType.LAZY)
    private List<Sousrubriquenotationprobleme> sousrubriquenotationproblemeList;

    public Rubriquenotationprobleme() {
    }

    public Rubriquenotationprobleme(Integer idrubriquenotationprobleme) {
        this.idrubriquenotationprobleme = idrubriquenotationprobleme;
    }

    public Integer getIdrubriquenotationprobleme() {
        return idrubriquenotationprobleme;
    }

    public void setIdrubriquenotationprobleme(Integer idrubriquenotationprobleme) {
        this.idrubriquenotationprobleme = idrubriquenotationprobleme;
    }

    public String getNomFr() {
        return nomFr;
    }

    public void setNomFr(String nomFr) {
        this.nomFr = nomFr;
    }

    public String getNomEn() {
        return nomEn;
    }

    public void setNomEn(String nomEn) {
        this.nomEn = nomEn;
    }

    @XmlTransient
    public List<Sousrubriquenotationprobleme> getSousrubriquenotationproblemeList() {
        return sousrubriquenotationproblemeList;
    }

    public void setSousrubriquenotationproblemeList(List<Sousrubriquenotationprobleme> sousrubriquenotationproblemeList) {
        this.sousrubriquenotationproblemeList = sousrubriquenotationproblemeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrubriquenotationprobleme != null ? idrubriquenotationprobleme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rubriquenotationprobleme)) {
            return false;
        }
        Rubriquenotationprobleme other = (Rubriquenotationprobleme) object;
        if ((this.idrubriquenotationprobleme == null && other.idrubriquenotationprobleme != null) || (this.idrubriquenotationprobleme != null && !this.idrubriquenotationprobleme.equals(other.idrubriquenotationprobleme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Rubriquenotationprobleme[ idrubriquenotationprobleme=" + idrubriquenotationprobleme + " ]";
    }
    
}
