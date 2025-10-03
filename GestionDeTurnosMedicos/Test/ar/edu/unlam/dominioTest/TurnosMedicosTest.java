package ar.edu.unlam.dominioTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import ar.edu.unlam.dominio.*;

public class TurnosMedicosTest {

	private GestionDeTurnos osde;
	// ATRIBUTOS MEDICO
	private Medico medico1;
	private Especialidad tipo1;

	// ATRIBUTOS PACIENTES
	private Paciente paciente1;
	private Paciente paciente2;

	// ATRIBUTOS RESERVA
	private Reserva reserva1;

	@Before
	public void inicializacion() {
		osde = new GestionDeTurnos();

		// MEDICO 1
		String nombreMedico1 = "Pablo";
		String apellidoMedico1 = "Martinez";
		Integer edadMedico1 = 60;
		Integer dniMedico1 = 17555892;
		tipo1 = Especialidad.CLINICA;
		medico1 = new Medico(nombreMedico1, apellidoMedico1, edadMedico1, dniMedico1, tipo1);

		// MEDICO 2

		// PACIENTE 1
		String nombrePaciente1 = "Pedro";
		String apellidoPaciente1 = "Lanzani";
		Integer edadPaciente1 = 33;
		Integer dniPaciente1 = 36888999;
		;

		paciente1 = new Paciente(nombrePaciente1, apellidoPaciente1, edadPaciente1, dniPaciente1,Plan.ESTANDAR);

		// PACIENTE 2
		String nombrePaciente2 = "Camila";
		String apellidoPaciente2 = "Dominguez";
		Integer edadPaciente2 = 37;
		Integer dniPaciente2 = 34999888;

		paciente2 = new Paciente(nombrePaciente2, apellidoPaciente2, edadPaciente2, dniPaciente2,Plan.COBERTURA_TOTAL);

		LocalDateTime fechaHora1 = LocalDateTime.of(2025, 10, 8, 10, 30); // 8 de Octubre 10.30 hs
		reserva1 = new Reserva(paciente1, medico1, fechaHora1);
	}

	@Test
	public void DadoQueExisteUnGestorDeTurnosSeQuiereAgregarUnNuevoPacienteYSeObtieneUnResultadoExitoso() {

		Boolean seAgrego = osde.agregarPacienteAlSistema(paciente1);
		assertTrue(seAgrego);
	}

	@Test
	public void DadoQueExisteUnGestorDeTurnosSeQuiereAgregar2PacientesConElMismoDniYSeObtieneUnResultadoFalso() {
		// PACIENTE 2
		String nombrePacienteNuevo = "Camila";
		String apellidoPacienteNuevo = "Dominguez";
		Integer edadPacienteNuevo = 37;
		Integer dniPacienteNuevo = 36888999; // MISMO DNI QUE PACIENTE 1 INICIALIZADO EN BEFORE.

		Paciente pacienteNuevo = new Paciente(nombrePacienteNuevo, apellidoPacienteNuevo, edadPacienteNuevo,
				dniPacienteNuevo,Plan.ESTANDAR);

		Boolean seAgrego = osde.agregarPacienteAlSistema(paciente1);
		Boolean seAgrego2 = osde.agregarPacienteAlSistema(pacienteNuevo);
		assertTrue(seAgrego);
		assertFalse(seAgrego2);
	}

	@Test

	public void DadoQueExisteUnGestorDeTurnosSeQuiereAgregarUnNuevoMedicoYSeObtieneUnResultadoExitoso() {
		Boolean seAgrego = osde.agregarMedicoAlSistema(medico1);
		assertTrue(seAgrego);
	}

	@Test
	public void DadoQueExisteUnGestorDeTurnosSeQuiereAgregar2MedicosConElMismoDniYSeObtieneUnResultadoFalso() {
		String nombreMedico2 = "Gustavo";
		String apellidoMedico2 = "Machargo";
		Integer edadMedico2 = 55;
		Integer dniMedico2 = 17555892; // MISMO DNI QUE MEDICO 1 QUE ESTA INICIALIZADO EN EL BEFORE.
		Especialidad tipo2 = Especialidad.DERMATOLOGIA;
		Medico medico2 = new Medico(nombreMedico2, apellidoMedico2, edadMedico2, dniMedico2, tipo2);

		Boolean seAgrego = osde.agregarMedicoAlSistema(medico1);
		Boolean seAgrego2 = osde.agregarMedicoAlSistema(medico2);
		assertTrue(seAgrego);
		assertFalse(seAgrego2);
	}

	@Test
	public void dadoQueExisteUnMedicoYSeQuiereRegistrarComoPacienteObtieneResultadoExitoso() {

		// DATOS DEL PACIENTE QUE COINCIDE CON EL MEDICO 1 .
		String nombrePacienteMedico = "Pablo";
		String apellidoPacienteMedico = "Martinez";
		Integer edadPacienteMedico = 60;
		Integer dniPacienteMedico = 17555892;

		Paciente pacienteMedico = new Paciente(nombrePacienteMedico, apellidoPacienteMedico, edadPacienteMedico,
				dniPacienteMedico,Plan.ESTANDAR);

		osde.agregarMedicoAlSistema(medico1);
		Boolean seAgrego = osde.agregarPacienteAlSistema(pacienteMedico);
		assertTrue(seAgrego);

	}

	@Test
	public void dadoQueExisteUnPacienteYQuiereReservarUnTurnoMedicoSeObtieneUnResultadoExitoso() {
		Boolean reservaDeTurno = osde.reservarUnTurno(reserva1);
		assertTrue(reservaDeTurno);
	}

	@Test
	public void dadoQueNoExisteUnClienteRegistradoEnElSistemaYQuiereReservarUnTurnoYObtieneResultadoFalso() {
		// EL PACIENTE NO ESTA AGREGADO AL SISTEMA Y SE  INTENTA HACER UNA RESERVA
		osde.agregarMedicoAlSistema(medico1);
		Boolean seReservo = osde.reservarUnTurno2(reserva1);
		assertFalse(seReservo);

	}

	@Test
	public void dadoQueExisteUnPacienteQuiereReservarUnTurnoFueraDelHorarioEstablecidoSeObtieneUnResultadoFalso() {
		Reserva reservaFueraDeHorario;
		LocalDateTime fechaHoraFueraDeHorario = LocalDateTime.of(2025, 12, 18, 7, 30); // 12 de Diciembre 7.30 hs

		reservaFueraDeHorario = new Reserva(paciente1, medico1, fechaHoraFueraDeHorario);

		Boolean reserva = osde.reservarUnTurno(reservaFueraDeHorario);
		assertFalse(reserva);
	}

	@Test
	public void dadoQueExisteUnClienteYQuiereReservarDosTurnosElMismoDiaYHoraYObtieneResultadoFalso() {
		osde.reservarUnTurno(reserva1); // reservamos la reserva1 que esta inicializada en el before

		LocalDateTime fechaHoraDeReserva = LocalDateTime.of(2025, 10, 8, 10, 30); // misma fecha que reserva1
		Reserva nuevaReserva = new Reserva(paciente1, medico1, fechaHoraDeReserva);

		Boolean reservaDuplicada = osde.reservarUnTurno(nuevaReserva);
		assertFalse(reservaDuplicada);
	}

	// TODO Validar que el medico no tenga un turno con 2 pacientes distintos en la
	// misma fecha y hora.
	
	@Test
	public void dadoQueExisteUnClienteConUnTurnoSolicitadoYLoQuiereCancelarSeObtieneUnResultadoExitoso() {
		osde.agregarPacienteAlSistema(paciente1);
		osde.agregarMedicoAlSistema(medico1);

		osde.reservarUnTurno(reserva1);

		Integer valorEsperado = 1;
		Integer valorObtenido = osde.obtenerCantidadDeReservas();

		assertEquals(valorEsperado, valorObtenido);

		Boolean seCancelo = osde.cancelarReserva(reserva1);
		Integer valorEsperado2 = 0;
		Integer valorObtenido2 = osde.obtenerCantidadDeReservas();

		assertEquals(valorEsperado, valorObtenido);
		assertTrue(seCancelo);
	}

	@Test

	public void dadoQueExisteUnClienteCon3ReservasSeQuiereCancelarLaReservaDosPorFechaYHora() {
		osde.agregarPacienteAlSistema(paciente1);
		osde.agregarMedicoAlSistema(medico1);

		LocalDateTime fechaHora2 = LocalDateTime.of(2025, 10, 10, 9, 00); // 10 de octubre 09:00 hs
		Reserva reserva2 = new Reserva(paciente1, medico1, fechaHora2);

		LocalDateTime fechaHora3 = LocalDateTime.of(2025, 10, 18, 15, 30); // 18 de octubre 15:30 hs
		Reserva reserva3 = new Reserva(paciente1, medico1, fechaHora3);

		LocalDateTime fechaHora4 = LocalDateTime.of(2025, 10, 20, 13, 00); // 20 de octubre 13:00s
		Reserva reserva4 = new Reserva(paciente1, medico1, fechaHora4);

		osde.reservarUnTurno(reserva1);
		osde.reservarUnTurno(reserva2);
		osde.reservarUnTurno(reserva3);

		LocalDateTime fechaYHoraACancelar = LocalDateTime.of(2025, 10, 18, 15, 30);

		Boolean seCancelo = osde.cancelarReserva(paciente1, fechaYHoraACancelar);
		assertTrue(seCancelo);

		Integer valorEsperado = 2;
		Integer valorObtenido = osde.obtenerCantidadDeReservasPorPacientes(paciente1);

	}

	@Test
	public void dadoQueExisteUnClienteCon4ReservasEnElMismoMesSeBuscaLaReservaPorIDYSeObtieneDichaReserva() {
		osde.agregarPacienteAlSistema(paciente1);
		osde.agregarMedicoAlSistema(medico1);

		LocalDateTime fechaHora2 = LocalDateTime.of(2025, 10, 10, 9, 00); // 10 de octubre 09:00 hs
		Reserva reserva2 = new Reserva(paciente1, medico1, fechaHora2);

		LocalDateTime fechaHora3 = LocalDateTime.of(2025, 10, 18, 15, 30); // 18 de octubre 15:30 hs
		Reserva reserva3 = new Reserva(paciente1, medico1, fechaHora3);

		LocalDateTime fechaHora4 = LocalDateTime.of(2025, 10, 20, 13, 00); // 20 de octubre 13:00s
		Reserva reserva4 = new Reserva(paciente1, medico1, fechaHora4);

		osde.reservarUnTurno(reserva1);
		osde.reservarUnTurno(reserva2);
		osde.reservarUnTurno(reserva3);
		osde.reservarUnTurno(reserva4);

		Reserva reservaBuscada = reserva3;
		Reserva reservaObtenida = osde.buscarReservaPorId(reserva3.getId());

		assertEquals(reservaBuscada, reservaObtenida);

	}

	@Test
	public void dadoQueExisteUnClienteCon4ReservasEnDistintosMesesSeBuscaLaCantidadDeReservasTotales() {
		osde.agregarPacienteAlSistema(paciente1);
		osde.agregarMedicoAlSistema(medico1);

		LocalDateTime fechaHora2 = LocalDateTime.of(2025, 9, 10, 9, 00); // 10 de Septiembre 09:00 hs
		Reserva reserva2 = new Reserva(paciente1, medico1, fechaHora2);

		LocalDateTime fechaHora3 = LocalDateTime.of(2025, 8, 18, 15, 30); // 18 de Agosto 15:30 hs
		Reserva reserva3 = new Reserva(paciente1, medico1, fechaHora3);

		LocalDateTime fechaHora4 = LocalDateTime.of(2025, 11, 20, 13, 00); // 20 de Noviembre 13:00s
		Reserva reserva4 = new Reserva(paciente1, medico1, fechaHora4);

		osde.reservarUnTurno(reserva1); // fecha 10 de Octubre
		osde.reservarUnTurno(reserva2);
		osde.reservarUnTurno(reserva3);
		osde.reservarUnTurno(reserva4);

//		Integer valorEsperado = 4;
//		Integer valorObtenido = osde.obtenerCantidadDeReservasPorPacientes(paciente1);
//		
//		assertEquals(valorEsperado,valorObtenido);

		HashSet<Reserva> reservasRealizadas = new HashSet<>();
		reservasRealizadas.add(reserva1);
		reservasRealizadas.add(reserva2);
		reservasRealizadas.add(reserva3);
		reservasRealizadas.add(reserva4);

		HashSet<Reserva> reservaObtenida = osde.buscarReservasPorPaciente(paciente1);

		assertEquals(reservasRealizadas, reservaObtenida);

	}

@Test
public void DadoQueExisteUnClienteYMedicosEnElSistemaSeDeniegaAgregarTurnosDeDiasAnteriores() {
	osde.agregarPacienteAlSistema(paciente1);
	osde.agregarMedicoAlSistema(medico1);

	LocalDateTime fechaHora2 = LocalDateTime.of(2025, 9, 10, 9, 00); // 10 de Septiembre 09:00 hs
	Reserva reserva2 = new Reserva(paciente1, medico1, fechaHora2);
	
	assertTrue(osde.reservarUnTurno(reserva1)); // fecha 10 de Octubre
	assertFalse(osde.reservarUnTurno2(reserva2));
	
	LocalDateTime fechaHora3 = LocalDateTime.of(2025, 10, 2, 8, 00); // 2 de Octubre 08:00 hs (dia de hoy a las 8 am)
	Reserva reserva3 = new Reserva(paciente1, medico1, fechaHora3);
	
	assertTrue(osde.reservarUnTurno2(reserva3));
	
	LocalDateTime fechaHora4 = LocalDateTime.of(2025, 10, 2, 17, 00); // 2 de Octubre 08:00 hs (dia de hoy a las 17:00 pm)
	Reserva reserva4 = new Reserva(paciente1, medico1, fechaHora4);
	
	assertTrue(osde.reservarUnTurno2(reserva4));
	
}
@Test
public void DadoQueExisteUnaReservaEnElSistemaBuscarUnaListaDeReservaDelMesDelCliente() {
	osde.agregarPacienteAlSistema(paciente1);
	osde.agregarMedicoAlSistema(medico1);

	LocalDateTime fechaHora2 = LocalDateTime.of(2025, 10, 12, 9, 00); // 12 de Octubre 09:00 hs
	Reserva reserva2 = new Reserva(paciente1, medico1, fechaHora2);
	
	LocalDateTime fechaHora3 = LocalDateTime.of(2025, 11, 15, 8, 00); // 15 de Noviembre 08:00 hs 
	Reserva reserva3 = new Reserva(paciente1, medico1, fechaHora3);
	
	assertTrue(osde.reservarUnTurno(reserva1)); // fecha 10 de Octubre
	assertTrue(osde.reservarUnTurno2(reserva2));
	assertTrue(osde.reservarUnTurno2(reserva3));
	
	LocalDateTime fechaMes = LocalDateTime.of(2025, 10, 1, 9, 00); // 1 de Octubre 09:00 hs
	
	HashSet<Reserva>listaEsperada=new HashSet<Reserva>();
	
	listaEsperada.add(reserva1);
	listaEsperada.add(reserva2);
	
	HashSet<Reserva>listaObtenida=osde.reservasDeUnClientePorMes(paciente1, fechaMes);
	
	assertEquals(listaObtenida,listaEsperada);
}
@Test
public void DesdeUnaListaDeReservasSeEsperaUnMontoCalculadoDe0ParaCoberturaTotaly400ParaPlanEstandar() {
	osde.agregarPacienteAlSistema(paciente1);
	osde.agregarPacienteAlSistema(paciente2);
	osde.agregarMedicoAlSistema(medico1);

	LocalDateTime fechaHora2 = LocalDateTime.of(2025, 10, 12, 9, 00); // 12 de Octubre 09:00 hs
	Reserva reserva2 = new Reserva(paciente1, medico1, fechaHora2);
	
	LocalDateTime fechaHora3 = LocalDateTime.of(2025, 10, 15, 8, 00); // 15 de Octubre 08:00 hs 
	Reserva reserva3 = new Reserva(paciente2, medico1, fechaHora3);
	
	assertTrue(osde.reservarUnTurno(reserva1)); // fecha 10 de Octubre
	assertTrue(osde.reservarUnTurno2(reserva2));
	assertTrue(osde.reservarUnTurno2(reserva3));
	
	Double resultadoEsperadoCoberturaTotal=0.0;
	Double resultadoObtenidoCoberturaTotal=osde.calcularImporteDelMesDado(fechaHora3, paciente2);
	
	Double resultadoEsperadoCoberturaEstandar=200.0;
	Double resultadoObtenidoCoberturaEstandar=osde.calcularImporteDelMesDado(fechaHora3, paciente1);
	
	assertEquals(resultadoEsperadoCoberturaTotal,resultadoObtenidoCoberturaTotal);
	assertEquals(resultadoEsperadoCoberturaEstandar,resultadoObtenidoCoberturaEstandar);
}
@Test
public void ObtenerUnaListaDePacientesOrdenadosAlfabeticamentePorSuTipoDePlan() {
	Paciente paciente3;
	Paciente paciente4;
	Paciente paciente5;
	Paciente paciente6;
	
	paciente3 = new Paciente("Mica", "Sarmiento",85, 00001,Plan.JUVENTUD);
	paciente4 = new Paciente("Mati", "Federal", 12, 024347,Plan.NO_POSEE);
	paciente5 = new Paciente("Tomas", "Mazza", 67,6546546,Plan.ESTANDAR);
	paciente6 = new Paciente("Ivan", "PalestinaFree", 120, 23523623,Plan.COBERTURA_TOTAL);
	
	osde.agregarPacienteAlSistema(paciente3);
	osde.agregarPacienteAlSistema(paciente4);
	osde.agregarPacienteAlSistema(paciente5);
	osde.agregarPacienteAlSistema(paciente6);
	
	ArrayList<Paciente>ListaEsperada=new ArrayList<Paciente>();
	
	ListaEsperada.add(paciente6); //COBERTURA_TOTAL
	ListaEsperada.add(paciente5); //ESTANDAR
	ListaEsperada.add(paciente3); //JUVENTUD
	ListaEsperada.add(paciente4); //NO_POSEE
	
	ArrayList<Paciente>ListaObtenida=new ArrayList<Paciente>();
	ListaObtenida=osde.listaOrdenadaPacientesPorTipos();
	
	assertEquals(ListaEsperada,ListaObtenida);
	
}


}
	

