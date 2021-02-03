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
@Table(name = "activite_structure_region")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActiviteStructureRegion.findAll", query = "SELECT a FROM ActiviteStructureRegion a"),
    @NamedQuery(name = "ActiviteStructureRegion.findByIdactiviteStructureRegion", query = "SELECT a FROM ActiviteStructureRegion a WHERE a.idactiviteStructureRegion = :idactiviteStructureRegion"),
    @NamedQuery(name = "ActiviteStructureRegion.findByPrograme", query = "SELECT a FROM ActiviteStructureRegion a WHERE a.programe = :programe"),
    @NamedQuery(name = "ActiviteStructureRegion.findByCout", query = "SELECT a FROM ActiviteStructureRegion a WHERE a.cout = :cout")})
public class ActiviteStructureRegion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idactivite_structure_region")
    private Long idactiviteStructureRegion;
    private Boolean programe;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double cout;
    @JoinColumn(name = "idactivite_region", referencedColumnName = "idactivite_region")
    @ManyToOne(fetch = FetchType.LAZY)
    private ActiviteRegion idactiviteRegion;
    @JoinColumn(name = "idannee", referencedColumnName = "idannee")
    @ManyToOne(fetch = FetchType.LAZY)
    private Annee idannee;
    @JoinColumn(name = "idstructure", referencedColumnName = "idstructure")
    @ManyToOne(fetch = FetchType.LAZY)
    private Structure idstructure;

    public ActiviteStructureRegion() {
    }

    public ActiviteStructureRegion(Long idactiviteStructureRegion) {
        this.idactiviteStructureRegion = idactiviteStructureRegion;
    }

    public Long getIdactiviteStructureRegion() {
        return idactiviteStructureRegion;
    }

    public void setIdactiviteStructureRegion(Long idactiviteStructureRegion) {
        this.idactiviteStructureRegion = idactiviteStructureRegion;
    }

    public Boolean getPrograme() {
        return programe;
    }

    public void setPrograme(Boolean programe) {
        this.programe = programe;
    }

    public Double getCout() {
        return cout;
    }

    public void setCout(Double cout) {
        this.cout = cout;
    }

    public ActiviteRegion getIdactiviteRegion() {
        return idactiviteRegion;
    }

    public void setIdactiviteRegion(ActiviteRegion idactiviteRegion) {
        this.idactiviteRegion = idactiviteRegion;
    }

    public Annee getIdannee() {
        return idannee;
    }

    public void setIdannee(Annee idannee) {
        this.idannee = idannee;
    }

    public Structure getIdstructure() {
        return idstructure;
    }

    public void setIdstructure(Structure idstructure) {
        this.idstructure = idstructure;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idactiviteStructureRegion != null ? idactiviteStructureRegion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActiviteStructureRegion)) {
            return false;
        }
        ActiviteStructureRegion other = (ActiviteStructureRegion) object;
        if ((this.idactiviteStructureRegion == null && other.idactiviteStructureRegion != null) || (this.idactiviteStructureRegion != null && !this.idactiviteStructureRegion.equals(other.idactiviteStructureRegion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ActiviteStructureRegion[ idactiviteStructureRegion=" + idactiviteStructureRegion + " ]";
    }
    
}
