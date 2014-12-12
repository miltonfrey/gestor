
package dao;

import entities.Asignatura;
import entities.AsignaturaPK;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;




@Named(value = "asignaturaDao")
@RequestScoped
public class AsignaturaDao extends GenericDao<Asignatura>{
    
    
    @PersistenceContext(unitName = "gestorPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
    public AsignaturaDao() {
        
        setClase(Asignatura.class);
    }
    
    
    
    public List<Asignatura> listarAsignaturasPorUniversidad(String nombre){
        
        Query q=getEntityManager().createQuery("select a from Asignatura a where a.universidad.nombre=:nombre order by a.universidad.nombre desc" );
        q.setParameter("nombre", nombre);
        return q.getResultList();
        
    }
    
    
    
   
    public List<Asignatura> listarPorCriterio(){
        
        return(getEntityManager().createQuery("select a from Asignatura a where a."+"periodo=:nombre").setParameter("nombre", "anual").getResultList());
    }
    
    public Asignatura find(AsignaturaPK id){
        
        try{
        return(Asignatura)getEntityManager().createQuery("select a from Asignatura a where a.asignaturaPK=:id").setParameter("id", id).getSingleResult();
    }catch(NoResultException ex){
        return null;
    }
    
}
    
}


    

