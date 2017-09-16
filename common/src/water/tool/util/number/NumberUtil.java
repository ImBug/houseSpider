package water.tool.util.number;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {
	
	/**
	 * 
	 * @param value 原始值
	 * @param format 以运算符开始
	 * @return
	 */
	public static double calVal(String value,String format) throws Exception{
		double val = getNumberOfStr(value);
		if(format != null && format.trim().length() > 0){
			StringBuffer n = new StringBuffer();
			char oper = '0';
			String f = format + '+';
			for(int i=0; i<f.length(); i++){
				char c = f.charAt(i);
				if((c >='0' && c <= '9') || c == '.'){
					n.append(c);
				}else{
					switch (oper) {
					case '+': {
						val += Double.valueOf(n.toString());
						break;
					}
					case '-': {
						val -= Double.valueOf(n.toString());
						break;
					}
					case '*': {
						val *= Double.valueOf(n.toString());
						break;
					}
					case '/': {
						val /= Double.valueOf(n.toString());
						break;
					}
					default:
						break;
					}
					oper = c;
					n = new StringBuffer();
				}
				//遇到完整的数字做一次运算
			}
		}
		return val;
	}
	
	/**
	 * 提取字符串中的数字组在一起
	 * 最多一个小数点
	 * 不要处理过大的数
	 * @param str
	 * @return
	 */
	public static double getNumberOfStr(String str){
		if(str != null){
			String num = "";
			for(int i=0; i<str.length(); i++){
				char c = str.charAt(i);
				if((c >='0' && c <= '9') || c == '.'){
					num += c;
				}
			}
			if(num.length() > 0)return Double.valueOf(num);
		}
		return 0.0;
	}
	
	/**
	 * 遇到第一个end 符号结束
	 * @param str
	 * @param end
	 * @return
	 */
	public static String fetchNumberFrom(String str,char end){
		if(str != null){
			StringBuffer num = new StringBuffer();
			for(int i=0; i<str.length(); i++){
				char c = str.charAt(i);
				if(c == end && num.length() > 0){//如果没提取到数字也不受end约束
					break;
				}
				if(c >='0' && c <= '9'){
					num.append(c);
				}
			}
			return num.toString();
		}
		return null;
	}

	public static String fetchNumberFrom(String str){
		if(str != null){
			StringBuffer num = new StringBuffer();
			for(int i=0; i<str.length(); i++){
				char c = str.charAt(i);
				if(c >='0' && c <= '9'){
					num.append(c);
				}
			}
			return num.toString();
		}
		return null;
	}
	
	public static String fetchIpFrom(String str){
		if(str != null){
			StringBuffer num = new StringBuffer();
			for(int i=0; i<str.length(); i++){
				char c = str.charAt(i);
				if(num.length() > 0){//如果没提取到数字也不受end约束
					if(c == '.'){
						num.append(c);
						continue;
					}else if(c == ':' || c < '0' || c > '9'){
						break;
					}
				}
				if(c >='0' && c <= '9'){
					num.append(c);
				}
			}
			return num.toString();
		}
		return null;
	}

	/**
	 * 判断单个字符是否为数字
	 * @param c
	 * @return
	 */
	public static boolean isNumber(char c){
		return c >= '0' && c <= '9';
	}
	
	public static String[] fetchNumbersFrom(String str){
		if(str != null){
			List<String> list = new ArrayList<>();
			StringBuffer num = new StringBuffer();
			for(int i=0; i<str.length(); i++){
				char c = str.charAt(i);
				if(isNumber(c)){
					num.append(c);
				}else{
					if(num.length() > 0){
						list.add(num.toString());
						num.delete(0, num.length());
					}
				}
			}
			if(num.length() > 0){
				list.add(num.toString());
			}
			return list.toArray(new String[0]);
		}
		return new String[0];
	}


	/**
	 * 判断一串字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	}

	/**
	 * 去除int数组中重复的元素
	 * @param intArray
	 * @return
	 */
	public static int[] removeDuplicateElem(int[] intArray) {
		TreeSet<Integer> treeSet = new TreeSet<>();
		for (int elem : intArray) {
			treeSet.add(elem);
		}
		int[] noRepeatArray = treeSet.stream().mapToInt(Integer::intValue).toArray();
		return noRepeatArray;
	}
}
