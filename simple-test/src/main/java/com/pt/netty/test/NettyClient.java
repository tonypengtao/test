package com.pt.netty.test;

import java.util.Date;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClient {

	public static void main(String[] args) throws InterruptedException {
		Bootstrap bs = new Bootstrap();
		bs.group(new NioEventLoopGroup())
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<Channel>() {
				
				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast(new StringEncoder());
				}
			});
		
		Channel channel = bs.connect("127.0.0.1",8000).channel();
		
		while (true) {
			channel.writeAndFlush(new Date() + "hello world.");
			Thread.sleep(2000);
		}
	}
}
