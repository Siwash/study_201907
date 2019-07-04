package rpf.authentication;

import org.jasig.services.persondir.IPersonAttributes;
import org.jasig.services.persondir.support.AttributeNamedPersonImpl;
import org.jasig.services.persondir.support.StubPersonAttributeDao;

import java.util.*;

public class UserStubPersonAttributeDao extends StubPersonAttributeDao {
    @Override
    public IPersonAttributes getPerson(String uid) {
        Map<String, List<Object>> attributes=new HashMap<String, List<Object>>();
        attributes.put("userId", Collections.singletonList((Object) uid));
        attributes.put("ServerTime", Collections.singletonList((Object) new Date()));
        attributes.put("defuatName", Collections.singletonList((Object) "siwash"));
        return new AttributeNamedPersonImpl(attributes);
    }
}
