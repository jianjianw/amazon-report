package com.nhsoft.module.report.comparator;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class HanYu {

	private HanyuPinyinOutputFormat format = null;

	private String[] pinyin;

	public HanYu()

	{
		format = new HanyuPinyinOutputFormat();

		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		pinyin = null;

	}

	// 转换单个字符

	public String getCharacterPinYin(char c)

	{
		try {
			pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
		}

		catch (BadHanyuPinyinOutputFormatCombination e)

		{
			e.printStackTrace();

		}

		// 如果c不是汉字，toHanyuPinyinStringArray会返回null

		if (pinyin == null)
			return null;

		// 只取一个发音，如果是多音字，仅取第一个发音

		return pinyin[0];

	}

	// 转换一个字符串

	public String getStringPinYin(String str)

	{
		if(str == null){
			return "";
		}
		StringBuilder sb = new StringBuilder();

		String tempPinyin = null;

		for (int i = 0; i < str.length(); ++i)

		{
			tempPinyin = getCharacterPinYin(str.charAt(i));

			if (tempPinyin == null) {
				// 如果str.charAt(i)非汉字，则保持原样
				sb.append(str.charAt(i));
			}

			else

			{
				sb.append(tempPinyin);

			}

		}

		return sb.toString();

	}
	
	// 转换一个字符串取第一个字母（速记码）
	
	public String getStringPinYinShort(String str)
	
	{
		if(str == null){
			str = "";
		}
		StringBuilder sb = new StringBuilder();
		
		String tempPinyin = null;
		
		for (int i = 0; i < str.length(); ++i)
			
		{
			tempPinyin = getCharacterPinYin(str.charAt(i));
			
			if (tempPinyin == null || tempPinyin.trim().equals("")) {
				// 如果str.charAt(i)非汉字，则保持原样
				char ch = str.charAt(i);
				if (ch >= 'a' && ch <= 'z'){
					ch = (char) (ch - 'a' + 'A');
					sb.append(ch);
				}else if (ch >= 'A' && ch <= 'Z') {
					sb.append(ch);
				}else if(ch >= '0' && ch <= '9'){
		        	sb.append(ch);
		        }
			}
			
			else
				
			{
				String value = tempPinyin.substring(0, 1).toUpperCase();
				sb.append(value);
				
			}
			
		}
		
		return sb.toString();
		
	}

    public static void main(String[] args) {
    	HanYu hanYu = new HanYu();
        System.out.println(hanYu.getStringPinYinShort("caomei"));
//        System.out.println(cn2py(",.[，。【"));
    }
}
