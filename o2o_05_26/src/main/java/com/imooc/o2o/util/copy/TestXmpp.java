package com.imooc.o2o.util.copy;

import java.io.File;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.filetransfer.FileTransfer;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;

public class TestXmpp {

	public static void main(String[] args) {

		String user = "user@testHost/spark";
		String host = "127.0.0.1";
		int port = 5222;
		String username = "daodaoling";
		String password = "123456";
		ConnectionConfiguration config = new ConnectionConfiguration(host, port);
		config.setCompressionEnabled(true);
		// config.setSASLAuthenticationEnabled(true);

		XMPPConnection connection = new XMPPConnection(config);

		try {
			connection.connect();

			// connection.login(username, password);

			sendFile(user,getFile(),connection );
			sendTextMessage(user, connection);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}

	}

	public static File getFile() {
		File file = new File("D:/test.jpg");
		return file;
	}

	public static void sendFile(String user, File file, XMPPConnection connection) throws Exception {
		FileTransferManager manager = new FileTransferManager(connection);
		OutgoingFileTransfer transfer = manager.createOutgoingFileTransfer(user);
		long timeOut = 1000000;
		long sleepMin = 3000;
		long spTime = 0;
		int rs = 0;
		System.out.println("进入sendfile！！");
		transfer.sendFile(file, "pls re file!");
		rs = transfer.getStatus().compareTo(FileTransfer.Status.complete);
		while (rs != 0) {
			System.out.println("进入while循环！！！");
			rs = transfer.getStatus().compareTo(FileTransfer.Status.complete);
			spTime = spTime + sleepMin;
			if (spTime > timeOut) {
				return;
			}
			Thread.sleep(sleepMin);
		}

	}

	public static void sendTextMessage(String user, XMPPConnection connection) throws Exception {
		System.out.println("进入sendTextMessage！！！");
		
		Chat chat = connection.getChatManager().createChat(user, new MessageListener() {

			public void processMessage(Chat chat, Message message) {
				System.out.println("Received message: " + message);
			}
		});
		
		chat.sendMessage("Hi Test Send Message........!");

	}

}
