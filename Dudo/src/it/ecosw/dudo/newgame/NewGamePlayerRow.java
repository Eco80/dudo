package it.ecosw.dudo.newgame;

public class NewGamePlayerRow {
	
	private int number;
	private String name;
	private boolean hidden;
	
	/**
	 * Costruttore
	 * @param progressive number
	 */
	public NewGamePlayerRow(int number,String name){
		this.number = number;
		this.name = name;
		hidden = false;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return the hidden
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * @param hidden the hidden to set
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return number+") "+name;
	}
	
	
}
