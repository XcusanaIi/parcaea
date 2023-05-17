package io.github.xcusanaii.parcaea.command;

import io.github.xcusanaii.parcaea.event.CommandMacroHandler;
import io.github.xcusanaii.parcaea.event.RecordHandler;
import io.github.xcusanaii.parcaea.io.JumpLoader;
import io.github.xcusanaii.parcaea.io.RecordSaver;
import io.github.xcusanaii.parcaea.model.Chart;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.util.Arrays;
import java.util.List;

public class PPKCommand extends CommandBase implements ICommand {

    @Override
    public String getCommandName() {
        return "ppk";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/ppk <help/save/en/scm/record>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            throw new CommandException("Invalid arguments. Usage: " + getCommandUsage(sender));
        }
        if (args[0].equals("help")) {
            if (sender instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) sender;
                ChatComponentText info = new ChatComponentText(getCommandHelp());
                info.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD));
                player.addChatMessage(info);
            }
        }else if (args[0].equals("save")) {
            boolean bl;
            if (args.length > 1) {
                bl = RecordSaver.saveLastInput(args[1]);
            }else {
                bl = RecordSaver.saveLastInput("record");
            }
            if (sender instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) sender;
                ChatComponentText info;
                if (bl) {
                    JumpLoader.reloadJump();
                    Chart.toChart();
                    info = new ChatComponentText("Record saved!");
                    info.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN));
                }else {
                    info = new ChatComponentText("Fail to save record!");
                    info.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED));
                }
                player.addChatMessage(info);
            }
        }else if (args[0].equals("en")) {
            CfgGeneral.enableMod = !CfgGeneral.enableMod;
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
        }else if (args[0].equals("scm")) {
            if (args.length < 3) {
                throw new CommandException("Invalid arguments. Usage: " + getCommandUsageScm());
            }
            CommandMacroHandler.msg1 = args[1];
            CommandMacroHandler.msg2 = args[2];
            if (sender instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) sender;
                ChatComponentText info = new ChatComponentText("Message set to ");
                ChatComponentText txtArgs = new ChatComponentText(Arrays.toString(new String[]{args[1], args[2]}));
                txtArgs.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA));
                info.appendSibling(txtArgs);
                player.addChatMessage(info);
            }
        }else if (args[0].equals("record")) {
            RecordHandler.isInRecord = !RecordHandler.isInRecord;
            if (RecordHandler.isInRecord) RecordHandler.onStartRecord();
            if (sender instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) sender;
                ChatComponentText info = new ChatComponentText("Recording");
                info.setChatStyle(new ChatStyle().setColor(RecordHandler.isInRecord ? EnumChatFormatting.GREEN : EnumChatFormatting.RED));
                player.addChatMessage(info);
            }
        }
    }

    private String getCommandUsageScm() {
        return "/ppk scm <msg1> <msg2>";
    }

    private String getCommandHelp() {
        return "/ppk save [file_name]\n" +
                "/ppk en\n" +
                "/ppk scm <msg1> <msg2>\n" +
                "/ppk record";
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "save", "en", "scm", "record");
        }
        return null;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
