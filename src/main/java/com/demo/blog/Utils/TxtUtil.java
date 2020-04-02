package com.demo.blog.Utils;


import java.io.*;

/**
 * Created by Nickwong on 31/07/2018.
 * 根据1-8楼的建议，优化了代码
 */
public class TxtUtil {

    public static void main(String args[]) {
    	
    	
    	
    	
    	
    	
//    	String str = "rqearefdsaqweweaeqw";
//    	char ar = 'a';
//    	boolean b =  lookindex(str,ar,3);
//    	System.out.println(b);
//        readFile();
//        writeFile();
    }

    /**
             * 读入TXT文件
     */
    public static void readFile(String pathname) {
        // 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入TXT文件
     */
    public static void writeFile() {
        try {
            File writeName = new File("output.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write("我会写入文件啦1\r\n"); // \r\n即为换行
                out.write("我会写入文件啦2\r\n"); // \r\n即为换行
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    //判断字符串str中ar字符是否出现第flagnum次
	public static boolean lookindex(String str,char ar,int flagnum) {
		int number = 0;
		char arr[] = str.toCharArray();
		for (int i = 0; i < arr.length; i++) {

			if (arr[i] == ar) {
				number++;
			}
			if (number == flagnum) {
				System.out.print("第"+flagnum+"次出现"+ar+"的位置是:" + (i + 1));
				return true;
			}
		}
		return false;

	}
}


