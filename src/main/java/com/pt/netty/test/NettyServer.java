package com.pt.netty.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {

	public static void main(String[] args) {
		ServerBootstrap sb = new ServerBootstrap();
		
		sb.group(new NioEventLoopGroup(),new NioEventLoopGroup())
		.channel(NioServerSocketChannel.class)
		.childHandler(new ChannelInitializer<NioSocketChannel>() {
			protected void initChannel(NioSocketChannel ch) throws Exception {
			
				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
					
					@Override
					protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
						System.out.println(msg);
					}
				});
			};
		}).bind(8000).addListener(future -> {
			if(future.isSuccess()) {
				System.out.println("sucess connected.");
			}else {
				System.out.println("fail connected.");
			}
		});
		
	}
}
