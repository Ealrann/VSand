# Vulkanized Sand
Rewrite of the [**Falling Sand**](https://en.wikipedia.org/wiki/Falling-sand_game) game with Vulkan (LWJGL3).

This implementation use *parallel computing* (your graphic card) to powerup the game.

The game is under the license GPL-3.0. Feel free to redistribute it (just let me know).

### Requirements

To launch the game, you need to intall:
* [Java](https://www.oracle.com/technetwork/java/javase/downloads/index.html) (version 9 or more).
* A recent [graphic driver](https://www.howtogeek.com/135976/how-to-update-your-graphics-drivers-for-maximum-gaming-performance/), with [Vulkan 1.0 compatibility](https://en.wikipedia.org/wiki/Vulkan_(API)#Compatibility).

### Download

You can find the last release here:

https://github.com/Ealrann/VSand/releases

If you like the game, and want to buy me a coffee:

[![PayPal](https://www.paypalobjects.com/en_US/i/btn/btn_donate_SM.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=ZETXTGG9ZGENU)

### Any problem?

The game run smooth on my computers, but I cannot test it on every existing configurations. If you find any problem (game doesn´t launch, crash, particles don´t move...), please considers openning an issue:

* First, try to launch the game in debug mode; edit the .bat file to add the debug option:
```set DEFAULT_JVM_OPTS="-Ddebug='true'"```
* Try to reproduce the bug, and see if there is any error in the console.
* Open a new issue. Please describe your problem, how it happens, and paste the log from the console.

### Game

Here some materials you can find in the game:

#### Sand
![Sand](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/sand.gif)

#### Water
![Water](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/water.gif)

#### Plant
![Plant](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/plant.gif)
![Plant1](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/plant2.gif)
![Plant2](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/plant_fire.gif)

#### Wax
![Wax](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/wax.gif)

#### Lava
![Lava](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/lava.gif)

#### Concrete
![Concrete](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/concrete.gif)

#### Dirt
![Dirt](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/dirt.gif)

### Contribution

Feel free to add a new issues if you want to see new things in the game, or if you find a bug. 

You can also contribute by sending me new pull requests.

Clone the repo and download the submodule:

`git clone --recursive https://github.com/Ealrann/Vulkanize.git`

Test the game:

`gradle run`

Prepare the project for eclipse environment:

`gradle eclipse`
