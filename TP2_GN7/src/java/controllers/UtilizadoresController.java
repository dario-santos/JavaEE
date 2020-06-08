package controllers;

import bean.ClientBean;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import entities.Utilizador;

@Named (value = "utilizadoresController")
@RequestScoped
public class UtilizadoresController {

    @EJB
    private ClientBean clientBean;

    Utilizador novoUtilizador = new Utilizador();
    private List<Utilizador> listaUtilizadores = new ArrayList<>();

    public ClientBean getClientBean() {
        return clientBean;
    }

    public void setClientBean(ClientBean clientBean) {
        this.clientBean = clientBean;
    }
    
    public String addNewUtilizador () {
        
        novoUtilizador.setUsername(novoUtilizador.username);
        novoUtilizador.setHashpassword(novoUtilizador.hashpassword);
        
        clientBean.setCurrentUser_username(novoUtilizador.username);
         
        try {
            boolean addedUser = clientBean.addUtilizador(novoUtilizador.username, novoUtilizador.hashpassword);

            if (addedUser) {
                listaUtilizadores = clientBean.getUtilizador();
                clientBean.setAutenticado(true);
                return "options.xhtml";
            }
        } catch (Exception e) { }
        
        
        return "addNewUtilizador_error.xhtml";
    }
    
    /**
     * Login: verificar as credenciais introduzidas em "login.xhtml" 
     * ou em "login_error.xhtml", caso a autenticação tenha falhado
     * por não correspondência de credenciais.
     * @return "options.xhtml" se foi autenticado com sucesso, "login_error.xhtml" caso contrário.
     */
    public String login() {
        
        novoUtilizador.setUsername(novoUtilizador.username);
        novoUtilizador.setHashpassword(novoUtilizador.hashpassword);
        
        boolean autenticado = clientBean.checkLogin(novoUtilizador.username, novoUtilizador.hashpassword);
        
        if (autenticado) {
            clientBean.setCurrentUser_username(novoUtilizador.username);
            clientBean.setAutenticado(true);
            return "options.xhtml";
        }
        
        else    
            return "login_error.xhtml";
    }

    public String logout () {
        clientBean.setAutenticado(false);
        clientBean.setCurrentUser_username("");
        return "index.xhtml";
    }

    public Utilizador getNovoUtilizador() {
        return novoUtilizador;
    }

    public void setNovoUtilizador(Utilizador novoUtilizador) {
        this.novoUtilizador = novoUtilizador;
    }

    public List<Utilizador> getListaUtilizadores() {
        listaUtilizadores = clientBean.getUtilizador();
        return listaUtilizadores;
    }

    public void setListaUtilizadores(List<Utilizador> listaUtilizadores) {
        this.listaUtilizadores = listaUtilizadores;
    }
}
