package com.alexdouble.simpleweb.services;

import com.alexdouble.simpleweb.entity.Departmant;
import com.alexdouble.simpleweb.entity.Person;
import com.alexdouble.simpleweb.repository.RepositoryPerson;
import com.alexdouble.simpleweb.repository.RepositoryDepartmant;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.logging.Logger;

@Service
public class UploadCSV {
    private static final Logger LOGGER = Logger.getLogger(UploadCSV.class.getName());
    @Autowired
    RepositoryDepartmant repositoryDepartmant;
    @Autowired
    RepositoryPerson repositoryPerson;
    public int uploadCSVFile( MultipartFile file){
        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".csv")) {
            return 0;
        }
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                    .build();
            List<String[]> data = csvReader.readAll();
            for (String[] row : data) {
                if(row.length == 4) {
                    Departmant departmant = repositoryDepartmant.findById(Integer.valueOf(row[2])).orElse(null);
                    if (departmant != null) {
                        try {
                            int number = Integer.parseInt(row[3]);
                            Person person = new Person();
                            person.setLastName(row[0]);
                            person.setFirstName(row[1]);
                            person.setDepartmant(repositoryDepartmant.findById(Integer.valueOf(row[2])).get());
                            person.setSalary(Integer.valueOf(row[3]));
                            repositoryPerson.save(person);
                        } catch (NumberFormatException e) {
                            LOGGER.info("Ошибка парсинга файла");
                        }
                    }
                }
            }
            csvReader.close();
        } catch (IOException | CsvException e) {
            LOGGER.info("Ошибка открытия файла CSV");
            return -1;
        }
        return 1;
    }
}
