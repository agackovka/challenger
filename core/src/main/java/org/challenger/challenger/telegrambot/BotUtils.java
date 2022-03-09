package org.challenger.challenger.telegrambot;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class BotUtils {

	public static SendMessage getText(String chatId, String text) {
		return new SendMessage(chatId, text);
	}

	public static SendMessage buildKeyboard(String chatId, String text, InlineKeyboardButton... buttons) {
		SendMessage sendMessage = getText(chatId, text);
		sendMessage.setReplyMarkup(buildKeyboard(buttons));
		return sendMessage;
	}

	public static SendMessage buildKeyboard(String chatId, String text, List<InlineKeyboardButton> buttons) {
		SendMessage sendMessage = getText(chatId, text);
		sendMessage.setReplyMarkup(buildKeyboard(buttons));
		return sendMessage;
	}

	public static ReplyKeyboard buildKeyboard(InlineKeyboardButton... buttons) {
		List<List<InlineKeyboardButton>> lists = Arrays.stream(buttons)
			.map(List::of)
			.toList();
		return new InlineKeyboardMarkup(lists);
	}

	public static ReplyKeyboard buildKeyboard(List<InlineKeyboardButton> buttons) {
		List<List<InlineKeyboardButton>> lists = buttons.stream()
			.map(List::of)
			.toList();
		return new InlineKeyboardMarkup(lists);
	}

	public static InlineKeyboardButton buildButton(String text, String cbData) {
		InlineKeyboardButton button = new InlineKeyboardButton();
		button.setText(text);
		button.setCallbackData(cbData);
		return button;
	}

	public static String appendParam(String command, Object... param) {
		String joinedParams = StringUtils.join(param, " ");
		return command + " " + joinedParams;
	}
}
