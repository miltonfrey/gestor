
package controllers;

import entities.Mensaje;
import entities.Usuario;
import exceptions.UsuarioNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import services.MensajeService;
import services.UsuarioService;

import utils.BeanUtilidades;


@Named(value = "escribeMensajeController")
@ViewScoped
public class EscribeMensajeController implements Serializable{

    @Inject
    private BeanUtilidades beanUtilidades;
    
    @EJB
    private MensajeService mensajeService;
    
    @EJB 
    private UsuarioService usuarioService;
    
    
    public EscribeMensajeController() {
    }
    
    
    private Usuario user;
    
    private String tema;
    private String texto;
    
    private boolean activaTexto;
    
    
    
    private ArrayList<Usuario> selectedUsuarios;
    
    
   
    
    @PostConstruct
    public void init(){
        HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        user=(Usuario)session.getAttribute("admin");
    }

   

    public boolean isActivaTexto() {
        return activaTexto;
    }

    public void setActivaTexto(boolean activaTexto) {
        this.activaTexto = activaTexto;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    
    
    
    
    

    public ArrayList<Usuario> getSelectedUsuarios() {
        return selectedUsuarios;
    }

    public void setSelectedUsuarios(ArrayList<Usuario> selectedUsuarios) {
        this.selectedUsuarios = selectedUsuarios;
    }
    
    
    
    
    
    public void activarTexto(){
        
        if(selectedUsuarios.isEmpty()==false){
        activaTexto=true;
    }else{
            beanUtilidades.creaMensaje("hay que seleccionar al menos un usuario", FacesMessage.SEVERITY_ERROR);
        }
    }
    
   
    
     public String enviarMensajesVarios(){
         
         Usuario aux=null;
         try{
         
         aux=usuarioService.find("admin");
         }catch(UsuarioNotFoundException ex){
             
         }
    if(selectedUsuarios.isEmpty()){
        return null;
    }
    for(Usuario u:selectedUsuarios){
        
        Mensaje mensaje=new Mensaje();
        mensaje.setDestino(u);
        mensaje.setEliminadoDestino("no");
        mensaje.setEliminadoOrigen("no");
        mensaje.setFecha(Calendar.getInstance().getTime());
        mensaje.setLeidoDestino("no");
        mensaje.setOrigen(aux);
        mensaje.setTema(tema);
        mensaje.setTexto(texto);
        try{
        mensajeService.enviarMensaje(mensaje);
        }catch(Exception ex){
            
            beanUtilidades.creaMensaje("error al enviar mensajes", FacesMessage.SEVERITY_ERROR);
            return null;
        }
    }
        beanUtilidades.creaMensaje("mensajes enviados correctamente", FacesMessage.SEVERITY_INFO);
        activaTexto=false;
        selectedUsuarios=null;
        tema="";
        texto="";
    return null;
}
    
     
      public void cancelarEnvioCoordinador(){
        
        activaTexto=false;
    }
    
       
}


