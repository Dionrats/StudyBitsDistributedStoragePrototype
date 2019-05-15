package nl.dionrats.studybitsprototype.repository;

import io.ipfs.api.IPFS;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import lombok.extern.slf4j.Slf4j;
import nl.dionrats.studybitsprototype.entity.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.io.IOException;

import static org.apache.commons.lang3.SerializationUtils.*;

@Slf4j
@Repository("IPFS")
public class IPFSRepository implements IRepository {

    private IPFS ipfs;

    @Autowired
    public IPFSRepository(Environment env) {
        log.debug("===== Using IPFSRepository =====");
        log.debug("HOST: {}", env.getProperty("IPFS.node"));
        ipfs = new IPFS(env.getProperty("IPFS.node.host"), Integer.valueOf(env.getProperty("IPFS.node.port")));
    }

    @Override
    public Document getFile(String key) {
        Multihash filePointer = Multihash.fromBase58(key);
        try {
            byte[] data = ipfs.cat(filePointer);

            return deserialize(data);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    @Override
    public String storeFile(String key, Document document) {
        NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(serialize(document));
        try {
            String address = ipfs.add(file).get(0).hash.toBase58();
            log.debug("Storing file: {}, at location: {}", document.getName(), address);

            return address;
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return key;
    }
}
