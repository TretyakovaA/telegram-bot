package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTasks;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.time.format.DateTimeFormatter;
import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            // Process your updates here
            long chatId = update.message().chat().id();
            if (update.message().text().equals("/start")){
                SendResponse response = telegramBot.execute(new SendMessage(chatId, "Приветствую! Пожалуйста, введите сообщение-напоминалку в формате: дд.мм.гггг чч:мм текст напоминалки"));
            } else {
                Pattern pattern = Pattern.compile ("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
                Matcher matcher = pattern.matcher(update.message().text());
                if (matcher.matches()) {
                    String date = matcher.group(1);
                    String item = matcher.group(3);
                    NotificationTasks task = new NotificationTasks(update.message().chat().id(), item, LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
                } else {
                    SendResponse response = telegramBot.execute(new SendMessage(chatId, " Ошибка! Введите сообщение в формате: дд.мм.гггг чч:мм текст напоминалки"));
                }

            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


}
