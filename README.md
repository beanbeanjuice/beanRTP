<p align="center">
  <img src="https://github.com/beanbeanjuice/beanRTP/blob/master/Images/Finished/beanRTP.png?raw=true" alt="beanRTP Logo"/>
</p>
<center>
  A simple plugin to allow random teleportation on your server. It always sends players to a safe location. It also caches safe locations, so there there is no lag when teleporting players!
</center>

---

<p align="center">
  <img src="https://github.com/beanbeanjuice/beanRTP/blob/master/Images/Finished/Installation.png?raw=true" alt="installation"/>
</p>

<center>
  Simply place the plugin in your plugins folder and restart your server! The constraints are based on your current world-border. If you change the size or location of your border, you must restart the server. If you're constantly getting kicked for flying, you must enable flight in your server.properties file.
</center>

---

<p align="center">
  <img src="https://github.com/beanbeanjuice/beanRTP/blob/master/Images/Finished/Features.png?raw=true" alt="features"/>
</p>

* **Instant Teleportation**
* **Safe Locations**
* **Customizable Messages**
* **Smart Cooldown System**

---

<p align="center">
  <img src="https://github.com/beanbeanjuice/beanRTP/blob/master/Images/Finished/Permissions.png?raw=true" alt="permissions"/>
</p>

* `beanRTP.use` - Use of the **/rtp** command.
* `beanRTP.others` - Use of the **/rtp (name)** command.
* `beanRTP.help` - Access to the **/rtp help** command.
* `beanRTP.reload` - Use of the **/rtp reload** command.
* `beanRTP.bypass.cooldown` - Bypasses the cooldown.
* `beanRTP.bypass.timer` - Bypasses the timer.

---

<p align="center">
  <img src="https://github.com/beanbeanjuice/beanRTP/blob/master/Images/Finished/Configuration.png?raw=true" alt="configuration"/>
</p>

### config.yml
```YAML
# =================================
#             beanRTP
# =================================
prefix: '&8[&bbeanRTP&8] '

# Minimum distance to teleport from the player's current location.
minimum-distance-from-border-center: 1000

# Allowed Worlds. This is case-sensitive.
allowed-worlds:
  - world
  - world_nether
  - world_the_end

# Cooldown in seconds.
cooldown-time: 300

# Countdown time in seconds.
countdown-time: 10

# Permissions:
# beanRTP.use - Use of the /rtp command.
# beanRTP.bypass.cooldown - Bypasses the cooldown.
# beanRTP.reload - Use of the /rtpreload command.
# beanRTP.others - Use of the /rtp (name) command.
# beanRTP.bypass.timer - Bypasses the timer.
# beanRTP.help - Access to the /rtp help command.

# DO NOT TOUCH THIS
file-version: 1
```

### messages.yml
```YAML
# Messages
no-permission: '&cYou do not have permission to use this command.'
starting-teleportation: '&3Teleportation is starting in &a{seconds} &3seconds...'
successful-teleportation: '&aYou have been successfully teleported!'
unsuccessful-teleportation: '&cTeleportation was unsuccessful. Please try again.'
other-successful-teleportation: '&a{player} has been successfully teleported.'
other-unsuccessful-teleportation: '&cThere was an error teleporting {player}. Please try again.'
moved-during-teleport: '&cYou moved! Cancelling teleportation.'
cooldown: '&cSorry, you are on a cooldown for &b{seconds} &cmore seconds.'
successful-reload: '&aThe plugin has been successfully reloaded.'
unknown-command: '&cThis is an unknown command. Please type &a/rtp help &cfor more information.'
not-allowed-world: '&cSorry, you cannot do that in this world.'
not-allowed-world-others: '&cSorry, {player} is in a disabled world.'
already-teleporting: '&cYou cannot execute this command. You are already teleporting.'

# Help commands
help:
  - '&aDisplaying help commands...'
  - '&6/rtp&a: Teleports you to a random location.'
  - '&6/rtp (player)&a: Teleports the selected player to a random location.'
  - '&6/rtp reload&a: Reloads the config file.'
  - '&6/rtp setspawn&a: Sets the spawn location in config. You need this in order for the plugin to work properly.'
  - '&6/rtp help&a: Displays these messages.'
  - '&c&oNote&r&c: If you do not do /rtp setspawn in each world, it will use the default coordinates of X = 0 and Z = 0.'

# DO NOT TOUCH THIS
file-version: 1
```

---

## Statistics
![statistics](https://bstats.org/signatures/bukkit/beanRTP.svg)
