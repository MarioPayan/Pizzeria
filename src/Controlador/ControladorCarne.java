/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import ControladorJPA.CarneJpaController;
import ControladorJPA.Conexion;
import Logica.Carne;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JComboBox;

/**
 *
 * @author Julian
 */
public class ControladorCarne {
    
    private CarneJpaController ControlCarneJPA;
    private Conexion conn;  //___________________________________________________Descomentar

    public ControladorCarne() {
   
        conn = new Conexion();
        ControlCarneJPA = new CarneJpaController (conn.getConx());
        ControlCarneJPA = new CarneJpaController (null); //------------------------------> eliminar
    }
    
     public String encontrarIdCarne (String nombreCarne) { 
        List<Carne> lista =  ControlCarneJPA.findCarneEntities();
        for (int i = 0; i < lista.size(); i++) { 
            if (lista.get(i).getNombre().equals(nombreCarne)) 
            { return lista.get(i).getCarneId();} 
            
        }
        return "";
    }
     
    /** Este metodo devuelve el bojeto tipo carne que se desea uyilizar, el cliente elije por medio del combobx que esta al lado
        izquierdo del boton eliminar el nombre del objeto que desea aliminar*/
    public Carne getCarneCombo (JComboBox jComboBoxCarneCrud) { 
        String nombreCarne = jComboBoxCarneCrud.getSelectedItem().toString();
        String idCarne = encontrarIdCarne (nombreCarne);
        
        return new  Carne( ControlCarneJPA.findCarne(idCarne).getCarneId(), 
                          ControlCarneJPA.findCarne(idCarne).getIngredienteId(), 
                          ControlCarneJPA.findCarne(idCarne).getNombre(), 
                          ControlCarneJPA.findCarne(idCarne).getPrecioporcion(), //float precioporcion 
                          ControlCarneJPA.findCarne(idCarne).getCantidad(), //int cantidad 
                          ControlCarneJPA.findCarne(idCarne).getTipo(),
                          ControlCarneJPA.findCarne(idCarne).getFoto(), //byte[] foto
                          ControlCarneJPA.findCarne(idCarne).getPresentacion(), 
                          ControlCarneJPA.findCarne(idCarne).getCantidadgrasas(),  // float cantidadgrasas, 
                          ControlCarneJPA.findCarne(idCarne).getAnimal());
    } 
    
    
    
    public void agregarCarne(String carneId, String ingredienteId, String nombre, 
                             float precioporcion, int cantidad, String tipo, 
                             byte[] foto, String presentacion, float cantidadgrasas, 
                             String animal) throws Exception{
    
        Carne carne = new Carne(carneId, ingredienteId, nombre,
                                precioporcion,cantidad,tipo,foto,
                                presentacion, cantidadgrasas,animal);

        ControlCarneJPA.create(carne);
    }

    public void actualizarCarne(String carneId, String ingredienteId, String nombre, 
                             float precioporcion, int cantidad, String tipo, 
                             byte[] foto, String presentacion, float cantidadgrasas, 
                             String animal) throws Exception{
    
       Carne carne = new Carne(carneId, ingredienteId, nombre,
                                precioporcion,cantidad,tipo,foto,
                                presentacion, cantidadgrasas,animal);
        
        ControlCarneJPA.edit(carne);
    }
    
    public void eliminarCarne(JComboBox jComboBoxCarneCrud) throws Exception{
        String nombreCarne = jComboBoxCarneCrud.getSelectedItem().toString();
        String idCarne = encontrarIdCarne (nombreCarne);
        ControlCarneJPA.destroy(idCarne);
    }
    
    public List <Carne> ListarCarnes () { 
        List<Carne> lista =  ControlCarneJPA.findCarneEntities();
        return lista;
    }
    
    public String [] nombreCarnes ( ) { 
        List <Carne> lista = ListarCarnes ();
        String[] StringListaCarne = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            StringListaCarne[i] = lista.get(i).getNombre();
        }
        return StringListaCarne;
    }
    
    public Carne buscarCarne(String IdCarne){
        
    
        return ControlCarneJPA.findCarne(IdCarne);
    
    }
}
