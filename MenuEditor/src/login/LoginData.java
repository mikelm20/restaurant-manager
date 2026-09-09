package login;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "LoginData", schema = "login_db", catalog = "")
public class LoginData implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;
    
    private String username;
    
    private String password;

    private String color;

    private String secondaryColor;

    private String name;



    public LoginData()
    {

    }

    public LoginData(String user, String pass, String col)
    {
        username = user;
        password = pass;
        color = col;
    }

    public int getId() {
        return idUser;
    }

    public void setId(int id) {
        this.idUser = id;
    }

   
    public String getUsername() {
        return username;
    }

   
    public void setUsername(String username) {
        this.username = username;
    }

   
    public String getPassword() {
        return password;
    }

    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getColor(){return color;}

    public void setColor(String color){this.color=color;}

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   
    
}
