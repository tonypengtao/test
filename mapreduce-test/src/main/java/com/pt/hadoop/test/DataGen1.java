package com.pt.hadoop.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 顺序生成编号 随机生成中文姓名，性别，出生年月，手机号，住址
 */
public class DataGen1 {

	public static String base = "abcdefghijklmnopqrstuvwxyz0123456789"; 
	private static String firstName="赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮卞齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闵席季麻强贾路娄危江童颜郭梅盛林刁钟徐邱骆高夏蔡田樊胡凌霍虞万支柯咎管卢莫经房裘缪干解应宗宣丁贲邓郁单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣翁荀羊於惠甄魏加封芮羿储靳汲邴糜松井段富巫乌焦巴弓牧隗山谷车侯宓蓬全郗班仰秋仲伊宫宁仇栾暴甘钭厉戎祖武符刘姜詹束龙叶幸司韶郜黎蓟薄印宿白怀蒲台从鄂索咸籍赖卓蔺屠蒙池乔阴郁胥能苍双闻莘党翟谭贡劳逄姬申扶堵冉宰郦雍却璩桑桂濮牛寿通边扈燕冀郏浦尚农温别庄晏柴瞿阎充慕连茹习宦艾鱼容向古易慎戈廖庚终暨居衡步都耿满弘匡国文寇广禄阙东殴殳沃利蔚越夔隆师巩厍聂晁勾敖融冷訾辛阚那简饶空曾毋沙乜养鞠须丰巢关蒯相查后江红游竺权逯盖益桓公万俟司马上官欧阳夏侯诸葛闻人东方赫连皇甫尉迟公羊澹台公冶宗政濮阳淳于仲孙太叔申屠公孙乐正轩辕令狐钟离闾丘长孙慕容鲜于宇文司徒司空亓官司寇仉督子车颛孙端木巫马公西漆雕乐正壤驷公良拓拔夹谷宰父谷粱晋楚阎法汝鄢涂钦段干百里东郭南门呼延归海羊舌微生岳帅缑亢况后有琴梁丘左丘东门西门商牟佘佴伯赏南宫墨哈谯笪年爱阳佟第五言福百家姓续";
	private static String girl="秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽 ";
	private static String boy="伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";
	private static String[] address="哈尔滨,大庆,齐齐哈尔,佳木斯,鸡西,鹤岗,双鸭山,牡丹江,伊春,七台河,黑河,绥化,长春,吉林,四平,辽源,通化,白山,松原,白城,沈阳,大连,鞍山,抚顺,本溪,丹东,锦州,营口,阜新,辽阳,盘锦,铁岭,朝阳,葫芦岛,石家庄,唐山,邯郸,秦皇岛,保定,张家口,承德,廊坊,沧州,衡水,邢台,济南,青岛,淄博,枣庄,东营,烟台,潍坊,济宁,泰安,威海,日照,莱芜,临沂,德州,聊城,菏泽,滨州,南京,镇江,常州,无锡,苏州,徐州,连云港,淮安,盐城,扬州,泰州,南通,宿迁,合肥,蚌埠,芜湖,淮南,亳州,阜阳,淮北,宿州,滁州,安庆,巢湖,马鞍山,宣城,黄山,池州,铜陵,杭州,嘉兴,湖州,宁波,金华,温州,丽水,绍兴,衢州,舟山,台州,福州,厦门,泉州,三明,南平,漳州,莆田,宁德,龙岩,广州,深圳,汕头,惠州,珠海,揭阳,佛山,河源,阳江,茂名,湛江,梅州,肇庆,韶关,潮州,东莞,中山,清远,江门,汕尾,云浮,海口,三亚,昆明,曲靖,玉溪,保山,昭通,丽江,普洱,临沧,贵阳,六盘水,遵义,安顺,成都,绵阳,德阳,广元,自贡,攀枝花,乐山,南充,内江,遂宁,广安,泸州,达州,眉山,宜宾,雅安,资阳,长沙,株洲,湘潭,衡阳,岳阳,郴州,永州,邵阳,怀化,常德,益阳,张家界,娄底,武汉,襄樊,宜昌,黄石,鄂州,随州,荆州,荆门,十堰,孝感,黄冈,咸宁,郑州,洛阳,开封,漯河,安阳,新乡,周口,三门峡,焦作,平顶山,信阳,南阳,鹤壁,濮阳,许昌,商丘,驻马店,太原,大同,忻州,阳泉,长治,晋城,朔州,晋中,运城,临汾,吕梁,西安,咸阳,铜川,延安,宝鸡,渭南,汉中,安康,商洛,榆林,兰州,天水,平凉,酒泉,嘉峪关,金昌,白银,武威,张掖,庆阳,定西,陇南,西宁,南昌,九江,赣州,吉安,鹰潭,上饶,萍乡,景德镇,新余,宜春,抚州,台北,台中,基隆,高雄,台南,新竹,嘉义".split(",");
	
	public static int getNum(int start,int end) {
		return (int)(Math.random()*(end-start+1)+start);
	}
	
	
	/**
	 * 返回中文姓名和性别
	 */
	private static String name_sex = "";
	private static String getChineseName() {
		int index=getNum(0, firstName.length()-1);
		String first=firstName.substring(index, index+1);
		int sex=getNum(0,1);
		String str=boy;
		int length=boy.length();
		if(sex==0){
			str=girl;
			length=girl.length();
			name_sex = "女";
		}else {
			name_sex="男";
		}
		index=getNum(0,length-1);
		String second=str.substring(index, index+1);
		int hasThird=getNum(0,1);
		String third="";
		//if(hasThird==1){	//77-80可以实现姓名是两个字三个字的随机实现
			index=getNum(0,length-1);
			third=str.substring(index, index+1);
		//}
		return first+second+third;
	}
	
	/**
	 * 返回出生日期
	 */
	public static String dateToStamp(String startDate, String endDate) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = simpleDateFormat.parse(startDate);
		Date date2 = simpleDateFormat.parse(endDate);
		long L1 = date1.getTime();
		long L2 = date2.getTime();
		long L3 = L2 - L1;
		long L4 = L2 - (long) (Math.random() * L3);
		String str = simpleDateFormat.format(L4);
		return str;
	}
	
	/**
	 * 返回手机号码
	 */
	private static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
	private static String getTel() {
		int index=getNum(0,telFirst.length-1);
		String first=telFirst[index];
		String second=String.valueOf(getNum(1,888)+10000).substring(1);
		String third=String.valueOf(getNum(1,9100)+10000).substring(1);
		return first+second+third;
	}
	
	
	/**
	 * 返回地址
	 * @return
	 */
	private static String getaddress() {
		int index=getNum(0,address.length-1);
		String first=address[index];
		return first;
	}


	/**
	 * 数据封装
	 * @return
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	public static Map getAddress() {
		Map map=new HashMap();
		map.put("name", getChineseName());
		map.put("sex", name_sex);
		map.put("tel", getTel());
		map.put("road", getaddress());
		
		//map.put("born", dateToStamp());
		return map;
	}
	
	public static void main(String[] args) throws ParseException {
		String st = "";
		/*String id ="";
		String name = "";
		String sex = "";
		String born ="";
		String telephone = "";
		String address = "";*/
		//前面的连续自增ID
		DecimalFormat format =new DecimalFormat("0000");
		for (int i = 1; i < 10000; i++) {
			System.out.print(format.format(i)+"\t");
			//打印出生年月
			System.out.print(getChineseName()+"\t");
			System.out.print(name_sex+"\t");
			//打印时间戳里面的信息
			String str = dateToStamp("1986-01-01", "2000-01-01");
			System.out.print(str+"\t");
			System.out.print(getTel()+"\t");
			System.out.println(getaddress());
				String id=format.format(i);
				String name=getChineseName();
				String sex=name_sex;
				String	born = str;
				String	telephone = getTel();
				String	address = getaddress();
				 st+=id+"\t"+name+"\t"+sex+"\t"+born+"\t"+telephone+"\t"+address+"\r\n";
			}
		File file = new File("D://test/hadoop/test3.txt");
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			bw.write(st);
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
