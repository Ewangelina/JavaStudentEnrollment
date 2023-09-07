import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import java.net.Socket;
import java.io.*;

import java.sql.*;


public class Client implements Runnable
{
	public Socket socClient;
	
	public BufferedReader reader;
	public BufferedWriter writer;

	private String username;

	Connection con;
	Statement statement;
	
	
	public Client (Socket soc, Connection c, Statement s)
	{
		try
		{
			socClient = soc;
			
			reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));

			con = c;
			statement = s;
		}
		catch (IOException e)
		{
			shutdown(reader, writer, socClient);
		}

	}

	private void addStudent(String index, String name, String surname)
	{
		try
		{
			String sql_command = "INSERT INTO `student`(`indeks`, `name`, `surname`) VALUES('" + index + "','" + name + "','" + surname + "')";
			PreparedStatement ps = con.prepareStatement(sql_command);
			statement.executeUpdate(sql_command);
			sendMessage("ok");
		}
		catch (CommunicationsException c)
		{
			sendMessage("s");
			close();
		}
		catch (SQLException e)
		{
			sendMessage(e.getMessage());
		}
	}

	private void sendStudents()
	{
		try
		{
			String sql_command = "select * from `student`";
			PreparedStatement ps = con.prepareStatement(sql_command);
			ResultSet rs = statement.executeQuery(sql_command);
			String send = "Index Imie Nazwisko";
			sendMessage(send);
			while (rs.next())
			{
				send = rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3);
				sendMessage(send);
			}
			sendMessage("q");
		}
		catch (CommunicationsException c)
		{
			sendMessage("s");
			close();
		}
		catch (SQLException e)
		{
			sendMessage("e");
		}
	}

	private void sendSubjects()
	{
		try
		{
			String sql_command = "select * from `przedmiot`";
			PreparedStatement ps = con.prepareStatement(sql_command);
			ResultSet rs = statement.executeQuery(sql_command);
			String send = "Identyfikator Nazwa";
			sendMessage(send);
			while (rs.next())
			{
				send = rs.getString(1) + " " + rs.getString(2);
				sendMessage(send);
			}
			sendMessage("q");
		}
		catch (CommunicationsException c)
		{
			sendMessage("s");
			close();
		}
		catch (SQLException e)
		{
			sendMessage("e");
		}
	}

	private void addSubject(String identifier, String name)
	{
		try
		{
			String sql_command = "INSERT INTO `przedmiot`(`identyfikator`, `nazwa`) VALUES ('" + identifier + "','" + name + "')";
			PreparedStatement ps = con.prepareStatement(sql_command);
			statement.executeUpdate(sql_command);
			sendMessage("ok");
		}
		catch (CommunicationsException c)
		{
			sendMessage("s");
			close();
		}
		catch (SQLException e)
		{
			sendMessage(e.getMessage());
		}
	}

	private void deleteStudent(String index)
	{

		boolean flag = true;
		try
		{
			String sql_command = "DELETE FROM `student` WHERE `student`.`indeks` = '" + index + "'";
			PreparedStatement ps = con.prepareStatement(sql_command);
			statement.executeUpdate(sql_command);
		}
		catch (CommunicationsException c)
		{
			sendMessage("s");
			close();
		}
		catch (SQLException e)
		{
			flag = false;
			sendMessage("Couldn`t delete student");
		}

		if (flag)
		{
			try
			{
				String sql_command = "DELETE FROM `enrollment` WHERE `enrollment`.`student_id` = '" + index + "'";
				PreparedStatement ps = con.prepareStatement(sql_command);
				statement.executeUpdate(sql_command);
				sendMessage("ok");
			}
			catch (CommunicationsException c)
			{
				sendMessage("s");
				close();
			}
			catch (SQLException e)
			{
				sendMessage("Couldn`t delete enrollment");
			}
		}
	}

	private void deleteSubject(String identifier)
	{
		boolean flag = true;
		try
		{
			String sql_command = "DELETE FROM `przedmiot` WHERE `przedmiot`.`identyfikator` = '" + identifier + "'";
			PreparedStatement ps = con.prepareStatement(sql_command);
			statement.executeUpdate(sql_command);
		}
		catch (CommunicationsException c)
		{
			sendMessage("s");
			close();
		}
		catch (SQLException e)
		{
			flag = false;
			sendMessage("Couldn`t delete subject");
		}

		if (flag)
		{
			try
			{
				String sql_command = "DELETE FROM `enrollment` WHERE `enrollment`.`subject_id` = '" + identifier + "'";
				PreparedStatement ps = con.prepareStatement(sql_command);
				statement.executeUpdate(sql_command);
				sendMessage("ok");
			}
			catch (CommunicationsException c)
			{
				sendMessage("s");
				close();
			}
			catch (SQLException e)
			{
				sendMessage("Couldn`t delete enrollment");
			}
		}
	}

	private void deleteEnrollment(String index, String identifier)
	{
		try
		{
			String sql_command = "DELETE FROM `enrollment` WHERE `enrollment`.`subject_id` = '" + identifier + "' and `enrollment`.`student_id` = '" + index + "'";
			PreparedStatement ps = con.prepareStatement(sql_command);
			statement.executeUpdate(sql_command);
			sendMessage("ok");
		}
		catch (CommunicationsException c)
		{
			sendMessage("s");
			close();
		}
		catch (SQLException e)
		{
			sendMessage(e.getMessage());
		}

	}

	private void enrollStudent (String index, String identifier)
	{
		try
		{
			String sql_command = "INSERT INTO `enrollment`(`student_id`, `subject_id`) VALUES ('" + index + "','" + identifier + "')";
			PreparedStatement ps = con.prepareStatement(sql_command);
			statement.executeUpdate(sql_command);
			sendMessage("ok");
		}
		catch (CommunicationsException c)
		{
			sendMessage("s");
			close();
		}
		catch (SQLException e)
		{
			sendMessage(e.getMessage());
		}
	}

	private void deleteGrade(String index, String identifier)
	{
		try
		{
			String sql_command = "UPDATE `enrollment` SET `grade`= NULL WHERE `enrollment`.`subject_id` = `" + identifier + "` and `enrollment`.`student_id` = `" + index + "`";
			PreparedStatement ps = con.prepareStatement(sql_command);
			statement.executeUpdate(sql_command);
			sendMessage("ok");
		}
		catch (CommunicationsException c)
		{
			sendMessage("s");
			close();
		}
		catch (SQLException e)
		{

			sendMessage(e.getMessage());
		}
	}

	private void gradeStudent(String index, String idenifier, String grade)
	{
		try
		{
			String sql_command = "UPDATE `enrollment` SET `grade`=" + grade + " WHERE `subject_id`='" + idenifier +"' and `student_id`='" + index + "'";
			PreparedStatement ps = con.prepareStatement(sql_command);
			statement.executeUpdate(sql_command);
			sendMessage("ok");
		}
		catch (CommunicationsException c)
		{
			sendMessage("s");
			close();
		}
		catch (SQLException e)
		{
			sendMessage(e.getMessage());
		}
	}

	private void showEnrolled()
	{
		try
		{
			String sql_command = "select `przedmiot`.`nazwa`, avg(`enrollment`.`grade`) from `przedmiot` join `enrollm" +
					"ent` on `przedmiot`.`identyfikator` = `enrollment`.`subject_id` group by `enrollment`.`subject_id`";
			PreparedStatement ps = con.prepareStatement(sql_command);
			ResultSet rs = statement.executeQuery(sql_command);
			String send = "Przedmiot średnia";
			sendMessage(send);
			while (rs.next())
			{
				if (rs.getString(2) == null)
				{
					send = rs.getString(1) + " NULL";

				}
				else
				{
					Double g = new Double(rs.getDouble(2));
					send = rs.getString(1) + " " + g.toString();
				}

				sendMessage(send);
			}
			sendMessage("q");
		}
		catch (CommunicationsException c)
		{
			sendMessage("s");
			close();
		}
		catch (SQLException e)
		{
			sendMessage(e.getMessage());
		}
	}

	private void showSubjects(String index)
	{
		try
		{
			String sql_command = "select `przedmiot`.`nazwa`, `enrollment`.`grade` from `przedmiot` JOIN `enrollment` " +
					"on `przedmiot`.`identyfikator` = `enrollment`.`subject_id` WHERE `enrollment`.`student_id` = '" +
					index + "'";
			PreparedStatement ps = con.prepareStatement(sql_command);
			ResultSet rs = statement.executeQuery(sql_command);
			String send = "Przedmiot Ocena";
			sendMessage(send);
			while (rs.next())
			{
				if (rs.getString(2) == null)
				{
					send = rs.getString(1) + " NULL";

				}
				else
				{
					Double g = new Double(rs.getDouble(2));
					send = rs.getString(1) + " " + g.toString();
				}

				sendMessage(send);
			}
			sendMessage("q");
		}
		catch (CommunicationsException c)
		{
			sendMessage("s");
			close();
		}
		catch (SQLException e)
		{
			sendMessage(e.getMessage());
		}
	}

	/**
	 * Processes message from user
	 * @param mes - message to process
	 */
	private Boolean processMessage (String mes)
	{
		try
		{
			if (mes.equals("quit"))
			{
				return false;
			}
			else if (mes.equals("gs")) //show student
			{
				sendMessage("ok");
				String index = reader.readLine();
				sendMessage("ok");
				String idenifier = reader.readLine();
				sendMessage("ok");
				String grade = reader.readLine();
				gradeStudent(index, idenifier, grade);
			}
			else if (mes.equals("ss")) //show student
			{
				sendStudents();
			}
			else if (mes.equals("pp")) //pokaż przedmioty
			{
				sendSubjects();
			}
			else if (mes.equals("se")) //show enrolled
			{
				showEnrolled();
			}
			else if (mes.equals("pl")) //pokaż lekcje
			{
				sendMessage("ok");
				String index = reader.readLine();
				showSubjects(index);
			}
			else if (mes.equals("as")) //add student
			{
				sendMessage("ok");
				String index = reader.readLine();
				sendMessage("ok");
				String name = reader.readLine();
				sendMessage("ok");
				String surname = reader.readLine();
				addStudent(index, name, surname);
			}
			else if (mes.equals("ap")) //add przedmiot
			{
				sendMessage("ok");
				String identifier = reader.readLine();
				sendMessage("ok");
				String name = reader.readLine();
				addSubject(identifier, name);
			}
			else if (mes.equals("es")) //enroll student
			{
				sendMessage("ok");
				String index = reader.readLine();
				sendMessage("ok");
				String identifier = reader.readLine();
				enrollStudent(index, identifier);
			}
			else if (mes.equals("s")) //delete student
			{
				sendMessage("ok");
				String index = reader.readLine();
				deleteStudent(index);

			}
			else if (mes.equals("p")) //delete przedmiot
			{
				sendMessage("ok");
				String identifier = reader.readLine();
				deleteSubject(identifier);
			}
			else if (mes.equals("e")) //delete enrollment
			{
				sendMessage("ok");
				String index = reader.readLine();
				sendMessage("ok");
				String identifier = reader.readLine();
				deleteEnrollment(index, identifier);

			}
			else if (mes.equals("o")) //delete ocena
			{
				sendMessage("ok");
				String index = reader.readLine();
				sendMessage("ok");
				String identifier = reader.readLine();
				deleteGrade(index, identifier);
			}
		}
		catch (Exception e)
		{
			return false;
		}

		return true;
	}
	
	@Override
	public void run()
	{
		if (socClient.isConnected())
		{
			while (socClient.isConnected())
			{
				try
				{
					String cliMessage = reader.readLine();
					if (!processMessage(cliMessage))
					{
						shutdown(reader, writer, socClient);
						break;
					}
					
				}
				catch (IOException e)
				{
					shutdown(reader, writer, socClient);
					break;
				}
			}
		}

		System.out.println("A connection was terminated!");		
	}

	/**
	 * Sends message to server
	 * @param message - message to send
	 */
	private void sendMessage (String message)
    {
        try
        {
            writer.write(message);
            writer.newLine();
            writer.flush();
        }
        catch (Exception e)
        {
			Client.shutdown(reader, writer, socClient);
        }
    }

	/**
	 * Closes open communication with database and ends program with 1
	 */
	private void close ()
	{
		shutdown(reader, writer, socClient);
		System.exit(1);
	}

	/**
	 * Closes open communication
	 */
	public static void shutdown (BufferedReader re, BufferedWriter wr, Socket soc)
	{
		try
		{
			if (re != null)
			{
				re.close();
			}

			if (wr != null)
			{
				wr.close();
			}

			if (soc != null)
			{
				soc.close();
			}
		}
		catch (Exception e)
		{
			System.out.println("Oops! Error 3");
		}
	}
}
