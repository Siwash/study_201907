package topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopicProducer {
    private static final String url="tcp://localhost:61616";
    private static final String queueName="queue";

    public static void main(String[] args) throws JMSException, InterruptedException {
        //创建连接工厂
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(url);
        //创建连接
        Connection connection=connectionFactory.createConnection();
        //连接启动
        connection.start();
        //通过连接获得session
        Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //创建目标
        Destination destination=session.createTopic(queueName);
        //创建一个生产者
        MessageProducer producer=session.createProducer(destination);
        for (int i = 0; i < 10000; i++) {
            //创建消息
            TextMessage message=session.createTextMessage(String.format("hello Mq:%d", i));
            //发布消息
            producer.send(message);
            System.out.println("发送消息:"+message.getText());
            Thread.sleep(500);
        }
        //关闭连接
        connection.close();

    }
}
