package ar.edu.unlam.dominio;

import java.time.LocalDateTime;
import java.util.HashSet;

public class GestionDeTurnos {

	private HashSet <Reserva> listadoDeReservas;
	private HashSet <Medico> listadoDeMedicos;
	private HashSet <Paciente> listadoDePacientes;
	
	public GestionDeTurnos() {
		this.listadoDeReservas = new HashSet <> ();
		this.listadoDeMedicos = new HashSet <> ();
		this.listadoDePacientes = new HashSet <> ();
	}
	
	
	public Boolean agregarPacienteAlSistema(Paciente clienteNuevo) {
		return this.listadoDePacientes.add(clienteNuevo);
	
	}	
	
	public Boolean agregarMedicoAlSistema(Medico medicoNuevo) {
		return this.listadoDeMedicos.add(medicoNuevo);
	}	
	
	
	public Boolean reservarUnTurno(Reserva reservaNueva) { 
		/*	 HACER METODO DE VALIDAR LA RESERVA O ALGO ASI.
		 *  1) VALIDAR QUE LA EL HORARIO NO SEA ANTES DE LAS 8 NI DESPUES DE LAS 17
		 * 2) VALIDAR QUE EL CLIENTE NO TENGA UNA RESERVA ESE DIA Y ESE HORARIO CON OTRO MEDICO
		  3) VALIDAR QUE EL MEDICO NO TENGA UNA RESERVAE ESE DIA Y ESE HORARIO CON OTRO PACIENTE */
		
		//1)
		int hora = reservaNueva.getFechaYHora().getHour();
		if(hora < 8 || hora > 17) {
			return false;
		}
		
		//2)
		for (Reserva reserva : listadoDeReservas) {
			if(reserva.getPaciente().equals(reservaNueva.getPaciente())
					&& reserva.getFechaYHora().equals(reservaNueva.getFechaYHora())
					&& !reserva.getMedico().equals(reservaNueva.getMedico())) {
				return false;
			}
		}
		
		//3)
		for(Reserva reserva : listadoDeReservas) {
			if(reserva.getMedico().equals(reservaNueva.getMedico())
					&& reserva.getFechaYHora().equals(reservaNueva.getFechaYHora())
					&& !reserva.getPaciente().equals(reservaNueva.getPaciente())) {
				return false;
			}
		}
		
		//paso todas las validaciones, agregamos la nueva reserva.
		return this.listadoDeReservas.add(reservaNueva);
	}
	
	
	// PARA CANCELA LA RESERVA
	public Boolean cancelarReserva(Reserva reserva) {
	  Reserva reservaACancelar = buscarReservaPorId(reserva.getId());
	  
	  if (reservaACancelar == null) return false;
	  
	 return  this.listadoDeReservas.remove(reservaACancelar);
	  
	}
	
     //METODO PARA BUSCAR RESERVA POR ID . 
	public Reserva buscarReservaPorId(Integer id) {
		for (Reserva reservas : listadoDeReservas) {
			if (reservas.getId().equals(id))
				return reservas;
		}
		return null;
	}
	
	// OBTENER FECHAYHORA  ( No se si sirve este metodo)
//	
//	public LocalDateTime obtenerFechaYHoraDeReserva(LocalDateTime fechaYhora) {	
//		for  (Reserva reservas : listadoDeReservas){
//			if (reservas.getFechaYHora().isEqual(fechaYhora)) {
//				return reservas.getFechaYHora();
//			}		
//		}
//		return null;
//	}
//	
	

	
	
	
	
	
	
	
	
}




	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

