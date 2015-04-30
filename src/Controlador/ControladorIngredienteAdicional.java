/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import ControladorJPA.IngredienteadicionalJpaController;
import ControladorJPA.Conexion;
import Logica.Carne;
import Logica.Ingredienteadicional;
import java.util.List;
import javax.swing.JComboBox;

/**
 *
 * @author Julian
 */
public class ControladorIngredienteAdicional {
    
    private IngredienteadicionalJpaController ControlCIngredienteJPA;
    private Conexion conn;  //___________________________________________________Descomentar

    public ControladorIngredienteAdicional() {
   
        conn = new Conexion();
        ControlCIngredienteJPA= new IngredienteadicionalJpaController (conn.getConx());
        ControlCIngredienteJPA= new IngredienteadicionalJpaController (null); //------------------------------> eliminar
    }
    
     public String encontrarIngredinte (String nombreIngrediente) { 
        List<Ingredienteadicional> lista =  ControlCIngredienteJPA.findIngredienteadicionalEntities();
        for (int i = 0; i < lista.size(); i++) { 
            if (lista.get(i).getNombre().equals(nombreIngrediente)) 
            { return lista.get(i).getIngredienteId();} 
        }
        return "";
    }
     
    /** Este metodo devuelve el bojeto tipo ingrediente que se desea uyilizar, el cliente elije por medio del combobx que esta al lado
        izquierdo del boton eliminar el nombre del objeto que desea aliminar*/
    public Ingredienteadicional getIngredienteCombo (JComboBox jcomboBox) { 
        String nombreIngrediente = jcomboBox.getSelectedItem().toString();
        String idIngrediente = encontrarIngredinte  (nombreIngrediente);
        
        return new Ingredienteadicional(ControlCIngredienteJPA.findIngredienteadicional(idIngrediente).getIngredienteId(),//String ingredienteId, 
                                        ControlCIngredienteJPA.findIngredienteadicional(idIngrediente).getNombre(),//String nombre, 
                                        ControlCIngredienteJPA.findIngredienteadicional(idIngrediente).getPrecioporcion(),//float precioporcion, 
                                        ControlCIngredienteJPA.findIngredienteadicional(idIngrediente).getCantidad(),//int cantidad, 
                                        ControlCIngredienteJPA.findIngredienteadicional(idIngrediente).getTipo(),//String tipo, 
                                        ControlCIngredienteJPA.findIngredienteadicional(idIngrediente).getFoto()); //byte[] foto);
    } 
    
    
    
    public void agregarIngrediente(String ingredienteId,String nombre,float precioporcion, 
                                   int cantidad, String tipo, byte[] foto) throws Exception{
        Ingredienteadicional ingrediente = new Ingredienteadicional(ingredienteId,nombre,precioporcion,cantidad, tipo, foto);
        ControlCIngredienteJPA.create(ingrediente);
    }

    public void actualizarIngrediente (String ingredienteId,String nombre,float precioporcion, 
                                   int cantidad, String tipo, byte[] foto) throws Exception{
    
       Ingredienteadicional ingrediente = new Ingredienteadicional(ingredienteId,nombre,precioporcion,cantidad, tipo, foto);
        
        ControlCIngredienteJPA.edit(ingrediente);
    }
    
    public void eliminarIngrediente(JComboBox jComboBoxCrud) throws Exception{
        String nombreIngrediente = jComboBoxCrud.getSelectedItem().toString();
        String idIngrediente = encontrarIngredinte (nombreIngrediente);
        ControlCIngredienteJPA.destroy(idIngrediente);
    }
    
    public List <Ingredienteadicional> ListarIngredientes () { 
        List<Ingredienteadicional> lista =  ControlCIngredienteJPA.findIngredienteadicionalEntities();
        return lista;
    }
    
    public String [] nombreIngredientes ( ) { 
        List <Ingredienteadicional> lista = ListarIngredientes ();
        String[] StringListaIngrediente = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            StringListaIngrediente[i] = lista.get(i).getNombre();
        }
        return StringListaIngrediente;
    }
    
    public Ingredienteadicional buscarIngrediente(String IdIngrediente){
        
    
        return ControlCIngredienteJPA.findIngredienteadicional(IdIngrediente);
    
    }
    
}
