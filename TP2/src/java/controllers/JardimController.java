package controllers;

import bean.MenuBean;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import bean.UtilizadorBean;
import entities.Reclamacao;
import entities.Recurso;
import entities.Requisitar;
import entities.Reservar;
import entities.Utilizador;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import javax.enterprise.context.SessionScoped;

@Named (value = "jardimController")
@SessionScoped
public class JardimController implements Serializable 
{
    @EJB
    private UtilizadorBean queryBean;

    @EJB
    private MenuBean recursoBean;

    /** Current user */
    private Utilizador user = new Utilizador();

    Recurso recurso = new Recurso();
    
    Requisitar requisitar = new Requisitar();
    
    Reclamacao reclamacao = new Reclamacao();

    List<Recurso> recursosTags = new ArrayList<>();

    List<Reclamacao> reclamacoesId = new ArrayList<>();

    public List<Reclamacao> getReclamacoesId() {
        return reclamacoesId;
    }

    public void setReclamacoesId(List<Reclamacao> reclamacoesId) {
        this.reclamacoesId = reclamacoesId;
    }
    
    public UtilizadorBean getQueryBean() 
    {
        return queryBean;
    }

    public void setQueryBean(UtilizadorBean queryBean) 
    {
        this.queryBean = queryBean;
    }
    
    public MenuBean getRecursoBean() 
    {
        return recursoBean;
    }

    public void setRecursoBean(MenuBean recursoBean) 
    {
        this.recursoBean = recursoBean;
    }
    
    public Utilizador getUser() 
    {
        return user;
    }
    
    public void setUser(Utilizador user)
    {
        this.user = user;
    }
    
    public Recurso getRecurso() 
    {
        return recurso;
    }

    public void setRecurso(Recurso recurso) 
    {
        this.recurso = recurso;
    }
    
    public Requisitar getRequisitar() 
    {
        return requisitar;
    }

    public void setRequisitar(Requisitar requisitar) 
    {
        this.requisitar = requisitar;
    }
    
    public Reclamacao getReclamacao() 
    {
        return reclamacao;
    }

    public void setReclamacao(Reclamacao reclamacao) 
    {
        this.reclamacao = reclamacao;
    }
    
    public List<Recurso> getRecursos() 
    {
        return recursoBean.consultarTodos();
    }
    
    public List<Recurso> getRecursosTags() 
    {
        return recursosTags;
    }
    
    public List<Requisitar> getRequisitados() 
    {
        return recursoBean.consultarRequisitados(user, false);
    }

    public List<Requisitar> getHistoricoRequisitados() 
    {
        return recursoBean.consultarHistoricoRequisitados(user);
    }
    
    public Integer getNotifications()
    {
        return recursoBean.consultarNotifications(user).size();
    }
    
    public List<Reservar> getNotificationList()
    {
        List<Reservar> notifications = recursoBean.consultarNotifications(user);
        
        // apagar todas as notificações da tabela
        for(Reservar r : notifications)
            recursoBean.removerReserva(r.getId());
        
        return notifications;
    }
    
    public List<Reclamacao> getReclamacoes() 
    {
        return recursoBean.consultarReclamacoes();
    }
    

    /**
     * Signs up a new user
     * @return The according web page
     */
    public String SignUp() 
    {
        try 
        {
            if(queryBean.addUtilizador(user))
                return "MainMenu.xhtml";
        } 
        catch (Exception e) 
        {
        }
        
        user.username = "";
        user.hashpassword = "";
        
        
        return "SignUp_Error.xhtml";
    }
    
    /**
     * Signs in a user
     * @return The according web page
     */
    public String SignIn()
    {
        if(queryBean.checkLogin(user))
            return "MainMenu.xhtml";
        
        user.username = "";
        user.hashpassword = "";
        
        return "SignIn_Error.xhtml";
    }
    
    /**
     * Signs in a user
     * @return The according web page
     */
    public Boolean IsUserLogged()
    {
        return user.username != null && !"".equals(user.username);
    }

    /**
     * Logs out the current user
     * @return The index web page
     */
    public String LogOut() 
    {
        this.user = new Utilizador();
        return "index.xhtml";
    }
    
    /**
     * Adds a new resource
     * @return The according web page
     */
    public String AddNewRecurso() 
    {
        recurso.setRecursoid(recursoBean.greatestRecursoId() + 1);
        recurso.setProprietario(user.username);
        
        recursoBean.insert(recurso);
        
        return "MainMenu.xhtml";
    }
    
    /**
     * Search available resources by tag(s)
     * @return The according web page
     */
    public String SearchRecursos() 
    {    
        recursosTags = recursoBean.consultarTag(recurso.tags);
        return "SearchByTags.xhtml";
    }
    
    /**
     * Solicites a certain resource, if possible
     * @return The accordign web page
     */
    public String SolicitRecurso()
    {
        try
        {
            if(recursoBean.requisitar(recurso.recursoid))
            {
                // Requisita o recurso
                Integer id = recursoBean.greatestRequisitoId() + 1;
        
                Requisitar r = new Requisitar();
                r.setId(id);
                r.data = Date.valueOf(LocalDate.now());
                r.devolvido = false;
                r.recursoid = recursoBean.FindById(recurso.recursoid);
                r.username = user;
                
                recursoBean.inserirRequisitar(r);
                
                return "SolicitRecurso_Sucess.xhtml";
            }
        }
        catch(NullPointerException ex)
        {
        }
        
        return "SolicitRecurso_Error.xhtml";
    }

    /**
     * Reserves a certain resource, if possible
     * @return The accordign web page
     */
    public String ReserveRecurso()
    {
        try
        {
            // If it's not possible to solicite then we can reserve
            if(!recursoBean.requisitar(recurso.recursoid))
            {
                // Requisita o recurso
                Integer id = recursoBean.greatestReservarId() + 1;
        
                Reservar r = new Reservar();
                r.setId(id);
                r.data = Date.valueOf(LocalDate.now());
                r.recursoid = recursoBean.FindById(recurso.recursoid);
                r.username = user;
                r.notificar = false;
                recursoBean.inserirReserva(r);
                
                return "ReserveRecurso_Sucess.xhtml";
            }
        }
        catch(NullPointerException ex)
        {
        }
        
        return "ReserveRecurso_Error.xhtml";
    }
    
    /**
     * Removes a certain resource if it exists
     * @return The according web page
     */   
    public String RemoveRecurso()
    {
        try
        {
            if(recursoBean.remover(recurso.recursoid, user.username))
                return "RemoveRecurso.xhtml";
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return "RemoveRecurso_Error.xhtml";
    }
    
    /**
     * Returns a certain resource
     * @return The according web page
     */   
    public String ReturnRecurso()
    {
        try
        {
            // Devolve por id de requisição
            recursoBean.devolver(requisitar.id);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return "ReturnRecurso.xhtml";
    }
    
    /**
     * Adds a reclamation
     * @return The according web page
     */   
    public String AddReclamacao()
    {
        try
        {
            // id
            reclamacao.setId(recursoBean.greatestReclamacaoId()+1);
            
            reclamacao.setUsername(user.getUsername());

            if(recursoBean.CanAddReclamacao(user, reclamacao.idrecurso))
            {
                recursoBean.inserirReclamacao(reclamacao);
                return "AddReclamacao_Sucess.xhtml";
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return "AddReclamacao_Error.xhtml";
    }
    
    /**
     * Search by a reclamacao
     * @return The according web page
     */   
    public String SearchReclamacao()
    {
        try
        {
            this.reclamacoesId = recursoBean.consultarReclamacoesId(reclamacao.getId());
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return "SearchReclamacao.xhtml";
    }
}
