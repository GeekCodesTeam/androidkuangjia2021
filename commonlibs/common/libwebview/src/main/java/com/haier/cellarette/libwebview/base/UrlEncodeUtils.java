package com.haier.cellarette.libwebview.base;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UrlEncodeUtils {
	/**
	 * 编码url的文件名称
	 * @param path
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String encodeUrl(String path) throws UnsupportedEncodingException{
		// url允许的字符
		String allow = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_.~!*'();:@&=+$,/?#[]";
		// 创建列表, 存储中文字符的位置
		List<Integer> position = new ArrayList<Integer>();
		// 遍历url中的每个字符,找出中文字符
		for (int i = 0; i < path.length(); i++) {
			// 当前字符
			char current = path.charAt(i);
			String currentString = String.valueOf(current);
			// 当前字符不在允许字符里
			if (!allow.contains(currentString)) {
				// 记录中文字符的位置
				position.add(i);
			}
		}
		// 根据记录的位置, 存储需要编码的字符列表, 使用集合去掉重复的字符
		Set<String> replaceSet = new HashSet<String>();
		// 遍历中文字符的位置列表
		for (int i = 0; i < position.size(); i++) {
			// 取得需要替换的字符
			String cuStr = String.valueOf(path.charAt(position.get(i)));
			// 把需要替换的中文字符, 加入
			replaceSet.add(cuStr);
		}
		// 遍历需要替换的字符集合
		for (String word:replaceSet) {
			// 把需要替换的字符, 进行编码, 替换
			path = path.replace(word, URLEncoder.encode(word, "UTF-8"));
		}
		return path;
	}
}