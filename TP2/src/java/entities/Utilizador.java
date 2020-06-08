package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
 * @author Margarida
 */
@Entity
@Table(name = "UTILIZADOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Utilizador.findAll", query = "SELECT u FROM Utilizador u")
    , @NamedQuery(name = "Utilizador.findByUsername", query = "SELECT u FROM Utilizador u WHERE u.username = :username")
    , @NamedQuery(name = "Utilizador.getUserPassword", query = "SELECT u.hashpassword FROM Utilizador u WHERE u.username = :username")
    , @NamedQuery(name = "Utilizador.findByHashpassword", query = "SELECT u FROM Utilizador u WHERE u.hashpassword = :hashpassword")})
public class Utilizador implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private Collection<Reservar> reservarCollection;

    @OneToMany(mappedBy = "username")
    private Collection<Requisitar> requisitarCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "USERNAME")
    public String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "HASHPASSWORD")
    public String hashpassword;
    @JoinTable(name = "REQUISITAR", joinColumns = {
        @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")}, inverseJoinColumns = {
        @JoinColumn(name = "RECURSOID", referencedColumnName = "RECURSOID")})
    @ManyToMany
    private Collection<Recurso> recursoCollection;
    @ManyToMany(mappedBy = "utilizadorCollection1")
    private Collection<Recurso> recursoCollection1;

    public Utilizador() {
    }

    public Utilizador(String username) {
        this.username = username;
    }

    public Utilizador(String username, String hashpassword) {
        this.username = username;
        this.hashpassword = hashpassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashpassword() {
        return hashpassword;
    }

    public void setHashpassword(String hashpassword) {
        this.hashpassword = hashpassword;
    }

    @XmlTransient
    public Collection<Recurso> getRecursoCollection() {
        return recursoCollection;
    }

    public void setRecursoCollection(Collection<Recurso> recursoCollection) {
        this.recursoCollection = recursoCollection;
    }

    @XmlTransient
    public Collection<Recurso> getRecursoCollection1() {
        return recursoCollection1;
    }

    public void setRecursoCollection1(Collection<Recurso> recursoCollection1) {
        this.recursoCollection1 = recursoCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utilizador)) {
            return false;
        }
        Utilizador other = (Utilizador) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tp2.entities.Utilizador[ username=" + username + " ]";
    }

    @XmlTransient
    public Collection<Requisitar> getRequisitarCollection() {
        return requisitarCollection;
    }

    public void setRequisitarCollection(Collection<Requisitar> requisitarCollection) {
        this.requisitarCollection = requisitarCollection;
    }

    @XmlTransient
    public Collection<Reservar> getReservarCollection() {
        return reservarCollection;
    }

    public void setReservarCollection(Collection<Reservar> reservarCollection) {
        this.reservarCollection = reservarCollection;
    }

}
