	package fp.trenes;

import java.time.LocalTime;

import fp.utiles.Checkers;


public record Parada(String estacion, LocalTime horaSalida, LocalTime horaLlegada) {
	
	
	public Parada {
		Checkers.check("La hora de salida no es anterior a la de llegada",
				horaSalida == null && horaLlegada!=null ||
				horaLlegada == null && horaSalida!= null ||
				horaSalida != null && horaLlegada!= null && 
				horaLlegada.isBefore(horaSalida)); 
		
	}

}


