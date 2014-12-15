
package controllers;

import entities.Contrato;
import entities.Equivalencia;
import entities.Movilidad;
import entities.Usuario;
import exceptions.ContratoNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import services.EquivalenciaService;
import services.MovilidadService;

import utils.BeanUtilidades;


@Named(value = "verContratosController")
@ViewScoped
public class VerContratosController implements Serializable{

    @Inject
    private BeanUtilidades beanUtilidades;
    
    @EJB
    private EquivalenciaService equivalenciaService;
    @EJB 
    private MovilidadService movilidadService;
    
    public VerContratosController() {
    }
    private Usuario user;
    
   
    private HttpSession session;
    private ExternalContext context;
            
    
    
    
    
    private List<Contrato> listaContratos;
    private ArrayList<Contrato> filteredContratos;
    private ArrayList<Contrato> selectedContratos;
    private Contrato selectedContrato;
    
    private Movilidad selectedMovilidad;
  
    @PostConstruct
    public void init(){
        
       
       session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
       user=(Usuario)session.getAttribute("admin");
       context=FacesContext.getCurrentInstance().getExternalContext();
       if(context.getSessionMap().containsKey("movilidad")==true){
           selectedMovilidad=(Movilidad)context.getSessionMap().get("movilidad");
           context.getSessionMap().remove("Movilidad");
           
           selectedMovilidad=movilidadService.findMovilidad(selectedMovilidad.getCodMovilidad());
           setListaContratos(equivalenciaService.listaContratos(selectedMovilidad));
           
       }
       else{
           try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/verMovilidades.xhtml");
            }catch(IOException ex){
                    
                    }
       }
        }

   
   
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    public ArrayList<Contrato> getSelectedContratos() {
        return selectedContratos;
    }

    public void setSelectedContratos(ArrayList<Contrato> selectedContratos) {
        this.selectedContratos = selectedContratos;
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

    public String eliminarContratos(){
        
        if(selectedContratos.isEmpty()){
            return null;
        }
        ArrayList<Equivalencia> listaCopia;
        
        for(Contrato c:selectedContratos){
        
            try{
            c=equivalenciaService.findContrato(c.getIdContrato());
            }catch(ContratoNotFoundException|EJBException ex){
             setListaContratos(equivalenciaService.listaContratos(selectedMovilidad));
              beanUtilidades.creaMensaje("contrato no encontrado", FacesMessage.SEVERITY_ERROR);
             return null;
            }
            listaCopia=new ArrayList<>(c.getEquivalenciaSet());
            
            c.setEquivalenciaSet(null);
            try{
            //equivalenciaService.modificaContrato(c);
            equivalenciaService.eliminaContrato(c);
            }catch(EJBException ex){
                
            }
            
             for(Equivalencia e:listaCopia){
            
            try{
            equivalenciaService.eliminarEquivalencia(e);
            }catch(EJBException ex){
                
            }
        }
          
           }
       
        beanUtilidades.creaMensaje("contrato eliminado correctamente", FacesMessage.SEVERITY_INFO);
        setListaContratos(equivalenciaService.listaContratos(selectedMovilidad));
        selectedContratos=null;
        
        return null;
    }
    
    public String seleccionarContratoAdmin(){
        
       FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contrato", selectedContratos.get(0));
       FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad",selectedMovilidad);
       return "gestionarContrato.xhtml?faces-redirect=true";
        
    }
    
    
    public String compararContratos(){
        
        if(selectedContratos.isEmpty()!=selectedContratos.size()>2){
            beanUtilidades.creaMensaje("hay que elegir uno o dos contratos", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if(selectedContratos.size()==1){
            
            
           return seleccionarContratoAdmin();
        }else{
            Contrato contratoComparado; 
            if(selectedContratos.get(0).getFecha().compareTo(selectedContratos.get(1).getFecha())<0){
                
            contratoComparado=selectedContratos.get(0);
            selectedContrato=selectedContratos.get(1);
            
                
            }else{
               contratoComparado=selectedContratos.get(1);
            selectedContrato=selectedContratos.get(0);
            }
            
            
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contrato", selectedContrato);
       FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad",selectedMovilidad);
       FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contratoComparado",contratoComparado);
       return "compararContratos.xhtml?faces-redirect=true";
        }
        }  
    
    
    
}

    
    




