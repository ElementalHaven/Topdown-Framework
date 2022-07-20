# Topdown Framework
Library for plotting 2D data such as maps in Java

## Requirements

- JDK 8 or newer to build and Java 8 or newer to run.

## Usage

All classes in the library are in the `tdf` package.
The core of the library is handled in two classes: `Config` and `Renderer`.
Both of these classes are meant to be subclassed to add extra functionality,
although doing so may not be necessary, especially for the `Renderer`.

The `Renderer` class is a viewport for rendering data. It can be embedded within an existing swing UI,
however a convenience method, `Renderer.simpleSetup(String, Config)`, 
is also provided to create a window consisting of only the Renderer.

The `Config` class contains customizable settings.
The Config is linked to a Renderer using the `Config.init(Renderer)` method.
Configs are mapped to Renderers in a 1 to 1 manner, meaning that each Config can only be tied to one Renderer.
The Config file needs to be linked to a Renderer so the Renderer knows what settings to use
and so the Config can properly set and update key bindings in the Renderer.

Generally, settings are loaded from file using `Config.load()` method.
Doing so will load settings from the file specified in Config's constructor,
or `config.ini` within the current working directory if the default constructor is used.
The `load` method is automatically called when the Config is linked to a Renderer with `init`.
Configuration settings can also be set manually.
After manually modifying settings, `Config.setModified()` should be called so
the Renderer know's to update its own internal copy before the next time the viewport is redrawn.

Rendered items are implemented by extending the `Renderable` class
and adding them using `Renderer.add(Renderable)` or `Renderer.add(Renderable, int)`.
The first method is a convenience method for the second, with the layer parameter defaulting to 0.
Layers are rendered low to high, meaning layer 1 will appear on top of layer 0.
The grid is rendered immediately before layer 0, if enabled.
While implementing the `Renderable.render(Graphics2D, Config)` is the only thing that's required
for an item to be rendered, there's a few other things that can be modified to improve rendering.
- `Rectangle bounds` - can be set so the Renderer knows not to draw it if it lies completely outside the viewport.
- `boolean visible` - can be modified to toggle rendering of the item completely.
- `boolean canBeAntialiased()` - can be overridden to make sure no antialiasing is applied to this object.
This is useful for cases such as filled triangles that might have gaps between them if antialiased.

## Builtin Features
- Viewport panning and zooming
- Togglable grid
- Layers for sorting of items
- Customizable background color
- Customizable grid size and colors
- Antialiasing that can be toggled in the config and selectively disabled for individual rendered items
- Customizable key bindings with convenience methods for adding more
- Coordinate readout
- Reversible Y axis
- Occlusion culling support
- Convience method to focus viewport on a specific area

## Default Controls

- **C**: Reload the config file.
- **E**: Clear/empty/erase the viewport of any data.
- **G**: Toggle grid.
- **Mouse Drag**: Move the viewport.
- **Mousewheel**: Zoom in/out.