package ar.edu.unlam.interfaz;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Scanner;

import ar.edu.unlam.dominio.*;
import ar.edu.unlam.interfaces.*;
import ar.edu.unlam.menu.*;

public class GestionDeTurnosInterfaz {

	static Scanner teclado = new Scanner(System.in);
	static Menum opciones[] = Menum.values();
	static Menum opcionSeleccionada = Menum.ALTA_PACIENTE;
	static Plan opcionesPlan[] = Plan.values();
	static Plan opcionSeleccionadaPlan = Plan.COBERTURA_TOTAL;
	static Especialidad tipoEspecialidad = Especialidad.CLINICA;
	static Especialidad[] opcionesEspecialidad = Especialidad.values();
	static GestionDeTurnos osde = new GestionDeTurnos();

	public static void main(String args[]) {
		// PACIENTES QUE YA ESTAN EN LA COLECCIONES. POR SI QUEREMOS HACER LA
		// COMPROBACION DE DNI.
		Paciente paciente1 = new Paciente("Mariana", "Lopez", 30, 38662442, Plan.NO_POSEE);
		Medico medico1 = new Medico("Pablo", "Martinez", 60, 17555892, Especialidad.CLINICA);

		osde.agregarPacienteAlSistema(paciente1);

		mensaje("-----BIENVENIDO AL SISTEMA DE RESERVA DE TURNOS-----");
		mensaje("Ingrese la opcion deseada");

		do {
			mostrarElMensajeMenuDeOpciones(opciones);
			int opcionIngresada = teclado.nextInt();

			if (opcionIngresada > 0 && opcionIngresada <= opciones.length) {
				opcionSeleccionada = opciones[opcionIngresada - 1];
			}

			switch (opcionSeleccionada) {

			case ALTA_PACIENTE:
				teclado.nextLine(); // Para limpiar el buffer
				String nombre = mensajeConTeclado("Ingrese su nombre");
				String apellido = mensajeConTeclado("Ingrese su apellido");
				Integer edad = mensajeConTecladoInt("Ingrese su edad");
				Integer dni = mensajeConTecladoInt("Ingrese su numero de DNI");
				mostrarElMenuDePlanes(opcionesPlan);
				Integer seleccionPlan = mensajeConTecladoInt("Ingrese su cobertura");
				Plan planSeleccionado = opcionesPlan[seleccionPlan - 1];

				Paciente pacienteNuevo = new Paciente(nombre, apellido, edad, dni, planSeleccionado);
				Boolean seAgrego = osde.agregarPacienteAlSistema(pacienteNuevo);

				if (!seAgrego) {
					System.out.println("El DNI: " + dni + " ya está registrado en nuestra Base de Datos");
				} else {
					System.out.println("El paciente " + nombre + ", dni: " + dni
							+ " se ha registrado correctamente en el sistema.");
				}

				break;
			case RESERVAR_TURNO:
				inicializacionDeMedicos();

				Integer numeroDni = mensajeConTecladoInt("Ingrese su numero de DNI");
				Paciente pacienteAReservar = osde.buscarPacientePorDni(numeroDni);

				if (pacienteAReservar == null) {
					System.out
							.println("El paciente con DNI:" + numeroDni + " no se encuentra registrado en el sistema.");
					break;
				}

				mostrarElMenuDeEspecialidad(opcionesEspecialidad);
				Integer opcionEspecialidad = mensajeConTecladoInt("Seleccione la especialidad:");
				Especialidad especialidadSeleccionada = opcionesEspecialidad[opcionEspecialidad - 1];

				HashSet<Medico> medicosEncontrados = osde.buscarMedicosPorEspecialidad(especialidadSeleccionada);

//				if (medicosEncontrados.isEmpty()) {
//					System.out.println("No hay médicos disponibles");
//					break; } 
				// NO SE SI HACE FALTA PORQ TENEMOS A LOS MEDICOS INICIALIZADOS POR ESO LO
				// COMENTÉ

				System.out.println("Los medicos disponibles son :");
				for (Medico medico : medicosEncontrados) {
					System.out.println(medico);
				}

				Integer opcionMedico = mensajeConTecladoInt(
						"Por favor, ingrese el codigo del medico que desea tomar turno:");
				Medico medicoElegido = osde.buscarMedicoPorCodigo(opcionMedico);

				LocalDateTime fechaYHoraTurno = mensajeParaFechaYHora();

				Reserva nuevaReserva = new Reserva(pacienteAReservar, medicoElegido, fechaYHoraTurno,
						pacienteAReservar.getPlan().getCopago());

				Boolean seAgregoReserva = osde.reservarUnTurno(nuevaReserva);

				// ACA VER SI LO DEJAMOS ASI, O VER EL PORQ NO SE RESERVO EL TURNO Y MANDAR MSJ
				// X ESO.
				// TIPO YA TIENE UN TURNO EN ESE HORARIO, O EL MEDICO NO PUEDE O ES FUERA DEL
				// HORARIO PERMITIDO
				if (!seAgregoReserva) {
					System.out.println("No se pudo reservar el turno. Intenté en otro horario ");
				} else {
					System.out.println("Reserva realizada exitosamente");
					System.out.println(pacienteAReservar.getNombre() + " tiene un turno programado con "  + medicoElegido.getNombreCompleto()  + " el día " + fechaYHoraTurno.getDayOfMonth() + " del mes: " + fechaYHoraTurno.getMonthValue() + " a las " + fechaYHoraTurno.getHour() + ":" + fechaYHoraTurno.getMinute());
				}
				break;
				
			case CANCELAR_TURNO:
				
				Integer dniACancelar = mensajeConTecladoInt("Ingrese su número de DNI");
			    Paciente pacienteACancelar = osde.buscarPacientePorDni(dniACancelar);

			    if (pacienteACancelar == null) {
			        System.out.println("El paciente con DNI: " + dniACancelar + " no se encuentra registrado.");
			        break;
			    }

			    HashSet<Reserva> reservasDelPaciente = osde.buscarReservasPorPaciente(pacienteACancelar);

			    if (reservasDelPaciente.isEmpty()) {
			        System.out.println("No se encontraron reservas para el paciente.");
			        break;
			    }

			    System.out.println("Turnos actuales del paciente:");
			    for (Reserva reserva : reservasDelPaciente) {
			        System.out.println("ID: " + reserva.getId() + " | Fecha: " + reserva.getFechaYHoraInicio() +
			            " | Médico: " + reserva.getMedico().getNombreCompleto());
			    }

			    Integer idReserva = mensajeConTecladoInt("Ingrese el ID del turno que desea cancelar:");
			    Reserva reservaSeleccionada = osde.buscarReservaPorId(idReserva);

			    if (reservaSeleccionada != null && reservaSeleccionada.getPaciente().equals(pacienteACancelar)) {
			        Boolean seCancelo = osde.cancelarReserva(reservaSeleccionada);
			        if (seCancelo) {
			            System.out.println("El turno ha sido cancelado exitosamente.");
			        } else {
			            System.out.println("No se pudo cancelar el turno.");
			        }
			    } else {
			        System.out.println("No se encontró una reserva con ese ID para este paciente.");
			    }
				
				break;
			case SALIR:
				System.out.println("Gracias por elegirnos");

			}
		} while (opcionSeleccionada != Menum.SALIR);

	}

	// METODOS
	public static void mensaje(String mensaje) {
		System.out.println(mensaje);
	}

	public static String mensajeConTeclado(String mensaje) {
		System.out.println(mensaje);
		String dato = teclado.nextLine();
		return dato;
	}

	public static Integer mensajeConTecladoInt(String mensaje) {
		System.out.println(mensaje);
		Integer dato = teclado.nextInt();
		return dato;
	}

	public static LocalDateTime mensajeParaFechaYHora() {
		Integer anio = 2025, dia = 0, mes = 0, hora = 0, minuto = -1;

		while (mes < 1 || mes > 12) {
			System.out.print("Ingrese el numero del mes: ");
			mes = teclado.nextInt();
		}

		while (dia < 1 || dia > 31) {
			System.out.print("Ingrese el numero del día: ");
			dia = teclado.nextInt();
		}

		//
		while (hora < 8 || hora > 17) {
			System.out.print("Ingrese hora del turno que desea tomar - entre las 8 y 17 hs- : ");
			hora = teclado.nextInt();
		}

		while (minuto < 0 || minuto > 59) {
			System.out.print("Ingrese los minutos que desea el turno . 00 - 15 - 30 - 45 ");
			minuto = teclado.nextInt();
		}

		return LocalDateTime.of(anio, mes, dia, hora, minuto);

	}

//	ENUM

	public static void mostrarElMensajeMenuDeOpciones(Menum[] opciones) {
		for (Menum opcion : Menum.values()) {
			System.out.println(opcion.obtenerMensaje());
		}
	}

	public static void mostrarElMenuDePlanes(Plan[] opcionesPlan) {
		for (Plan opcionPlan : Plan.values()) {
			System.out.println(opcionPlan.getMensaje());
		}
	}

	public static void mostrarElMenuDeEspecialidad(Especialidad[] opcionesEspecialidad) {
		System.out.println("¿De qué especialidad necesita el turno?");
		for (Especialidad opcionEspecialidad : Especialidad.values()) {
			System.out.println(opcionEspecialidad.obtenerMensaje());
		}
	}

// INICIALIZACION DE MEDICOS Y PACIENTES 
	public static void inicializacionDeMedicos() {
		Medico medico1 = new Medico("Gustavo", "Lopez", 60, 10123456, Especialidad.CARDIOLOGIA);
		Medico medico2 = new Medico("Juan", "Riquelme", 47, 206752869, Especialidad.CARDIOLOGIA);

		Medico medico3 = new Medico("Pablo", "Martinez", 60, 17555892, Especialidad.CLINICA);
		Medico medico4 = new Medico("Ana", "Gomez", 60, 17555892, Especialidad.CLINICA);

		Medico medico5 = new Medico("Lucia", "Fernandez", 40, 10345678, Especialidad.DERMATOLOGIA);
		Medico medico6 = new Medico("Diego", "Sanchez", 42, 10345679, Especialidad.DERMATOLOGIA);

		Medico medico7 = new Medico("Sofia", "Lopez", 36, 33333334, Especialidad.PEDIATRIA);
		Medico medico8 = new Medico("Jorge", "Ramirez", 39, 22222223, Especialidad.PEDIATRIA);

		Medico medico9 = new Medico("Elena", "Torres", 44, 11111112, Especialidad.TRAUMATOLOGIA);
		Medico medico10 = new Medico("Fernando", "Vega", 46, 11111111, Especialidad.TRAUMATOLOGIA);

		osde.agregarMedicoAlSistema(medico1);
		osde.agregarMedicoAlSistema(medico2);
		osde.agregarMedicoAlSistema(medico3);
		osde.agregarMedicoAlSistema(medico4);
		osde.agregarMedicoAlSistema(medico5);
		osde.agregarMedicoAlSistema(medico6);
		osde.agregarMedicoAlSistema(medico7);
		osde.agregarMedicoAlSistema(medico8);
		osde.agregarMedicoAlSistema(medico9);
		osde.agregarMedicoAlSistema(medico10);

	}

}
