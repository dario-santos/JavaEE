package entities;

import entities.Requisitar;
import entities.Reservar;
import entities.Utilizador;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-13T22:26:31")
@StaticMetamodel(Recurso.class)
public class Recurso_ { 

    public static volatile CollectionAttribute<Recurso, Requisitar> requisitarCollection;
    public static volatile CollectionAttribute<Recurso, Reservar> reservarCollection;
    public static volatile SingularAttribute<Recurso, String> proprietario;
    public static volatile SingularAttribute<Recurso, String> recursonome;
    public static volatile SingularAttribute<Recurso, Integer> recursoid;
    public static volatile CollectionAttribute<Recurso, Utilizador> utilizadorCollection;
    public static volatile SingularAttribute<Recurso, String> tags;
    public static volatile CollectionAttribute<Recurso, Utilizador> utilizadorCollection1;

}