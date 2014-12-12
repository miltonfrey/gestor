/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Cursoacademico;
import entities.Pais;
import entities.Universidad;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 *
 * @author cba
 */
@Named(value = "universidadDao")
@RequestScoped
public class UniversidadDao extends GenericDao<Universidad>{

    @PersistenceContext(unitName = "gestorPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
    public UniversidadDao() {
        
        setClase(Universidad.class);
    }
    
    public List<Pais> listaPaises(){
        
        return getEntityManager().createQuery("select p from Pais p order by p.nombre asc").getResultList();
        
    }
    
    public Pais findPais(String pais){
        try{
        return (Pais) getEntityManager().createQuery("select p from Pais p where p.nombre=:pais order by p.nombre asc").setParameter("pais", pais).getSingleResult();
        }catch(NoResultException ex){
            return null;
        }
    }
    
    
   
    public void insertarPais(Pais p){
        
        getEntityManager().persist(p);
        
    }
        
   
    public void deletePais(Pais p){
       getEntityManager().remove(getEntityManager().merge(p));
    }
    
    
    
    public void delete(Universidad u){
         u=getEntityManager().find(Universidad.class, u.getNombre());// esto tiene que ver con que pais sea fetch eager
        getEntityManager().remove(getEntityManager().merge(u));
        
        
    }
    
    
     public List<Universidad> listarPorPais(String pais){
    
    Query q=getEntityManager().createQuery("select u from Universidad u where u.pais.nombre=:pais");
    q.setParameter("pais",pais);
    return(q.getResultList());
}
    
    
     
     public void crearCursoAcademico(Cursoacademico c){
         
         getEntityManager().persist(c);
         
     }
     
     
     public void eliminarCursoAcademico(Cursoacademico c){
         
         getEntityManager().remove(getEntityManager().merge(c));
         
     }
     
    public List<Cursoacademico> listaCursosAcademicos(){
        
        return getEntityManager().createQuery("select c from Cursoacademico c order by c.cursoAcademico").getResultList();
        
    }
    
    
    public Universidad findUniversidad(String universidad){
        
        try{
        Query q=getEntityManager().createQuery("select u from Universidad u where u.nombre=:universidad");
        q.setParameter("universidad", universidad);
        return (Universidad)q.getSingleResult();
        }catch(NoResultException ex){
            
            return null;
        }
        
    }
}
    
    
    

