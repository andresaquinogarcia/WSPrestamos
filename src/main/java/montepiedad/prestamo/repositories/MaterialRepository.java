package montepiedad.prestamo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import montepiedad.prestamo.entity.MaterialEntity;

/*
 * Repositorio que extiende de MongoRepository para operaciones Crud 
*/
public interface MaterialRepository extends MongoRepository<MaterialEntity, String> 
{

}
