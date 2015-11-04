package avetmiss.infrastructure;

import avetmiss.domain.Suburb;
import avetmiss.domain.SuburbRepository;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

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
        return this.suburbs.get(postCode);
    }

    private Map<Integer, List<Suburb>> initializeSuburbs(String suburbFilePath) {
        BasicJsonParser basicJsonParser = new BasicJsonParser();

        List<Object> suburbJsons =
                basicJsonParser.parseList(asJson(suburbFilePath));

        Map<Integer, List<Suburb>> suburbsMap = Maps.newLinkedHashMap();

        for(Object suburbJson: suburbJsons) {
            Suburb suburb = jsonToSuburb((String) suburbJson, basicJsonParser);

            List<Suburb> existingSuburbs = suburbsMap.get(suburb.getPostCode());

            if(existingSuburbs == null) {
                existingSuburbs = newArrayList();
                suburbsMap.put(suburb.getPostCode(), existingSuburbs);
            }

            existingSuburbs.add(suburb);
        }

        return suburbsMap;
    }

    private Suburb jsonToSuburb(String suburbJson, BasicJsonParser basicJsonParser) {
        Map<String, Object> suburbJsonMap = basicJsonParser.parseMap(suburbJson);

        String suburbName = (String) suburbJsonMap.get("name");
        int postcode = ((Long) suburbJsonMap.get("postcode")).intValue();

        return new Suburb(suburbName, postcode);
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
