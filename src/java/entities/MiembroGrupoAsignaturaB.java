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
@Table(name = "miembro_grupo_asignatura_b")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MiembroGrupoAsignaturaB.findAll", query = "SELECT m FROM MiembroGrupoAsignaturaB m"),
    @NamedQuery(name = "MiembroGrupoAsignaturaB.findByIdmiembroGrupoAsignaturaB", query = "SELECT m FROM MiembroGrupoAsignaturaB m WHERE m.idmiembroGrupoAsignaturaB = :idmiembroGrupoAsignaturaB")})
public class MiembroGrupoAsignaturaB implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmiembro_grupo_asignatura_b")
    private Integer idmiembroGrupoAsignaturaB;
    @JoinColumn(name = "idEquivalencia", referencedColumnName = "idequivalencia")
    @ManyToOne(fetch = FetchType.LAZY)
    private Equivalencia idEquivalencia;
    @JoinColumns({
        @JoinColumn(name = "codAsignatura", referencedColumnName = "codAsignatura"),
        @JoinColumn(name = "nombreUniversidad", referencedColumnName = "nombreUniversidad")})
    @ManyToOne(fetch = FetchType.LAZY)
    private Asignatura asignatura;

    public MiembroGrupoAsignaturaB() {
    }

    public MiembroGrupoAsignaturaB(Integer idmiembroGrupoAsignaturaB) {
        this.idmiembroGrupoAsignaturaB = idmiembroGrupoAsignaturaB;
    }

    public Integer getIdmiembroGrupoAsignaturaB() {
        return idmiembroGrupoAsignaturaB;
    }

    public void setIdmiembroGrupoAsignaturaB(Integer idmiembroGrupoAsignaturaB) {
        this.idmiembroGrupoAsignaturaB = idmiembroGrupoAsignaturaB;
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
        return "pojos.MiembroGrupoAsignaturaB[ idmiembroGrupoAsignaturaB=" + idmiembroGrupoAsignaturaB + " ]";
    }
    
}
