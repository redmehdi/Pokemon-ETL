package com.red.one.batch.pokemon;

import com.red.one.batch.pokemon.JobCompletionNotificationListener;
import com.red.one.batch.pokemon.process.PokemonItemProcessor;
import com.red.one.batch.pokemon.reader.PokemonApiReader;
import com.red.one.batch.pokemon.reader.dto.PokemonDetail;
import com.red.one.batch.pokemon.writer.PokemonDBWriter;
import com.red.one.batch.pokemon.reader.PokemonExternalApiClient;
import com.red.one.batch.pokemon.writer.PokeSpecies;
import com.red.one.batch.pokemon.reader.dto.PokeApiSpecies;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableBatchProcessing
@EnableFeignClients
@EnableScheduling
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	JobRepository jobRepository;

	@Autowired
	private PokemonExternalApiClient client;

	@Bean
	public ItemReader<PokemonDetail> reader() throws Exception {
		return new PokemonApiReader(client);
	}

	@Bean
	public PokemonItemProcessor processor() {
		return new PokemonItemProcessor();
	}

	@Bean
	public ItemWriter<PokemonDetail> writer(DataSource dataSource) {
		return new PokemonDBWriter();
	}

	// tag::jobstep[]
	@Bean
	public Job importPokemonJob(JobCompletionNotificationListener listener, Step step1) {
		return jobBuilderFactory.get("importPokemonJob")
			.incrementer(new RunIdIncrementer())
				.preventRestart()
			.listener(listener)
			.flow(step1)
			.end()
			.build();
	}

	@Bean
	public Step step1(ItemWriter<PokemonDetail> writer) throws Exception {
		return stepBuilderFactory.get("step1")
				.<PokemonDetail, PokemonDetail> chunk(1)
				.reader(reader())
				.processor(processor())
				.writer(writer)
				.startLimit(1)
				.allowStartIfComplete(true)
				.build();
	}
}
