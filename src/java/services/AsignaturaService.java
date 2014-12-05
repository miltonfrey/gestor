package services;

import entities.Asignatura;
import java.util.List;
import javax.ejb.Local;


@Local
public interface AsignaturaService {
 
     public void crearAsignatura(Asignatura a);
    public List<Asignatura> listarAsignaturas();
    public List<Asignatura> listarAsignaturasPorUniversidad(String codUniversidad);
    public void eliminaAsignatura(Asignatura a);
    public void actualizarAsignatura(Asignatura a);
    public List<Asignatura> listarPorCriterio();
    
}
