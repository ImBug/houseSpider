package water.htmlParser.entity;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.visitors.NodeVisitor;

import water.tool.util.string.StringUtil;


/**
 * 一次性构建Text树.
 * @author honghm
 *
 */
public class TextTree{
	
	private List<TextNode> datas;
	private Node root;
	
	public TextTree(TagNode root){
		super();
		datas = new ArrayList<>();
		this.root = root;
		root.accept(new Visitor());
	}
	
	
	public TextTree(TagNode root,int size,int deep){
		super();
		datas = new ArrayList<>(size);
		this.root = root;
		root.accept(new Visitor());
	}
	
	public String toHtml(){
		return root.toHtml();
	}
	
	private boolean isLineTag(String tagName){
		return "LI|DIV|P".indexOf(tagName.toUpperCase()) > -1;
	}
	
	/**
	 * 是否需要合并到前面一段
	 * @param prNode
	 * @param node
	 * @return
	 */
	private boolean isNeedMerg(TextNode prNode,TextNode node){
		if(!isLineTag(node.getTagName())){
			return true;
//			if(node.getDeep() > prNode.getDeep()){
//			}
		}
		return false;
	}
	
	/** 有时间来完善这一段代码 采用html格式
	public String formatText(){
		TextNode preNode= null;
		Set<Integer> depset = new HashSet<>(5);
		for(TextNode node:datas){//获取深度总数
			depset.add(node.getDeep());
		}
		List<Integer> depList = depset.stream().collect(Collectors.toList()).stream().sorted().collect(Collectors.toList());
		for(TextNode node:datas){//获取深度总数
			if(preNode != null){
				if(node.getDeep() > preNode.getDeep()){
					if(isNeedMerg(preNode,node)){
						node.setPreTb(-1);
					}else{
						node.setPreTb(preNode.getPreTb() + 1);
					}
				}else{
					for(int i=0; i<depList.size(); i++){
						if(depList.get(i) == node.getDeep()){
							node.setPreTb(i);
							break;
						}
					}
				}
			}else{
				node.setPreTb(0);
			}
			preNode = node;
		}
		StringBuffer stringBuffer = new StringBuffer();
		for(TextNode node:datas){
			if(node.getPreTb() > -1){
				for(int i=0; i<node.getPreTb();i++){
					stringBuffer.append("*");
				}
				stringBuffer.append(node.getText()).append("\n");
			}else{
				stringBuffer.append(node.getText());
			}
		}
		return stringBuffer.toString();
	}
	**/
	
	public String getText(){
		StringBuffer txt = new StringBuffer();
		for(TextNode node:datas){
			txt.append(StringUtil.trim(node.getText())).append(";");
		}
		if(txt.length() > 0 )txt.deleteCharAt(txt.length() - 1);
		return txt.toString();
	}
	
	public List<TextNode> getDatas() {
		return datas;
	}

	public Node getRoot() {
		return root;
	}

	class Visitor extends NodeVisitor{
		
		@Override
		public void visitStringNode(Text textNode) {
			String txt = StringUtil.trim(textNode.getText());
			if(StringUtil.isNotEmpty(txt)){
				TextNode node = new TextNode(txt);
				node.setRoot(root);
				node.setDeep(calDepth(textNode));
				datas.add(node);
				Node pNode = textNode.getParent();
				if(pNode instanceof Tag){
					node.setTagName(((Tag)pNode).getTagName());
				}
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
	
	private int calDepth(Node node){
		if(node == root){
			return 0;
		}else{
			int depth = 0;
			Node cNode = node;
			while(true){
				Node pNode = cNode.getParent();
				if(pNode == root){
					return ++depth;
				}else{
					cNode = pNode;
					if(depth > 20)return -1;
					else{
						depth++;
					}
				}
			}
		}
	}
	
	
}
