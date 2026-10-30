# Tamed Phantoms Colors — инструкции для AI-агентов

Аддон Minecraft 1.21.1 для NeoForge и Fabric к `tamedphantoms-mod`: краситель, светящиеся чернила и кисть по прирученному фантому. Общие правила — `../AGENTS.md`. Раскладка как у родителя: общий код в `src/main`, NeoForge в `src/neoforge`, Fabric в проекте `fabric/`.

## Правила

- Код — Kotlin. Новый Java-файл не добавляй.
- Облик фантома (`bodyDye`, `saddleDye`, `luminous`, отряхивание) хранит, синхронизирует и рисует родитель. Аддон только решает, что делает клик, через `TamedPhantomsApi`. Новое поле облика добавляй в родителе, а не здесь.
- Логика без Minecraft живёт в `paint/PaintRules.kt` и покрыта тестом. Новое правило клика сначала туда.
- Tamed Phantoms берётся со страниц его GitHub Release через ivy-репозиторий в Gradle: группа — тег страницы, модуль — имя jar без версии. Пока нужной версии родителя там нет, собирай с `-Ptamedphantoms_local=<каталог tamedphantoms-mod>` после его `gradle build`.
- LambDynamicLights — только `compileOnly`. Точка входа — `compat/ColorsDynamicLights`: в `fabric.mod.json` ключ `lambdynlights:initializer`, в `neoforge.mods.toml` таблица `[modproperties."${mod_id}"."yumi:entrypoints"]`. Для NeoForge нужен классификатор `mojmap`. Свет движка одноцветный: цвет краски на блоки не ложится.
- Страница книги лежит в пространстве имён родителя: `assets/tamedphantoms/patchouli_books/phantom_guide/en_us/entries/`. Ключ `extend` Patchouli убрал в 1.20, его не используй.
- McnCDN у аддона нет: новый пакет на CDN не заводи без прямой просьбы владельца.
- Коммит и пуш — только когда владелец прямо просит. Теги `minecraft-*` двигает workflow релиза.

## Готово

Из каталога аддона: `gradle build --stacktrace` (Gradle 8.11, JDK 21), затем `runClient` и `:fabric:runClient` до главного меню, `runServer` и `:fabric:runServer` до `Done`. Логику кликов проверяет `runGameTestServer` (NeoForge): тесты в `src/gametest`, в jar они не попадают. Для проверки света положи jar LambDynamicLights в `run/mods` и `fabric/run/mods`. Его рантайм 4.8.11 требует Fabric Loader 0.18.4 или новее, поэтому Fabric-клиент с ним запускай с `-Pfabric_loader_version=0.19.5`, как в сборке игроков. В `gradle.properties` версию загрузчика ради этого не поднимай.
