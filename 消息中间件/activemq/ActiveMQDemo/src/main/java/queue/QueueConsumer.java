package queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

public class QueueConsumer {
    private static final String queueName="HelloActiveMQ";
    private static final String url="tcp://localhost:61616";

    public static void main(String[] args) throws JMSException {
        //获取连接工厂
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(url);
//        List<String> list=new ArrayList<String>();
//        list.add("pojo.User");
//        ((ActiveMQConnectionFactory) connectionFactory).setTrustedPackages(list);//设置指定的package
        ((ActiveMQConnectionFactory) connectionFactory).setTrustAllPackages(true);
        //获取连接
        Connection connectionn=connectionFactory.createConnection();
        //启动连接
        connectionn.start();
        //创建session
        Session session=connectionn.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //绑定地址
        Destination destination=session.createQueue(queueName);
        //创建一个消费者对象
        MessageConsumer consumer=session.createConsumer(destination);
        //创建监听器
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                //TextMessage textMessage= (TextMessage) message;
                ObjectMessage Objmessage= (ObjectMessage) message;
                try {
                    System.out.println("接收到消息:"+Objmessage.getObject());
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
