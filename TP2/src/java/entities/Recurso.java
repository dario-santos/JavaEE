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

@Entity
@Table(name = "RECURSO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recurso.findAll", query = "SELECT r FROM Recurso r")
    , @NamedQuery(name = "Recurso.findByRecursoid", query = "SELECT r FROM Recurso r WHERE r.recursoid = :recursoid")
    , @NamedQuery(name = "Recurso.getAllRecursoid", query = "SELECT r.recursoid FROM Recurso r")
    , @NamedQuery(name = "Recurso.findByRecursonome", query = "SELECT r FROM Recurso r WHERE r.recursonome = :recursonome")
    , @NamedQuery(name = "Recurso.findByTags", query = "SELECT r FROM Recurso r WHERE r.tags LIKE :tags")
    , @NamedQuery(name = "Recurso.findByProprietario", query = "SELECT r FROM Recurso r WHERE r.proprietario = :proprietario")})
public class Recurso implements Serializable {

    @Size(max = 30)
    @Column(name = "PROPRIETARIO")
    private String proprietario;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recursoid")
    private Collection<Reservar> reservarCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recursoid")
    private Collection<Requisitar> requisitarCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECURSOID")
    public Integer recursoid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "RECURSONOME")
    public String recursonome;
    @Size(max = 100)
    @Column(name = "TAGS")
    public String tags;
    @ManyToMany(mappedBy = "recursoCollection")
    private Collection<Utilizador> utilizadorCollection;
    @JoinTable(name = "RESERVAR", joinColumns = {
        @JoinColumn(name = "RECURSOID", referencedColumnName = "RECURSOID")}, inverseJoinColumns = {
        @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")})
    @ManyToMany
    private Collection<Utilizador> utilizadorCollection1;

    public Recurso() {
    }

    public Recurso(Integer recursoid) {
        this.recursoid = recursoid;
    }

    public Recurso(Integer recursoid, String recursonome) {
        this.recursoid = recursoid;
        this.recursonome = recursonome;
    }

    public Integer getRecursoid() {
        return recursoid;
    }

    public void setRecursoid(Integer recursoid) {
        this.recursoid = recursoid;
    }

    public String getRecursonome() {
        return recursonome;
    }

    public void setRecursonome(String recursonome) {
        this.recursonome = recursonome;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @XmlTransient
    public Collection<Utilizador> getUtilizadorCollection() {
        return utilizadorCollection;
    }

    public void setUtilizadorCollection(Collection<Utilizador> utilizadorCollection) {
        this.utilizadorCollection = utilizadorCollection;
    }

    @XmlTransient
    public Collection<Utilizador> getUtilizadorCollection1() {
        return utilizadorCollection1;
    }

    public void setUtilizadorCollection1(Collection<Utilizador> utilizadorCollection1) {
        this.utilizadorCollection1 = utilizadorCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recursoid != null ? recursoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recurso)) {
            return false;
        }
        Recurso other = (Recurso) object;
        if ((this.recursoid == null && other.recursoid != null) || (this.recursoid != null && !this.recursoid.equals(other.recursoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(recursoid);
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

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }
}
