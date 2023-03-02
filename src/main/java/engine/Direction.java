package engine;

public enum Direction {

	UP("up"), DOWN("down"), LEFT("left"), RIGHT("right");
	
	private String direccion;

    Direction(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean equalsByDireccion(Direction direction) {
    	
        return this.direccion.equals(direction.getDireccion());
    }
}
