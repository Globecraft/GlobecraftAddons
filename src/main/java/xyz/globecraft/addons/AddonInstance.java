package xyz.globecraft.addons;

public interface AddonInstance {
	/**
	 * When the addon gets enabled, this gets called.
	 */
	public void enable();

	/**
	 * When the addon gets disabled, this gets called.
	 */
	public void disable();

	/**
	 * Whether the addon is enabled or not in the config
	 * @return If the addon is enabled or not
	 */
	public boolean enabled();

	/**
	 * Whether the addon is loaded
	 * @return If the addon is loaded or not
	 */
	public boolean loaded();
	
	/**
	 * Naming convention for addons in the plugin
	 * @return Name to display in /gcaddons.
	 */
	public String getName();
	
	/**
	 * Write a short description here
	 * @return A short description of the addon and what it does
	 */
	public String getDescription();
	
	/**
	 * A multi-line message to send to the player everytime they /gcaddon this specific addon.
	 * Preferably store the array in a private static final form
	 * @return A string array of line by line description
	 */
	public String[] getHelpScreen();
}
