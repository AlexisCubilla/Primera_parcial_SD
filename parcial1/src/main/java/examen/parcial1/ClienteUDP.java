package examen.parcial1;

import java.io.*;
import java.net.*;

	public class ClienteUDP {
		
		String direccion_Servidor;
		Integer puerto_Servidor;
		DatagramSocket client_Socket;
		InetAddress direccionIP;
		BufferedReader inFromUser;
		byte[] sendData;
		byte[] receiveData;
		
		public ClienteUDP( String serverAddress, Integer serverPort ) {
			inicializar( serverAddress , serverPort );
		}
	    
	    public void inicializar( String serverAddress, Integer serverPort )
	    {
	    	try {
	    	direccion_Servidor = serverAddress;
	    	puerto_Servidor = serverPort;
	    	client_Socket = new DatagramSocket();
	    	direccionIP = InetAddress.getByName(direccionServidor);
	    	inFromUser = new BufferedReader(new InputStreamReader(System.in));
	    	sendData = new byte[1024];
	        receiveData = new byte[1024];
	    	} catch (UnknownHostException ex) {
	             System.err.println(ex);
	    	} catch (SocketException e) {
	    		System.err.println(e);
			}
	  
	    }
		public void envio() throws IOException
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
					
					dat = new Datos( in, id, ciudad, humedad, temperatura, velocidad, fecha, temperatura);
					
				
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
					dat= new Datos( in);
					
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
		
		public void receiveMessage() throws IOException
		{
			 DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	        //Vamos a hacer una llamada BLOQUEANTE entonces establecemos un timeout maximo de espera
	        //client_Socket.setSoTimeout(10000); no necesario

	        try {
	        	Datos dat = new Datos();
	            // ESPERAMOS LA RESPUESTA, BLOQUENTE
	            client_Socket.receive(receivePacket);

	            String respuesta = new String(receivePacket.getData());
	            msg.toMessage( respuesta.trim() );
	            
	            Integer operation = Integer.parseInt( msg.getOperation() );
	            if( operation.equals(2) )
	            {
	            	System.out.println("Temperatura en ciudad: " + msg.getCity() + " es igual a: " + msg.getTemp() );
	            }
	            else if( operation.equals(3) )
	            {
	            	System.out.println("Temperatura en fecha: " + msg.getDate() + " es igual a: " + msg.getTemp()  );
	            }
	            else
	            {
	            	System.out.println( "Error en mensaje recibido" );
	            }
	            

	            

	        } catch (SocketTimeoutException ste) {

	            System.out.println("TimeOut: El paquete udp se asume perdido.");
	        }
		}
		
		public void run() 
		{
			Boolean flag = true;
			while ( flag )
			{
				try {
					sendMessage();
					receiveMessage();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			}
		}
		
		public static void main(String[] args)
		{
			Client cliente = new Client("127.0.0.1" , 6666);
			cliente.run();
			
			
		}
	}

}
