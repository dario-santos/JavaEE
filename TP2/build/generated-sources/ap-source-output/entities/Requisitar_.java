package entities;

import entities.Recurso;
import entities.Utilizador;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-10T22:21:27")
@StaticMetamodel(Requisitar.class)
public class Requisitar_ { 

    public static volatile SingularAttribute<Requisitar, Date> data;
    public static volatile SingularAttribute<Requisitar, Boolean> devolvido;
    public static volatile SingularAttribute<Requisitar, Recurso> recursoid;
    public static volatile SingularAttribute<Requisitar, Integer> id;
    public static volatile SingularAttribute<Requisitar, Utilizador> username;

}