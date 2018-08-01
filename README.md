# Vulkanized Sand
Rewrite of the [**Falling Sand**](https://en.wikipedia.org/wiki/Falling-sand_game) game with Vulkan (LWJGL3).

This implementation use *parallel computing* (your graphic card) to powerup the game.

The game is under the license GPL-3.0. Feel free to redistribute it (just let me know).

### Requirements

To launch the game, you need to intall:
* Java version 9 or more.
* A recent graphic driver, with Vulkan 1.1 compatibility.

### Download

You can find the last release here:

https://github.com/Ealrann/VSand/releases

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
