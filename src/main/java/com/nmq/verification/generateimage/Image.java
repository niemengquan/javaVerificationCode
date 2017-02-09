package com.nmq.verification.generateimage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by niemengquan on 2017/2/4.
 */
public class Image {
    private static Map<String,ImageGroup> imageGroupMap=new HashMap<String, ImageGroup>();
    private static Map<Integer,Map<String,ImageGroup>> countGroupsmap=new HashMap<Integer, Map<String, ImageGroup>>();

    public static ImageResult generateImage() throws IOException {
        initImageGroup();
        GenerateImageGroup generateImageGroup =randomImageGroups();
        List<BufferedImageWrap> images=new ArrayList<BufferedImageWrap>();
        for(ImageGroup group : generateImageGroup.getGroups()){
            for(String imageName:group.getImages()){
                images.add(new BufferedImageWrap(false,getBufferedImage(imageName)));
            }
        }
        for(String imgName:generateImageGroup.getKeyGroup().getImages()){
            images.add(new BufferedImageWrap(true,getBufferedImage(imgName)));
        }
        return mergeImage(images,generateImageGroup.getKeyGroup().getName());
    }

    private static GenerateImageGroup randomImageGroups() {
        List<ImageGroup> result=new ArrayList<ImageGroup>();
        int num=random(0,imageGroupMap.size()-1);
        
        String name=new ArrayList<String>(imageGroupMap.keySet()).get(num);
        ImageGroup keyGroup = imageGroupMap.get(name);

        Map<Integer,Map<String,ImageGroup>> thisCountGroupsMap=new HashMap<Integer, Map<String, ImageGroup>>(countGroupsmap);
        thisCountGroupsMap.get(keyGroup.getCount()).remove(name);

        //假设总量8个，每种名称图片只有2个或者4个、6个（为了逻辑简单）
        int leftCount=8-keyGroup.getCount();
        if(leftCount==4){
            if(new Random().nextInt()%2==0){
                List<ImageGroup> groups=new ArrayList<ImageGroup>(thisCountGroupsMap.get(4).values());
                if(groups.size()>1){
                    //随机取一个
                    num=random(0,groups.size()-1);
                }else{
                    //直接取0
                    num=0;
                }
                result.add(groups.get(num));
            }else{
                List<ImageGroup> groups=new ArrayList<ImageGroup>(thisCountGroupsMap.get(2).values());
                int num1=random(0,groups.size()-1);
                result.add(groups.get(num1));

                int num2=random(0,groups.size()-1,num1);
                result.add(groups.get(num2));
            }
        }else if(leftCount==6) {
            if (new Random().nextInt() % 2 == 0) {
                //组合为一个4张的和一个2张的
                List<ImageGroup> groups1 = new ArrayList<ImageGroup>(thisCountGroupsMap.get(4).values());
                int num1 = random(0, groups1.size() - 1);
                result.add(groups1.get(num1));

                List<ImageGroup> groups2 = new ArrayList<ImageGroup>(thisCountGroupsMap.get(2).values());
                int num2 = random(0, groups2.size() - 1);
                result.add(groups2.get(num2));
            } else {
                //组合为三个两张的
                List<ImageGroup> groups = new ArrayList<ImageGroup>(thisCountGroupsMap.get(2).values());
                int num1 = random(0, groups.size() - 1);
                result.add(groups.get(num1));

                int num2 = random(0, groups.size() - 1, num1);
                result.add(groups.get(num2));

                int num3 = random(0, groups.size() - 1, num1, num2);
                result.add(groups.get(num3));
            }
        }else if(leftCount==2){
            List<ImageGroup> groups2 = new ArrayList<ImageGroup>(thisCountGroupsMap.get(2).values());
            int num2 = random(0, groups2.size() - 1);
            result.add(groups2.get(num2));
        }
        return new GenerateImageGroup(keyGroup,result);
    }

    /**
     * 生成验证码图片
     * @param imageWraps
     * @param tip
     * @return
     */
    private static ImageResult mergeImage(List<BufferedImageWrap> imageWraps, String tip) {
        Collections.shuffle(imageWraps);
        //原始图片宽200px，高200px
        int width=200;
        int high=200;
        int totalWidth=width*4;

        BufferedImage destImage=new BufferedImage(totalWidth,400,BufferedImage.TYPE_INT_RGB);
        int x1=0;
        int x2=0;
        int order=0;
        List<Integer> keysOrderList=new ArrayList<Integer>();
        StringBuilder keysOrder=new StringBuilder();
        Set<Integer> keySet=new HashSet<Integer>();
        for(BufferedImageWrap image:imageWraps){
            int[] rgb = image.getBufferedImage().getRGB(0, 0, width, high, null, 0, width);
            if(image.isKey()){
                keysOrderList.add(order);
                int x=(order%4)*200;
                int y=order<4?0:200;
                keySet.add(order);
                keysOrder.append(order).append("(").append(x).append(",").append(y).append(")|");
            }
            if (order < 4) {
                destImage.setRGB(x1,0,width,high,rgb,0,width);//设置上半部分的RGB
                x1+=width;
            }else{
                destImage.setRGB(x2,high,width,high,rgb,0,width);
                x2+=width;
            }
            order++;
        }
        keysOrder.deleteCharAt(keysOrder.length()-1);
        System.out.println("答案位置："+keysOrder.toString());
        String fileName=UUID.randomUUID().toString().replaceAll("-","");
        String baseDir=System.getProperty("webapp.root")+File.separator+"mergeImage";
        //String baseDir="E:\\java_workspace\\javaVerificationCode\\src\\main\\webapp\\mergeImage";
        String fileUrl=baseDir+File.separator+fileName;
        saveImage(destImage,fileUrl,"jpeg");

        ImageResult ir=new ImageResult();
        ir.setName(fileName+".jpeg");
        ir.setKeySet(keySet);
        ir.setUniqueKey(fileName);
        ir.setTip(tip);
        return ir;
    }

    /**
     * 初始化图片组map
     */
    private static void initImageGroup() {
       /* ImageGroup group1=new ImageGroup("土豆",2,"tudou/1.jpg","tudou/2.jpg");
        ImageGroup group2=new ImageGroup("兔子",2,"tuzi/1.jpg","tuzi/2.jpg");
        initMap(group1,group2);*/
        String baseDir=System.getProperty("webapp.root")+File.separator+"image";
        //String baseDir="E:\\java_workspace\\javaVerificationCode\\src\\main\\webapp\\image";
        File baseDirFile=new File(baseDir);
        if(baseDirFile.exists()&&baseDirFile.isDirectory()){
            File[] files = baseDirFile.listFiles();
            for(File file:files){
                String oneLevelFileName=file.getName();
                if(file.exists()&&file.isDirectory()){
                    File[] twoLevelFiles = file.listFiles();
                    String[] imageGroups=new String[twoLevelFiles.length];
                    int index=0;
                    for(File f:twoLevelFiles){
                        String twoLevelFileName=f.getName();
                        imageGroups[index]=oneLevelFileName+"/"+twoLevelFileName;
                        index++;
                    }
                    ImageGroup group=new ImageGroup(oneLevelFileName,twoLevelFiles.length,imageGroups);
                    initMap(group);
                }
            }
        }
    }

    private static void initMap(ImageGroup ... groups) {
        for(ImageGroup imageGroup:groups){
            imageGroupMap.put(imageGroup.getName(),imageGroup);
            if(!countGroupsmap.containsKey(imageGroup.getCount())){
                countGroupsmap.put(imageGroup.getCount(),new HashMap<String, ImageGroup>());
            }
            countGroupsmap.get(imageGroup.getCount()).put(imageGroup.getName(),imageGroup);
        }
    }

    private static int random(int min,int max){
        Random random=new Random();
        return random.nextInt(max-min+1)+min;
    }
    private static int random(int min,int max,Integer ... notInclude){
        int num=random(min,max);
        List<Integer> notList= Arrays.asList(notInclude);
        while (notList.contains(num)){
            num=random(min,max);
        }
        return num;
    }

    private static BufferedImage getBufferedImage(String fileUrl) throws IOException {
        String baseDir=System.getProperty("webapp.root")+File.separator+"image";
        //String baseDir="E:\\java_workspace\\javaVerificationCode\\src\\main\\webapp\\image";
        File f=new File(baseDir+File.separator+fileUrl);
        return ImageIO.read(f);
    }
    private static boolean saveImage(BufferedImage saveImg,String fileUrl,String format){
        File file=new File(fileUrl+"."+format);
        try {
            return ImageIO.write(saveImg,format,file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
