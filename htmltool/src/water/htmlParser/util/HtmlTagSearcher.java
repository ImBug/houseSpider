package water.htmlParser.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.visitors.NodeVisitor;

public class HtmlTagSearcher {
	
	
	public static List<TagNode> findTagNodesByTag(TagNode tagNode,String tagName){
		if(tagNode != null){
			SearchVisitor visitor = new SearchVisitor();
			visitor.setTagName(tagName);
			visitor.root = tagNode;
			tagNode.accept(visitor);
			return visitor.targets;
		}
		return Collections.EMPTY_LIST;
	}
	
	public static class SearchVisitor extends NodeVisitor {

		protected List<TagNode> targets = new ArrayList<TagNode>();

		private String tagName;
		private String word;
		private Node root;
		private Map<String, String> attrMap;
		private boolean visitNext = true;
		private boolean recurseSelf = true;
		
		public void reset() {
			visitNext = true;
			root = null;
			targets.clear();
		}

		public void setTagName(String tagName) {
			if (tagName != null) {
				this.tagName = tagName.toUpperCase();
			}
		}

		public void setWord(String txt) {
			this.word = txt;
		}

		@Override
		public void visitTag(Tag tag) {
			if(tag.getTagName().equals(tagName)){
				boolean add = true;
				if(attrMap != null){
					long count = attrMap.entrySet().stream().filter(entry -> {
						return entry.getValue().equals(tag.getAttribute(entry.getKey()));
					}).count();
					add = attrMap.size() == count;
				}
				if(add)targets.add((TagNode) tag);
			}
		}

		@Override
		public void visitStringNode(Text string) {
			if (word != null) {
				if (string.getText().indexOf(word) > -1) {
					TagNode tnNode = (TagNode) string.getParent();
					while (true) {
						if (tnNode.getTagName().equals(tagName)) {
							targets.add(tnNode);
							visitNext = false;
							break;
						} else {
							if (tnNode == root) {
								break;
							}
							tnNode = (TagNode) tnNode.getParent();
						}
					}
				}
			}
		}

		@Override
		public boolean shouldRecurseChildren() {
			return visitNext;
		}

		@Override
		public boolean shouldRecurseSelf() {
			return recurseSelf;
		}

	}
}
