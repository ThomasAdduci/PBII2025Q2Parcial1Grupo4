package ar.edu.unlam.dominio;

import java.time.LocalDateTime;

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
    
    
    
    
    
}
