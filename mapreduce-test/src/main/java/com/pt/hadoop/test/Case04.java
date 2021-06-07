package com.pt.hadoop.test;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 写mapreduce程序，输出随机的手机号4位尾数最大的10个学生的姓名
 * 0001  陈白露  女  1980-02-10  12312341234  北京市
0002  刘云飞  男  1980-10-01  13612341234  郑州市
0003  张小强  男  1979-08-02  19999228822  杭州市
 * @author pengtao
 *
 */
public class Case04 {

	private static class Job2Mapper extends Mapper<LongWritable, Text, Text, Text> {
		
		private TreeMap<Integer, String> cacheNum = new TreeMap<>();
		
		@Override
		protected void setup(Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.setup(context);
		}
		
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String[] data = value.toString().trim().split("\t");
			String[] phone = data[4].split("");
			Integer endNum = Integer.parseInt(phone[7]+phone[8]+phone[9]+phone[10]);
			cacheNum.put(endNum, data[1]);
			if(cacheNum.size()>10) {
				cacheNum.remove(cacheNum.firstKey());
			}
		}
		
		@Override
		protected void cleanup(Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			for(Entry<Integer, String> entry: cacheNum.entrySet()) {
				context.write(new Text(entry.getKey()+""), new Text(entry.getValue()));
			}
		}
	}
	
	private static class Job2Reducer extends Reducer<Text, Text, Text, Text>{
		@Override
		protected void setup(Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.setup(context);
		}
		
		@Override
		protected void reduce(Text arg0, Iterable<Text> arg1, Reducer<Text, Text, Text, Text>.Context arg2)
				throws IOException, InterruptedException {
			for(Text t: arg1) {
				arg2.write(t, arg0);
			}
		}
		
		@Override
		protected void cleanup(Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.cleanup(context);
		}
	}
	
	public static void main(String[] args) throws Exception {
		String pathIn = args[0];
		String pathOut = args[1];
		Job job = new Job();
		job.setJarByClass(Case01.class);
		job.setJobName("Case02");
		FileInputFormat.addInputPath(job, new Path(pathIn));
		FileOutputFormat.setOutputPath(job, new Path(pathOut));
		job.setMapperClass(Job2Mapper.class);
		job.setReducerClass(Job2Reducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		System.exit(job.waitForCompletion(true)?0:1);
	}
}
