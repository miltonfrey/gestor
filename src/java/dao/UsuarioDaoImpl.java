
package dao;

import entities.Usuario;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;




@Named(value = "usuarioDaoImpl")
@RequestScoped
public class UsuarioDaoImpl implements UsuarioDao{

    
    @PersistenceContext(unitName = "pruebaHibernate2PU")
    private EntityManager entityManager;
   
    public UsuarioDaoImpl() {
    }
    
    
    @Override
    public Usuario find(String nombre){
        
        
        return (Usuario) entityManager.createQuery("select u from Usuario u where u.login=:nombre").setParameter("nombre", nombre).getSingleResult();
        
        
        
        
        
    }
    
    @Override
    public void delete(Usuario u) {
        
        
        
        entityManager.remove(entityManager.merge(u));
        
        
    }
    
    @Override
    public List<Usuario> listar(){
        
        
        return((List<Usuario>)entityManager.createQuery("select u from Usuario u").getResultList());
        
        
    }
    
    
    
    @Override
    public void insertarUsuario(Usuario u){ //throws org.springframework.dao.DataIntegrityViolationException{
        
           
           
            entityManager.persist(u);
            
        
    }
    @Override
    public void actualizar(Usuario u){
        Usuario aux=entityManager.getReference(Usuario.class, u.getLogin());
        entityManager.merge(u);
        
    }
    
    
    
    
}

    
    
    
    

