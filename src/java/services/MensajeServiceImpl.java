package services;

import dao.MensajeDao;
import entities.Mensaje;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;



@Stateless
public class MensajeServiceImpl implements MensajeService{

    @Inject
    private MensajeDao mensajeDao;
    
    
     @Override
    public List<Mensaje> mensajesEnviados(String origen,String destino){
        
        return mensajeDao.mensajesEnviados(origen, destino);
    }
    
     @Override
    public List<Mensaje> mensajesRecibidos(String origen,String destino){
        
        return mensajeDao.mensajesRecibidos(origen, destino);
    }
    
    @Override
    public void enviarMensaje(Mensaje m){
        mensajeDao.create(m);
        
    }
    @Override
    public List<Mensaje> mensajesEnviadosTotal(String destino){
        
        return mensajeDao.mensajesEnviadosTotal(destino);
        
        
    }
    @Override
    public List<Mensaje> mensajesRecibidosTotal(String origen){
        
        return mensajeDao.mensajesRecibidosTotal(origen);
    }
    
    
    @Override
    public void eliminarMensaje(Mensaje m,String accion){
        
        
         Mensaje aux=find(m.getIdmensaje());
            
            if(aux!=null){
                
                
                if(accion.equals("recibido")){
            aux.setEliminadoDestino("si");
                
             
                if(aux.getEliminadoOrigen().equals("si")){
                    
                    mensajeDao.remove(aux);
                }else{
                    
                     enviarMensaje(aux);
                }
              }else{
                    if(accion.equals("enviado")){
            aux.setEliminadoOrigen("si");
                
             
                if(aux.getEliminadoDestino().equals("si")){
                    
                    mensajeDao.remove(aux);
                }else{
                    
                     enviarMensaje(aux);
                }
              }
                }
            }
                
            
    }
    
    @Override
    public Mensaje find(Integer msgId){
        
        return mensajeDao.find(msgId);
    }
    


    @Override
    public void leerMensajeRecibido(Mensaje selectedMensajeRecibido){

            Mensaje aux=find(selectedMensajeRecibido.getIdmensaje());
            
            if(aux!=null){
            aux.setLeidoDestino("si");
            enviarMensaje(aux);
            
        
            }
      
}
    
    
    
}