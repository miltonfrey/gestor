package dao;

import entities.Mensaje;
import entities.Movilidad;
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
@Named(value = "mensajeDao")
@RequestScoped
public class MensajeDao extends GenericDao<Mensaje>{

     @PersistenceContext(unitName = "gestorPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public MensajeDao() {
        setClase(Mensaje.class);
    }
    

    
    
    public List<Mensaje> mensajesEnviados(String origen,String destino){
        
        Query q=getEntityManager().createQuery("select m from Mensaje m where m.origen.login=:origen  " +
                "and m.destino.login=:destino and m.eliminadoOrigen='no' order by m.fecha desc" );
             
        q.setParameter("origen", origen);
        q.setParameter("destino", destino);
        return q.getResultList();
      
        
    }
    
    
    
    public List<Mensaje> mensajesRecibidos(String origen,String destino){
        
        Query q=getEntityManager().createQuery("select m from Mensaje m where m.origen.login=:origen  " +
                "and m.destino.login=:destino and m.eliminadoDestino='no' order by m.fecha desc" );
                
        q.setParameter("origen", origen);
        q.setParameter("destino", destino);
        return q.getResultList();
       
        
    }
                  
       public List<Mensaje> mensajesRecibidosTotal(String destino){
           Query q=getEntityManager().createQuery("select m from Mensaje m where m.destino.login=:destino and m.eliminadoDestino='no' order by m.fecha desc");
          q.setParameter("destino", destino);
          return q.getResultList();
       }     
            
            
   
    public List<Mensaje> mensajesEnviadosPorFecha(String origen,String destino,Date d,Date d2){
        
        Query q=getEntityManager().createQuery("select m from Mensaje m where m.origen.login=:origen " +
                "and m.destino.login=:destino and m.fecha<=:d2 and m.fecha>=:d  order by m.fecha desc");
        q.setParameter("origen", origen);
        q.setParameter("destino", destino);
        q.setParameter("d", d);
        q.setParameter("d2", d2);
        
               
        
        
        return q.getResultList();
    }
         
      public List<Mensaje> mensajesEnviadosTotal(String origen){
          
          Query q=getEntityManager().createQuery("select m from Mensaje m where m.origen.login=:origen and m.eliminadoOrigen='no' order by m.fecha desc");
          q.setParameter("origen", origen);
          return q.getResultList();
      }
          
       
     public Mensaje find(Integer msgId){
         return(Mensaje)getEntityManager().createQuery("select m from Mensaje m where m.idmensaje=:msgId").setParameter("msgId", msgId).getSingleResult();
         
         
     }
    
    
}