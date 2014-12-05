
package dao;

import entities.Usuario;
import java.util.List;



public interface UsuarioDao {

    public Usuario find(String name);
    public void delete(Usuario u);
    public List<Usuario> listar();
    public void insertarUsuario(Usuario u);
    public void actualizar(Usuario u);
   
    
        
        
    
}


