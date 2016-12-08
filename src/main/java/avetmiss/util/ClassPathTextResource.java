package avetmiss.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;

public class ClassPathTextResource<T> {
    private final Resource resource;

    public ClassPathTextResource(String fileName) {
        checkArgument(StringUtils.hasLength(fileName), "fileName is requred");
        this.resource = new ClassPathResource(fileName);

        checkArgument(resource.exists(), "Cannot find file in classpath:" + fileName);
    }

    public List<T> read(TextLineMapper<T> mapper) {
        try (Stream<String> lines = Files.lines(Paths.get(resource.getURI())).filter(Objects::nonNull)) {
            List<T> list = new ArrayList<>();
            lines.forEach(new Consumer<String>() {
                int i = 0;
                @Override public void accept(String s) {
                    T object = mapper.mapRow(s, i++);
                    addToListIfNotNull(list, object);
                }
            });

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
