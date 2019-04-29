package server.models.map;

public class Position {//TODO:Change class name
	private int row;
	private int column;

	public Position(Cell cell) {
		this.row = cell.getRow();
		this.column = cell.getColumn();
	}

	public Position(Position position) {
		this.row = position.row;
		this.column = position.column;
	}

	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}