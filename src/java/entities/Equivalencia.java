/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author cba
 */
@Entity
@Table(name = "equivalencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equivalencia.findAll", query = "SELECT e FROM Equivalencia e"),
    @NamedQuery(name = "Equivalencia.findByIdequivalencia", query = "SELECT e FROM Equivalencia e WHERE e.idequivalencia = :idequivalencia"),
    @NamedQuery(name = "Equivalencia.findByVisible", query = "SELECT e FROM Equivalencia e WHERE e.visible = :visible")})
public class Equivalencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idequivalencia")
    private Integer idequivalencia;
    @Size(max = 2)
    @Column(name = "visible")
    private String visible;
    @ManyToMany(mappedBy = "equivalenciaSet")
    private Set<Contrato> contratoSet;
    @OneToMany(mappedBy = "idEquivalencia")
    private Set<MiembroGrupoAsignaturaB> miembroGrupoAsignaturaBSet;
    @OneToMany(mappedBy = "idEquivalencia")
    private Set<MiembroGrupoAsignaturaA> miembroGrupoAsignaturaASet;

    public Equivalencia() {
    }

    public Equivalencia(Integer idequivalencia) {
        this.idequivalencia = idequivalencia;
    }

    public Integer getIdequivalencia() {
        return idequivalencia;
    }

    public void setIdequivalencia(Integer idequivalencia) {
        this.idequivalencia = idequivalencia;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    @XmlTransient
    public Set<Contrato> getContratoSet() {
        return contratoSet;
    }

    public void setContratoSet(Set<Contrato> contratoSet) {
        this.contratoSet = contratoSet;
    }

    @XmlTransient
    public Set<MiembroGrupoAsignaturaB> getMiembroGrupoAsignaturaBSet() {
        return miembroGrupoAsignaturaBSet;
    }

    public void setMiembroGrupoAsignaturaBSet(Set<MiembroGrupoAsignaturaB> miembroGrupoAsignaturaBSet) {
        this.miembroGrupoAsignaturaBSet = miembroGrupoAsignaturaBSet;
    }

    @XmlTransient
    public Set<MiembroGrupoAsignaturaA> getMiembroGrupoAsignaturaASet() {
        return miembroGrupoAsignaturaASet;
    }

    public void setMiembroGrupoAsignaturaASet(Set<MiembroGrupoAsignaturaA> miembroGrupoAsignaturaASet) {
        this.miembroGrupoAsignaturaASet = miembroGrupoAsignaturaASet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idequivalencia != null ? idequivalencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equivalencia)) {
            return false;
        }
        Equivalencia other = (Equivalencia) object;
        if ((this.idequivalencia == null && other.idequivalencia != null) || (this.idequivalencia != null && !this.idequivalencia.equals(other.idequivalencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Equivalencia[ idequivalencia=" + idequivalencia + " ]";
    }
    
}
