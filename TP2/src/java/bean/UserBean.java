package bean;

import entities.Recurso;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserBean {

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    @PersistenceContext(unitName = "TP2PU")
    private EntityManager em;

    public List<Recurso> consultarTodos() {
        return (List<Recurso>) em.createNamedQuery("Recurso.findAll").getResultList();
    }    
    
    // Permite consultar os Materiais que Possuam certas Tags.
    public List<Recurso> consultarTag(String tag) {
        return (List<Recurso>) em.createNamedQuery("Recurso.findByTags")
                .setParameter("tags", "%" + tag + "%").getResultList();
    }
    
    public Integer greatestId() {
        
        Integer max = 0;
        
        List<Integer> ids = em.createNamedQuery("Recurso.getAllRecursoid").getResultList();
        
        for (Integer id : ids) {
            if (id > 0)
                max = id;
        }
        
        return max;
    }  
    
    public void inserir(Recurso mj) {
        em.persist(mj);
    }

    public void remover(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void requisitar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void devolver(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void remover(Integer id) 
    {
        try
        {
            Recurso r = em.find(Recurso.class, id);
            em.remove(r);
        }
        catch(NullPointerException ex)
        {
            System.out.println(ex.getMessage());
        }
    }


    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public void persist(Object object) {
        em.persist(object);
    }

    public void consultarMaterial() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
