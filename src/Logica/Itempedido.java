/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kazemu
 */
@Entity
@Table(catalog = "kazemu", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Itempedido.findAll", query = "SELECT i FROM Itempedido i"),
    @NamedQuery(name = "Itempedido.findByFacturaId", query = "SELECT i FROM Itempedido i WHERE i.facturaId = :facturaId")})
public class Itempedido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "factura_id", nullable = false, length = 11)
    private String facturaId;
    @JoinColumn(name = "factura_id", referencedColumnName = "factura_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Factura factura;
    @JoinColumn(name = "pizza_id_ingrediente_id", referencedColumnName = "pizza_id")
    @ManyToOne
    private Pizzabase pizzaIdIngredienteId;

    public Itempedido() {
    }

    public Itempedido(String facturaId) {
        this.facturaId = facturaId;
    }

    public String getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(String facturaId) {
        this.facturaId = facturaId;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Pizzabase getPizzaIdIngredienteId() {
        return pizzaIdIngredienteId;
    }

    public void setPizzaIdIngredienteId(Pizzabase pizzaIdIngredienteId) {
        this.pizzaIdIngredienteId = pizzaIdIngredienteId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facturaId != null ? facturaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Itempedido)) {
            return false;
        }
        Itempedido other = (Itempedido) object;
        if ((this.facturaId == null && other.facturaId != null) || (this.facturaId != null && !this.facturaId.equals(other.facturaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Logica.Itempedido[ facturaId=" + facturaId + " ]";
    }
    
}
