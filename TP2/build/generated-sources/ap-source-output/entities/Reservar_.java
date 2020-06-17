package entities;

import entities.Recurso;
import entities.Utilizador;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-13T22:26:31")
@StaticMetamodel(Reservar.class)
public class Reservar_ { 

    public static volatile SingularAttribute<Reservar, Boolean> notificar;
    public static volatile SingularAttribute<Reservar, Date> data;
    public static volatile SingularAttribute<Reservar, Recurso> recursoid;
    public static volatile SingularAttribute<Reservar, Integer> id;
    public static volatile SingularAttribute<Reservar, Utilizador> username;

}