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
    List<Recurso> recursos = new ArrayList<>();

    List<Reclamacao> reclamacoesId = new ArrayList<>();
    
    List<Reservar> notifications = new ArrayList<>();
    
    List<Recurso> recursosUserPerfilInseridos = new ArrayList<>();
    List<Requisitar> recursosUserPerfilRequisitados = new ArrayList<>();
    List<Reservar> recursosUserPerfilReservados = new ArrayList<>();
    List<Reclamacao> reclamacoesUserPerfil = new ArrayList<>();
    
    String clicked_user;

    public String getClicked_user() {
        return clicked_user;
    }

    public void setClicked_user(String clicked_user) {
        this.clicked_user = clicked_user;
    }

    public List<Recurso> getRecursosUserPerfilInseridos() {
        return recursosUserPerfilInseridos;
    }

    public void setRecursosUserPerfilInseridos(List<Recurso> recursosUserPerfilInseridos) {
        this.recursosUserPerfilInseridos = recursosUserPerfilInseridos;
    }

    public List<Requisitar> getRecursosUserPerfilRequisitados() {
        return recursosUserPerfilRequisitados;
    }

    public void setRecursosUserPerfilRequisitados(List<Requisitar> recursosUserPerfilRequisitados) {
        this.recursosUserPerfilRequisitados = recursosUserPerfilRequisitados;
    }

    public List<Reservar> getRecursosUserPerfilReservados() {
        return recursosUserPerfilReservados;
    }

    public void setRecursosUserPerfilReservados(List<Reservar> recursosUserPerfilReservados) {
        this.recursosUserPerfilReservados = recursosUserPerfilReservados;
    }

    public List<Reclamacao> getReclamacoesUserPerfil() {
        return reclamacoesUserPerfil;
    }

    public void setReclamacoesUserPerfil(List<Reclamacao> reclamacoesUserPerfil) {
        this.reclamacoesUserPerfil = reclamacoesUserPerfil;
    }
    
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
    
    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }
    
    public List<Recurso> getRecursosTags() 
    {
        return recursosTags;
    }

    public void setRecursosTags(List<Recurso> recursosTags) 
    {
        this.recursosTags = recursosTags;
    }
    
    public List<Requisitar> getRequisitados() 
    {
        return recursoBean.consultarRequisitados(user, false);
    }

    public List<Requisitar> getHistoricoRequisitados() 
    {
        return recursoBean.consultarHistoricoRequisitados(user);
    }
    
    public List<Reservar> getReservados() 
    {
        return recursoBean.consultarReservados(user);
    }
    
    public Integer getNotifications()
    {
        return recursoBean.consultarNotifications(user).size();
    }
    
    public List<Reservar> getNotificationList()
    {
        notifications = recursoBean.consultarNotifications(user);
        
        return notifications;
    }
    
    public String returnFromNotification()
    {
        // apagar todas as notificações da tabela
        for(Reservar r : notifications)
            recursoBean.removerReserva(r.getId());
        
        return "MainMenu.xhtml";
        
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
     * Public search available resources by tag(s)
     * @return The according web page
     */
    public String PublicSearchRecursos() 
    {    
        recursosTags = recursoBean.consultarTag(recurso.tags);
        return "PublicSearchByTags.xhtml";
    }
    
    /**
     * Solicites a certain resource, if possible
     * @return The according web page
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
        catch(Exception ex)
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
        catch(Exception ex)
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
                return "RemoveRecurso_Success.xhtml";
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
        
        return "SearchReclamacaoResults.xhtml";
    }
    
    public String UsersProfile(String user) {
        this.setClicked_user(user);
        recursosUserPerfilInseridos = recursoBean.consultarTodosUser(user);
        recursosUserPerfilRequisitados = recursoBean.consultarRequisitadosUser(user);
        recursosUserPerfilReservados = recursoBean.consultarReservadosUser(user);
        reclamacoesUserPerfil = recursoBean.consultarReclamacoesUser(user);
        
        return "UserProfile.xhtml";
    }
    
    public Boolean OwnProfile(String user) 
    {
        return this.user.username != null && this.user.username.equals(user);
    }
}
