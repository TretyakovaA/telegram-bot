package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.NotificationTasks;

public interface NotificationTasksRepository extends JpaRepository<NotificationTasks, Long> {
}
