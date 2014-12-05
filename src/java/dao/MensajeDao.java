package dao;

import entities.Mensaje;
import java.util.Date;
import java.util.List;



public interface MensajeDao {

    public void crearMensaje(Mensaje m);
    public List<Mensaje> mensajesEnviados(String origen,String destino);
    public List<Mensaje> mensajesRecibidos(String origen,String destino);
    public List<Mensaje> mensajesEnviadosPorFecha(String origen,String destino, Date d,Date d2);
    public void modificarEstado(Mensaje m);
    public List<Mensaje> mensajesEnviadosTotal(String origen);
    public List<Mensaje> mensajesRecibidosTotal(String destino);
    public void eliminarMensaje(Mensaje m);
    public Mensaje find(Integer msgId);
}
