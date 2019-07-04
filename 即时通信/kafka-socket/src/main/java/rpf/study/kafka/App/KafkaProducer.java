package rpf.study.kafka.App;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import rpf.study.kafka.producer.KafkaProducerServer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class KafkaProducer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("application-context.xml");
        if (context != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
            System.out.println("消息中心启动：" + formatter.format(new Date(context.getStartupDate())));
        }
        KafkaProducerServer kafkaProducer = (KafkaProducerServer) context.getBean(KafkaProducerServer.class);
        String topic = "orderTopic";
        String value = "test";
        String ifPartition = "0";
        Integer partitionNum = 1;
        String role = "test";//用来生成key
        Map<String,Object> res = kafkaProducer.sndMesForTemplate(topic, value, ifPartition, partitionNum, role);
        System.out.println("测试结果如下：===============");
        String message = (String)res.get("message");
        String code = (String)res.get("code");
        System.out.println("code:"+code);
        System.out.println("message:"+message);
    }
}
