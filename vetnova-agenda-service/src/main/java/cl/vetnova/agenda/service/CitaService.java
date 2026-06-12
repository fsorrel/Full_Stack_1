package cl.vetnova.agenda.service;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.agenda.exception.ResourceNotFoundException;

import cl.vetnova.agenda.model.Cita;
import cl.vetnova.agenda.repository.CitaRepository;

@Service
public class CitaService {
    private static final Logger log = LoggerFactory.getLogger(CitaService.class);

    @Autowired
    private CitaRepository citaRepository;

    public Cita crear(Cita cita){
        log.info("event=crear_cita clienteId={} veterinarioId={}", cita.getClienteId(), cita.getVeterinarioId());
        return citaRepository.save(cita);
    }

    public List<Cita> listar(){
        return citaRepository.findAll();
    }

    public Cita confirmar(Long id){

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id " + id));

        cita.setEstado("CONFIRMADA");
        return citaRepository.save(cita);
    }

    public Cita reprogramar(Long id, Date nuevaFecha){

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id " + id));

        cita.setFechaHora(nuevaFecha);
        return citaRepository.save(cita);
    }

    public Cita cancelar(Long id, String motivo){

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id " + id));

        cita.setEstado("CANCELADA");
        return citaRepository.save(cita);
    }

    public Cita registrarAsistencia(Long id){

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id " + id));

        cita.setEstado("ASISTIO");
        return citaRepository.save(cita);
    }

    public Cita marcarAusente(Long id){

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id " + id));

        cita.setEstado("AUSENTE");
        return citaRepository.save(cita);
    }
}
