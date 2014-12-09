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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author abc
 */
@Entity
@Table(name = "estado_movilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoMovilidad.findAll", query = "SELECT e FROM EstadoMovilidad e"),
    @NamedQuery(name = "EstadoMovilidad.findByEstadoMovilidad", query = "SELECT e FROM EstadoMovilidad e WHERE e.estadoMovilidad = :estadoMovilidad")})
public class EstadoMovilidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "estadoMovilidad")
    private String estadoMovilidad;

    public EstadoMovilidad() {
    }

    public EstadoMovilidad(String estadoMovilidad) {
        this.estadoMovilidad = estadoMovilidad;
    }

    public String getEstadoMovilidad() {
        return estadoMovilidad;
    }

    public void setEstadoMovilidad(String estadoMovilidad) {
        this.estadoMovilidad = estadoMovilidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estadoMovilidad != null ? estadoMovilidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoMovilidad)) {
            return false;
        }
        EstadoMovilidad other = (EstadoMovilidad) object;
        if ((this.estadoMovilidad == null && other.estadoMovilidad != null) || (this.estadoMovilidad != null && !this.estadoMovilidad.equals(other.estadoMovilidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojos.EstadoMovilidad[ estadoMovilidad=" + estadoMovilidad + " ]";
    }
    
}
