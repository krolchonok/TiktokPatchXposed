# TiktokPatchXposed

<div align="center">

[![Latest Release](https://img.shields.io/github/v/release/krolchonok/TiktokPatchXposed?label=download&style=for-the-badge)](https://github.com/krolchonok/TiktokPatchXposed/releases/latest)
[![License](https://img.shields.io/github/license/krolchonok/TiktokPatchXposed?style=for-the-badge)](LICENSE)
[![Stars](https://img.shields.io/github/stars/krolchonok/TiktokPatchXposed?style=for-the-badge)](https://github.com/krolchonok/TiktokPatchXposed/stargazers)
[![Forks](https://img.shields.io/github/forks/krolchonok/TiktokPatchXposed?style=for-the-badge)](https://github.com/krolchonok/TiktokPatchXposed/network/members)

<br/>

[🇷🇺 Читать на русском](README_RU.md)

</div>

An Xposed module for TikTok that bypasses regional restrictions and removes download limitations.

## Features

- 🌍 **Region spoofing** — replaces SIM card data (country, operator) to unlock videos unavailable in your region
- 💾 **Watermark-free downloads** — removes ACL download restrictions on videos
- 🔑 **Google login fix** — fixes Google Auth / OneTap sign-in on devices where it is broken (inspired by [ReVanced](https://github.com/ReVanced/revanced-patches/tree/main/patches/src/main/kotlin/app/revanced/patches/tiktok/misc/login/fixgoogle))

## Requirements

- Rooted device with LSPosed / EdXposed framework installed
- TikTok (`com.zhiliaoapp.musically`) or TikTok Lite (`com.ss.android.ugc.trill`)
- Tested on `com.zhiliaoapp.musically` version **41.4.3**

## Installation

1. Download the APK from the [Releases](https://github.com/krolchonok/TiktokPatchXposed/releases/latest) page
2. Install the APK on your device
3. Open LSPosed and enable the module for the TikTok packages
4. Force-stop and restart TikTok

## Warning

> [!WARNING]
> Using Xposed modules may result in account bans or unstable app behaviour. All modifications are made at your own risk.

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=krolchonok/TiktokPatchXposed&type=Date)](https://star-history.com/#krolchonok/TiktokPatchXposed&Date)

## Feedback

- Author: [@krolchonok](https://github.com/krolchonok)
- Questions and suggestions — open an [Issue](https://github.com/krolchonok/TiktokPatchXposed/issues) on GitHub
