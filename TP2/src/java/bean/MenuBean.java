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

    public void remover(Integer id) 
    {
        try
        {
            Recurso r = em.find(Recurso.class, id);
            em.remove(r);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
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
            Requisitar r = em.find(Requisitar.class, id);
            
            // Atualiza como devolvido
            em.createQuery("update Requisitar set devolvido = true where id=" + r.id).executeUpdate();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        // Vai verificar as reservas deste recurso
        // Cria uma nova linha no requisitar com os dados da reserva
        // Atualiza a linha da reserva para notificar quando o utilizador ir ao menu principal
    }
}
