package com.example.projekt;

import java.net.Socket;
import java.io.*;
import java.util.*;


/**
 * Used to communicate with the server
 */
public class Sender
{
    public Socket socket;

    public BufferedReader reader;
    public BufferedWriter writer;

    private HelloApplication src;

    public Sender (Socket soc, HelloApplication s)
    {
        src = s;
        try
        {
            socket = soc;

            reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));
        }
        catch (IOException e)
        {
            System.out.println("Server connection error");
            close();
        }
    }

    /**
     * Adds to the enrollment table the passed values
     * @param index - index of the student
     * @param identifier - identifier of the subject
     */
    public void enrollStudent (String index, String identifier)
    {
        try
        {
            sendMessage("es");
            String m = reader.readLine();
            sendMessage(index);
            m = reader.readLine();
            sendMessage(identifier);
            m = reader.readLine();
            src.set_show_mode(0);
            if (!m.equals("ok"))
            {
                src.set_show_mode(1);
                System.out.println(m);
            }
        }
        catch (IOException e)
        {
            System.out.println("Server connection error");
            close();
        }
    }

    /**
     * Returns the List of students to display
     * @return the list of students
     */
    public List<String> showStudents()
    {
        sendMessage("ss");
        return recieveTable();
    }

    /**
     * Returns the List of subjects to display
     * @return the list of subjects
     */
    public List<String> showAllSubjects()
    {
        sendMessage("pp");
        return recieveTable();
    }

    /**
     * Returns the List of subjects and grades to display
     * @return the list of subjects and grades
     */
    public List<String> showEnrolled()
    {
        sendMessage("se");
        return recieveTable();
    }

    /**
     * Returns the List of subjects of a student to display
     * @return the list of subjects of a student
     */
    public List<String> showSubjects(String index)
    {
        try
        {
            sendMessage("pl");
            String m = reader.readLine();
            sendMessage(index);
            return recieveTable();
        }
        catch (IOException e)
        {
            System.out.println("Server connection error");
            close();
        }

        return null;
    }

    /**
     * Recieves a list from the server
     * @return the list of Strings
     */
    private List<String> recieveTable()
    {
        List<String> ret = new ArrayList<String>();
        try
        {
            String m = reader.readLine();
            if (m.equals("e"))
            {
                src.set_show_mode(1);
                return ret;
            }
            else if (m.equals("s"))
            {
                src.set_show_mode(-1);
                System.out.println("Database connection error");
                close();
                return ret;
            }

            while (!m.equals("q"))
            {
                ret.add(m);
                m = reader.readLine();
            }


        }
        catch (IOException e)
        {
            System.out.println("Server connection error");
            close();
        }
        return ret;
    }

    /**
     * Adds to the student table the passed values
     * @param index - index of the student
     * @param name - name of the student
     * @param surname - surname of the student
     */
    public void createStudent (String index, String name, String surname)
    {
        try
        {
            sendMessage("as");
            String m = reader.readLine();
            sendMessage(index);
            m = reader.readLine();
            sendMessage(name);
            m = reader.readLine();
            sendMessage(surname);
            m = reader.readLine();
            src.set_show_mode(0);
            if (!m.equals("ok"))
            {
                src.set_show_mode(1);
                System.out.println(m);
            }
        }
        catch (IOException e)
        {
            System.out.println("Server connection error");
            close();
        }
    }

    /**
     * Removes from the table
     * @param mode - specifies what to delete
     *             e - enrollment of a student to a subject
     *             s - student
     *             p - subject
     *             o - grade
     * @param primIndex - index of the student in e, s and o and identifier of a subject in p
     * @param secIndex - identifier of a subject in e and o
     */
    public void delete (String mode, String primIndex, String secIndex)
    {
        try
        {
            sendMessage(mode);
            if (mode.equals("s")) //delete student
            {
                String m = reader.readLine();
                sendMessage(primIndex);
                m = reader.readLine();
                src.set_show_mode(0);
                if (!m.equals("ok"))
                {
                    src.set_show_mode(1);
                    System.out.println(m);
                }

            }
            else if (mode.equals("p")) //delete przedmiot
            {
                String m = reader.readLine();
                sendMessage(primIndex);
                m = reader.readLine();
                src.set_show_mode(0);
                if (!m.equals("ok"))
                {
                    src.set_show_mode(1);
                    System.out.println(m);
                }
            }
            else if (mode.equals("e")) //delete enrollment
            {
                String m = reader.readLine();
                sendMessage(primIndex);
                m = reader.readLine();
                sendMessage(secIndex);
                m = reader.readLine();
                src.set_show_mode(0);
                if (!m.equals("ok"))
                {
                    src.set_show_mode(1);
                    System.out.println(m);
                }

            }
            else if (mode.equals("o")) //delete ocena
            {
                String m = reader.readLine();
                sendMessage(primIndex);
                m = reader.readLine();
                sendMessage(secIndex);
                m = reader.readLine();
                src.set_show_mode(0);
                if (!m.equals("ok"))
                {
                    src.set_show_mode(1);
                    System.out.println(m);
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Server connection error");
            close();
        }
    }

    /**
     * Adds to the enrollment table the grade to a proper student and subject
     * @param index - index of the student
     * @param identifier - identifier of the subject
     * @param grade - grade of the student
     */
    public void gradeStudent (String index, String identifier, String grade)
    {
        try
        {
            sendMessage("gs");
            String m = reader.readLine();
            sendMessage(index);
            m = reader.readLine();
            sendMessage(identifier);
            m = reader.readLine();
            sendMessage(grade);
            m = reader.readLine();
            src.set_show_mode(0);
            if (!m.equals("ok"))
            {
                src.set_show_mode(1);
                System.out.println(m);
            }
        }
        catch (IOException e)
        {
            System.out.println("Server connection error");
            close();
        }
    }

    /**
     * Adds to the subject table a new subject
     * @param name - index of the student
     * @param id - identifier of the subject
     */
    public void createSubject (String id, String name)
    {
        try
        {
            sendMessage("ap");
            String m = reader.readLine();
            sendMessage(id);
            m = reader.readLine();
            sendMessage(name);
            m = reader.readLine();
            src.set_show_mode(0);
            if (!m.equals("ok"))
            {
                src.set_show_mode(1);
                System.out.println(m);
            }
        }
        catch (IOException e)
        {
            System.out.println("Server connection error");
            close();
        }
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
            System.out.println("Server connection error");
            close();
        }
    }

    /**
     * Closes open communication with server and ends program with 1
     */
    private void close ()
    {
        src.set_show_mode(-1);
        Closer.shutdown(reader, writer, socket);
        System.exit(1);
    }
}
