# Fabric Death Messages

### Buy me covfefe ❤️
```
BTC     bc1qjqzkrfupcrgtzpeu0pmut24vq8tfzs9rqe6458
ETH     0x799b3b5520525CDd95d1D5C7Ba1a2Ee6037B1bFE
ADA     addr1q8mz3z7cw4jz9dacvpz6dpw2c6xke6nv8vk6rfnt7mkaat8vgnu796g5vrarn4pjgpdqkare9zryx645e25wcae8636q97typg
XRP     r3Bpcyp8zVNkaDzpppdRTuSXSvxAUJXAVj
LTC     ltc1qpja2nr6x9nz3q3ya3ec6ec5hxvm8dz52urn39z
BCH     1NAteBJF7wKw8BdzLJ6YE65ja1ZAHf68jf
DOGE    DL4VNBx6EGuPcgnJrfgxok9tTvcbVGKx3R
XMR     89S6qYdMJyZZ8ddKtFqTzGhuDZxJkNcmL9L6KzTSw7AeQos1uku2GBhBaHLUYtgv4TQRRQuNF4FixAu6geKC2r25NyWZj2Q
DASH    XtffD9gZFDKWWpabMyAi8sF9EeDREH5dUy
DCR     DsSAqeDekTCvbd84GkAofHyutrFrFDX1EnD
ZEC     t1P336iRRMM6Yu2wTzXJmjm6p4RgNAQkgsM
STRAX   SVfFcCZtQ8yMSMxc2K8xzFr4psHpGpnKNT 
```

<a href='https://www.curseforge.com/minecraft/mc-mods/fabric-api'><img src='https://i.imgur.com/Ol1Tcf8.png' width="150"></a>

**Note: this mod is server side only and won't work on clients**

A Fabric mod that executes and sends configurable commands and messages respectively on certain events triggered by a player, such as Dying, Joining a server, Killing another player, etc.

The config file is located in the config directory (`config/player_events.json`) and looks like this:

```JSON
{
  "death": {
    "no_entity": [
      [
        "cactus",
        "${player} was pricked to death"
      ],
      [
        "drown"
      ],
      [
        "fall"
      ],
      [
        "flyIntoWall"
      ],
      [
        "hotFloor"
      ],
      [
        "inFire"
      ],
      [
        "inWall"
      ],
      [
        "indirectMagic"
      ],
      [
        "lava"
      ],
      [
        "lightningBolt"
      ],
      [
        "onFire"
      ],
      [
        "outOfWorld"
      ],
      [
        "starve"
      ],
      [
        "wither"
      ]
    ],
    "yes_entity": [
      [
        "entity.minecraft.bee",
        "${player} was stung to death"
      ],
      [
        "entity.minecraft.blaze"
      ],
      [
        "entity.minecraft.cave_spider"
      ],
      [
        "entity.minecraft.creeper"
      ],
      [
        "entity.minecraft.dolphin"
      ],
      [
        "entity.minecraft.drowned"
      ],
      [
        "entity.minecraft.elder_guardian"
      ],
      [
        "entity.minecraft.ender_dragon"
      ],
      [
        "entity.minecraft.enderman"
      ],
      [
        "entity.minecraft.endermite"
      ],
      [
        "entity.minecraft.ghast"
      ],
      [
        "entity.minecraft.guardian"
      ],
      [
        "entity.minecraft.hoglin"
      ],
      [
        "entity.minecraft.husk"
      ],
      [
        "entity.minecraft.illusioner"
      ],
      [
        "entity.minecraft.iron_golem"
      ],
      [
        "entity.minecraft.llama"
      ],
      [
        "entity.minecraft.panda"
      ],
      [
        "entity.minecraft.phantom"
      ],
      [
        "entity.minecraft.piglin"
      ],
      [
        "entity.minecraft.piglin_brute"
      ],
      [
        "entity.minecraft.pillager"
      ],
      [
        "entity.minecraft.polar_bear"
      ],
      [
        "entity.minecraft.pufferfish"
      ],
      [
        "entity.minecraft.ravager"
      ],
      [
        "entity.minecraft.shulker"
      ],
      [
        "entity.minecraft.silverfish"
      ],
      [
        "entity.minecraft.skeleton"
      ],
      [
        "entity.minecraft.slime"
      ],
      [
        "entity.minecraft.spider"
      ],
      [
        "entity.minecraft.stray"
      ],
      [
        "entity.minecraft.tnt"
      ],
      [
        "entity.minecraft.vex"
      ],
      [
        "entity.minecraft.vindicator"
      ],
      [
        "entity.minecraft.witch"
      ],
      [
        "entity.minecraft.wither"
      ],
      [
        "entity.minecraft.wither_skeleton"
      ],
      [
        "entity.minecraft.wolf"
      ],
      [
        "entity.minecraft.zoglin"
      ],
      [
        "entity.minecraft.zombie"
      ],
      [
        "entity.minecraft.zombie_villager"
      ],
      [
        "entity.minecraft.zombified_piglin"
      ]
    ],
    "broadcast_to_everyone": true
  },
  "join": {
    "actions": [
      "Welcome ${player}",
      "/say Hello ${player}"
    ],
    "broadcast_to_everyone": true
  },
  "kill_entity": {
    "actions": [
      "${player} killed ${killedEntity}"
    ],
    "broadcast_to_everyone": true
  },
  "kill_player": {
    "actions": [
      "${player} killed ${killedPlayer}",
      "F ${killedPlayer}"
    ],
    "broadcast_to_everyone": true
  },
  "leave": {
    "actions": [
      "Goodbye ${player}!",
      "/say Hope to see you soon ${player}"
    ],
    "broadcast_to_everyone": true
  }
}
```

For `death` you have sample keys to use, if no message is given as second argument then it is considered disabled and the default death message will be displayed.

There are probably some yesEntity or noEntity sources that were omitted, feel free to add them to your config file and everything should work.

For every other field in `actions` you can define multiple commands, `death` is the only exception.

## Formatting

```
0 is black
1 is dark blue
2 is dark green
3 is dark aqua
4 is dark red
5 is dark purple
6 is gold
7 is gray
8 is dark gray
9 is blue
a is green
b is aqua
c is red
d is light purple
e is yellow
f is white
----------------------------------------------------
k is obfuscated
l is bold
m is strikethrough
n is underline
o is italic
```

