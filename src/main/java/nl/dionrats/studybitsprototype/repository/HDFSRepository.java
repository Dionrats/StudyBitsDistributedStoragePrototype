package nl.dionrats.studybitsprototype.repository;

import lombok.extern.slf4j.Slf4j;
import nl.dionrats.studybitsprototype.entity.Document;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;

@Slf4j
@Repository("HDFS")
public class HDFSRepository implements IRepository {

    private FileSystem fileSystem;

    public HDFSRepository() {
        log.debug("====== Using HDFS =====");
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://127.0.0.1:9000");

        try {
            fileSystem = FileSystem.get(conf);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public Document getFile(String key) {



        return null;
    }

    @Override
    public String storeFile(String key, Document document) {

        try {
            //FSDataOutputStream outputStream = fileSystem.append(new Path("/Users/dionrats/Nextcloud/school/Leerjaar_4/Stage/Document Systeem/Prototype/src/main/resources/application.properties"));
            Path path = new Path("/test");
            fileSystem.mkdirs(path);
            log.debug("Created: {}", path);


        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return null;
    }
}
