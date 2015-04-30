/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;


import persistencia.Conexion;
import persistencia.PizzabaseJpaController;
import Logica.Pizzabase;
import java.util.List;
import javax.swing.JComboBox;

/**
 *
 * @author Julian
 */
public class ControladorPizzaBase {
    
    
    private PizzabaseJpaController ControlPizzaBaseJPA;
    private Conexion conn;  //___________________________________________________Descomentar

    public ControladorPizzaBase() {
   
        conn = new Conexion();
        ControlPizzaBaseJPA = new PizzabaseJpaController (conn.getConx());
    }
    
     public String encontrarIdPizza (String nombrePizza) { 
        List<Pizzabase> lista = ControlPizzaBaseJPA.findPizzabaseEntities();
        for (int i = 0; i < lista.size(); i++) { 
            if (lista.get(i).getNombre().equals(nombrePizza)) 
            { return lista.get(i).getPizzaId();} 
        }
        return "";
    }
     
    /** Este metodo devuelve el bojeto tipo pizzabase que se desea uyilizar, el cliente elije por medio del combobx que esta al lado
        izquierdo del boton eliminar el nombre del objeto que desea aliminar*/
    public Pizzabase getPizzabaseCombo (JComboBox jComboBoxCrud) { 
        String nombrePizzabase = jComboBoxCrud.getSelectedItem().toString();
        String idPizzabase = encontrarIdPizza (nombrePizzabase);
        return new  Pizzabase(ControlPizzaBaseJPA.findPizzabase(idPizzabase).getPizzaId(),//String pizzaId, 
                              ControlPizzaBaseJPA.findPizzabase(idPizzabase).getNombre(),//String nombre, 
                              ControlPizzaBaseJPA.findPizzabase(idPizzabase).getTamanio(),//int tamanio, 
                              ControlPizzaBaseJPA.findPizzabase(idPizzabase).getPresentacion(),//String presentacion, 
                              ControlPizzaBaseJPA.findPizzabase(idPizzabase).getPrecio(),//float precio, 
                              ControlPizzaBaseJPA.findPizzabase(idPizzabase).getFoto());//byte[] foto);
    } 
    
    
    
    public void agregarPizzaBase(String pizzaId, String nombre, int tamanio, String presentacion, 
                                 float precio, byte[] foto) throws Exception{
    
        Pizzabase pizza = new Pizzabase(pizzaId, nombre, tamanio,  presentacion, precio, foto);

        ControlPizzaBaseJPA.create(pizza);
    }

    public void actualizarPizza(String pizzaId, String nombre, int tamanio, String presentacion, 
                                 float precio, byte[] foto) throws Exception{
    
       Pizzabase pizza = new Pizzabase(pizzaId, nombre, tamanio,  presentacion, precio, foto);

        ControlPizzaBaseJPA.edit(pizza);
    }
    
    public void eliminarPizza(JComboBox jComboBoxCrud) throws Exception{
        String nombrePizza = jComboBoxCrud.getSelectedItem().toString();
        String idPizza = encontrarIdPizza  (nombrePizza);
        ControlPizzaBaseJPA.destroy(idPizza);
    }
    
    public List <Pizzabase> ListarPizzabase () { 
        List<Pizzabase> lista =  ControlPizzaBaseJPA.findPizzabaseEntities();
        return lista;
    }
    
    public String [] nombrePizzabase  ( ) { 
        List <Pizzabase> lista = ListarPizzabase  ();
        String[] StringListaPizza = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            StringListaPizza[i] = lista.get(i).getNombre();
        }
        return StringListaPizza;
    }
    
    public Pizzabase buscarPizzabase(String Id){
        
    
        return ControlPizzaBaseJPA.findPizzabase(Id);
    
    }
    
}
