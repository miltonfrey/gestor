
package controllers;

import entities.Pais;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import services.UniversidadService;

import utils.BeanUtilidades;


@Named(value = "paisController")
@ViewScoped
public class PaisController implements Serializable{
    
    @Inject
    private BeanUtilidades beanUtilidades;
    @EJB
    private UniversidadService universidadService;

    
    public PaisController() {
    }
     private String paisStr;
    private Pais pais;
    private List<Pais> listaPaises;
    
     @PostConstruct
    public void init(){
        
        setListaPaises(universidadService.listaPaises());
        
    }

    public String getPaisStr() {
        return paisStr;
    }

    public void setPaisStr(String paisStr) {
        this.paisStr = paisStr;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public List<Pais> getListaPaises() {
        return listaPaises;
    }

    public void setListaPaises(List<Pais> listaPaises) {
        this.listaPaises = listaPaises;
    }

    
   
    
    
    
    
    
    public String creaPais(){
      
      
      try{
      universidadService.insertarPais(paisStr);
      }catch(javax.ejb.EJBException ex){
          
          beanUtilidades.creaMensaje("Ya existe ese país", FacesMessage.SEVERITY_ERROR);
          return null;
      }
          
          beanUtilidades.creaMensaje("se ha creado el país correctamente", FacesMessage.SEVERITY_INFO);
          paisStr="";
          setListaPaises(universidadService.listaPaises());
      return null;
      
      
  }
  
  public String eliminaPais(){
      
      try{
          
          universidadService.deletePais(pais);
          
          
      }catch(RuntimeException ex){
          
          beanUtilidades.creaMensaje("se ha producido un error eliminando el país", FacesMessage.SEVERITY_ERROR);
          return null;
      }
      
      beanUtilidades.creaMensaje("se ha eliminado correctamente el pais", FacesMessage.SEVERITY_INFO);
      setListaPaises(universidadService.listaPaises());
      return null;
  }
  
     
}  


