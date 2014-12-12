/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Contrato;
import entities.Equivalencia;
import entities.Movilidad;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



/**
 *
 * @author cba
 */
@Named(value = "equivalenciaDao")
@RequestScoped
public class EquivalenciaDao extends GenericDao<Equivalencia>{

   @PersistenceContext(unitName = "gestorPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public EquivalenciaDao() {
        setClase(Equivalencia.class);
    }
    
    
    public Equivalencia find(Integer id){
        
        return (Equivalencia)getEntityManager().createQuery("select e from Equivalencia e where e.idequivalencia=:id").setParameter("id", id).getSingleResult();
        
    }
    
  
   
   
   public List<Equivalencia> equivalenciasPublicas(String universidad){
       
       
       
       return getEntityManager().createQuery("select e from Equivalencia e where e.visible='si' and e.idequivalencia in" +
               "(select m.idEquivalencia from MiembroGrupoAsignaturaB m where m.asignatura.asignaturaPK.nombreUniversidad=:universidad)").setParameter("universidad", universidad).getResultList();
   }
   
   
}
    

