package club.moddedminecraft.mmccranks.Command;

import club.moddedminecraft.mmccranks.MMCCRanks;
import club.moddedminecraft.mmccranks.Rank;
import com.mojang.authlib.GameProfile;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandRank extends CommandBase {
    @Override
    public String getCommandName() { return "rank"; }

    @Override
    public String getCommandUsage(ICommandSender sender) { return "Usage: /rank [set/unset] [player] <rank>"; }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.addChatMessage(new ChatComponentTranslation(getCommandUsage(sender)));
            return;
        }
        rank_switch: switch (args[0]) {
            case "unset": {
                try {
                    GameProfile profile = MMCCRanks.server.func_152358_ax().func_152655_a(args[1]);
                    MMCCRanks.player_ranks.remove(profile.getId());
                    MMCCRanks.exportPlayerRanks(MMCCRanks.mod_config_dir);
                    sender.addChatMessage(new ChatComponentTranslation("Successfully removed rank from player " + args[1]));
                    break;
                } catch (Exception e) {
                    ChatComponentTranslation response = new ChatComponentTranslation("Failed to remove rank from player " + args[1]);
                    response.getChatStyle().setColor(EnumChatFormatting.RED);
                    sender.addChatMessage(response);
                    break;
                }
            }
            case "set": {
                try {
                    GameProfile profile = MMCCRanks.server.func_152358_ax().func_152655_a(args[1]);
                    for (Rank rank : MMCCRanks.rank_list) {
                        if (rank.getName().equals(args[2])) {
                            MMCCRanks.player_ranks.remove(profile.getId());
                            MMCCRanks.player_ranks.putIfAbsent(profile.getId(), rank);
                            MMCCRanks.exportPlayerRanks(MMCCRanks.mod_config_dir);
                            sender.addChatMessage(new ChatComponentTranslation("Successfully added rank " + rank.getName() + " to player " + profile.getName()));
                            break rank_switch;
                        }
                    }
                    ChatComponentTranslation response = new ChatComponentTranslation("Error: Rank " + args[2] + " not found.");
                    response.getChatStyle().setColor(EnumChatFormatting.RED);
                    sender.addChatMessage(response);
                    break;
                } catch (Exception e) {
                    ChatComponentTranslation response = new ChatComponentTranslation("Failed to add rank " + args[2] + " to player " + args[1]);
                    response.getChatStyle().setColor(EnumChatFormatting.RED);
                    sender.addChatMessage(response);
                    break;
                }
            }
            default: {
                ChatComponentTranslation response = new ChatComponentTranslation(getCommandUsage(sender));
                response.getChatStyle().setColor(EnumChatFormatting.RED);
                sender.addChatMessage(response);
            }
        }
    }
}
