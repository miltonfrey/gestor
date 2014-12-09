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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author abc
 */
@Entity
@Table(name = "contrato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contrato.findAll", query = "SELECT c FROM Contrato c"),
    @NamedQuery(name = "Contrato.findByIdContrato", query = "SELECT c FROM Contrato c WHERE c.idContrato = :idContrato"),
    @NamedQuery(name = "Contrato.findByFecha", query = "SELECT c FROM Contrato c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Contrato.findByEstado", query = "SELECT c FROM Contrato c WHERE c.estado = :estado")})
public class Contrato implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idContrato")
    private Integer idContrato;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 10)
    @Column(name = "estado")
    private String estado;
    @JoinTable(name = "contrato_equivalencia", joinColumns = {
        @JoinColumn(name = "idContrato", referencedColumnName = "idContrato")}, inverseJoinColumns = {
        @JoinColumn(name = "idEquivalencia", referencedColumnName = "idequivalencia")})
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Set<Equivalencia> equivalenciaSet;
    @JoinColumn(name = "idMovilidad", referencedColumnName = "codMovilidad")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movilidad idMovilidad;

    public Contrato() {
    }

    public Contrato(Integer idContrato) {
        this.idContrato = idContrato;
    }

    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Integer idContrato) {
        this.idContrato = idContrato;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Set<Equivalencia> getEquivalenciaSet() {
        return equivalenciaSet;
    }

    public void setEquivalenciaSet(Set<Equivalencia> equivalenciaSet) {
        this.equivalenciaSet = equivalenciaSet;
    }

    public Movilidad getIdMovilidad() {
        return idMovilidad;
    }

    public void setIdMovilidad(Movilidad idMovilidad) {
        this.idMovilidad = idMovilidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContrato != null ? idContrato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contrato)) {
            return false;
        }
        Contrato other = (Contrato) object;
        if ((this.idContrato == null && other.idContrato != null) || (this.idContrato != null && !this.idContrato.equals(other.idContrato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojos.Contrato[ idContrato=" + idContrato + " ]";
    }
    
}
