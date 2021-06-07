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
 * 查找并输出所有重复出现的生日和出现次数
 * @author pengtao
 *
 */
public class Case02 {

	
	private static class Job2Mapper extends Mapper<LongWritable, Text, Text, LongWritable>{
		
		LongWritable one = new LongWritable(1);
		
		@Override
		protected void setup(Mapper<LongWritable, Text, Text, LongWritable>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.setup(context);
		}
		
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, LongWritable>.Context context)
				throws IOException, InterruptedException {
			String[] columns = value.toString().trim().split("\t");
			context.write(new Text(columns[3]), one);
			
		}
		
		@Override
		protected void cleanup(Mapper<LongWritable, Text, Text, LongWritable>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.cleanup(context);
		}
	}
	
	private static class Job2Reducer extends Reducer<Text, LongWritable, Text, LongWritable> {
		@Override
		protected void setup(Reducer<Text, LongWritable, Text, LongWritable>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.setup(context);
		}
		
		@Override
		protected void reduce(Text arg0, Iterable<LongWritable> arg1, Reducer<Text, LongWritable, Text, LongWritable>.Context arg2)
				throws IOException, InterruptedException {
			long count = 0;
			for(LongWritable v: arg1) {
				count++;
			}
			if(count>1) {
				arg2.write(arg0, new LongWritable(count));
			}
		}
		
		@Override
		protected void cleanup(Reducer<Text, LongWritable, Text, LongWritable>.Context context)
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
		job.setOutputValueClass(LongWritable.class);
		System.exit(job.waitForCompletion(true)?0:1);
	}
}
