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
@Table(name = "RESERVAR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservar.findAll", query = "SELECT r FROM Reservar r")
    , @NamedQuery(name = "Reservar.findByReservaid", query = "SELECT r FROM Reservar r WHERE r.reservaid = :reservaid")
    , @NamedQuery(name = "Reservar.getAllReservarid", query = "SELECT r.reservaid FROM Reservar r")
    , @NamedQuery(name = "Reservar.findByData", query = "SELECT r FROM Reservar r WHERE r.data = :data")
    , @NamedQuery(name = "Reservar.findByUsername", query = "SELECT r.recursoid FROM Reservar r WHERE r.username = :username")
    , @NamedQuery(name = "Reservar.findByRecursoid", query = "SELECT r FROM Reservar r WHERE r.recursoid = :recursoid")
    , @NamedQuery(name = "Reservar.findByUsernameandRecursoid", query = "SELECT r FROM Reservar r WHERE r.username = :username and r.recursoid = :recursoid")})
public class Reservar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RESERVAID")
    private Integer reservaid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATA")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 30)
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "RECURSOID")
    private Integer recursoid;

    public Reservar() {
    }

    public Reservar(Integer reservaid, Integer recursoid, String username, Date data) {
        this.reservaid = reservaid;
        this.recursoid = recursoid;
        this.username = username;
        this.data = data;
    }
    
    public Reservar(Integer reservaid) {
        this.reservaid = reservaid;
    }

    public Reservar(Integer reservaid, Date data) {
        this.reservaid = reservaid;
        this.data = data;
    }

    public Integer getReservaid() {
        return reservaid;
    }

    public void setReservaid(Integer reservaid) {
        this.reservaid = reservaid;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRecursoid() {
        return recursoid;
    }

    public void setRecursoid(Integer recursoid) {
        this.recursoid = recursoid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reservaid != null ? reservaid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservar)) {
            return false;
        }
        Reservar other = (Reservar) object;
        if ((this.reservaid == null && other.reservaid != null) || (this.reservaid != null && !this.reservaid.equals(other.reservaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Reservar[ reservaid=" + reservaid + " ]";
    }

}
