
 
package controllers;

import entities.Usuario;
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


@Named(value = "editarPerfilController")
@ViewScoped
public class EditarPerfilController implements Serializable{

   @EJB 
   private UsuarioService usuarioService;
   @Inject 
   private BeanUtilidades beanUtilidades;
    
    public EditarPerfilController() {
    }
    
    private Usuario user;
   private String password;
   private String nuevoPassword; 
   private String repitePassword;
   
   
    
    @PostConstruct
    public void init(){
        HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        user=(Usuario)session.getAttribute("user");
        
        
    }

   

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getNuevoPassword() {
        return nuevoPassword;
    }

    public void setNuevoPassword(String nuevoPassword) {
        this.nuevoPassword = nuevoPassword;
    }

    public String getRepitePassword() {
        return repitePassword;
    }

    public void setRepitePassword(String repitePassword) {
        this.repitePassword = repitePassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String editar(){
        
        if(user.getPassword().equals(usuarioService.md5Password(password))==false){
            
            beanUtilidades.creaMensaje("el password es incorrecto", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if(nuevoPassword.equals(repitePassword)==false){
            
            beanUtilidades.creaMensaje("el nuevo password no coincide con la confirmación", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        user.setPassword(usuarioService.md5Password(nuevoPassword));
        try{
        usuarioService.actualizar(user);
        }catch(RuntimeException ex){
            beanUtilidades.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
            return "/principal.xhtml?faces-redirect=true";
        }
        beanUtilidades.creaMensaje("password modificado correctamente", FacesMessage.SEVERITY_INFO);
        return null;
    }
    
    
    
    
    
    
    
    
    
    
    
}



    

