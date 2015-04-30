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
    @NamedQuery(name = "Carne.findAll", query = "SELECT c FROM Carne c"),
    @NamedQuery(name = "Carne.findByIngredienteId", query = "SELECT c FROM Carne c WHERE c.ingredienteId = :ingredienteId"),
    @NamedQuery(name = "Carne.findByNombre", query = "SELECT c FROM Carne c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Carne.findByPrecioporcion", query = "SELECT c FROM Carne c WHERE c.precioporcion = :precioporcion"),
    @NamedQuery(name = "Carne.findByCantidad", query = "SELECT c FROM Carne c WHERE c.cantidad = :cantidad"),
    @NamedQuery(name = "Carne.findByTipo", query = "SELECT c FROM Carne c WHERE c.tipo = :tipo"),
    @NamedQuery(name = "Carne.findByCarneId", query = "SELECT c FROM Carne c WHERE c.carneId = :carneId"),
    @NamedQuery(name = "Carne.findByPresentacion", query = "SELECT c FROM Carne c WHERE c.presentacion = :presentacion"),
    @NamedQuery(name = "Carne.findByCantidadgrasas", query = "SELECT c FROM Carne c WHERE c.cantidadgrasas = :cantidadgrasas"),
    @NamedQuery(name = "Carne.findByAnimal", query = "SELECT c FROM Carne c WHERE c.animal = :animal")})
public class Carne implements Serializable {
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
    @Column(name = "carne_id", nullable = false, length = 11)
    private String carneId;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String presentacion;
    @Basic(optional = false)
    @Column(nullable = false)
    private float cantidadgrasas;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String animal;

    public Carne() {
    }

    public Carne(String carneId) {
        this.carneId = carneId;
    }

    public Carne(String carneId, String ingredienteId, String nombre, float precioporcion, int cantidad, String tipo, byte[] foto, String presentacion, float cantidadgrasas, String animal) {
        this.carneId = carneId;
        this.ingredienteId = ingredienteId;
        this.nombre = nombre;
        this.precioporcion = precioporcion;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.foto = foto;
        this.presentacion = presentacion;
        this.cantidadgrasas = cantidadgrasas;
        this.animal = animal;
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

    public String getCarneId() {
        return carneId;
    }

    public void setCarneId(String carneId) {
        this.carneId = carneId;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public float getCantidadgrasas() {
        return cantidadgrasas;
    }

    public void setCantidadgrasas(float cantidadgrasas) {
        this.cantidadgrasas = cantidadgrasas;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (carneId != null ? carneId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carne)) {
            return false;
        }
        Carne other = (Carne) object;
        if ((this.carneId == null && other.carneId != null) || (this.carneId != null && !this.carneId.equals(other.carneId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Logica.Carne[ carneId=" + carneId + " ]";
    }
    
}
