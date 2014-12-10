

package controllers;

import entities.Asignatura;
import entities.Contrato;
import entities.Equivalencia;
import entities.Mensaje;
import entities.MiembroGrupoAsignaturaA;
import entities.MiembroGrupoAsignaturaB;
import entities.Movilidad;
import entities.Usuario;
import exceptions.ContratoNotFoundException;
import exceptions.EquivalenciaException;
import exceptions.UsuarioNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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

import org.primefaces.component.datatable.DataTable;

import services.AsignaturaService;
import services.EquivalenciaService;
import services.MensajeService;
import services.MovilidadService;
import services.UsuarioService;

import utils.BeanUtilidades;


@Named(value = "misEquivalenciasController")
@ViewScoped
public class MisEquivalenciasController implements Serializable{

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
    
    
    public MisEquivalenciasController() {
    }
    private ExternalContext context;
    private HttpSession session;
    private Movilidad selectedMovilidad;
    private Contrato selectedContrato;
    Contrato c;
    
    private Usuario user;
    
    Equivalencia equivalencia;
    
    
    private List<Asignatura> listaAsignaturasFic;
    private List<Asignatura>listaAsignaturasUniversidad;
   
    
    private ArrayList<Equivalencia> listaAuxEquivalencias=new ArrayList<Equivalencia>();
    private ArrayList<Equivalencia> listaAuxEquivalenciasComparado=new ArrayList<Equivalencia>();
    
    
    private Asignatura selectedAsignatura;
    private ArrayList<Asignatura>selectedAsignaturasFic;
    private ArrayList<Asignatura> selectedAsignaturasUni;
    
    private ArrayList<Asignatura>filteredAsignaturasFic;
    private ArrayList<Asignatura>filteredAsignaturasUni;
    
    private ArrayList<Equivalencia>selectedEquivalencias;
    
    private int j=0;
    private int creditosA;
    private int creditosB;
    
    private int creditosComparadoA;
    private int creditosComparadoB;
    
    
    private boolean verInfo;
    private boolean verConfirmar=true;
    
    private boolean ultimo;
    
    
    
    
    
    @PostConstruct
    public void init(){
        context=FacesContext.getCurrentInstance().getExternalContext();
        session=(HttpSession)context.getSession(false);
        
        
       
      
        user=(Usuario)session.getAttribute("user");
        
        
        
        if(context.getSessionMap().containsKey("movilidad")){
        selectedMovilidad=(Movilidad)context.getSessionMap().get("movilidad");
        context.getSessionMap().remove("movilidad");
        setListaAsignaturasFic(asignaturaService.listarAsignaturasPorUniversidad("UDC"));
        setListaAsignaturasUniversidad(asignaturaService.listarAsignaturasPorUniversidad(selectedMovilidad.getNombreUniversidad().getNombre()));
        
         if(context.getSessionMap().containsKey("contrato")){
        selectedContrato=(Contrato)context.getSessionMap().get("contrato");
        context.getSessionMap().remove("contrato");
        try{
        c=equivalenciaService.findContrato(selectedContrato.getIdContrato());
        }catch(ContratoNotFoundException|EJBException ex){
             try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/usuario/verMisMovilidades.xhtml");
            }catch(IOException ex2){
                    
                    }
        }
        listaAuxEquivalencias.addAll(c.getEquivalenciaSet());   
        listaAuxEquivalenciasComparado.addAll(c.getEquivalenciaSet()); 
        //lo comparamos igual en la version b
              creditosA=equivalenciaService.totalCreditos(listaAuxEquivalencias)[0];
              creditosB=equivalenciaService.totalCreditos(listaAuxEquivalencias)[1];
             creditosComparadoA=creditosA;
             creditosComparadoB=creditosB;
             
        if(context.getSessionMap().containsKey("ultimo")){
           ultimo=true;
        
        context.getSessionMap().remove("ultimo");
        }
        
         }
        
        }
        
        
        else{
             try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/usuario/verMisMovilidades.xhtml");
            }catch(IOException ex){
                    
                    }
    }
         
       
    }
    

    
    
    //////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<Asignatura> getSelectedAsignaturasFic() {
        return selectedAsignaturasFic;
    }

    public Asignatura getSelectedAsignatura() {
        return selectedAsignatura;
    }

    public void setSelectedAsignatura(Asignatura selectedAsignatura) {
        this.selectedAsignatura = selectedAsignatura;
    }

   

    public void setSelectedAsignaturasFic(ArrayList<Asignatura> selectedAsignaturasFic) {
        this.selectedAsignaturasFic = selectedAsignaturasFic;
    }

    public ArrayList<Asignatura> getFilteredAsignaturasFic() {
        return filteredAsignaturasFic;
    }

    public void setFilteredAsignaturasFic(ArrayList<Asignatura> filteredAsignaturasFic) {
        this.filteredAsignaturasFic = filteredAsignaturasFic;
    }

    public ArrayList<Asignatura> getFilteredAsignaturasUni() {
        return filteredAsignaturasUni;
    }

    public void setFilteredAsignaturasUni(ArrayList<Asignatura> filteredAsignaturasUni) {
        this.filteredAsignaturasUni = filteredAsignaturasUni;
    }

    
    
    public ArrayList<Equivalencia> getSelectedEquivalencias() {
        return selectedEquivalencias;
    }

    public void setSelectedEquivalencias(ArrayList<Equivalencia> selectedEquivalencias) {
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

    

    public ArrayList<Asignatura> getSelectedAsignaturasUni() {
        return selectedAsignaturasUni;
    }

    public void setSelectedAsignaturasUni(ArrayList<Asignatura> selectedAsignaturasUni) {
        this.selectedAsignaturasUni = selectedAsignaturasUni;
    }

    public List<Asignatura> getListaAsignaturasFic() {
        return listaAsignaturasFic;
    }

    public void setListaAsignaturasFic(List<Asignatura> listaAsignaturasFic) {
        this.listaAsignaturasFic = listaAsignaturasFic;
    }

    public List<Asignatura> getListaAsignaturasUniversidad() {
        return listaAsignaturasUniversidad;
    }

    public void setListaAsignaturasUniversidad(List<Asignatura> listaAsignaturasUniversidad) {
        this.listaAsignaturasUniversidad = listaAsignaturasUniversidad;
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

    public boolean isUltimo() {
        return ultimo;
    }

    public void setUltimo(boolean ultimo) {
        this.ultimo = ultimo;
    }

  
    

    
    
   

    public boolean isVerInfo() {
        return verInfo;
    }

    public void setVerInfo(boolean verInfo) {
        this.verInfo = verInfo;
    }

    public boolean isVerConfirmar() {
        return verConfirmar;
    }

    public void setVerConfirmar(boolean verConfirmar) {
        this.verConfirmar = verConfirmar;
    }

    
     public String asignaturasTotales(){
       
          equivalencia=new Equivalencia();
         
          //MiembroGrupoAsignaturaA ma;
          MiembroGrupoAsignaturaB mb;
        
          
         
        DataTable dataTable=(DataTable)FacesContext.getCurrentInstance().getViewRoot().findComponent("formEquivalenciaFic:tablaFic");
        DataTable dataTable2=(DataTable)FacesContext.getCurrentInstance().getViewRoot().findComponent("formEquivalenciaFic:tablaUniversidad");
        
        
        
        selectedAsignaturasFic=(ArrayList < Asignatura >)dataTable.getSelection();
        selectedAsignaturasUni=(ArrayList<Asignatura>)dataTable2.getSelection();
        
         
        
        if(selectedAsignaturasFic.isEmpty()){
            
            beanUtilidades.creaMensaje("No hay asignaturas de origen", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        if(selectedAsignaturasUni.isEmpty()){
            beanUtilidades.creaMensaje("No hay asignaturas de destino", FacesMessage.SEVERITY_ERROR);
            return null;
        }
       
        
        Set<MiembroGrupoAsignaturaA> sA=new HashSet();
        for(Asignatura a:selectedAsignaturasFic){
       
       MiembroGrupoAsignaturaA ma=new MiembroGrupoAsignaturaA();
        ma.setAsignatura(a);
        ma.setIdEquivalencia(equivalencia);
           
        sA.add(ma);
        
                  
                  creditosA=creditosA+a.getCreditos();                              // con cascade save-update no hace falta salvar el miembro_grupo_asignaturas
        
        }
        Set sB=new HashSet();
        for(Asignatura a:selectedAsignaturasUni){
            
        mb=new MiembroGrupoAsignaturaB();
        mb.setAsignatura(a);
        mb.setIdEquivalencia(equivalencia);
        sB.add(mb);
        
                   creditosB=creditosB+a.getCreditos();
            
        }
        equivalencia.setMiembroGrupoAsignaturaASet(sA);
        equivalencia.setMiembroGrupoAsignaturaBSet(sB);
        equivalencia.setVisible("no");
        equivalencia.setIdequivalencia(j);
        
        
         
         
            
           
            listaAuxEquivalencias.add(equivalencia);
            j++;
            
            
            
            return null;      
         
         
         
    }
    
    public String confirmarContrato(){
        if(listaAuxEquivalencias.isEmpty()){
            
            beanUtilidades.creaMensaje("el contrato está vacío", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        Contrato c=new Contrato();
        c.setIdMovilidad(selectedMovilidad);
        c.setFecha(Calendar.getInstance().getTime());
        c.setEstado("pendiente");
        
        try{
        equivalenciaService.confirmarContrato(listaAuxEquivalencias, c);
        }catch(ContratoNotFoundException|EJBException ex){
         
             try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/usuario/verMisMovilidades.xhtml");
            }catch(IOException ex2){
                    
                    }
        }
            
        
        beanUtilidades.creaMensaje("se ha registrado el contrato correctamente", FacesMessage.SEVERITY_INFO);
        try{
             Mensaje m=new Mensaje();
     m.setDestino(usuarioService.find("admin"));
     m.setEliminadoDestino("no");
     m.setEliminadoOrigen("no");
     m.setFecha(Calendar.getInstance().getTime());
     m.setLeidoDestino("no");
     m.setOrigen(user);
     m.setTema("contrato modificado");
     m.setTexto("el usuario "+user.getLogin()+" ha creado un contrato");
     mensajeService.enviarMensaje(m);
       
        }catch(UsuarioNotFoundException ex){
            beanUtilidades.creaMensaje("Usuario inexistente", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        verConfirmar=false;
        
        return null;
        
    }
    
    public String editarContrato(){
        if(listaAuxEquivalencias.isEmpty()){
            
            beanUtilidades.creaMensaje("el contrato está vacío", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        ArrayList<Equivalencia>listaCopia=null;
         try{
        listaCopia=equivalenciaService.editarContrato(listaAuxEquivalencias, c);
        }catch(ContratoNotFoundException|EJBException ex){
         
             try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/usuario/verMisMovilidades.xhtml");
            }catch(IOException ex2){
                    
                    }
        }
        
        
        for(Equivalencia e:listaCopia){
            try{
                equivalenciaService.eliminarEquivalencia(e);
            }catch(EJBException ex){
                
            }
        }
        
        beanUtilidades.creaMensaje("se ha registrado el contrato correctamente", FacesMessage.SEVERITY_INFO);
     try{
          Mensaje m=new Mensaje();
     m.setDestino(usuarioService.find("admin"));
     m.setEliminadoDestino("no");
     m.setEliminadoOrigen("no");
     m.setFecha(Calendar.getInstance().getTime());
     m.setLeidoDestino("no");
     m.setOrigen(user);
     m.setTema("contrato modificado");
     m.setTexto("el usuario "+user.getLogin()+" ha modificado un contrato");
     mensajeService.enviarMensaje(m);
    
     }catch(Exception ex){
         
     }
        
        verConfirmar=false;
        return null;
    
    }
    
    
 
    
     public String  crearContratoDesdeAceptado(){
       if(listaAuxEquivalencias.isEmpty()){
            
            beanUtilidades.creaMensaje("el contrato está vacío", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        Contrato cNuevo=new Contrato();
        cNuevo.setFecha(Calendar.getInstance().getTime());
        cNuevo.setIdMovilidad(selectedMovilidad);
        cNuevo.setEstado("pendiente");
        
         try{
         equivalenciaService.crearContratoDesdeAceptado(listaAuxEquivalencias, c, cNuevo);
        }catch(ContratoNotFoundException|EquivalenciaException|EJBException ex){
         
             try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/usuario/verMisMovilidades.xhtml");
            }catch(IOException ex2){
                    
                    }
        }
        
        
       
        
     beanUtilidades.creaMensaje("se ha registrado el contrato correctamente", FacesMessage.SEVERITY_INFO);
     try{
     Mensaje m=new Mensaje();
     m.setDestino(usuarioService.find("admin"));
     m.setEliminadoDestino("no");
     m.setEliminadoOrigen("no");
     m.setFecha(Calendar.getInstance().getTime());
     m.setLeidoDestino("no");
     m.setOrigen(user);
     m.setTema("contrato creado");
     m.setTexto("el usuario "+user.getLogin()+" ha creado un contrato");
     mensajeService.enviarMensaje(m);
     }catch(UsuarioNotFoundException ex){
         
     }
        verConfirmar=false;
        return null;
    
  }
    
    
    public String eliminaEquivalenciasLista(){
        if(selectedEquivalencias.isEmpty()==false){
            
           for(int i=0;i<selectedEquivalencias.size();i++){
          
               Iterator it=selectedEquivalencias.get(i).getMiembroGrupoAsignaturaASet().iterator();
               while(it.hasNext()){
                   MiembroGrupoAsignaturaA mA=(MiembroGrupoAsignaturaA)it.next();
                   creditosA=creditosA-mA.getAsignatura().getCreditos();
               }
               
               it=selectedEquivalencias.get(i).getMiembroGrupoAsignaturaBSet().iterator();
               while(it.hasNext()){
                   MiembroGrupoAsignaturaB mB=(MiembroGrupoAsignaturaB)it.next();
                   creditosB=creditosB-mB.getAsignatura().getCreditos();
               }
               
          listaAuxEquivalencias.remove(selectedEquivalencias.get(i));
          
          
          
        }
        }
        
        return null;
        
    }
    
    public void detallesAsign(){
         
        verInfo=true;
        
    }
    
    public void cerrarDetallesAsign(){
        
        verInfo=false;
    }
   
    
    
    
    public String nuevoAceptado(){
        
       
        
         FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad",selectedMovilidad);
         FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contrato", selectedContrato);
         
         return ("crearContratoDesdeAceptado.xhtml?faces-redirect=true");
         
     
        
    }
     
     
    
}


