/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loginrmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ghadeer
 */
public class LogInServer extends UnicastRemoteObject implements LogInFacade{

    private TreeMap clients = new TreeMap<String,String>();
    
    public LogInServer() throws RemoteException{
        super();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            LogInServer obj = new LogInServer();
            
            reg.rebind("rmi://localhost/service", obj);
            
            System.out.println("Server Running...");
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public String login(String username, String password) throws RemoteException {
        init();
        
        String response = search(username, password);
        
        return response;
    }
    
    
    private String search(String username , String password){
        String response = "";
        
        Set set = clients.entrySet();
        Iterator itr = set.iterator();
        boolean flag = false;
        
        while(itr.hasNext()){
            response = "";
            Map.Entry entry = (Map.Entry) itr.next();
            String user = entry.getKey().toString();
            String pass = entry.getValue().toString();
            
            if(username.equals(user)){
                flag = true;
                if(password.equals(pass)){
                    response = "LOGIN_SUCCESFUL";
                }else{
                    response = "PASSWORD_INCORRECT";
                }                
                break;
            }           
        }
        if(! flag){
            response = "USER_NOT_EXISTS";
        }
        
        return response;
    }
    
    private void init(){
        clients.put("user1", "1234");
        clients.put("user2", "1234");
    }
    
}
