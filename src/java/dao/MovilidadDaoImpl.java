
package dao;

import entities.Movilidad;
import java.util.List;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;



@Named(value = "movilidadDaoImpl")
@RequestScoped
public class MovilidadDaoImpl implements MovilidadDao{

    
   @PersistenceContext(unitName = "gestorPU")
    private EntityManager entityManager;
    
    public MovilidadDaoImpl() {
    }
    
    @Override
    public void crearMovilidad(Movilidad m){
        
       entityManager.persist(m);
        
        
    }
    @Override
    public List<Movilidad> listarMovilidad(){
        
        return(entityManager.createQuery("select m from Movilidad m").getResultList());
        
    }
    @Override
    public void cambiarMovilidad(Movilidad m){
      Movilidad aux=entityManager.getReference(Movilidad.class, m.getCodMovilidad());
      entityManager.merge(m);
        
    }
    
    
    @Override
    public void eliminarMovilidad(Movilidad m){
        
       entityManager.remove(entityManager.merge(m));
        
    }
    @Override
    public List<Movilidad> listarPorEstado(String estado){
        
        Query q=entityManager.createQuery("select m from Movilidad m where m.estado=:estado");
        q.setParameter("estado", estado);
        return(q.getResultList());
    }
    @Override
    public List<Movilidad> listarMisMovilidades(String user){
        
        Query q= entityManager.createQuery("select m from Movilidad m where m.loginUsuario.login=:user");
        q.setParameter("user", user);
        return q.getResultList();
        
        
    }
    @Override
    public List<Movilidad> listarMisMovilidadesPorEstado(String user,String estado){
        
        Query q=entityManager.createQuery("select m from Movilidad m where m.loginUsuario.login=:user and m.estado=:estado");
        q.setParameter("user", user);
        q.setParameter("estado", estado);
        return q.getResultList();
        
    }
    
    @Override
    public List<Object> doJoin(){
        
        String s="user1";
        Query q=entityManager.createQuery("select m,u from Movilidad m,Universidad u where m.loginUsuario.login=:s");
        q.setParameter("s", s);
        return q.getResultList();
    }
    
    @Override
    public List<Movilidad> listarMovilidadesValidas(String usuario){
        
        String encurso="en curso";
        String aceptada="aceptada";
        
        Query q= entityManager.createQuery("select m from Movilidad m where m.loginUsuario.login=:usuario and (m.estado=:encurso or m.estado=:aceptada)");
        q.setParameter("usuario", usuario);
        q.setParameter("encurso", encurso);
        q.setParameter("aceptada", aceptada);
        
        return q.getResultList();
        
    }
    @Override
    public Movilidad findMovilidad(Integer id){
        
        return (Movilidad)entityManager.createQuery("select m from Movilidad m where m.codMovilidad=:id").setParameter("id", id).getSingleResult();
    }
    
    
    
}

    

