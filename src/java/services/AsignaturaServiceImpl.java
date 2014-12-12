/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.AsignaturaDao;
import entities.Asignatura;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;



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
        
        
        
        asignaturaDao.create(a);
    }
    
    @Override
    public List<Asignatura> listarAsignaturas(){
        return asignaturaDao.findAll();
    }
    
    
    @Override
    
    public List<Asignatura> listarAsignaturasPorUniversidad(String codUniversidad){
        
        return asignaturaDao.listarAsignaturasPorUniversidad(codUniversidad);
                
                
    }
    @Override
    public void eliminaAsignatura(Asignatura a){
        
        
        asignaturaDao.remove(a);
        
    }
    
    
    @Override
    public void actualizarAsignatura(Asignatura a){
        
        
        asignaturaDao.edit(a, a.getAsignaturaPK());
       
    }
    
    @Override
    
    public List<Asignatura> listarPorCriterio(){
        
        return asignaturaDao.listarPorCriterio();
    }
    
    
}
