import java.rmi.*;

public class servera {
    public servera() throws RemoteException
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
