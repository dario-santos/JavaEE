package controllers;

import bean.MenuBean;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import bean.UtilizadorBean;
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
    
    List<Recurso> recursosTags = new ArrayList<>();

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
        
        return "SignIn_Error.xhtml";
    }

    /**
     * Logs out the current user
     * @return The index web page
     */
    public String LogOut() 
    {
        this.user = null;
        return "index.xhtml";
    }
    
    /**
     * Adds a new resource
     * @return The according web page
     */
    public String AddNewRecurso() 
    {
        recurso.setRecursoid(recursoBean.greatestRecursoId() + 1);
        
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
            recursoBean.remover(recurso.recursoid);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return "RemoveRecurso.xhtml";
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
}
