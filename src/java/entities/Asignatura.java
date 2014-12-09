/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author abc
 */
@Entity
@Table(name = "asignatura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asignatura.findAll", query = "SELECT a FROM Asignatura a"),
    @NamedQuery(name = "Asignatura.findByCodAsignatura", query = "SELECT a FROM Asignatura a WHERE a.asignaturaPK.codAsignatura = :codAsignatura"),
    @NamedQuery(name = "Asignatura.findByNombreAsignatura", query = "SELECT a FROM Asignatura a WHERE a.nombreAsignatura = :nombreAsignatura"),
    @NamedQuery(name = "Asignatura.findByCreditos", query = "SELECT a FROM Asignatura a WHERE a.creditos = :creditos"),
    @NamedQuery(name = "Asignatura.findByPeriodo", query = "SELECT a FROM Asignatura a WHERE a.periodo = :periodo"),
    @NamedQuery(name = "Asignatura.findByWebAsignatura", query = "SELECT a FROM Asignatura a WHERE a.webAsignatura = :webAsignatura"),
    @NamedQuery(name = "Asignatura.findByFacultad", query = "SELECT a FROM Asignatura a WHERE a.facultad = :facultad"),
    @NamedQuery(name = "Asignatura.findByTitulacion", query = "SELECT a FROM Asignatura a WHERE a.titulacion = :titulacion"),
    @NamedQuery(name = "Asignatura.findByNombreUniversidad", query = "SELECT a FROM Asignatura a WHERE a.asignaturaPK.nombreUniversidad = :nombreUniversidad")})
public class Asignatura implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AsignaturaPK asignaturaPK;
    @Size(max = 50)
    @Column(name = "nombreAsignatura")
    private String nombreAsignatura;
    @Column(name = "creditos")
    private Short creditos;
    @Size(max = 10)
    @Column(name = "periodo")
    private String periodo;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "infoAsigantura")
    private String infoAsigantura;
    @Size(max = 200)
    @Column(name = "webAsignatura")
    private String webAsignatura;
    @Size(max = 50)
    @Column(name = "facultad")
    private String facultad;
    @Size(max = 45)
    @Column(name = "titulacion")
    private String titulacion;
    @OneToMany(mappedBy = "asignatura", fetch = FetchType.LAZY)
    private Set<MiembroGrupoAsignaturaB> miembroGrupoAsignaturaBSet;
    @JoinColumn(name = "nombreUniversidad", referencedColumnName = "nombre", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Universidad universidad;
    @OneToMany(mappedBy = "asignatura", fetch = FetchType.LAZY)
    private Set<MiembroGrupoAsignaturaA> miembroGrupoAsignaturaASet;

    public Asignatura() {
    }

    public Asignatura(AsignaturaPK asignaturaPK) {
        this.asignaturaPK = asignaturaPK;
    }

    public Asignatura(int codAsignatura, String nombreUniversidad) {
        this.asignaturaPK = new AsignaturaPK(codAsignatura, nombreUniversidad);
    }

    public AsignaturaPK getAsignaturaPK() {
        return asignaturaPK;
    }

    public void setAsignaturaPK(AsignaturaPK asignaturaPK) {
        this.asignaturaPK = asignaturaPK;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public Short getCreditos() {
        return creditos;
    }

    public void setCreditos(Short creditos) {
        this.creditos = creditos;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getInfoAsigantura() {
        return infoAsigantura;
    }

    public void setInfoAsigantura(String infoAsigantura) {
        this.infoAsigantura = infoAsigantura;
    }

    public String getWebAsignatura() {
        return webAsignatura;
    }

    public void setWebAsignatura(String webAsignatura) {
        this.webAsignatura = webAsignatura;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getTitulacion() {
        return titulacion;
    }

    public void setTitulacion(String titulacion) {
        this.titulacion = titulacion;
    }

    @XmlTransient
    public Set<MiembroGrupoAsignaturaB> getMiembroGrupoAsignaturaBSet() {
        return miembroGrupoAsignaturaBSet;
    }

    public void setMiembroGrupoAsignaturaBSet(Set<MiembroGrupoAsignaturaB> miembroGrupoAsignaturaBSet) {
        this.miembroGrupoAsignaturaBSet = miembroGrupoAsignaturaBSet;
    }

    public Universidad getUniversidad() {
        return universidad;
    }

    public void setUniversidad(Universidad universidad) {
        this.universidad = universidad;
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
        hash += (asignaturaPK != null ? asignaturaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asignatura)) {
            return false;
        }
        Asignatura other = (Asignatura) object;
        if ((this.asignaturaPK == null && other.asignaturaPK != null) || (this.asignaturaPK != null && !this.asignaturaPK.equals(other.asignaturaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojos.Asignatura[ asignaturaPK=" + asignaturaPK + " ]";
    }
    
}
