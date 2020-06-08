package controllers;

import bean.ClientBean;
import entities.Recurso;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named (value = "materialController")
@RequestScoped
public class MaterialController {

    @EJB
    private ClientBean clientBean;

    public ClientBean getClientBean() {
        return clientBean;
    }

    public void setClientBean(ClientBean clientBean) {
        this.clientBean = clientBean;
    }
    
    Recurso material = new Recurso();
    
    private List<Recurso> listaMateriais = new ArrayList<>();
    private List<Recurso> listaMateriaisTags = new ArrayList<>();
    private List<Recurso> listaMateriaisInseridos = new ArrayList<>();
    private List<Recurso> listaMateriaisRequisitados = new ArrayList<>();
    private List<Recurso> listaMateriaisReservados = new ArrayList<>();
    
    // INSERIR
    public String addNewMaterial () {
        
        Integer greatestId = clientBean.greatestId("Recurso");
        
        material.setRecursoid(greatestId+1);
        material.setRecursonome(material.recursonome);
        material.setTags(material.tags);
        material.setProprietario(clientBean.getCurrentUser_username());
        material.setProprietario(clientBean.getCurrentUser_username());
        material.setEstado("Disponível");
        
        clientBean.inserir(material);
        
        resetMaterial();
        return "consultarTodos.xhtml";
    }
    
    // CONSULTAR
    // Permite obter uma lista que contém os Materiais Todos.
    public List<Recurso> getListaMateriais() {
        listaMateriais = clientBean.consultarTodos();
        return listaMateriais;
    }
    
    // Permite obter uma lista que contém os Materiais Inseridos pelo utilizador atual.
    public List<Recurso> getListaMateriaisInseridos() {
        listaMateriaisInseridos = clientBean.consultarInseridos();
        return listaMateriaisInseridos;
    }

    public List<Recurso> getListaMateriaisRequisitados() {
        listaMateriaisRequisitados = clientBean.consultarRequisitados();
        return listaMateriaisRequisitados;
    }

    public List<Recurso> getListaMateriaisReservados() {
        listaMateriaisReservados = clientBean.consultarReservados();
        return listaMateriaisReservados;
    }

    public void setListaMateriaisInseridos(List<Recurso> listaMateriaisInseridos) {
        this.listaMateriaisInseridos = listaMateriaisInseridos;
    }
    
    public void setListaMateriaisRequisitados(List<Recurso> listaMateriaisRequisitados) {
        this.listaMateriaisRequisitados = listaMateriaisRequisitados;
    }
    
    public void setListaMateriaisReservados(List<Recurso> listaMateriaisReservados) {
        this.listaMateriaisReservados = listaMateriaisReservados;
    }
    
    // Permite consultar os Materiais que Possuam certas Tags.
    public String searchMaterials () {
        material.setTags(material.tags);
        listaMateriaisTags = clientBean.consultarTag(material.tags);
        return "consultarTags.xhtml";
    }
    
    public String removeRecurso(Recurso r) {
        try {
            if (!clientBean.remover(r)) {
                return "consultarInseridos_error.xhtml";
            }
        }
        catch(NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
        
        return "consultarInseridos.xhtml";
    }
    
    public String requisitarRecurso(Recurso r) {
        
        listaMateriaisRequisitados = clientBean.consultarRequisitados();
        listaMateriaisReservados = clientBean.consultarReservados();
        
        if (listaMateriaisRequisitados.contains(r) || listaMateriaisReservados.contains(r))
            return "consultarTodos_error.xhtml";
        
        else
            clientBean.requisitar(r);
        
        return "consultarTodos.xhtml";
    }
    
    public String devolverRecurso(Recurso r)  {
        clientBean.devolver(r.getRecursoid());
        return "consultarRequisitados.xhtml";
    }

    public List<Recurso> getListaMateriaisTags() {
        return listaMateriaisTags;
    }

    public void setListaMateriaisTags(List<Recurso> listaMateriaisTags) {
        this.listaMateriaisTags = listaMateriaisTags;
    }
    
    public Recurso getMaterial() {
        return material;
    }

    public void setMaterial(Recurso material) {
        this.material = material;
    }

    public void setListaMateriais(List<Recurso> listaMateriais) {
        this.listaMateriais = listaMateriais;
    }
    
    public void resetMaterial () {
        material.setRecursonome("");
        material.setTags("");
    }
}
