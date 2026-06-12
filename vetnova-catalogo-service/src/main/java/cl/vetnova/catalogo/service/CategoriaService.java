package cl.vetnova.catalogo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.catalogo.exception.ResourceNotFoundException;
import cl.vetnova.catalogo.model.Categoria;
import cl.vetnova.catalogo.repository.CategoriaRepository;

@Service
public class CategoriaService {
    private static final Logger log = LoggerFactory.getLogger(CategoriaService.class);

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria crear(Categoria categoria){
        log.info("event=crear_categoria nombre={}", categoria.getNombre());
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listar(){
        return categoriaRepository.findAll();
    }

    public void eliminar(Long id){
        log.info("event=eliminar_categoria categoriaId={}", id);
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría no encontrada con id " + id);
        }
        categoriaRepository.deleteById(id);
    }
}
