/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.AsignaturaDao;
import entities.Asignatura;
import exceptions.UniversidadException;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;


/**
 *
 * @author cba
 */
@Stateless
public class AsignaturaServiceImpl implements AsignaturaService{

    @Inject
    private AsignaturaDao asignaturaDao;
    
    @Override
    public void crearAsignatura(Asignatura a){
        
        
        
        asignaturaDao.crearAsignatura(a);
    }
    
    @Override
    public List<Asignatura> listarAsignaturas(){
        return asignaturaDao.listarAsignaturas();
    }
    
    
    @Override
    
    public List<Asignatura> listarAsignaturasPorUniversidad(String codUniversidad){
        
        return asignaturaDao.listarAsignaturasPorUniversidad(codUniversidad);
                
                
    }
    @Override
    public void eliminaAsignatura(Asignatura a){
        
        
        asignaturaDao.eliminaAsignatura(a);
        
    }
    
    
    @Override
    public void actualizarAsignatura(Asignatura a) throws UniversidadException{
        
        
        asignaturaDao.actualizarAsignatura(a);
       
    }
    
    @Override
    
    public List<Asignatura> listarPorCriterio(){
        
        return asignaturaDao.listarPorCriterio();
    }
    
    
}
