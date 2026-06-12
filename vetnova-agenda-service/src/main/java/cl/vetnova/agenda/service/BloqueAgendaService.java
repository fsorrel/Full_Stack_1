package cl.vetnova.agenda.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.agenda.model.BloqueAgenda;
import cl.vetnova.agenda.repository.BloqueAgendaRepository;

@Service
public class BloqueAgendaService {
    private static final Logger log = LoggerFactory.getLogger(BloqueAgendaService.class);

    @Autowired
    private BloqueAgendaRepository bloqueAgendaRepository;

    public BloqueAgenda crear(BloqueAgenda bloque){
        log.info("event=crear_bloque_agenda veterinarioId={}", bloque.getVeterinarioId());
        return bloqueAgendaRepository.save(bloque);
    }

    public List<BloqueAgenda> listar(){
        return bloqueAgendaRepository.findAll();
    }

    public void eliminar(Long id){
        bloqueAgendaRepository.deleteById(id);
    }
}