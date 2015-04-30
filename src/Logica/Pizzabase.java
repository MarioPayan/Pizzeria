/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kazemu
 */
@Entity
@Table(catalog = "kazemu", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pizzabase.findAll", query = "SELECT p FROM Pizzabase p"),
    @NamedQuery(name = "Pizzabase.findByPizzaId", query = "SELECT p FROM Pizzabase p WHERE p.pizzaId = :pizzaId"),
    @NamedQuery(name = "Pizzabase.findByNombre", query = "SELECT p FROM Pizzabase p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Pizzabase.findByTamanio", query = "SELECT p FROM Pizzabase p WHERE p.tamanio = :tamanio"),
    @NamedQuery(name = "Pizzabase.findByPresentacion", query = "SELECT p FROM Pizzabase p WHERE p.presentacion = :presentacion"),
    @NamedQuery(name = "Pizzabase.findByPrecio", query = "SELECT p FROM Pizzabase p WHERE p.precio = :precio")})
public class Pizzabase implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "pizza_id", nullable = false, length = 11)
    private String pizzaId;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String nombre;
    @Basic(optional = false)
    @Column(nullable = false)
    private int tamanio;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String presentacion;
    @Basic(optional = false)
    @Column(nullable = false)
    private float precio;
    @Basic(optional = false)
    @Lob
    @Column(nullable = false)
    private byte[] foto;
    @OneToMany(mappedBy = "pizzaIdIngredienteId")
    private Collection<Itempedido> itempedidoCollection;

    public Pizzabase() {
    }

    public Pizzabase(String pizzaId) {
        this.pizzaId = pizzaId;
    }

    public Pizzabase(String pizzaId, String nombre, int tamanio, String presentacion, float precio, byte[] foto) {
        this.pizzaId = pizzaId;
        this.nombre = nombre;
        this.tamanio = tamanio;
        this.presentacion = presentacion;
        this.precio = precio;
        this.foto = foto;
    }

    public String getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(String pizzaId) {
        this.pizzaId = pizzaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @XmlTransient
    public Collection<Itempedido> getItempedidoCollection() {
        return itempedidoCollection;
    }

    public void setItempedidoCollection(Collection<Itempedido> itempedidoCollection) {
        this.itempedidoCollection = itempedidoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pizzaId != null ? pizzaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pizzabase)) {
            return false;
        }
        Pizzabase other = (Pizzabase) object;
        if ((this.pizzaId == null && other.pizzaId != null) || (this.pizzaId != null && !this.pizzaId.equals(other.pizzaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Logica.Pizzabase[ pizzaId=" + pizzaId + " ]";
    }
    
}
