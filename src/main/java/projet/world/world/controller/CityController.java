package projet.world.world.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projet.world.world.model.City;
import projet.world.world.service.CityService;

import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
public class CityController {
    @Autowired
    private CityService cityService;
    // affichage des données
    @RequestMapping(method = RequestMethod.GET, value = "/citys")
    public List<City> getAllCity(){
        return cityService.getCity();
    }
   //l'API qui envoie les coordonnées du point cliqué
    @RequestMapping(method = RequestMethod.GET, value = "/citys/{lat}/{lon}")
    public City getCityByLatAndLong(@PathVariable double lat, @PathVariable double lon){
        return cityService.getCityByLatAndLong(lat, lon);   }
}
