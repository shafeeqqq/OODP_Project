//import java.io.FileInputStream;
//import java.lang.reflect.Constructor;
//import java.util.Properties;
//
//public class ObjectCreator {
//	
//	public static Student create() {
//		Properties prop = new Properties();
//		try {
//			prop.load(new FileInputStream("data1.txt"));
//			String cName = prop.getProperty("classname");
//			String title = prop.getProperty("title");
//			
//			Class cls = Class.forName(cName.trim());
//			Class[] parmTypes = {String.class};
//			Constructor cons = cls.getConstructor(parmTypes);
//			
//			Student s= (Student) cons.newInstance(new Object[] {title});
//			return s;
//		} catch (ClassNotFoundException e)
//	}
//
//}
