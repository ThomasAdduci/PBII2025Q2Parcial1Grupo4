package ar.edu.unlam.dominioTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import ar.edu.unlam.dominio.*;

import ar.edu.unlam.interfaces.AccesorioClinico;
import ar.edu.unlam.interfaces.AnalisisSangre;
import ar.edu.unlam.interfaces.RayosX;

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
	private Reserva reservaConAccesorios;
	
	// COLECCION DE ACCESORIOS
	Set<AccesorioClinico> accesorios = new HashSet<>();

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

		//paciente1 = new Paciente(nombrePaciente1, apellidoPaciente1, edadPaciente1, dniPaciente1);
		paciente1 = new Paciente(nombrePaciente1, apellidoPaciente1, edadPaciente1, dniPaciente1, Plan.COBERTURA_TOTAL);

		// PACIENTE 2
		String nombrePaciente2 = "Camila";
		String apellidoPaciente2 = "Dominguez";
		Integer edadPaciente2 = 37;
		Integer dniPaciente2 = 34999888;

		//paciente2 = new Paciente(nombrePaciente2, apellidoPaciente2, edadPaciente2, dniPaciente2);
		paciente2 = new Paciente(nombrePaciente2, apellidoPaciente2, edadPaciente2, dniPaciente2, Plan.ESTANDAR);

		LocalDateTime fechaHora1 = LocalDateTime.of(2025, 10, 8, 10, 30); // 8 de Octubre 10.30 hs
		reserva1 = new Reserva(paciente1, medico1, fechaHora1, paciente1.getPlan().getCopago());
		reservaConAccesorios = new Reserva(paciente1, medico1, fechaHora1, accesorios, paciente1.getPlan().getCopago());
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
				dniPacienteNuevo, Plan.COBERTURA_TOTAL);

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

		Paciente pacienteMedico = new Paciente(nombrePacienteMedico, apellidoPacienteMedico, edadPacienteMedico,dniPacienteMedico, Plan.COBERTURA_TOTAL);

		osde.agregarMedicoAlSistema(medico1);
		Boolean seAgrego = osde.agregarPacienteAlSistema(pacienteMedico);
		assertTrue(seAgrego);

	}

	@Test
	public void dadoQueExisteUnPacienteYQuiereReservarUnTurnoMedicoSeObtieneUnResultadoExitoso() {
		osde.agregarPacienteAlSistema(paciente1);
		Boolean reservaDeTurno = osde.reservarUnTurno(reserva1);
		assertTrue(reservaDeTurno);
	}
	

	@Test
	public void dadoQueNoExisteUnClienteRegistradoEnElSistemaYQuiereReservarUnTurnoYObtieneResultadoFalso() {
		// EL PACIENTE NO ESTA AGREGADO AL SISTEMA Y SE  INTENTA HACER UNA RESERVA
		osde.agregarMedicoAlSistema(medico1);
		Boolean seReservo = osde.reservarUnTurno(reserva1);
		assertFalse(seReservo);
	}

	@Test
	public void dadoQueExisteUnPacienteQuiereReservarUnTurnoFueraDelHorarioEstablecidoSeObtieneUnResultadoFalso() {
		Reserva reservaFueraDeHorario;
		LocalDateTime fechaHoraFueraDeHorario = LocalDateTime.of(2025, 12, 18, 7, 30); // 12 de Diciembre 7.30 hs

		reservaFueraDeHorario = new Reserva(paciente1, medico1, fechaHoraFueraDeHorario, paciente1.getPlan().getCopago());

		Boolean reserva = osde.reservarUnTurno(reservaFueraDeHorario);
		assertFalse(reserva);
	}

	@Test
	public void dadoQueExisteUnClienteYQuiereReservarDosTurnosElMismoDiaYHoraYObtieneResultadoFalso() {
		
		osde.reservarUnTurno(reserva1); // reservamos la reserva1 que esta inicializada en el before
		LocalDateTime fechaHoraDeReserva = LocalDateTime.of(2025, 10, 8, 10, 30); // misma fecha que reserva1
		Reserva nuevaReserva = new Reserva(paciente1, medico1, fechaHoraDeReserva, paciente1.getPlan().getCopago());
		Boolean reservaDuplicada = osde.reservarUnTurno(nuevaReserva);
		assertFalse(reservaDuplicada);
		
	}
	
	@Test
	public void dadoQueExisteUnPacienteConUnaReservaYOtroClienteQuiereSacarTurno10MinutosDespuesNoPuede() {
		osde.agregarPacienteAlSistema(paciente1);
		osde.agregarPacienteAlSistema(paciente2);
		osde.reservarUnTurno(reserva1); // RESERVA 1 CON FECHA 08/10/2025 10.30 HS
		
		// RESERVA 1 8 de Octubre 10.30 hs
		LocalDateTime fechaHora2 = LocalDateTime.of(2025, 10, 8, 10, 40); // 10 MINUTOS DESPUES
		Reserva reserva2 = new Reserva(paciente2, medico1, fechaHora2, paciente2.getPlan().getCopago());
		Boolean seAgrego = osde.reservarUnTurno(reserva2);
		
		assertFalse(seAgrego);
	}
	
	@Test
	public void dadoQueExisteUnPacienteConUnaReservaYOtroClienteQuiereSacarTurno15MinutosDespuesObtieneResultadoExitoso() {
		osde.agregarPacienteAlSistema(paciente1);
		osde.agregarPacienteAlSistema(paciente2);
		osde.reservarUnTurno(reserva1); // RESERVA 1 CON FECHA 08/10/2025 10.30 HS
		
		// RESERVA 1 8 de Octubre 10.30 hs
		LocalDateTime fechaHora2 = LocalDateTime.of(2025, 10, 8, 10, 45); // 10 MINUTOS DESPUES
		Reserva reserva2 = new Reserva(paciente2, medico1, fechaHora2, paciente2.getPlan().getCopago());
		Boolean seAgrego = osde.reservarUnTurno(reserva2);
		
		assertTrue(seAgrego);
	}
<<<<<<< HEAD
	
	@Test
	public void dadoQueExisteUnPacienteConUnaReservaYOtroClienteQuiereSacarTurno10MinutosDespuesNoPuede() {
		osde.agregarPacienteAlSistema(paciente1);
		osde.agregarPacienteAlSistema(paciente2);
		osde.reservarUnTurno(reserva1); // RESERVA 1 CON FECHA 08/10/2025 10.30 HS
		
		// RESERVA 1 8 de Octubre 10.30 hs
		LocalDateTime fechaHora2 = LocalDateTime.of(2025, 10, 8, 10, 40); // 10 MINUTOS DESPUES
		Reserva reserva2 = new Reserva(paciente2, medico1, fechaHora2, paciente2.getPlan().getCopago());
		Boolean seAgrego = osde.reservarUnTurno(reserva2);
		
		assertFalse(seAgrego);
	}
	
	@Test
	public void dadoQueExisteUnPacienteConUnaReservaYOtroClienteQuiereSacarTurno15MinutosDespuesObtieneResultadoExitoso() {
		osde.agregarPacienteAlSistema(paciente1);
		osde.agregarPacienteAlSistema(paciente2);
		osde.reservarUnTurno(reserva1); // RESERVA 1 CON FECHA 08/10/2025 10.30 HS
		
		// RESERVA 1 8 de Octubre 10.30 hs
		LocalDateTime fechaHora2 = LocalDateTime.of(2025, 10, 8, 10, 45); // 10 MINUTOS DESPUES
		Reserva reserva2 = new Reserva(paciente2, medico1, fechaHora2, paciente2.getPlan().getCopago());
		Boolean seAgrego = osde.reservarUnTurno(reserva2);
		
		assertTrue(seAgrego);
	}
=======
>>>>>>> desarrollo
	
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
		Reserva reserva2 = new Reserva(paciente1, medico1, fechaHora2, paciente1.getPlan().getCopago());

		LocalDateTime fechaHora3 = LocalDateTime.of(2025, 10, 18, 15, 30); // 18 de octubre 15:30 hs
		Reserva reserva3 = new Reserva(paciente1, medico1, fechaHora3, paciente1.getPlan().getCopago());

		LocalDateTime fechaHora4 = LocalDateTime.of(2025, 10, 20, 13, 00); // 20 de octubre 13:00s
		Reserva reserva4 = new Reserva(paciente1, medico1, fechaHora4, paciente1.getPlan().getCopago());

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
		Reserva reserva2 = new Reserva(paciente1, medico1, fechaHora2, paciente1.getPlan().getCopago());

		LocalDateTime fechaHora3 = LocalDateTime.of(2025, 10, 18, 15, 30); // 18 de octubre 15:30 hs
		Reserva reserva3 = new Reserva(paciente1, medico1, fechaHora3, paciente1.getPlan().getCopago());

		LocalDateTime fechaHora4 = LocalDateTime.of(2025, 10, 20, 13, 00); // 20 de octubre 13:00s
		Reserva reserva4 = new Reserva(paciente1, medico1, fechaHora4, paciente1.getPlan().getCopago());

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

		LocalDateTime fechaHora2 = LocalDateTime.of(2026, 9, 10, 9, 00); // 10 de Septiembre 09:00 hs
		Reserva reserva2 = new Reserva(paciente1, medico1, fechaHora2, paciente1.getPlan().getCopago());

		LocalDateTime fechaHora3 = LocalDateTime.of(2026, 8, 18, 15, 30); // 18 de Agosto 15:30 hs
		Reserva reserva3 = new Reserva(paciente1, medico1, fechaHora3, paciente1.getPlan().getCopago());

		LocalDateTime fechaHora4 = LocalDateTime.of(2025, 11, 20, 13, 00); // 20 de Noviembre 13:00s
		Reserva reserva4 = new Reserva(paciente1, medico1, fechaHora4, paciente1.getPlan().getCopago());

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
	public void calcularCostoFinalDeUnTurnoConUnAccesorioIncluido() {
		accesorios.add(new RayosX()); //le sumamos accesorio rayosx, paga 100.
		//la reserva ya está inicializada en @Before con paciente1, medico1.
		Integer costoObtenido = reservaConAccesorios.calcularCostoFinal();
		//Tiene cobertura total, no paga consulta.
		Integer costoEsperado = 100;
		assertEquals(costoEsperado, costoObtenido);
	}
	
	@Test
	public void calcularCostoFinalDeUnTurnoConVariosAccesoriosIncluidos() {
		accesorios.add(new RayosX());
		accesorios.add(new AnalisisSangre()); //lo calculamos con 2 accesorios.
		Integer costoObtenido = reservaConAccesorios.calcularCostoFinal();
		//Cobertura total -> $0
		//RayosX -> $100
		//Analisis -> $200
		Integer costoEsperado = 300;
		assertEquals(costoEsperado, costoObtenido);
	}
	
	@Test
	public void calcularCostoFinalDeUnTurnoSinAccesoriosIncluidos() {
		LocalDateTime fechaHoraTurno = LocalDateTime.of(2025, 10, 8, 10, 30);
		Reserva reservaSinAccesorios = new Reserva(paciente2, medico1, fechaHoraTurno, paciente2.getPlan().getCopago());
		Integer costoObtenido = reservaSinAccesorios.calcularCostoFinal();
		//Paciente2 tiene plan estandar -> costo $100
		//no tiene accesorios la consulta
		Integer costoEsperado = 100;
		assertEquals(costoEsperado, costoObtenido);
		
		
	}
	
	@Test
	public void dadoQueExisteUnMedicoEnElSistemaCuandoSeBuscaPorCodigoSeObtieneElMedicoCorrecto() {
		// Se agrega el medico al sistema
		osde.agregarMedicoAlSistema(medico1);
		// Se busca al medico agregado por su codigo
		Medico medicoBuscado = osde.buscarMedicoPorCodigo(medico1.getCodigoMedico());
		// El medico buscado tiene que ser el mismo que el medico agregado al sistema
		assertEquals(medico1, medicoBuscado);
	}
	
	@Test
	public void dadoQueNoExisteUnMedicoConElCodigo777CuandoSeBuscaPorElCodigoDevuelveNull() {
		// Busco un medico con un codigo que no existe
		Medico medicoBuscado = osde.buscarMedicoPorCodigo(777);
		// El medico buscado da null porque no existe uno con el codigo suministrado
		assertNull(medicoBuscado);
	
	}
	
	@Test
	public void dadoQueExistenMedicosEnElSistemaCuandoSeBuscanPorEspecialidadSeObtienenLosCorrectos() {
		// Se inicializan dos nuevos medicos
		Medico medico2 = new Medico("Maria", "Almaraz", 45, 12345678, Especialidad.CARDIOLOGIA);
		Medico medico3 = new Medico("Jose", "Centurion", 45, 87654321, Especialidad.CLINICA);
		// Se agregan medicos de distinta especialidad al sistema
		// El medico1 tiene de especialidad clinica
		osde.agregarMedicoAlSistema(medico1);
		osde.agregarMedicoAlSistema(medico2);
		osde.agregarMedicoAlSistema(medico3);
		// Busco medicos con la especialidad clinica
		HashSet<Medico> medicosEncontrados = osde.buscarMedicosPorEspecialidad(Especialidad.CLINICA);
		// Verifico si los medicos fueron encontrados
		assertTrue(medicosEncontrados.contains(medico1));
		assertTrue(medicosEncontrados.contains(medico3));
		// Devuelve la cantidad de demidos encontrados con especialidad clinica
		assertEquals(2, medicosEncontrados.size());
	}
	
	@Test
	public void dadoQueNoExistenMedicosConEsaEspecialidadCuandoSeBuscanSeObtieneVacioPeroNoNull() {
		// Se inicializan medicos
	    Medico medico2 = new Medico("Maria", "Almaraz", 40, 12345678, Especialidad.CARDIOLOGIA);
	    Medico medico3 = new Medico("Jose", "Centurion", 38, 87654321, Especialidad.DERMATOLOGIA);
	 // Se agregan medicos de distinta especialidad al sistema
	    osde.agregarMedicoAlSistema(medico2);
	    osde.agregarMedicoAlSistema(medico3);
	    // Busco medicos con la especialidad en la que no existen medicos
	    HashSet<Medico> medicosEncontrados = osde.buscarMedicosPorEspecialidad(Especialidad.PEDIATRIA);
	    // El resultado no es null
	    assertNotNull(medicosEncontrados);
	    // Devuelve un conjunto vacio
	    assertTrue(medicosEncontrados.isEmpty());
	}

@Test
public void DadoQueExisteUnClienteYMedicosEnElSistemaSeDeniegaAgregarTurnosDeDiasAnteriores() {
	osde.agregarPacienteAlSistema(paciente1);
	osde.agregarMedicoAlSistema(medico1);

	LocalDateTime fechaHora2 = LocalDateTime.of(2025, 9, 10, 9, 00); // 10 de Septiembre 09:00 hs
	Reserva reserva2 = new Reserva(paciente1, medico1, fechaHora2,paciente1.getPlan().getCopago());
	
	assertTrue(osde.reservarUnTurno(reserva1)); // fecha 10 de Octubre
	assertFalse(osde.reservarUnTurno(reserva2));
	
	LocalDateTime fechaHora3 = LocalDateTime.of(2025, 10, 22, 8, 00); // 22 de Octubre 08:00 hs (dia de hoy a las 8 am)
	Reserva reserva3 = new Reserva(paciente1, medico1, fechaHora3,paciente1.getPlan().getCopago());
	
	assertTrue(osde.reservarUnTurno(reserva3));
	
	LocalDateTime fechaHora4 = LocalDateTime.of(2025, 10, 2, 17, 00); // 2 de Octubre 08:00 hs (dia de hoy a las 17:00 pm)
	Reserva reserva4 = new Reserva(paciente1, medico1, fechaHora4,paciente1.getPlan().getCopago());
	
	assertFalse(osde.reservarUnTurno(reserva4));
	
}
@Test
public void DadoQueExisteUnaReservaEnElSistemaBuscarUnaListaDeReservaDelMesDelCliente() {
	osde.agregarPacienteAlSistema(paciente1);
	osde.agregarMedicoAlSistema(medico1);

	LocalDateTime fechaHora2 = LocalDateTime.of(2025, 10, 12, 9, 00); // 12 de Octubre 09:00 hs
	Reserva reserva2 = new Reserva(paciente1, medico1, fechaHora2,paciente1.getPlan().getCopago());
	
	LocalDateTime fechaHora3 = LocalDateTime.of(2025, 11, 15, 8, 00); // 15 de Noviembre 08:00 hs 
	Reserva reserva3 = new Reserva(paciente1, medico1, fechaHora3,paciente1.getPlan().getCopago());
	
	assertTrue(osde.reservarUnTurno(reserva1)); // fecha 10 de Octubre
	assertTrue(osde.reservarUnTurno(reserva2));
	assertTrue(osde.reservarUnTurno(reserva3));
	
	LocalDateTime fechaMes = LocalDateTime.of(2025, 10, 1, 9, 00); // 1 de Octubre 09:00 hs
	
	HashSet<Reserva>listaEsperada=new HashSet<Reserva>();
	
	listaEsperada.add(reserva1);
	listaEsperada.add(reserva2);
	
	HashSet<Reserva>listaObtenida=osde.reservasDeUnClientePorMes(paciente1, fechaMes);
	
	assertEquals(listaObtenida,listaEsperada);
}
@Test
public void DesdeUnaListaDeReservasSeEsperaUnMontoCalculadoDe0ParaCoberturaTotaly200ParaPlanEstandar() {
	osde.agregarPacienteAlSistema(paciente1);
	osde.agregarPacienteAlSistema(paciente2);
	osde.agregarMedicoAlSistema(medico1);

	LocalDateTime fechaHora2 = LocalDateTime.of(2025, 10, 12, 9, 00); // 12 de Octubre 09:00 hs
	Reserva reserva2 = new Reserva(paciente1, medico1, fechaHora2,paciente1.getPlan().getCopago());
	
	LocalDateTime fechaHora3 = LocalDateTime.of(2025, 10, 15, 8, 00); // 15 de Octubre 08:00 hs 
	Reserva reserva3 = new Reserva(paciente2, medico1, fechaHora3,paciente2.getPlan().getCopago());
	
	LocalDateTime fechaHora4 = LocalDateTime.of(2025, 10, 18, 10, 00); // 17 de Octubre 10:00 hs 
	Reserva reserva4 = new Reserva(paciente2, medico1, fechaHora4,paciente2.getPlan().getCopago());
	
	assertTrue(osde.reservarUnTurno(reserva1)); // fecha 10 de Octubre
	assertTrue(osde.reservarUnTurno(reserva2));
	assertTrue(osde.reservarUnTurno(reserva3));
	assertTrue(osde.reservarUnTurno(reserva4));
	
	
	Double resultadoEsperadoCoberturaTotal=0.0;
	Double resultadoObtenidoCoberturaTotal=osde.calcularImporteDelMesDado(fechaHora3, paciente1);
	
	Double resultadoEsperadoCoberturaEstandar=200.0;
	Double resultadoObtenidoCoberturaEstandar=osde.calcularImporteDelMesDado(fechaHora3, paciente2);
	
	assertEquals(resultadoEsperadoCoberturaTotal,resultadoObtenidoCoberturaTotal);
	assertEquals(resultadoEsperadoCoberturaEstandar,resultadoObtenidoCoberturaEstandar);
}

@Test
public void DesdeUnaListaDeReservasSeEsperaUnMontoCalculadoDe200ParaCoberturaTotalConAnalisisDeSangrey800ParaPlanEstandarConAnalisisYRayos() {
	osde.agregarPacienteAlSistema(paciente1);
	osde.agregarPacienteAlSistema(paciente2);
	osde.agregarMedicoAlSistema(medico1);
	AnalisisSangre analisis=new AnalisisSangre();
	RayosX rayos=new RayosX();
	Set<AccesorioClinico> accesoriosRequeridos=new HashSet();
	Set<AccesorioClinico> accesoriosRequeridos2=new HashSet();
	accesoriosRequeridos.add(analisis);
	accesoriosRequeridos2.add(analisis);
	accesoriosRequeridos2.add(rayos);
	
	LocalDateTime fechaHora2 = LocalDateTime.of(2025, 10, 12, 9, 00); // 12 de Octubre 09:00 hs
	Reserva reserva2 = new Reserva(paciente1, medico1, fechaHora2,accesoriosRequeridos,paciente1.getPlan().getCopago());
	
	LocalDateTime fechaHora3 = LocalDateTime.of(2025, 10, 15, 8, 00); // 15 de Octubre 08:00 hs 
	Reserva reserva3 = new Reserva(paciente2, medico1, fechaHora3,accesoriosRequeridos2,paciente2.getPlan().getCopago());
	
	LocalDateTime fechaHora4 = LocalDateTime.of(2025, 10, 18, 10, 00); // 17 de Octubre 10:00 hs 
	Reserva reserva4 = new Reserva(paciente2, medico1, fechaHora4,accesoriosRequeridos2,paciente2.getPlan().getCopago());
	
	assertTrue(osde.reservarUnTurno(reserva1)); // fecha 10 de Octubre
	assertTrue(osde.reservarUnTurno(reserva2));
	assertTrue(osde.reservarUnTurno(reserva3));
	assertTrue(osde.reservarUnTurno(reserva4));
	
	
	Double resultadoEsperadoCoberturaTotal=200.0;
	Double resultadoObtenidoCoberturaTotal=osde.calcularImporteDelMesDado(fechaHora3, paciente1);
	
	Double resultadoEsperadoCoberturaEstandar=500.0;
	Double resultadoObtenidoCoberturaEstandar=osde.calcularImporteDelMesDado(fechaHora3, paciente2);
	
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

	

