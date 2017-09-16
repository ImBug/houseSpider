package water.htmlParser.util;

import org.htmlparser.Node;
import org.htmlparser.Text;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.NodeVisitor;

import water.tool.util.string.StringUtil;

public class HtmlUtil {
	
	public static String getTextOfTag(TagNode tag){
		TextVisitor visitor = new TextVisitor();
		tag.accept(visitor);
		return visitor.txt;
	}
	
	
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 * 	0 : parent 
	 *  1 : brother
	 *  2 : else
	 */
	public static int findGenetic(Node a,Node b){
		TagNode tgA = findFirstTagNode(a);
		TagNode tgB = findFirstTagNode(b);
		if(tgA == tgB){
			return 1;
		}else{
			TagNode ptgA = findFirstTagNode(tgA.getParent());
			TagNode ptgB = findFirstTagNode(tgB.getParent());
			if(ptgA == tgB || ptgB == tgA) return 0;
		}
		return 2;
	} 
	
	public static TagNode findFirstTagNode(Node node){
		if(node instanceof TagNode) return (TagNode)node;
		Node pNode = node.getParent();
		if(pNode instanceof TagNode){
			return (TagNode)pNode;
		}else if(pNode != null){
			return findFirstTagNode(pNode);
		}else{
			return null;
		}
	}
	/**
	 * 
	 * @param node
	 * @param tag
	 * @return
	 */
	public static TagNode findNodeByTag(TagNode node,String tag){
		String _tag = String.valueOf(tag).toUpperCase();
		if(node.getTagName().equals(_tag) && !node.isEndTag())return node;
		else{
			NodeList nodes = node.getChildren();
			if(nodes != null){
				for(int i=0; i<nodes.size(); i++){
					Node cNode = nodes.elementAt(i);
					if(cNode instanceof TagNode){
						TagNode tagNode = findNodeByTag((TagNode)cNode,_tag);
						if(tagNode != null) return tagNode;
					}
				}
				return null;
			}else{
				return null;
			}
		}
	}
	
	static class TextVisitor extends NodeVisitor{
		
		String txt;
		int count;
		private boolean visitNext = true,visitSelf = true;
		
		@Override
		public void visitStringNode(Text string) {
			count++;
			String str = StringUtil.trim(string.getText());
			if(StringUtil.isNotEmpty(str)){
				txt = str;
				visitNext = false;
				visitSelf = false;
			}
		}

		@Override
		public boolean shouldRecurseChildren() {
			return visitNext;
		}

		@Override
		public boolean shouldRecurseSelf() {
			return visitSelf;
		}
		
	}
}
