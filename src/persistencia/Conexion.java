
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author marioapv
 */
public class Conexion {
    private EntityManagerFactory conx;
    
    public Conexion(){
        conx = Persistence.createEntityManagerFactory("PizerriaPU"); //
    }
    
    public EntityManagerFactory getConx(){
        return conx;
    }
}
