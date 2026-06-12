package cl.vetnova.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.vetnova.catalogo.model.Producto;
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
