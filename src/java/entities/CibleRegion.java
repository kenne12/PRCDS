/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kenne
 */
@Entity
@Table(name = "cible_region")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CibleRegion.findAll", query = "SELECT c FROM CibleRegion c"),
    @NamedQuery(name = "CibleRegion.findByIdcibleRegion", query = "SELECT c FROM CibleRegion c WHERE c.idcibleRegion = :idcibleRegion"),
    @NamedQuery(name = "CibleRegion.findByValeur", query = "SELECT c FROM CibleRegion c WHERE c.valeur = :valeur")})
public class CibleRegion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idcible_region")
    private Long idcibleRegion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double valeur;
    @JoinColumn(name = "idannee", referencedColumnName = "idannee")
    @ManyToOne(fetch = FetchType.LAZY)
    private Annee idannee;
    @JoinColumn(name = "idindicateur", referencedColumnName = "idindicateur")
    @ManyToOne(fetch = FetchType.LAZY)
    private Indicateur idindicateur;
    @JoinColumn(name = "idregion", referencedColumnName = "idregion")
    @ManyToOne(fetch = FetchType.LAZY)
    private Region idregion;

    public CibleRegion() {
    }

    public CibleRegion(Long idcibleRegion) {
        this.idcibleRegion = idcibleRegion;
    }

    public Long getIdcibleRegion() {
        return idcibleRegion;
    }

    public void setIdcibleRegion(Long idcibleRegion) {
        this.idcibleRegion = idcibleRegion;
    }

    public Double getValeur() {
        return valeur;
    }

    public void setValeur(Double valeur) {
        this.valeur = valeur;
    }

    public Annee getIdannee() {
        return idannee;
    }

    public void setIdannee(Annee idannee) {
        this.idannee = idannee;
    }

    public Indicateur getIdindicateur() {
        return idindicateur;
    }

    public void setIdindicateur(Indicateur idindicateur) {
        this.idindicateur = idindicateur;
    }

    public Region getIdregion() {
        return idregion;
    }

    public void setIdregion(Region idregion) {
        this.idregion = idregion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcibleRegion != null ? idcibleRegion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CibleRegion)) {
            return false;
        }
        CibleRegion other = (CibleRegion) object;
        if ((this.idcibleRegion == null && other.idcibleRegion != null) || (this.idcibleRegion != null && !this.idcibleRegion.equals(other.idcibleRegion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.CibleRegion[ idcibleRegion=" + idcibleRegion + " ]";
    }
    
}
