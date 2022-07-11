package tdf;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.util.List;

import javax.swing.KeyStroke;

/**
 * A list of configuration settings, generally loaded from file, for use by the renderer.<br>
 * Before loading data, the Config object should be linked to a Renderer
 * using the {@literal init(Renderer)} function.
 * This makes it so key bindings are properly applied to the renderer
 * and so the renderer is able to use the configuration settings for rendering.<br>
 * While settings should generally be loaded from file with the {@literal load()} function,
 * settings can also be set manually.
 * After manually editing settings, the {@literal setModified()} function should be called
 * so the renderer knows to update its local copy of the configuration.
 */
public class Config {
	/**
	 * Attempts to parse the given string as an integer with no minimum or maximum value.<br>
	 * If the string can not be parsed, a message is written to the console
	 * and the fallback value is returned.
	 * 
	 * @param value The string that should be parsed as an integer
	 * @param friendlyName The name of the config option that should be reported in
	 * the console if the value could not be successfully parsed
	 * @param fallback An existing value that should be returned if the string could not be successfully parsed
	 * @return The parsed integer, or the fallback value if the string could not be successfully parsed
	 */
	public static int valueAsInt(String value, String friendlyName, int fallback) {
		return valueAsInt(value, friendlyName, Integer.MIN_VALUE, Integer.MAX_VALUE, fallback);
	}

	/**
	 * Attempts to parse the given string as an integer with the specified minimum value.<br>
	 * If the string can not be parsed or if the value is outside the allowable range,
	 * a message is written to the console and the fallback value is returned.
	 * 
	 * @param value The string that should be parsed as an integer
	 * @param friendlyName The name of the config option that should be reported in
	 * the console if the value could not be successfully parsed
	 * @param minValue The minimum acceptable value to return
	 * @param fallback An existing value that should be returned if the string
	 * could not be successfully parsed or lies outside the acceptable range
	 * @return The parsed integer, or the fallback value if the string could not
	 * be successfully parsed or its value lies outside the acceptable range
	 */
	public static int valueAsInt(String value, String friendlyName, int minValue, int fallback) {
		return valueAsInt(value, friendlyName, minValue, Integer.MAX_VALUE, fallback);
	}

	/**
	 * Attempts to parse the given string as an integer with the specified minimum and maximum values.<br>
	 * If the string can not be parsed or if the value is outside the allowable range,
	 * a message is written to the console and the fallback value is returned.
	 * 
	 * @param value The string that should be parsed as an integer
	 * @param friendlyName The name of the config option that should be reported in
	 * the console if the value could not be successfully parsed
	 * @param minValue The minimum acceptable value to return
	 * @param maxValue The maximum acceptable value to return
	 * @param fallback An existing value that should be returned if the string
	 * could not be successfully parsed or lies outside the acceptable range
	 * @return The parsed integer, or the fallback value if the string could not
	 * be successfully parsed or its value lies outside the acceptable range
	 */
	public static int valueAsInt(String value, String friendlyName, int minValue, int maxValue, int fallback) {
		try {
			int val = Integer.parseInt(value);
			if(val >= minValue) {
				if(val <= maxValue) {
					return val;
				} else {
					System.err.println(friendlyName + " must be <= " + maxValue);
				}
			} else {
				System.err.println(friendlyName + " must be >= " + minValue);
			}
		} catch(NumberFormatException nfe) {
			System.err.println(friendlyName + " is not a valid number");
		}
		return fallback;
	}

	/**
	 * Attempts to parse the given string as a float with no minimum or maximum value.<br>
	 * If the string can not be parsed, a message is written to the console
	 * and the fallback value is returned.
	 * 
	 * @param value The string that should be parsed as a float
	 * @param friendlyName The name of the config option that should be reported in
	 * the console if the value could not be successfully parsed
	 * @param fallback An existing value that should be returned if the string could not be successfully parsed
	 * @return The parsed float, or the fallback value if the string could not be successfully parsed
	 */
	public static float valueAsFloat(String value, String friendlyName, float fallback) {
		return valueAsFloat(value, friendlyName, Float.MIN_VALUE, Float.MAX_VALUE, fallback);
	}

	/**
	 * Attempts to parse the given string as a float with the specified minimum value.<br>
	 * If the string can not be parsed or if the value is outside the allowable range,
	 * a message is written to the console and the fallback value is returned.
	 * 
	 * @param value The string that should be parsed as a float
	 * @param friendlyName The name of the config option that should be reported in
	 * the console if the value could not be successfully parsed
	 * @param minValue The minimum acceptable value to return
	 * @param fallback An existing value that should be returned if the string
	 * could not be successfully parsed or lies outside the acceptable range
	 * @return The parsed float, or the fallback value if the string could not
	 * be successfully parsed or its value lies outside the acceptable range
	 */
	public static float valueAsFloat(String value, String friendlyName, float minValue, float fallback) {
		return valueAsFloat(value, friendlyName, minValue, Float.MAX_VALUE, fallback);
	}

	/**
	 * Attempts to parse the given string as a float with the specified minimum and maximum values.<br>
	 * If the string can not be parsed or if the value is outside the allowable range,
	 * a message is written to the console and the fallback value is returned.
	 * 
	 * @param value The string that should be parsed as a float
	 * @param friendlyName The name of the config option that should be reported in
	 * the console if the value could not be successfully parsed
	 * @param minValue The minimum acceptable value to return
	 * @param maxValue The maximum acceptable value to return
	 * @param fallback An existing value that should be returned if the string
	 * could not be successfully parsed or lies outside the acceptable range
	 * @return The parsed float, or the fallback value if the string could not
	 * be successfully parsed or its value lies outside the acceptable range
	 */
	public static float valueAsFloat(String value, String friendlyName, float minValue, float maxValue, float fallback) {
		try {
			float val = Float.parseFloat(value);
			if(val >= minValue) {
				if(val <= maxValue) {
					return val;
				} else {
					System.err.println(friendlyName + " must be <= " + maxValue);
				}
			} else {
				System.err.println(friendlyName + " must be >= " + minValue);
			}
		} catch(NumberFormatException nfe) {
			System.err.println(friendlyName + " is not a valid number");
		}
		return fallback;
	}

	/**
	 * Attempts to convert the specified string value to a KeyStroke.<br>
	 * If the string can not be successfully parsed as a KeyStroke,
	 * a message is written to the console and the fallback value is returned.
	 * 
	 * @param value A string represent a KeyStroke. See
	 * <a href="https://docs.oracle.com/javase/8/docs/api/javax/swing/KeyStroke.html#getKeyStroke-java.lang.String-">
	 * https://docs.oracle.com/javase/8/docs/api/javax/swing/KeyStroke.html#getKeyStroke-java.lang.String-</a>
	 * for more details on the string format.
	 * @param friendlyName The name of the config option that should be reported in
	 * the console if the value could not be successfully parsed
	 * @param fallback An existing value that should be returned if the string could not be successfully parsed
	 * @return The parsed KeyStroke, or the fallback value if one could not be successfully parsed
	 */
	public static KeyStroke valueAsKeystroke(String value, String friendlyName, KeyStroke fallback) {
		KeyStroke keystroke = KeyStroke.getKeyStroke(value);
		if(keystroke == null) {
			System.err.println(friendlyName + " is not a valid keystroke");
			keystroke = fallback;
		}
		return keystroke;
	}

	/**
	 * Converts the given string to a boolean value<br>
	 * Positive integers and "true", ignoring case are treated as true
	 * Everything else is considered false
	 * 
	 * @param value The string to derive a boolean value from
	 * @return The resulting boolean value
	 */
	public static boolean valueAsBoolean(String value) {
		try {
			int val = Integer.parseInt(value);
			return val > 0;
		} catch(NumberFormatException nfe) {
			return Boolean.parseBoolean(value);
		}
	}

	public static Color valueAsColor(String value, String friendlyName, Color fallback) {
		int len = value.length();
		boolean isShortenedForm = len == 4;
		boolean ok = (len == 7 || isShortenedForm) && value.charAt(0) == '#';
		if(ok) {
			if(isShortenedForm) {
				// some math after being converted to an int would probably be more efficient
				// but this is probably simpler and more readable code
				char r = value.charAt(1);
				char g = value.charAt(2);
				char b = value.charAt(3);
				value = "#" + r + r + g + g + b + b;
			}
			try {
				int rgb = Integer.parseInt(value.substring(1), 16);
				return new Color(rgb);
			} catch(NumberFormatException e) {}
		}
		System.err.println(friendlyName + " is not a valid color");
		return fallback;
	}

	public final File			file;
	boolean						modified				= false;

	/**
	 * Value indicating whether or not antialiasing
	 * should generally be applied when rendering.<br>
	 * Rendered items can opt out of antialiasing on a per item basis.
	 */
	public boolean				antialias				= false;
	/**
	 * Indicates whether or not the grid should be rendered.
	 * This includes the axes.<br>
	 * The grid is rendered before layer 0.
	 */
	public boolean				drawGrid				= true;
	/**
	 * Flag indicating that when the viewport is scaled,
	 * the cursor should be the point in the viewport that remains the same.
	 * Otherwise, the center of the viewport is the point that remains the same.
	 */
	public boolean				centerResizeOnCursor	= false;

	public Color				backgroundColor			= Color.BLACK;
	/** color of the X and Y axes */
	public Color				axisColor				= Color.ORANGE;
	/** color of a large grid square */
	public Color				gridColorMajor			= Color.LIGHT_GRAY;
	/** color of a small grid square */
	public Color				gridColorMinor			= Color.DARK_GRAY;

	/** size of a grid square */
	public int					gridSizeMinor			= 64;
	/** number of small grid squares in a large grid square */
	public int					gridSizeMajor			= 8;

	private transient Renderer	renderer;

	private KeyBinding			bindingConfig			= new KeyBinding("C", "DEFAULT:CONFIG", () -> {
		load();
		renderer.repaint();
	});
	private KeyBinding			bindingEmpty			= new KeyBinding("E", "DEFAULT:CLEAR", () -> {
																renderer.clear();
																renderer.repaint();
															});
	private KeyBinding			bindingGrid				= new KeyBinding("G", "DEFAULT:GRID", () -> {
		drawGrid = !drawGrid;
		setModified();
		renderer.repaint();
	});

	/**
	 * Create a new config object with a default config file of "config.ini"
	 */
	public Config() {
		this(new File("config.ini"));
	}

	/**
	 * Create a config file that loads its data from the specified file
	 * @param file The file to read configuration data from
	 */
	public Config(File file) {
		if(file == null) {
			throw new IllegalArgumentException("File can not be null");
		}
		this.file = file;
	}
	
	/**
	 * Mark this config file as modified so that the renderer knows that
	 * it should copy the current settings to its own internal copy.<br>
	 * This should be called if changes to the config are made outside
	 * of the normal call to load
	 */
	public final void setModified() {
		modified = true;
	}

	/**
	 * Should be called exactly once per renderer for the lifespan of the program,
	 * ideally before the renderer is added to a parent component like a window.
	 * All other changes are handled internally.
	 */
	public void init(Renderer renderer) {
		this.renderer = renderer;
		renderer.addKeyBinding(bindingConfig);
		renderer.addKeyBinding(bindingEmpty);
		renderer.addKeyBinding(bindingGrid);
		load();
		renderer.setConfig(this);
	}

	/**
	 * Causes this config to be loaded or reloaded from the config file.<br>
	 * Settings that are not present in the config file
	 * or are present but contain invalid values are left unmodified.
	 */
	public final void load() {
		int updateCount = 0;
		try {
			List<String> lines = Files.readAllLines(file.toPath());
			for(String line : lines) {
				line = line.trim();
				if(line.isEmpty() || line.startsWith("#") || line.startsWith("//")) continue;

				int idx = line.indexOf('=');
				if(idx == -1) continue;

				String key = line.substring(0, idx).trim().toLowerCase().replaceAll("[- _]", "");
				String value = line.substring(idx + 1).trim();

				boolean handled = handleStandardConfigLine(key, value);
				if(!handled) {
					handled = handleConfigLine(key, value);
				}
				if(handled) {
					updateCount++;
				}
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		if(updateCount > 0) {
			modified = true;
		}
	}

	private final boolean handleStandardConfigLine(String key, String value) {
		switch(key) {
			case "antialias":
				antialias = valueAsBoolean(value);
				break;
			case "drawgrid":
				drawGrid = valueAsBoolean(value);
				break;
			case "backgroundcolor":
				backgroundColor = valueAsColor(value, "Background color", backgroundColor);
				break;
			case "axiscolor":
				axisColor = valueAsColor(value, "Axis color", axisColor);
				break;
			case "majorgridcolor":
				gridColorMajor = valueAsColor(value, "Major grid color", gridColorMajor);
				break;
			case "minorgridcolor":
				gridColorMinor = valueAsColor(value, "Minor grid color", gridColorMinor);
				break;
			case "majorgridsize":
				gridSizeMajor = valueAsInt(value, "Major grid size", 1, gridSizeMajor);
				break;
			case "minorgridsize":
				gridSizeMinor = valueAsInt(value, "Minor grid size", 1, gridSizeMinor);
				break;
			case "keyclear":
				updateKeyBinding(bindingEmpty, "Clear renderer key", value);
				break;
			case "keyconfig":
				updateKeyBinding(bindingConfig, "Config update key", value);
				break;
			case "keygrid":
				updateKeyBinding(bindingGrid, "Grid toggle key", value);
				break;
			case "centerresizeoncursor":
				centerResizeOnCursor = valueAsBoolean(value);
				break;
			default:
				return false;
		}
		return true;
	}

	/**
	 * This method should be overridden in a subclass to handle program-specific config lines
	 * 
	 * @param key The name of the config option.
	 * This value is assured to be all lowercase
	 * with all dashes, underscores, spaces, and leading and trailing whitespace removed.
	 * @param value The config options value as a string.
	 * Any leading or trailing whitespace is removed
	 * @return true if the config option was handled. Otherwise false.
	 */
	public boolean handleConfigLine(String key, String value) {
		return false;
	}

	final Color gridLineColor(int val) {
		if(val == 0) return axisColor;
		if((val / gridSizeMinor) % gridSizeMajor == 0) return gridColorMajor;
		return gridColorMinor;
	}

	/**
	 * Creates a new configuration object, ideally of the same type as this one.
	 * This is used by the renderer so that it can keep its own copy that
	 * is assured to be unmodified for the duration of a rendered frame.<br>
	 * Config subclasses should either override this method to provide
	 * an instance of the proper subclass or make sure that there is a
	 * public constructor with either a File parameter or no parameters. 
	 */
	public Config newInstance() {
		// attempt to handle users not overriding this themselves
		// when creating a Config subclass
		Config instance = null;
		Class<? extends Config> cls = getClass();
		if(cls != Config.class) {
			try {
				try {
					// try the no argument one first as file won't matter
					// technically, this just calls getConstructor internally
					instance = cls.newInstance();
				} catch(InstantiationException e) {
					// try to handle no nullary constructor
					Constructor<? extends Config> con = cls.getConstructor(File.class);
					if(con != null) {
						instance = con.newInstance(file);
					}
				}
			} catch(ReflectiveOperationException e) {}
		}
		
		if(instance == null) {
			instance = new Config(file);
		}
		
		return instance;
	}

	/**
	 * Copy all config settings from this config to the specified one <br>
	 * Subclasses shoud be sure to call {@code super.copyTo(config)}
	 * so that no settings are left out
	 * 
	 * @param config The Config to copy settings from
	 */
	public void copyTo(Config config) {
		config.antialias = antialias;
		config.drawGrid = drawGrid;
		config.backgroundColor = backgroundColor;
		config.axisColor = axisColor;
		config.gridColorMajor = gridColorMajor;
		config.gridColorMinor = gridColorMinor;
		config.gridSizeMajor = gridSizeMajor;
		config.gridSizeMinor = gridSizeMinor;
		config.bindingConfig = bindingConfig;
		config.bindingEmpty = bindingEmpty;
		config.bindingGrid = bindingGrid;
		config.renderer = renderer;
	}

	/**
	 * Updates the renderer to use the specified KeyBinding
	 * @param binding A new KeyBinding to register with the renderer
	 *	or an existing KeyBinding that should have its key updated
	 * @param friendlyName The name of the binding that should be reported in
	 * the console if the new key value could not be successfully parsed
	 * @param newValue A keypress that the KeyBinding should have. See
	 * <a href="https://docs.oracle.com/javase/8/docs/api/javax/swing/KeyStroke.html#getKeyStroke-java.lang.String-">
	 * https://docs.oracle.com/javase/8/docs/api/javax/swing/KeyStroke.html#getKeyStroke-java.lang.String-</a>
	 * for more details on the string format.
	 */
	public void updateKeyBinding(KeyBinding binding, String friendlyName, String newValue) {
		KeyStroke oldKey = binding.keystroke;
		KeyStroke newKey = valueAsKeystroke(newValue, friendlyName, oldKey);
		if(newKey != oldKey) {
			binding.keystroke = newKey;
			if(renderer == null) {
				System.err.println("Can't set key binding as config doesn't have a copy of the renderer to update");
			} else {
				renderer.addKeyBinding(binding);
			}
		}
	}
}
