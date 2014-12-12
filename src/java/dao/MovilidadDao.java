
package dao;

import entities.Movilidad;
import entities.Usuario;
import java.util.List;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;



@Named(value = "movilidadDao")
@RequestScoped
public class MovilidadDao extends GenericDao<Movilidad>{

    
   @PersistenceContext(unitName = "gestorPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
    public MovilidadDao() {
        
        setClase(Movilidad.class);
    }
    
    
   
   
    
    
   
    
    public List<Movilidad> listarPorEstado(String estado){
        
        Query q=getEntityManager().createQuery("select m from Movilidad m where m.estado=:estado");
        q.setParameter("estado", estado);
        return(q.getResultList());
    }
    
    
    public List<Movilidad> listarMisMovilidades(String user){
        
        Query q= getEntityManager().createQuery("select m from Movilidad m where m.loginUsuario.login=:user");
        q.setParameter("user", user);
        return q.getResultList();
        
        
    }
    
    public List<Movilidad> listarMisMovilidadesPorEstado(String user,String estado){
        
        Query q=getEntityManager().createQuery("select m from Movilidad m where m.loginUsuario.login=:user and m.estado=:estado");
        q.setParameter("user", user);
        q.setParameter("estado", estado);
        return q.getResultList();
        
    }
    
    
    public Movilidad findMovilidad(Integer id){
        
        return (Movilidad)getEntityManager().createQuery("select m from Movilidad m where m.codMovilidad=:id").setParameter("id", id).getSingleResult();
    }
    
    
    
}

    

