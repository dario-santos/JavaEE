package controllers;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import bean.QueryBean;
import bean.UserBean;
import entities.Utilizador;

@Named (value = "utilizadoresController")
@RequestScoped
public class UtilizadoresController {

    @EJB
    private QueryBean queryBean;

    public QueryBean getQueryBean() {
        return queryBean;
    }

    public void setQueryBean(QueryBean queryBean) {
        this.queryBean = queryBean;
    }
    
    String currentUser_username;

    public String getCurrentUser_username() {
        return currentUser_username;
    }

    public void setCurrentUser_username(String currentUser_username) {
        this.currentUser_username = currentUser_username;
    }
    
    Utilizador novoUtilizador = new Utilizador();
    private List<Utilizador> listaUtilizadores = new ArrayList<>();
    
    public String addNewUtilizador () {
        
        novoUtilizador.setUsername(novoUtilizador.username);
        novoUtilizador.setHashpassword(novoUtilizador.hashpassword);
        
        currentUser_username = novoUtilizador.username;
         
        try {
            boolean addedUser = queryBean.addUtilizador(novoUtilizador.username, novoUtilizador.hashpassword);

            if (addedUser) {
                listaUtilizadores = queryBean.getUtilizador();
                return "options.xhtml";
            }
        } catch (Exception e) { }
        
        
        return "addNewUtilizador_error.xhtml";
    }
    
    // Login: verificar as credenciais.
    public String login() {
        
        novoUtilizador.setUsername(novoUtilizador.username);
        novoUtilizador.setHashpassword(novoUtilizador.hashpassword);
        
        boolean autenticado = queryBean.checkLogin(novoUtilizador.username, novoUtilizador.hashpassword);
        
        if (autenticado) {
            currentUser_username = novoUtilizador.username;
            return "options.xhtml";
        }
        
        else    
            return "login_error.xhtml";
    }

    public String logout () {
        currentUser_username = "";
        return "index.xhtml";
    }

    public Utilizador getNovoUtilizador() {
        return novoUtilizador;
    }

    public void setNovoUtilizador(Utilizador novoUtilizador) {
        this.novoUtilizador = novoUtilizador;
    }

    public List<Utilizador> getListaUtilizadores() {
        listaUtilizadores = queryBean.getUtilizador();
        return listaUtilizadores;
    }

    public void setListaUtilizadores(List<Utilizador> listaUtilizadores) {
        this.listaUtilizadores = listaUtilizadores;
    }
}
