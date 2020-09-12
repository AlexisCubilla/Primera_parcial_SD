package examen.parcial1;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Datos{
	
	String operacion;
	String id;
	String ciudad;
	String humedad;
	String temperatura;
	String velocidad;
	String fecha;
	String hora;
	
	
	public Datos() {
		this.operacion = "0";
		this.id = "0";
		this.ciudad = "0";
		this.humedad = "0";
		this.temperatura = "0";
		this.velocidad = "0";
		this.fecha = "0";
		this.hora = "0";
	}
	public Datos(String operacion) {
		this.operacion = operacion;
		this.id = "0";
		this.ciudad = "0";
		this.humedad = "0";
		this.temperatura = "0";
		this.velocidad = "0";
		this.fecha = "0";
		this.hora = "0";
	}
	
	public Datos(String operacion, String fecha) {
		this.operacion = operacion;
		this.id = "0";
		this.ciudad = "0";
		this.humedad = "0";
		this.temperatura = "0";
		this.velocidad = "0";
		this.fecha = fecha;
		this.hora = "0";
	}
	
	public Datos(String operacion, String id, String ciudad, String humedad, String temperatura, String velocidad, String fecha, String hora) {
		this.operacion = operacion;
		this.id = id;
		this.ciudad = ciudad;
		this.humedad = humedad;
		this.temperatura = temperatura;
		this.velocidad = velocidad;
		this.fecha = fecha;
		this.hora = hora;
	}
	
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operation) {
		this.operacion = operation;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String city) {
		this.ciudad = city;
	}
	public String getHumedad() {
		return humedad;
	}
	public void setHumedad(String humedad) {
		this.humedad = humedad;
	}
	public String getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(String temp) {
		this.temperatura = temp;
	}
	public String getVelocidad() {
		return velocidad ;
	}
	public void setVelocidad(String velocidad) {
		this.velocidad = velocidad;
	}
	public String getfecha() {
		return fecha;
	}
	public void sefecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	
	public String toJSON( ) {
		JSONObject obj = new JSONObject();
		obj.put("operacion", getOperacion() );
		obj.put("id", getId() );
		obj.put("ciudad", getCiudad() );
		obj.put("humedad", getHumedad() );
		obj.put("temperatura", getTemperatura() );
		obj.put("Velocidad", getVelocidad() );
		obj.put("fecha", getfecha() );
		obj.put("hora", getHora() );


		return obj.toJSONString();
	}
	
	
	public void toDatos(String str) {

		try {
			//parsear
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(str.trim());
			JSONObject jsonObject = (JSONObject) obj;
			//extraer variables
			String operacion = (String)jsonObject.get("operacion");
			String id = (String)jsonObject.get("id");
			String ciudad = (String)jsonObject.get("ciudad");
			String humedad = (String)jsonObject.get("humedad");
			String temperatura = (String)jsonObject.get("temperatura");
			String velocidad = (String)jsonObject.get("velocidad");
			String fecha = (String)jsonObject.get("fecha");
			String hora = (String)jsonObject.get("hora");

			//asignar variables
			this.operacion = operacion;
			this.id = id;
			this.ciudad = ciudad;
			this.humedad = humedad;
			this.temperatura = temperatura;
			this.velocidad = velocidad;
			this.fecha = fecha;
			this.hora = hora;

		}catch (ParseException e) {
			e.printStackTrace();
		}
	}

}