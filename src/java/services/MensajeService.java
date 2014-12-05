package services;

import entities.Mensaje;
import java.util.List;
import javax.ejb.Local;


@Local
public interface MensajeService {
    public List<Mensaje> mensajesEnviados(String origen,String destino);
public List<Mensaje> mensajesRecibidos(String origen,String destino);
public void enviarMensaje(Mensaje m);
public List<Mensaje> mensajesRecibidosTotal(String destino);
public List<Mensaje> mensajesEnviadosTotal(String origen);
public void eliminarMensaje(Mensaje m,String accion);
public Mensaje find(Integer msgId);
public void leerMensajeRecibido(Mensaje m);
    
}