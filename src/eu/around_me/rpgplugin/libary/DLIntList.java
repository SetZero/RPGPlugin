package eu.around_me.rpgplugin.libary;

public class DLIntList {
	//Privateigentum von DLIntList
	private DNode dummy;

	public DLIntList() {
		dummy = new DNode(null, null);
		//Endloser verweis auf sich selbst
		dummy.setNext(dummy);
		dummy.setPrev(dummy);
	}
	public void add(int value) {
		//Erstes einf�gen? Bin ich mit mir selbst Identisch?
		if(dummy.getPrev()==dummy) {
			DNode node = new DNode(dummy, value, dummy);
			dummy.setNext(node);
			dummy.setPrev(node);
		} else {
			//N�? Sag meinen Nachbarn das ich eingezogen bin.
			DNode node = new DNode(dummy.getPrev(), value, dummy);
			dummy.getPrev().setNext(node);
			dummy.setPrev(node);
		}

	}
	public String toString() {
		DNode currentNode = dummy.getNext();
		/*
		 *	Ich gehe einfach mal davon aus das dies Lesbarer ist, wie wenn ich
		 * hier 3 Zeilen mehr einf�ge f�r Klammern nur um "[]" zur�ckzugeben
		 */
		if(currentNode == dummy) return "[]";
		String output = "[";

		while(currentNode != dummy) {
			output += currentNode.getValue() + ", ";
			currentNode = currentNode.getNext();
		}
		/*
		 *	Einzeiler sind sch�n (Ich freu mich schon auf RegExp?)
		 * Ups...bin hier bin ich �ber die 80 Zeichen Grenze gekommen...
		 * Mehr als 2 Zeichen  => Liste nicht  leer => ", " am drei Ende.
		 * Entfernen wir einfach die letzen beiden Zeichen ;-)
		 */
		output = (output.length() > 2) ? output.substring(0, output.length()-2) : output;
		output += "]";
		return output;
	}

	public DLIntListIterator iterator() {
		//Dummy Element �bergeben als erstes Element
		return new DLIntListIterator(dummy);
	}
	public DLIntListDescendingIterator descendingIterator() {
		return new DLIntListDescendingIterator(dummy);
	}
}