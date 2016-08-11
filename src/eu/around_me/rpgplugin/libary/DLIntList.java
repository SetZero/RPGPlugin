package eu.around_me.rpgplugin.libary;
import org.apache.commons.lang.ArrayUtils;

public class DLIntList {
	//Privateigentum von DLIntList
	private DNode[] dummy;

	public DLIntList() {
		dummy = new DNode[1];
		dummy[0] = new DNode(null, null);
		//Endloser verweis auf sich selbst
		dummy[0].setNext(dummy);
		dummy[0].setPrev(dummy);
	}
	public DNode add(DNode[] nodefrom, Skill value) {
		//Erstes einfügen? Bin ich mit mir selbst Identisch?
		DNode node = new DNode(dummy, value, dummy);
		for(int i=0;i<nodefrom.length;i++) {
			if(nodefrom[i]==dummy[0]) {
				node = new DNode(dummy, value, dummy);
				DNode[] prevnode = (DNode[]) ArrayUtils.add(dummy[0].getPrev(), node);
				DNode[] nextnode = (DNode[]) ArrayUtils.add(dummy[0].getNext(), node);
				dummy[0].setNext(nextnode);
				dummy[0].setPrev(prevnode);
			} else {
				//Nö? Sag meinen Nachbarn das ich eingezogen bin.
				node = new DNode(dummy[0].getPrev(), value, dummy);
				DNode[] prevnode = (DNode[]) ArrayUtils.add(dummy[0].getPrev(), node);
				DNode[] nextnode = (DNode[]) ArrayUtils.add(dummy[0].getNext(), node);
				
				dummy[0].setNext(nextnode);
				dummy[0].setPrev(prevnode);
				nodefrom[i].setNext(nextnode);
				nodefrom[i].setPrev(prevnode);
			}
		}
		return node;

	}
	public DNode[] getStart() {
		return dummy;
	}
//	public String toString() {
//		DNode<T>[] currentNode = dummy[0].getNext();
//		/*
//		 *	Ich gehe einfach mal davon aus das dies Lesbarer ist, wie wenn ich
//		 * hier 3 Zeilen mehr einfüge für Klammern nur um "[]" zurückzugeben
//		 */
//		if(currentNode == dummy) return "[]";
//		String output = "[";
//
//		while(currentNode != dummy) {
//			output += currentNode.getValue() + ", ";
//			currentNode = currentNode.getNext();
//		}
//		/*
//		 *	Einzeiler sind schön (Ich freu mich schon auf RegExp?)
//		 * Ups...bin hier bin ich über die 80 Zeichen Grenze gekommen...
//		 * Mehr als 2 Zeichen  => Liste nicht  leer => ", " am drei Ende.
//		 * Entfernen wir einfach die letzen beiden Zeichen ;-)
//		 */
//		output = (output.length() > 2) ? output.substring(0, output.length()-2) : output;
//		output += "]";
//		return output;
//	}

	public DLIntListIterator iterator() {
		//Dummy Element übergeben als erstes Element
		return new DLIntListIterator(dummy[0]);
	}
	public DLIntListDescendingIterator descendingIterator() {
		return new DLIntListDescendingIterator(dummy[0]);
	}
}