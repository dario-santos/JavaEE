package entities;

import entities.Recurso;
import entities.Requisitar;
import entities.Reservar;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-13T22:26:31")
@StaticMetamodel(Utilizador.class)
public class Utilizador_ { 

    public static volatile CollectionAttribute<Utilizador, Requisitar> requisitarCollection;
    public static volatile CollectionAttribute<Utilizador, Recurso> recursoCollection1;
    public static volatile CollectionAttribute<Utilizador, Reservar> reservarCollection;
    public static volatile CollectionAttribute<Utilizador, Recurso> recursoCollection;
    public static volatile SingularAttribute<Utilizador, String> hashpassword;
    public static volatile SingularAttribute<Utilizador, String> username;

}