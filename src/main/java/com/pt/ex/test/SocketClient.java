package com.pt.ex.test;

import java.io.DataInputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/**
 * Created by can on 2017/6/2.
 */

public class SocketClient {
    private Socket socket;
    private OutputStream socketOutput;
    private DataInputStream dataInputStream;
    private String ip;
    private int port;
    private ClientCallback listener = new ClientCallbacker();

    Thread thread;
    private boolean PersonClose=false;
    public SocketClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public boolean isConnect() {
        if (socket != null) {
            return !socket.isClosed();

        } else {
            return false;
        }

    }

    public void connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                socket = new Socket();
                InetSocketAddress socketAddress = new InetSocketAddress(ip, port);
                try {
                    socket.connect(socketAddress);
                    socketOutput = socket.getOutputStream();
                    socket.setKeepAlive(true); //設定長連接
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    thread = new Thread(Recive);
                    thread.setName("lycodes/jag/socket");
                    thread.start();

                    if (listener != null)
                        listener.onConnect(socket);
                } catch (Exception e) {
                    if (listener != null)
                        listener.onConnectError(socket, e.getMessage());
                }

            }
        }).start();
    }

    public void disconnect() {
        PersonClose=true;
        try {
            if (thread != null)
                thread.interrupt();
            //   thread.stop();
            if (dataInputStream != null)
                dataInputStream.close();

            if (socketOutput != null)
                socketOutput.close();

            if (socket != null)
                socket.close();
        } catch (Exception e) {

        }

    }

    public void send(final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] jsonByte = message.getBytes();
                byte[] intbyte = intToByteArray(jsonByte.length);

                try {
                    socketOutput.write(intbyte);
                    socketOutput.write(jsonByte);
                    socketOutput.flush();
                } catch (Exception e) {
                    System.out.println("IOException: ");
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onConnectError(socket, e.getMessage());
                    } else {
                        disconnect();
                    }
                }
            }
        }).start();

    }

    private boolean getAppStatus() {
        return true;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    private Runnable Recive = new Runnable() {
        @Override
        public void run() {
        	System.out.println("ThreadStartRecive");
            while (socket.isConnected() && !socket.isClosed()) {   // each line must end with a \n to be received
                if (getAppStatus()) {

                    try {
                        if (dataInputStream.available() > 36864) {
                        	System.out.println("isServiceRunning"+ dataInputStream.available());
                            if (listener != null) {
                                listener.onConnectError(socket, "");
                            } else {
                                disconnect();
                            }
                        } else {
                            byte[] buffer = new byte[4];
                            dataInputStream.readFully(buffer); //直到讀滿，否則阻塞。
                            int count = IntLength(buffer);
                            byte[] b = new byte[count];
                            dataInputStream.readFully(b); //直到讀滿，否則阻塞
                            if (listener != null) {
                                listener.onMessage(new String(b, Charset.forName("UTF-8")));
                            }
                        }

                    } catch (Exception e) {
                        if (listener != null) {
                            if(!PersonClose){
                                listener.onConnectError(socket, "");
                            }

                        }
                        removeClientCallback();
                        disconnect();

                    }
                } else {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            if (!socket.isConnected() || socket.isClosed()) {
                if (listener != null) {
                    listener.onDisconnect(socket, "Disconnect");
                }
            }

        }
    };

    public int IntLength(byte[] buf) {
        return java.nio.ByteBuffer.wrap(buf).order(ByteOrder.BIG_ENDIAN).getInt();
    }

    public void setClientCallback(ClientCallback listener) {
        this.listener = listener;
    }

    public void removeClientCallback() {
        this.listener = null;
    }
    
    public interface ClientCallback {
        void onMessage(String message);

        void onConnect(Socket socket);

        void onDisconnect(Socket socket, String message);

        void onConnectError(Socket socket, String message);
    }
    
    class ClientCallbacker implements ClientCallback {

		@Override
		public void onMessage(String message) {
			System.out.println("onMessage: "+message);
		}

		@Override
		public void onConnect(Socket socket) {
			System.out.println("onConnect: ");
		}

		@Override
		public void onDisconnect(Socket socket, String message) {
			System.out.println("onDisconnect: "+message);
		}

		@Override
		public void onConnectError(Socket socket, String message) {
			System.out.println("onConnectError: "+message);
		}
    	
    }
    
    public static void main(String[] args) {
    	SocketClient sc = new SocketClient("47.75.231.120", 7443);
    	try {
    		sc.connect();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//sc.send("{\"c\":1,\"lo\":8720529,\"pwd\":\"pOzf5ut\",\"price_mode\":0,\"locale\":\"en\"}");
    	
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	System.out.println("101");
    	sc.send("{\"c\":101,\"locale\":\"tw\"}");
    	
    	System.out.println("5");
    	sc.send("{\"c\":5,\"cmd\":1,\"a\":0,\"s\":\"BTCEURbo\",\"sec\":30,\"key\":\"A1234567\"}");
    	
    	
    	
    	
    	
//    	System.out.println("5");
//    	sc.send("{\"c\":5,\"cmd\":1,\"a\":0,\"s\":\"BTCEURbo\",\"sec\":30,\"key\":\"A1234567\"}");
    	
	}
}
