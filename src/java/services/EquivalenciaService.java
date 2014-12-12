
package services;

import entities.Contrato;
import entities.Equivalencia;
import entities.Movilidad;
import exceptions.ContratoNotFoundException;
import exceptions.EquivalenciaException;
import exceptions.FechaIncorrectaException;
import exceptions.MovilidadNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;


import utils.EquivalenciaRevisada;

@Local
public interface EquivalenciaService {
    public void crearEquivalencia(Equivalencia e);
    public void eliminarEquivalencia(Equivalencia e) ;
    public void actualizarEquivalencia(Equivalencia e);
    public List<Equivalencia> listarEquivalencias();
    public Equivalencia find(Integer id) throws EquivalenciaException;
    
    
       
    
    public void creaContrato(Contrato c);
    public void modificaContrato(Contrato c);
    public List<Contrato> listaContratos(Movilidad m);
    public void eliminaContrato(Contrato c);  
    public Contrato findContrato(Integer id) throws ContratoNotFoundException;
   public List<Equivalencia> equivalenciasPublicas(String universidad);
   public int[] totalCreditos(ArrayList<Equivalencia> lista);
   public void confirmarContrato(ArrayList<Equivalencia> lista,Contrato c)throws ContratoNotFoundException;
   public ArrayList<Equivalencia> editarContrato(ArrayList<Equivalencia>listaAuxEquivalencias,Contrato c) throws ContratoNotFoundException;
   public void crearContratoDesdeAceptado(ArrayList<Equivalencia>listaAuxEquivalencias,Contrato c, Contrato cNuevo)throws ContratoNotFoundException,EquivalenciaException;
   public void compruebaFechaCrearContrato(Contrato c,Date aux)throws FechaIncorrectaException;
   public ArrayList<EquivalenciaRevisada> compararEquivalencias(ArrayList<Equivalencia> listaAuxEquivalencias,ArrayList<Equivalencia> listaAuxEquivalenciasComparado);
    public Contrato verContratoPorEquivalencia(Equivalencia e)throws ContratoNotFoundException,EquivalenciaException;
    public Movilidad buscarMovilidadPorContrato(Contrato c)throws ContratoNotFoundException,MovilidadNotFoundException;
    
}
