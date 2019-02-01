package club.moddedminecraft.mmccranks;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

public class EventListener {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onServerChatEvent(ServerChatEvent event)
    {
        Rank rank = MMCCRanks.player_ranks.getOrDefault(event.player.getUniqueID(), MMCCRanks.default_rank);

        ChatComponentTranslation newMessage = new ChatComponentTranslation("");

        IChatComponent rankDisplay = new ChatComponentText(rank.getDisplay());
        rankDisplay.getChatStyle().setColor(rank.getColor());

        IChatComponent prefix = new ChatComponentText(rank.getPrefix());
        prefix.getChatStyle().setColor(rank.getPrefixColor());

        IChatComponent username = new ChatComponentText(event.username);
        username.getChatStyle().setColor(rank.getUsernameColor());

        IChatComponent suffix = new ChatComponentText(rank.getSuffix());
        suffix.getChatStyle().setColor(rank.getSuffixColor());

        IChatComponent message = new ChatComponentText(event.message);
        message.getChatStyle().setColor(EnumChatFormatting.RESET);

        newMessage.appendSibling(rankDisplay);
        newMessage.appendSibling(prefix);
        newMessage.appendSibling(username);
        newMessage.appendSibling(suffix);
        newMessage.appendSibling(message);

        event.component = newMessage;
    }
}
