
package controllers;

import entities.Mensaje;
import entities.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

import javax.servlet.http.HttpSession;

import services.MensajeService;




@Named(value = "mensajesRecibidosUserController")
@ViewScoped
public class MensajesRecibidosUserController implements Serializable{

    
    
    @EJB
    private MensajeService mensajeService;
    
    public MensajesRecibidosUserController() {
    }
     private Usuario user;
    
     private boolean activaRecibido;
    private String textAreaRecibido;
    private String temaRecibido;
    
    private boolean activaTexto;
    
    private Mensaje selectedMensajeRecibido;
    private List<Mensaje> listaMensajesRecibidos;
    private ArrayList<Mensaje> selectedMensajesRecibidos;
    private ArrayList<Mensaje> filteredMensajesRecibidos;
    
    
    
    
    
    
    @PostConstruct
    public void init(){
        HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        user=(Usuario)session.getAttribute("user");
        setListaMensajesRecibidos(mensajeService.mensajesRecibidos("admin", user.getLogin()));
    }

    

    public boolean isActivaRecibido() {
        return activaRecibido;
    }

    public void setActivaRecibido(boolean activaRecibido) {
        this.activaRecibido = activaRecibido;
    }

    public String getTextAreaRecibido() {
        return textAreaRecibido;
    }

    public void setTextAreaRecibido(String textAreaRecibido) {
        this.textAreaRecibido = textAreaRecibido;
    }

    public String getTemaRecibido() {
        return temaRecibido;
    }

    public void setTemaRecibido(String temaRecibido) {
        this.temaRecibido = temaRecibido;
    }

    public boolean isActivaTexto() {
        return activaTexto;
    }

    public void setActivaTexto(boolean activaTexto) {
        this.activaTexto = activaTexto;
    }

    public Mensaje getSelectedMensajeRecibido() {
        return selectedMensajeRecibido;
    }

    public void setSelectedMensajeRecibido(Mensaje selectedMensajeRecibido) {
        this.selectedMensajeRecibido = selectedMensajeRecibido;
    }

    public List<Mensaje> getListaMensajesRecibidos() {
        return listaMensajesRecibidos;
    }

    public void setListaMensajesRecibidos(List<Mensaje> listaMensajesRecibidos) {
        this.listaMensajesRecibidos = listaMensajesRecibidos;
    }

   

    public ArrayList<Mensaje> getSelectedMensajesRecibidos() {
        return selectedMensajesRecibidos;
    }

    public void setSelectedMensajesRecibidos(ArrayList<Mensaje> selectedMensajesRecibidos) {
        this.selectedMensajesRecibidos = selectedMensajesRecibidos;
    }

    public ArrayList<Mensaje> getFilteredMensajesRecibidos() {
        return filteredMensajesRecibidos;
    }

    public void setFilteredMensajesRecibidos(ArrayList<Mensaje> filteredMensajesRecibidos) {
        this.filteredMensajesRecibidos = filteredMensajesRecibidos;
    }

    
    
    
     public String leerMensajeRecibido(){
        
        activaRecibido=true;
        temaRecibido=selectedMensajeRecibido.getTema();
        textAreaRecibido=selectedMensajeRecibido.getTexto();
        selectedMensajeRecibido.setLeidoDestino("si");
        mensajeService.leerMensajeRecibido(selectedMensajeRecibido);
        actualizarRecibidos();
         return null;
    }
     
     public void actualizarRecibidos(){
     
       setListaMensajesRecibidos(mensajeService.mensajesRecibidos("admin", user.getLogin()));
        for(Mensaje m:selectedMensajesRecibidos){
            
            if(selectedMensajeRecibido!=null&&m.getIdmensaje().equals(selectedMensajeRecibido.getIdmensaje()))
             
            activaRecibido=false;
         
        }     
            
        }    
     
    public String eliminarMensajesRecibidos(){
        
        if(selectedMensajesRecibidos.isEmpty()){
            return null;
        }
        
        for(Mensaje m:selectedMensajesRecibidos){
         
            mensajeService.eliminarMensaje(m,"recibido");
           
            
        actualizarRecibidos();
        
    
    }
        return null;
    }
    
        public void cerrarMensajeRecibido(){
        
        textAreaRecibido="";
        temaRecibido="";
        activaRecibido=false;
        
    }   
              
   
    
    
    
}
    

