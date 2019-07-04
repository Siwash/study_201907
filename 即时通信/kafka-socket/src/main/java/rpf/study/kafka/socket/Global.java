package rpf.study.kafka.socket;

import org.jboss.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
/**
 * 保存所有的连接到socket的用户id和通信管道channel
 * **/
public class Global {

	public static Map<String, Map<Integer, Channel>> ctxs= new HashMap<>();

}
