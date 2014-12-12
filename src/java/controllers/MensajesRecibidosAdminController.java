
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
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import services.MensajeService;

import utils.BeanUtilidades;


@Named(value = "mensajesRecibidosAdminController")
@ViewScoped
public class MensajesRecibidosAdminController implements Serializable{

   @Inject 
    private BeanUtilidades beanUtilidades;
    
    @EJB
    private MensajeService mensajeService;
    
    
    public MensajesRecibidosAdminController() {
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
    
    private ArrayList<Usuario> selectedUsuarios;
    
    private ArrayList<String> estados;
    
    
    
    @PostConstruct
    public void init(){
         ArrayList<String> aux= new ArrayList<String>();
        aux.add("si");
        aux.add("no");
        setEstados(aux);
        
        HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        user=(Usuario)session.getAttribute("admin");
        setListaMensajesRecibidos(mensajeService.mensajesRecibidosTotal("admin"));
        
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

    public ArrayList<Usuario> getSelectedUsuarios() {
        return selectedUsuarios;
    }

    public void setSelectedUsuarios(ArrayList<Usuario> selectedUsuarios) {
        this.selectedUsuarios = selectedUsuarios;
    }

    public ArrayList<String> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<String> estados) {
        this.estados = estados;
    }
    
    
    
    
    
     public String leerMensajeRecibido(){
        
        
        activaRecibido=true;
        temaRecibido=selectedMensajeRecibido.getTema();
        textAreaRecibido=selectedMensajeRecibido.getTexto();
        selectedMensajeRecibido.setLeidoDestino("si");
        try{
        mensajeService.leerMensajeRecibido(selectedMensajeRecibido);
        }catch(RuntimeException ex){
            
        }
        actualizarRecibidos();
         return null;
     }
     
     public void actualizarRecibidos(){
     
        setListaMensajesRecibidos(mensajeService.mensajesRecibidosTotal("admin"));
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
         
            try{
            mensajeService.eliminarMensaje(m,"recibido");
            }catch(RuntimeException ex){
                
            }
            
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

