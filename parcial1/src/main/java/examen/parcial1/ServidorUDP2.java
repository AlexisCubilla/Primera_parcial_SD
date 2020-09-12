package examen.parcial1;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServidorUDP2 {
	
	ArrayList<Datos> list;
	Integer puertoServidor;
	DatagramSocket serverSocket;
    byte[] Datosrecibidos;
    byte[] Datosenviados;
	
	public ServidorUDP2( Integer serverPort)
	{
		list = new ArrayList<Datos>();
		puertoServidor = serverPort;
		try {
			serverSocket = new DatagramSocket(puertoServidor);
		}catch( SocketException e){
			System.err.println(e);
		}

		Datosrecibidos = new byte[1024];
		Datosenviados = new byte[1024];
	}
	
	public static void main(String[] args)throws Exception
	{
		ServidorUDP2 servidor = new ServidorUDP2( 6666 );
		
		while( true )
		{
			
			servidor.Datosrecibidos = new byte[1024];
	
	        DatagramPacket receivePacket = new DatagramPacket(servidor.Datosrecibidos, servidor.Datosrecibidos.length);
	
	        System.out.println("Esperando a algun cliente... ");
	
	        // 4) Receive LLAMADA BLOQUEANTE
	        try {
	        	servidor.serverSocket.receive(receivePacket);
	        }catch(IOException e){
	        	System.err.println(e);
	        }
	        
			System.out.println("________________________________________________");
	        System.out.println("Aceptamos un paquete");
	        
	        // Datos recibidos e Identificamos quien nos envio
	        InetAddress IPAddress = receivePacket.getAddress();
	        int port = receivePacket.getPort();
	        
	        Datos dat = new Datos();
	        String datoRecibido = new String(receivePacket.getData());
	        System.out.println(datoRecibido);
	        dat.toDatos( datoRecibido.trim() );
	        Integer operacion = Integer.parseInt( dat.getOperacion() );
	        
			if ( operacion.equals(1) )
			{
				servidor.list.add(dat);
				dat = new Datos("1");
				servidor.envio( dat , IPAddress , port);
				System.out.println( "datos metereologicos recibido operacion 1 ");
				
			}
			else if ( operacion.equals(2) )
			{
				String ciudad = dat.getCiudad();
				Datos res = new Datos();
			    for(Datos m : servidor.list) {
			        if ( m.getCiudad().equals(ciudad) ) {
			            res = m;
			        }
			    }
			    dat = new Datos("2");
			    dat.setTemperatura( res.getTemperatura() );
			    dat.setCiudad( res.getCiudad() );
			    servidor.envio( dat , IPAddress, port);
			    System.out.println( "datos metereologicos enviado operacion 2 ");
			}
			else if( operacion.equals(3) )
			{
				Integer temperatura = 0;
				Integer contador = 0;
				String tempPromedio;
				String fecha = dat.getfecha();
				
			    for(Datos m : servidor.list) {
			        if ( m.getfecha().equals(fecha) ) {
			            temperatura = temperatura + Integer.parseInt( m.getTemperatura() );
			            contador++;
			        }
			    }
			    
			    tempPromedio = Integer.toString( temperatura / contador );
			    
			    dat = new Datos("3");
			    dat.setTemperatura( tempPromedio );
			    dat.setfecha( fecha );
			    servidor.envio(dat , IPAddress, port);
			    System.out.println( "datos metereologicos enviado operacion 3 ");
			}
			else
			{
				System.out.println("Error de operacion");
			}
	        
		}
		
	}
	public void envio( Datos dat, InetAddress IPAddress , int port )
	{
		Datosenviados = dat.toJSON().getBytes();
        DatagramPacket sendPacket = new DatagramPacket(Datosenviados, Datosenviados.length, IPAddress, port);
        
        try {
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}