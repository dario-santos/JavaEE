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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author themu
 */
@Entity
@Table(name = "RESERVAR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservar.findAll", query = "SELECT r FROM Reservar r")
    , @NamedQuery(name = "Reservar.findAllId", query = "SELECT r.id FROM Reservar r")
    , @NamedQuery(name = "Reservar.findByData", query = "SELECT r FROM Reservar r WHERE r.data = :data")
    , @NamedQuery(name = "Reservar.findFirst", query = "SELECT r FROM Reservar r ORDER BY r.data")
    , @NamedQuery(name = "Reservar.findById", query = "SELECT r FROM Reservar r WHERE r.id = :id")})
public class Reservar implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "NOTIFICAR")
    public Boolean notificar;

    private static final long serialVersionUID = 1L;
    @Column(name = "DATA")
    @Temporal(TemporalType.DATE)
    public Date data;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "RECURSOID", referencedColumnName = "RECURSOID")
    @ManyToOne(optional = false)
    public Recurso recursoid;
    @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")
    @ManyToOne(optional = false)
    public Utilizador username;

    public Reservar() {
    }

    public Reservar(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Recurso getRecursoid() {
        return recursoid;
    }

    public void setRecursoid(Recurso recursoid) {
        this.recursoid = recursoid;
    }

    public Utilizador getUsername() {
        return username;
    }

    public void setUsername(Utilizador username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservar)) {
            return false;
        }
        Reservar other = (Reservar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Reservar[ id=" + id + " ]";
    }

    public Boolean getNotificar() {
        return notificar;
    }

    public void setNotificar(Boolean notificar) {
        this.notificar = notificar;
    }
    
}
