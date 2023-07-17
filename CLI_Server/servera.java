import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class servera extends UnicastRemoteObject implements serverInterface {
    public servera() throws RemoteException
    {
        System.out.println("An intance has been started...");
    }
    public double sum (double a, double b) throws RemoteException
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
    @Override
    public double multiplica(double a, double b) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'multiplica'");
    }
}
