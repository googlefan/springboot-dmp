package com.zpy.hadoop.exam;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Created by zhuhui on 16-10-19.
 */
public class ExamTypeTotal {
    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            String line = value.toString();
            StringTokenizer tokenizer = new StringTokenizer(line);
            while (tokenizer.hasMoreTokens()) {
                word.set(tokenizer.nextToken());
                output.collect(word, one);
            }
        }

        public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {

            @Override
            public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
                int sum = 0;
                while (values.hasNext()) {
                    sum += values.next().get();
                }
                output.collect(key, new IntWritable(sum));
            }
        }

        public static void main(String[] args) throws IOException {
            JobConf conf = new JobConf(ExamTypeTotal.class);
            conf.setJobName("ExamTypeTotal");
            conf.setOutputKeyClass(Text.class);
            conf.setOutputValueClass(IntWritable.class);
            conf.setMapperClass(ExamTypeTotal.Map.class);
            conf.setCombinerClass(ExamTypeTotal.Map.Reduce.class);
            conf.setInputFormat(TextInputFormat.class);
            conf.setOutputFormat(TextOutputFormat.class);
            conf.setUser("root");
            FileInputFormat.setInputPaths(conf, new Path("hdfs://localhost:54310/user/zhuhui/tmp/+~JF6974580061511079712.tmp"));
            FileOutputFormat.setOutputPath(conf, new Path("/tmp/out6"));
            JobClient.runJob(conf);
        }
    }
}
