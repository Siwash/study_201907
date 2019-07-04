package queue;

import org.springframework.stereotype.Component;

import javax.jms.*;

@Component("messageListener")
public class QueueConstumer implements MessageListener {
    public void onMessage(Message message) {
        ObjectMessage textMessage= (ObjectMessage) message;
        try {
            System.out.println("收到消息："+textMessage.getObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
