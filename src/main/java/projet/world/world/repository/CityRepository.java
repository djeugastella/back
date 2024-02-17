package projet.world.world.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import projet.world.world.model.City;

public interface CityRepository extends JpaRepository<City, Long> {
    City findByLatitudeAndLongitude(double latitude, double longitude);
}
