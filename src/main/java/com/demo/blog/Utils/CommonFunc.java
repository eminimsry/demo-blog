package com.demo.blog.Utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.sf.json.JSONObject;

public class CommonFunc {
	public static byte[] compressImage=null;
    /** 
     * stringToAscii      *       
     * @paramst      *
     * @return      */ 
	public static String stringToAscii(String value)  
	{  
	    StringBuffer sbu = new StringBuffer();  
	    char[] chars = value.toCharArray();   
	    for (int i = 0; i < chars.length; i++) {  
	        if(i != chars.length - 1)  
	        {  
	            sbu.append((int)chars[i]).append(",");  
	        }  
	        else {  
	            sbu.append((int)chars[i]);  
	        }  
	    }  
	    return sbu.toString();  
	}  

    /** 
     * asciiToString      *       
     * @paramst      *
     * @return      */ 
	public static String asciiToString(String value)
	{
		StringBuffer sbu = new StringBuffer();
		String[] chars = value.split(",");
		for (int i = 0; i < chars.length; i++) {
			sbu.append((char) Integer.parseInt(chars[i]));
		}
		return sbu.toString();
	}
    public static boolean ShowInterfaces() throws Exception
    {
    	boolean NetworkState=false;
		Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
		for (; n.hasMoreElements();) {
			NetworkInterface e = n.nextElement();
			//System.out.println("Interface: " + e.getName());
			if (e.getName().contains("eth") || e.getName().contains("wlan")) {
				Enumeration<InetAddress> a = e.getInetAddresses();
				for (; a.hasMoreElements();) {
					InetAddress addr = a.nextElement();
					//System.out.println("  " + addr.getHostAddress());
					if(addr.getHostAddress().startsWith("10")||addr.getHostAddress().startsWith("172")||addr.getHostAddress().startsWith("192")){
					//if (!addr.getHostAddress().equals(addr.getHostName())) {
						NetworkState = true;
						break;
					}
				}
			}
		}
		if (NetworkState) {
			return true;
		} else {
			return false;
		}
     }
    
    public static String getRaspberryIP() throws Exception{
    	String ip="";
		Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
		for (; n.hasMoreElements();) {
			NetworkInterface e = n.nextElement();
			//System.out.println("Interface: " + e.getName());
			if (e.getName().contains("eth") || e.getName().contains("wlan")) {
				Enumeration<InetAddress> a = e.getInetAddresses();
				for (; a.hasMoreElements();) {
					InetAddress addr = a.nextElement();
					//System.out.println("  " + addr.getHostAddress());
					if(addr.getHostAddress().startsWith("10")||addr.getHostAddress().startsWith("172")||addr.getHostAddress().startsWith("192")){
					//if (!addr.getHostAddress().equals(addr.getHostName())) {
						ip=addr.getHostAddress();
						break;
					}
				}
			}
		}
		return ip;
    }
	 /*
     * get the local host IP 
     */
     public static String getLocalHostIP()
     {
         try
         {
             InetAddress addr=InetAddress.getLocalHost();
             return addr.getHostAddress();
         }
         catch(UnknownHostException e)
         {
             return "";
         }
     }
 
     /*
     * get the local host name
     */
     public static String getLocalHostName()
     {
         try
         {
             InetAddress addr=InetAddress.getLocalHost();
             return addr.getHostName();
         }
         catch(UnknownHostException e)
         {
             return "";
         }
     }    
     
     /**
  	* @MethodName: getMacAddress 
  	* @description :获取MAC地址
  	* @return String
  	*/
  	public static String getMacAddress(){
  		String os = getOSName();
  		String execStr = getSystemRoot()+"/system32/ipconfig /all";
  		String mac = null;
  		if(os.startsWith("windows")){
  			if(os.equals("windows xp")){//XP
  				mac = getWindowXPMACAddress(execStr);  
  			}else if(os.equals("windows 2003")){//2003
  				mac = getWindowXPMACAddress(execStr);   
  			}else{//win7
  				mac = getWindow7MACAddress(); 
  			}
  		}else if (os.startsWith("linux")) {
  			mac = getLinuxMACAddress();
  		}else{
  			mac = getUnixMACAddress();
  		}
  		return mac;
  	}
     
 	/**
 	* @MethodName: getOSName 
 	* @description : 获取当前操作系统名称. return 操作系统名称 例如:windows,Linux,Unix等
 	* @return String
 	*/
 	public static String getOSName() {
 		return System.getProperty("os.name").toLowerCase();
 	}
 	
 	/**
	* @MethodName: getSystemRoot 
	* @description :jdk1.4获取系统命令路径 ：SystemRoot=C:\WINDOWS
	* @return String
	*/
	public static String getSystemRoot(){
		String cmd = null;
		String os = null;
		String result = null;
		String envName = "windir";
		os = System.getProperty("os.name").toLowerCase();
		if(os.startsWith("windows")) {
			cmd = "cmd /c SET";
		}else {
			cmd = "env";
		}
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			InputStreamReader isr = new InputStreamReader(p.getInputStream());
			BufferedReader commandResult = new BufferedReader(isr);
			String line = null;
			while ((line = commandResult.readLine()) != null) {
				line=line.toLowerCase();//重要(同一操作系统不同电脑有些返回的是小写,有些是大写.因此需要统一转换成小写)
				if (line.indexOf(envName) > -1) {
					result =  line.substring(line.indexOf(envName)
							+ envName.length() + 1);
					return result;
				}
			}
		} catch (Exception e) {
			System.out.println("获取系统命令路径 error: " + cmd + ":" + e);
		}
		return null;
	}
 	
	/**
	* @MethodName: getWindowXPMACAddress 
	* @description : 获取widnowXp网卡的MAC地址
	* @return String
	*/
	public static String getWindowXPMACAddress(String execStr) {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			// windows下的命令，显示信息中包含有MAC地址信息
			process = Runtime.getRuntime().exec(execStr);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				if(line.indexOf("本地连接") != -1)     //排除有虚拟网卡的情况
					continue;
				
				// 寻找标示字符串[physical address]
				index = line.toLowerCase().indexOf("physical address");
				if (index != -1) {
					index = line.indexOf(":");
					if (index != -1) {
						//取出MAC地址并去除2边空格
						mac = line.substring(index + 1).trim();
					}
					break;
				}	
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}
		return mac;
	}
	
	/**
	* @MethodName: getWindow7MACAddress 
	* @description : 获取widnow7网卡的MAC地址
	* @return String
	*/
	public static String getWindow7MACAddress() {
		//获取本地IP对象
		InetAddress ia = null;
		try {
			ia = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		//获得网络接口对象（即网卡），并得到MAC地址，MAC地址存在于一个byte数组中。
        byte[] mac = null;
		try {
			mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		} catch (SocketException e) {
			e.printStackTrace();
		}

        if(mac!=null)
        {
            //下面代码是把MAC地址拼装成String
            StringBuffer sb = new StringBuffer(); 
			for (int i = 0; i < mac.length; i++) {
				if (i != 0) {
					sb.append("-");
				}
				// MAC[i] & 0xFF 是为了把byte转化为正整数
				String s = Integer.toHexString(mac[i] & 0xFF);
				sb.append(s.length() == 1 ? 0 + s : s);
			}
			// 把字符串所有小写字母改为大写成为正规的MAC地址并返回
			return sb.toString().toUpperCase();
        }
        else
        {
        	String macaddress=null;
  			List<String> list = getAllMac();
  	        for (String address : list) {
  	        	return address.toUpperCase();
  	        }
  	        return macaddress;
        }

	}
	
	/**
	* @MethodName: getLinuxMACAddress 
	* @description : 获取Linux网卡的MAC地址
	* @return String
	*/
	public static String getLinuxMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			// LINUX下的命令，一般取eth0作为本地主网卡 显示信息中包含有MAC地址信息
			process = Runtime.getRuntime().exec("ifconfig eth0");
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				index = line.toLowerCase().indexOf("硬件地址");
				if (index != -1) {
					// 取出MAC地址并去除2边空格
					mac = line.substring(index + 4).trim();
					break;
				}
				// 寻找标示字符串[HWADDR]
				index = line.toLowerCase().indexOf("hwaddr");
				if (index != -1) {
					// 取出MAC地址并去除2边空格
					mac = line.substring(index + "hwaddr".length() + 1).trim();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}
		return mac;
	}
	
	/**
	* @MethodName: getUnixMACAddress 
	* @description : 获取Unix网卡的MAC地址
	* @return String
	*/
	public static String getUnixMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			// Unix下的命令，一般取eth0作为本地主网卡 显示信息中包含有MAC地址信息
			process = Runtime.getRuntime().exec("ifconfig eth0");
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				// 寻找标示字符串[HWADDR]
				index = line.toLowerCase().indexOf("hwaddr");
				if (index != -1) {
					// 取出MAC地址并去除2边空格
					mac = line.substring(index + "hwaddr".length() + 1).trim();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}
	
	public static HashMap<String, String> JsonToHashMap(String JsonStrin){	
		HashMap<String, String> data = new HashMap<String, String>();  
		try{
		    // 将json字符串转换成jsonObject  
		    JSONObject jsonObject = JSONObject.fromObject(JsonStrin);  
		    @SuppressWarnings("rawtypes")
			Iterator it = jsonObject.keys();
		    // 遍历jsonObject数据，添加到Map对象  
		    while (it.hasNext())  
		    {
		    	String key = String.valueOf(it.next()).toString();  
		        String value = (String) jsonObject.get(key).toString();    
		        data.put(key, value);  
		    }  
		}catch (Exception e) {
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null,"ERROR:["+e+"]");
		}
		return data; 	
	}
	public static HashMap<String, Object> JsonToMap(String JsonStrin){	
		HashMap<String, Object> data = new HashMap<String, Object>();  
		try{
		    // 将json字符串转换成jsonObject  
		    JSONObject jsonObject = JSONObject.fromObject(JsonStrin);  
		    @SuppressWarnings("rawtypes")
			Iterator it = jsonObject.keys();
		    // 遍历jsonObject数据，添加到Map对象  
		    while (it.hasNext())  
		    {
		    	String key = String.valueOf(it.next()).toString();  
		        Object value = (Object) jsonObject.get(key).toString();    
		        data.put(key, value);  
		    }  
		}catch (Exception e) {
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null,"ERROR:["+e+"]");
		}
		return data; 	
	}
	
	/**
     * 功能：Java读取txt文件的内容
     * @param filePath    System.getProperty("file.separator") 路徑分割符 linux下要轉\\為/
     * @return 
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes", "resource" })
	public static ArrayList readTxtFile_GetArry(String filePath)
            throws Exception
    {
        String encoding="GBK";
        ArrayList arryList=new ArrayList();
        File file=new File(filePath);
        if(file.isFile() && file.exists())
        {  
            try (InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding)) 
            {
                BufferedReader bufferedReader = new BufferedReader(read);
                String r=bufferedReader.readLine();
                while(r != null)
                {
                    arryList.add(r);
                    r=bufferedReader.readLine();
                }
            }
        }
        return arryList;
    }    
    
     @SuppressWarnings("resource")
	public static String readTxtFile_GetStr(String filePath)
            throws Exception
    {
        String encoding="UTF-8";
        String LabelString="";
        File file=new File(filePath);
        if(file.isFile() && file.exists())
        {  
            try (InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding)) 
            {
                BufferedReader bufferedReader = new BufferedReader(read);
                String r=bufferedReader.readLine();
                while(r != null)
                {
                    LabelString=LabelString+r;
                    r=bufferedReader.readLine();
                }
            }
        }
        return LabelString;
    }   
     
    
    public static void writeerrid(String text, String charset)
            throws IOException 
    {
        String StrName="ERRID.txt";
        String StrFolder=System.getProperty("user.dir")+ System.getProperty("file.separator") +"ERRID";
    	File file =new File(StrFolder);
    	if(!file.exists() || !file.isDirectory()){//判斷文件夾是否存在
    		file.mkdir();    
    	}
    	String strPath=StrFolder+System.getProperty("file.separator") +StrName;
    	file =new File(strPath);
    	if(file.exists()){
    		file.delete();
    	} 
    	file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file,true);
        OutputStreamWriter writer = null;
        try
        {
            if (null != charset) 
            {
                writer = new OutputStreamWriter(fos, charset);
            }
            else
            {
                writer = new OutputStreamWriter(fos);
            }
//            Date date1=new Date();
//			DateFormat format1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String time1=format1.format(date1); 
//            writer.write(time1+":"+text+"\r\n");    
			writer.write(text);  
        }
        finally
        {
            if (null != writer) 
            {
                writer.flush();
                writer.close();	
            }
        }
    }
    
    
    /*** 
    * @param path 文件绝对路径 如 c:\test.txt，若文件存在，则覆盖内容  System.getProperty("file.separator") 路徑分割符
    * @param text 要写入文件的内容
    * @param charset 字符集，若为null,则采用平台默认字符集
    * @throws IOException
    */
    public static void writeLog(String path, String text, String charset)
            throws IOException 
    {
    	File file =new File(path);
    	if(!file.exists()){
    		file.createNewFile();
    	} 
        FileOutputStream fos = new FileOutputStream(file,true);
        OutputStreamWriter writer = null;
        try
        {
            if (null != charset) 
            {
                writer = new OutputStreamWriter(fos, charset);
            }
            else
            {
                writer = new OutputStreamWriter(fos);
            }
            writer.append(text);
        }
        finally
        {
            if (null != writer) 
            {
                writer.flush();
                writer.close();	
            }
        }
    }
    
    
    public static String get2DtoIsn(String ISNRule,String str2DIsn){
    	if(ISNRule.startsWith("[")&&ISNRule.endsWith("]")){
    		ISNRule=ISNRule.substring(1,ISNRule.length()-1);
    	}
    	
		String[] strr=ISNRule.split(",");
		String strLength=strr[2].toString();
		String[] str2Dd=str2DIsn.split(":");
		if(str2Dd.length>=Integer.parseInt(strLength)){
			return str2Dd[Integer.parseInt(strLength)-1].toString();
		}else{
			return "ERROR";
		}
    }
    public static List<String> getAllMac() {
		List<String> list = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> e = NetworkInterface
					.getNetworkInterfaces();// 返回所有网络接口的一个枚举实例
			while (e.hasMoreElements()) {
				NetworkInterface network = e.nextElement();// 获得当前网络接口
				if (network != null) {
					if (network.getHardwareAddress() != null) {
						// 获得MAC地址
						// 结果是一个byte数组，每项是一个byte，我们需要通过parseByte方法转换成常见的十六进制表示
						byte[] addres = network.getHardwareAddress();
						StringBuffer sb = new StringBuffer();
						if (addres != null && addres.length > 1) {
							sb.append(parseByte(addres[0])).append("-")
									.append(parseByte(addres[1])).append("-")
									.append(parseByte(addres[2])).append("-")
									.append(parseByte(addres[3])).append("-")
									.append(parseByte(addres[4])).append("-")
									.append(parseByte(addres[5]));
							if(!sb.toString().equals("0-0-0-0-0-0")){
							  list.add(sb.toString());
							}
						}
					}
				} else {
					System.out.println("获取MAC地址发生异常");
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 格式化二进制
	private static String parseByte(byte b) {
		int intValue = 0;
		if (b >= 0) {
			intValue = b;
		} else {
			intValue = 256 + b;
		}
		return Integer.toHexString(intValue);
	}
	
	  //调用此函数保证程序只有一个实例在运行
	public static void makeSingle(String singleId) {
		// TODO Auto-generated method stub
		RandomAccessFile raf = null;
		FileChannel channel = null;
		FileLock lock = null;
		try {
			// 在临时文件夹创建一个临时文件，锁住这个文件用来保证应用程序只有一个实例被创建.
	        File sf = new File(System.getProperty("java.io.tmpdir") + singleId + ".single");
	        sf.deleteOnExit();
	        sf.createNewFile();
	        raf = new RandomAccessFile(sf, "rw");
	        channel = raf.getChannel();
	        lock = channel.tryLock();
	        if (lock == null) {
	        	// 如果没有得到锁，则程序退出.
	        	//throw new Error("An instance of the application is running.");
	          	JOptionPane.showMessageDialog(null,"An instance of the application is running.");
	           	System.exit(0);//exit
	        }
		} catch (Exception e) {
			e.printStackTrace();
			}
	}

	public static String getRealPath() {
		String realPath = CommonFunc.class.getClassLoader().getResource("").getFile();
		File file = new File(realPath);
		realPath = file.getAbsolutePath();
		try {
			realPath = java.net.URLDecoder.decode(realPath, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return realPath;
	}

	//region 設置顏色
	//Set SW Color
	public static void setBkColor(JFrame frame,Color color) {
		 setBkColor(frame.getContentPane(),color);
	}
	public static void setBkColor(JDialog dialog,Color color) {
		 setBkColor(dialog.getContentPane(),color);
	}
	public static void setBkColor(Container container,Color color) {
		 container.setBackground(color);
		 setBkColor(container.getComponents(),color);
	}
	public static void setBkColor(JPanel panel,Color color) {
		 panel.setBackground(color);
		 setBkColor(panel.getComponents(),color);
	}
	private static void setBkColor(Component[] ca,Color color) {
		 for (int i = 0; i < ca.length; i++) {
			 if(ca[i] instanceof JPanel) {
				 setBkColor((JPanel)ca[i],color);
			 } else {
				if(ca[i] instanceof JCheckBox)
					 ca[i].setBackground(color);
			 	}
		 	}
	}
	//endregion
	
	/** 
	* 传入byte[],传出byte[] 
	* @param imageByte 图片字节数组 
	* @param width 生成小图片宽度 
	* @param height 生成小图片高度 
	* @param gp 是否等比缩放 
	* @return 
	*/  

	//public static byte[] compressPic(byte[] imageByte, int width, int height, boolean gp) { 
	public static byte[] compressPic(byte[] imageByte,int width,int height, boolean gp) { 
		byte[] inByte = null;  
		try {   
		ByteArrayInputStream byteInput = new ByteArrayInputStream(imageByte);  
		Image img = ImageIO.read(byteInput);  
		// 判断图片格式是否正确   
		if (img.getWidth(null) == -1) {  
		return inByte;  
		} else {   
		int newWidth; int newHeight;   
		// 判断是否是等比缩放   
		if (gp == true) {   
		// 为等比缩放计算输出的图片宽度及高度   
		double rate1 = ((double) img.getWidth(null)) / (double) width + 0.1;   
		double rate2 = ((double) img.getHeight(null)) / (double) height + 0.1;  
		// 根据缩放比率大的进行缩放控制   
		double rate = rate1 > rate2 ? rate1 : rate2;   
		newWidth = (int) (((double) img.getWidth(null)) / rate);   
		newHeight = (int) (((double) img.getHeight(null)) / rate);   
		} else {   
		newWidth = width; // 输出的图片宽度   
		newHeight = height; // 输出的图片高度   
		}   
		BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);   
		img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);  
		/* 
		* Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 
		* 优先级比速度高 生成的图片质量比较好 但速度慢 
		*/   
		tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);  
		  
		ImageWriter imgWrier;  
		ImageWriteParam imgWriteParams;  
		// 指定写图片的方式为 jpg  
		imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();  
		imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);  
		//       // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT  
		//       imgWriteParams.setCompressionMode(imgWriteParams.MODE_EXPLICIT);  
		//       // 这里指定压缩的程度，参数qality是取值0~1范围内，  
		//       imgWriteParams.setCompressionQuality((float)45217/imageByte.length);  
		//                          
		//       imgWriteParams.setProgressiveMode(imgWriteParams.MODE_DISABLED);  
		//       ColorModel colorModel = ColorModel.getRGBdefault();  
		//       // 指定压缩时使用的色彩模式  
		//       imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel, colorModel  
		//               .createCompatibleSampleModel(100, 100)));  
		//将压缩后的图片返回字节流  
		ByteArrayOutputStream out = new ByteArrayOutputStream(imageByte.length);  
		imgWrier.reset();  
		// 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何 OutputStream构造  
		imgWrier.setOutput(ImageIO.createImageOutputStream(out));  
		// 调用write方法，就可以向输入流写图片  
		imgWrier.write(null, new IIOImage(tag, null, null), imgWriteParams);  
		out.flush();  
		out.close();  
		byteInput.close();  
		inByte = out.toByteArray();  
		 
		}   
		} catch (IOException ex) {   
		ex.printStackTrace();  
		}   
		return inByte;  
	} 

	public static void writeLog(String path, String str)
    {
        try
        {
        File file=new File(path);
        if(!file.exists())
            file.createNewFile();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        FileOutputStream out=new FileOutputStream(file,false); //如果追加方式用true        
        StringBuffer sb=new StringBuffer();
        sb.append("-----------"+sdf.format(new Date())+"------------\n");
        sb.append(str+"\n");
        out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
        out.close();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getStackTrace());
        }
    }    
    public static String readLog(File file)
    {
        StringBuffer sb=new StringBuffer();
        String tempstr=null;
        try
        {         
            FileInputStream fis=new FileInputStream(file);
            BufferedReader br=new BufferedReader(new InputStreamReader(fis));
            while((tempstr=br.readLine())!=null)
                sb.append(tempstr);
        }
        catch(IOException ex)
        {
            System.out.println(ex.getStackTrace());
        }
        return sb.toString();
    }
    
    
	
	
    /**
     * 删除空目录
     * @param dir 将要删除的目录路径
     */
    public static void doDeleteEmptyDir(String dir) {
        boolean success = (new File(dir)).delete();
        if (success) {
            System.out.println("Successfully deleted empty directory: " + dir);
        } else {
            System.out.println("Failed to delete empty directory: " + dir);
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    
    public static void delDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
            }
        }else{
        	dir.mkdir();
        }
    }
    
    public static boolean PrintZPL(String strZPL){
		DocFlavor dof = null;

		// 获得打印属性
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
	
		// 获得打印设备 ，字节流方式
		PrintService pss[] = PrintServiceLookup.lookupPrintServices(dof,pras);
		
		// 如果没有获取打印机
		if (pss.length == 0) {
			System.out.println("没有打印机！");
			JOptionPane.showMessageDialog(null,"ERROR:没有打印机");
			// 终止程序
			return false;
		}
		//JOptionPane.showMessageDialog(null,PrintServiceLookup.lookupDefaultPrintService());
		PrintService psZebra= null;  
        for (int i = 0; i < pss.length; i++) {  
           System.out.println("service found: " + pss[i]);  
            String svcName = pss[i].toString();  
            if (svcName.contains("Zebra_RAW")) {  
            	psZebra = pss[i];  
                //System.out.println("my printer found: " + svcName);  
                //System.out.println("my printer found: " + psZebra);  
               break;  
            }  
        } 
		if (psZebra == null) {
			String strOsName=getOSName();
			if(strOsName.startsWith("windows")){
				 psZebra = PrintServiceLookup.lookupDefaultPrintService();
			}
		}
       
		if (psZebra == null) {
			 psZebra = PrintServiceLookup.lookupDefaultPrintService();
			 if(psZebra == null){
				 //JOptionPane.showMessageDialog(null,"ERROR:没有打印机1");
					System.out.println("No Printer.");
					return false; 
			 }
		}
		
		DocPrintJob job = psZebra.createPrintJob();
		byte[] by = (strZPL).getBytes();
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		Doc doc = new SimpleDoc(by, flavor, null);
		try {
			job.print(doc, null);
		} catch (PrintException e) {
			// 
			e.printStackTrace();
			System.out.println(e.toString());
			//JOptionPane.showMessageDialog(null,e.toString());
			return false;
		}
		return true;
	}
}