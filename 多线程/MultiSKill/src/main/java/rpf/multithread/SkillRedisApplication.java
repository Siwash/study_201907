package rpf.multithread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import rpf.multithread.skill_redis.MyThread;
import rpf.multithread.skill_redis.RedisLock;
import rpf.multithread.skill_redis.SkillService;

@SpringBootApplication
public class SkillRedisApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SkillRedisApplication.class, args);
        RedisTemplate<String,Object> redisTemplate= (RedisTemplate<String, Object>) context.getBean("redisTemplate");
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                StringRedisSerializer serializer = new StringRedisSerializer();
                redisConnection.set(serializer.serialize("pronum"), serializer.serialize(String.valueOf(1000)));
                return serializer;
            }
        });
        SkillService service=new SkillService();
        for (int i = 0; i < 1000; i++) {
            Thread task=new MyThread(service,redisTemplate,"msKey");
            task.start();
        }
    }
}
