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
		if(mycurrnode.getNext().length > 0) {
			System.out.println("OK1");
			if(mycurrnode.getNext()[0] != dummy) {
				System.out.println("OK2");
				return true;
			}
		}
		return false;
	}
	public DNode[] next(DNode nextelement) throws Exception {
		if(!hasNext()) {
			throw new Exception();
		}
		mycurrnode = nextelement;
		return mycurrnode.getNext();
	}
	public DNode[] subnodes() {
		return mycurrnode.getNext();
	}
	public void remove() {
		/*
		 * 1. Hat die Liste überhaupt ein Element?
		 * 2. Bin ich (noch) das nächste Element von meinem Vorgänger?
		 */
			boolean error = true;
			if(mycurrnode == dummy) {
				throw new IllegalStateException();
			}
			for(int j=0;j<mycurrnode.getPrev().length;j++) {
				for(int k=0;k<mycurrnode.getNext().length;k++) {
					 if(mycurrnode.getPrev()[j].getNext()[k] == mycurrnode) {
						 error = false;
					}
				}
			}
			if(error) {
				throw new IllegalStateException();
			}
			for(int j=0;j<mycurrnode.getPrev().length;j++) {
				mycurrnode.getPrev()[j].setNext(mycurrnode.getNext());
			}
			for(int j=0;j<mycurrnode.getNext().length;j++) {
				mycurrnode.getNext()[j].setPrev(mycurrnode.getPrev());
			}
	}
}