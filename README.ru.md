# Tamed Phantoms Colors

**[English](README.md)** · **[Русский](README.ru.md)**

Аддон к [Tamed Phantoms](https://github.com/Nergan/tamedphantoms-mod) для **Minecraft 1.21.1** на NeoForge и Fabric. Хозяин может покрасить прирученного фантома и его седло, заставить его светиться и отмыть обратно. Ставится один загрузчик, не оба сразу. Сборка NeoForge идёт через [Kotlin for Forge](https://modrinth.com/mod/kotlin-for-forge), сборка Fabric — через [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin).

## Возможности

- **Краситель.** Хозяин кликает правой кнопкой мыши любым красителем по прирученному фантому. Тело принимает цвет, глаза остаются своими, а след от крыльев становится того же цвета. Краситель по седлу красит только седло, так что тело и седло красятся отдельно. Тот же цвет второй раз краситель не тратит.
- **Свечение.** Мешок светящихся чернил превращает фантома в источник света уровня 15, как лампа. Фантом рисуется в полную яркость, поэтому окрашенный светится своим цветом. Свет на блоках вокруг даёт [LambDynamicLights](https://modrinth.com/mod/lambdynamiclights) на клиенте. Свет в Minecraft бесцветный, поэтому на блоках он остаётся белым.
- **Кисть.** Кисть смывает краску и свечение с фантома и его седла. Фантом сухо отряхивается, без брызг, а кисть теряет одну единицу прочности.
- **Освобождённые фантомы.** Покрасить можно только прирученного. Краситель или светящиеся чернила по освобождённому фантому злят его: сердитые тучки, рык, и какое-то время он нападает на этого игрока, как при самозащите.
- **Освобождение.** Когда хозяин отпускает окрашенного фантома, тот стряхивает краску брызгами её цвета и возвращается к своему цвету. Седло тоже теряет краску. Седло, снятое ножницами, теряет краску так же.
- **Книга.** С [Patchouli](https://modrinth.com/mod/patchouli) в книге Tamed Phantoms появляется страница «Краски» в разделе о приручении.

## Загрузки

Готовые jar лежат в [GitHub Releases](https://github.com/Nergan/tamedphantoms-colors-mod/releases/latest). Пуш в `main` обновляет страницу релиза для каждого загрузчика. На странице есть `all-jars.zip`. Скачайте один набор и положите эти файлы в папку `mods`. Файл `*-sources.jar` в `mods` класть не нужно.

### NeoForge

| Файл | Обязателен | Что это |
| --- | --- | --- |
| `tamedphantomscolors-neoforge-1.21.1-1.0.0.jar` | Да | этот аддон, NeoForge 1.21.1 |
| `tamedphantoms-neoforge-1.21.1-1.0.5.jar` | Да | [Tamed Phantoms](https://github.com/Nergan/tamedphantoms-mod) 1.0.5 или новее |
| `kotlinforforge-5.8.0-all.jar` | Да | [Kotlin for Forge](https://modrinth.com/mod/kotlin-for-forge) |
| `Patchouli-1.21.1-93-NEOFORGE.jar` | Нет | [Patchouli](https://modrinth.com/mod/patchouli), только для книги |

### Fabric

| Файл | Обязателен | Что это |
| --- | --- | --- |
| `tamedphantomscolors-fabric-1.21.1-1.0.0.jar` | Да | этот аддон, Fabric 1.21.1 |
| `tamedphantoms-fabric-1.21.1-1.0.5.jar` | Да | [Tamed Phantoms](https://github.com/Nergan/tamedphantoms-mod) 1.0.5 или новее |
| `fabric-api-0.116.17+1.21.1.jar` | Да | [Fabric API](https://modrinth.com/mod/fabric-api) |
| `fabric-language-kotlin-1.13.2+kotlin.2.1.20.jar` | Да | [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin) |
| `cloth-config-15.0.140-fabric.jar` | Да | [Cloth Config](https://modrinth.com/mod/cloth-config), его требует Tamed Phantoms |
| `Patchouli-1.21.1-93-FABRIC.jar` | Нет | [Patchouli](https://modrinth.com/mod/patchouli), только для книги |

LambDynamicLights в релиз не входит: для настоящего света поставьте его с [Modrinth](https://modrinth.com/mod/lambdynamiclights) (4.8.11 или новее для 1.21.1).

## Требования

| Компонент | Версия |
| --- | --- |
| Minecraft | 1.21.1 |
| Tamed Phantoms | 1.0.5 или новее, того же загрузчика |
| NeoForge | 21.1.209 или новее |
| Kotlin for Forge | 5.8.0, сборка **NeoForge** |
| Fabric Loader | 0.16.10 или новее |
| Fabric API | 0.116.17+1.21.1 |
| Fabric Language Kotlin | 1.13.2+kotlin.2.1.20 |
| Java | 21 |
| LambDynamicLights | необязательно, только клиент, 4.8.11+1.21.1 |
| Patchouli | необязательно, 1.21.1-93 того же загрузчика |

## Установка

1. Поставьте Tamed Phantoms 1.0.5 или новее со всеми его зависимостями.
2. Положите в `mods` jar аддона для того же загрузчика.
3. По желанию добавьте LambDynamicLights на каждый клиент для настоящего света и Patchouli для страницы в книге.

Аддон нужен и на сервере, и на клиентах.

## Настройки

Настроек у аддона нет.

## Лицензия

Код — [MPL-2.0](LICENSE). Облик фантома (краску тела и седла, свечение) хранит и рисует Tamed Phantoms; аддон только решает, что делает клик.
