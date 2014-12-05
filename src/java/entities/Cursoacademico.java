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
import javax.persistence.Id;
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
@Table(name = "cursoacademico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cursoacademico.findAll", query = "SELECT c FROM Cursoacademico c"),
    @NamedQuery(name = "Cursoacademico.findByCursoAcademico", query = "SELECT c FROM Cursoacademico c WHERE c.cursoAcademico = :cursoAcademico")})
public class Cursoacademico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "cursoAcademico")
    private String cursoAcademico;
    @OneToMany(mappedBy = "cursoAcademico")
    private Set<Movilidad> movilidadSet;

    public Cursoacademico() {
    }

    public Cursoacademico(String cursoAcademico) {
        this.cursoAcademico = cursoAcademico;
    }

    public String getCursoAcademico() {
        return cursoAcademico;
    }

    public void setCursoAcademico(String cursoAcademico) {
        this.cursoAcademico = cursoAcademico;
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
        hash += (cursoAcademico != null ? cursoAcademico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cursoacademico)) {
            return false;
        }
        Cursoacademico other = (Cursoacademico) object;
        if ((this.cursoAcademico == null && other.cursoAcademico != null) || (this.cursoAcademico != null && !this.cursoAcademico.equals(other.cursoAcademico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Cursoacademico[ cursoAcademico=" + cursoAcademico + " ]";
    }
    
}
