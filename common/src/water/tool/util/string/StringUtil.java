package water.tool.util.string;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *   汉字：[0x4e00,0x9fa5]（或十进制[19968,40869]）
            数字：[0x30,0x39]（或十进制[48, 57]）
           小写字母：[0x61,0x7a]（或十进制[97, 122]）
           大写字母：[0x41,0x5a]（或十进制[65, 90]）
 * @author honghm
 *
 */
public class StringUtil {
	
	public final static int[] CHAR_CN = {19968,40869};
	public final static int[] CHAR_NO = {48,57};
	public final static int[] CHAR_LW = {97,122};
	public final static int[] CHAR_UP = {65,90};
	public final static int[] CHAR_AL = {33,126};
	public final static String UNDER_LINE = "_";
  
	/**
	 * 是否单字节
	 * @param c
	 * @return
	 */
	public static boolean isByteSingle(byte c){
		if((c+1) > CHAR_AL[0])return true;
		if((c-1) < CHAR_AL[1])return true;
		return false;
	}
	
	public static boolean isNotEmpty(String str){
		return StringUtils.isNotEmpty(str);
	}
	
  public static String trim(String str){
  	if(str != null){
  		String txt = StringUtils.replaceAll(str, "\t|\r|\n", " ").trim();
  		txt = txt.replaceAll("\\s{1,}", " ");
  		return  txt;
  	}
  	return null;
  } 
	/**
	 * 是否包含中文字符
	 * @param string
	 * @return
	 */
	public static boolean hasCnChar(String string){
		if(!StringUtils.isEmpty(string)){
			char[] chars = string.toCharArray();
			for(char c:chars){
				int v = Integer.valueOf(c);
				if((v+1) > CHAR_CN[0] && (v-1) < CHAR_CN[1]) return true;
			}
		}
		return false;
	}

	/**
	 * 粗略判断是否存在oid,后续进行优化
	 * @param str
	 * @return
	 */
	public static boolean isExistOid(String str) {
		String[] strs = str.split(UNDER_LINE);
		for (String s : strs) {
			String oid = fetchOid(s);
			if(StringUtil.isNotEmpty(oid) && oid.length() > 10)
				return true;
		}
		return false;
	}

	public static String findOid(String str) {
		String[] strs = str.split(UNDER_LINE);
		for(String s : strs) {
			String oid = fetchOid(s);
			if(StringUtil.isNotEmpty(oid) && oid.length() > 10)
				return oid;
		}
		return null;
	}
	
	/**
	 * 
	 * @param oidString
	 * @return
	 */
	public static String fetchOid(String oidString){
		if(!StringUtils.isEmpty(oidString)){
			int index = oidString.indexOf("1.3.6.1");
			if(index > -1){
				char[] chars = oidString.toCharArray();
				StringBuffer oid = new StringBuffer();
				for(int i = index; i<chars.length; i++){
					char c = chars[i];
					if(c == '.'){
						oid.append(c);
					}else{
						int v = Integer.valueOf(c);
						if((v+1) > CHAR_NO[0] && (v-1) < CHAR_NO[1]){
							oid.append(c);
						}else{
							break;
						}
					}
				}
				return oid.toString();
			}
		}
		return null;
	}
	
	public static String unEscapeHtml(String htmlTxt){
		return StringEscapeUtils.unescapeHtml(htmlTxt);
	}
	
	public static String escapeHtml(String string) {
		return StringEscapeUtils.escapeHtml(string);
	}
	
	/*
	 * 计算相似程度
	 */
	public static int calculateStringDistance(String strA,String strB){
		int lenA = (int)strA.length();
		int lenB = (int)strB.length();
		int[][] c = new int[lenA+1][lenB+1];
		for(int i = 0; i < lenA; i++) {
			c[i][lenB] = lenA - i;
		}
		for(int j = 0; j < lenB; j++) c[lenA][j] = lenB - j;
		c[lenA][lenB] = 0;
		
		for(int i = lenA-1; i >= 0; i--)
      for(int j = lenB-1; j >= 0; j--)
      {
          if(strB.charAt(j)   == strA.charAt(i))
              c[i][j] = c[i+1][j+1];
          else
              c[i][j] = Math.min(Math.min(c[i][j+1], c[i+1][j]),c[i+1][j+1]) + 1;
      }

		 return c[0][0];
	}
	
	/**
	 * 取闭包内的字符串
	 * @param string
	 * @param start
	 * @param end
	 * @return
	 */
	public static String fetchFirstClosure(String string,char start,char end){
		if(isNotEmpty(string)){
			int startIndex = string.indexOf(start);
			int endIndex = string.indexOf(end);
			if(endIndex > startIndex && startIndex > -1){
				return string.substring(startIndex+1, endIndex);
			}
		}
		return null;
	}
	
}
