package rpf.study.kafka.simpleUse;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.ArrayList;
import java.util.Properties;

public class Topic {
    public static void main(String[] args) {
        Properties properties=new Properties();
        properties.put("bootstrap.servers","127.0.0.1:9092");
        AdminClient adminClient=AdminClient.create(properties);
        ArrayList<NewTopic> topics = new ArrayList<NewTopic>();
        NewTopic topic = new NewTopic("topicst", 1, (short) 1);
        topics.add(topic);
        CreateTopicsResult result=adminClient.createTopics(topics);
        try {
            result.all().get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
