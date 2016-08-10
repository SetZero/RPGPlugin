package eu.around_me.rpgplugin.libary;

class DNode {
	//Privateigentum von DNode
	private DNode prev;
	private int value;
	private DNode next;

	//Konstruktoren
	public DNode(DNode prev, int value, DNode next) {
		this.prev = prev;
		this.value = value;
		this.next = next;
	}

	public DNode(DNode prev, DNode next) {
		this.prev = prev;
		this.next = next;
	}
	//Setter
	public void setPrev(DNode prev) {
		this.prev = prev;
	}
	public void setNext(DNode next){
		this.next = next;
	}
	public void setValue(int value){
		this.value = value;
	}
	//Getter
	public DNode getPrev() {
		return this.prev;
	}
	public DNode getNext(){
		return this.next;
	}
	public int getValue(){
		return this.value;
	}
}