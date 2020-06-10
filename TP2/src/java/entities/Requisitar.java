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
@Table(name = "REQUISITAR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Requisitar.findAll", query = "SELECT r FROM Requisitar r")
    , @NamedQuery(name = "Requisitar.findAllId", query = "SELECT r.id FROM Requisitar r")
    , @NamedQuery(name = "Requisitar.findByData", query = "SELECT r FROM Requisitar r WHERE r.data = :data")
    , @NamedQuery(name = "Requisitar.findByDevolvido", query = "SELECT r FROM Requisitar r WHERE r.devolvido = :devolvido")
    , @NamedQuery(name = "Requisitar.findByRecursoId", query = "SELECT r FROM Requisitar r WHERE r.recursoid.recursoid = :id AND r.devolvido = false")
    , @NamedQuery(name = "Requisitar.findByRecursoIdUsername", query = "SELECT r FROM Requisitar r WHERE r.recursoid.recursoid = :idrecurso AND r.username.username = :username AND r.devolvido = false")
    , @NamedQuery(name = "Requisitar.findAllByUserId", query = "SELECT r FROM Requisitar r WHERE r.username.username = :id")
    , @NamedQuery(name = "Requisitar.findByUserId", query = "SELECT r FROM Requisitar r WHERE r.username.username = :id AND r.devolvido = :devolvido")
    , @NamedQuery(name = "Requisitar.findById", query = "SELECT r FROM Requisitar r WHERE r.id = :id")})
public class Requisitar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "DATA")
    @Temporal(TemporalType.DATE)
    public Date data;
    @Column(name = "DEVOLVIDO")
    public Boolean devolvido;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    public Integer id;
    @JoinColumn(name = "RECURSOID", referencedColumnName = "RECURSOID")
    @ManyToOne(optional = false)
    public Recurso recursoid;
    @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")
    @ManyToOne
    public Utilizador username;

    public Requisitar() {
    }

    public Requisitar(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Boolean getDevolvido() {
        return devolvido;
    }

    public void setDevolvido(Boolean devolvido) {
        this.devolvido = devolvido;
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
        if (!(object instanceof Requisitar)) {
            return false;
        }
        Requisitar other = (Requisitar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Requisitar[ id=" + id + " ]";
    }
    
}
