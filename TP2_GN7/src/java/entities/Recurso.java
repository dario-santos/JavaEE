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
 * @author Margarida
 */
@Entity
@Table(name = "RECURSO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recurso.findAll", query = "SELECT r FROM Recurso r")
    , @NamedQuery(name = "Recurso.findByRecursoid", query = "SELECT r FROM Recurso r WHERE r.recursoid = :recursoid")
    , @NamedQuery(name = "Recurso.getAllRecursoid", query = "SELECT r.recursoid FROM Recurso r")
    , @NamedQuery(name = "Recurso.findByRecursonome", query = "SELECT r FROM Recurso r WHERE r.recursonome = :recursonome")
    , @NamedQuery(name = "Recurso.findByTags", query = "SELECT r FROM Recurso r WHERE r.tags LIKE :tags")
    , @NamedQuery(name = "Recurso.findByProprietario", query = "SELECT r FROM Recurso r WHERE r.proprietario = :proprietario")
    , @NamedQuery(name = "Recurso.findByEstado", query = "SELECT r FROM Recurso r WHERE r.estado = :estado")
    , @NamedQuery(name = "Recurso.getEstado", query = "SELECT r.estado FROM Recurso r WHERE r.recursoid = :recursoid")})
public class Recurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECURSOID")
    private Integer recursoid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "RECURSONOME")
    public String recursonome;
    @Size(max = 100)
    @Column(name = "TAGS")
    public String tags;
    @Size(max = 30)
    @Column(name = "PROPRIETARIO")
    private String proprietario;
    @Size(max = 30)
    @Column(name = "ESTADO")
    private String estado;

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

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        return "entities.Recurso[ recursoid=" + recursoid + " ]";
    }

}
