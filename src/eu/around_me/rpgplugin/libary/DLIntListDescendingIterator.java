package eu.around_me.rpgplugin.libary;

class DLIntListDescendingIterator {
	//Privateigentum von DLIntListDescendingIterator
	private DNode mycurrnode;
	private DNode dummy;

	public DLIntListDescendingIterator(DNode node) {
		dummy = node;
		mycurrnode = node;
	}
	public boolean hasNext() {
		if(mycurrnode.getPrev() != dummy) {
			return true;
		}
		return false;
	}
	public int next() {
		mycurrnode = mycurrnode.getPrev();
		return mycurrnode.getValue();
	}
}
