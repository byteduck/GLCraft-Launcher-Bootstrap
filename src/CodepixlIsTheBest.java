import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.commons.io.FileUtils;

public class CodepixlIsTheBest {
	private static JFrame j = new JFrame();
	public OutputWindow w;
	public String newVer = "(Well this is supposed to be a version number... What went wrong?)";
	
	public final String bootstrapVer = "1";
	
	public static void main(String[] args){
		try {
			new CodepixlIsTheBest();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public CodepixlIsTheBest() throws IOException, InterruptedException{
		j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		j.setSize(Toolkit.getDefaultToolkit().getScreenSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height/2);
		j.setLocationRelativeTo(null);
		OutputWindow w = new OutputWindow(j);
		w.write("Loading Bootstrapper...");
		w.write("Attempting to delete temp directory...");
		FileUtils.deleteDirectory(new File(System.getProperty("user.home")+"/GLCraft/temp"));
		w.write("Deleted!");
		w.write("Checking for launcher update...");
		Files.createDirectories(new File(System.getProperty("user.home")+"/GLCraft/temp").toPath());
		Files.createDirectories(new File(System.getProperty("user.home")+"/GLCraft/launcher").toPath());
		Download d = new Download("https://www.codepixl.net/GLCraft/lver.txt", System.getProperty("user.home")+"/GLCraft/temp/lver.txt", w, false);
		while(d.getStatus() == d.DOWNLOADING){Thread.sleep(10);}
		boolean newVersion = false;
		if(d.getStatus() == d.ERROR){
			w.write("Error downloading file, assuming no new version.");
		}else{
			newVersion = evalVer();
			if(newVersion){
				w.write("New launcher version: "+newVer);
				d = new Download("https://www.codepixl.net/GLCraft/launcher.jar",System.getProperty("user.home")+"/GLCraft/launcher/launcher.jar", w, true);
				w.write("Downloading...");
				while(d.getStatus() == d.DOWNLOADING){Thread.sleep(10);}
			}else{
				w.write("No new version.");
			}
		}
		w.write("Launching...");
		ProcessBuilder pb = new ProcessBuilder("java","-jar", System.getProperty("user.home")+"\\GLCraft\\launcher\\launcher.jar","bootstrapver:"+bootstrapVer);
        Process p = pb.start();
        w.write("Launched!");
        Thread.sleep(1000);
        System.exit(0);
	}
	
	private boolean evalVer() throws IOException{
		String cver = FileUtils.readFileToString(new File(System.getProperty("user.home")+"/GLCraft/temp/lver.txt"));
		File fver = new File(System.getProperty("user.home")+"/GLCraft/launcher/ver.txt");
		File lfile = new File(System.getProperty("user.home")+"/GLCraft/launcher/launcher.jar");
		if(!fver.exists() || !lfile.exists()){
			fver.getParentFile().mkdirs();
			FileUtils.copyFile(new File(System.getProperty("user.home")+"/GLCraft/temp/lver.txt"), fver);
			newVer = cver;
			return true;
		}
		String ver = FileUtils.readFileToString(fver);
		if(!cver.equals(ver)){
			FileUtils.copyFile(new File(System.getProperty("user.home")+"/GLCraft/temp/lver.txt"), fver);
			newVer = cver;
			return true;
		}
		return false;
	}
}
