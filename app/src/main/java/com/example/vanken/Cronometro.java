package com.example.vanken;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;


public class Cronometro implements Runnable{

    private TextView etiqueta;
    private String nombrecronometro;
    private int segundos, minutos, horas;
    private Handler escribirenUI;
    private Boolean pausado;
    private String salida;

    public Cronometro(String nombre, TextView etiqueta)
    {
        this.etiqueta = etiqueta;
        this.salida = "";
        this.segundos = 0;
        this.minutos = 0;
        this.horas = 0;
        this.nombrecronometro = nombre;
        this.escribirenUI = new Handler();
        this.pausado = Boolean.FALSE;
    }

    @Override
    public void run() {
        try
        {
            while(Boolean.TRUE)
            {
                Thread.sleep(998);
                salida = "";
                if( !pausado )
                {
                    segundos++;
                    if(segundos == 60)
                    {
                        segundos = 0;
                        minutos++;
                    }
                    if(minutos == 60)
                    {
                        minutos = 0;
                        horas++;
                    }
                    // Formateo la salida
                    salida += "0";
                    salida += horas;
                    salida += ":";
                    if( minutos <= 9 )
                    {
                        salida += "0";
                    }
                    salida += minutos;
                    salida += ":";
                    if( segundos <= 9 )
                    {
                        salida += "0";
                    }
                    salida += segundos;
                    // Modifico la UI
                    try
                    {
                        escribirenUI.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                etiqueta.setText(salida);
                            }
                        });
                    }
                    catch (Exception e)
                    {
                        Log.i("Cronometro", "Error en el cronometro " + nombrecronometro + " al escribir en la UI: " + e.toString());
                    }
                }
            }
        }
        catch (InterruptedException e)
        {
            Log.i("Cronometro", "Error en el cronometro " + nombrecronometro + ": " + e.toString());
        }
    }

    public void reiniciar()
    {
        segundos = 0;
        minutos = 0;
        horas = 0;
        pausado = Boolean.FALSE;
    }

    public void pause()
    {
        pausado = !pausado;
    }
}
