import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Collections;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Server
{
	private ServerSocket socServer;
	
	public static void main (String [] args)
	{
		Connection con;
		Statement statement;

		try
		{
			//con = DriverManager.getConnection("jdbc:mysql://169.254.188.8:3306/projekt","root","");
			con = DriverManager.getConnection("jdbc:mysql://localhost/projekt","root","");
			statement = con.createStatement();
			System.out.println("Database Connected");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Database Not Connected");
			return;
		}

		try
		{
			ServerSocket serverSocket = new ServerSocket(7);
			Server server = new Server(serverSocket);
			System.out.println("Server now running!");
			server.waitForConnection(con, statement);
		}
		catch (IOException e)
		{
			System.out.println("Oops! Error 2");
			System.out.println("Is a Server already running?");
		}
		
		
	}

	
	public Server (ServerSocket soc)
	{
		socServer = soc;
	}
	
	public void waitForConnection (Connection con, Statement statement)
	{
		try
		{
			while (!socServer.isClosed())
			{
				Socket temp = socServer.accept();
				System.out.println("New connection made!");
				Client newCli = new Client(temp, con, statement);
				
				Thread thr = new Thread(newCli);
				thr.start();
			}
		}
		catch (IOException e)
		{
			try
			{
				if (socServer != null)
				{
					socServer.close();
				}
			}
			catch (IOException f)
			{
				System.out.println("Oops! Error 1");
			}			
		}
	}
}
