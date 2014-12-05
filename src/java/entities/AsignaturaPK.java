/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author cba
 */
@Embeddable
public class AsignaturaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "codAsignatura")
    private int codAsignatura;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombreUniversidad")
    private String nombreUniversidad;

    public AsignaturaPK() {
    }

    public AsignaturaPK(int codAsignatura, String nombreUniversidad) {
        this.codAsignatura = codAsignatura;
        this.nombreUniversidad = nombreUniversidad;
    }

    public int getCodAsignatura() {
        return codAsignatura;
    }

    public void setCodAsignatura(int codAsignatura) {
        this.codAsignatura = codAsignatura;
    }

    public String getNombreUniversidad() {
        return nombreUniversidad;
    }

    public void setNombreUniversidad(String nombreUniversidad) {
        this.nombreUniversidad = nombreUniversidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codAsignatura;
        hash += (nombreUniversidad != null ? nombreUniversidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsignaturaPK)) {
            return false;
        }
        AsignaturaPK other = (AsignaturaPK) object;
        if (this.codAsignatura != other.codAsignatura) {
            return false;
        }
        if ((this.nombreUniversidad == null && other.nombreUniversidad != null) || (this.nombreUniversidad != null && !this.nombreUniversidad.equals(other.nombreUniversidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.AsignaturaPK[ codAsignatura=" + codAsignatura + ", nombreUniversidad=" + nombreUniversidad + " ]";
    }
    
}
