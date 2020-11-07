package com.dexsys.TelegramBotDexsys.services;

import com.dexsys.TelegramBotDexsys.domain.services.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
