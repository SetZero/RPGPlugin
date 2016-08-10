package eu.around_me.rpgplugin.libary;


class DLIntListIterator {
	//Privateigentum von DLIntListIterator
	private DNode mycurrnode;
	private DNode dummy;

	public DLIntListIterator(DNode node) {
		dummy = node;
		mycurrnode = node;
	}
	public boolean hasNext() {
		if(mycurrnode.getNext() != dummy) {
			return true;
		}
		return false;
	}
	public int next() throws Exception {
		if(!hasNext()) {
			throw new Exception();
		}
		mycurrnode = mycurrnode.getNext();
		return mycurrnode.getValue();
	}
	public void remove() {
		/*
		 * 1. Hat die Liste überhaupt ein Element?
		 * 2. Bin ich (noch) das nächste Element von meinem Vorgänger?
		 */
		if(mycurrnode == dummy || mycurrnode.getPrev().getNext() != mycurrnode) {
			throw new IllegalStateException();
		}
		mycurrnode.getPrev().setNext(mycurrnode.getNext());
		mycurrnode.getNext().setPrev(mycurrnode.getPrev());
	}
}