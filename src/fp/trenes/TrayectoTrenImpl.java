package fp.trenes;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fp.utiles.Checkers;


public class TrayectoTrenImpl implements TrayectoTren {
	//Atributos
	
	private String codigoTren;
	private String nombreTrayecto;
	private TipoTren tipo;
	private List<String> estaciones;
	private List <LocalTime> horasSalida;
	private List <LocalTime> horasLlegada;
	
	
	
	public TrayectoTrenImpl(String codigoTren, String nombreTrayecto, TipoTren tipo, 
			String estacionOrigen, String estacionDestino, LocalTime horaSalida, 
			LocalTime horaLlegada) {
		
		Checkers.check("El código del tren no está formado por 5 dígitos", codigoTren.length() == 5
				&& Validators.sonDigitos(codigoTren));
		this.codigoTren = codigoTren;
		this.nombreTrayecto = nombreTrayecto;
		this.tipo = tipo;
		this.estaciones = new ArrayList<>();
		estaciones.add(estacionOrigen);
		estaciones.add(estacionDestino);
		
		this.horasSalida = new ArrayList<>();
		Checkers.checkNoNull(horaSalida, horaLlegada);
		Checkers.check("La hora de salida debe ser anterior a la hora de llegada", horaSalida.isBefore(horaLlegada));
		horasSalida.add(horaSalida);
		this.horasLlegada = new ArrayList<>();
		horasLlegada.add(horaLlegada);
		
		
	}

	@Override
	public String getCodigoTren() {
		return codigoTren;
	}

	@Override
	public String getNombre() {
		return nombreTrayecto;
	}

	@Override
	public TipoTren getTipo() {
		return tipo;
	}

	@Override
	public List<String> getEstaciones() {
		return estaciones;
	}

	@Override
	public List<LocalTime> getHorasSalida() {
		return horasSalida;
	}

	@Override
	public List<LocalTime> getHorasLlegada() {
		return horasLlegada;
	}

	@Override
	public LocalTime getHoraSalida() {
		return horasSalida.getFirst();
	}

	@Override
	public LocalTime getHoraLlegada() {
		return horasLlegada.get(horasLlegada.size()-1);
	}

	@Override
	public Duration getDuracion() {
		return Duration.between(getHoraSalida(), getHoraLlegada());
	}

	@Override
	public LocalTime getHoraSalida(String estacion) {
		LocalTime res = null;
		int pos = estaciones.indexOf(estacion);
		if (pos >= 0) {
			res = horasSalida.get(pos);
		}
		return res;
	}

	@Override
	public LocalTime getHoraLlegada(String estacion) {
		LocalTime res = null;
		int pos = estaciones.indexOf(estacion);
		if (pos >= 0) {
			res = horasLlegada.get(pos);
		}
		return res;
	}

	@Override
	public void anadirEstacionIntermedia(int posicion, String estacion, LocalTime horaLlegada, LocalTime horaSalida) {
		Checkers.check("La posicion intermedia no está entre 1 y n",  posicion>0 && posicion < estaciones.size());
		Checkers.check("La hora de salida no es posterior a la de llegada", horaSalida.isAfter(horaLlegada));
		Checkers.check("La hora de llegada tiene que ser posterior a hora salida de la estacion anterior",
				horaLlegada.isAfter(horasSalida.get(posicion - 1)));
		Checkers.check("Hora de salida tiene que ser posterior a la hora de llegada siguiente",
				horaSalida.isBefore(horasLlegada.get(posicion)));
		estaciones.add(posicion,estacion);
		horasLlegada.add(posicion,horaLlegada);
		horasSalida.add(posicion,horaSalida);

	}

	@Override
	public void eliminarEstacionIntermedia(String estacion) {
		Checkers.check("La estacion no está en el trayecto", !(estacion.equals(estaciones.getFirst())));
		Checkers.check("No puede ser la última estación", !(estacion.equals(estaciones.getLast())));
		Checkers.check("La estacion no está", estaciones.contains(estacion));
		estaciones.remove(estacion);

	}

	@Override
	public int compareTo(TrayectoTren t) {
		int res = this.getNombre().compareTo(t.getNombre());
		if (res == 0) {
			res = this.getHoraSalida().compareTo(t.getHoraSalida());
			if (res == 0) {
				res = this.getCodigoTren().compareTo(t.getCodigoTren());
				}
			}
		return res;
	}
	
	public boolean equals(Object obj) {
		
		boolean res=false;
		if(obj instanceof TrayectoTrenImpl) {
			TrayectoTrenImpl t1= (TrayectoTrenImpl) obj;
			res= getNombre().equals(t1.getNombre()) && 
					getHoraSalida().equals(t1.getHoraSalida())
					&& getCodigoTren().equals(t1.getCodigoTren());
		}
		return res;
	}
	
	public int hashCode() {
		return getNombre().hashCode() + 31* getHoraSalida().hashCode()
				+31*31* getCodigoTren().hashCode();
	}
	
	private String auxTostring(List<String> estaciones2, List<LocalTime> horasSalida2, List<LocalTime> horasLlegada2) {
	    String res = "";
	    for (int i = 0; i < estaciones2.size(); i++) {
	        res += estaciones2.get(i) + "    " + horasSalida2.get(i) + "     " + horasLlegada2.get(i) + "\n";
	    }
	    return res;
	    
	}

	public String toString() {

		return nombreTrayecto + "-" + tipo + " " + "(" + codigoTren + ")\n" + auxTostring(estaciones, horasSalida, horasLlegada);
	}
}
