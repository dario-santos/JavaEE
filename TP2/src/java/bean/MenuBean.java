package bean;

import entities.Reclamacao;
import entities.Recurso;
import entities.Requisitar;
import entities.Reservar;
import entities.Utilizador;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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
    
    public List<Reservar> consultarReservados(Utilizador user) {
        
        return (List<Reservar>) em.createNamedQuery("Reservar.findByUserId")
                .setParameter("id", user.username).getResultList();
    }
    
    public List<Reservar> consultarNotifications(Utilizador user) 
    {
        return (List<Reservar>) em.createNamedQuery("Reservar.findAllNotificationsByUserId")
                .setParameter("id", user.username).getResultList();
    }
    
    public List<Reclamacao> consultarReclamacoes() 
    {
        return (List<Reclamacao>) em.createNamedQuery("Reclamacao.findAll").getResultList();
    }
    
    public List<Reclamacao> consultarReclamacoesId(Integer id) 
    {
        return (List<Reclamacao>) em.createNamedQuery("Reclamacao.findByIdrecurso")
                .setParameter("idrecurso", id).getResultList();
    }
    
    public List<Recurso> consultarTodosUser(String proprietario)
    {
        return (List<Recurso>) em.createNamedQuery("Recurso.findByProprietario")
                .setParameter("proprietario", proprietario).getResultList();
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
    
    
    public void inserirReclamacao(Reclamacao r) 
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
    
    public int greatestReclamacaoId()
    {    
        Integer max = 0;
        
        List<Integer> ids = em.createNamedQuery("Reclamacao.findAllId").getResultList();
        
        for (Integer id : ids)
            if (id > 0)
                max = id;
        
        return max;
    } 
    
    public void inserirReserva(Reservar r)
    {
        em.persist(r);
    }

    public Boolean devolver(Integer id)
    {
        Boolean devolvido = false;
        try
        {
            // Vai buscar o requisito com o id dado
            Requisitar requisitar = em.find(Requisitar.class, id);
            
            // Verifica se já foi devolvido
            if(requisitar.devolvido)
                return devolvido;
            
            // Atualiza como devolvido
            em.createQuery("update Requisitar set devolvido = true where id=" + requisitar.id).executeUpdate();
            
            devolvido = true;
            
            // Vai buscar a última reserva colocada
            List<Reservar> reservas =  em.createNamedQuery("Reservar.findFirst")
                    .setParameter("id", requisitar.recursoid.recursoid).getResultList();
            
            Reservar reserva = reservas.get(0);
            
            // Atualiza a linha da reserva para notificar quando o utilizador for ao menu principal
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
        return devolvido;
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

    public boolean CanAddReclamacao(Utilizador user, int idrecurso) 
    {
        try
        {
            em.createNamedQuery("Requisitar.findByRecursoIdUsername")
                .setParameter("idrecurso", idrecurso).setParameter("username", user.username).getSingleResult();
            return true;
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return false;
    }

    
    public List<Requisitar> consultarRequisitadosUser(String proprietario) {
        List<Requisitar> nao_devolvidos = (List<Requisitar>) em.createNamedQuery("Requisitar.findByUserId")
                .setParameter("id", proprietario).setParameter("devolvido", false).getResultList();
        
        List<Requisitar> devolvidos = (List<Requisitar>) em.createNamedQuery("Requisitar.findByUserId")
                .setParameter("id", proprietario).setParameter("devolvido", true).getResultList();
        
        List<Requisitar> todos = new ArrayList<>();
        
        for (int i=0; i<nao_devolvidos.size(); i++) 
            todos.add(nao_devolvidos.get(i));
        
        for (int i=0; i<devolvidos.size(); i++) 
            todos.add(devolvidos.get(i));
        
        return todos;
    }

    public List<Reservar> consultarReservadosUser(String proprietario) {
        return (List<Reservar>) em.createNamedQuery("Reservar.findByUserId")
                .setParameter("id", proprietario).getResultList();
    }

    public List<Reclamacao> consultarReclamacoesUser(String proprietario) {
        return (List<Reclamacao>) em.createNamedQuery("Reclamacao.findByUsername")
                .setParameter("username", proprietario).getResultList();
    }

}
