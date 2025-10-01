package ar.edu.unlam.dominio;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reserva {

	private static Integer proximoId = 0;
	private Integer id;
	private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaYHora;
    
    public Reserva (Paciente paciente,Medico medico ,LocalDateTime fechaYHora) {
    	this.paciente = paciente;
    	this.medico = medico;
    	this.fechaYHora = fechaYHora;
    	this.id = ++proximoId; // ID AUTOINCREMENTAL. PARA BUSCAR POR ID LA RESERVA. 	
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

	public LocalDateTime getFechaYHora() {
		return fechaYHora;
	}
    
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        
        if((obj == null) || (obj.getClass() != this.getClass())) return false;

        Reserva reserva = (Reserva) obj;
        return Objects.equals(paciente, reserva.paciente)
        		&& Objects.equals(medico, reserva.medico)
        		&& Objects.equals(fechaYHora, reserva.fechaYHora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paciente, medico, fechaYHora);
    }
    
    
    
}
