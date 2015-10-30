package clarity.domain;

import java.util.List;

public interface SuburbRepository {
    List<Suburb> getSuburbs(int postCode);
}
