/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Contrato;
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
@Named(value = "contratoDao")
@RequestScoped
public class ContratoDao extends GenericDao<Contrato>{

    
    @PersistenceContext(unitName = "gestorPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ContratoDao() {
        setClase(Contrato.class);
    }
    


    
    public List<Contrato> listaContratos(Movilidad m){
        
        return getEntityManager().createQuery("select c from Contrato c where c.idMovilidad=:movilidad").setParameter("movilidad", m).getResultList();
                
    }
    
    public Contrato findContrato(Integer id){
        
        return (Contrato)getEntityManager().createQuery("select c from Contrato c where c.idContrato=:contrato").setParameter("contrato", id).getSingleResult();
    }
}