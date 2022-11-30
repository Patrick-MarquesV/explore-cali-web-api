package com.group.artifact;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.artifact.service.TourPackageService;
import com.group.artifact.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

@SpringBootApplication
public class ExploreApplication implements CommandLineRunner {

	@Autowired
	private TourPackageService tourPackageService;

	@Autowired
	private TourService tourService;

	public static void main(String[] args) {

		SpringApplication.run(ExploreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		createTourPackage();

		long numOfPackages = tourPackageService.total();

		createTour("ExploreCalifornia.json");
		long numOfTours = tourService.total();

	}


	private void createTourPackage() {
		tourPackageService.createTourPackage("BC", "Backpack Cal");
		tourPackageService.createTourPackage("CC", "California Calm");
		tourPackageService.createTourPackage("CH", "California Hot springs");
		tourPackageService.createTourPackage("CY", "Cycle California");
		tourPackageService.createTourPackage("DS", "From Desert to Sea");
		tourPackageService.createTourPackage("KC", "Kids California");
		tourPackageService.createTourPackage("NW", "Nature Watch");
		tourPackageService.createTourPackage("SC", "Snowboard Cali");
		tourPackageService.createTourPackage("TC", "Taste of California");
	}

	private void createTour(String fileToImport) throws IOException{
		TourFromFile.read(fileToImport).forEach(tourFromFile ->
				tourService.createTour(tourFromFile.getTitle(), tourFromFile.getPackageName(), tourFromFile.getDetails()));
	}

	private static class TourFromFile {
		//fields
		String title;
		String packageName;
		Map<String, String> details;

		TourFromFile(Map<String, String> record){
			this.title = record.get("title");
			this.packageName = record.get("packageType");
			this.details = record;
		}


		//reader
		static List<TourFromFile> read(String fileToImport) throws IOException {
			return new ObjectMapper().setVisibility(FIELD, ANY).
					readValue(new FileInputStream(fileToImport), new TypeReference<List<TourFromFile>>() {
					});
		}

		protected TourFromFile() {
		}

		String getTitle() { return title; }

		String getPackageName() { return packageName; }

		Map<String, String> getDetails() { return details; }

	}

}

