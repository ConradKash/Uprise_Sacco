package CommandP;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface LogInFacade extends Remote{
    public String login(String username, String password) throws RemoteException;
}
