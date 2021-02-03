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
    @NamedQuery(name = "Typeacteur.findAll", query = "SELECT t FROM Typeacteur t"),
    @NamedQuery(name = "Typeacteur.findByIdtypeacteur", query = "SELECT t FROM Typeacteur t WHERE t.idtypeacteur = :idtypeacteur"),
    @NamedQuery(name = "Typeacteur.findByNomFr", query = "SELECT t FROM Typeacteur t WHERE t.nomFr = :nomFr"),
    @NamedQuery(name = "Typeacteur.findByNomEn", query = "SELECT t FROM Typeacteur t WHERE t.nomEn = :nomEn")})
public class Typeacteur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idtypeacteur;
    @Size(max = 254)
    @Column(name = "nom_fr")
    private String nomFr;
    @Size(max = 2147483647)
    @Column(name = "nom_en")
    private String nomEn;
    @OneToMany(mappedBy = "idtypeacteur", fetch = FetchType.LAZY)
    private List<Acteur> acteurList;

    public Typeacteur() {
    }

    public Typeacteur(Integer idtypeacteur) {
        this.idtypeacteur = idtypeacteur;
    }

    public Integer getIdtypeacteur() {
        return idtypeacteur;
    }

    public void setIdtypeacteur(Integer idtypeacteur) {
        this.idtypeacteur = idtypeacteur;
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
    public List<Acteur> getActeurList() {
        return acteurList;
    }

    public void setActeurList(List<Acteur> acteurList) {
        this.acteurList = acteurList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtypeacteur != null ? idtypeacteur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Typeacteur)) {
            return false;
        }
        Typeacteur other = (Typeacteur) object;
        if ((this.idtypeacteur == null && other.idtypeacteur != null) || (this.idtypeacteur != null && !this.idtypeacteur.equals(other.idtypeacteur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Typeacteur[ idtypeacteur=" + idtypeacteur + " ]";
    }
    
}
