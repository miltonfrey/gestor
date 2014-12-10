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
@Named(value = "universidadDaoImpl")
@RequestScoped
public class UniversidadDaoImpl implements UniversidadDao{

     @PersistenceContext(unitName = "gestorPU")
    private EntityManager entityManager;
    
      public UniversidadDaoImpl() {
    }
    
    
   
    
    @Override
    public List<Pais> listaPaises(){
        
        return entityManager.createQuery("select p from Pais p order by p.nombre asc").getResultList();
        
        
    }
    @Override
    public List<Universidad> listaUniversidades(){
        
        return entityManager.createQuery("select u from Universidad u order by u.nombre asc").getResultList();
    }
    
    
    @Override
    public Pais findPais(String pais){
        try{
        return (Pais) entityManager.createQuery("select p from Pais p where p.nombre=:pais order by p.nombre asc").setParameter("pais", pais).getSingleResult();
        }catch(NoResultException ex){
            return null;
        }
    }
    
    
   @Override
    public void insertarPais(Pais p){
        
        entityManager.persist(p);
        
    }
        
    @Override
    public void deletePais(Pais p){
        entityManager.remove(entityManager.merge(p));
    }
    
    
    @Override
    public void delete(Universidad u){
         u=entityManager.find(Universidad.class, u.getNombre());// esto tiene que ver con que pais sea fetch eager
        entityManager.remove(entityManager.merge(u));
        
        
    }
    @Override
    public List<Universidad> listarUniversidades(){
        
        return(entityManager.createQuery("select u from Universidad u").getResultList());
        
    }
    
    @Override
    public void insertarCarrera(Universidad u){
        
       entityManager.persist(u);
        
    }
    
    @Override
    public void actualizar(Universidad u){
        
        Universidad aux=entityManager.getReference(Universidad.class, u.getNombre());
        //u=findUniversidad(u.getNombre());
        entityManager.merge(u);
    }
    
    
    @Override
    public List<Universidad> listarPorUniversidad(String universidad){
    
    Query q=entityManager.createQuery("select u from Universidad u where u.nombre=:universidad");
    q.setParameter("universidad",universidad);
    return(q.getResultList());
}
    
   /* @Override
    public List<String> listarPorUniversidadStr(String universidad){
        
        Query q=entityManager.createQuery("select distinct c.id.nombre from Universidad c where c.id.universidad=:universidad");
    q.setParameter("universidad",universidad);
    return(q.getResultList());
    }
    */
    @Override
     public List<Universidad> listarPorPais(String pais){
    
    Query q=entityManager.createQuery("select u from Universidad u where u.pais.nombre=:pais");
    q.setParameter("pais",pais);
    return(q.getResultList());
}
    
    
     @Override
     public void crearCursoAcademico(Cursoacademico c){
         
         entityManager.persist(c);
         
     }
     
     @Override
     public void eliminarCursoAcademico(Cursoacademico c){
         
         entityManager.remove(entityManager.merge(c));
         
     }
     @Override
    public List<Cursoacademico> listaCursosAcademicos(){
        
        return entityManager.createQuery("select c from Cursoacademico c order by c.cursoAcademico").getResultList();
        
    }
    
    @Override
    public Universidad findUniversidad(String universidad){
        
        try{
        Query q=entityManager.createQuery("select u from Universidad u where u.nombre=:universidad");
        q.setParameter("universidad", universidad);
        return (Universidad)q.getSingleResult();
        }catch(NoResultException ex){
            
            return null;
        }
        
    }
}
    
    
    

