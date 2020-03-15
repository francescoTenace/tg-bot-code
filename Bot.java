import org.telegram.telegrambots.api.methods.ActionType;
import org.telegram.telegrambots.api.methods.send.SendChatAction;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Random;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Date;


public class Bot extends TelegramLongPollingBot {

	@Override
	public void onUpdateReceived(Update update) {

		if(update.hasMessage() && update.getMessage().hasText()){

			String text_in = update.getMessage().getText();
			long chat_id = update.getMessage().getChatId();

			SendMessage message = new SendMessage().setChatId(chat_id);
			SendMessage replyMessage = new SendMessage().setChatId(chat_id);
			SendChatAction chatAction = new SendChatAction().setChatId(chat_id);

			if(update.getMessage().getChat().isUserChat()) {
				switch (text_in.toLowerCase()) {
					case "/start":
						chatAction.setAction(ActionType.TYPING);
						try {
							sendChatAction(chatAction);
						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
						message.setText("Hello World!\nI'm Debbie, to know what I can do, send /help");
						try {
							sendMessage(message);
						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
						break;
					case "/help":
						chatAction.setAction(ActionType.TYPING);
						try {
							sendChatAction(chatAction);
						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
						message.setText("Here's a list of the commands I can execute:\n/help - display this text\n/toss - definitely the best way to take important decisions\n/ping - display the current ping in milliseconds");
						try {
							sendMessage(message);
						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
						break;
					case "/toss":
						chatAction.setAction(ActionType.TYPING);
						try {
							sendChatAction(chatAction);
						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
						Random random = new Random();
						message.setText("Alright... Heads or tails?");
						try {
							sendMessage(message);
						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
						if (((random.nextInt(2) + 1) % 2) == 0) {
							message.setText("Heads!");
						} else {
							message.setText("Tails!");
						}
						try {
							sendMessage(message);
						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
						break;
					case "/ping":
						try {
							String hostAddress = "localhost";
							int port = 80;
							long timeToRespond = 0;

							InetAddress inetAddress = InetAddress.getByName(hostAddress);
							InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, port);

							SocketChannel sc = SocketChannel.open();
							sc.configureBlocking(true);

							Date start = new Date();
							if (sc.connect(socketAddress)) {
								Date stop = new Date();
								timeToRespond = (stop.getTime() - start.getTime());
							}

							message.setText("Pong!\n" + timeToRespond + " ms");

							try {
								sendMessage(message);
							} catch (TelegramApiException e) {
								e.printStackTrace();
							}

						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					default:
						message.setText("Sorry, I couldn't understand your command\nHere's a list of the commands I can execute:\n/help - display this text\n/toss - definitely the best way to take important decisions");
						try {
							sendMessage(message);
						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
						break;
				}
			} else if(update.getMessage().getChat().isGroupChat() || update.getMessage().getChat().isSuperGroupChat()) {
				String userChat = update.getMessage().getFrom().getId().toString();
				int userMessageId = update.getMessage().getMessageId();

				switch(text_in.toLowerCase()){
					case "/help@f10acebot":
						chatAction.setAction(ActionType.TYPING);
						try {
							sendChatAction(chatAction);
						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
						message.setText("Here's a list of the commands I can execute:\n/help - display this text\n/toss - definitely the best way to take important decisions\n/ping - display the current ping in milliseconds");
						message.setChatId(userChat);
						try {
							sendMessage(message);
						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
						replyMessage.setText("Help message sent in private chat");
						replyMessage.setReplyToMessageId(userMessageId);
						try {
							sendMessage(replyMessage);
						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
						break;
					case "/toss@f10acebot":
						chatAction.setAction(ActionType.TYPING);
						try {
							sendChatAction(chatAction);
						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
						Random random = new Random();
						message.setText("Alright... Heads or tails?");
						try {
							sendMessage(message);
						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
						if (((random.nextInt(2) + 1) % 2) == 0) {
							replyMessage.setText("Heads!");
						} else {
							replyMessage.setText("Tails!");
						}
						replyMessage.setReplyToMessageId(userMessageId);
						try {
							sendMessage(replyMessage);
						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
						break;
					case "/ping@f10acebot" :
						try {
							String hostAddress = "0.0.0.0";
							int port = 8080;
							float timeToRespond = 0;
							InetAddress inetAddress = InetAddress.getByName(hostAddress);
							InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, port);
							SocketChannel sc = SocketChannel.open();
							sc.configureBlocking(true);
							Date start = new Date();
							if (sc.connect(socketAddress)) {
								Date stop = new Date();
								timeToRespond = (stop.getTime() - start.getTime());
							}
							replyMessage.setText("Pong!\n" + timeToRespond + " ms");
							replyMessage.setReplyToMessageId(userMessageId);
							try {
								sendMessage(replyMessage);
							} catch (TelegramApiException e) {
								e.printStackTrace();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
				}
			}
		}
	}

	@Override
	public String getBotUsername() {
		return "F10acebot";
	}

	@Override
	public String getBotToken() {
		return "1072901982:AAFKjpqvYzenfopz0UowPeUmDOtYmxA2ZdM";
	}
}