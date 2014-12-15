
package controllers;

import entities.Contrato;
import entities.Equivalencia;
import entities.Movilidad;
import entities.Usuario;
import exceptions.ContratoNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
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

import services.EquivalenciaService;


import utils.BeanUtilidades;


@Named(value = "verMisContratosController")
@ViewScoped
public class VerMisContratosController implements Serializable{

   @Inject 
   private BeanUtilidades beanUtilidades;
   
   @EJB
   private EquivalenciaService equivalenciaService;
   
    
    public VerMisContratosController() {
    }
     private Usuario user;
    
    private HttpSession session;
    
    
    private boolean nuevo;
    private boolean visibleContratos;
    private boolean verAceptado;
    
    
    
    private List<Contrato> listaContratos;
    private ArrayList<Contrato> filteredContratos;
    private Contrato selectedContrato;
    
    private Movilidad selectedMovilidad;
  
    
    
    
    @PostConstruct
    public void init(){
        
       
       session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        user=(Usuario)session.getAttribute("user");
         
        }

    

   
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
   

    public boolean isNuevo() {
        return nuevo;
    }

   
    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    public boolean isVisibleContratos() {
        return visibleContratos;
    }

    public void setVisibleContratos(boolean visibleContratos) {
        this.visibleContratos = visibleContratos;
    }

    public boolean isVerAceptado() {
        return verAceptado;
    }

    public void setVerAceptado(boolean verAceptado) {
        this.verAceptado = verAceptado;
    }

    public List<Contrato> getListaContratos() {
        return listaContratos;
    }

    public void setListaContratos(List<Contrato> listaContratos) {
        this.listaContratos = listaContratos;
    }
    
    public ArrayList<Contrato> getFilteredContratos() {
        return filteredContratos;
    }

    public void setFilteredContratos(ArrayList<Contrato> filteredContratos) {
        this.filteredContratos = filteredContratos;
    }

    public Contrato getSelectedContrato() {
        return selectedContrato;
    }

    public void setSelectedContrato(Contrato selectedContrato) {
        this.selectedContrato = selectedContrato;
    }

    
    public Movilidad getSelectedMovilidad() {
        return selectedMovilidad;
    }

    public void setSelectedMovilidad(Movilidad selectedMovilidad) {
        this.selectedMovilidad = selectedMovilidad;
    }

    
    
    
    public String eliminarContrato(){
        
        ArrayList<Equivalencia> listaCopia;
        
           try{
              
           selectedContrato=equivalenciaService.findContrato(selectedContrato.getIdContrato());
            listaCopia=new ArrayList<Equivalencia>(selectedContrato.getEquivalenciaSet());
             selectedContrato.setEquivalenciaSet(null);
           //equivalenciaService.modificaContrato(selectedContrato);
           equivalenciaService.eliminaContrato(selectedContrato);
           }catch(ContratoNotFoundException ex){
               beanUtilidades.creaMensaje("contrato no encontrado", FacesMessage.SEVERITY_ERROR);
               cerrarContratos();
               return null;
           }catch(EJBException ex2){
               
               beanUtilidades.creaMensaje("contrato no encontrado", FacesMessage.SEVERITY_ERROR);
               cerrarContratos();
               return null;
           } 
            for(Equivalencia e:listaCopia){
              
                try{
                equivalenciaService.eliminarEquivalencia(e);
                }catch(EJBException ex){
                    
                }
            }
            
          
        beanUtilidades.creaMensaje("contrato eliminado correctamente", FacesMessage.SEVERITY_INFO);
        verContratos();
        
        selectedContrato=null;
        
        return null;
    }
    
    
    public void verContratos(){
        verAceptado=true;
        visibleContratos=true;
        
        
        setListaContratos(equivalenciaService.listaContratos(selectedMovilidad));
        
        if(listaContratos.isEmpty()){
        nuevo=true;
    }else
            for (Contrato c: listaContratos){
                
        if(c.getEstado().equalsIgnoreCase("pendiente")||c.getEstado().equalsIgnoreCase("rechazado")||c.getEstado().equalsIgnoreCase("aceptado")){
            nuevo=false;
            break;
        }
    }
        
      /*  for(Contrato c:listaContratos){
            
            if(c.getEstado().equalsIgnoreCase("pendiente")||c.getEstado().equalsIgnoreCase("rechazado")){
            verAceptado=false;
               
                break;
            }
            
        }
        */
        
    }
    
     public void cerrarContratos(){
        
        visibleContratos=false;
    }
    
     
     public String revisarContrato(){
         
          FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad", selectedMovilidad);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contrato", selectedContrato);
       
        
        
        
        for(Contrato c:listaContratos){
            
            if(selectedContrato.getFecha().compareTo(c.getFecha())==-1){
                
             return ("verContrato.xhtml?faces-redirect=true");
            }
            
            
        }
        
       FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ultimo", "ultimo");
          return ("verContrato.xhtml?faces-redirect=true");
     }
     
    
    public String crearContrato(){
        System.out.println(selectedMovilidad.getNombreUniversidad().getNombre());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad", selectedMovilidad);
        return ("elaborarContrato.xhtml?faces-redirect=true");
        
        
    }
    
    public String editarContrato(){
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad", selectedMovilidad);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contrato", selectedContrato);
       
        
        return ("elaborarContratoEditado.xhtml?faces-redirect=true");
        
    }
    
    
    
    
    
    
    
    
    
   
    
    
    
  
    
    
}


