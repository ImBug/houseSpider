package water.htmlParser.entity;

import java.beans.Transient;

import water.tool.json.CustomObjectMapper;

/**
 * 
 * @author honghm
 *
 */
public abstract class PageEntity implements Comparable<PageEntity>{

	protected String fileName;
	
	@Transient
	public abstract boolean isNotEmpty();
	
	@Transient
	public String getFileIndex(){
		if(fileName != null && fileName.contains("_")){
			return fileName.substring(0, fileName.lastIndexOf("_"));
		}else{
			return null;
		}
	}
	
	public String toJson(){
		return CustomObjectMapper.encodeJson(this);
	}
	
	@Override
	public int compareTo(PageEntity o) {
		return fileName.compareTo(o.getFileName());
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
