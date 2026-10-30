# Tamed Phantoms Colors

**[English](README.md)** · **[Русский](README.ru.md)**

Аддон к [Tamed Phantoms](https://github.com/Nergan/tamedphantoms-mod) для **Minecraft 1.21.1 и 26.3** на NeoForge и Fabric. Хозяин может покрасить прирученного фантома и его седло, заставить его светиться и отмыть обратно. Ставится один загрузчик, не оба сразу. Сборки NeoForge идут через [Kotlin for Forge](https://modrinth.com/mod/kotlin-for-forge), сборки Fabric — через [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin).

## Возможности

- **Краситель.** Хозяин кликает правой кнопкой мыши любым красителем по прирученному фантому. У каждого цвета свой облик: шкура переливается от тёмного к светлому, брюхо и кости светлеют в тон того же цвета, кромки берут акцент, а тёмные краски рассыпают по крыльям звёзды. Глаза остаются своими, а след от крыльев становится того же цвета. Краситель по седлу красит только седло: кожа берёт цвет, а строчка светлеет. Тело и седло красятся отдельно. Тот же цвет второй раз краситель не тратит.
- **С седла.** Всё это можно делать и сидя верхом на своём фантоме: глядя на крыло или на седло под собой, используйте краситель, светящиеся чернила или кисть.
- **Свечение.** Мешок светящихся чернил превращает фантома в источник света уровня 15, как лампа. Фантом рисуется в полную яркость, поэтому окрашенный светится своим цветом. Свет на блоках вокруг даёт [LambDynamicLights](https://modrinth.com/mod/lambdynamiclights) на клиенте. Свет в Minecraft бесцветный, поэтому на блоках он остаётся белым.
- **Кисть.** Кисть смывает краску и свечение с фантома и его седла. Фантом сухо отряхивается, без брызг, а кисть теряет одну единицу прочности.
- **Только хозяин.** Красить, зажигать и отмывать прирученного фантома и его седло может только хозяин. Краситель, чернила и кисть другого игрока ничего не делают и не тратятся.
- **Освобождённые фантомы.** Покрасить можно только прирученного. Краситель или светящиеся чернила по освобождённому фантому злят его: сердитые тучки, рык, и какое-то время он нападает на этого игрока, как при самозащите.
- **Освобождение.** Когда хозяин отпускает окрашенного фантома, тот стряхивает краску брызгами её цвета и возвращается к своему цвету. Седло тоже теряет краску. Седло, снятое ножницами, теряет краску так же.
- **Достижения.** «Теперь мы отличаемся» за первую покраску, «Свет во тьме» за светящиеся чернила, «Что естественно, то правильно» за кисть, «Свободен от ярлыков», когда освобождённый фантом стряхивает краску, и «Я это не надену!» за попытку покрасить освобождённого фантома. Они стоят рядом с достижениями Tamed Phantoms.
- **Книга.** В книге Tamed Phantoms появляется запись «Краски» в разделе о приручении: с [Patchouli](https://modrinth.com/mod/patchouli) на 1.21.1 и с [Modonomicon](https://modrinth.com/mod/modonomicon) на 26.3, где сборки Patchouli нет.

## Загрузки

Готовые jar лежат в [GitHub Releases](https://github.com/Nergan/tamedphantoms-colors-mod/releases/latest). Пуш в `main` обновляет страницу релиза для каждой пары версии Minecraft и загрузчика. На каждой странице есть всё нужное и для аддона, и для самого Tamed Phantoms, а `all-jars.zip` собирает все jar страницы. Скачайте один набор и положите эти файлы в папку `mods`. Файл `*-sources.jar` в `mods` класть не нужно.

### Minecraft 1.21.1, NeoForge

| Файл | Обязателен | Что это |
| --- | --- | --- |
| `tamedphantomscolors-neoforge-1.21.1-1.2.1.jar` | Да | этот аддон |
| `tamedphantoms-neoforge-1.21.1-1.0.11.jar` | Да | [Tamed Phantoms](https://github.com/Nergan/tamedphantoms-mod) 1.0.11 или новее |
| `kotlinforforge-5.8.0-all.jar` | Да | [Kotlin for Forge](https://modrinth.com/mod/kotlin-for-forge) |
| `Patchouli-1.21.1-93-NEOFORGE.jar` | Нет | [Patchouli](https://modrinth.com/mod/patchouli), только для книги |

### Minecraft 1.21.1, Fabric

| Файл | Обязателен | Что это |
| --- | --- | --- |
| `tamedphantomscolors-fabric-1.21.1-1.2.1.jar` | Да | этот аддон |
| `tamedphantoms-fabric-1.21.1-1.0.11.jar` | Да | [Tamed Phantoms](https://github.com/Nergan/tamedphantoms-mod) 1.0.11 или новее |
| `fabric-api-0.116.17+1.21.1.jar` | Да | [Fabric API](https://modrinth.com/mod/fabric-api) |
| `fabric-language-kotlin-1.13.2+kotlin.2.1.20.jar` | Да | [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin) |
| `cloth-config-15.0.140-fabric.jar` | Да | [Cloth Config](https://modrinth.com/mod/cloth-config), его требует Tamed Phantoms |
| `modmenu-11.0.4.jar` | Нет | [Mod Menu](https://modrinth.com/mod/modmenu), открывает экран настроек Tamed Phantoms |
| `placeholder-api-2.4.2+1.21.jar` | Нет | [Text Placeholder API](https://modrinth.com/mod/placeholder-api), его требует Mod Menu |
| `Patchouli-1.21.1-93-FABRIC.jar` | Нет | [Patchouli](https://modrinth.com/mod/patchouli), только для книги |

### Minecraft 26.3, NeoForge

| Файл | Обязателен | Что это |
| --- | --- | --- |
| `tamedphantomscolors-neoforge-26.3-1.2.1.jar` | Да | этот аддон |
| `tamedphantoms-neoforge-26.3-1.0.11.jar` | Да | [Tamed Phantoms](https://github.com/Nergan/tamedphantoms-mod) 1.0.11 или новее |
| `kotlinforforge-6.4.0-all.jar` | Да | [Kotlin for Forge](https://modrinth.com/mod/kotlin-for-forge) |
| `modonomicon-26.3-neoforge-2.20.0.jar` | Нет | [Modonomicon](https://modrinth.com/mod/modonomicon), только для книги |

### Minecraft 26.3, Fabric

| Файл | Обязателен | Что это |
| --- | --- | --- |
| `tamedphantomscolors-fabric-26.3-1.2.1.jar` | Да | этот аддон |
| `tamedphantoms-fabric-26.3-1.0.11.jar` | Да | [Tamed Phantoms](https://github.com/Nergan/tamedphantoms-mod) 1.0.11 или новее |
| `fabric-api-0.162.0+26.3.jar` | Да | [Fabric API](https://modrinth.com/mod/fabric-api) |
| `fabric-language-kotlin-1.14.2+kotlin.2.4.21.jar` | Да | [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin) |
| `cloth-config-fabric-26.3.159.jar` | Да | [Cloth Config](https://modrinth.com/mod/cloth-config), его требует Tamed Phantoms |
| `modmenu-21.0.0.jar` | Нет | [Mod Menu](https://modrinth.com/mod/modmenu), открывает экран настроек Tamed Phantoms |
| `placeholder-api-3.2.0+26.3.jar` | Нет | [Text Placeholder API](https://modrinth.com/mod/placeholder-api), его требует Mod Menu |
| `modonomicon-26.3-fabric-2.20.0.jar` | Нет | [Modonomicon](https://modrinth.com/mod/modonomicon), только для книги |

LambDynamicLights в релиз не входит: для настоящего света поставьте его с [Modrinth](https://modrinth.com/mod/lambdynamiclights) (на 1.21.1 — 4.8.11+1.21.1 или новее, на 26.3 — 4.13.0+26.3 или новее).

## Требования

| Компонент | 1.21.1 | 26.3 |
| --- | --- | --- |
| Tamed Phantoms | 1.0.11 или новее, того же загрузчика | 1.0.11 или новее, того же загрузчика |
| NeoForge | 21.1.209 или новее | 26.3.0.76 или новее |
| Kotlin for Forge | 5.8.0, сборка **NeoForge** | 6.4.0 |
| Fabric Loader | 0.16.10 или новее | 0.19.5 или новее |
| Fabric API | 0.116.17+1.21.1 | 0.162.0+26.3 |
| Fabric Language Kotlin | 1.13.2+kotlin.2.1.20 | 1.14.2+kotlin.2.4.21 |
| Java | 21 | 25 |
| LambDynamicLights | необязательно, только клиент, 4.8.11+1.21.1 | необязательно, только клиент, 4.13.0+26.3 |
| Книга | необязательно, Patchouli 1.21.1-93 того же загрузчика | необязательно, Modonomicon 26.3-2.20.0 того же загрузчика |

## Установка

1. Поставьте Tamed Phantoms 1.0.11 или новее со всеми его зависимостями для своей версии Minecraft и загрузчика. Они все есть на страницах релиза этого аддона.
2. Положите в `mods` jar аддона для той же версии и загрузчика.
3. По желанию добавьте LambDynamicLights на каждый клиент для настоящего света и мод книг для записи в книге.

Аддон нужен и на сервере, и на клиентах.

## Настройки

Настроек у аддона нет.

## Лицензия

Код — [MPL-2.0](LICENSE). Облик фантома (краску тела и седла, свечение) хранит и рисует Tamed Phantoms; аддон только решает, что делает клик.
