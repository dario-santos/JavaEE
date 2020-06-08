/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entities.Recurso;
import entities.Requisitar;
import entities.Reservar;
import entities.Utilizador;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateful
public class MenuBean
{
    @PersistenceContext(unitName = "TP2PU")
    private EntityManager em;
    
   
    public EntityManager getEm() 
    {
        return em;
    }

    public void setEm(EntityManager em) 
    {
        this.em = em;
    }

    public List<Recurso> consultarTodos() 
    {
        return (List<Recurso>) em.createNamedQuery("Recurso.findAll").getResultList();
    }    
    
    // Permite consultar os Materiais que Possuam certas Tags.
    public List<Recurso> consultarTag(String tag) 
    {
        return (List<Recurso>) em.createNamedQuery("Recurso.findByTags")
                .setParameter("tags", "%" + tag + "%").getResultList();
    }
    
    public List<Requisitar> consultarRequisitados(Utilizador user, Boolean devolvido)
    {
        return (List<Requisitar>) em.createNamedQuery("Requisitar.findByUserId")
                .setParameter("id", user.username).setParameter("devolvido", devolvido).getResultList();
    }
    
    
    public List<Requisitar> consultarHistoricoRequisitados(Utilizador user)
    {
        return (List<Requisitar>) em.createNamedQuery("Requisitar.findAllByUserId")
                .setParameter("id", user.username).getResultList();
    }
    
    public List<Reservar> consultarNotifications(Utilizador user) 
    {
        return (List<Reservar>) em.createNamedQuery("Reservar.findAllNotificationsByUserId")
                .setParameter("id", user.username).getResultList();
    }
    
    public Integer greatestRecursoId()
    {    
        Integer max = 0;
        
        List<Integer> ids = em.createNamedQuery("Recurso.getAllRecursoid").getResultList();
        
        for (Integer id : ids)
            if (id > 0)
                max = id;
        
        return max;
    } 
    
    public Integer greatestRequisitoId()
    {    
        Integer max = 0;
        
        List<Integer> ids = em.createNamedQuery("Requisitar.findAllId").getResultList();
        
        for (Integer id : ids)
            if (id > 0)
                max = id;
        
        return max;
    }  
    
    public void insert(Recurso mj) 
    {
        em.persist(mj);
    }
    
    public void inserirRequisitar(Requisitar r) 
    {
        em.persist(r);
    }

    public boolean requisitar(Integer id)
    {
        try
        {
            return em.createNamedQuery("Requisitar.findByRecursoId")
                .setParameter("id", id).getResultList().isEmpty();
            
        }
        catch(Exception ex)
        {
            return true;
        }
    }

    public Recurso FindById(Integer id)
    {
        try
        {
            Recurso r = (Recurso) em.createNamedQuery("Recurso.findByRecursoid")
                .setParameter("recursoid", id).getSingleResult();
            return r;
        }
        catch(Exception ex)
        {
            return null;
        }
    }

    public Boolean remover(Integer id, String username) 
    {
        try
        {
            Recurso r = em.find(Recurso.class, id);
            
            Boolean isNotRequisitado = em.createNamedQuery("Requisitar.findByRecursoId")
                .setParameter("id", id).getResultList().isEmpty();
            
            
            Boolean isNotReservado = em.createNamedQuery("Reservar.findByRecursoId")
                .setParameter("id", id).getResultList().isEmpty();
            
            
            if(r.getProprietario().equals(username) && isNotRequisitado && isNotReservado)
                em.remove(r);
            return true;
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return false;
    }


    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public void persist(Object object) 
    {
        em.persist(object);
    }

    public int greatestReservarId()
    {    
        Integer max = 0;
        
        List<Integer> ids = em.createNamedQuery("Reservar.findAllId").getResultList();
        
        for (Integer id : ids)
            if (id > 0)
                max = id;
        
        return max;
    } 

    public void inserirReserva(Reservar r)
    {
        em.persist(r);
    }

    public void devolver(Integer id)
    {
        try
        {
            // Vai buscar o requisito com o id dado
            Requisitar requisitar = em.find(Requisitar.class, id);
            
            // Atualiza como devolvido
            em.createQuery("update Requisitar set devolvido = true where id=" + requisitar.id).executeUpdate();
            
            // Vai buscar a última reserva colocada
            List<Reservar> reservas =  em.createNamedQuery("Reservar.findFirst")
                    .setParameter("id", requisitar.recursoid.recursoid).getResultList();
            
            Reservar reserva = reservas.get(0);
            
            // Atualiza a linha da reserva para notificar quando o utilizador ir ao menu principal
            em.createQuery("update Reservar set notificar = true where id=" + reserva.getId()).executeUpdate();
            
            // Cria uma nova linha no requisitar com os dados da reserva
            Requisitar r = new Requisitar();
            r.setId(greatestRequisitoId() + 1);
            r.data = Date.valueOf(LocalDate.now());
            r.devolvido = false;
            r.recursoid = reserva.recursoid;
            r.username = reserva.username;
                
            inserirRequisitar(r);
    
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        
    }

    public void removerReserva(Integer id) 
    {
     
        try
        {
            Reservar r = em.find(Reservar.class, id);
            em.remove(r);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
