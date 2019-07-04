package queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import pojo.User;

import javax.annotation.Resource;
import javax.jms.*;

@Component
public class QueueProducer {
    @Autowired
    JmsTemplate jmsTemplate;
    @Resource(name = "queueDestination")
    Destination destination;
    public  void  sendMessage(final User msg){
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                final ObjectMessage objectMessage = session.createObjectMessage(msg);
                return objectMessage;
            }
        });
        System.out.println("发送消息："+msg);
    }
}
