/*
 ** 2014 July 28
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */

package info.ata4.minecraft.minema.client.cmd;

import info.ata4.minecraft.minema.Minema;
import info.ata4.minecraft.minema.client.modules.DisplaySizeModifier;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class CommandMinema extends CommandBase {
    
    private final Minema minema;

    public CommandMinema(Minema minema) {
        this.minema = minema;
    }
    
    @Override
    public String getCommandName() {
        return "minema";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.minema.usage";
    }
    
    @Override
    public void processCommand(ICommandSender sender, String[] args) throws WrongUsageException {
        if (args.length == 0) {
            throw new WrongUsageException(getCommandUsage(sender));
        }
        
        String cmd = args[0];
        
        if (cmd.equals("enable")) {
            minema.enable();
        } else if (cmd.equals("disable")) {
            minema.disable();
        } else if (cmd.equals("resize")) {
        	//Usage: /minema resize 720p   or   /minema resize 1280x720     
            DisplaySizeModifier modifier = new DisplaySizeModifier(minema.getConfig());
            
            int width, height, mode = 0;
            switch(args.length){
            case 3:
            	mode = Integer.parseInt(args[2]);
            case 2:
            	String res = args[1];
            	if(res.endsWith("p")){
            		res = res.substring(0,res.length()-1);
            		int p = Integer.parseInt(res);
            		height = p;
            		width = (int) (p*16.0/9.0);
            		// eg. for 480p height becomes 853, so it rounds it up to multiple of 2
            		if((height & 1) == 1) height++;
            	}else{
            		String[] d = res.split("x");
            		width = Integer.parseInt(d[0]);
            		height = Integer.parseInt(d[1]);
            	}
            	break;
            default:
            	throw new WrongUsageException(getCommandUsage(sender));
            }
            
            
            modifier.resize(width, height);
            
            switch (mode) {
                case 0:
                    try {
                        Display.setDisplayMode(new DisplayMode(width, height));
                    } catch (LWJGLException ex) {
                        throw new RuntimeException("Can't resize LWJGL display", ex);
                    }
                    break;
                
                case 1:
                    modifier.setFramebufferTextureSize(
                            Math.max(width, Display.getWidth()),
                            Math.max(height, Display.getHeight()));
                    break;
            }
        } else {
            throw new WrongUsageException(getCommandUsage(sender));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
}
