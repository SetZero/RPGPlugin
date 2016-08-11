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
		if(mycurrnode.getPrev().length > 0) {
			if(mycurrnode.getPrev()[0] != dummy) {
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
		return mycurrnode.getPrev();
	}
}
