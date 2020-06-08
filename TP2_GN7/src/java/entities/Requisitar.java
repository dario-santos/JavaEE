package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
 * @author Margarida
 */
@Entity
@Table(name = "REQUISITAR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Requisitar.findAll", query = "SELECT r FROM Requisitar r")
    , @NamedQuery(name = "Requisitar.findByRequisicaoid", query = "SELECT r FROM Requisitar r WHERE r.requisicaoid = :requisicaoid")
    , @NamedQuery(name = "Requisitar.getAllRequisitarid", query = "SELECT r.requisicaoid FROM Requisitar r")
    , @NamedQuery(name = "Requisitar.findByData", query = "SELECT r FROM Requisitar r WHERE r.data = :data")
    , @NamedQuery(name = "Requisitar.findByDevolvido", query = "SELECT r FROM Requisitar r WHERE r.devolvido = :devolvido")
    , @NamedQuery(name = "Requisitar.findByRecursoid", query = "SELECT r FROM Requisitar r WHERE r.recursoid = :recursoid")
    , @NamedQuery(name = "Requisitar.findByUsername", query = "SELECT r.recursoid FROM Requisitar r WHERE r.username = :username")
    , @NamedQuery(name = "Requisitar.findByUsernameandRecursoid", query = "SELECT r FROM Requisitar r WHERE r.username = :username and r.recursoid = :recursoid")})
public class Requisitar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "REQUISICAOID")
    private Integer requisicaoid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATA")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DEVOLVIDO")
    private Boolean devolvido;
    @Column(name = "RECURSOID")
    private Integer recursoid;
    @Size(max = 30)
    @Column(name = "USERNAME")
    private String username;

    public Requisitar() {
    }

    public Requisitar(Integer requisicaoid, Integer recursoid, String username, Date data, Boolean devolvido) {
        this.requisicaoid = requisicaoid;
        this.recursoid = recursoid;
        this.username = username;
        this.data = data;
        this.devolvido = devolvido;
    }
    
    public Requisitar(Integer requisicaoid) {
        this.requisicaoid = requisicaoid;
    }

    public Requisitar(Integer requisicaoid, Date data, Boolean devolvido) {
        this.requisicaoid = requisicaoid;
        this.data = data;
        this.devolvido = devolvido;
    }

    public Integer getRequisicaoid() {
        return requisicaoid;
    }

    public void setRequisicaoid(Integer requisicaoid) {
        this.requisicaoid = requisicaoid;
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

    public Integer getRecursoid() {
        return recursoid;
    }

    public void setRecursoid(Integer recursoid) {
        this.recursoid = recursoid;
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
        hash += (requisicaoid != null ? requisicaoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Requisitar)) {
            return false;
        }
        Requisitar other = (Requisitar) object;
        if ((this.requisicaoid == null && other.requisicaoid != null) || (this.requisicaoid != null && !this.requisicaoid.equals(other.requisicaoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Requisitar[ requisicaoid=" + requisicaoid + " ]";
    }

}
