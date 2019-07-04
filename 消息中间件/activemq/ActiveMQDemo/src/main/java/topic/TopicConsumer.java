package topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopicConsumer {
    private static final String queueName = "queue";
    private static final String url = "tcp://localhost:61616";

    public static void main(String[] args) throws JMSException {
        //获取连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        //获取连接
        Connection connectionn = connectionFactory.createConnection();
        //启动连接
        connectionn.start();
        //创建session
        Session session = connectionn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //绑定地址
        Destination destination = session.createTopic(queueName);
        //创建一个消费者对象
        MessageConsumer consumer = session.createConsumer(destination);
        //创建监听器
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    Thread.sleep(500);
                    System.out.println("接收到消息:" + textMessage.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
