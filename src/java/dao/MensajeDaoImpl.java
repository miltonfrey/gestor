package dao;

import entities.Mensaje;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 *
 * @author abc
 */
@Named(value = "mensajeDaoImpl")
@RequestScoped
public class MensajeDaoImpl implements MensajeDao{

    @PersistenceContext(unitName = "gestorPU")
    private EntityManager entityManager;
   
    public MensajeDaoImpl() {
    }
    
@Override
    public void crearMensaje(Mensaje m){
        
        entityManager.merge(m);
        
    }
    
    @Override
    public List<Mensaje> mensajesEnviados(String origen,String destino){
        
        Query q=entityManager.createQuery("select m from Mensaje m where m.origen.login=:origen  " +
                "and m.destino.login=:destino and m.eliminadoOrigen='no' order by m.fecha desc" );
             
        q.setParameter("origen", origen);
        q.setParameter("destino", destino);
        return q.getResultList();
      
        
    }
    
    
    @Override
    public List<Mensaje> mensajesRecibidos(String origen,String destino){
        
        Query q=entityManager.createQuery("select m from Mensaje m where m.origen.login=:origen  " +
                "and m.destino.login=:destino and m.eliminadoDestino='no' order by m.fecha desc" );
                
        q.setParameter("origen", origen);
        q.setParameter("destino", destino);
        return q.getResultList();
       
        
    }
            @Override         
       public List<Mensaje> mensajesRecibidosTotal(String destino){
           Query q=entityManager.createQuery("select m from Mensaje m where m.destino.login=:destino and m.eliminadoDestino='no' order by m.fecha desc");
          q.setParameter("destino", destino);
          return q.getResultList();
       }     
            
            
   @Override
    public List<Mensaje> mensajesEnviadosPorFecha(String origen,String destino,Date d,Date d2){
        
        Query q=entityManager.createQuery("select m from Mensaje m where m.origen.login=:origen " +
                "and m.destino.login=:destino and m.fecha<=:d2 and m.fecha>=:d  order by m.fecha desc");
        q.setParameter("origen", origen);
        q.setParameter("destino", destino);
        q.setParameter("d", d);
        q.setParameter("d2", d2);
        
               
        
        
        return q.getResultList();
    }
   
    @Override
    public void modificarEstado(Mensaje m){
        
        entityManager.merge(m);
        
    }
    
    
    
    public List<Object[]> join(String user){
        
        Query q=entityManager.createQuery("select u from Usuario u join u.mensajeSet s ");
        return q.getResultList();
        
    }
      @Override          
      public List<Mensaje> mensajesEnviadosTotal(String origen){
          
          Query q=entityManager.createQuery("select m from Mensaje m where m.origen.login=:origen and m.eliminadoOrigen='no' order by m.fecha desc");
          q.setParameter("origen", origen);
          return q.getResultList();
      }
      
                
             @Override   
             public void eliminarMensaje(Mensaje m){
                 
                 entityManager.remove(m);
                 
             }   
                
     @Override   
     public Mensaje find(Integer msgId){
         return(Mensaje)entityManager.createQuery("select m from Mensaje m where m.idmensaje=:msgId").setParameter("msgId", msgId).getSingleResult();
         
         
     }
    
    
}