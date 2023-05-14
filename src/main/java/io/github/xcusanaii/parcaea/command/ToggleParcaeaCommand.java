package io.github.xcusanaii.parcaea.command;

import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class ToggleParcaeaCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "parcaea";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/parcaea <enable/disable>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0 || (!args[0].equals("enable") && !args[0].equals("disable"))) {
            throw new CommandException("Invalid arguments. Usage: " + getCommandUsage(sender));
        }
        CfgGeneral.enableMod = args[0].equals("enable");
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            ChatComponentText info;
            if (CfgGeneral.enableMod) {
                info = new ChatComponentText("Enable Parcaea");
                info.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN));
            }else {
                info = new ChatComponentText("Disable Parcaea");
                info.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED));
            }
            player.addChatMessage(info);
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
