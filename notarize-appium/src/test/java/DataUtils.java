import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by paul on 7/6/17.
 */
public class DataUtils {
    // utility functions

    public static Collection<String[]> getTestData(String fileName)
            throws IOException {

        String resolvedFilename = null;
        if((new File(fileName)).exists())
            resolvedFilename = fileName;
        else
            resolvedFilename = Paths.get(fileName).toAbsolutePath().toString();

        List<String[]> records = new ArrayList<String[]>();

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(fileName));
            String[] line;
            while ((line = reader.readNext()) != null) {
                records.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }
}
