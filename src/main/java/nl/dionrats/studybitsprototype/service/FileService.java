package nl.dionrats.studybitsprototype.service;

import nl.dionrats.studybitsprototype.adapters.DocumentAdapter;
import nl.dionrats.studybitsprototype.entity.Document;
import lombok.extern.slf4j.Slf4j;
import nl.dionrats.studybitsprototype.entity.DocumentDTO;
import org.kohsuke.randname.RandomNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import nl.dionrats.studybitsprototype.repository.IRepository;

import java.io.IOException;
import java.util.Random;

@Slf4j
@Service
public class FileService {

    private final IRepository repository;
    private final RandomNameGenerator nameGenerator;

    //TODO take qualifier from properties
    @Autowired
    public FileService(@Qualifier("IPFS") IRepository repository) {
        this.repository = repository;
        nameGenerator = new RandomNameGenerator(new Random().nextInt());
    }


    public String processFile(DocumentDTO dto) throws IOException {
        String key = nameGenerator.next();
        Document document = DocumentAdapter.toDocument(dto);

        return repository.storeFile(key, document);
    }

    public Document retrieveFile(String id) {
        return repository.getFile(id);
    }


}
