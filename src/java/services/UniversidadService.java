package services;

import entities.Cursoacademico;
import entities.Pais;
import entities.Universidad;
import exceptions.PaisException;
import exceptions.UniversidadException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;


@Local
public interface UniversidadService {
    
    public List<Pais>listaPaises();
    public List<Universidad>listaUniversidades();
    public Pais findPais(String pais) throws PaisException;
    public void insertarPais(String pais);
    public void deletePais(Pais p);
    public List<Universidad> listar();
    public void delete(Universidad u);
    
    public void insertarUniversidad(Universidad u);
    public void actualizar(Universidad u);
    
    public List<Universidad> listarPorPais(String pais);
    public void crearCursoAcademico(Cursoacademico cursoAcademico);
    public void eliminarCursoAcademico(String c);
    public List<Cursoacademico> listaCursosAcademicos();
    public Universidad findUniversidad(String universidad) throws UniversidadException;
    public Cursoacademico buscarCursoAcademico(Date fechaInicio,Date fechaFin);
    
}
