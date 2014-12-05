

package utils;

import entities.Cursoacademico;
import entities.Estado;
import entities.EstadoMovilidad;
import java.util.List;
import javax.ejb.Local;



@Local
public interface UtilidadService {
    
    public void crearEstado(Estado e);
    public List<Estado> listaEstados();
    public void eliminaEstado(Estado e);
    
    public void crearEstadoMovilidad(EstadoMovilidad e);
    public List<EstadoMovilidad> listaEstadosMovilidad();
    public void eliminaEstadoMovilidad(EstadoMovilidad e);
    
    public void crearCursoAcademico(Cursoacademico c);
    public List<Cursoacademico> listaCursoAcademico();
    public void eliminaCursoAcademico(Cursoacademico c);
    
}
