/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.UniversidadDao;
import entities.Cursoacademico;
import entities.Pais;
import entities.Universidad;
import exceptions.PaisException;
import exceptions.UniversidadException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;


@Stateless
public class UniversidadServiceImpl implements UniversidadService,Serializable{
    
    @Inject
    private UniversidadDao universidadDao;

@Override
    
    public List<Pais> listaPaises(){
        
        return universidadDao.listaPaises();
    }
    
    @Override
    public List<Universidad> listaUniversidades(){
        
        return universidadDao.findAll();
    }
    
    @Override
    public Pais findPais(String pais) throws PaisException{
        Pais p=universidadDao.findPais(pais);
        if(p==null){
            throw new PaisException();
        }
        return p;
    }
    
    
    @Override
    public void insertarPais(String pais){
        
        
        Pais p=new Pais(pais);
        universidadDao.insertarPais(p);        
            
        
    }
    @Override
    public void deletePais(Pais p){
        
        universidadDao.deletePais(p);
    }
    
    
    
    @Override
    public List<Universidad> listar(){
        
        return(universidadDao.findAll());
    }
    
    @Override
    public void delete(Universidad u){
       
        universidadDao.delete(u);
        
    }
    
    
        
    
    @Override
    public void insertarUniversidad(Universidad u){
        
     universidadDao.create(u);
        
        
    }
    @Override
    public void actualizar(Universidad u){
        
        universidadDao.edit(u, u.getNombre());
        
    }
    
    @Override
    public List<Universidad>listarPorPais(String pais){
        
        return universidadDao.listarPorPais(pais);
        
    }
   
    @Override
   public void crearCursoAcademico(Cursoacademico cursoAcademico){
       
       
       universidadDao.crearCursoAcademico(cursoAcademico);
       
   }
   @Override
   public void eliminarCursoAcademico(String cursoAcademico){
       
       Cursoacademico c=new Cursoacademico(cursoAcademico);
       universidadDao.eliminarCursoAcademico(c);
       
       
   }
    
   @Override
   public List<Cursoacademico> listaCursosAcademicos(){
       
       return universidadDao.listaCursosAcademicos();
   }
   
   @Override
   public Cursoacademico buscarCursoAcademico(Date fechaInicio,Date fechaFin){
    Calendar cal1=Calendar.getInstance();
    Calendar cal2=Calendar.getInstance();
    cal1.setTime(fechaInicio);
    cal2.setTime(fechaFin);
    Cursoacademico ca=new Cursoacademico();
                    
                    if(cal1.get(2)>8){
                       
                        ca.setCursoAcademico(cal1.get(1)+"/"+(cal1.get(1)+1));
                    }else{
                        
                        ca.setCursoAcademico(cal1.get(1)-1+"/"+(cal1.get(1)));
                    }
                    
                    for(Cursoacademico c:listaCursosAcademicos()){
                        if (c.getCursoAcademico().equals(ca.getCursoAcademico())){
                            return ca;
                        }
                        
                    }
                    
                    crearCursoAcademico(ca);
                    return ca;
    
    
}
   
   
   @Override
   public Universidad findUniversidad(String universidad) throws UniversidadException{
       
       Universidad u=universidadDao.findUniversidad(universidad);
       if(u==null){
           throw new UniversidadException();
       }
       return u;
   }
    
}

    
    
    
    

