import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.User;
import queue.QueueProducer;

import java.util.UUID;

public class App {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext applicationContext= new ClassPathXmlApplicationContext("activeMq-config.xml");
        QueueProducer producer=applicationContext.getBean(QueueProducer.class);
        User user=new User();
        for (int i = 0; i < 100; i++) {
            user.setAge(i);
            user.setName((i%2==0? "Mr":"Mrs")+String.valueOf((char)(int)(Math.random()*26+'A'+1))+String.valueOf((char)(int)(Math.random()*26+'a')));
            user.setSex(i%2==0? "男":"女");
            user.setSalary((int)(Math.random()*10000));
            user.setNumber(UUID.randomUUID().toString());
            producer.sendMessage(user);
            Thread.sleep(50);
        }
        applicationContext.close();
    }
}
