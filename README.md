# TelegramBirthdayDexsysBot

Телеграм-бот для позравлений с днем рождения.
Принцип работы:

1. При диалоге с чат-ботом, отображается меню, в котором присутствует кнопка авторизации.
При ее нажатии - берется телефон из вашего телеграм-профиля и сравнивается ее с базой данных сотрудников (на Mock-сервере)
2. Если в ней присутствует пользователь с данным телефоном, данный профиль пользователя сохраняется в базе данных самого приложения. Если же такого телефона не найдено - выводится соответствующее сообщение
3. Далее пользователю становится доступно меня авторизованного пользователя где он может посмотреть информаацию о себе и о всех других авторизованныъ польхователях.
 Также возможно вручную изменить дату вашего рождения и выйти из приложения (удалить данные из базы данных самого приложения)

4. Добавлен rest-controller, являющийся копией контроллера Mock-сервера, а данное приложение играет роль прокси-сервера.
5. Также возможна функция отключения Mock-servera (useMock = false в файле application.yaml).

6. Приложение и соответствующая база данных (PostgreSQL) развернута на Heroku.
