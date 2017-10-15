package water.htmlParser.entity;

import org.htmlparser.Tag;
import org.htmlparser.visitors.NodeVisitor;

/**
 * 
 * @author honghm
 *
 */
public abstract class TagVisitor extends NodeVisitor {

	public abstract void visit(Tag tag);

	@Override
	public void visitTag(Tag tag) {
		visit(tag);
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
