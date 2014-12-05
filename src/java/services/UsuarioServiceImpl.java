package services;


import dao.UsuarioDao;
import entities.Usuario;
import exceptions.PasswordIncorrectoException;
import exceptions.UsuarioNotFoundException;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import java.util.UUID;



@Stateless
public class UsuarioServiceImpl implements UsuarioService{

    @Inject
    private UsuarioDao usuarioDao;

  
    @Override
    public Usuario find(String nombre)throws UsuarioNotFoundException{
        Usuario u;
        try{
        u=usuarioDao.find(nombre);
        }catch(RuntimeException ex){
            throw new UsuarioNotFoundException();
        }
        if(u==null){
            throw new UsuarioNotFoundException();
        }
        
        return u;
    }
    
    @Override
    public void delete(Usuario u) throws UsuarioNotFoundException{
        
        
        
        
        usuarioDao.delete(u);
       
    }
    
    @Override
    public List<Usuario>listar(){
        
        List<Usuario> lista=usuarioDao.listar();
        Usuario u=usuarioDao.find("admin");
        lista.remove(u);
        return lista;
    }
    
    
    @Override
    public void insertarUsuario(Usuario u){
        
        
            
            usuarioDao.insertarUsuario(u);
}
    @Override
    public void actualizar(Usuario u){
        
        
        usuarioDao.actualizar(u);
        
        
    }
    
    
    @Override
    public void autenticarUsuario(String password,Usuario u)throws PasswordIncorrectoException{
        
        password=md5Password(password);
            String pass=u.getPassword();
            if((pass.equals(password)==false)||u.getTipoUsuario()!=1){
                
               throw new PasswordIncorrectoException();
            }
        
    
}

   @Override
   public void autenticarAdmin(String password,Usuario u) throws PasswordIncorrectoException{
       
       password=md5Password(password);
            String pass=u.getPassword();
            if((pass.equals(password)==false)||u.getTipoUsuario()==1){
                
               throw new PasswordIncorrectoException();
            }
       
   }
   
   @Override
   public String generarPassword(){
       
       return UUID.randomUUID().toString();
       
       
   }
   
   
   @Override
    public String md5Password(String password){
        
        
        try {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
        byte[] array = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
       }
        return sb.toString();
    } catch (java.security.NoSuchAlgorithmException e) {
    }
    return null;
        
        
        
    }
   
   @Override
    public void enviarEmail(String login,String password) throws EmailException{
        
    
       
        String mensaje="La contraseña es "+password+"\n Puedes cambiarla en la aplicación";
        Email email = new SimpleEmail();
        email.setHostName("smtp.googlemail.com");
        //email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        
        email.setAuthenticator(new DefaultAuthenticator("registroerasmus@gmail.com", "registrousers"));
        email.setSSLOnConnect(true);
        email.setFrom("registro.erasmus@gmail.com");
        email.setSubject("Usuario creado");
        email.setMsg(mensaje);
        email.addTo(login+"@udc.es");
        //email.setTLS(true);
        email.send();
        
        
    }
    
}
