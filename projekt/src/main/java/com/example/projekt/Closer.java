package com.example.projekt;

import java.net.Socket;
import java.io.*;
import java.util.*;

public class Closer
{
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
