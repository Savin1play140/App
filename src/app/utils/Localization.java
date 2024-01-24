package app.utils;

import java.util.HashMap;

import app.Main;
import app.logger.Logger;

public final class Localization {
	private static HashMap<String, String> ru_ru;
	private static HashMap<String, String> en_us;
	private static void initUS() {
		en_us = new HashMap<String, String>();
		en_us.put("window.init", "Created window successful");
		en_us.put("config.server", "Server address: "+Main.getConfig().get("serverHttp"));
		en_us.put("text.0", "Hello user.");
		en_us.put("text.1", "This jar file has compiled in release mode.");
		en_us.put("text.2", "This project for all.");
		en_us.put("text.3", "Version format: v.v_b");
		en_us.put("text.4", "   v - version");
		en_us.put("text.5", "   b - build");
		en_us.put("zip.unpack", "Unziping resources.zip...");
		en_us.put("zip.download", "Download resources.zip...");
		en_us.put("zip.error", "Directory and zip file not exist: ");
		en_us.put("gc.start", "GC starting...");
		en_us.put("main.exit", "exit...");
		en_us.put("main.sync", "sync data...");
		en_us.put("update.fail-conn", "You have not connnection or server is not connectable");
		en_us.put("update.skip", "updating skiped");
		en_us.put("update.install", "Installing update...");
		en_us.put("update.successful", "The update was successfully installed in");
		en_us.put("update.test", "Test connection, server: "+Main.getConfig().get("serverHttp"));
		en_us.put("update.server-old", "The server version has not yet been updated");
		en_us.put("update.last", "Latest version is already installed");
		en_us.put("update.getting", "Getting updates...");
		en_us.put("update.update-new", "Updating to a server older than installed");
		en_us.put("update.no-update", "Updates not found, you have latest updates");
		en_us.put("sound.wait", "waiting...");
		en_us.put("sound.play", "play sound...");
		en_us.put("sound.played", "sound played");
		en_us.put("math.static", "Math class loaded as static");
	}
	private static void initRU() {
		ru_ru = new HashMap<String, String>();
		ru_ru.put("main.successful", "завершено");
		ru_ru.put("window.init", "Окно создано успешно");
		ru_ru.put("config.server", "Адрес сервера: "+Main.getConfig().get("serverHttp"));
		ru_ru.put("text.0", "Привет пользователь.");
		ru_ru.put("text.1", "Этот jar-файл скомпилирован в режиме релиза.");
		ru_ru.put("text.2", "Этот проект для всех.");
		ru_ru.put("text.3", "Формат версии: v.v_b");
		ru_ru.put("text.4", "   v - версия");
		ru_ru.put("text.5", "   b - сборка");
		ru_ru.put("zip.unpack", "Распаковка resources.zip...");
		ru_ru.put("zip.download", "Скачать resources.zip...");
		ru_ru.put("zip.error", "Каталог и zip-файл не существуют: ");
		ru_ru.put("gc.start", "Очистка запускается...");
		ru_ru.put("main.exit", "выход...");
		ru_ru.put("main.sync", "синхронизация данных...");
		ru_ru.put("update.fail-conn", "У вас нет подключения или сервер не доступен");
		ru_ru.put("update.skip", "обновление пропущено");
		ru_ru.put("update.install", "Устанавливается обновление...");
		ru_ru.put("update.successful", "Обновление было успешно установлено в");
		ru_ru.put("update.test", "Проверка подключения, сервер: "+Main.getConfig().get("serverHttp"));
		ru_ru.put("update.server-old", "Версия на сервера еще не обновлена");
		ru_ru.put("update.last", "Последняя версия уже установлена");
		ru_ru.put("update.getting", "Получение обновлений...");
		ru_ru.put("update.update-new", "Обновление на сервер старше установленного");
		ru_ru.put("update.no-update", "Обновления не найдены, у вас есть последние обновления");
		ru_ru.put("sound.wait", "ожидающий...");
		ru_ru.put("sound.play", "воспроизвести звук...");
		ru_ru.put("sound.played", "звук воспроизведено");
	}
	static {
		initUS();
		initRU();
	}
	private static String lang = "ru_ru";
	public static void setLang(String l) {
		lang = l;
	}
	public static String getText(String key) {
		String message = "";
		switch (lang) {
		case "ru_ru":
			message = ru_ru.get(key);
			break;
		case "en_us":
			message = en_us.get(key);
			break;
		default:
			Logger.error("Language "+lang+" not found");
			break;
		}
		if (message == null) return key;
		return message;
	}
}
