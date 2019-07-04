package rpf.study.kafka.App;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import rpf.study.kafka.socket.WebSocketServer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KafkaConsumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("application-context2.xml");
        if (context != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
            System.out.println("消息中心启动：" + formatter.format(new Date(context.getStartupDate())));
        }
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8081;
        }
        new WebSocketServer(port).run();
    }
}
