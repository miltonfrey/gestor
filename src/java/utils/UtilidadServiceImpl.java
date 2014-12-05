/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;


import entities.Cursoacademico;
import entities.Estado;
import entities.EstadoMovilidad;
import java.util.List;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



@Stateless
public class UtilidadServiceImpl implements UtilidadService {

    @PersistenceContext(unitName = "pruebaHibernate2PU")
    private EntityManager entityManager;
    
    @Override
    public void crearEstado(Estado e){
        entityManager.persist(e);
    }
    @Override
    public List<Estado> listaEstados(){
        return entityManager.createQuery("select e from Estado e").getResultList();
    }
    @Override
    public void eliminaEstado(Estado e){
        e=findEstado(e);
       entityManager.remove(entityManager.merge(e));
                
    }
    
    @Override
    public void crearEstadoMovilidad(EstadoMovilidad e){
        
        entityManager.persist(e);
        
    }
    
    @Override
    public List<EstadoMovilidad> listaEstadosMovilidad(){
         return entityManager.createQuery("select e from EstadoMovilidad e").getResultList();
        
        
    }
    
    
    @Override
    public void eliminaEstadoMovilidad(EstadoMovilidad e){
        entityManager.remove(entityManager.merge(e));
    }
    
    @Override
    public void crearCursoAcademico(Cursoacademico c){
        
      entityManager.persist(c);
        
    }
    
    @Override
    public List<Cursoacademico> listaCursoAcademico(){
        return entityManager.createQuery("select c from Cursoacademico c").getResultList();
                
    }
    @Override
    public void eliminaCursoAcademico(Cursoacademico c){
        entityManager.remove(entityManager.merge(c));
    }
    
    public Estado findEstado(Estado e){
        
       return (Estado)entityManager.find(Estado.class, e.getEstado());
        
    }
    
    
}

