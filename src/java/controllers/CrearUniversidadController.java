package controllers;

import entities.Pais;
import entities.Universidad;
import exceptions.PaisException;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import services.UniversidadService;

import utils.BeanUtilidades;


@Named(value = "crearUniversidadController")
@ViewScoped
public class CrearUniversidadController implements Serializable{
    @EJB
    private UniversidadService universidadService;
    @Inject
    private BeanUtilidades beanUtilidades;
    
    public CrearUniversidadController() {
    }
    
    private String paisStr;
   
    
   
    
    //universidad
    private String codUniversidad;
    private String nombre;
    private String universidadStr;
    private String info;
    private String web;
    private String paisStrEdit;
    
    private List<Pais> listaPaises;
    private List<Universidad> listaUniversidades;
    private ArrayList<Universidad> selectedUniversidades;
    
    private boolean checkPaisStr;
    private boolean checkUniversidadStr;
    private boolean checkDetalles;
    
    
    private Pais selectedPais;
    private Universidad selectedUniversidad;
   

    
    // GETTER Y SETTER
    
    public String getPaisStr() {
        return paisStr;
    }

    public void setPaisStr(String paisStr) {
        this.paisStr = paisStr;
    }

    public String getCodUniversidad() {
        return codUniversidad;
    }

    public void setCodUniversidad(String codUniversidad) {
        this.codUniversidad = codUniversidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUniversidadStr() {
        return universidadStr;
    }

    public void setUniversidadStr(String universidadStr) {
        this.universidadStr = universidadStr;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getPaisStrEdit() {
        return paisStrEdit;
    }

    public void setPaisStrEdit(String paisStrEdit) {
        this.paisStrEdit = paisStrEdit;
    }

    public List<Pais> getListaPaises() {
        return listaPaises;
    }

    public void setListaPaises(List<Pais> listaPaises) {
        this.listaPaises = listaPaises;
    }
    
    

    

    public List<Universidad> getListaUniversidades() {
        return listaUniversidades;
    }

    public void setListaUniversidades(List<Universidad> listaUniversidades) {
        this.listaUniversidades = listaUniversidades;
    }

   

    public ArrayList<Universidad> getSelectedUniversidades() {
        return selectedUniversidades;
    }

    public void setSelectedUniversidades(ArrayList<Universidad> selectedUniversidades) {
        this.selectedUniversidades = selectedUniversidades;
    }

   //BOOLEAN 

  

    public boolean isCheckPaisStr() {
        return checkPaisStr;
    }

    public void setCheckPaisStr(boolean checkPaisStr) {
        this.checkPaisStr = checkPaisStr;
    }

    public boolean isCheckUniversidadStr() {
        return checkUniversidadStr;
    }

    public void setCheckUniversidadStr(boolean checkUniversidadStr) {
        this.checkUniversidadStr = checkUniversidadStr;
    }

    public boolean isCheckDetalles() {
        return checkDetalles;
    }

    public void setCheckDetalles(boolean checkDetalles) {
        this.checkDetalles = checkDetalles;
    }


    public Pais getSelectedPais() {
        return selectedPais;
    }

    public void setSelectedPais(Pais selectedPais) {
        this.selectedPais = selectedPais;
    }

    public Universidad getSelectedUniversidad() {
        return selectedUniversidad;
    }

    public void setSelectedUniversidad(Universidad selectedUniversidad) {
        this.selectedUniversidad = selectedUniversidad;
    }

   
    
    public void onChangePais(){
        
        
        setListaUniversidades(universidadService.listarPorPais(paisStr));
        
        
    }
    
    
   public String creaUniversidad(){
       
       Pais p;
       try{
        p=universidadService.findPais(paisStr);
       }catch(PaisException ex){
          beanUtilidades.creaMensaje("se ha producido un error,no existe ese país", FacesMessage.SEVERITY_ERROR);
           return "crearUniversidad.xhtml";
       }
        Universidad u=new Universidad();
        u.setInfo(info);
        u.setNombre(nombre);
        u.setPais(p);
        u.setWeb(web);
        u.setCodUniversidad(codUniversidad);
        try{
            
            universidadService.insertarUniversidad(u);
            
        }catch(RuntimeException ex){
            beanUtilidades.creaMensaje("ya existe esa universidad", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        catch(Exception ex){
            beanUtilidades.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_INFO);
            return "crearUniversidad.xhtml";
        }
        
        beanUtilidades.creaMensaje("universidad creada", FacesMessage.SEVERITY_INFO);
        nombre="";
        web="";
        info="";
        //paisStr="";
        codUniversidad="";
        listaUniversidades=universidadService.listarPorPais(paisStr);
        return null;
        
    }
    
    
    public void verDetalles(){
        
        codUniversidad=selectedUniversidad.getCodUniversidad();
        paisStr=selectedUniversidad.getPais().getNombre();
        info=selectedUniversidad.getInfo();
        web=selectedUniversidad.getWeb();
        checkDetalles=true;
    }
    
    public String eliminaUniversidadLista(){
        
       
        
        if(selectedUniversidades.isEmpty()==true){
            return null;
        }   
            
            for(Universidad u:selectedUniversidades){
               
                try{
                    universidadService.delete(u);
                }catch(RuntimeException ex){
                   
                   beanUtilidades.creaMensaje("Error eliminando", FacesMessage.SEVERITY_ERROR); 
                   paisStr="";
                    return "crearUniversidad.xhtml";
                }    
            }
            beanUtilidades.creaMensaje("se han eliminado las universidades correctamente", FacesMessage.SEVERITY_INFO);
            listaUniversidades=universidadService.listarPorPais(paisStr);
            checkDetalles=false;
            return null;
        
        
        
    }
    
    public String editar(){
        
        checkDetalles=false;
        
           
                    
        try{
        universidadService.actualizar(selectedUniversidad);
        listaUniversidades=universidadService.listarPorPais(paisStr);
        }catch(RuntimeException ex){
           
                
                try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/crearUniversidad.xhtml");
            }catch(IOException ex2){
                
            }
        }
        beanUtilidades.creaMensaje("edición correcta", FacesMessage.SEVERITY_INFO);
        return null;
    }
    
    public void cerrar(){
        
        checkDetalles=false;
        codUniversidad="";
        nombre="";
        web="";
        info="";
        
        
    }
    
    
    
}
    
    

