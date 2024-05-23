package org.example;

import org.example.objects.*;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.GetMe;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MedicineBot implements LongPollingSingleThreadUpdateConsumer {

    private static final ArrayList<Hospital> hospitals = new ArrayList<>();
    private static final ArrayList<Specialization> specializations = new ArrayList<>();
    private static ArrayList<Doctor> doctors = new ArrayList<>();
    private static final ArrayList<User> users = new ArrayList<>();

    static {
        // Добавление больниц
        hospitals.add(new Hospital(1,"Городская клиническая больница № 1"));
        hospitals.add(new Hospital(2,"Центральная городская клиническая больница"));
        hospitals.add(new Hospital(3,"Городская клиническая больница № 4"));
        hospitals.add(new Hospital(4,"Городская клиническая больница № 5"));
        hospitals.add(new Hospital(5,"Городская клиническая больница № 7"));
        hospitals.add(new Hospital(6,"Больница скорой неотложной помощи (БСНП)"));
        hospitals.add(new Hospital(7,"Городская клиническая инфекционная больница"));
        hospitals.add(new Hospital(8,"АО \"ЖГМК \" Алматинская Железнодорожная Больница"));
        hospitals.add(new Hospital(9,"Новая больница"));

        // Специализаций
        specializations.add(new Specialization(1,"Педиатр"));
        specializations.add(new Specialization(2,"Терапевт"));
        specializations.add(new Specialization(3,"Хирург"));
        specializations.add(new Specialization(4,"Кардиолог"));
        specializations.add(new Specialization(5,"Доктор"));

        // Больница 9
        doctors.add(new Doctor(37,9,5,"Новый Доктор",777));

        // Больница 1
        doctors.add(new Doctor(1, 1, 1, "Доктор Иванов", 1000));
        doctors.add(new Doctor(2, 1, 2, "Доктор Петров", 1200));
        doctors.add(new Doctor(3, 1, 3, "Доктор Сидоров", 1100));
        doctors.add(new Doctor(4, 1, 4, "Доктор Смирнов", 1300));

// Больница 2
        doctors.add(new Doctor(5, 2, 1, "Доктор Кузнецов", 1050));
        doctors.add(new Doctor(6, 2, 2, "Доктор Орлов", 1150));
        doctors.add(new Doctor(7, 2, 3, "Доктор Николаев", 1250));
        doctors.add(new Doctor(8, 2, 4, "Доктор Захаров", 1350));

// Больница 3
        doctors.add(new Doctor(9, 3, 1, "Доктор Киселев", 1100));
        doctors.add(new Doctor(10, 3, 2, "Доктор Попов", 1200));
        doctors.add(new Doctor(11, 3, 3, "Доктор Савельев", 1150));
        doctors.add(new Doctor(12, 3, 4, "Доктор Власов", 1250));

// Больница 4
        doctors.add(new Doctor(13, 4, 1, "Доктор Романов", 1300));
        doctors.add(new Doctor(14, 4, 2, "Доктор Борисов", 1400));
        doctors.add(new Doctor(15, 4, 3, "Доктор Фролов", 1350));
        doctors.add(new Doctor(16, 4, 4, "Доктор Беляев", 1450));

// Больница 5
        doctors.add(new Doctor(17, 5, 1, "Доктор Миронов", 1500));
        doctors.add(new Doctor(18, 5, 2, "Доктор Алексеев", 1550));
        doctors.add(new Doctor(19, 5, 3, "Доктор Громов", 1600));
        doctors.add(new Doctor(20, 5, 4, "Доктор Васильев", 1650));

// Больница 6
        doctors.add(new Doctor(21, 6, 1, "Доктор Ершов", 1100));
        doctors.add(new Doctor(22, 6, 2, "Доктор Захаров", 1200));
        doctors.add(new Doctor(23, 6, 3, "Доктор Голубев", 1300));
        doctors.add(new Doctor(24, 6, 4, "Доктор Королёв", 1400));

// Больница 7
        doctors.add(new Doctor(25, 7, 1, "Доктор Лебедев", 1500));
        doctors.add(new Doctor(26, 7, 2, "Доктор Козлов", 1600));
        doctors.add(new Doctor(27, 7, 3, "Доктор Новиков", 1700));
        doctors.add(new Doctor(28, 7, 4, "Доктор Макаров", 1800));

// Больница 8
        doctors.add(new Doctor(29, 8, 1, "Доктор Ильин", 1400));
        doctors.add(new Doctor(30, 8, 1, "Доктор Павлов", 1450));
        doctors.add(new Doctor(31, 8, 2, "Доктор Федоров", 1500));
        doctors.add(new Doctor(32, 8, 2, "Доктор Пономарев", 1550));
        doctors.add(new Doctor(33, 8, 3, "Доктор Соболев", 1600));
        doctors.add(new Doctor(34, 8, 3, "Доктор Васильев", 1650));
        doctors.add(new Doctor(35, 8, 4, "Доктор Захаров", 1700));
        doctors.add(new Doctor(36, 8, 4, "Доктор Белов", 1750));

        doctors = DoctorRatingManager.loadRatings(doctors);
    }

    public static TelegramClient telegramClient;

    public MedicineBot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update) {
        if(update.hasMessage()){
            if(update.getMessage().hasText()){
                String text = update.getMessage().getText();
                long user_id = update.getMessage().getFrom().getId();
                String chat_id = String.valueOf(update.getMessage().getChatId());
                if(chat_id.equals(String.valueOf(user_id))){
                    // Обработка команды старт
                    if(text.equals("/start")||text.equals("\uD83D\uDD19 Назад")){
                        List<KeyboardRow> rows = new ArrayList<>();
                        {
                            KeyboardRow row = new KeyboardRow();
                            row.add("\uD83C\uDD95 Сделать запись");
                            rows.add(row);
                        }
                        {
                            KeyboardRow row = new KeyboardRow();
                            row.add("\uD83D\uDCC5 Мои записи");
                            rows.add(row);
                        }
                        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(rows);
                        markup.setResizeKeyboard(true);
                        markup.setOneTimeKeyboard(true);
                        sendMessage(chat_id,
                                "\uD83D\uDC4B Привет! Добро пожаловать в наш сервис записи на прием к врачу. Что хотите сделать?",
                               markup);
                        return;
                    }else if(text.equals("\uD83C\uDD95 Сделать запись")){
                        // Создание записи
                        List<KeyboardRow> rows = new ArrayList<>();
                        for (Hospital hospital:hospitals){
                            KeyboardRow row = new KeyboardRow();
                            row.add(hospital.getName());
                            rows.add(row);
                        }
                        {
                            KeyboardRow row = new KeyboardRow();
                            row.add("\uD83D\uDD19 Назад");
                            rows.add(row);
                        }
                        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(rows);
                        markup.setResizeKeyboard(true);
                        markup.setOneTimeKeyboard(true);
                        sendMessage(chat_id,
                                "\uD83C\uDFE5 Выберите нужную поликлинику:",
                                markup);
                        return;
                    }else if(text.equals("\uD83D\uDCC5 Мои записи")){
                        // Список записей пользователя
                        List<KeyboardRow> rows = new ArrayList<>();
                        {
                            KeyboardRow row = new KeyboardRow();
                            row.add("\uD83D\uDD19 Назад");
                            rows.add(row);
                        }
                        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(rows);
                        markup.setResizeKeyboard(true);
                        markup.setOneTimeKeyboard(true);
                        String result = "";
                        int i = 0;
                        for (User user:users){
                            if(user.getUser_id()==user_id){
                                i++;
                                try {
                                    if(user.getTime()<=System.currentTimeMillis()/1000){
                                        result+=i+". <b>✅ Статус:</b> Прём завершен\n"+user.getText()+"\n" +
                                                "<a href='https://t.me/"+ telegramClient.execute(GetMe.builder().build()).getUserName() +"?start=delete="+user.getId()+"'>Удалить запись</a>\n" +
                                                "<a href='https://t.me/"+ telegramClient.execute(GetMe.builder().build()).getUserName() +"?start=rate="+user.getId()+"'>Оценить услугу</a>" +
                                                "\n";
                                    }else {
                                        result+=i+". <b>⏱ Статус:</b> В ожиданий\n"+user.getText()+"\n" +
                                                "<a href='https://t.me/"+ telegramClient.execute(GetMe.builder().build()).getUserName() +"?start=delete="+user.getId()+"'>Удалить запись</a>\n" +
                                                "<a href='https://t.me/"+ telegramClient.execute(GetMe.builder().build()).getUserName() +"?start=rate="+user.getId()+"'>Оценить услугу</a>" +
                                                "\n";
                                    }
                                } catch (TelegramApiException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        sendMessage(chat_id,
                                "<b>\uD83D\uDCC5 Ваши записи:</b>\n"+result,
                                markup);
                        return;
                    }else if(text.startsWith("/start delete=")){
                        // Удаление записи
                        for (User u:users){
                            if(u.getId().equals(text.substring(text.lastIndexOf("=")+1))){
                                users.remove(u);
                                sendMessage(chat_id,"✅ Запись удалена");
                                break;
                            }
                        }
                    }else if(text.startsWith("/start rate=")){
                        // Рейтинг доктора
                        for (User u:users){
                            if(u.getId().equals(text.substring(text.lastIndexOf("=")+1))){
                                List<InlineKeyboardRow> rows = new ArrayList<>();

                                for (int i = 1; i <= 5; i++) {
                                    InlineKeyboardRow row = new InlineKeyboardRow();
                                    InlineKeyboardButton button = new InlineKeyboardButton(getStars(i));
                                    button.setCallbackData("rating="+u.getDoctor_id()+"=" + i);
                                    row.add(button);
                                    rows.add(row);
                                }
                                String doctorName = "";
                                InlineKeyboardMarkup markup = new InlineKeyboardMarkup(rows);
                                for (Doctor d:doctors){
                                    if(d.getId()==u.getDoctor_id()){
                                        doctorName=d.getName();
                                        break;
                                    }
                                }
                                sendMessage(chat_id,"<b>⭐\uFE0F Оцените услугу:</b>\n\n" +
                                        "\uD83E\uDDD1\u200D⚕\uFE0F Ваш доктор: "+doctorName,markup);
                                break;
                            }
                        }
                    }
                    // Проверка выбора больницы
                    for (Hospital hospital:hospitals){
                        if(text.startsWith(hospital.getName())){
                            List<InlineKeyboardRow> rows = new ArrayList<>();
                            for (Specialization specialization:specializations){
                                InlineKeyboardRow row = new InlineKeyboardRow();
                                InlineKeyboardButton button = new InlineKeyboardButton(specialization.getName());
                                button.setCallbackData("specialization="+specialization.getId());
                                row.add(button);
                                rows.add(row);
                            }
                            InlineKeyboardMarkup markup = new InlineKeyboardMarkup(rows);
                            sendMessage(chat_id,"<b>\uD83C\uDFE5 Вы выбрали:</b>\n"+hospital.getName()+"\n\n" +
                                    "<blockquote>\uD83E\uDDD1\u200D⚕\uFE0F Пожалуйста, выберите специализацию:</blockquote>",markup);
                            return;
                        }
                    }
                }
            }
        }else if(update.hasCallbackQuery()){
            try {
                // Обработка запроса при нажатий на кнопки
                CallbackQuery callbackQuery = update.getCallbackQuery();
                String data = callbackQuery.getData();
                if(data.startsWith("specialization=")){
                    // Выбор специализаций
                    Specialization specialization = null;
                    for (Specialization s:specializations){
                        if(s.getId()==Long.parseLong(data.substring(data.indexOf("=")+1))){
                            specialization=s;
                        }
                    }
                    Message message = (Message) callbackQuery.getMessage();
                    String[] s = message.getText().split("\n");
                    Hospital hospital = null;
                    for (Hospital h:hospitals){
                        if(h.getName().equalsIgnoreCase(s[1])){
                            hospital=h;
                        }
                    }
                    List<InlineKeyboardRow> rows = new ArrayList<>();
                    for (Doctor doctor:doctors){
                        if(doctor.getHospital_id()==hospital.getId()&&doctor.getSpecialist_type()==specialization.getId()){
                            InlineKeyboardRow row = new InlineKeyboardRow();
                            InlineKeyboardButton button = new InlineKeyboardButton(doctor.getName()+" | "+doctor.getCost()+" ₸"+" | "+doctor.getRating()+" ⭐\uFE0F");
                            button.setCallbackData("doctor="+doctor.getId());
                            row.add(button);
                            rows.add(row);
                        }
                    }
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup(rows);
                    editMessage(String.valueOf(callbackQuery.getMessage().getChatId()),callbackQuery.getMessage().getMessageId(),"\uD83C\uDFE5 Больница:\n" +
                            ""+s[1]+"\n\n" +
                            "\uD83E\uDDD1\u200D⚕\uFE0FСпециализация:\n"+specialization.getName(),markup);
                }else if(data.startsWith("doctor=")){
                    // Выбор доктора
                    Doctor doctor = null;
                    for (Doctor d:doctors){
                        if(d.getId()==Long.parseLong(data.substring(data.indexOf("=")+1))){
                            doctor=d;
                            break;
                        }
                    }
                    Message message = (Message) callbackQuery.getMessage();
                    String[] s = message.getText().split("\n");
                    Hospital hospital = null;
                    for (Hospital h:hospitals){
                        if(h.getName().equalsIgnoreCase(s[1])){
                            hospital=h;
                        }
                    }
                    Specialization specialization = null;
                    for (Specialization sp:specializations){
                        if(sp.getName().equalsIgnoreCase(s[4])){
                            specialization=sp;
                        }
                    }

                    List<InlineKeyboardRow> rows = new ArrayList<>();
                    for (int i = 1; i <= 5; i++) {
                        LocalDate date = LocalDate.now().plusDays(i);
                        InlineKeyboardRow row = new InlineKeyboardRow();
                        InlineKeyboardButton button = new InlineKeyboardButton(date.toString());
                        if(i==1){
                            button.setText("Сегодня");
                        }else if(i==2){
                            button.setText("Завтра");
                        }
                        button.setCallbackData("date=" + date + "&doctor=" + doctor.getId());
                        row.add(button);
                        rows.add(row);
                    }
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup(rows);
                    editMessage(String.valueOf(callbackQuery.getMessage().getChatId()), callbackQuery.getMessage().getMessageId(),
                            "\uD83C\uDFE5 Больница:\n" + s[1] + "\n\n" +
                                    "\uD83E\uDDD1\u200D⚕\uFE0FСпециализация:\n" + specialization.getName() + "\n\n" +
                                    "\uD83D\uDC68\u200D⚕\uFE0FДоктор:\n" + doctor.getName() + "\n\n" +
                                    "📅 Пожалуйста, выберите дату:", markup);
                } else if(data.startsWith("date=")){
                    // Выбор даты
                    String[] params = data.split("&");
                    LocalDate date = LocalDate.parse(params[0].split("=")[1]);
                    long doctorId = Long.parseLong(params[1].split("=")[1]);

                    List<InlineKeyboardRow> rows = new ArrayList<>();
                    for (int i = 10; i <= 17; i++) {
                        InlineKeyboardRow row = new InlineKeyboardRow();
                        InlineKeyboardButton button = new InlineKeyboardButton(i + ":00");
                        button.setCallbackData("time=" + i + "&date=" + date + "&doctor=" + doctorId);
                        row.add(button);
                        rows.add(row);
                    }
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup(rows);
                    editMessage(String.valueOf(callbackQuery.getMessage().getChatId()), callbackQuery.getMessage().getMessageId(),
                            "📅 Дата: " + date + "\n\n" +
                                    "🕑 Пожалуйста, выберите время:", markup);
                } else if(data.startsWith("time=")){
                    // Выбор времени
                    String[] params = data.split("&");
                    int time = Integer.parseInt(params[0].split("=")[1]);
                    LocalDate date = LocalDate.parse(params[1].split("=")[1]);
                    long doctorId = Long.parseLong(params[2].split("=")[1]);

                    Doctor doctor = null;
                    for (Doctor d : doctors) {
                        if (d.getId() == doctorId) {
                            doctor = d;
                            break;
                        }
                    }

                    // Найти больницу и специализацию
                    Hospital hospital = null;
                    Specialization specialization = null;
                    for (Hospital h : hospitals) {
                        if (h.getId() == doctor.getHospital_id()) {
                            hospital = h;
                            break;
                        }
                    }
                    for (Specialization s : specializations) {
                        if (s.getId() == doctor.getSpecialist_type()) {
                            specialization = s;
                            break;
                        }
                    }
                    LocalDateTime dateTime = date.atTime(time, 0);
                    ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());
                    long unixTime = zonedDateTime.toEpochSecond();
                    // Сохранение записи
                    users.add(new User(UUID.randomUUID().toString(),callbackQuery.getFrom().getId(),"\uD83C\uDFE5 Больница:\n" + hospital.getName() + "\n" +
                            "\uD83E\uDDD1\u200D⚕\uFE0FСпециализация:\n" + specialization.getName() + "\n" +
                            "\uD83D\uDC68\u200D⚕\uFE0FДоктор:\n" + doctor.getName() + "\n" +
                            "📅 Дата: " + date + "\n" +
                            "🕑 Время: " + time + ":00",unixTime,doctorId));
                    List<KeyboardRow> rows = new ArrayList<>();
                    {
                        KeyboardRow row = new KeyboardRow();
                        row.add("\uD83C\uDD95 Сделать запись");
                        rows.add(row);
                    }
                    {
                        KeyboardRow row = new KeyboardRow();
                        row.add("\uD83D\uDCC5 Мои записи");
                        rows.add(row);
                    }
                    ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(rows);
                    markup.setResizeKeyboard(true);
                    markup.setOneTimeKeyboard(true);
                    editMessage(String.valueOf(callbackQuery.getMessage().getChatId()), callbackQuery.getMessage().getMessageId(),
                            "\uD83C\uDFE5 Больница:\n" + hospital.getName() + "\n\n" +
                                    "\uD83E\uDDD1\u200D⚕\uFE0FСпециализация:\n" + specialization.getName() + "\n\n" +
                                    "\uD83D\uDC68\u200D⚕\uFE0FДоктор:\n" + doctor.getName() + "\n\n" +
                                    "📅 Дата: " + date + "\n" +
                                    "🕑 Время: " + time + ":00",null);
                    sendMessage(String.valueOf(callbackQuery.getMessage().getChatId()),"✅ Ваша запись подтверждена!",markup);
                }else if(data.startsWith("rating=")){
                    String[] s = data.split("=");
                    int i = 0;
                    for (Doctor d:doctors){
                        if(d.getId()==Long.parseLong(s[1])){
                            ArrayList<Doctor.Rating> ratings = d.getRatings();
                            // Добавление оценки
                            ratings.add(new Doctor.Rating(Integer.parseInt(s[2]), callbackQuery.getFrom().getFirstName()));
                            d.setRatings(ratings);
                            doctors.set(i,d);

                            // Сохранение данных
                            DoctorRatingManager.saveRatings(doctors);
                            break;
                        }
                        i++;
                    }
                    editMessage(String.valueOf(callbackQuery.getMessage().getChatId()),callbackQuery.getMessage().getMessageId(),"✅ Спасибо за ваш отзыв",null);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // Метод для изменения сообщения
    private static Message editMessage(String chat_id,int msg_id, String text, InlineKeyboardMarkup markup){
        EditMessageText editMessageText = EditMessageText.builder()
                .chatId(chat_id)
                .messageId(msg_id)
                .text(text)
                .replyMarkup(markup)
                .build();
        try {
            return (Message) telegramClient.execute(editMessageText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод для отправки сообщения
    private static Message sendMessage(String chat_id, String text, InlineKeyboardMarkup markup){
        SendMessage sendMessage = SendMessage.builder()
                .parseMode("HTML")
                .text(text)
                .replyMarkup(markup)
                .chatId(chat_id)
                .build();
        try {
            return telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод для отправки сообщения
    private static Message sendMessage(String chat_id, String text, ReplyKeyboardMarkup markup){
        SendMessage sendMessage = SendMessage.builder()
                .parseMode("HTML")
                .text(text)
                .replyMarkup(markup)
                .chatId(chat_id)
                .build();
        try {
            return telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод для отправки сообщения
    private static Message sendMessage(String chat_id, String text){
        SendMessage sendMessage = SendMessage.builder()
                .parseMode("HTML")
                .text(text)
                .chatId(chat_id)
                .build();
        try {
            return telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    // Функция для получения строки с количеством звезд
    private String getStars(int count) {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < count; i++) {
            stars.append("★");
        }
        return stars.toString();
    }
}
