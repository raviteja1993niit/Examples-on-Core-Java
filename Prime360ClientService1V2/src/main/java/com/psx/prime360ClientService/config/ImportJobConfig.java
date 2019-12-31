/*package com.psx.prime360ClientService.config;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.ResourceUtils;

import com.psx.prime360ClientService.entity.Customer1;

@Configuration
public class ImportJobConfig<JobCompletionNotificationListener> {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Value("${filepath}")
	String pathToFile;
	
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	

	public DataSource dataSource(){
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://192.168.1.148/pdborcl");
		dataSource.setUsername("bajaj_migration2");
		dataSource.setPassword("posidex");
		
		return dataSource;
	}
	

    @Bean
    @Scope(value = "step", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public FlatFileItemReader<Customer1> importReader() {
    
    	
    	
        FlatFileItemReader<Customer1> reader = new FlatFileItemReader<>();
        try {
			reader.setResource(new FileSystemResource(ResourceUtils.getFile("classpath:abv.csv")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        reader.setLineMapper(new DefaultLineMapper<Customer1>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"USERID", "NAME"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Customer1>() {{
                setTargetType(Customer1.class);
            }});
        }});
        return reader;
    }

    @Bean
    public APSUploadFileItemProcessor processor() {
        return new APSUploadFileItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Customer1> writer() {
        JdbcBatchItemWriter<Customer1> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<Customer1>());
        writer.setSql("INSERT INTO PSX_COMMON_STAGING_DUMMY(USERID, NAME)"
				+ " VALUES(:USERID, :NAME)");
        writer.setDataSource(dataSource);
        return writer;
    }
   
    @Bean
    public Job importUserJob(ItemReader<Customer1> importReader) {
    	return jobBuilderFactory.get("importUserJob")
				.incrementer(new RunIdIncrementer())
				.flow(step1(importReader))
				.end()
				.build();
    }
    
    @Bean
    public Step step1(ItemReader<Customer1> importReader) {
        return stepBuilderFactory.get("step1").<Customer1, Customer1>chunk(10).reader(importReader)
                .processor(processor()).writer(writer()).build();
    }

}*/