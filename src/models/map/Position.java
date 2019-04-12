package models.map;

public class Position {
	private int row;
	private int column;

	Position(Cell cell) {
		this.row = cell.getRow();
		this.column = cell.getColumn();
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}