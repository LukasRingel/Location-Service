package locationservice.geolocation;

import com.maxmind.geoip2.DatabaseReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.rauschig.jarchivelib.ArchiverFactory;
import org.rauschig.jarchivelib.FileType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
public class GeoResolver {

    private static final String PATH = "./database/";
    private static final String FILE_NAME = "GeoLite2-City.mmdb";

    private final String downloadUrl;

    private DatabaseReader databaseReader;

    private GeoResolver(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @SneakyThrows
    public static GeoResolver updateAndCreate(String downloadUrl) {
        return new GeoResolver(downloadUrl).updateMaxmindDatabase().initDatabase();
    }

    public GeoResolver initDatabase() throws IOException {
        var databaseFile = new File(PATH + FILE_NAME);
        databaseReader = new DatabaseReader.Builder(databaseFile).build();
        return this;
    }

    public GeoResolver updateMaxmindDatabase() throws IOException {
        log.info("Updating geo-data database...");

        var folder = new File(PATH);
        if (!folder.exists()) folder.mkdir();

        var databaseFile = new File(folder, FILE_NAME);
        if (databaseFile.exists()) {
            FileUtils.cleanDirectory(folder);
        }

        var archiveFile = new File(PATH, "archive.tar.gz");
        FileUtils.copyURLToFile(new URL(downloadUrl), archiveFile);

        var archiver = ArchiverFactory.createArchiver(FileType.get(archiveFile));
        archiver.extract(archiveFile, folder);
        archiveFile.delete();

        Arrays.stream(Objects.requireNonNull(folder.listFiles()))
              .filter(file -> file.getName().toLowerCase().startsWith("geolite2"))
              .findFirst()
              .ifPresent(file -> {
                  new File(file, FILE_NAME).renameTo(databaseFile);
                  try {
                      FileUtils.deleteDirectory(file);
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              });


        return this;
    }

    public DatabaseReader databaseReader() {
        return databaseReader;
    }
}