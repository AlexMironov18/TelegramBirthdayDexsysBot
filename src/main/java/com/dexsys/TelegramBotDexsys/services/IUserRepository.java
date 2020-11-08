package com.dexsys.TelegramBotDexsys.services;

import com.dexsys.TelegramBotDexsys.domain.services.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<User, String> {
}
