package bean;

import entities.Utilizador;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
public class UtilizadorBean 
{
    @PersistenceContext(unitName = "TP2PU")
    private EntityManager em;
    
    public void persist(Object object) 
    {
        em.persist(object);
    }

    public List<Utilizador> getUtilizador() 
    {
        return (List<Utilizador>) em.createNamedQuery("Utilizador.findAll").getResultList();
    }    
    
    /**
     * Adiciona um novo utilizador
     * @param username
     * @param password
     * @return o utilizador
     */
    public boolean addUtilizador (Utilizador user) 
    {
        boolean addedUser = false;
        
        try 
        {
            em.persist(user);
            addedUser = true;
        }
        catch (Exception ex) 
        { 
        }
        
        return addedUser;
    }  
    
    public boolean checkLogin(Utilizador user) 
    {   
        boolean authenticated = false;
        
        // Obter o hash da password registada do utilizador.
        try 
        {
            Utilizador tmp = (Utilizador) em.createNamedQuery("Utilizador.findByUsername")
                .setParameter("username", user.username).getSingleResult();
        
        
            if (tmp.getHashpassword().equals(calculateSHA256(user.hashpassword)))
            {
                authenticated = true;
            }
        }
        catch (Exception ex) 
        { 
            System.out.println(ex.getMessage()); 
        }
        return authenticated;
    }

    // Calcular hash.
    public String calculateSHA256(String password) 
    {
        MessageDigest hash = null;
        try 
        {
            hash = MessageDigest.getInstance("SHA-256");
        } 
        catch(NoSuchAlgorithmException ex) 
        {
            
        }
        byte[] encodedhash = hash.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < encodedhash.length; i++) 
        {
            String hex = Integer.toHexString(0xff & encodedhash[i]);
            if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
        }
        return hexString.toString();
    }

    public Utilizador getLoggedUser() 
    {
        return null;
    }
}
