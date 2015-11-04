package avetmiss.util;

import com.google.common.base.Preconditions;
import com.google.common.io.Closeables;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClassPathTextResource<T> {
    private final Resource resource;

    public ClassPathTextResource(String fileName) {
        Preconditions.checkArgument(StringUtils.hasLength(fileName), "fileName is requred");
        this.resource = new ClassPathResource(fileName);

        Preconditions.checkArgument(resource.exists(), "Cannot find file in classpath:" + fileName);
    }

    public List<T> read(TextLineMapper<T> mapper) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(resource.getFile()));
            List<T> list = new ArrayList<T>();
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                T object = mapper.mapRow(line, lineNumber);
                addToListIfNotNull(list, object);
                lineNumber++;
            }

            Closeables.closeQuietly(reader);
            return list;
        } catch (FileNotFoundException e) {
            throw new AssertionError(e);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private void addToListIfNotNull(List<T> list, T object) {
        if(object != null) {
            list.add(object);
        }
    }
}
