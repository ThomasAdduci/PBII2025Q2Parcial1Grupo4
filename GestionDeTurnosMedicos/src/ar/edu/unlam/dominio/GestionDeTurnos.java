package ar.edu.unlam.dominio;

import java.time.LocalDateTime;
import java.util.HashSet;

public class GestionDeTurnos {

	private HashSet<Reserva> listadoDeReservas;
	private HashSet<Medico> listadoDeMedicos;
	private HashSet<Paciente> listadoDePacientes;

	public GestionDeTurnos() {
		this.listadoDeReservas = new HashSet<>();
		this.listadoDeMedicos = new HashSet<>();
		this.listadoDePacientes = new HashSet<>();
	}

	public Boolean agregarPacienteAlSistema(Paciente clienteNuevo) {
		return this.listadoDePacientes.add(clienteNuevo);

	}

	public Boolean agregarMedicoAlSistema(Medico medicoNuevo) {
		return this.listadoDeMedicos.add(medicoNuevo);
	}

	public Integer obtenerCantidadDeReservas() {
		return this.listadoDeReservas.size();
	}

	public Integer obtenerCantidadDeReservasPorPacientes(Paciente paciente) {
		return this.buscarReservasPorPaciente(paciente).size();
	}

//	 COMENTO ESTE CODIGO ASI MODIFICO LOS TEST CON EL RESERVA QUE VA
//	public Boolean reservarUnTurno(Reserva reservaNueva) {
//		/*
//		 * HACER METODO DE VALIDAR LA RESERVA O ALGO ASI. 1) VALIDAR QUE LA EL HORARIO
//		 * NO SEA ANTES DE LAS 8 NI DESPUES DE LAS 17 2) VALIDAR QUE EL CLIENTE NO TENGA
//		 * UNA RESERVA ESE DIA Y ESE HORARIO CON OTRO MEDICO 3) VALIDAR QUE EL MEDICO NO
//		 * TENGA UNA RESERVAE ESE DIA Y ESE HORARIO CON OTRO PACIENTE
//		 */
//
//		// 1)
//		Integer hora = reservaNueva.getFechaYHora().getHour();
//		if (hora < 8 || hora > 17) {
//			return false;
//		}
//
//		// 2)
//		for (Reserva reserva : listadoDeReservas) {
//			if (reserva.getPaciente().equals(reservaNueva.getPaciente())
//					&& reserva.getFechaYHora().equals(reservaNueva.getFechaYHora())
//					&& !reserva.getMedico().equals(reservaNueva.getMedico())) {
//				return false;
//			}
//		}
//
//		// 3)
//		for (Reserva reserva : listadoDeReservas) {
//			if (reserva.getMedico().equals(reservaNueva.getMedico())
//					&& reserva.getFechaYHora().equals(reservaNueva.getFechaYHora())
//					&& !reserva.getPaciente().equals(reservaNueva.getPaciente())) {
//				return false;
//			}
//		}
//
//		// paso todas las validaciones, agregamos la nueva reserva.
//		return this.listadoDeReservas.add(reservaNueva);
//	}

	/*
	 * ES EL MISMO METODO QUE EL DE ARRIBA , NADA MAS QUE ABAJO AGREGUE OTROS
	 * METODOS CON LAS VALIDACIONES Y LOS IMPLEMENTE EN ESTE TEST. ADEMAS LE AGREGUE
	 * QUE SI EL PACIENTE NO ESTA REGISTRADO EN EL SISTEMA QUE DEVUELVA FALSO A
	 * REALIZAR LA RESERVA
	 */

	public Boolean reservarUnTurno(Reserva reservaNueva) {
// LO POBRE EN EL TEST 7 
		if (!buscarPacienteEnElSistema(reservaNueva.getPaciente()) || (!validarHoraDeLasReservas(reservaNueva))
				|| (!validarQueElPacienteNoTengaOtraReservaEnElMismoHorarioYFecha(reservaNueva))
				|| (!validarQueElMedicoNoTengaOtraReservaEnElMismoHorarioYFecha(reservaNueva))
				|| (!validarHorarioDelturno(reservaNueva.getFechaYHoraInicio()))) {
			return false;
		}

		// paso todas las validaciones, agregamos la nueva reserva.
		return this.listadoDeReservas.add(reservaNueva);
	}

	public Boolean buscarPacienteEnElSistema(Paciente paciente) {

		return this.listadoDePacientes.contains(paciente);
	}

	public Boolean validarHoraDeLasReservas(Reserva reservaNueva) {

		LocalDateTime fechaYHora = reservaNueva.getFechaYHoraInicio();
		Integer hora = fechaYHora.getHour();
		if (hora < 8 || hora > 17) {
			return false;
		}
		return true;

	}

//	 METODO PARA QUE VALIDE QUE EL TURNO DURE 15 MINUTOS 
	public Boolean validarHorarioDelturno(LocalDateTime fechaYHora) {

		for (Reserva reserva : listadoDeReservas) {
			if (reserva.getFechaYHoraInicio().toLocalDate().equals(fechaYHora.toLocalDate())) {
				if (fechaYHora.isBefore(reserva.getFechaYHoraFinal())) {
					return false;
				}
			}
			// SI LA HORA NUEVA ES ANTERIOR A LA HORA DE FINALIZACION DEL TURNO DEVUELVE
			// FALSE..
			// TOLOCALDATE SIRVE PARA QUE TOME SOLO EL LOCALDATE (LA FECHA)
			// ENTONCES COMPARA PARA Q VALIDE SI ES EL MISMO DIA
		}
		return true;
	}

	public Boolean validarQueElPacienteNoTengaOtraReservaEnElMismoHorarioYFecha(Reserva reservaNueva) {

		for (Reserva reserva : listadoDeReservas) {
			if (reserva.getPaciente().equals(reservaNueva.getPaciente())
					&& reserva.getFechaYHoraInicio().equals(reservaNueva.getFechaYHoraInicio())
					&& !reserva.getMedico().equals(reservaNueva.getMedico())) {
				return false;
			}
		}
		return true;
	}

	public Boolean validarQueElMedicoNoTengaOtraReservaEnElMismoHorarioYFecha(Reserva reservaNueva) {

		for (Reserva reserva : listadoDeReservas) {
			if (reserva.getMedico().equals(reservaNueva.getMedico())
					&& reserva.getFechaYHoraInicio().equals(reservaNueva.getFechaYHoraInicio())
					&& !reserva.getPaciente().equals(reservaNueva.getPaciente())) {
				return false;
			}
		}

		return true;
	}

	// PARA CANCELA EL TURNO POR RESERVA
	public Boolean cancelarReserva(Reserva reserva) {
		Reserva reservaACancelar = buscarReservaPorId(reserva.getId());

		if (reservaACancelar == null)
			return false;

		return this.listadoDeReservas.remove(reservaACancelar);

	}

	// CANCELAR TURNO POR PACIENTE Y FECHA Y HORA
	public Boolean cancelarReserva(Paciente paciente, LocalDateTime fechaYHora) {
		Boolean seCancelo = false;
		Reserva reservaACancelar = buscarReservaPorPaciente(paciente, fechaYHora);

		if (reservaACancelar != null) {
			this.listadoDeReservas.remove(reservaACancelar);
			seCancelo = true;
		}
		return seCancelo;
	}

	// BUSCAR RESERVA POR PACIENTE
	public Reserva buscarReservaPorPaciente(Paciente paciente, LocalDateTime fechaYHora) {
		for (Reserva reservas : listadoDeReservas) {
			if (reservas.getPaciente().equals(paciente) && reservas.getFechaYHoraInicio().equals(fechaYHora)) {
				return reservas;
			}
		}
		return null;
	}

	// METODO PARA BUSCAR RESERVA POR ID .
	public Reserva buscarReservaPorId(Integer id) {
		for (Reserva reservas : listadoDeReservas) {
			if (reservas.getId().equals(id))
				return reservas;
		}
		return null;
	}

	// BUSCAR PACIENTE POR DNI
	public Paciente buscarPacientePorDni(Integer dni) {
		for (Paciente paciente : listadoDePacientes) {
			if (paciente.getDni().equals(dni)) {
				return paciente;
			}
		}
		return null;
	}

	// BUSCAR LISTADO DE RESERVAS POR PACIENTE
	public HashSet<Reserva> buscarReservasPorPaciente(Paciente paciente) {

		HashSet<Reserva> reservasEncontradas = new HashSet<>();

		for (Reserva reservas : listadoDeReservas) {
			if (reservas.getPaciente().equals(paciente)) {
				reservasEncontradas.add(reservas);
			}
		}

		return reservasEncontradas;

	}

	// BUSCAR PACIENTE POR RESERVA
//	public Paciente buscarPacientePorReserva(Paciente paciente) {
//		for (Reserva reservas : listadoDeReservas)
//			if (reservas.getPaciente().equals(paciente)) {
//				return reservas.getPaciente();
//			}
//
//		return null;
//	}

	// OBTENER FECHAYHORA ( No se si sirve este metodo)
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
