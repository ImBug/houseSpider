package water.htmlParser.entity;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.NodeVisitor;

import water.htmlParser.util.HtmlUtil;
import water.tool.json.CustomObjectMapper;
import water.tool.util.string.StringUtil;

/**
 * 递进选择
 * @author honghm
 *
 */
public class Selector {
	
	/*没符号代表tagName*/
	//#id
	public final static char ID = '#';
	//.class
	public final static char CLASS = '.';
	//:txt  == contains(txt)
	public final static char COMPAR = ':';
	//:eq(txt) == .equls(txt) 
	public final static String COMPAR_EQ = "eq";
	//:nt(txt) == !.equls(txt) 
	//public final static String COMPAR_NT = "nt";
	//[attr1=value1,attr2=value2]
	public final static char ATTR = '[';
	//空格占位
	public final static char SPACE = '^';
	
	public static void main(String[] args) {
		String xx = ".clsName^xxx";
		TagCond cond = parse(xx);
		System.out.println(cond);
		//System.out.println(cond);
		//System.out.println(cond.getParent());
		//System.out.println(cond.getParent().getParent());
		//System.out.println(cond.getParent().getParent().getParent());
	}
	
	private static TagNode SelectTagNode(TagNode target,TagCond cond){
		if(cond.match(target))return target;
		NodeList nodes = target.getChildren();
		if(nodes != null){
			for(int i=0; i<nodes.size(); i++){
				Node node = nodes.elementAt(i);
				if(node instanceof TagNode){
					TagNode tagNode = SelectTagNode((TagNode)node,cond);
					if(tagNode != null)return tagNode;
				}
			}
		}
		return null;
	}
	
	/**
	 * 采用递归实现
	 * @param target
	 * @param cond
	 * @return
	 */
	private static List<TagNode> SelectTagNodes(TagNode target,TagCond cond){
		List<TagNode> targets = new ArrayList<>(5);
		if(cond.match(target)){
			targets.add(target);
			return targets;
		}
		NodeList nodes = target.getChildren();
		if(nodes != null){
			for(int i=0; i<nodes.size(); i++){
				Node node = nodes.elementAt(i);
				if(node instanceof TagNode){
					targets.addAll(SelectTagNodes((TagNode)node,cond));
				}
			}
		}
		return targets;
	}
	
	public static List<TagNode> select(TagNode target,String condition){
		return SelectTagNodes(target,parse(condition));
//		if(target != null && StringUtil.isNotEmpty(condition)){
//			TagVisitor visitor = new TagVisitor(parse(condition));
//			target.accept(visitor);
//			return visitor.tagNodes;
//		}
//		return Collections.EMPTY_LIST;
	}
	
	public static TagNode selectTagNode(TagNode target,String condition){
		if(target != null && StringUtil.isNotEmpty(condition)){
			return SelectTagNode(target,parse(condition));
//			TagVisitor visitor = new TagVisitor(parse(condition));
//			visitor.visitAll = false;
//			target.accept(visitor);
//			if(visitor.tagNodes.size() > 0) return visitor.tagNodes.get(0);
		}
		return null;
	}
	
	private static TagCond parse(String selector){
		if(StringUtil.isNotEmpty(selector)){
			String[] condtions = selector.split(" ");
			TagCond parent = null;
			TagCond tgcond = null;
			for(String cond:condtions){
				tgcond = new TagCond(cond);
				if(parent != null)tgcond.parent = parent;
				parent = tgcond;
			}
			return tgcond;
		}
		return null;
	} 
	
	static class TagVisitor extends NodeVisitor{
		
		TagCond cond;
		boolean visitAll = true;
		List<TagNode> tagNodes = new ArrayList<>(5);
		private boolean visitNext = true;
		
		public TagVisitor(TagCond cond) {
			super();
			this.cond = cond;
		}

		@Override
		public void visitTag(Tag tag) {
			TagNode node = (TagNode)tag;
			if(cond.match(node)){
				tagNodes.add(node);
				if(!visitAll)visitNext=false;
			}
		}

		@Override
		public boolean shouldRecurseChildren() {
			return visitNext;
		}

		@Override
		public boolean shouldRecurseSelf() {
			return true;
		}
		
	}

	protected static class TagCond{
		TagCond parent;
		List<String[]> attrList;
		String tagName;
		String outerTxt;
		boolean eq = false;
		
		public TagCond(String cond) {
			compile(cond);
		}
		
		/**
		 * 从叶子向父节点递进
		 * @param tag
		 * @return
		 */
		public boolean match(TagNode tag){
			if(tagName != null){
				if(!tagName.equalsIgnoreCase(tag.getTagName()))return false;
			}
			if(outerTxt != null){
				String txt = HtmlUtil.getTextOfTag(tag);
				if(!StringUtil.isNotEmpty(txt))return false;
				if(eq && !outerTxt.equals(txt))return false;
				if(!txt.contains(outerTxt))return false;
			}
			if(attrList != null){
				long count = attrList.stream().filter(attr ->{
					String value = StringUtil.trim(tag.getAttribute(attr[0]));
					if(value != null){
						for(String part:value.split(" ")){
							if(attr[1].indexOf(part) < 0) return false;
						}
						return true;
					}
					return false;
				}).count();
				if(attrList.size() > count) return false;
			}
			if(parent != null){
				if(tag.getParent() != null){
					return parent.match((TagNode)tag.getParent());
				}else{
					return false;
				}
			}
			return true;
		}
		
		@Override
		public String toString() {
			return CustomObjectMapper.encodeJson(this);
		}

		/**
		 * 这里算法不严谨
		 * TODO 完善
		 * 目前只处理严格顺序：# or .[]:
		 * @param cond
		 */
		private void compile(String cond){
			if(!StringUtil.isNotEmpty(cond)) throw new IllegalArgumentException("TagCond.cond is null");
			char c = cond.charAt(0);
			if(ID == c){
				attrList = new ArrayList<>(1);
				attrList.add(new String[]{"id",cond.substring(1)});
				return;
			}
			if(CLASS == c){//.cls[a1=v1,a2=v2]:eq()
				String className = StringUtil.fetchFirstClosure(cond, CLASS, ATTR);
				if(className == null){
					className = StringUtil.fetchFirstClosure(cond, CLASS, COMPAR);
					if(className != null){
						attrList = new ArrayList<>(1);
						attrList.add(new String[]{"class",className});
						int index = cond.indexOf(COMPAR);
						if(index > -1){
							outerTxt = StringUtil.fetchFirstClosure(cond, '(', ')');
							if(outerTxt == null){
								outerTxt = cond.substring(index+1);
							}else{
								eq = true;
							}
						}
					}else{
						attrList = new ArrayList<>(1);
						attrList.add(new String[]{"class",cond.substring(1)});
					}
					return;
				}else{
					attrList = new ArrayList<>(5);
					attrList.add(new String[]{"class",className});
					String attrs = StringUtil.fetchFirstClosure(cond, ATTR, ']');
					for(String at:attrs.split(",")){
						String[] atStrings = at.split("=");
						attrList.add(new String[]{atStrings[0],atStrings[1]});
					}
					int index = cond.indexOf(COMPAR);
					if(index > -1){
						outerTxt = StringUtil.fetchFirstClosure(cond, '(', ')');
						if(outerTxt == null){
							outerTxt = cond.substring(index+1);
						}else{
							eq = true;
						}
					}
				}
				return ;
			}
			if(ATTR == c){//[a1=v1,a2=v2]:eq()
				attrList = new ArrayList<>(5);
				String attrs = StringUtil.fetchFirstClosure(cond, ATTR, ']');
				for(String at:attrs.split(",")){
					String[] atStrings = at.split("=");
					attrList.add(new String[]{atStrings[0],atStrings[1]});
				}
				int index = cond.indexOf(COMPAR);
				if(index > -1){
					outerTxt = StringUtil.fetchFirstClosure(cond, '(', ')');
					if(outerTxt == null){
						outerTxt = cond.substring(index+1);
					}else{
						eq = true;
					}
				}
				return;
			}
			for(int index=0; index<cond.length();index++){
				c = cond.charAt(index);
				if(ID == c ||CLASS == c || ATTR == c){
					tagName = cond.substring(0, index);
					compile(cond.substring(index));
					return;
				}
				if(COMPAR == c){
					outerTxt = StringUtil.fetchFirstClosure(cond, '(', ')');
					if(outerTxt == null){
						outerTxt = cond.substring(index+1);
					}else{
						eq = true;
					}
					tagName = cond.substring(0, index);
					return;
				}
			}
			tagName = cond;
		}

		public TagCond getParent() {
			return parent;
		}

		public void setParent(TagCond parent) {
			this.parent = parent;
		}

		public List<String[]> getAttrList() {
			return attrList;
		}

		public void setAttrList(List<String[]> attrList) {
			this.attrList = attrList;
		}

		public String getTagName() {
			return tagName;
		}

		public void setTagName(String tagName) {
			this.tagName = tagName;
		}

		public String getOuterTxt() {
			return outerTxt;
		}

		public void setOuterTxt(String outerTxt) {
			this.outerTxt = outerTxt;
		}

		public boolean isEq() {
			return eq;
		}

		public void setEq(boolean eq) {
			this.eq = eq;
		}
		
	}
}
