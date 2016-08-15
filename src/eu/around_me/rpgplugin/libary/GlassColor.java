package eu.around_me.rpgplugin.libary;

import org.bukkit.DyeColor;

public enum GlassColor {
	WHITE(0), ORANGE(1), MAGENTA(2), LIGHT_BLUE(3), YELLOW(4), LIME(5), PINK(6),
	GRAY(7), LIGHT_GRAY(8), CYAN(9), PURPLE(10), BLUE(11), BROWN(12), GREEN(13),
	RED(14), BLACK(15);
	
	
	int value;
	GlassColor(int value) {
		this.value = value;
	}
	
	public static int DyetoGlass(DyeColor color) {
		if(color == DyeColor.WHITE) {
			return 0;
		} else if(color == DyeColor.ORANGE) {
			return 1;
		} else if(color == DyeColor.MAGENTA) {
			return 2;
		} else if(color == DyeColor.LIGHT_BLUE) {
			return 3;
		} else if(color == DyeColor.YELLOW) {
			return 4;
		} else if(color == DyeColor.LIME) {
			return 5;
		} else if(color == DyeColor.PINK) {
			return 6;
		} else if(color == DyeColor.GRAY) {
			return 7;
		} else if(color == DyeColor.CYAN) {
			return 9;
		} else if(color == DyeColor.PURPLE) {
			return 10;
		} else if(color == DyeColor.BLUE) {
			return 11;
		} else if(color == DyeColor.BROWN) {
			return 12;
		} else if(color == DyeColor.GREEN) {
			return 13;
		} else if(color == DyeColor.RED) {
			return 14;
		} else if(color == DyeColor.BLACK) {
			return 15;
		}  
		return 0;
	}
}
