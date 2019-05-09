package nl.dionrats.studybitsprototype.service;

import nl.dionrats.studybitsprototype.entity.Document;
import lombok.extern.slf4j.Slf4j;
import nl.dionrats.studybitsprototype.entity.DocumentDTO;
import org.kohsuke.randname.RandomNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nl.dionrats.studybitsprototype.repository.IRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@Slf4j
@Service
public class FileService {

    private final IRepository repository;
    private final RandomNameGenerator nameGenerator;

    @Autowired
    public FileService(IRepository repository) {
        this.repository = repository;
        nameGenerator = new RandomNameGenerator(new Random().nextInt());
    }


    public String processFile(DocumentDTO dto) throws IOException {
        String key = nameGenerator.next();
        Document document = new Document(dto.getName(), dto.getType(), dto.getFile().getBytes());
        repository.storeFile(key, document);

        return key;
    }

    public Document retrieveFile(String id) {
        return repository.getFile(id);
    }


}
