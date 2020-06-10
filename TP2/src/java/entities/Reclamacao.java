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
 * @author themu
 */
@Entity
@Table(name = "RECLAMACAO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reclamacao.findAll", query = "SELECT r FROM Reclamacao r")
    , @NamedQuery(name = "Reclamacao.findAllId", query = "SELECT r.id FROM Reclamacao r")
    , @NamedQuery(name = "Reclamacao.findById", query = "SELECT r FROM Reclamacao r WHERE r.id = :id")
    , @NamedQuery(name = "Reclamacao.findByReclamacao", query = "SELECT r FROM Reclamacao r WHERE r.reclamacao = :reclamacao")
    , @NamedQuery(name = "Reclamacao.findByIdrecurso", query = "SELECT r FROM Reclamacao r WHERE r.idrecurso = :idrecurso")
    , @NamedQuery(name = "Reclamacao.findByUsername", query = "SELECT r FROM Reclamacao r WHERE r.username = :username")})
public class Reclamacao implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "IDRECURSO")
    public int idrecurso;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    public Integer id;
    @Size(max = 300)
    @Column(name = "RECLAMACAO")
    public String reclamacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "USERNAME")
    public String username;

    public Reclamacao() {
    }

    public Reclamacao(Integer id) {
        this.id = id;
    }

    public Reclamacao(Integer id, Integer idrecurso, String username) {
        this.id = id;
        this.idrecurso = idrecurso;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReclamacao() {
        return reclamacao;
    }

    public void setReclamacao(String reclamacao) {
        this.reclamacao = reclamacao;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
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
        if (!(object instanceof Reclamacao)) {
            return false;
        }
        Reclamacao other = (Reclamacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Reclamacao[ id=" + id + " ]";
    }

    public int getIdrecurso() {
        return idrecurso;
    }

    public void setIdrecurso(int idrecurso) {
        this.idrecurso = idrecurso;
    }
    
}
