# Vulkanized Sand

VSand is a [falling-sand](https://en.wikipedia.org/wiki/Falling-sand_game)
game whose simulation and display run through Vulkan
([LWJGL 3](https://www.lwjgl.org/)).

Since that kind of game requires substantial compute power, the simulation
uses the graphics card to process and display particles in parallel.

License GPL-3.0. Feel free to redistribute (please let me know).

Some historical releases bundled an OpenJDK/OpenJ9 runtime. The current
`jlink` build uses the active JDK's `java.home`, so the runtime vendor is
the one selected by the builder rather than a project-wide guarantee.

Since v1.2.2, the game also has a benchmark mode, with a window or headless.
It runs a predefined scene without waiting for v-sync, for a fixed number of
frames, then computes a duration-based score.

## Architecture

VSand is the end-to-end consumer of the Logoce stack:

- `org.sheepy.vsand/src/main/model/VSand.lm` defines the game metamodel.
- `org.sheepy.vsand/src/main/resources/Application.vsand.lm` is the main
  application instance: engines, compute/graphic pipelines, GPU resources,
  materials, transformations, scene, and UI.
- Java `@ModelExtender` classes implement game-specific services and bind
  named model objects to buffer content, dispatch sizes, input, and behavior.
- Lily-vulkan allocations materialize the modeled Vulkan/OpenAL resources.
- Compute shaders under `org.sheepy.vsand/src/main/shader` implement drawing,
  board simulation, liquid pressure, and board-to-image conversion.

Read [../ARCHITECTURE.md](../ARCHITECTURE.md) for adapter/allocation discovery
and the complete launch loop. Focused implementation references include:

- [doc/board-update-algorithm.md](doc/board-update-algorithm.md)
- [doc/liquid-pressure.md](doc/liquid-pressure.md)
- [doc/liquid-pressure-baseline.md](doc/liquid-pressure-baseline.md)
- [state-dump-runner.md](state-dump-runner.md)

Other root-level algorithm Markdown files are design/experiment history. Treat
the current shaders, `Application.vsand.lm`, and the focused documents above
as the source of truth, and re-check historical notes before implementing
from them.

## Requirements

To launch a packaged game, install a recent graphics driver with
[Vulkan 1.0 compatibility](https://en.wikipedia.org/wiki/Vulkan_(API)#Compatibility).
A source build additionally requires JDK 25.

## Download

You can find the last release for free on [itch.io](https://ealrann.itch.io/vsand).

## Any problem?

The game cannot be tested on every hardware and driver configuration. If it
does not start, crashes, or particles do not move, please open an issue or
contact the author.

## Game

Here some materials you can find in the game:

### Sand
![Sand](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/sand.gif)

### Water
![Water](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/water.gif)

### Plant
![Plant](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/plant.gif)
![Plant1](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/plant2.gif)
![Plant2](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/plant_fire.gif)

### Wax
![Wax](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/wax.gif)

### Fuel
![Fuel](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/Fuel.gif)

### Lava
![Lava](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/lava.gif)

### Petrol
![Petrol](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/Petrol.gif)

### Concrete
![Concrete](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/concrete.gif)

### Dirt
![Dirt](https://raw.githubusercontent.com/Ealrann/VSand/master/doc/image/dirt.gif)

## Development

Feel free to open an issue if you want to suggest a feature or report a bug.

The current source layout is a Gradle composite with sibling `lmf`,
`Lily-core`, `Lily-vulkan`, and `VSand` builds. From the composite
workspace root:

```shell
./gradlew -p VSand :org.sheepy.vsand:run
./gradlew -p VSand test
```

Build a packaged runtime image with:

```shell
./gradlew -p VSand :org.sheepy.vsand:jlink
```

## Frameworks and APIs

### Graphics

- [**LWJGL 3**](https://www.lwjgl.org/)
- [**JOML**](https://github.com/JOML-CI/JOML)
- [**Vulkan**](https://www.khronos.org/vulkan/)
- [**Nuklear**](https://github.com/vurtun/nuklear)

### Design and code

- [**LMF**](../lmf/README.md), the textual modeling framework used by the
  application, engine, and game models
- [**Lily-core**](../Lily-core/README.md), the application runtime
- [**Lily-vulkan**](../Lily-vulkan/README.md), the Vulkan/OpenAL backend
- **Java 25** and JPMS

### Build

- [**Gradle**](https://gradle.org/)
- [**JLink**](https://docs.oracle.com/javase/9/tools/jlink.htm)
