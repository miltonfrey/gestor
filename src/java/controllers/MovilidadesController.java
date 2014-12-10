
package controllers;

import entities.Mensaje;
import entities.Movilidad;
import entities.Usuario;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.primefaces.event.RowEditEvent;

import services.MensajeService;
import services.MovilidadService;
import services.UsuarioService;

import utils.BeanUtilidades;


@Named(value = "movilidadesController")
@ViewScoped
public class MovilidadesController implements Serializable{

    @Inject 
    private BeanUtilidades beanUtilidades;
    
    @EJB
    private MovilidadService movilidadService;
    
    @EJB 
    private UsuarioService usuarioService;
    
    @EJB 
    private MensajeService mensajeService;
    
    public MovilidadesController() {
    }
    
     private Usuario usuario;
    
    private String texto;
    private String tema;
    private boolean activaTexto;
    
    private String changeEstado;
    
    private Movilidad selectedMovilidad;
    private List<Movilidad>listaMovilidades;
    private ArrayList<Movilidad> filteredMovilidades;
    private ArrayList<Movilidad> selectedMovilidades;
   
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
    
  
    @PostConstruct
    public void init(){
    
       HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
      
       usuario=(Usuario)session.getAttribute("admin");
       setListaMovilidades(movilidadService.listarTodasMovilidades());
      
       }

    
     

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public boolean isActivaTexto() {
        return activaTexto;
    }

    public void setActivaTexto(boolean activaTexto) {
        this.activaTexto = activaTexto;
    }


    public ArrayList<Movilidad> getSelectedMovilidades() {
        return selectedMovilidades;
    }

    
    
    public void setSelectedMovilidades(ArrayList<Movilidad> selectedMovilidades) {
        this.selectedMovilidades = selectedMovilidades;
    }

    public Movilidad getSelectedMovilidad() {
        return selectedMovilidad;
    }

    public void setSelectedMovilidad(Movilidad selectedMovilidad) {
        this.selectedMovilidad = selectedMovilidad;
    }
    
    
    
    

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Movilidad> getListaMovilidades() {
        return listaMovilidades;
    }

    public void setListaMovilidades(List<Movilidad> listaMovilidades) {
        this.listaMovilidades = listaMovilidades;
    }
    
   
    public ArrayList<Movilidad> getFilteredMovilidades() {
        return filteredMovilidades;
    }

    public void setFilteredMovilidades(ArrayList<Movilidad> filteredMovilidades) {
        this.filteredMovilidades = filteredMovilidades;
    }

    public String getChangeEstado() {
        return changeEstado;
    }

    public void setChangeEstado(String changeEstado) {
        this.changeEstado = changeEstado;
    }

    
       
    public String verContratos(){
       
        
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad", selectedMovilidad);
        
        return ("verContratos.xhtml?faces-redirect=true");
       
    }    
   

   
    public void onRowCancel(){
        
        
    }
    
    
    
    public String onRowEdit(RowEditEvent event){
       
        Movilidad m=(Movilidad)event.getObject();
        
        if(changeEstado.equals(m.getEstado())==false){
        
        m.setEstado(changeEstado);
        try{
            
        movilidadService.cambiarMovilidad(m,changeEstado);
        }catch(RuntimeException ex){
            beanUtilidades.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
            setListaMovilidades(movilidadService.listarTodasMovilidades());
            return "verMovilidades.xhtml";
        }
                
        Mensaje mensaje=new Mensaje();
        mensaje.setDestino(m.getLoginUsuario());
        mensaje.setEliminadoDestino("no");
        mensaje.setEliminadoOrigen("no");
        mensaje.setFecha(Calendar.getInstance().getTime());
        mensaje.setLeidoDestino("no");
        mensaje.setOrigen(usuario);
        mensaje.setTema("cambio de estado de movilidad");
        mensaje.setTexto("destino:"+m.getNombreUniversidad().getNombre()+" \n"+"fecha de inicio:"+sdf.format(m.getFechaInicio())+" \n"+"fecha fin:"+sdf.format(m.getFechaFin())+"\n\n"+ "el estado de la movilidad ahora es: "+m.getEstado());
            mensajeService.enviarMensaje(mensaje);
            beanUtilidades.creaMensaje("estado de una movilidad modificado, se ha enviado un mensaje", FacesMessage.SEVERITY_INFO);
        }
               return null;
    }
    
    
        
    public String eliminaMovilidadLista(){
        if(selectedMovilidades.isEmpty()){
            return null;
        }
        
        
        for(Movilidad m:selectedMovilidades){
            try{
        movilidadService.eliminarMovilidad(m);
            }catch(EJBException ex){
                beanUtilidades.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
                return "verMovilidades.xhtml";
            }
        }
        
        actualizarTodasMovilidades();
        beanUtilidades.creaMensaje("movilidad eliminada correctamente", FacesMessage.SEVERITY_INFO);
        return null;
        
    }
    
     public void actualizarTodasMovilidades(){
        
        setListaMovilidades(movilidadService.listarTodasMovilidades());
        
    }
    
    public void activaTexto(){
        
        if(selectedMovilidades.isEmpty()==false){
        activaTexto=true;
    }else{
            beanUtilidades.creaMensaje("hay que seleccionar al menos un usuario", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    
    public String enviarMensajesVarios(){
    if(selectedMovilidades.isEmpty()){
        return null;
    }
    for(Movilidad m:selectedMovilidades){
        Mensaje mensaje=new Mensaje();
        mensaje.setDestino(m.getLoginUsuario());
        mensaje.setEliminadoDestino("no");
        mensaje.setEliminadoOrigen("no");
        mensaje.setFecha(Calendar.getInstance().getTime());
        mensaje.setLeidoDestino("no");
        mensaje.setOrigen(usuario);
        mensaje.setTema(tema);
        mensaje.setTexto(texto);
        mensajeService.enviarMensaje(mensaje);
        
        
    }
        beanUtilidades.creaMensaje("mensajes enviados correctamente", FacesMessage.SEVERITY_INFO);
        activaTexto=false;
        tema="";
        texto="";
        selectedMovilidades=null;
        
    return null;
}
    
    public void cancelar(){
        
        activaTexto=false;
    }
    
    
    
    
    }
  
    

