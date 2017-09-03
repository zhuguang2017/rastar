package com.myCode.comm;

public class Text {

	static int MaxValue=0x7777777f;
	static int chazhi=48;
	
	public static int valueof(String s){
		int result = 0;
		boolean flag=true;
		int len=s.length();
		char[] strliu=s.toCharArray();
		int[] a = new int[len];
		int jinzhiwei=1;
		int i=0;
		for (char c : strliu) {
			if(c>48&&c<57){
				a[i]=(c-48);
				i++;
			}else{
				flag=false;
			}
		}
		if(flag){
			for (int j = 0; j < a.length; j++) {
				int swap=a.length-j-1;
				int swaptmp=1;
				while(swap>0){
					swaptmp=swaptmp*10;
					swap--;
				}
//				result=a[j]*10^(a.length-j)+result;
				result=result+swaptmp*a[j];
			}
		}
		
		return result;
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.err.println(valueof("666"));
//		System.err.println(valueof("0"));
//		System.err.println(valueof("12345678r"));
//		System.err.println(valueof("123456789"));
//		System.err.println(valueof("-123456789"));
//		System.err.println(valueof("90-293"));
//		System.err.println(valueof("散散心"));
	}

}
