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
 * 统计所有不同城市的学生数量分布
 * @author pengtao
 *
 */
public class Case01 {

	public static void main(String[] args) throws Exception {
//		String pathIn = args[0];
//		String pathOut = args[1];
		Job job = new Job();
		job.setJarByClass(Case01.class);
		job.setJobName("Case01");
		FileInputFormat.addInputPath(job, new Path("D:\\test\\hadoop\\test3.txt"));
		FileOutputFormat.setOutputPath(job, new Path("D:\\test\\hadoop_output"));
		job.setMapperClass(Job2Mapper.class);
		job.setReducerClass(Job2Reducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		System.exit(job.waitForCompletion(true)?0:1);
	}
	
	private static class Job2Mapper extends Mapper<LongWritable, Text, Text, Text> {
		
		@Override
		protected void setup(Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.setup(context);
		}
		
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			
			String[] fields = value.toString().trim().split("\t");
			context.write(new Text(fields[5]), new Text(fields[1]));
			
		}
		
		@Override
		protected void cleanup(Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.cleanup(context);
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
			int count = 0;
			for(Text value: arg1) {
				count++;
			}
			arg2.write(arg0, new Text(count+""));
		}
		
		@Override
		protected void cleanup(Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.cleanup(context);
		}
	}
}
