package com.springframework.springboot.ApacheCamel;

import java.util.Arrays;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApacheCamelApplication extends RouteBuilder{

	public static void main(String[] args) {
		SpringApplication.run(ApacheCamelApplication.class, args);
	}

	@Override
	public void configure() throws Exception {
		// move the data(files) to one directory another directory  
		System.out.println("<----Started Point---->");
//		from("file:C:/Users/HP/Desktop/input?true")
//		.to("file:C:/Users/HP/Desktop/output");
		
		//we can this for transferring data 
//		moveAllFiles();
		
		// transfer or move a specific file 
//		moveSpecificFile("file1");
		
//		move content of file 
//		moveSpecificFileWithBody("Hey");
		
		
		//generate file with , separate content 
		fileProcess();
		System.out.println("<----Ended Point---->");
	}
	
	//we can move(send,transfer) files(data) staring point to ending point 
	//it will be executed when we call it in side the configure method
	public void moveAllFiles() {
		from("file:C:/Users/HP/Desktop/input?noop=true")
		.to("file:C:/Users/HP/Desktop/output");
	}
	
	//move a specific file using below method
	public void moveSpecificFile(String type) {
		from("file:C:/Users/HP/Desktop/input?noop=true").filter(header(Exchange.FILE_NAME).startsWith(type))
		.to("file:C:/Users/HP/Desktop/output");
	}
	
	//move a particular file with content 
	public void moveSpecificFileWithBody(String content) {
		from("file:C:/Users/HP/Desktop/input?noop=true").filter(body().startsWith(content))
		.to("file:C:/Users/HP/Desktop/output");
	}
	
	// it use to generate new file with content 
	public void fileProcess() {
		from("file:source?noop=true").process(p->{
			String body = p.getIn().getBody(String.class);
			StringBuilder sb = new StringBuilder();
			
			Arrays.stream(body.split(" ")).forEach(s->{
				sb.append(s+",");
			});
			
			p.getIn().setBody(sb);
		})
		.to("file:destination?fileName=records.csv");
	}
}
