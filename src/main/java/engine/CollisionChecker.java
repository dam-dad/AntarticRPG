package engine;

import engine.entity.Entity;
import threads.GameLoop;

public class CollisionChecker {

	private GameLoop loop;
	
	public CollisionChecker(GameLoop loop) {
		if(loop == null)
			throw new NullPointerException("Valor nulo.");
		this.loop = loop;
	}
	
	public void checkTile(Entity e) {

	    int leftX = (int) (e.worldX + e.areaSolid.getX());
	    int rightX =  (int) (e.worldX + e.areaSolid.getX() + e.areaSolid.getWidth());
	    int topY = (int) (e.worldY + e.areaSolid.getY());
	    int bottomY = (int) (e.worldY + e.areaSolid.getY() + e.areaSolid.getHeight());

	    int leftCol = leftX / GameVariables.TILE_SIZE < 0 ? 0 : leftX / GameVariables.TILE_SIZE;
	    int rightCol = rightX / GameVariables.TILE_SIZE >= loop.getHandler().getMapNum()[0].length ? loop.getHandler().getMapNum()[0].length - 1 : rightX / GameVariables.TILE_SIZE;
	    int topRow = topY / GameVariables.TILE_SIZE < 0 ? 0 : topY / GameVariables.TILE_SIZE;
	    int bottomRow = bottomY / GameVariables.TILE_SIZE >= loop.getHandler().getMapNum().length ? loop.getHandler().getMapNum().length - 1 : bottomY / GameVariables.TILE_SIZE;
	    
	    System.out.println("TopRow: " + topRow);
	    System.out.println("BottomRow: " + bottomRow);
	    System.out.println("LeftCol: " + leftCol);
	    System.out.println("RightCol: " + rightCol);
	    
	    int tile1, tile2;

	    switch(e.direction) {
        case UP: 
            topRow = (int) ((topY - e.speed) / GameVariables.TILE_SIZE); 
            tile1 = loop.getHandler().getMapNum()[leftCol][topRow];
            tile2 = loop.getHandler().getMapNum()[rightCol][topRow];
            if(loop.getHandler().getTiles()[tile1].colision || loop.getHandler().getTiles()[tile2].colision) {
                e.colision = true;
            }
            break;
        case DOWN: 
            bottomRow = (int) ((bottomY + e.speed) / GameVariables.TILE_SIZE);
            tile1 = loop.getHandler().getMapNum()[leftCol][bottomRow];
            tile2 = loop.getHandler().getMapNum()[rightCol][bottomRow];
            if(loop.getHandler().getTiles()[tile1].colision || loop.getHandler().getTiles()[tile2].colision) {
                e.colision = true;
            }
            break;
        case LEFT: 
            leftCol = (int) ((leftX - e.speed) / GameVariables.TILE_SIZE);
            tile1 = loop.getHandler().getMapNum()[leftCol][topRow];
            tile2 = loop.getHandler().getMapNum()[leftCol][bottomRow];
            if(loop.getHandler().getTiles()[tile1].colision || loop.getHandler().getTiles()[tile2].colision) {
                e.colision = true;
            }
            break;
        case RIGHT:
            rightCol = (int) ((rightX + e.speed) / GameVariables.TILE_SIZE);
            tile1 = loop.getHandler().getMapNum()[rightCol][topRow];
            tile2 = loop.getHandler().getMapNum()[rightCol][bottomRow];
            if(loop.getHandler().getTiles()[tile1].colision || loop.getHandler().getTiles()[tile2].colision)  {
                e.colision = true;
            }
            break;
	    }
		
	}
	
	
}
