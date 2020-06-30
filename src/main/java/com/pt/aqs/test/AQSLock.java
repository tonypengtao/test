package com.pt.aqs.test;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

@SuppressWarnings("restriction")
public class AQSLock {

	
	private volatile int state = 0;
	
	private Thread lockHolder;
	
	private ConcurrentLinkedQueue<Thread> waiters = new ConcurrentLinkedQueue<Thread>();
	
	public boolean aquire() {
		Thread current = Thread.currentThread();

		int c = getState();
		if (c == 0) {
			if ((waiters.size() == 0 || current == waiters.peek()) && compareAndSwapState(0, 1)) {
				setLockHolder(current);
				return true;
			}
		}
		return false;
	}
	
	public void lock() {
		
		if(aquire()) {
			return;
		}
		
		Thread current = Thread.currentThread();
		waiters.add(current);
		
		for (;;) {
			
			if(current == waiters.peek() && aquire()) {
				//加锁成功，移除
				waiters.poll();
				return;
			}
			//阻塞线程操作
			LockSupport.park(current);
			
		}
		
	}
	
	
	public void unlock() {
		if(Thread.currentThread() != lockHolder) {
			throw new RuntimeException("lockholder is not current thread.");
		}
		int state = getState();
		if(compareAndSwapState(state, 0)) {
			setLockHolder(null);
			Thread first = waiters.peek();
			if(first != null) {
				LockSupport.unpark(first);
			}
		}
		
	}
	
	public final boolean compareAndSwapState(int except, int update) {
		return unsafe.compareAndSwapInt(this, stateOffset, except, update);
	}
	
	private static final sun.misc.Unsafe unsafe = UnsafeInstance.getUnsafe();
	
	private static final long stateOffset;
	
	
	static {
		try {
			stateOffset = unsafe.objectFieldOffset(AQSLock.class.getDeclaredField("state"));
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			throw new Error();
		}
	}


	public int getState() {
		return state;
	}


	public void setState(int state) {
		this.state = state;
	}


	public Thread getLockHolder() {
		return lockHolder;
	}


	public void setLockHolder(Thread lockHolder) {
		this.lockHolder = lockHolder;
	}
}
