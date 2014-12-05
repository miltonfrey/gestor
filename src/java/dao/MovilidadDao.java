package dao;

import entities.Movilidad;
import java.util.List;



public interface MovilidadDao {
    
    public void crearMovilidad(Movilidad m);
    public List<Movilidad> listarMovilidad();
    public void cambiarMovilidad(Movilidad m);
    public void eliminarMovilidad(Movilidad m);
    
    public List<Movilidad> listarPorEstado(String estado);
    public List<Movilidad> listarMisMovilidades(String user);
    public List<Movilidad> listarMisMovilidadesPorEstado(String user, String estado);
    
    public List<Movilidad> listarMovilidadesValidas(String usuario);
    
    public Movilidad findMovilidad(Integer id);
    
    public List<Object> doJoin();
        
        
     
    
    
    
}
