package com.example.fakespotify;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class InputDevice {

    InputStream inputStream ;
    Scanner scanner ;
    InputDevice()
    {
        inputStream = System.in ;
        scanner = new Scanner(inputStream) ;
    }
    InputDevice(InputStream inputStream)
    {
        this.inputStream = inputStream ;
        scanner = new Scanner(inputStream) ;
    }
    String GetNextLine()
    {
        return scanner.nextLine() ;
    }

    public void SetInputStream(InputStream newInputStream)
    {
        inputStream = newInputStream ;
        scanner = new Scanner(inputStream) ;
    }
    public void close()
    {
        scanner.close() ;
    }
}

