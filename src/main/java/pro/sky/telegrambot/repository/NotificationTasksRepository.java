package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.NotificationTasks;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationTasksRepository extends JpaRepository<NotificationTasks, Long> {

    List<NotificationTasks> findNotificationTasksByNotificationTime(LocalDateTime notificationTime);
}
