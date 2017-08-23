package avetmiss.infrastructure;

import avetmiss.domain.Suburb;
import avetmiss.domain.SuburbRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Repository
public class JsonSuburbRepository implements SuburbRepository{

    private Map<Integer, List<Suburb>> suburbs;

    public JsonSuburbRepository() {
        this("suburbs.json");
    }

    public JsonSuburbRepository(String suburbFilePath) {
        this.suburbs = initializeSuburbs(suburbFilePath);
    }

    @Override
    public List<Suburb> getSuburbs(int postCode) {
        List<Suburb> suburbs = this.suburbs.get(postCode);
        return (suburbs == null) ? Collections.emptyList() : suburbs;
    }

    private Map<Integer, List<Suburb>> initializeSuburbs(String suburbFilePath) {
        Map<Integer, List<Suburb>> suburbsMap = new LinkedHashMap();

        String json = asJson(suburbFilePath);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<PostCodeJson> postcodes = objectMapper.readValue(json, new TypeReference<List<PostCodeJson>>(){});


            for(PostCodeJson suburbJson: postcodes) {
                Suburb suburb =
                        new Suburb(suburbJson.name, suburbJson.postcode);

                List<Suburb> existingSuburbs = suburbsMap.get(suburb.getPostCode());

                if(existingSuburbs == null) {
                    existingSuburbs = new ArrayList();
                    suburbsMap.put(suburb.getPostCode(), existingSuburbs);
                }

                existingSuburbs.add(suburb);
            }

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return suburbsMap;
    }

    private String asJson(String suburbFilePath) {
        ClassPathResource resource = new ClassPathResource(suburbFilePath);
        try {
            String json = CharStreams.toString(new InputStreamReader(resource.getInputStream(), Charsets.UTF_8));
            return json;
        } catch (IOException e) {
            throw new IllegalStateException("Fail to initialize suburbs: " + suburbFilePath, e);
        }

    }
}
