package queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import pojo.User;

import javax.jms.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class QueueProducer {
    private static final String url="tcp://localhost:61616";
    private static final String queueName="HelloActiveMQ";

    public static void main(String[] args) throws JMSException, InterruptedException {
        //创建连接工厂
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(url);
        List<String> list=new ArrayList<String>();
        list.add("pojo.User");
        ((ActiveMQConnectionFactory) connectionFactory).setTrustedPackages(list);
       // ((ActiveMQConnectionFactory) connectionFactory).setTrustAllPackages(true);
        //创建连接
        Connection connection=connectionFactory.createConnection();
        //连接启动
        connection.start();
        //通过连接获得session
        Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //创建目标
        Destination destination=session.createQueue(queueName);
        //创建一个生产者
        MessageProducer producer=session.createProducer(destination);
        for (int i = 0; i < 100; i++) {
            //创建消息
            User user=new User();
            user.setAge(i);
            user.setName((i%2==0? "Mr":"Miss")+String.valueOf((char)(int)(Math.random()*26+'A'))+String.valueOf((char)(int)(Math.random()*26+'a')));
            user.setSex(i%2==0? "男":"女");
            user.setSalary((int)(Math.random()*10000));
            user.setNumber(UUID.randomUUID().toString());
            ArrayList<User> users=new ArrayList<User>();
            HashMap<String,User> map=new HashMap<String, User>();
            map.put(String.format("第%d个", i),user);
            users.add(user);
            ObjectMessage message=session.createObjectMessage(map);
            //TextMessage message=session.createTextMessage(String.format("hello Mq:%d", i));
            //发布消息
            producer.send(message);
            System.out.println("发送消息:"+message.getObject());
            Thread.sleep(1);
        }
        //关闭连接
        connection.close();

    }
}
