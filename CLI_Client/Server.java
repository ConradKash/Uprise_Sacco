import java.rmi.*;
import java.rmi.server.*;

public class Server extends UnicastRemoteObject implements interfaceMat {
    public ServerMat() throws RemoteException
    {
        System.out.println("An intance has been started...");
    }
    public double soma(double a, double b) throws RemoteException
    {
        return a+b;
    }
    public double subtruct(double a, double b) throws RemoteException
    {
        return a-b;
    }
    public double multiply(double a, double b) throws RemoteException
    {
        return a*b;
    }
    public double divide(double a, double b) throws RemoteException
    {
        return a/b;
    }
}
public interface interfaceMat extends Remote {
    public double soma(double a, double b) throws RemoteException;
    public double subtruct(double a, double b) throws RemoteException;
    public double multiply(double a, double b) throws RemoteException;
    public double divide(double a, double b) throws RemoteException;
}
public class startServer
{
    public static void main(String argv[])
    {
        try
        {
            System.out.println("Starting Server...");
            Naming.rebind("ServerMat_1", new ServerMat());
        }
        catch (Exception e)
        {
            System.out.println("An Error  has occured.\n"+e.toString());
        }
    }
}
