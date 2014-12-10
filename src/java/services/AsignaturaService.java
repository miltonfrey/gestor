package services;

import entities.Asignatura;
import exceptions.UniversidadException;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityNotFoundException;


@Local
public interface AsignaturaService {
 
     public void crearAsignatura(Asignatura a);
    public List<Asignatura> listarAsignaturas();
    public List<Asignatura> listarAsignaturasPorUniversidad(String codUniversidad);
    public void eliminaAsignatura(Asignatura a);
    public void actualizarAsignatura(Asignatura a) throws UniversidadException;
    public List<Asignatura> listarPorCriterio();
    
}
