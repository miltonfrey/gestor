
package controllers;

import entities.Mensaje;
import entities.Movilidad;
import entities.Usuario;
import exceptions.UsuarioNotFoundException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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

import services.MensajeService;
import services.MovilidadService;
import services.UsuarioService;


import utils.BeanUtilidades;


@Named(value = "misMovilidadesController")
@ViewScoped
public class MisMovilidadesController implements Serializable{
    
    @Inject 
    private BeanUtilidades beanUtilidades;
    
    @EJB
    private MovilidadService movilidadService;
    
    @EJB 
    private UsuarioService usuarioService;
    
    @EJB 
    private MensajeService mensajeService;
    

    
    public MisMovilidadesController() {
    }
    private Usuario usuario;
    
    
    
    private Movilidad selectedMovilidad;
    private List<Movilidad> listaMisMovilidades;
    private ArrayList<Movilidad> filteredMovilidades;
    
   
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
    
    
    @PostConstruct
    public void init(){
    
       HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
       usuario=(Usuario)session.getAttribute("user");
       setListaMisMovilidades(movilidadService.listarMisMovilidades(usuario.getLogin()));
       Collections.reverse(listaMisMovilidades);
       
        
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

    public List<Movilidad> getListaMisMovilidades() {
        return listaMisMovilidades;
    }

    public void setListaMisMovilidades(List<Movilidad> listaMisMovilidades) {
        this.listaMisMovilidades = listaMisMovilidades;
    }
    
   

    public ArrayList<Movilidad> getFilteredMovilidades() {
        return filteredMovilidades;
    }

    public void setFilteredMovilidades(ArrayList<Movilidad> filteredMovilidades) {
        this.filteredMovilidades = filteredMovilidades;
    }

    
    public String eliminarMovilidad(){
        Usuario admin=null;
        try{
         admin=usuarioService.find(("admin"));
        }catch(UsuarioNotFoundException ex){
        }
        
        
        if(selectedMovilidad.getEstado().equalsIgnoreCase("pendiente")){
           
            try{
            movilidadService.eliminarMovilidad(selectedMovilidad);
            }catch(EJBException ex){
                    beanUtilidades.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
                    return "verMisMovilidades.xhtml";
                    }
            Mensaje mensaje=new Mensaje();
            mensaje.setDestino(admin);
            mensaje.setEliminadoDestino("no");
            mensaje.setEliminadoOrigen("no");
            mensaje.setFecha(Calendar.getInstance().getTime());
            mensaje.setLeidoDestino("no");
            mensaje.setOrigen(usuario);
            mensaje.setTema("movilidad eliminada");
            mensaje.setTexto("el usuario "+usuario.getLogin()+" ha eliminado una movilidad");
                mensajeService.enviarMensaje(mensaje);
                beanUtilidades.creaMensaje("movilidad eliminada correctamente, se ha enviado un mensaje al coordinador ", FacesMessage.SEVERITY_INFO);
                selectedMovilidad=null;
                actualizar();
                return null;
            }
            
         
            if(selectedMovilidad.getEstado().equalsIgnoreCase("aceptada")){
             
            Mensaje mensaje=new Mensaje();
            mensaje.setDestino(admin);
            mensaje.setEliminadoDestino("no");
            mensaje.setEliminadoOrigen("no");
            mensaje.setFecha(Calendar.getInstance().getTime());
            mensaje.setLeidoDestino("no");
            mensaje.setOrigen(usuario);
            mensaje.setTema("movilidad eliminada");
            mensaje.setTexto("el usuario "+usuario.getLogin()+" quiere cancelar una movilidad en curso en: "+selectedMovilidad.getNombreUniversidad().getNombre()+" con fecha de inicio:"+ sdf.format(selectedMovilidad.getFechaInicio())+" y fecha fin:"+sdf.format(selectedMovilidad.getFechaFin()));
                mensajeService.enviarMensaje(mensaje);
                beanUtilidades.creaMensaje("se ha enviado un mensaje al coordinador para su cancelación", FacesMessage.SEVERITY_INFO);
                selectedMovilidad=null;
                return null;
                    
            }
                
                if(selectedMovilidad.getEstado().equalsIgnoreCase("rechazada")){
                    try{
                movilidadService.eliminarMovilidad(selectedMovilidad);    
                    }catch(EJBException ex){
                        beanUtilidades.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
                        return "verMisMovilidades.xhtml";
                    }
                actualizar();     
                beanUtilidades.creaMensaje("movilidad eliminada correctamente", FacesMessage.SEVERITY_INFO);
                selectedMovilidad=null;
                return null;
           
    }
     return null;
    }
    public void actualizar(){
        
        setListaMisMovilidades(movilidadService.listarMisMovilidades(usuario.getLogin()));
        Collections.reverse(listaMisMovilidades);
    }
    
   
    }
    

    
    



