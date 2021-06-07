package com.pt.hadoop.test;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 输出所有11月出生且手机尾号是9的男生姓名
 * 0001  陈白露  女  1980-02-10  12312341234  北京市
0002  刘云飞  男  1980-10-01  13612341234  郑州市
0003  张小强  男  1979-08-02  19999228822  杭州市
 * @author pengtao
 *
 */
public class Case03 {

	private static class Job2Mapper extends Mapper<LongWritable, Text, Text, Text>{
		@Override
		protected void setup(Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.setup(context);
		}
		
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			
			String[] fields = value.toString().split("\t");
			String sex = fields[2];
			String birth = fields[3];
			String phone = fields[4];
			if("男".equals(sex) && "11".equals(birth.split("-")[1]) && phone.endsWith("9")) {
				context.write(new Text("11"), new Text(fields[1]));
			}
			
		}
		
		@Override
		protected void cleanup(Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.cleanup(context);
		}
	}
	
	private static class Job2Reducer extends Reducer<Text, Text, Text, Text> {
		@Override
		protected void setup(Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.setup(context);
		}
		
		@Override
		protected void reduce(Text arg0, Iterable<Text> arg1, Reducer<Text, Text, Text, Text>.Context arg2)
				throws IOException, InterruptedException {
			
			StringBuilder str = new StringBuilder();
			for(Text t: arg1) {
				str.append(t.toString()).append(" ");
			}
			arg2.write(arg0, new Text(str.toString()));
			
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
