
package controllers;

import entities.Contrato;
import entities.Equivalencia;
import entities.Mensaje;
import entities.Movilidad;
import entities.Usuario;
import exceptions.ContratoNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import services.AsignaturaService;
import services.EquivalenciaService;
import services.MensajeService;
import services.MovilidadService;
import services.UsuarioService;

import utils.BeanUtilidades;
import utils.EquivalenciaRevisada;


@Named(value = "equivalenciasController")
@ViewScoped
public class EquivalenciasController implements Serializable{

    @Inject 
    private BeanUtilidades beanUtilidades;
    @EJB
    private UsuarioService usuarioService;
    @EJB
    private MovilidadService movilidadService;
    @EJB
    private AsignaturaService asignaturaService;
    @EJB
    private EquivalenciaService equivalenciaService;
    @EJB
    private MensajeService mensajeService;
    
    public EquivalenciasController() {
    }
    
     private ExternalContext context;
    private HttpSession session;
    private Movilidad selectedMovilidad;
    private Contrato selectedContrato;
    private Contrato contratoComparado;
    private Usuario user;
    
    private int creditosA;
    private int creditosB;
    private int creditosComparadoA;
    private int creditosComparadoB;
    
    Equivalencia equivalencia;
    
    
    
    
    
    
    private ArrayList<Equivalencia> listaAuxEquivalencias=new ArrayList<Equivalencia>();
    private ArrayList<Equivalencia> listaAuxEquivalenciasComparado=new ArrayList<Equivalencia>();
    
    private ArrayList<EquivalenciaRevisada> equivalenciasRevisadas;
    private ArrayList<EquivalenciaRevisada> equivalenciasRevisadasComparado;
    
    
    private ArrayList<EquivalenciaRevisada>selectedEquivalencias;
    private ArrayList<Equivalencia> selectedEquivalenciasSimples;
    
    private static int j=0;
    
    
    
    private String apruebaOrechaza;
    
   
    
    @PostConstruct
    public void init(){
        context=FacesContext.getCurrentInstance().getExternalContext();
        session=(HttpSession)context.getSession(false);
        
           if(context.getSessionMap().containsKey("movilidad")&&context.getSessionMap().containsKey("contrato")){
           user=(Usuario)session.getAttribute("admin");
           selectedMovilidad=(Movilidad)context.getSessionMap().get("movilidad");
           selectedContrato=(Contrato)context.getSessionMap().get("contrato");
           context.getSessionMap().remove("contrato");
           context.getSessionMap().remove("movilidad");
           try{
           selectedContrato=equivalenciaService.findContrato(selectedContrato.getIdContrato());
           }catch(ContratoNotFoundException|RuntimeException ex){
               try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/verMovilidades.xhtml");
            }catch(IOException ex2){
                    
                    }
           }
           
           listaAuxEquivalencias.addAll(selectedContrato.getEquivalenciaSet());
            creditosA=equivalenciaService.totalCreditos(listaAuxEquivalencias)[0];
            creditosB=equivalenciaService.totalCreditos(listaAuxEquivalencias)[1];
             
           if(context.getSessionMap().containsKey("contratoComparado")){
        contratoComparado=(Contrato)context.getSessionMap().get("contratoComparado");
        context.getSessionMap().remove("contratoComparado");
             try{
        contratoComparado=equivalenciaService.findContrato(contratoComparado.getIdContrato());
             }catch(ContratoNotFoundException ex3){
                 try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/verMovilidades.xhtml");
            }catch(IOException ex4){
                    
                    }
             }
        listaAuxEquivalenciasComparado.addAll(contratoComparado.getEquivalenciaSet());
        equivalenciasRevisadas=equivalenciaService.compararEquivalencias(listaAuxEquivalencias, listaAuxEquivalenciasComparado);
        equivalenciasRevisadasComparado=equivalenciaService.compararEquivalencias(listaAuxEquivalenciasComparado, listaAuxEquivalencias);
        creditosComparadoA=equivalenciaService.totalCreditos(listaAuxEquivalenciasComparado)[0];
        creditosComparadoB=equivalenciaService.totalCreditos(listaAuxEquivalenciasComparado)[1];
       
         
           }
           
        }
       else{
            try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/verMovilidades.xhtml");
            }catch(IOException ex){
                    
                    }
    }
       } 

   
    //////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<EquivalenciaRevisada> getEquivalenciasRevisadas() {
        return equivalenciasRevisadas;
    }

    public void setEquivalenciasRevisadas(ArrayList<EquivalenciaRevisada> equivalenciasRevisadas) {
        this.equivalenciasRevisadas = equivalenciasRevisadas;
    }

    public ArrayList<EquivalenciaRevisada> getEquivalenciasRevisadasComparado() {
        return equivalenciasRevisadasComparado;
    }

    public void setEquivalenciasRevisadasComparado(ArrayList<EquivalenciaRevisada> equivalenciasRevisadasComparado) {
        this.equivalenciasRevisadasComparado = equivalenciasRevisadasComparado;
    }
    
    
    
   
    public ArrayList<EquivalenciaRevisada> getSelectedEquivalencias() {
        return selectedEquivalencias;
    }

    public void setSelectedEquivalencias(ArrayList<EquivalenciaRevisada> selectedEquivalencias) {
        this.selectedEquivalencias = selectedEquivalencias;
    }

    public ArrayList<Equivalencia> getListaAuxEquivalencias() {
        return listaAuxEquivalencias;
    }

    public void setListaAuxEquivalencias(ArrayList<Equivalencia> listaAuxEquivalencias) {
        this.listaAuxEquivalencias = listaAuxEquivalencias;
    }

    public ArrayList<Equivalencia> getListaAuxEquivalenciasComparado() {
        return listaAuxEquivalenciasComparado;
    }

    public void setListaAuxEquivalenciasComparado(ArrayList<Equivalencia> listaAuxEquivalenciasComparado) {
        this.listaAuxEquivalenciasComparado = listaAuxEquivalenciasComparado;
    }

    public Movilidad getSelectedMovilidad() {
        return selectedMovilidad;
    }

    public void setSelectedMovilidad(Movilidad selectedMovilidad) {
        this.selectedMovilidad = selectedMovilidad;
    }

    public Contrato getSelectedContrato() {
        return selectedContrato;
    }

    public void setSelectedContrato(Contrato selectedContrato) {
        this.selectedContrato = selectedContrato;
    }

    public Contrato getContratoComparado() {
        return contratoComparado;
    }

    public void setContratoComparado(Contrato contratoComparado) {
        this.contratoComparado = contratoComparado;
    }

    public String getApruebaOrechaza() {
        return apruebaOrechaza;
    }

    public void setApruebaOrechaza(String apruebaOrechaza) {
        this.apruebaOrechaza = apruebaOrechaza;
    }

    public int getCreditosA() {
        return creditosA;
    }

    public void setCreditosA(int creditosA) {
        this.creditosA = creditosA;
    }

    public int getCreditosB() {
        return creditosB;
    }

    public void setCreditosB(int creditosB) {
        this.creditosB = creditosB;
    }

    public int getCreditosComparadoA() {
        return creditosComparadoA;
    }

    public void setCreditosComparadoA(int creditosComparadoA) {
        this.creditosComparadoA = creditosComparadoA;
    }

    public int getCreditosComparadoB() {
        return creditosComparadoB;
    }

    public void setCreditosComparadoB(int creditosComparadoB) {
        this.creditosComparadoB = creditosComparadoB;
    }

    public ArrayList<Equivalencia> getSelectedEquivalenciasSimples() {
        return selectedEquivalenciasSimples;
    }

    public void setSelectedEquivalenciasSimples(ArrayList<Equivalencia> selectedEquivalenciasSimples) {
        this.selectedEquivalenciasSimples = selectedEquivalenciasSimples;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
    
    
    
    
    
    public String publicarEquivalencia(){
        
        if(selectedEquivalencias.isEmpty()){
            return null;
        }
        
        for(EquivalenciaRevisada e:selectedEquivalencias){
            e.getEquivalencia().setVisible("si");
           
                try{
                    
                equivalenciaService.actualizarEquivalencia(e.getEquivalencia());
            }catch(RuntimeException ex){
                try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/verMovilidades.xhtml");
            }catch(IOException ex2){
                    
                    }
            }
            
        }
        beanUtilidades.creaMensaje("Las equivalencias han sido publicadas", FacesMessage.SEVERITY_INFO);
        
        
        return null;
    }
    

   
    public String publicarEquivalenciaSimple(){
        
        if(selectedEquivalenciasSimples.isEmpty()){
            return null;
        }
        
        
        
        
        for(Equivalencia e:selectedEquivalenciasSimples){
            e.setVisible("si");
           
                 try{
                     
                equivalenciaService.actualizarEquivalencia(e);
            }catch(RuntimeException ex){
                 try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/verMovilidades.xhtml");
            }catch(IOException ex2){
                    
                    }
            }
            
        }
        beanUtilidades.creaMensaje("Las equivalencias han sido publicadas", FacesMessage.SEVERITY_INFO);
        
        
        return null;
    }
    
    
    public String noPublicar(){
        
         if(selectedEquivalencias.isEmpty()){
            return null;
        }
        
        for(EquivalenciaRevisada e:selectedEquivalencias){
            e.getEquivalencia().setVisible("no");
            
                 try{
                    
                equivalenciaService.actualizarEquivalencia(e.getEquivalencia());
            }catch(RuntimeException ex){
                 try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/verMovilidades.xhtml");
            }catch(IOException ex2){
                    
                    }
            }
            
        }
        beanUtilidades.creaMensaje("Las equivalencias seleccionadas ya no son públicas", FacesMessage.SEVERITY_INFO);
        return null;
        
        
    }
    
     public String noPublicarSimple(){
        
         if(selectedEquivalenciasSimples.isEmpty()){
            return null;
        }
        
        for(Equivalencia e:selectedEquivalenciasSimples){
            e.setVisible("no");
            
            try{
               
                equivalenciaService.actualizarEquivalencia(e);
            }catch(RuntimeException ex){
                 try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/verMovilidades.xhtml");
            }catch(IOException ex2){
                    
                    }
            }
            
        }
        beanUtilidades.creaMensaje("Las equivalencias seleccionadas ya no son públicas", FacesMessage.SEVERITY_INFO);
        return null;
        
        
    }
    
    
    public String cambiarEstadoContrato(){
        
       
        if(apruebaOrechaza.equals(selectedContrato.getEstado()))
            return null;
        
        
        
        
        selectedContrato.setEstado(apruebaOrechaza);
        
            try{
               
            equivalenciaService.modificaContrato(selectedContrato);
            }catch(RuntimeException ex){
                try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/verMovilidades.xhtml");
            }catch(IOException ex2){
                    
                    }
        }
                
            
        beanUtilidades.creaMensaje("contrato modificado correctamente, se le ha enviado un mensaje al usuario", FacesMessage.SEVERITY_INFO);
        Mensaje m= new Mensaje();
        m.setDestino(selectedMovilidad.getLoginUsuario());
        m.setEliminadoDestino("no");
        m.setEliminadoOrigen("no");
        m.setFecha(Calendar.getInstance().getTime());
        m.setLeidoDestino("no");
        m.setOrigen(user);
        m.setTema("cambio de estado de contrato");
        m.setTexto("El estado de un contrato ha sido modificado");
        mensajeService.enviarMensaje(m);
        context.getSessionMap().remove("contrato");
        
        return null;
    }
    
    
}
     
    

