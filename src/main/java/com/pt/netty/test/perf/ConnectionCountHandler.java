package com.pt.netty.test.perf;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class ConnectionCountHandler extends ChannelInboundHandlerAdapter{

	 private AtomicInteger nConnection = new AtomicInteger();

	    public ConnectionCountHandler() {
	        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
	            System.out.println("connections: " + nConnection.get());
	        }, 0, 2, TimeUnit.SECONDS);

	    }

	    @Override
	    public void channelActive(ChannelHandlerContext ctx) {
	        nConnection.incrementAndGet();
//	        System.out.println("+connections: " + nConnection.get());
	    }

	    @Override
	    public void channelInactive(ChannelHandlerContext ctx) {
	        nConnection.decrementAndGet();
//	        System.out.println("-connections: " + nConnection.get());
	    }
	
}
