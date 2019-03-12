/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.time.Duration;


/**
 *
 *
 */
public class LoginBuisness{
    
    private EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("LoginPU");
    private EntityManager em = emf.createEntityManager();

    public LoginBuisness()
    {

    }



    
public int persistLoginData(LoginData object, String conf) {
   int code;
   
   if(object.getUsername().isEmpty() || object.getPassword().isEmpty() || conf.isEmpty() ){
        code = 2;
    }
   else if(tryUser(object.getUsername())){
        if(object.getPassword().equals(conf))
        {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
            code = 0;
            
            return code;
        }
        
    code = 3;
      
    }
    
    else
        code=1;
    
    return code;
}

public boolean getLogin(String username, String password){

    boolean validData;
    
    try{
    LoginData userData = (LoginData)em.createQuery("SELECT l FROM LoginData l WHERE l.username = :username")
	                      .setParameter("username", username)
	                      .getSingleResult();
    
    validData=(userData.getPassword().equals(password));
   
    
    }
    catch(NoResultException exception){
        validData = false;
    }
    
    return validData;


}

public boolean tryUser(String user){

    boolean write;
    
    try{
    LoginData userData = (LoginData)em.createQuery("SELECT l FROM LoginData l WHERE l.username = :username")
	                      .setParameter("username", user)
	                      .getSingleResult();
    
    write=false;
    }
    catch(NoResultException exception){
        write = true;
    }
    
    return write;
    
    }

    public boolean isFirstLogin(){
        return !(em.createQuery("SELECT l FROM LoginData l").getResultList().size() > 1);

    }
    
}
