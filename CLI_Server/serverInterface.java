import java.rmi.*;

public interface serverInterface extends Remote {
    public double sum(double a, double b) throws RemoteException;
    public double subtruct(double a, double b) throws RemoteException;
    public double multiplica(double a, double b) throws RemoteException;
    public double divide(double a, double b) throws RemoteException;
    
}
