package config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

public class UserConfig {

	private File f;
	private Properties p;
	
	public UserConfig(File f) {
		p = new Properties();
		if(f == null)
			throw new NullPointerException();
		this.f = f;
		try {
			p.load(new FileReader(f, Charset.forName("UTF-8")));
			if(!f.exists()) {
				if(!f.createNewFile()) 
					throw new RuntimeException("No se ha podido crear el archivo de configuración");
				p.setProperty("musicMuted", "false");
				p.setProperty("sfxMuted", "false");
				p.setProperty("musicVolume", "100");
				p.setProperty("sfxVolume", "100");
				saveConfig();
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}

	public void saveConfig() {
		try {
			p.store(new FileOutputStream(f), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Properties getConfig() {
		return p;
	}
	
}
