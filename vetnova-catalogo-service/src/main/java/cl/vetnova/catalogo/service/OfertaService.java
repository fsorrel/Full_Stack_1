package cl.vetnova.catalogo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.catalogo.exception.ResourceNotFoundException;
import cl.vetnova.catalogo.model.Oferta;
import cl.vetnova.catalogo.repository.OfertaRepository;

@Service
public class OfertaService {
    private static final Logger log = LoggerFactory.getLogger(OfertaService.class);

    @Autowired
    private OfertaRepository ofertaRepository;

    public Oferta crear(Oferta oferta){
        log.info("event=crear_oferta productoId={}", oferta.getProductoId());
        if (oferta.getActiva() == null) {
            oferta.setActiva(true);
        }
        return ofertaRepository.save(oferta);
    }

    public List<Oferta> listar(){
        return ofertaRepository.findAll();
    }

    public Oferta activar(Long id){
        log.info("event=activar_oferta ofertaId={}", id);
        Oferta oferta = buscar(id);
        oferta.setActiva(true);
        return ofertaRepository.save(oferta);
    }

    public Oferta desactivar(Long id){
        log.info("event=desactivar_oferta ofertaId={}", id);
        Oferta oferta = buscar(id);
        oferta.setActiva(false);
        return ofertaRepository.save(oferta);
    }

    public void eliminar(Long id){
        log.info("event=eliminar_oferta ofertaId={}", id);
        buscar(id);
        ofertaRepository.deleteById(id);
    }

    private Oferta buscar(Long id){
        return ofertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oferta no encontrada con id " + id));
    }
}
