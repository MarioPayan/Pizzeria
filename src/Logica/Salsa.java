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
    @NamedQuery(name = "Salsa.findAll", query = "SELECT s FROM Salsa s"),
    @NamedQuery(name = "Salsa.findByIngredienteId", query = "SELECT s FROM Salsa s WHERE s.ingredienteId = :ingredienteId"),
    @NamedQuery(name = "Salsa.findByNombre", query = "SELECT s FROM Salsa s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "Salsa.findByPrecioporcion", query = "SELECT s FROM Salsa s WHERE s.precioporcion = :precioporcion"),
    @NamedQuery(name = "Salsa.findByCantidad", query = "SELECT s FROM Salsa s WHERE s.cantidad = :cantidad"),
    @NamedQuery(name = "Salsa.findByTipo", query = "SELECT s FROM Salsa s WHERE s.tipo = :tipo"),
    @NamedQuery(name = "Salsa.findBySalsaId", query = "SELECT s FROM Salsa s WHERE s.salsaId = :salsaId"),
    @NamedQuery(name = "Salsa.findByAzucares", query = "SELECT s FROM Salsa s WHERE s.azucares = :azucares"),
    @NamedQuery(name = "Salsa.findByCarbohidratos", query = "SELECT s FROM Salsa s WHERE s.carbohidratos = :carbohidratos"),
    @NamedQuery(name = "Salsa.findByGrasa", query = "SELECT s FROM Salsa s WHERE s.grasa = :grasa")})
public class Salsa implements Serializable {
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
    @Column(name = "salsa_id", nullable = false, length = 11)
    private String salsaId;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String azucares;
    @Basic(optional = false)
    @Column(nullable = false)
    private int carbohidratos;
    @Basic(optional = false)
    @Column(nullable = false)
    private float grasa;

    public Salsa() {
    }

    public Salsa(String salsaId) {
        this.salsaId = salsaId;
    }

    public Salsa(String salsaId, String ingredienteId, String nombre, float precioporcion, int cantidad, String tipo, byte[] foto, String azucares, int carbohidratos, float grasa) {
        this.salsaId = salsaId;
        this.ingredienteId = ingredienteId;
        this.nombre = nombre;
        this.precioporcion = precioporcion;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.foto = foto;
        this.azucares = azucares;
        this.carbohidratos = carbohidratos;
        this.grasa = grasa;
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

    public String getSalsaId() {
        return salsaId;
    }

    public void setSalsaId(String salsaId) {
        this.salsaId = salsaId;
    }

    public String getAzucares() {
        return azucares;
    }

    public void setAzucares(String azucares) {
        this.azucares = azucares;
    }

    public int getCarbohidratos() {
        return carbohidratos;
    }

    public void setCarbohidratos(int carbohidratos) {
        this.carbohidratos = carbohidratos;
    }

    public float getGrasa() {
        return grasa;
    }

    public void setGrasa(float grasa) {
        this.grasa = grasa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (salsaId != null ? salsaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salsa)) {
            return false;
        }
        Salsa other = (Salsa) object;
        if ((this.salsaId == null && other.salsaId != null) || (this.salsaId != null && !this.salsaId.equals(other.salsaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Logica.Salsa[ salsaId=" + salsaId + " ]";
    }
    
}
