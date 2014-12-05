/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author cba
 */
@Entity
@Table(name = "movilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Movilidad.findAll", query = "SELECT m FROM Movilidad m"),
    @NamedQuery(name = "Movilidad.findByCodMovilidad", query = "SELECT m FROM Movilidad m WHERE m.codMovilidad = :codMovilidad"),
    @NamedQuery(name = "Movilidad.findByFechaInicio", query = "SELECT m FROM Movilidad m WHERE m.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Movilidad.findByFechaFin", query = "SELECT m FROM Movilidad m WHERE m.fechaFin = :fechaFin"),
    @NamedQuery(name = "Movilidad.findByEstado", query = "SELECT m FROM Movilidad m WHERE m.estado = :estado")})
public class Movilidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codMovilidad")
    private Integer codMovilidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaFin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "estado")
    private String estado;
    @OneToMany(mappedBy = "idMovilidad")
    private Set<Contrato> contratoSet;
    @JoinColumn(name = "loginUsuario", referencedColumnName = "login")
    @ManyToOne(optional = false)
    private Usuario loginUsuario;
    @JoinColumn(name = "nombreUniversidad", referencedColumnName = "nombre")
    @ManyToOne(optional = false)
    private Universidad nombreUniversidad;
    @JoinColumn(name = "cursoAcademico", referencedColumnName = "cursoAcademico")
    @ManyToOne
    private Cursoacademico cursoAcademico;

    public Movilidad() {
    }

    public Movilidad(Integer codMovilidad) {
        this.codMovilidad = codMovilidad;
    }

    public Movilidad(Integer codMovilidad, Date fechaInicio, Date fechaFin, String estado) {
        this.codMovilidad = codMovilidad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public Integer getCodMovilidad() {
        return codMovilidad;
    }

    public void setCodMovilidad(Integer codMovilidad) {
        this.codMovilidad = codMovilidad;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Set<Contrato> getContratoSet() {
        return contratoSet;
    }

    public void setContratoSet(Set<Contrato> contratoSet) {
        this.contratoSet = contratoSet;
    }

    public Usuario getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(Usuario loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public Universidad getNombreUniversidad() {
        return nombreUniversidad;
    }

    public void setNombreUniversidad(Universidad nombreUniversidad) {
        this.nombreUniversidad = nombreUniversidad;
    }

    public Cursoacademico getCursoAcademico() {
        return cursoAcademico;
    }

    public void setCursoAcademico(Cursoacademico cursoAcademico) {
        this.cursoAcademico = cursoAcademico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codMovilidad != null ? codMovilidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movilidad)) {
            return false;
        }
        Movilidad other = (Movilidad) object;
        if ((this.codMovilidad == null && other.codMovilidad != null) || (this.codMovilidad != null && !this.codMovilidad.equals(other.codMovilidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Movilidad[ codMovilidad=" + codMovilidad + " ]";
    }
    
}
