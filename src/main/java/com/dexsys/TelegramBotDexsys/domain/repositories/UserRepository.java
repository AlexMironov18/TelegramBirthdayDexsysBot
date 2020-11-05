//package com.dexsys.TelegramBotDexsys.domain.repositories;
//
//import com.dexsys.TelegramBotDexsys.domain.services.entities.User;
//import com.dexsys.TelegramBotDexsys.services.IRepository;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@AllArgsConstructor
//@Repository
//@Slf4j
//@Data
//public class UserRepository implements IRepository {
//
//    //user repository
//    private Map<String, User> userMap;
//    // chatId - phone map, to get a phone number, using chatId
//    private Map<String, String> chatIdMap;
//
//
//    @Override
//    public void addUser(User user) {
//        if (!userMap.containsKey(user.getPhone())) {
//            userMap.put(user.getPhone(), user);
//            chatIdMap.put(user.getChatId(), user.getPhone());
//            log.info("Добавлен пользователь в базу данных с телефоном {}", user.getPhone());
//        }
//    }
//
//    @Override
//    public List<User> getUserList() {
//        return userMap.values().stream().collect(Collectors.toList());
//    }
//
//    @Override
//    public User getUser(String chatId) {
//        return userMap.get(chatIdMap.get(chatId));
//    }
//
//    @Override
//    public boolean deleteUser(String chatId) {
//        Object user = userMap.remove(chatIdMap.get(chatId));
//        chatIdMap.remove(chatId);
//        return user != null;
//    }
//}
