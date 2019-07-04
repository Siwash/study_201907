package rpf.multithread.skill_redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

public class SkillService {
    private   Logger LOG=LoggerFactory.getLogger(this.getClass());
    public boolean seckill(RedisTemplate<String,Object> redisTemplate,String  key){
            RedisLock lock=new RedisLock(redisTemplate,key,10000,10000);
            try {
                Long start=System.currentTimeMillis();
                if (lock.lock()){
                    String  pronum=lock.get("pronum");
                    LOG.info("进来一个线程："+Thread.currentThread().getName());
                    if (Integer.parseInt(pronum)-1>=0){
                        lock.set("pronum",String.valueOf(Integer.parseInt(pronum)-1));
                        LOG.info("库存数量:"+(Integer.parseInt(pronum) -1)+"     成功!!!"+Thread.currentThread().getName());
                    }else {
                        LOG.info("已经被抢光了，请参与下轮抢购");
                    }
                    LOG.info("++++++++++++++++++++++++++++++++++++++参加了抢购,耗时："+(System.currentTimeMillis()-start));
                    return true;
                }else {
                    System.out.println("GGGGGGGGGGGGGGGGGGGGGGG");
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                //lock.unlock();
            }
            return  false;
    }
    public synchronized boolean synchrSeckill(RedisTemplate<String,Object> redisTemplate,String  key) throws InterruptedException {
        RedisLock lock=new RedisLock(redisTemplate,key,10000,10000);
        String  pronum=lock.get("pronum");
        //Thread.sleep((int)(Math.random()*10));
        if (Integer.parseInt(pronum)>0){
            LOG.info("进来一个线程："+Thread.currentThread().getName());
            if (Integer.parseInt(pronum)-1>=0){
                lock.set("pronum",String.valueOf(Integer.parseInt(pronum)-1));
                LOG.info("库存数量:"+(Integer.parseInt(pronum) -1)+"     成功!!!"+Thread.currentThread().getName());
                return true;
            }else {
                LOG.info("已经被抢光了，请参与下轮抢购");
                return  false;
            }
        }
        return false;
    }
}
