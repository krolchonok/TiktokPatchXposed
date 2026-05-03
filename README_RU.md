# TiktokPatchXposed

<div align="center">

[![Последняя версия](https://img.shields.io/github/v/release/krolchonok/TiktokPatchXposed?label=скачать&style=for-the-badge)](https://github.com/krolchonok/TiktokPatchXposed/releases/latest)
[![Лицензия](https://img.shields.io/github/license/krolchonok/TiktokPatchXposed?style=for-the-badge)](LICENSE)
[![Звезды](https://img.shields.io/github/stars/krolchonok/TiktokPatchXposed?style=for-the-badge)](https://github.com/krolchonok/TiktokPatchXposed/stargazers)
[![Форки](https://img.shields.io/github/forks/krolchonok/TiktokPatchXposed?style=for-the-badge)](https://github.com/krolchonok/TiktokPatchXposed/network/members)

<br/>

[🇬🇧 English](README.md)

</div>

TiktokPatchXposed — модуль Xposed для TikTok, обходящий региональные ограничения и снимающий ограничения на скачивание.

## Возможности

- 🌍 **Смена региона** — подмена данных SIM-карты (страна, оператор) для доступа к видео, недоступным в вашем регионе
- 💾 **Скачивание без водяного знака** — снятие ограничений ACL на скачивание видео
- 🔑 **Исправление входа через Google** — исправление Google Auth/OneTap на устройствах, где вход не работает (по мотивам [ReVanced](https://github.com/ReVanced/revanced-patches/tree/main/patches/src/main/kotlin/app/revanced/patches/tiktok/misc/login/fixgoogle))

## Требования

- Рут-устройство с установленным фреймворком LSPosed / EdXposed
- TikTok (`com.zhiliaoapp.musically`) или TikTok Lite (`com.ss.android.ugc.trill`)
- Протестировано на `com.zhiliaoapp.musically` версии **41.4.3**

## Установка

1. Скачайте APK из раздела [Releases](https://github.com/krolchonok/TiktokPatchXposed/releases/latest)
2. Установите APK на устройство
3. Откройте LSPosed и активируйте модуль для пакетов TikTok
4. Перезапустите TikTok

## Важно

> [!WARNING]
> Использование модулей Xposed может привести к блокировке аккаунта или нестабильной работе приложения. Всё, что вы делаете, — на ваш страх и риск.

## История звезд

[![Star History Chart](https://api.star-history.com/svg?repos=krolchonok/TiktokPatchXposed&type=Date)](https://star-history.com/#krolchonok/TiktokPatchXposed&Date)

## Обратная связь

- Автор: [@krolchonok](https://github.com/krolchonok)
- Вопросы и предложения — через [Issues](https://github.com/krolchonok/TiktokPatchXposed/issues) на GitHub
