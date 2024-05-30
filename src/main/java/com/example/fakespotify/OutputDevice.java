package com.example.fakespotify;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class OutputDevice {
    public OutputStream outputStream ;

    OutputDevice()
    {
        outputStream = null ;
    }

    OutputDevice(OutputStream outputStream)
    {
        this.outputStream = outputStream ;
    }

    public void SetOutputStream(OutputStream outputStream)
    {
        this.outputStream = outputStream ;
    }
    public void Write(String s)
    {
        try {
            outputStream.write(s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Append(String s)
    {

    }
}
