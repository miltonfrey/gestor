
package controllers;

import entities.Asignatura;
import entities.AsignaturaPK;
import entities.Pais;
import entities.Universidad;
import exceptions.UniversidadException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import services.AsignaturaService;
import services.UniversidadService;

import utils.BeanUtilidades;


@Named(value = "crearAsignaturaController")
@ViewScoped
public class CrearAsignaturaController implements Serializable{
    @EJB
    private AsignaturaService asignaturaService;
    @EJB
    private UniversidadService universidadService;
    
    @Inject
    private BeanUtilidades beanUtilidades;
    
    public CrearAsignaturaController()  {
    }
    
    
     private String paisStr;
    private String universidadStr;
    
   
    
    
   
    
    //asignatura
    
    private Integer codAsignatura;
    private String nombreAsignatura;
    private Integer creditosAsignatura;
    private String periodoAsignatura;
    private String infoAsignatura;
    private String webAsignatura;
    private String facultadAsignatura;
    private String titulacionAsignatura;
    
    
    private List<Pais> listaPaises;
    private List<Universidad> listaUniversidades;
    private List<Asignatura> listaAsignaturas;
    
    private boolean checkPaisStr;
    private boolean checkUniversidadStr;
    private boolean checkDetalles;
    private boolean checkTabla;
    
    private Pais selectedPais;
    private Universidad selectedUniversidad;
    private ArrayList<Asignatura> selectedAsignaturas;
    private Asignatura SelectedAsignatura;
    private ArrayList<Asignatura>filteredAsignaturas;
    
    
    public String getPaisStr() {
        return paisStr;
    }

    public void setPaisStr(String paisStr) {
        this.paisStr = paisStr;
    }

    

   

    public String getUniversidadStr() {
        return universidadStr;
    }

    public void setUniversidadStr(String universidadStr) {
        this.universidadStr = universidadStr;
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

    public List<Asignatura> getListaAsignaturas() {
        return listaAsignaturas;
    }

    public void setListaAsignaturas(List<Asignatura> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }

    
    
    
    public Pais getSelectedPais() {
        return selectedPais;
    }

    public void setSelectedPais(Pais selectedPais) {
        this.selectedPais = selectedPais;
    }

   
    public void onChangePaisUni(){
    
    
        setListaUniversidades(universidadService.listarPorPais(paisStr));
}
   
   
    
     public Universidad getSelectedUniversidad() {
        return selectedUniversidad;
    }

    public void setSelectedUniversidad(Universidad selectedUniversidad) {
        this.selectedUniversidad = selectedUniversidad;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////
    public Integer getCodAsignatura() {
        return codAsignatura;
    }

    public void setCodAsignatura(Integer codAsignatura) {
        this.codAsignatura = codAsignatura;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public Integer getCreditosAsignatura() {
        return creditosAsignatura;
    }

    public void setCreditosAsignatura(Integer creditosAsignatura) {
        this.creditosAsignatura = creditosAsignatura;
    }

    public String getPeriodoAsignatura() {
        return periodoAsignatura;
    }

    public void setPeriodoAsignatura(String periodoAsignatura) {
        this.periodoAsignatura = periodoAsignatura;
    }

    public String getInfoAsignatura() {
        return infoAsignatura;
    }

    public void setInfoAsignatura(String infoAsignatura) {
        this.infoAsignatura = infoAsignatura;
    }

    public String getWebAsignatura() {
        return webAsignatura;
    }

    public void setWebAsignatura(String webAsignatura) {
        this.webAsignatura = webAsignatura;
    }

    public String getFacultadAsignatura() {
        return facultadAsignatura;
    }

    public void setFacultadAsignatura(String facultadAsignatura) {
        this.facultadAsignatura = facultadAsignatura;
    }

    public String getTitulacionAsignatura() {
        return titulacionAsignatura;
    }

    public void setTitulacionAsignatura(String titulacionAsignatura) {
        this.titulacionAsignatura = titulacionAsignatura;
    }

    public ArrayList<Asignatura> getSelectedAsignaturas() {
        return selectedAsignaturas;
    }

    public void setSelectedAsignaturas(ArrayList<Asignatura> selectedAsignaturas) {
        this.selectedAsignaturas = selectedAsignaturas;
    }

    public Asignatura getSelectedAsignatura() {
        return SelectedAsignatura;
    }

    public void setSelectedAsignatura(Asignatura SelectedAsignatura) {
        this.SelectedAsignatura = SelectedAsignatura;
    }

    public ArrayList<Asignatura> getFilteredAsignaturas() {
        return filteredAsignaturas;
    }

    public void setFilteredAsignaturas(ArrayList<Asignatura> filteredAsignaturas) {
        this.filteredAsignaturas = filteredAsignaturas;
    }
    
   public void onChangePais(){
       
       
       checkPaisStr=true;
       setListaUniversidades(universidadService.listarPorPais(paisStr));
       universidadStr="";
       checkDetalles=false;
       checkUniversidadStr=false;
       checkTabla=false;
       
       
   }

    public void onChangeUniversidad(){
        
        checkUniversidadStr=true;
        checkTabla=true;
        setListaAsignaturas( asignaturaService.listarAsignaturasPorUniversidad(universidadStr));
        
       checkDetalles=false;
       
        
    }
    
    public String creaAsignatura(){
        Universidad uni;
        try{
        uni=universidadService.findUniversidad(universidadStr);
        }catch(UniversidadException ex){
            beanUtilidades.creaMensaje("no existe la universidad", FacesMessage.SEVERITY_ERROR);
            return "crearAsignatura.xhtml";
        }
            
            
        AsignaturaPK id=new AsignaturaPK(codAsignatura,universidadStr);
               
        Asignatura a=new Asignatura();
        a.setAsignaturaPK(id);
        a.setNombreAsignatura(nombreAsignatura);
        a.setCreditos(creditosAsignatura.shortValue());
        a.setFacultad(facultadAsignatura);
        a.setInfoAsigantura(infoAsignatura);
        a.setTitulacion(titulacionAsignatura);
        a.setPeriodo(periodoAsignatura);
        a.setWebAsignatura(webAsignatura);
        a.setUniversidad(uni);
        
        try{
            
            asignaturaService.crearAsignatura(a);
        }catch(RuntimeException ex){
            
            beanUtilidades.creaMensaje("La asignatura ya existe", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        beanUtilidades.creaMensaje("asignatura creada correctamente", FacesMessage.SEVERITY_INFO);
        nombreAsignatura="";
        codAsignatura=null;
        creditosAsignatura=null;
        periodoAsignatura="";
        titulacionAsignatura="";
        facultadAsignatura="";
        infoAsignatura="";
        webAsignatura="";
        setListaAsignaturas( asignaturaService.listarAsignaturasPorUniversidad(universidadStr));
        
        return null;
    }
    
    public void verDetalles(){
        
        webAsignatura=SelectedAsignatura.getWebAsignatura();
        infoAsignatura=SelectedAsignatura.getInfoAsigantura();
        facultadAsignatura=SelectedAsignatura.getFacultad();
        titulacionAsignatura=SelectedAsignatura.getTitulacion();
        checkDetalles=true;
        //checkUniversidadStr=false;
        
    }
    public String editar(){
        try{
            asignaturaService.actualizarAsignatura(SelectedAsignatura);
           setListaAsignaturas( asignaturaService.listarAsignaturasPorUniversidad(universidadStr));
        }catch(UniversidadException ex){
           /* try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/crearAsignatura.xhtml");
        }catch(IOException ex2){
            
        }*/beanUtilidades.creaMensaje("Se ha producido un error", FacesMessage.SEVERITY_ERROR);
        checkDetalles=false;
        checkUniversidadStr=false;
        checkTabla=false;
        checkPaisStr=false;
        paisStr="";
        universidadStr="";
        return null;
        }
            checkDetalles=false;
            beanUtilidades.creaMensaje("Edición correcta", FacesMessage.SEVERITY_INFO);
           return null;
        
    }
    
    public void cerrar(){
        
        checkDetalles=false;
        webAsignatura="";
        infoAsignatura="";
        facultadAsignatura="";
        titulacionAsignatura="";
    }
    
    
    public String eliminaAsignaturasLista(){
        
        if(selectedAsignaturas.isEmpty()==true){
            return null;
        }
            
        for (Asignatura a:selectedAsignaturas){
            try{
                asignaturaService.eliminaAsignatura(a);
            }catch(RuntimeException ex){
                 beanUtilidades.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
                 checkDetalles=false;
                 checkTabla=false;
                 checkUniversidadStr=false;
                 checkPaisStr=false;
                 paisStr="";
                 return null;
                 
            }
        }
        
        beanUtilidades.creaMensaje("se han eliminado correctamente las asignaturas",FacesMessage.SEVERITY_INFO);
        setListaAsignaturas( asignaturaService.listarAsignaturasPorUniversidad(universidadStr));
        //checkUniversidadStr=false;
        checkDetalles=false;
        return null;
    }
        
     
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public boolean isCheckPaisStr() {
        return checkPaisStr;
    }

    public void setCheckPais(boolean checkPaisStr) {
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

    public boolean isCheckTabla() {
        return checkTabla;
    }

    public void setCheckTabla(boolean checkTabla) {
        this.checkTabla = checkTabla;
    }

}

    
    
    

