# EasyTexts
**EasyTexts** is a lightweight text utility plugin designed for PaperMC 1.21.11+ servers. It provides server admin with easy way of sending messages to players, titles/subtitles, broadcasting, intervaled broadcasts on the server without the bloat of other plugins.

---

## Key Features & Why You Should Download It

* **Easy Text Utilities:** Easily send messages to players, sebd titles/subtitles, broadcast to whoe server, or intervaled broadcasts on server on `config.yml` with MiniMessage support. Perfect for server owners who doesn't want to deal with clunky vanilla commands.
* **Minimal Overhead:** Built to be lightweight and efficient, ensuring minimal impact on your server's performance. Perfect for small to medium-sized survival, semi-vanilla, or even large public servers that just need text utilities!
* **Customize Plugin Messages:** Customize all EasyTexts messages completely on `messages.yml`! Uses MiniMessage so you can have gradients, hex, clickable text and more!
* **Full MiniMessage Support:** Supports MiniMessage everywhere so you can use hex, gradients clickable texts etc.! It works everywhere flawlessly no bloat. No more ugly § color codes! MiniMessage formatter: [https://webui.advntr.dev/](https://webui.advntr.dev/)
* **Modern Server Support:** Built specifically for the latest **PaperMC 1.21.11+** versions and its forks, guaranteeing up-to-date performance and stability.
* **Open Source:** Developed under the **GNU General Public License v3.0**, allowing for transparency and community contributions.

---

## Commands & Usage
**(<> is required, [] is optional):**<br>
*NOTE: It shows command usage if you typed the command without any arguments.*

| Command                                         | Description                                 | Permission                    |
|:------------------------------------------------|:--------------------------------------------|:------------------------------|
| `/easytexts [info/reload]`                      | EasyTexts admin command.                    | `easytexts.admin`             |
| `/broadcast <msg> [showauthor: true/false]`     | Broadcast a message in whole server.        | `easytexts.broadcast`         |
| `/sendmsg <player/me> <msg>`                    | Simply send a message to player or yourself.| `easytexts.sendmsg`           |
| `/sendactionbar <player/me> <time_secs> <msg>`  | Simply send an actionbar to player or yourself.| `easytexts.sendactionbar`  |
| `/sendtitle <player/me> <time_secs> <title> [subtitle]`| Simply send a title and subtitle to player or yourself. | `easytexts.sendtitle`   |

---

# Default Configs
Default configs used on the plugin.

<details>
  <summary>config.yml</summary>

  ```yaml
# EasyTexts
# Github: https://github.com/WatermanMC/EasyTexts
# Discord Support: https://discord.gg/Scgqfm5EU4

# Only supports MiniMessage here!
# MiniMessage previewer: https://webui.advntr.dev/
# Use \n too for newlines
random-broadcasts:
  enabled: false
  # in secs
  interval: 600
  texts:
    - "<green>Hello server owner!\n\n<red>You can change this in EasyText's config.yml :)"
    - "<green>You can add more messages too"
  ```
</details>

<details>
  <summary>messages.yml</summary>

  ```yaml
# Only supports MiniMessage here!
# MiniMessage previewer: https://webui.advntr.dev/

prefix: "<green><b>EasyTexts<reset><white><b>:<reset>"

nopermission: "<red>You don't have permission for that!"
reloaded: "<green>Plugin reloaded!"
broadcast: "<yellow><b>BROADCAST</b></yellow><gray>:</gray> %msg%"
broadcast_author: "<yellow><b>BROADCAST by %author%</b></yellow><gray>:</gray> %msg%"
broadcast_nomsg: "<red>Please specify a message to broadcast"
sendmsg:
  sent: "<gray>Message sent to %target%."
  target_offline: "<red>Player '%target%' is offline"

actionbar:
  sent: "<gray>Actionbar sent to %target%."
  target_offline: "<red>Player '%target%' is offline"
  invalid_duration: "<red>Invalid duration"

titles:
  sent: "<gray>Title sent to %target%."
  target_offline: "<red>Player '%target%' is offline"
  invalid_duration: "<red>Invalid duration"
  ```
</details>

---

## Links & Additional Information

**Author:** WatermanMC

* **GitHub Repository** [https://github.com/WatermanMC/EasyTexts](https://github.com/WatermanMC/EasyTexts)
* **Discord Support:** [https://discord.gg/Scgqfm5EU4](https://discord.gg/Scgqfm5EU4)
