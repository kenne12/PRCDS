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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kenne
 */
@Entity
@Table(name = "situation_socioculturel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SituationSocioculturel.findAll", query = "SELECT s FROM SituationSocioculturel s"),
    @NamedQuery(name = "SituationSocioculturel.findByIdsitutationScRegion", query = "SELECT s FROM SituationSocioculturel s WHERE s.idsitutationScRegion = :idsitutationScRegion"),
    @NamedQuery(name = "SituationSocioculturel.findByEducation", query = "SELECT s FROM SituationSocioculturel s WHERE s.education = :education"),
    @NamedQuery(name = "SituationSocioculturel.findByStatut", query = "SELECT s FROM SituationSocioculturel s WHERE s.statut = :statut"),
    @NamedQuery(name = "SituationSocioculturel.findByFacteurculturel", query = "SELECT s FROM SituationSocioculturel s WHERE s.facteurculturel = :facteurculturel"),
    @NamedQuery(name = "SituationSocioculturel.findByFacteurSocioculturel", query = "SELECT s FROM SituationSocioculturel s WHERE s.facteurSocioculturel = :facteurSocioculturel")})
public class SituationSocioculturel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idsitutation_sc_region")
    private Integer idsitutationScRegion;
    @Size(max = 2147483647)
    private String education;
    @Size(max = 2147483647)
    private String statut;
    @Size(max = 2147483647)
    private String facteurculturel;
    @Size(max = 2147483647)
    @Column(name = "facteur_socioculturel")
    private String facteurSocioculturel;
    @JoinColumn(name = "idregion", referencedColumnName = "idregion")
    @ManyToOne(fetch = FetchType.LAZY)
    private Region idregion;

    public SituationSocioculturel() {
    }

    public SituationSocioculturel(Integer idsitutationScRegion) {
        this.idsitutationScRegion = idsitutationScRegion;
    }

    public Integer getIdsitutationScRegion() {
        return idsitutationScRegion;
    }

    public void setIdsitutationScRegion(Integer idsitutationScRegion) {
        this.idsitutationScRegion = idsitutationScRegion;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getFacteurculturel() {
        return facteurculturel;
    }

    public void setFacteurculturel(String facteurculturel) {
        this.facteurculturel = facteurculturel;
    }

    public String getFacteurSocioculturel() {
        return facteurSocioculturel;
    }

    public void setFacteurSocioculturel(String facteurSocioculturel) {
        this.facteurSocioculturel = facteurSocioculturel;
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
        hash += (idsitutationScRegion != null ? idsitutationScRegion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SituationSocioculturel)) {
            return false;
        }
        SituationSocioculturel other = (SituationSocioculturel) object;
        if ((this.idsitutationScRegion == null && other.idsitutationScRegion != null) || (this.idsitutationScRegion != null && !this.idsitutationScRegion.equals(other.idsitutationScRegion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SituationSocioculturel[ idsitutationScRegion=" + idsitutationScRegion + " ]";
    }
    
}
