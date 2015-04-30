/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import persistencia.FacturaJpaController;
import persistencia.Conexion;
/**
 *
 * @author kazemu
 */
public class ControladorFactura {
    private FacturaJpaController ControlCarneJPA;
    private Conexion conn;  //___________________________________________________Descomentar

    public ControladorFactura() {
        conn = new Conexion();
        ControlCarneJPA = new FacturaJpaController (conn.getConx());
    }
    
    public void crearFactura(String fecha, String info, float precio){
        
    }
}
