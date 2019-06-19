# Vulkanized Sand
Rewrite of the [**Falling Sand**](https://en.wikipedia.org/wiki/Falling-sand_game) game with Vulkan ([LWJGL3](https://www.lwjgl.org/)).

Since that kind of game requires a lot of computing power, I wanted to exploit the graphic card capabilities to process and to display the particles (*parallel computing*).

License GPL-3.0. Feel free to redistribute (please let me know).
Since version 1.2, the VSand releases contain an [OpenJDK with OpenJ9](https://www.eclipse.org/openj9/).

Since v1.2.2, you can launch the game in *benchmark* mode, with or without window (*headless*). This mode run a predefined scene as fast as possible (no v-sync wait), for a fixed number of frames, and will then compute a score based on the duration.

### Requirements

To launch the game, you only need to intall a recent [graphic driver](https://www.howtogeek.com/135976/how-to-update-your-graphics-drivers-for-maximum-gaming-performance/), with [Vulkan 1.0 compatibility](https://en.wikipedia.org/wiki/Vulkan_(API)#Compatibility).

### Download

You can find the last release here:

https://github.com/Ealrann/VSand/releases

The game is also available on [itch.io](https://ealrann.itch.io/vsand).

If you like the game, and want to buy me a coffee:

[![PayPal](https://www.paypalobjects.com/en_US/i/btn/btn_donate_SM.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=ZETXTGG9ZGENU)

### Any problem?

The game run smooth on my computers, but I cannot test it on every existing configurations. If you find any problem (game doesn´t launch, crash, particles don´t move...), please considers opening an issue, or directly contact me.

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

#### Fuel
![Fuel](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/Fuel.gif)

#### Lava
![Lava](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/lava.gif)

#### Petrol
![Petrol](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/Petrol.gif)

#### Concrete
![Concrete](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/concrete.gif)

#### Dirt
![Dirt](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/dirt.gif)

### Contribution

Feel free to add a new issues if you want to see new things in the game, or if you find a bug.

If you want to contribute to the code, you first need to install:
- Git.
- Gradle.

Clone the repo and download the submodule:

`git clone --recursive https://github.com/Ealrann/VSand.git`

Test the game:

`gradle run`

Prepare the project for eclipse environment:

`gradle eclipse`

### Frameworks/API used
#### Graphic
- [**LWJGL 3**](https://www.lwjgl.org/)
- [**Vulkan**](https://www.khronos.org/vulkan/)
- [**Nuklear**](https://github.com/vurtun/nuklear)
#### Design/Code
- [**EMF** (Eclipse Modeling Framework)](https://www.eclipse.org/modeling/emf/)
- [**Java** (especially jigsaw)](https://openjdk.java.net/projects/jigsaw/)
#### Build
- [**Gradle**](https://gradle.org/)
- [**Java9-modularity**](https://github.com/java9-modularity/gradle-modules-plugin)
- [**JLink**](https://docs.oracle.com/javase/9/tools/jlink.htm)
- [**Badass-jlink-plugin**](https://github.com/beryx/badass-jlink-plugin)
