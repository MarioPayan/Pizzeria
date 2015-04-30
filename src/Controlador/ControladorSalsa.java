/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import ControladorJPA.Conexion;
import ControladorJPA.SalsaJpaController;
import Logica.Salsa;
import java.util.List;
import javax.swing.JComboBox;

/**
 *
 * @author Julian
 */
public class ControladorSalsa {
    
    
    
    private SalsaJpaController ControlSalsaJPA;
    private Conexion conn;  //___________________________________________________Descomentar

    public ControladorSalsa() {
   
        conn = new Conexion();
        ControlSalsaJPA = new SalsaJpaController (conn.getConx());
    }
    
     public String encontrarIdSalsa (String nombre) { 
        List<Salsa> lista = ControlSalsaJPA.findSalsaEntities();
        for (int i = 0; i < lista.size(); i++) { 
            if (lista.get(i).getNombre().equals(nombre)) 
            { return lista.get(i).getSalsaId();} 
        }
        return "";
    }
     
    /** Este metodo devuelve el bojeto tipo pizzabase que se desea uyilizar, el cliente elije por medio del combobx que esta al lado
        izquierdo del boton eliminar el nombre del objeto que desea aliminar*/
    public Salsa getSalsaCombo (JComboBox jComboBoxCrud) { 
        String nombre = jComboBoxCrud.getSelectedItem().toString();
        String id = encontrarIdSalsa (nombre);
        
        return new Salsa (ControlSalsaJPA.findSalsa(id).getSalsaId(), //String salsaId, 
                          ControlSalsaJPA.findSalsa(id).getIngredienteId(), //String ingredienteId, 
                          ControlSalsaJPA.findSalsa(id).getNombre(), //String nombre, 
                          ControlSalsaJPA.findSalsa(id).getPrecioporcion(), //float precioporcion, 
                          ControlSalsaJPA.findSalsa(id).getCantidad(), //int cantidad, 
                          ControlSalsaJPA.findSalsa(id).getTipo(), //String tipo, 
                          ControlSalsaJPA.findSalsa(id).getFoto(), //byte[] foto, 
                          ControlSalsaJPA.findSalsa(id).getAzucares(), //String azucares, 
                          ControlSalsaJPA.findSalsa(id).getCarbohidratos(), //int carbohidratos, 
                          ControlSalsaJPA.findSalsa(id).getGrasa()); //float grasa);
    } 
    
    
    
    public void agregarSalsa (String salsaId, String ingredienteId, String nombre, float precioporcion, int cantidad, String tipo, 
                              byte[] foto, String azucares, int carbohidratos, float grasa) throws Exception{
    
        Salsa salsa = new Salsa (salsaId, ingredienteId,  nombre,  precioporcion,  cantidad, tipo, foto, azucares, carbohidratos,grasa);

        ControlSalsaJPA.create(salsa);
    }

    public void actualizarPizza(String salsaId, String ingredienteId, String nombre, float precioporcion, int cantidad, String tipo, 
                              byte[] foto, String azucares, int carbohidratos, float grasa) throws Exception{
    
       Salsa salsa = new Salsa (salsaId, ingredienteId,  nombre,  precioporcion,  cantidad, tipo, foto, azucares, carbohidratos,grasa);

        ControlSalsaJPA.edit(salsa);
    }
    
    public void eliminarSalsa(JComboBox jComboBoxCrud) throws Exception{
        String nombre = jComboBoxCrud.getSelectedItem().toString();
        String id = encontrarIdSalsa  (nombre);
        ControlSalsaJPA.destroy(id);
    }
    
    public List <Salsa> ListarSalsa () { 
        List<Salsa> lista =  ControlSalsaJPA.findSalsaEntities();
        return lista;
    }
    
    public String [] nombreSalsa  ( ) { 
        List <Salsa> lista = ListarSalsa  ();
        String[] StringLista = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            StringLista[i] = lista.get(i).getNombre();
        }
        return StringLista;
    }
    
    public Salsa buscarSalsa (String Id){
        
    
        return ControlSalsaJPA.findSalsa(Id);
    
    }
    
}
