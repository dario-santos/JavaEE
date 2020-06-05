package bean;

import entities.Recurso;
import entities.Requisitar;
import entities.Reservar;
import entities.Utilizador;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ClientBean {

    public String currentUser_username;
    public boolean autenticado;
    Utilizador novoUtilizador = new Utilizador();

    @PersistenceContext(unitName = "TP2_GN7PU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Utilizador getNovoUtilizador() {
        return novoUtilizador;
    }

    public void setNovoUtilizador(Utilizador novoUtilizador) {
        this.novoUtilizador = novoUtilizador;
    }

    public String getCurrentUser_username() {
        return currentUser_username;
    }

    public void setCurrentUser_username(String currentUser_username) {
        this.currentUser_username = currentUser_username;
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }
    
    /**
     * 
     * @return lista de todos os recursos registados.
     */
    public List<Recurso> consultarTodos() {
        return (List<Recurso>) em.createNamedQuery("Recurso.findAll").getResultList();
    }    

    public List<Recurso> consultarInseridos() {
        return (List<Recurso>) em.createNamedQuery("Recurso.findByProprietario")
                .setParameter("proprietario", currentUser_username).getResultList();
    }    
    
    public List<Recurso> consultarRequisitados() {
        
        List<Recurso> recursos = new ArrayList();
        
        List<Integer> recursos_id = (List<Integer>) em.createNamedQuery("Requisitar.findByUsername")
                .setParameter("username", currentUser_username)
                .getResultList();
        
        for ( Integer id : recursos_id ) {
            Recurso r = (Recurso) em.createNamedQuery("Recurso.findByRecursoid")
                    .setParameter("recursoid", id)
                    .getSingleResult();
            
            recursos.add(r);
        }
        
        return recursos;
    }    
    
    public List<Recurso> consultarReservados() {
        
        List<Recurso> recursos = new ArrayList();
        
        List<Integer> recursos_id = (List<Integer>) em.createNamedQuery("Reservar.findByUsername")
                .setParameter("username", currentUser_username)
                .getResultList();
        
        for ( Integer id : recursos_id ) {
            Recurso r = (Recurso) em.createNamedQuery("Recurso.findByRecursoid")
                    .setParameter("recursoid", id)
                    .getSingleResult();
            
            recursos.add(r);
        }
        
        return recursos;
    }    
    
    /**
     * Permite consultar os Materiais que Possuam certas Tags.
     * @param tag introduzido pelo utilizador em "consultarTodos.xhmtl" ou "consultarTags.xhtml".
     * @return lista de recursos que contêm tags que correspondem às introduzidas pelo utilizador.
     */
    public List<Recurso> consultarTag(String tag) {
        return (List<Recurso>) em.createNamedQuery("Recurso.findByTags")
                .setParameter("tags", "%" + tag + "%").getResultList();
    }
    
    /**
     * Obtém o id máximo presente na tabela indicada.
     * @return o id máximo encontrado.
     */
    public Integer greatestId(String tabela) {
        
        Integer max = 0;
        List<Integer> ids;
        
        ids = em.createNamedQuery(tabela+".getAll"+tabela+"id").getResultList();
        
        for (Integer id : ids) {
            if (id > 0)
                max = id;
        }
        
        return max;
    }  
    
    public void inserir(Recurso mj) {
        em.persist(mj);
    }

    public boolean remover(Recurso r) {
        
        boolean removido = false;
        
        try {
            Recurso rec = em.find(Recurso.class, r.getRecursoid());
            
            if (rec.getEstado().equals("Disponível"))  {
                em.remove(rec);
                removido = true;
            }
        }
        catch(NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
        
        return removido;
    }

    public boolean requisitar(Recurso r) {
        
        boolean sucesso = false;
        Date d = new Date();
        
        try {
        // Atualização do estado do recurso r.
        if (r.getEstado().equals("Disponível")) {
            em.createQuery(
                "UPDATE Recurso r SET r.estado = 'Requisitado' WHERE r.recursoid = :id")
                .setParameter("id", r.getRecursoid())
                .executeUpdate();

                Requisitar requisicao = new Requisitar (greatestId("Requisitar")+1, r.getRecursoid(), currentUser_username, d, false);
                em.persist(requisicao);
            }

        else {
            em.createQuery(
                "UPDATE Recurso r SET r.estado = 'Reservado' WHERE r.recursoid = :id")
                .setParameter("id", r.getRecursoid())
                .executeUpdate();

            Reservar reserva = new Reservar(greatestId("Reservar")+1, r.getRecursoid(), currentUser_username, d);
            em.persist(reserva);
        }
            sucesso = true;
        }
        catch (Exception e) {
            sucesso = false;
        }
        return sucesso;
    }

    // Falta fazer. Tá no botão devolver no "consultarRequisitados.xhtml".
    public void devolver(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void persist(Object object) {
        em.persist(object);
    }

    public void consultarMaterial() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // ----------------------------------------------------------
    // Métodos dedicados à autenticação e registo de utilizadores.
    
    
    public List<Utilizador> getUtilizador () {
        return (List<Utilizador>) em.createNamedQuery("Utilizador.findAll").getResultList();
    }
    
    /**
     * Adiciona um novo utilizador
     * @param username
     * @param password
     * @return true se foi adicionado, false se já existe.
     */
    public boolean addUtilizador (String username, String password) {
        
        boolean addedUser = false;
        
        try {
            novoUtilizador = new Utilizador(username, calculateSHA256(password));
            em.persist(novoUtilizador);
            addedUser = true;
        }
        catch (Exception ex) { System.out.println(ex.getMessage());}
        
        return addedUser;
    }  
    
    /**
     * Verifica as credenciais introduzidas.
     * @param username
     * @param password
     * @return true se login com sucesso, else false.
     */
    public boolean checkLogin (String username, String password) {
        
        boolean authenticated = false;
        
        // Obter o hash da password registada do utilizador.
        try {
            String user_password = (String) em.createNamedQuery("Utilizador.getUserPassword")
                .setParameter("username", username).getSingleResult();
        
        
            // Calcular o hash da password introduzida e
            // Verificar a password introduzida e a registada.
            if (user_password.equals(calculateSHA256((password)))) {
                authenticated = true;
            }
        }
        catch (Exception ex) { System.out.println(ex.getMessage()); }

        return authenticated;
    }

    // Calcular hash.
    public String calculateSHA256(String password) {
        MessageDigest hash = null;
        try {
            hash = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) { }
        
        byte[] encodedhash = hash.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < encodedhash.length; i++) {
            String hex = Integer.toHexString(0xff & encodedhash[i]);
            if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
        }
        return hexString.toString();
    }
}