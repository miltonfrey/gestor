package controllers;

import entities.Usuario;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import services.UsuarioService;
import utils.BeanUtilidades;



@Named(value = "crearAdminController")
@ViewScoped
public class CrearAdminController implements Serializable{
    
    @Inject
    private BeanUtilidades beanUtilidades;
    
    @EJB
    private UsuarioService usuarioService;
    
    public CrearAdminController() {
    }
    
    private String login;
    private String password;
    private String passwordAux;
    private String nuevoPassword;
    

    

   
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

    public String getPasswordAux() {
        return passwordAux;
    }

    public void setPasswordAux(String passwordAux) {
        this.passwordAux = passwordAux;
    }

    public String getNuevoPassword() {
        return nuevoPassword;
    }

    public void setNuevoPassword(String nuevoPassword) {
        this.nuevoPassword = nuevoPassword;
    }
    
    
    
    public String crearAdmin(){
        
        if(password.equals(passwordAux)==false){
            beanUtilidades.creaMensaje("el password no coincide con la confirmación", FacesMessage.SEVERITY_ERROR);
           return null;
        }
        
        
        Short s=2;
       
        Usuario u=new Usuario(login,password,s,"tutor","tutor","tutor");
        try{
        usuarioService.insertarUsuario(u);
    }catch(Exception ex){
        beanUtilidades.creaMensaje("Ese usuario ya existe", FacesMessage.SEVERITY_ERROR);
        return null;
    }
        
        beanUtilidades.creaMensaje("usuario creado", FacesMessage.SEVERITY_INFO);
        password="";
        passwordAux="";
        nuevoPassword="";
        return null;
                
    
}
    
    public String cambiarPasswordAdmin(){
        
        HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario u=(Usuario)session.getAttribute("admin");
        if(usuarioService.md5Password(password).equals(u.getPassword())==false){
            beanUtilidades.creaMensaje("password erróneo", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        if(nuevoPassword.equals(passwordAux)==false){
            beanUtilidades.creaMensaje("el password no coincide con la confirmación", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        u.setPassword(usuarioService.md5Password(nuevoPassword));
        usuarioService.actualizar(u);
        beanUtilidades.creaMensaje("password modificado", FacesMessage.SEVERITY_INFO);
        password="";
        passwordAux="";
        nuevoPassword="";
        login="";
        return null;
    }
    

}
    
    

