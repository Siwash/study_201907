package rpf.study.netty.socket;

import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.websocket.WebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.HOST;

public class WebSocketServerHandler extends SimpleChannelUpstreamHandler {
	
	private static final String WEBSOCKET_PATH = "/websocket";
	private WebSocketServerHandshaker handshaker;
	//通道建立时候回到
	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("Open connection from: " + e.getChannel().getRemoteAddress());
	}
	//通道结束时候回调
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("Close connection from: " + e.getChannel().getRemoteAddress());
	}
	//消息接收函数
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		Object msg = e.getMessage();
		System.out.println(msg);
		if (msg instanceof TextWebSocketFrame){
			System.out.println("--------------这是消息体： "+msg);
			sendMessageToAll(new Date() +"|在线用户-"+ctx.getChannel().getId()+" 说："+((TextWebSocketFrame) msg).getText());
		}
		if (msg instanceof HttpRequest) {
			handleHttpRequest(ctx, (HttpRequest) msg);
			System.out.println("-------------这是http： "+msg);
		} else if (msg instanceof WebSocketFrame) {
			System.out.println("---------------这是socket： "+msg);
			handleWebSocketFrame(ctx, (WebSocketFrame) msg);
		}
	}
//http握手器
	private void handleHttpRequest(ChannelHandlerContext ctx, HttpRequest req) throws Exception {
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(req),
				null, false);
		handshaker = wsFactory.newHandshaker(req);
		if (handshaker == null) {
			wsFactory.sendUnsupportedWebSocketVersionResponse(ctx.getChannel());
		} else {
			handshaker.handshake(ctx.getChannel(), req).addListener(WebSocketServerHandshaker.HANDSHAKE_LISTENER);
		}
		WebSocketServer.ctx = ctx;
		String requestUri = req.getUri();
		// 保存用户连接信息，支持单用户多点登录
		if (requestUri.contains(WEBSOCKET_PATH) && requestUri.length() > WEBSOCKET_PATH.length()) {
			String userId = requestUri.substring(WEBSOCKET_PATH.length() + 1);
			Map<Integer, Channel> channelMap = Global.ctxs.get(userId);
			if (channelMap == null) {
				channelMap = new HashMap<>();
				Global.ctxs.put(userId, channelMap);
			}
			channelMap.put(ctx.getChannel().getId(), ctx.getChannel());
		}
	}
//在此方法种做退出判断，并移除Global种的用户
	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
		// Check for closing frame
		if (frame instanceof CloseWebSocketFrame) {
			// 查找相应通道关闭并移除
			handshaker.close(ctx.getChannel(), (CloseWebSocketFrame) frame);
			WebSocketServer.ctx = null;

			Iterator<String> keyIter = Global.ctxs.keySet().iterator();
			boolean isChannelFound = false;
			while (keyIter.hasNext()) {
				Map<Integer, Channel> channelMap = Global.ctxs.get(keyIter.next());
				Iterator<Integer> channelIter = channelMap.keySet().iterator();
				//遍历Global
				while (channelIter.hasNext()) {
					if (channelIter.next().intValue() == ctx.getChannel().getId().intValue()) {
						channelMap.remove(channelIter);
						isChannelFound = true;
						break;
					}
				}
				if (isChannelFound) {
					break;
				}
			}
			return;
		}

	}
//打印异常的回调函数，通过channel.close方法终端socket连接
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		e.getCause().printStackTrace();
		e.getChannel().close();
		WebSocketServer.ctx = null;
	}
//获取socket连接的地址
	private static String getWebSocketLocation(HttpRequest req) {
		return "ws://" + req.getHeader(HOST) + WEBSOCKET_PATH;
	}
	
	public boolean sendMessageToAll(String message) {
		if (StringUtils.isNotEmpty(message)) {
			Iterator<String> userCtx = Global.ctxs.keySet().iterator();
			while (userCtx.hasNext()) {
				sendMessageToUser(message, userCtx.next());
			}
		}
		return true;
	}
	//主推消息的方法：channel.write
	public boolean sendMessageToUser(String message, String userId) {
		if (StringUtils.isNotEmpty(message) && Global.ctxs.containsKey(userId)) {
			Map<Integer, Channel> channelMap = Global.ctxs.get(userId);
			Iterator<Channel> channelCtx = channelMap.values().iterator();
			while(channelCtx.hasNext()) {
				Channel channel = channelCtx.next();
				if (channel != null && channel.isOpen()) {
					channel.write(new TextWebSocketFrame(message));
				} else {
					System.out.println("The channel is closed for user: " + userId + ", channelId: " + channel.getId());
					channelCtx.remove();
				}
			}
		}
		return true;
	}
	
	public boolean sendMessageToUsers(String message, String[] userIds) {
		if (StringUtils.isNotEmpty(message)) {
			for (String userId : userIds) {
				sendMessageToUser(message, userId);
			}
		}
		return true;
	}

}