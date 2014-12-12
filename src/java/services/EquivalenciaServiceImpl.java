
package services;

import dao.ContratoDao;
import dao.EquivalenciaDao;
import entities.Asignatura;
import entities.Contrato;
import entities.Equivalencia;
import entities.MiembroGrupoAsignaturaA;
import entities.MiembroGrupoAsignaturaB;
import entities.Movilidad;
import exceptions.ContratoNotFoundException;
import exceptions.EquivalenciaException;
import exceptions.FechaIncorrectaException;
import exceptions.MovilidadNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.hibernate.Hibernate;


import utils.EquivalenciaRevisada;


@Stateless
public class EquivalenciaServiceImpl implements EquivalenciaService{

    @Inject
    private EquivalenciaDao equivalenciaDao;
    
    @Inject ContratoDao contratoDao;
    
    @Override
   public Equivalencia find(Integer id) throws EquivalenciaException{
       
       Equivalencia e=equivalenciaDao.find(id);
       if(e==null)
           throw new EquivalenciaException();
       return e;
   }
    
    
    @Override
    public void crearEquivalencia(Equivalencia e){
        
        equivalenciaDao.create(e);
    }
    
    @Override
    public void eliminarEquivalencia(Equivalencia e){
        
        
        
        equivalenciaDao.remove(e);
        
    }
    @Override
    public void actualizarEquivalencia(Equivalencia e){
        
        equivalenciaDao.edit(e, e.getIdequivalencia());
        
    }
    
    @Override
    
    public List<Equivalencia> listarEquivalencias(){
        
        return equivalenciaDao.findAll();
    }
    
    @Override
    public void creaContrato(Contrato c){
        contratoDao.create(c);
        
    }
    
    @Override
    public void modificaContrato(Contrato c){
        
        
        contratoDao.edit(c, c.getIdContrato());
    }
    @Override
    
    public List<Contrato> listaContratos(Movilidad m){
        return contratoDao.listaContratos(m);
    }
    @Override
    public void eliminaContrato(Contrato c){
        
       
       contratoDao.remove(c);
       
    }
    
    
    
    @Override
    public Contrato findContrato(Integer id) throws ContratoNotFoundException{
        
        Contrato c=contratoDao.findContrato(id);
        if (c==null)
                throw new ContratoNotFoundException();
        
        Hibernate.initialize(c.getEquivalenciaSet());
        for(Equivalencia e:c.getEquivalenciaSet()){ 
            
        //Hibernate.initialize(e.getGrupoAsignaturaB().getMiembroGrupoAsignaturaBs());
        Iterator i=e.getMiembroGrupoAsignaturaASet().iterator();
        while(i.hasNext()){
            MiembroGrupoAsignaturaA m=(MiembroGrupoAsignaturaA)i.next();
            Hibernate.initialize(m.getAsignatura());
        }
        //Hibernate.initialize(e.getGrupoAsignaturaA().getMiembroGrupoAsignaturaAs());
        Iterator j=e.getMiembroGrupoAsignaturaBSet().iterator();
        while(j.hasNext()){
            MiembroGrupoAsignaturaB m=(MiembroGrupoAsignaturaB)j.next();
            Hibernate.initialize(m.getAsignatura());
            
        }
        
        
           
}
        
         return c; 
    }
  
      
    @Override  
    public List<Equivalencia> equivalenciasPublicas(String Universidad){
        
         List<Equivalencia>listaEquivalencias=equivalenciaDao.equivalenciasPublicas(Universidad);
        
        for(Equivalencia e:listaEquivalencias){
            Hibernate.initialize(e.getMiembroGrupoAsignaturaASet());
           Iterator i=e.getMiembroGrupoAsignaturaASet().iterator();
            while(i.hasNext()){
                MiembroGrupoAsignaturaA m=(MiembroGrupoAsignaturaA)i.next();
                Hibernate.initialize(m.getAsignatura());
            }
            
             Hibernate.initialize(e.getMiembroGrupoAsignaturaBSet());
            i=e.getMiembroGrupoAsignaturaBSet().iterator();
            while(i.hasNext()){
                MiembroGrupoAsignaturaB m=(MiembroGrupoAsignaturaB)i.next();
                Hibernate.initialize(m.getAsignatura());
            }
        }
        return listaEquivalencias;
    }
    
    @Override
    public int[] totalCreditos(ArrayList<Equivalencia> lista){
        
         int a=0;
         int b=0;
         
        for(Equivalencia e:lista){
            Iterator i=e.getMiembroGrupoAsignaturaASet().iterator();
            while(i.hasNext()){
                MiembroGrupoAsignaturaA mA=(MiembroGrupoAsignaturaA)i.next();
                a=a+mA.getAsignatura().getCreditos();
            }
        }
        
        for(Equivalencia e:lista){
            Iterator i=e.getMiembroGrupoAsignaturaBSet().iterator();
            while(i.hasNext()){
                MiembroGrupoAsignaturaB mB=(MiembroGrupoAsignaturaB)i.next();
                b=b+mB.getAsignatura().getCreditos();
            }
        }
        
        return new int[]{a,b};
    }
    
    @Override
    public void confirmarContrato(ArrayList<Equivalencia> listaAuxEquivalencias,Contrato c) throws ContratoNotFoundException{
        
        Set s1=new HashSet<Equivalencia>();
        for(Equivalencia e:listaAuxEquivalencias){
          e.setIdequivalencia(null);
            s1.add(e);
            crearEquivalencia(e);
           
             
        }
        c.setEquivalenciaSet(s1);
        creaContrato(c);
        
    }
    
    @Override
    public ArrayList<Equivalencia> editarContrato(ArrayList<Equivalencia> listaAuxEquivalencias,Contrato c) throws ContratoNotFoundException{
        
        ArrayList<Equivalencia> listaCopia=new ArrayList<Equivalencia>();
               
        
        for(Equivalencia e:c.getEquivalenciaSet()){
            
            if(listaAuxEquivalencias.contains(e)==false){
              
                   listaCopia.add(e);
          
        }
        }
        
        for(Equivalencia e:listaCopia){
           
            c.getEquivalenciaSet().remove(e);
            
            modificaContrato(c);
            
        }
         
        
        for(Equivalencia e2:listaAuxEquivalencias){
            
         if(c.getEquivalenciaSet().contains(e2)==false){   
           
            e2.setIdequivalencia(null);
             Iterator i=e2.getMiembroGrupoAsignaturaASet().iterator();
          while(i.hasNext()){
              
              MiembroGrupoAsignaturaA ma=(MiembroGrupoAsignaturaA)i.next();
              
              
          }
          
            crearEquivalencia(e2);
            c.getEquivalenciaSet().add(e2);
             
        }
        
        }
        
        c.setEstado("pendiente");
        c.setFecha(Calendar.getInstance().getTime());
        modificaContrato(c);
            return listaCopia;
    }
       
           
        
    
    
    
 
    @Override
     public void crearContratoDesdeAceptado(ArrayList<Equivalencia>listaAuxEquivalencias,Contrato c,Contrato cNuevo) throws ContratoNotFoundException,EquivalenciaException{
      // c=findContrato(c.getIdContrato());
        cNuevo.setEquivalenciaSet(new HashSet<Equivalencia>());
        
        for(Equivalencia e:listaAuxEquivalencias){
            
         if(c.getEquivalenciaSet().contains(e)==true){   
            //e=find(e.getIdequivalencia());
            cNuevo.getEquivalenciaSet().add(e);
            
         
           
        }else{
             e.setIdequivalencia(null);
            crearEquivalencia(e);
            
             cNuevo.getEquivalenciaSet().add(e);
         }
       
    }
        
        creaContrato(cNuevo);
        
    }
     
     @Override
     public void compruebaFechaCrearContrato(Contrato c,Date aux)throws FechaIncorrectaException{
         
         
         
     }
    
    @Override 
    public ArrayList<EquivalenciaRevisada> compararEquivalencias(ArrayList<Equivalencia> listaAuxEquivalencias,ArrayList<Equivalencia> listaAuxEquivalenciasComparado){
        
        ArrayList<EquivalenciaRevisada> listaRevisada=new ArrayList<EquivalenciaRevisada>();
        
        loop:
        for(Equivalencia e:listaAuxEquivalencias){
            EquivalenciaRevisada er=new EquivalenciaRevisada(e);
            er.setIgual(true);
            ArrayList<Asignatura> listaAsignaturas=new ArrayList<Asignatura>();
            ArrayList<Asignatura> listaAsignaturasB=new ArrayList<Asignatura>();
            
            
            Iterator i=e.getMiembroGrupoAsignaturaASet().iterator();
            while(i.hasNext()){
                MiembroGrupoAsignaturaA m=(MiembroGrupoAsignaturaA)i.next();
                listaAsignaturas.add(m.getAsignatura());   
            }
            
            
            Iterator j=e.getMiembroGrupoAsignaturaBSet().iterator();
            while(j.hasNext()){
                MiembroGrupoAsignaturaB mb=(MiembroGrupoAsignaturaB)j.next();
                listaAsignaturasB.add(mb.getAsignatura());
            }     
            
            
            loopB: 
                  for(Equivalencia eComp:listaAuxEquivalenciasComparado){
                      
                       ArrayList<Asignatura> listaAsignaturasComp=new ArrayList<Asignatura>();
                       i=eComp.getMiembroGrupoAsignaturaASet().iterator();
                            while(i.hasNext()){
                            MiembroGrupoAsignaturaA m=(MiembroGrupoAsignaturaA)i.next();
                            listaAsignaturasComp.add(m.getAsignatura());
                
                        }
                             
                           if(listaAsignaturas.size()==listaAsignaturasComp.size()){
                                 if(contiene(listaAsignaturas, listaAsignaturasComp)){
                              
                                   System.out.println(listaAsignaturasComp.get(0).getNombreAsignatura());
                                   ArrayList<Asignatura> listaAsignaturasCompB=new ArrayList<Asignatura>();
                                      j=eComp.getMiembroGrupoAsignaturaBSet().iterator();
                                      
                                       while(j.hasNext()){
                                       MiembroGrupoAsignaturaB mb=(MiembroGrupoAsignaturaB)j.next();
                                       listaAsignaturasCompB.add(mb.getAsignatura());
                           }     
                                      
                                      if(listaAsignaturasB.size()==listaAsignaturasCompB.size()){
                                          if(contiene(listaAsignaturasB, listaAsignaturasCompB)){
                                                er.setIgual(false);
                                                listaRevisada.add(er);
                                                continue loop;
                                          }
                                      }
                               
                               
                           }
                           
                  }
                           
               }
                listaRevisada.add(er);
            }
            return listaRevisada;
        }
       
     
 
     
  /*   @Override
     public ArrayList<EquivalenciaRevisada> compararEquivalencias(ArrayList<Equivalencia> listaAuxEquivalencias,ArrayList<Equivalencia> listaAuxEquivalenciasComparado){
         
         ArrayList<EquivalenciaRevisada> listaRevisada=new ArrayList<EquivalenciaRevisada>();
         
         for(Equivalencia e:listaAuxEquivalencias){
             EquivalenciaRevisada er=new EquivalenciaRevisada(e);
             
             if(listaAuxEquivalenciasComparado.contains(e)==false){
                 er.setIgual(true);
             }
             listaRevisada.add(er);
         }
          
         return listaRevisada;
        
     }*/
     
     
     public boolean contiene(ArrayList<Asignatura> listaA, ArrayList<Asignatura> listaB){
        
         
         loopA:
         for(Asignatura a:listaA){
             for(Asignatura b:listaB){
                 if(a.getNombreAsignatura().equals(b.getNombreAsignatura()))
                 continue loopA;    
             }
             return false;
         }
         
         return true;
     }
     
     @Override
     public Contrato verContratoPorEquivalencia(Equivalencia e) throws ContratoNotFoundException,EquivalenciaException{
         
         Contrato c=null;
         e=find(e.getIdequivalencia());
         Hibernate.initialize(e.getContratoSet());
         
         Iterator i=e.getContratoSet().iterator();
         while(i.hasNext()){
             if(c==null){
                 c=(Contrato)i.next();
             }else{
                 Contrato aux=(Contrato)i.next();
                 if (c.getFecha().compareTo(aux.getFecha())<0){
                     
                 c=aux;
                     
                 
             }
             
         }
         
     }
    if(c==null)
        throw new ContratoNotFoundException();
     Hibernate.initialize(c.getIdMovilidad());
     Hibernate.initialize(c.getIdMovilidad().getLoginUsuario());
     return c;
     
}
     
     @Override //no hace falta
     public Movilidad buscarMovilidadPorContrato(Contrato c)throws ContratoNotFoundException,MovilidadNotFoundException{
        c=findContrato(c.getIdContrato());
        //Hibernate.initialize(c.getMovilidad());
        if(c.getIdMovilidad()==null)
            throw new MovilidadNotFoundException();
        //Hibernate.initialize(c.getMovilidad().getUniversidad());
        Hibernate.initialize(c.getIdMovilidad());
        return c.getIdMovilidad();
     }
             
    
   
}        
          

