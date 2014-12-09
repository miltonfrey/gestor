/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author abc
 */
@Entity
@Table(name = "mensaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mensaje.findAll", query = "SELECT m FROM Mensaje m"),
    @NamedQuery(name = "Mensaje.findByIdmensaje", query = "SELECT m FROM Mensaje m WHERE m.idmensaje = :idmensaje"),
    @NamedQuery(name = "Mensaje.findByFecha", query = "SELECT m FROM Mensaje m WHERE m.fecha = :fecha"),
    @NamedQuery(name = "Mensaje.findByLeidoDestino", query = "SELECT m FROM Mensaje m WHERE m.leidoDestino = :leidoDestino"),
    @NamedQuery(name = "Mensaje.findByEliminadoOrigen", query = "SELECT m FROM Mensaje m WHERE m.eliminadoOrigen = :eliminadoOrigen"),
    @NamedQuery(name = "Mensaje.findByEliminadoDestino", query = "SELECT m FROM Mensaje m WHERE m.eliminadoDestino = :eliminadoDestino")})
public class Mensaje implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmensaje")
    private Integer idmensaje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Lob
    @Size(max = 16777215)
    @Column(name = "tema")
    private String tema;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "texto")
    private String texto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "leidoDestino")
    private String leidoDestino;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "eliminadoOrigen")
    private String eliminadoOrigen;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "eliminadoDestino")
    private String eliminadoDestino;
    @JoinColumn(name = "origen", referencedColumnName = "login")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Usuario origen;
    @JoinColumn(name = "destino", referencedColumnName = "login")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Usuario destino;

    public Mensaje() {
    }

    public Mensaje(Integer idmensaje) {
        this.idmensaje = idmensaje;
    }

    public Mensaje(Integer idmensaje, Date fecha, String texto, String leidoDestino, String eliminadoOrigen, String eliminadoDestino) {
        this.idmensaje = idmensaje;
        this.fecha = fecha;
        this.texto = texto;
        this.leidoDestino = leidoDestino;
        this.eliminadoOrigen = eliminadoOrigen;
        this.eliminadoDestino = eliminadoDestino;
    }

    public Integer getIdmensaje() {
        return idmensaje;
    }

    public void setIdmensaje(Integer idmensaje) {
        this.idmensaje = idmensaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getLeidoDestino() {
        return leidoDestino;
    }

    public void setLeidoDestino(String leidoDestino) {
        this.leidoDestino = leidoDestino;
    }

    public String getEliminadoOrigen() {
        return eliminadoOrigen;
    }

    public void setEliminadoOrigen(String eliminadoOrigen) {
        this.eliminadoOrigen = eliminadoOrigen;
    }

    public String getEliminadoDestino() {
        return eliminadoDestino;
    }

    public void setEliminadoDestino(String eliminadoDestino) {
        this.eliminadoDestino = eliminadoDestino;
    }

    public Usuario getOrigen() {
        return origen;
    }

    public void setOrigen(Usuario origen) {
        this.origen = origen;
    }

    public Usuario getDestino() {
        return destino;
    }

    public void setDestino(Usuario destino) {
        this.destino = destino;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmensaje != null ? idmensaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mensaje)) {
            return false;
        }
        Mensaje other = (Mensaje) object;
        if ((this.idmensaje == null && other.idmensaje != null) || (this.idmensaje != null && !this.idmensaje.equals(other.idmensaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojos.Mensaje[ idmensaje=" + idmensaje + " ]";
    }
    
}
