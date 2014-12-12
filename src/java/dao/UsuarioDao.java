package dao;

import entities.Usuario;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Named("daoImpl")
@RequestScoped
public class UsuarioDao extends GenericDao<Usuario> implements Serializable{

    
@PersistenceContext(unitName = "gestorPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
    public UsuarioDao() {
        
        setClase(Usuario.class);
    }
    
   
}
