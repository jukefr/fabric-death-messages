# Fabric Death Messages

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

**Supports [color codes](https://minecraft.gamepedia.com/Formatting_codes#Color_codes) too!**

