package io.github.xcusanaii.parcaea.command;

import io.github.xcusanaii.parcaea.event.CommandMacroHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.util.Arrays;

public class PCMCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "pcm";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/pcm <msg1> <msg2>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 2) {
            throw new CommandException("Invalid arguments. Usage: " + getCommandUsage(sender));
        }
        CommandMacroHandler.msg1 = args[0];
        CommandMacroHandler.msg2 = args[1];
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            ChatComponentText info = new ChatComponentText("Message set to ");
            ChatComponentText txtArgs = new ChatComponentText(Arrays.toString(args));
            txtArgs.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA));
            info.appendSibling(txtArgs);
            player.addChatMessage(info);
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
