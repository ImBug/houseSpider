package water.htmlParser.entity;

import java.util.HashSet;
import java.util.Set;

import org.htmlparser.Text;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.visitors.NodeVisitor;

public class TextVisitor extends NodeVisitor {
	
	private StringBuffer content = new StringBuffer();
	private Set<String> filterWords = new HashSet<>(10);
	
	public void resetContent(){
		content.delete(0, content.length());
	}
	
	public String getContent(){
		return content.toString();
	}
	
	public void addFilterWord(String word){
		filterWords.add(word);
	}
	
	@Override
	public void visitStringNode(Text txt) {
		TagNode tag = (TagNode)txt.getParent();
		if(tag instanceof Div || tag instanceof Span ||tag instanceof TitleTag){
			boolean append = true;
			for(String word:filterWords){
				if(txt.getText().contains(word)){
					append = false;
					break;
				}
			}
			if(append)content.append("\t").append(txt.getText()).append("\n");
		}
	}

	@Override
	public boolean shouldRecurseChildren() {
		return true;
	}

	@Override
	public boolean shouldRecurseSelf() {
		return true;
	}
	
	
}
