package MephiPackage.logging;

public enum EventType {
    // Жизненный цикл приложения
    APP_START,          // запуск приложения
    APP_EXIT,           // выход из приложения

    // Работа с файлами и источниками данных
    FILE_SELECTED,      // выбран файл
    FORMAT_DETECTED,    // определён формат файла
    MISSION_LOADED,     // миссия успешно загружена

    // GUI события
    GUI_OPENED,         // открыто окно GUI

    // Ошибки
    ERROR_OCCURRED      // произошла ошибка
}