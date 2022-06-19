package tdf;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

/**
 * Represents a bindable key or combination of keys that maps to an action 
 */
public class KeyBinding {
	private static class RunnableAsAction extends AbstractAction {
		private final Runnable action;
		
		private RunnableAsAction(Runnable action) {
			this.action = action;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			action.run();
		}
	}
	
	/**
	 * A unique value so that this binding to action mapping
	 * can be properly identified by the Renderer
	 */
	public final String	id;
	/**
	 * An action to be performed when the key combination is pressed.
	 */
	public Action		action;
	/**
	 * The key or combination of keys to be pressed to perform the action.
	 */
	public KeyStroke	keystroke;

	/**
	 * Initializes this KeyBinding with the given keystroke, id, and action
	 * 
	 * @param keystroke The key or combination of keys to be pressed to perform the action.
	 * @param id A unique value so that this binding to action mapping
	 * can be properly identified by the Renderer
	 * @param action An action to be performed when the key combination is pressed.
	 * @throws IllegalArgumentException if keystroke is null
	 */
	public KeyBinding(KeyStroke keystroke, String id, Action action) {
		if(keystroke == null) {
			throw new IllegalArgumentException("Keystroke provided was invalid");
		}
		this.id = id;
		this.keystroke = keystroke;
		this.action = action;
	}

	/**
	 * Initializes this KeyBinding with the given keystroke, id, and action
	 * 
	 * @param keystroke A string representing the key or
	 * combination of keys to be pressed to perform the action.
	 * @param id A unique value so that this binding to action mapping
	 * can be properly identified by the Renderer
	 * @param action An action to be performed when the key combination is pressed.
	 * @throws IllegalArgumentException if keystroke can't be mapped to a valid KeyStroke
	 * @see KeyStroke
	 */
	public KeyBinding(String keystroke, String id, Action action) {
		this(KeyStroke.getKeyStroke(keystroke), id, action);
	}

	/**
	 * Initializes this KeyBinding with the given keystroke, id, and action
	 * 
	 * @param keystroke The key or combination of keys to be pressed to perform the action.
	 * @param id A unique value so that this binding to action mapping
	 * can be properly identified by the Renderer
	 * @param action An action to be performed when the key combination is pressed.
	 * The action is specified in the form of a Runnable for convenience
	 * @throws IllegalArgumentException if keystroke can't be mapped to a valid KeyStroke
	 * @see KeyStroke
	 */
	public KeyBinding(String keystroke, String id, Runnable action) {
		this(KeyStroke.getKeyStroke(keystroke), id, new RunnableAsAction(action));
	}
}
