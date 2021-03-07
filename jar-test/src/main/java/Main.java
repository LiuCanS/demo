public class Main {
    public static void main(String[] args) {
        //第一种
//        File path = new File(ResourceUtils.getURL("classpath:").getPath());
//        if(!path.exists()) path = new File("");
//        System.out.println(path.getAbsolutePath());
//        //第二种
//        System.out.println(System.getProperty("user.dir"));
//        //第三种
//        String path1 = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//        System.out.println(URLDecoder.decode(path1, "utf-8"));
//        //第四种
//        String path2 = ResourceUtils.getURL("classpath:").getPath();
//        System.out.println(path2);
        //第五种

       Test t = new Test();
        String filePath = t.getFilePath();
        System.out.println(filePath);
    }
}
