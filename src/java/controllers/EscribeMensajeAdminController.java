
package controllers;

import entities.Mensaje;
import entities.Usuario;
import exceptions.UsuarioNotFoundException;
import java.io.Serializable;
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


@Named(value = "escribeMensajeAdminController")
@ViewScoped
public class EscribeMensajeAdminController implements Serializable{

    @Inject
    private BeanUtilidades beanUtilidades;
    
    @EJB
    private UsuarioService usuarioService;
    
    @EJB 
    private MensajeService mensajeService;
    
    public EscribeMensajeAdminController() {
    }
    
    private Usuario user;
    
    private String tema;
    private String texto;
    
   
    
    @PostConstruct
    public void init(){
       HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
       user=(Usuario)session.getAttribute("user");
        
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

    
    
    

   
    public String enviarMensajeCoordinador(){
        
        Usuario destino=null;
        try{
        destino=usuarioService.find("admin");
        }catch(UsuarioNotFoundException ex){
            
        }
        
        Mensaje m=new Mensaje();
        m.setDestino(destino);
        m.setEliminadoDestino("no");
        m.setEliminadoOrigen("no");
        m.setFecha(Calendar.getInstance().getTime());
        m.setLeidoDestino("no");
        m.setOrigen(user);
        m.setTema(tema);
        m.setTexto(texto);
       
        mensajeService.enviarMensaje(m);
        
        beanUtilidades.creaMensaje("envío correctamente", FacesMessage.SEVERITY_INFO);
        texto="";
        tema="";
        
        //actualizarEnviados();
        return null;
    }
    
   
   
}

    
    

