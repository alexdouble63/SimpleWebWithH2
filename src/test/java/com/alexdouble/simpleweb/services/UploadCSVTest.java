package com.alexdouble.simpleweb.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.alexdouble.simpleweb.entity.Person;
import com.alexdouble.simpleweb.repository.RepositoryPerson;
import com.alexdouble.simpleweb.repository.RepositoryDepartmant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@ExtendWith(SpringExtension.class)
public class UploadCSVTest {
    @Mock
    private RepositoryDepartmant repositoryDepartmant;
    @Mock
    private RepositoryPerson repositoryPerson;
    @InjectMocks
    private UploadCSV uploadCSV;


    @Test
    public void testUploadCSVFile() throws Exception {
        Person person = new Person();
        String csvData = "LastName;FirstName;2;5000n";
        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", inputStream);

        assertEquals(1, uploadCSV.uploadCSVFile(file));
    }

    @Test
    public void testUploadCSVFileFail() throws Exception {
        Person person = new Person();
        String csvData = "";
        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", inputStream);

        assertEquals(0, uploadCSV.uploadCSVFile(file));
    }
}