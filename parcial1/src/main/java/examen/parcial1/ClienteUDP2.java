package examen.parcial1;

import java.io.*;
import java.net.*;

	public class ClienteUDP2 {
		
		String direccion_Servidor;
		Integer puerto_Servidor;
		DatagramSocket client_Socket;
		InetAddress direccionIP;
		BufferedReader inFromUser;
		byte[] sendData;
		byte[] receiveData;
		
		public ClienteUDP2( String serverAddress, Integer serverPort ) {
			inicializar( serverAddress , serverPort );
		}
	    
	    public void inicializar( String serverAddress, Integer serverPort )
	    {
	    	try {
	    	direccion_Servidor = serverAddress;
	    	puerto_Servidor = serverPort;
	    	client_Socket = new DatagramSocket();
	    	direccionIP = InetAddress.getByName(direccion_Servidor);
	    	inFromUser = new BufferedReader(new InputStreamReader(System.in));
	    	sendData = new byte[1024];
	        receiveData = new byte[1024];
	    	} catch (UnknownHostException ex) {
	             System.err.println(ex);
	    	} catch (SocketException e) {
	    		System.err.println(e);
			}
	  
	    }
		
		
		public static void main(String[] args)
		{
			ClienteUDP2 cliente = new ClienteUDP2("localhost" , 6666);
	
			while ( true )
			{
				try {
					cliente.envio();
					cliente.recibir();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			}
			
		}
		public  void envio() throws IOException
		{
			try {
				Datos dat;
				System.out.println("Ingrese la operacion");
				String in = inFromUser.readLine();
				Integer operation = Integer.parseInt(in);
				
				if ( operation.equals(1) )
				{
					System.out.println("Ingrese id");
					String id = inFromUser.readLine();
					System.out.println("Ingrese ciudad");
					String ciudad = inFromUser.readLine();
					System.out.println("Ingrese humedad");
					String humedad = inFromUser.readLine();
					System.out.println("Ingrese temperatura");
					String temperatura = inFromUser.readLine();
					System.out.println("Ingrese velocidad del viento");
					String velocidad = inFromUser.readLine();
					System.out.println("Ingrese fecha");
					String fecha = inFromUser.readLine();
					System.out.println("Ingrese hora");
					String hora = inFromUser.readLine();
					
					dat = new Datos( in, id, ciudad, humedad, temperatura, velocidad, fecha, hora);
					
				
			        String datoPaquete = dat.toJSON();
			        sendData = datoPaquete.getBytes();
			        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, direccionIP, puerto_Servidor);
			        client_Socket.send(sendPacket);
				}
				else if ( operation.equals(2) )
				{
					System.out.println("Ingrese ciudad");
					String ciudad = inFromUser.readLine();
					dat = new Datos( in, ciudad);
					

			        String datoPaquete = dat.toJSON();
			        sendData = datoPaquete.getBytes();
			        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, direccionIP, puerto_Servidor);
			        client_Socket.send(sendPacket);
				}
				else if( operation.equals(3) )
				{
					dat= new Datos(in);
					
					
			        String datoPaquete = dat.toJSON();
			        sendData = datoPaquete.getBytes();
			        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, direccionIP, puerto_Servidor);
			        client_Socket.send(sendPacket);
				}
				else
				{
					System.out.println("Error de operacion");
				}
			}catch( IOException e ){
				System.err.println(e);
			}
		     
		}
		
		public void recibir() throws IOException
		{
			 DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	        
	        //client_Socket.setSoTimeout(10000); no necesario

	        try {
	        	Datos dat = new Datos();
	            // ESPERAMOS LA RESPUESTA, BLOQUENTE
	            client_Socket.receive(receivePacket);

	            String respuesta = new String(receivePacket.getData());
	            dat.toDatos( respuesta.trim() );
	            
	            Integer operation = Integer.parseInt( dat.getOperacion() );
	            if( operation.equals(2) )
	            {
	            	System.out.println("Temperatura de la ciudad: " + dat.getCiudad() + "---" + dat.getTemperatura());
	            }
	            else if( operation.equals(3) )
	            {
	            	System.out.println("Temperatura en la fecha: " + dat.getfecha() + "----" + dat.getTemperatura()  );
	            }
	            else
	            {
	            	System.out.println( "Error en mensaje recibido" );
	            }
	            

	        } catch (SocketTimeoutException ste) {

	            System.out.println("TimeOut: El paquete udp se asume perdido.");
	        }
		}
		
		
	}
