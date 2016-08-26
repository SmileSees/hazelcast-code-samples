import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class NearCacheWithMemoryFormatObject extends NearCacheSupport {

    public static void main(String[] args) {
        HazelcastInstance hz = Hazelcast.newHazelcastInstance();
        IMap<Integer, Article> map = hz.getMap("articlesObject");

        Article article = new Article("foo");
        map.put(1, article);

        // the first get() will populate the Near Cache
        Article firstGet = map.get(1);
        // the second and third get() will be served from the Near Cache
        Article secondGet = map.get(1);
        Article thirdGet = map.get(1);

        printNearCacheStats(map);

        System.out.println("Since we use in-memory format OBJECT, the article instances from the Near Cache will be identical.");
        System.out.println("Compare first and second article instance: " + (firstGet == secondGet));
        System.out.println("Compare second and third article instance: " + (secondGet == thirdGet));

        Hazelcast.shutdownAll();
    }
}
