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
		
		gl.getNpcs().add(1, new Npc(gl));
		gl.getNpcs().get(1).worldX = GameVariables.TILE_SIZE*10;
		gl.getNpcs().get(1).worldY = GameVariables.TILE_SIZE*10;
	
		
		gl.getNpcs().add(2, new Npc(gl));
		gl.getNpcs().get(2).worldX = GameVariables.TILE_SIZE*19;
		gl.getNpcs().get(2).worldY = GameVariables.TILE_SIZE*17;

		
		gl.getNpcs().add(3, new Npc(gl));
		gl.getNpcs().get(3).worldX = GameVariables.TILE_SIZE*8;
		gl.getNpcs().get(3).worldY = GameVariables.TILE_SIZE*1;
	
	
	}
	
	public Npc getNpc() {
		return gl.getNpcs().get(0);
	}
}
