package rpf.multithread.skill_redis;

import org.springframework.data.redis.core.RedisTemplate;

public class MyThread extends  Thread {

    private SkillService skillService;
    private RedisTemplate<String,Object> redisTemplate;
    private String key;

    public MyThread(SkillService skillService, RedisTemplate<String, Object> redisTemplate, String key) {
        this.skillService = skillService;
        this.redisTemplate = redisTemplate;
        this.key = key;
    }

    @Override
    public void run() {
        try {
            Long start=System.currentTimeMillis();
            if (skillService.synchrSeckill(redisTemplate,key)){

                System.out.println("++++++++++++++++++++++++++++++++++++++参加了抢购,耗时："+(System.currentTimeMillis()-start));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
