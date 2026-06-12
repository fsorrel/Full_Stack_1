package cl.vetnova.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.vetnova.catalogo.model.Oferta;
@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Long> {

}