
package controllers;

import entities.Usuario;
import exceptions.PasswordIncorrectoException;
import exceptions.UsuarioNotFoundException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import services.UsuarioService;

import utils.BeanUtilidades;


@Named(value = "autenticarUsuarioController")
@ViewScoped
public class AutenticarUsuarioController implements Serializable{

    @EJB
    private UsuarioService usuarioService;
    
    @Inject
    private BeanUtilidades beanUtilidades;
    
    public AutenticarUsuarioController() {
    }
    
    private String login;
    private String password;  
    
   

    @PostConstruct
    public void init(){
        
        
    }
    
    
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    public String autenticarUsuario(){
             
        Usuario u;
        
             try{
             u=usuarioService.find(getLogin());
             }catch(UsuarioNotFoundException ex){
              beanUtilidades.creaMensaje("login inexistente", FacesMessage.SEVERITY_ERROR);
              return null; 
             }
             
             
            try{ 
            usuarioService.autenticarUsuario(password,u);
            }catch(PasswordIncorrectoException ex){
               beanUtilidades.creaMensaje("password incorrecto", FacesMessage.SEVERITY_ERROR);
               return null;
            }
            
                HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                session.setAttribute("user", u);
                return "usuario/index.xhtml?faces-redirect=true";
            
        }
    
    
}

    

