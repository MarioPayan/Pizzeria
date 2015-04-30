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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
    @NamedQuery(name = "Vegetal.findAll", query = "SELECT v FROM Vegetal v"),
    @NamedQuery(name = "Vegetal.findByIngredienteId", query = "SELECT v FROM Vegetal v WHERE v.ingredienteId = :ingredienteId"),
    @NamedQuery(name = "Vegetal.findByNombre", query = "SELECT v FROM Vegetal v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "Vegetal.findByPrecioporcion", query = "SELECT v FROM Vegetal v WHERE v.precioporcion = :precioporcion"),
    @NamedQuery(name = "Vegetal.findByCantidad", query = "SELECT v FROM Vegetal v WHERE v.cantidad = :cantidad"),
    @NamedQuery(name = "Vegetal.findByTipo", query = "SELECT v FROM Vegetal v WHERE v.tipo = :tipo"),
    @NamedQuery(name = "Vegetal.findByVegetalId", query = "SELECT v FROM Vegetal v WHERE v.vegetalId = :vegetalId"),
    @NamedQuery(name = "Vegetal.findByCarbohidratos", query = "SELECT v FROM Vegetal v WHERE v.carbohidratos = :carbohidratos")})
public class Vegetal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "ingrediente_id", nullable = false, length = 11)
    private String ingredienteId;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String nombre;
    @Basic(optional = false)
    @Column(nullable = false)
    private float precioporcion;
    @Basic(optional = false)
    @Column(nullable = false)
    private int cantidad;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String tipo;
    @Basic(optional = false)
    @Lob
    @Column(nullable = false)
    private byte[] foto;
    @Id
    @Basic(optional = false)
    @Column(name = "vegetal_id", nullable = false, length = 11)
    private String vegetalId;
    @Basic(optional = false)
    @Column(nullable = false)
    private int carbohidratos;

    public Vegetal() {
    }

    public Vegetal(String vegetalId) {
        this.vegetalId = vegetalId;
    }

    public Vegetal(String vegetalId, String ingredienteId, String nombre, float precioporcion, int cantidad, String tipo, byte[] foto, int carbohidratos) {
        this.vegetalId = vegetalId;
        this.ingredienteId = ingredienteId;
        this.nombre = nombre;
        this.precioporcion = precioporcion;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.foto = foto;
        this.carbohidratos = carbohidratos;
    }

    public String getIngredienteId() {
        return ingredienteId;
    }

    public void setIngredienteId(String ingredienteId) {
        this.ingredienteId = ingredienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecioporcion() {
        return precioporcion;
    }

    public void setPrecioporcion(float precioporcion) {
        this.precioporcion = precioporcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getVegetalId() {
        return vegetalId;
    }

    public void setVegetalId(String vegetalId) {
        this.vegetalId = vegetalId;
    }

    public int getCarbohidratos() {
        return carbohidratos;
    }

    public void setCarbohidratos(int carbohidratos) {
        this.carbohidratos = carbohidratos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vegetalId != null ? vegetalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vegetal)) {
            return false;
        }
        Vegetal other = (Vegetal) object;
        if ((this.vegetalId == null && other.vegetalId != null) || (this.vegetalId != null && !this.vegetalId.equals(other.vegetalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Logica.Vegetal[ vegetalId=" + vegetalId + " ]";
    }
    
}
