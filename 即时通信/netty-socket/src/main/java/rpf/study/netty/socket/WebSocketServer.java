package rpf.study.netty.socket;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * service启动类，绑定了端口号和业务处理WebSocketServerHandler类
 * */
public class WebSocketServer {
	private int port;
	public static ChannelHandlerContext ctx = null;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public WebSocketServer() {
		
	}
	
	public WebSocketServer(int port) {
		this.port = port;
	}

	public void run() {
		//netty手动实现服务器的引导类，都会用这个对象创建
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		//设置管道工厂初始化所需要的参数类WebSocketServerPipelineFactory
		bootstrap.setPipelineFactory(new WebSocketServerPipelineFactory());
		System.out.println("Web socket server starting...");
		//申明server的类型为socket，端口地址为port
		bootstrap.bind(new InetSocketAddress(port));
		System.out.println("Web socket server started at port " + port + '.');
//		System.out.println("Open your browser and navigate to http://localhost:" + port + '/');
	}

	public static void main(String[] args) {
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 8081;
		}
		new WebSocketServer(port).run();
	}
}