package ar.edu.unlam.dominio;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ar.edu.unlam.interfaces.AccesorioClinico;

public class Reserva {

	private static Integer proximoId = 0;
	private Integer id;
	private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaYHoraInicio;
    private LocalDateTime fechaYHoraFinal;
    private Set<AccesorioClinico> accesoriosUsados = new HashSet(); //coleccion de accesorios que debe usar en la consulta
    private Integer costoConsulta; //costo de la consulta proviene del copago
    
    public Reserva (Paciente paciente,Medico medico ,LocalDateTime fechaYHora, Integer costoConsulta) {
    	this.paciente = paciente;
    	this.medico = medico;
    	this.fechaYHoraInicio = fechaYHora;
    	this.fechaYHoraFinal = fechaYHora.plusMinutes(15); // PARA QUE EL TURNO DURE 15 MINUTOS
    	this.id = ++proximoId; // ID AUTOINCREMENTAL. PARA BUSCAR POR ID LA RESERVA. 
    	this.accesoriosUsados = new HashSet<>();
    	this.costoConsulta = costoConsulta;
    }
    
    public Reserva (Paciente paciente,Medico medico ,LocalDateTime fechaYHora, Set<AccesorioClinico> accesoriosRequeridos, Integer costoConsulta) {
    	this.paciente = paciente;
    	this.medico = medico;
    	this.fechaYHoraInicio = fechaYHora;
    	this.id = ++proximoId; // ID AUTOINCREMENTAL. PARA BUSCAR POR ID LA RESERVA. 
    	this.accesoriosUsados = accesoriosRequeridos != null ? accesoriosRequeridos : new HashSet<>();
    	this.costoConsulta = costoConsulta;
    	this.fechaYHoraFinal = fechaYHora.plusMinutes(15); // PARA QUE EL TURNO DURE 15 MINUTOS
    }

	public Integer getId() {
		return id;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public Medico getMedico() {
		return medico;
	}

	public LocalDateTime getFechaYHoraInicio() {
		return fechaYHoraInicio;
	}
	
	
	public LocalDateTime getFechaYHoraFinal() {
		return fechaYHoraFinal;
	}
	
	public Integer calcularCostoFinal() {
        int total = this.costoConsulta;
        for (AccesorioClinico accesorio : accesoriosUsados) {
            total += accesorio.getCosto();
        }
        return total;
    }

	


	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        
        if((obj == null) || (obj.getClass() != this.getClass())) return false;

        Reserva reserva = (Reserva) obj;
        return Objects.equals(paciente, reserva.paciente)
        		&& Objects.equals(medico, reserva.medico)
        		&& Objects.equals(fechaYHoraInicio, reserva.fechaYHoraInicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paciente, medico, fechaYHoraInicio);
    }
    
    
    
}