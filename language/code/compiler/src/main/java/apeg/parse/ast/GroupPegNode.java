package apeg.parse.ast;

//import java.util.List;
//import apeg.util.Pair;

public interface GroupPegNode extends PegNode {
	/**
	 * @return a set of ranges 
	 */
	//public List<Pair<Character,Character> > getRanges();
	
	/**
	 * @return a string representing the ranges specification 
	 */
	public String getRanges();
}
