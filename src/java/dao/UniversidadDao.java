package dao;

import entities.Cursoacademico;
import entities.Pais;
import entities.Universidad;
import java.util.List;



public interface UniversidadDao {
    
    

    public void insertarPais(Pais p);

    
    
    public List<Pais> listaPaises();
    public List<Universidad>listaUniversidades();
    public Pais findPais(String pais);
    public List<Universidad> listarUniversidades();
    public void delete(Universidad u);
    public void deletePais(Pais p);
    public void insertarCarrera(Universidad u);
    public void actualizar(Universidad u);
    public List<Universidad> listarPorUniversidad(String universidad);
    public List<Universidad> listarPorPais(String pais);
   public void crearCursoAcademico(Cursoacademico c); 
   public void eliminarCursoAcademico(Cursoacademico c);
   public List<Cursoacademico> listaCursosAcademicos();
   public Universidad findUniversidad(String universidad);
   
}


    

