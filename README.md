# Vulkanized Sand
Rewrite of the [**Falling Sand**](https://en.wikipedia.org/wiki/Falling-sand_game) game with Vulkan ([LWJGL3](https://www.lwjgl.org/)).

Since that kind of game requires a lot of computing power, I wanted to exploit the graphic card capabilities to process and to display the particles (*parallel computing*).

License GPL-3.0. Feel free to redistribute (please let me know).
Since version 1.2, the VSand releases contain an [OpenJDK with OpenJ9](https://www.eclipse.org/openj9/).

Since v1.2.2, you can launch the game in *benchmark* mode, with or without window (*headless*). This mode run a predefined scene as fast as possible (no v-sync wait), for a fixed number of frames, and will then compute a score based on the duration.

### Requirements

To launch the game, you only need to intall a recent [graphic driver](https://www.howtogeek.com/135976/how-to-update-your-graphics-drivers-for-maximum-gaming-performance/), with [Vulkan 1.0 compatibility](https://en.wikipedia.org/wiki/Vulkan_(API)#Compatibility).

### Download

You can find the last release for free on [itch.io](https://ealrann.itch.io/vsand).

### Any problem?

The game run smoothly on my computers, but I cannot test it on every existing configurations. If you find any problem (game doesn´t start, crash, particles don´t move...), please consider opening an issue, or directly contact me.

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

Feel free to open an issue if you want to suggest a feature or report a bug.

If you want to contribute to the project, or simply run it from source:

#### 1. JDK
You need to setup a JDK 25 (for example, [Eclipse Temurin](https://adoptium.net/)).

#### 2. Clone the repository
Clone from the `root` branch, because it is preconfigured with the correct submodule layout and references:

```shell
git clone --recursive --single-branch --branch root https://github.com/Ealrann/VSand.git
```

#### 3. Run the game
From the VSand project directory:
```shell
cd VSand/VSand/
./gradlew run
```

To update an existing clone, go back to the root directory (the one containing the submodules *Lily-core*, *Lily-vulkan*, *lmf*, and *VSand*), and use:
```
git pull --rebase --recurse-submodules
```

### Frameworks/API used
#### Graphic
- [**LWJGL 3**](https://www.lwjgl.org/)
- [**JOML**](https://github.com/JOML-CI/JOML)
- [**Vulkan**](https://www.khronos.org/vulkan/)
- [**Nuklear**](https://github.com/vurtun/nuklear)
#### Design/Code
- [**EMF** (Eclipse Modeling Framework)](https://www.eclipse.org/modeling/emf/)
- [**Java** (especially jigsaw)](https://openjdk.java.net/projects/jigsaw/)
#### Build
- [**Gradle**](https://gradle.org/)
- [**Java9-modularity**](https://github.com/java9-modularity/gradle-modules-plugin)
- [**JLink**](https://docs.oracle.com/javase/9/tools/jlink.htm)
