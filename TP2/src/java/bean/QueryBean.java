package bean;

import entities.Utilizador;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class QueryBean {

    Utilizador novoUtilizador = new Utilizador();
    
    @PersistenceContext(unitName = "TP2PU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    public List<Utilizador> getUtilizador () {
        return (List<Utilizador>) em.createNamedQuery("Utilizador.findAll").getResultList();
    }    
    
    /**
     * Adiciona um novo utilizador
     * @param username
     * @param hashpassword -> falta fazer o hash no utilizadorescontroller
     * @return o utilizador
     */
    public boolean addUtilizador (String username, String password) {
        
        boolean addedUser = false;
        
        novoUtilizador = new Utilizador(username, password);
            
        try {
            em.persist(novoUtilizador);
            addedUser = true;
        }
        catch (Exception ex) { }
        
        return addedUser;
    }  
    
    public boolean checkLogin (String username, String password) {
        
        boolean authenticated = false;
        
        // Obter o hash da password registada do utilizador.
        try {
            String user_password = (String) em.createNamedQuery("Utilizador.getUserPassword")
                .setParameter("username", username).getSingleResult();
        
        
            if (user_password.equals(password)) {
                authenticated = true;
            }
        }
        catch (Exception ex) { System.out.println(ex.getMessage()); }

        // Calcular o hash da password introduzida.
        //String hashedPassword = DigestUtils.sha256Hex(password);
            
        // Verificação da password introduzida com a registada.
        //if (user_password.equals(hashedPassword)) {
        return authenticated;
    }

    /*
    // Calcular hash.
    public String calculateSHA256(String password) {
        MessageDigest hash = null;
        try {
            hash = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            
        }
        byte[] encodedhash = hash.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < encodedhash.length; i++) {
            String hex = Integer.toHexString(0xff & encodedhash[i]);
            if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
        }
        return hexString.toString();
    }
*/
}