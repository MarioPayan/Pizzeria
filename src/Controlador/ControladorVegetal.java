/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import ControladorJPA.Conexion;
import ControladorJPA.VegetalJpaController;
import Logica.Vegetal;
import java.util.List;
import javax.swing.JComboBox;

/**
 *
 * @author Julian
 */
public class ControladorVegetal {
    private VegetalJpaController ControlVegetalJPA;
    private Conexion conn;  //___________________________________________________Descomentar

    public ControladorVegetal () {
   
        conn = new Conexion();
        ControlVegetalJPA = new VegetalJpaController (conn.getConx());
    }
    
     public String encontrarIdvegetal (String nombre) { 
        List<Vegetal> lista = ControlVegetalJPA.findVegetalEntities();
        for (int i = 0; i < lista.size(); i++) { 
            if (lista.get(i).getNombre().equals(nombre)) 
            { return lista.get(i).getVegetalId();} 
        }
        return "";
    }
     
    /** Este metodo devuelve el bojeto tipo pizzabase que se desea uyilizar, el cliente elije por medio del combobx que esta al lado
        izquierdo del boton eliminar el nombre del objeto que desea aliminar*/
    public Vegetal getSalsaCombo (JComboBox jComboBoxCrud) { 
        String nombre = jComboBoxCrud.getSelectedItem().toString();
        String id = encontrarIdvegetal (nombre);
        
        return new Vegetal (ControlVegetalJPA.findVegetal(id).getVegetalId() , //String vegetalId, 
                            ControlVegetalJPA.findVegetal(id).getIngredienteId() , //String ingredienteId, 
                            ControlVegetalJPA.findVegetal(id).getNombre() , //String nombre, 
                            ControlVegetalJPA.findVegetal(id).getPrecioporcion() , //float precioporcion, 
                            ControlVegetalJPA.findVegetal(id).getCantidad() , //int cantidad, 
                            ControlVegetalJPA.findVegetal(id).getTipo() , //String tipo, 
                            ControlVegetalJPA.findVegetal(id).getFoto() , //byte[] foto, 
                            ControlVegetalJPA.findVegetal(id).getCarbohidratos()); //int carbohidratos);
   
    } 
    
    
    
    public void agregarVegetal (String vegetalId, String ingredienteId, String nombre, float precioporcion, int cantidad, 
                                String tipo, byte[] foto, int carbohidratos) throws Exception{
    
        Vegetal vegetal = new Vegetal (vegetalId,ingredienteId,nombre,precioporcion,cantidad,tipo,foto,carbohidratos);

        ControlVegetalJPA.create(vegetal);
    }

    public void actualizarVegetal(String vegetalId, String ingredienteId, String nombre, float precioporcion, int cantidad, 
                                  String tipo, byte[] foto, int carbohidratos) throws Exception{
    
       Vegetal vegetal = new Vegetal (vegetalId,ingredienteId,nombre,precioporcion,cantidad,tipo,foto,carbohidratos);

        ControlVegetalJPA.edit(vegetal);
    }
    
    public void eliminarVegetal (JComboBox jComboBoxCrud) throws Exception{
        String nombre = jComboBoxCrud.getSelectedItem().toString();
        String id = encontrarIdvegetal  (nombre);
       ControlVegetalJPA.destroy(id);
    }
    
    public List <Vegetal> ListarVegetal () { 
        List<Vegetal> lista =  ControlVegetalJPA.findVegetalEntities();
        return lista;
    }
    
    public String [] nombreVegetal  ( ) { 
        List <Vegetal> lista = ListarVegetal  ();
        String[] StringLista = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            StringLista[i] = lista.get(i).getNombre();
        }
        return StringLista;
    }
    
    public Vegetal buscarVegetal (String Id){
        
        return ControlVegetalJPA.findVegetal(Id);
    
    }
    
}
