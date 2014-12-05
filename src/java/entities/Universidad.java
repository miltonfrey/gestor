/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author cba
 */
@Entity
@Table(name = "universidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Universidad.findAll", query = "SELECT u FROM Universidad u"),
    @NamedQuery(name = "Universidad.findByNombre", query = "SELECT u FROM Universidad u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "Universidad.findByWeb", query = "SELECT u FROM Universidad u WHERE u.web = :web"),
    @NamedQuery(name = "Universidad.findByCodUniversidad", query = "SELECT u FROM Universidad u WHERE u.codUniversidad = :codUniversidad")})
public class Universidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "info")
    private String info;
    @Size(max = 50)
    @Column(name = "web")
    private String web;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "codUniversidad")
    private String codUniversidad;
    @JoinColumn(name = "pais", referencedColumnName = "nombre")
    @ManyToOne
    private Pais pais;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "universidad")
    private Set<Asignatura> asignaturaSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nombreUniversidad")
    private Set<Movilidad> movilidadSet;

    public Universidad() {
    }

    public Universidad(String nombre) {
        this.nombre = nombre;
    }

    public Universidad(String nombre, String codUniversidad) {
        this.nombre = nombre;
        this.codUniversidad = codUniversidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getCodUniversidad() {
        return codUniversidad;
    }

    public void setCodUniversidad(String codUniversidad) {
        this.codUniversidad = codUniversidad;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    @XmlTransient
    public Set<Asignatura> getAsignaturaSet() {
        return asignaturaSet;
    }

    public void setAsignaturaSet(Set<Asignatura> asignaturaSet) {
        this.asignaturaSet = asignaturaSet;
    }

    @XmlTransient
    public Set<Movilidad> getMovilidadSet() {
        return movilidadSet;
    }

    public void setMovilidadSet(Set<Movilidad> movilidadSet) {
        this.movilidadSet = movilidadSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Universidad)) {
            return false;
        }
        Universidad other = (Universidad) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Universidad[ nombre=" + nombre + " ]";
    }
    
}
