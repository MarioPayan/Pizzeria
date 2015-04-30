package Logica;

import Logica.Itempedido;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-27T23:36:08")
@StaticMetamodel(Pizzabase.class)
public class Pizzabase_ { 

    public static volatile SingularAttribute<Pizzabase, Float> precio;
    public static volatile SingularAttribute<Pizzabase, byte[]> foto;
    public static volatile SingularAttribute<Pizzabase, String> pizzaId;
    public static volatile SingularAttribute<Pizzabase, Integer> tamanio;
    public static volatile SingularAttribute<Pizzabase, String> presentacion;
    public static volatile CollectionAttribute<Pizzabase, Itempedido> itempedidoCollection;
    public static volatile SingularAttribute<Pizzabase, String> nombre;

}