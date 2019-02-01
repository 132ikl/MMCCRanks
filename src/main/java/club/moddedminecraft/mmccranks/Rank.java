package club.moddedminecraft.mmccranks;

import net.minecraft.util.EnumChatFormatting;

public class Rank {

    private String name;
    private String display;
    private String prefix;
    private String suffix;
    private EnumChatFormatting color;
    private EnumChatFormatting usernameColor;
    private EnumChatFormatting prefix_color;
    private EnumChatFormatting suffix_color;

    public Rank(String name, String display, String prefix, String suffix, String color, String usernameColor, String prefixColor, String suffixColor) {
        this.name = name;
        this.display = display;
        this.prefix =  prefix;
        this.suffix = suffix;
        this.color = EnumChatFormatting.getValueByName(color);
        this.usernameColor = EnumChatFormatting.getValueByName(usernameColor);
        this.prefix_color = EnumChatFormatting.getValueByName(prefixColor);
        this.suffix_color = EnumChatFormatting.getValueByName(suffixColor);
    }

    public String getName() { return name; }

    public String getDisplay() { return display; }

    public String getPrefix() { return prefix; }

    public String getSuffix() { return suffix; }

    public EnumChatFormatting getColor() { return color; }

    public EnumChatFormatting getUsernameColor() { return usernameColor; }

    public EnumChatFormatting getPrefixColor() { return prefix_color; }

    public EnumChatFormatting getSuffixColor() {  return suffix_color; }

}
