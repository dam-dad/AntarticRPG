package engine;

import engine.entity.Npc;
import threads.GameLoop;

public class AssetSetter {

	GameLoop gl;
	
	public AssetSetter(GameLoop gl) {
		this.gl = gl;
	}
	
	public void setObject() {
		
	}
	
	public void setNpc() {
		gl.getNpcs().add(0, new Npc(gl));
		gl.getNpcs().get(0).worldX = GameVariables.TILE_SIZE;
		gl.getNpcs().get(0).worldY = GameVariables.TILE_SIZE;
	
	}
	
	public Npc getNpc() {
		return gl.getNpcs().get(0);
	}
}
