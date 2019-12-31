package com.psx.prime360ClientService.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.psx.prime360ClientService.serviceImpl.StorageService;

@SpringBootApplication
public class Prime360RequestPosting implements CommandLineRunner{

	@Resource
	StorageService storageService;
	
	public static void main(String[] args) {
		SpringApplication.run(Prime360RequestPosting.class, args);
		
	}
	
	@Override
	public void run(String... arg) throws Exception {
		storageService.deleteAll();
		storageService.init();
	}
}
