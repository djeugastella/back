package projet.world.world.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import projet.world.world.model.City;
import projet.world.world.repository.CityRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ResourceLoader resourceLoader;

    private List<City> mapCsvDataToEntities(List<String[]> csvData) {
        List<City> entities = new ArrayList<>();
        try {
            csvData.forEach(row -> {
                City entity = new City();
                entity.setName(row[0]);
                entity.setLatitude(Double.parseDouble(row[1]));
                entity.setLongitude(Double.parseDouble(row[2]));
                entity.setRegion(row[3]);
                entity.setPopulation(Integer.parseInt(row[4]));
                entities.add(entity);
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return entities;
    }

    public void synchReadExcel() throws IOException {

        if (cityRepository.findAll().isEmpty()) {
            try (CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(getCSVResource().getInputStream())))) {
                List<String[]> csvData = reader.readAll();
                List<City> entities = mapCsvDataToEntities(csvData);
                entities.forEach(c -> {
                    try {
                        cityRepository.save(c);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                });

            } catch (CsvException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Resource getCSVResource() throws IOException {
        return resourceLoader.getResource("classpath:storage/fr.csv");
    }

    public List<City> getCity() {
        return (List<City>) cityRepository.findAll();
    }
    public City getCityByLatAndLong(double latitude, double longitude) {
        try {
            City city = cityRepository.findByLatitudeAndLongitude(latitude, longitude);
            if (city == null) {
                throw new RuntimeException("cette ville n'existe pas dans la base de donnee");
            }
            return city;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
