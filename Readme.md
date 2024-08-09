# Дипломная работа профессии Инженер по тестированию: с нуля до middle

## Тестирование мобильного приложение "Хоспис"

[Ссылка на задание](https://github.com/netology-code/qamid-diplom)

### Для работы с проектом необходимо: 
- Android Studio
- Allure



### Для запуска тестов:

- Склонировать репозиторий с при помощи команды git clone https://github.com/sharutyunyanqa/qamid-Diplom
- Открыть Android studio
- Открыть склонированный проект
- Запустить приложение на эмуляторе Pixel 4 API 29
- Запустить тесты из консоли с помощью команды ./gradlew connectedAndroidTest
- Дождаться завершения выполнения тестов и посмотреть результаты

### Для формирования Allure отчетов

- Во вкладке Project перейти в папку с тестами fmh-android\app\src\androidTest\java\ru\iteco\fmhandroid\ui\tests
- Щелкнуть правой кнопкой мыши, выбрать "Run 'Tests in 'ru.iteco.fmhandroid.ui.test'" или использовать комбинацию клавиш Ctrl+Shft+F10
- Выгрузить каталог /data/data/ru.iteco.fmhandroid/files/allure-results с эмулятора (при помощи Device Manager). Лучше выгрузить в корень директории проекта.
- Перейти в консоли на уровень выше каталога allure-results(если выгрузили каталог в корень проекта, то это и есть нужный уровень)
- Выполнить команду allure serve
- Дождаться формирования отчета
- Открыть их в браузере
