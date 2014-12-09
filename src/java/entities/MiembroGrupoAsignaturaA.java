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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author abc
 */
@Entity
@Table(name = "miembro_grupo_asignatura_a")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MiembroGrupoAsignaturaA.findAll", query = "SELECT m FROM MiembroGrupoAsignaturaA m"),
    @NamedQuery(name = "MiembroGrupoAsignaturaA.findByIdmiembroGrupoAsignaturaA", query = "SELECT m FROM MiembroGrupoAsignaturaA m WHERE m.idmiembroGrupoAsignaturaA = :idmiembroGrupoAsignaturaA")})
public class MiembroGrupoAsignaturaA implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmiembro_grupo_asignatura_a")
    private Integer idmiembroGrupoAsignaturaA;
    @JoinColumn(name = "idEquivalencia", referencedColumnName = "idequivalencia")
    @ManyToOne(fetch = FetchType.LAZY)
    private Equivalencia idEquivalencia;
    @JoinColumns({
        @JoinColumn(name = "codAsignatura", referencedColumnName = "codAsignatura"),
        @JoinColumn(name = "nombreUniversidad", referencedColumnName = "nombreUniversidad")})
    @ManyToOne(fetch = FetchType.LAZY)
    private Asignatura asignatura;

    public MiembroGrupoAsignaturaA() {
    }

    public MiembroGrupoAsignaturaA(Integer idmiembroGrupoAsignaturaA) {
        this.idmiembroGrupoAsignaturaA = idmiembroGrupoAsignaturaA;
    }

    public Integer getIdmiembroGrupoAsignaturaA() {
        return idmiembroGrupoAsignaturaA;
    }

    public void setIdmiembroGrupoAsignaturaA(Integer idmiembroGrupoAsignaturaA) {
        this.idmiembroGrupoAsignaturaA = idmiembroGrupoAsignaturaA;
    }

    public Equivalencia getIdEquivalencia() {
        return idEquivalencia;
    }

    public void setIdEquivalencia(Equivalencia idEquivalencia) {
        this.idEquivalencia = idEquivalencia;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    
    @Override
    public String toString() {
        return "pojos.MiembroGrupoAsignaturaA[ idmiembroGrupoAsignaturaA=" + idmiembroGrupoAsignaturaA + " ]";
    }
    
}
