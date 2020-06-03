package controllers;

import bean.UserBean;
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
    private UserBean userBean;
    
    Recurso material = new Recurso();
    private List<Recurso> listaMateriais = new ArrayList<>();
    private List<Recurso> listaMateriaisTags = new ArrayList<>();
    //private List<Recurso> listaMateriais_tagCorrespondente = new ArrayList<>();
    
    // INSERIR
    public String addNewMaterial () {
        
        Integer greatestId = userBean.greatestId();
        
        material.setRecursoid(greatestId+1);
        material.setRecursonome(material.recursonome);
        material.setTags(material.tags);
        
        userBean.inserir(material);
        
        resetMaterial();
        return "consultarTodos.xhtml";
    }
    
    // CONSULTAR
    // Permite consultar os Materiais Todos.
    public List<Recurso> getListaMateriais() {
        listaMateriais = userBean.consultarTodos();
        return listaMateriais;
    }
    
    // Permite consultar os Materiais que Possuam certas Tags.
    public String searchMaterials () {
        material.setTags(material.tags);
        listaMateriaisTags = userBean.consultarTag(material.tags);
        return "consultarTags.xhtml";
    }
    // --- Fim Consultar

    public List<Recurso> getListaMateriaisTags() {
        return listaMateriaisTags;
    }

    public void setListaMateriaisTags(List<Recurso> listaMateriaisTags) {
        this.listaMateriaisTags = listaMateriaisTags;
    }
    
    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
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
