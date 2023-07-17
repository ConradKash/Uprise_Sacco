import java.rmi.*;


public class startServer {
    
    public static void main(String argv[])
    {
        try
        {
            System.out.println("Starting Server...");
            Naming.rebind("ServerMat_1", (Remote) new servera());
        }
        catch (Exception e)
        {
            System.out.println("An Error  has occured.\n"+e.toString());
        }
    }
    
}
