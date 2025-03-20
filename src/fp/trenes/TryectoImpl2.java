	package fp.trenes;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fp.utiles.Checkers;

public class TryectoImpl2{
	
	private String codigoTren;
	private String nombreTrayecto;
	private TipoTren tipo;
	private List<Parada> paradas;
	
	
	public TryectoImpl2(String codigoTren, String nombreTrayecto, TipoTren tipo, String origen, String destino,
			List<Parada> paradas, LocalTime horaSalida, LocalTime horaLlegada) {
	
		
		Checkers.check("El código del tren no está formado por 5 dígitos", esCodigoTrenOk(codigoTren));
		this.codigoTren = codigoTren;
		this.nombreTrayecto = nombreTrayecto;
		this.tipo = tipo;
		Parada estacionInicial = new Parada(origen, horaSalida, null);
		Parada estacionFinal = new Parada(destino, null, horaLlegada);
		paradas = new LinkedList<Parada> ();
		paradas.add(estacionInicial);
		paradas.add(estacionFinal);
		
	}
	
	private Boolean esCodigoTrenOk(String codigo) {
		return codigo.length() == 5 && Validators.sonDigitos(codigo);
	}

	public String getCodigoTren() {
		return codigoTren;
	}


	public String getNombreTrayecto() {
		return nombreTrayecto;
	}


	public TipoTren getTipo() {
		return tipo;
	}

	public List<Parada> getParadas() {
		return paradas;
	}
	
	
	public LocalTime getHoraSalida() {
		return paradas.getFirst().horaSalida();
	}

	public LocalTime getHoraLlegada() {
		return paradas.getLast().horaLlegada();
	}
	
	public List<String> getEstaciones() {
		List<String> estaciones= new ArrayList<>();
		for(Parada p: paradas) {
			estaciones.add(p.estacion());
		}
		return estaciones;

	}
	
	public List<LocalTime> getHorasSalida() {
		List<LocalTime> res = new LinkedList<>();
		for (Parada p : paradas) {
			res.add(p.horaSalida());
		}
		return res;
	}

	public List<LocalTime> getHorasLlegada() {
		List<LocalTime> res = new LinkedList<>();
		for (Parada p : paradas) {
			res.add(p.horaLlegada());
		}
		return res;
	}
	
	public Duration getDuracion() {
		return Duration.between(getHoraSalida(), getHoraLlegada());
	}
	
	public void anadirEstacionIntermedia(int posicion, String estacion, LocalTime horaLlegada, LocalTime horaSalida) {
		
		Checkers.check("La posicion intermedia tiene que estar entre 1 y n", posicion>0 && posicion < paradas.size());
		Checkers.check("La hora de salida tiene que ser posterior a la de llegada", horaSalida.isAfter(horaLlegada));
		Checkers.check("La hora de llegada tiene que ser posterior a hora salida de la estacion anterior", horaLlegada.isAfter(paradas.get(posicion-1).horaSalida()));
		Checkers.check("La hora de salida es anterior a la de la hora de llegada de la siguiente estación", horaSalida.isBefore(paradas.get(posicion).horaLlegada()));
		Parada p= new Parada(estacion,horaLlegada,horaSalida);
		paradas.add(p);

	}


	public void eliminarEstacionIntermedia(String estacion) {
		Checkers.check("La estacion es la ultima o la primera",
				(estacion != getEstaciones().getFirst() && estacion != getEstaciones().getLast()));
		
		int posicion = getEstaciones().indexOf(estacion);
		paradas.remove(posicion);

	}
	

	public int hashCode() {
		return getNombreTrayecto().hashCode() + 31* getHoraSalida().hashCode()
				+31*31* getCodigoTren().hashCode();
	}
	
	public int compareTo(TrayectoTren t) {
		int res= getNombreTrayecto().compareTo(t.getNombre());
		if (res==0) {
			res=getHoraSalida().compareTo(t.getHoraSalida());
		}
		return res;
	}	
	
	
	public boolean equals(Object obj) {
		boolean res=false;
		if(obj instanceof TryectoImpl2) {
			TryectoImpl2 t1= (TryectoImpl2) obj;
			res= getNombreTrayecto().equals(t1.getNombreTrayecto()) && 
					getHoraSalida().equals(t1.getHoraSalida())
					&& getCodigoTren().equals(t1.getCodigoTren());
		}
		return res;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
