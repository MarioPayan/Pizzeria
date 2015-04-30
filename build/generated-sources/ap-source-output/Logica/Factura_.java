package Logica;

import Logica.Itempedido;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-27T23:36:08")
@StaticMetamodel(Factura.class)
public class Factura_ { 

    public static volatile SingularAttribute<Factura, Date> fecha;
    public static volatile SingularAttribute<Factura, String> facturaId;
    public static volatile SingularAttribute<Factura, Itempedido> itempedido;
    public static volatile SingularAttribute<Factura, Float> preciototal;

}