

package controllers;

import entities.Usuario;
import exceptions.UsuarioNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import services.UsuarioService;

import utils.BeanUtilidades;


@Named(value = "eliminarUsuarioController")
@ViewScoped
public class EliminarUsuarioController implements Serializable{

    @Inject
    private BeanUtilidades beanUtilidades;
   
    @EJB
    private UsuarioService usuarioService;
    
    public EliminarUsuarioController() {
    }
    
    private ArrayList<Usuario> selectedUsuarios;
    private List<Usuario> listaUsuarios;
    private ArrayList<Usuario> filteredUsuarios;
    
    
   
    @PostConstruct
    public void init(){
        listaUsuarios=new ArrayList<Usuario>();
        listaUsuarios.addAll(usuarioService.listar());
        
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    

    public ArrayList<Usuario> getSelectedUsuarios() {
        return selectedUsuarios;
    }

    public void setSelectedUsuarios(ArrayList<Usuario> selectedUsuarios) {
        this.selectedUsuarios = selectedUsuarios;
    }


    public ArrayList<Usuario> getFilteredUsuarios() {
        return filteredUsuarios;
    }

    public void setFilteredUsuarios(ArrayList<Usuario> filteredUsuarios) {
        this.filteredUsuarios = filteredUsuarios;
    }
    
    
    
    public String eliminaUsuariosLista(){
        
        if(selectedUsuarios.isEmpty()){
            return null;
        }
        
        for(Usuario u:selectedUsuarios){
            try{
                
                usuarioService.delete(u);
               
            }catch(UsuarioNotFoundException ex){
                beanUtilidades.creaMensaje("usuario inexistente", FacesMessage.SEVERITY_ERROR);
                return null;
            }
        
        }
        
         beanUtilidades.creaMensaje("usuarios borrado ", FacesMessage.SEVERITY_INFO);
        setListaUsuarios(usuarioService.listar());
        return null;
      
        
        
          
    }
    
    
    
    
    
    
    
}
    

