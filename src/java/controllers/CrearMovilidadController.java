/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Cursoacademico;
import entities.Mensaje;
import entities.Universidad;
import entities.Usuario;
import exceptions.DuracionException;
import exceptions.NumeroDeMovilidadesException;
import exceptions.UniversidadException;
import exceptions.UsuarioNotFoundException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import services.MensajeService;
import services.MovilidadService;
import services.UniversidadService;
import services.UsuarioService;

import utils.BeanUtilidades;

/**
 *
 * @author cba
 */
@Named(value = "crearMovilidadController")
@ViewScoped
public class CrearMovilidadController implements Serializable{
    @Inject
    private BeanUtilidades beanUtilidades;
    @EJB
    private UsuarioService usuarioService;
    @EJB
    private MovilidadService movilidadService;
    @EJB
    private UniversidadService universidadService;
    @EJB
    private MensajeService mensajeService;

    
    public CrearMovilidadController() {
    }
    
    @PostConstruct
    public void init(){
        HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        usuario=(Usuario)session.getAttribute("user");
       fechaMin=movilidadService.fechaMin();
       fechaMax=movilidadService.fechaMax();
    } 
       
       
     
    private Usuario usuario;
    
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
   
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaMin;
    private Date fechaMax;
    private Cursoacademico cursoacademico;
    
    private String selectedPais;
    private String selectedUniversidad;
    private Universidad universidad;
    
    private List<Universidad> listaUniversidades;
    
    private boolean checkPais;

   
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaMin() {
        return fechaMin;
    }

    public void setFechaMin(Date fechaMin) {
        this.fechaMin = fechaMin;
    }

    public Date getFechaMax() {
        return fechaMax;
    }

    public void setFechaMax(Date fechaMax) {
        this.fechaMax = fechaMax;
    }

    public Cursoacademico getCursoacademico() {
        return cursoacademico;
    }

    public void setCursoacademico(Cursoacademico cursoacademico) {
        this.cursoacademico = cursoacademico;
    }

    public String getSelectedPais() {
        return selectedPais;
    }

    public void setSelectedPais(String selectedPais) {
        this.selectedPais = selectedPais;
    }

    public String getSelectedUniversidad() {
        return selectedUniversidad;
    }

    public void setSelectedUniversidad(String selectedUniversidad) {
        this.selectedUniversidad = selectedUniversidad;
    }

    
    public Universidad getUniversidad() {
        return universidad;
    }

    public void setUniversidad(Universidad universidad) {
        this.universidad = universidad;
    }

    public List<Universidad> getListaUniversidades() {
        return listaUniversidades;
    }

    public void setListaUniversidades(List<Universidad> listaUniversidades) {
        this.listaUniversidades = listaUniversidades;
    }
    
    

   

    public boolean isCheckPais() {
        return checkPais;
    }

    public void setCheckPais(boolean checkPais) {
        this.checkPais = checkPais;
    }
    
    
    public void onDropboxChangePais(){
       
       checkPais=true;
       listaUniversidades=universidadService.listarPorPais(selectedPais);
     
   }
    
    public void onDropboxchangeUni(){
        
        try{
        universidad=universidadService.findUniversidad(selectedUniversidad);
        }catch(UniversidadException ex){
            beanUtilidades.creaMensaje("universidad inexistente", FacesMessage.SEVERITY_ERROR);
            
        }
    }
    
    
public String crearMovilidad(){
   
        checkPais=false;
        //checkUni=false;
        
         Universidad u;
        try{
       u=universidadService.findUniversidad(selectedUniversidad);
        }catch(UniversidadException ex){
            beanUtilidades.creaMensaje("universidad inexistente", FacesMessage.SEVERITY_ERROR);
            selectedUniversidad="";
                selectedPais="";
                selectedUniversidad="";
                fechaFin=null;
                fechaInicio=null;
                universidad=null;
            return "";
        }
        Calendar cal1=Calendar.getInstance();
        Calendar cal2=Calendar.getInstance();
                cal1.setTime(fechaInicio);
                cal2.setTime(fechaFin);
                Cursoacademico ca=universidadService.buscarCursoAcademico(fechaInicio, fechaFin);
                
              try{
                  
                  movilidadService.crearMovilidad(fechaInicio, fechaFin, usuario, u,ca);
                  
                  
             
              
              }catch(DuracionException ex){
                  beanUtilidades.creaMensaje(ex.getMessage(),FacesMessage.SEVERITY_ERROR);
                  return null;
              }catch(NumeroDeMovilidadesException ex2){
                  beanUtilidades.creaMensaje(ex2.getMessage(),FacesMessage.SEVERITY_ERROR);
                  return null;
                  
              }
              
              
               catch(RuntimeException ex){
                 beanUtilidades.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
                  return "crearMovilidad.xhtml";
              }
             
              
              Usuario admin=null;
               try{
                    admin=usuarioService.find("admin");
               }catch(UsuarioNotFoundException ex){
                   
               }
                
                
                Mensaje mensaje= new Mensaje();
                mensaje.setOrigen(usuario);
                mensaje.setDestino(admin);
                mensaje.setEliminadoDestino("no");
                mensaje.setEliminadoOrigen("no");
                mensaje.setFecha(Calendar.getInstance().getTime());
                mensaje.setLeidoDestino("no");
                mensaje.setTema("movilidad creada");
                mensaje.setTexto("el usuario "+usuario.getLogin()+""
                        + " ha creado una movilidad a "+selectedUniversidad+" entre el "+sdf.format(fechaInicio)+" y "+sdf.format(fechaFin));
                
                    mensajeService.enviarMensaje(mensaje);
                
                
                beanUtilidades.creaMensaje("movilidad creada", FacesMessage.SEVERITY_INFO);
                //beanUtilidades.creaMensaje(usuario.getLogin()+" a "+ selectedUniversidad+" "+selectedPais+" "+" " + " de "+sdf.format(fechaInicio)+" a "+ sdf.format(fechaFin), FacesMessage.SEVERITY_INFO);
                selectedUniversidad="";
                selectedPais="";
                selectedUniversidad="";
                fechaFin=null;
                fechaInicio=null;
                universidad=null;
                
                
                
                
                return "";
        
    }


   
}

    
    

