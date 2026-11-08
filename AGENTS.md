# Tamed Phantoms Colors — инструкции для AI-агентов

Аддон Minecraft 1.21.1 и 26.3 для NeoForge и Fabric к `tamedphantoms-mod`: краситель, светящиеся чернила и кисть по прирученному фантому. Общие правила — `../../AGENTS.md`. Раскладка как у родителя: общий код в `src/main`, NeoForge в `src/neoforge`, Fabric в проекте `fabric/`. Сборка 26.3 — отдельный проект `versions/26.3`.

## Полная актуализация

Аддон выпускается для 1.21.1 и 26.3, Fabric и NeoForge. Игровая правка аддона входит во все эти сборки. На каждой странице его релиза лежит jar родителя `tamedphantoms-mod` той же версии Minecraft, того же загрузчика и текущего `mod_version` родителя. После релиза родителя подними `tamedphantoms_version` и `tamedphantoms_version_range` и запушь аддон, только когда этот jar родителя уже скачивается. Пока на какой-то странице родитель старше, релиз аддона не актуален.

## Правила

- Прежде чем писать своё, портировать или декомпилировать, найди готовый мод, порт или форк. Веб-поиск (Google, Perplexity) малоизвестные репозитории GitHub не видит: репозиторий без звёзд и без ссылки с уже проиндексированной страницы в их индекс не попадает. Ищи каталог GitHub. Перед поиском прочитай https://github.com/aaddrick/gh-cli-search/blob/main/skills/gh-search-repos/SKILL.md и для исходников https://github.com/aaddrick/gh-cli-search/blob/main/skills/gh-search-code/SKILL.md. Нижний порог звёзд не ставь: примеры `stars:>N` в скилле применяй только если владелец просил известные проекты. Скилл https://github.com/py-sit/github-search-skill отбрасывает репозитории без лицензии, без свежих коммитов и учебные, поэтому для порта и малоизвестного мода его не открывай. Сделай несколько запросов: `gh search repos` по имени, `--match readme` и `gh search code` по mod id; для порта добавь `--include-forks true` и список форков исходного репозитория. Дефис в имени совпадает со словами запроса. Минус в начале квалификатора — исключение: такой запрос отделяй `--`, в PowerShell ещё `gh --%`. Пустое описание, ноль звёзд и пустые topics результат не отменяют. Опубликованные моды дополнительно смотри в поиске Modrinth: исходник порта часто есть только на GitHub. Свою реализацию не начинай, пока этот поиск не сделан.
- Код — Kotlin. Новый Java-файл не добавляй.
- Облик фантома (`bodyDye`, `saddleDye`, `luminous`, отряхивание) хранит, синхронизирует и рисует родитель. Аддон только решает, что делает клик, через `TamedPhantomsApi`. Новое поле облика добавляй в родителе, а не здесь.
- Логика без Minecraft живёт в `paint/PaintRules.kt` и покрыта тестом. Новое правило клика сначала туда.
- Достижения — JSON с критерием `minecraft:impossible` в `data/tamedphantomscolors/advancement`, выдаёт их `paint/ColorAdvancements.kt`. Родители в дереве — достижения Tamed Phantoms.
- `tamedphantoms_version` в этом аддоне равен текущему `mod_version` родителя `tamedphantoms-mod`, а `tamedphantoms_version_range` начинается с него: `[x.y.z,)`. То же число стоит в README, `modrinth.md` и в зависимости `tamedphantoms` обоих `fabric.mod.json`. Страницы GitHub Release аддона несут jar родителя именно этой версии. После релиза родителя сразу подними зависимость и запушь аддон, иначе его релиз остаётся со старым фантомом. Пока нужного jar родителя на странице релиза нет, собирай с `-Ptamedphantoms_local=<каталог tamedphantoms-mod>` после его сборки.
- LambDynamicLights — только `compileOnly`. Точка входа — `compat/ColorsDynamicLights`: в `fabric.mod.json` ключ `lambdynlights:initializer`, в `neoforge.mods.toml` таблица `[modproperties."${mod_id}"."yumi:entrypoints"]`. На 1.21.1 у NeoForge и Fabric классификатор `mojmap`: API собран Loom 1.16, и Loom 1.9.2 его не переназначает. Свет движка одноцветный: цвет краски на блоки не ложится.
- Страница книги лежит в пространстве имён родителя: `assets/tamedphantoms/patchouli_books/phantom_guide/en_us/entries/`. Ключ `extend` Patchouli убрал в 1.20, его не используй. На 26.3 книга родителя — Modonomicon: запись аддона лежит в `versions/26.3/src/main/resources/data/tamedphantoms/modonomicon/books/phantom_guide/entries/`, тексты с разметкой Patchouli идут туда через ключи `tamedphantomscolors.guide.md.*` в markdown. Правишь текст книги — правь и их.
- Предметы, которые работают с седла, аддон называет родителю через `registerRiderTool` на обеих сторонах.
- Страницы релиза несут всё, что нужно и родителю: на Fabric ещё Mod Menu и Placeholder API, на 26.3 — Modonomicon. Версии те же, что в `gradle.properties` родителя.
- McnCDN у аддона нет: новый пакет на CDN не заводи без прямой просьбы владельца. Скилл CDN, если запись всё же понадобится: https://cdn.mikchan.net/ai/SKILL.md. Перед записью снова вызови `GET https://cdn.mikchan.net/version`. Токен не печатать.
- Коммит и пуш — только когда владелец прямо просит. Теги `minecraft-*` двигает workflow релиза.

## Сборка 26.3

`versions/26.3` копирует общий Kotlin задачей `sharedKotlin` и переписывает строки функцией `rewriteFor26`. После смены правил подними `rewriteStamp` в обоих проектах. Отличия 26.3, которые закрыты правилами: `ResourceLocation` — `Identifier`, цвет красителя — компонент `DataComponents.DYE` (у `DyeItem` нет `getDyeColor`), `hurtAndBreak(1, player, hand)`, `DustParticleOptions(rgb, scale)`, у `DynamicLightsInitializer` нет старого метода с `ItemLightSourceManager`. LambDynamicLights 4.13.0+26.3 — один jar без классификатора, событие из `yumi-commons-event` 2.0.0. Иконка в `neoforge.mods.toml` 26.3 — `iconFile`: `logoFile` там устарел. Игровые тесты есть только на 1.21.1.

## Готово

Из каталога аддона: `gradle build --stacktrace` (Gradle 8.11, JDK 21) и `gradle runGameTestServer`, затем `runClient` и `:fabric:runClient` до главного меню, `runServer` и `:fabric:runServer` до `Done`. То же в `versions/26.3` на Gradle 9.7.0 и JDK 25, сервер слушает `25567`. Логику кликов и выдачу достижений проверяет `runGameTestServer` (NeoForge 1.21.1): тесты в `src/gametest`, в jar они не попадают. Для проверки света положи jar LambDynamicLights в `run/mods` и `fabric/run/mods`. Рантайм 4.8.11 на 1.21.1 требует Fabric Loader 0.18.4 или новее, поэтому Fabric 1.21.1 с ним запускай с `-Pfabric_loader_version=0.19.5`, как в сборке игроков. В `gradle.properties` версию загрузчика ради этого не поднимай.

## Modrinth

На Modrinth у каждой пары версии Minecraft и загрузчика одна версия: самая новая. Релиз снимает прежние версии этого слота и ставит на их место текущий jar.
