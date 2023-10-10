package com.example.getcoordinates;

import com.example.getcoordinates.Entity.Adresse;
import com.example.getcoordinates.Entity.Koordinaten;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class GetCoordinatesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetCoordinatesApplication.class, args);

		Adresse adresse = new Adresse("Leopoldstraße", 5, "Karlsruhe", "BW", "76133", "DE");
		getCoordinates(adresse);
	}

	private static void getCoordinates(Adresse adresse) {
		String url = getUrl(adresse);
		WebClient.Builder builder = WebClient.builder();

		String geoData = builder.build()
				.get()
				.uri(url)
				.retrieve()
				.bodyToMono(String.class)
				.block();

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = null;
		try {
			jsonNode = objectMapper.readTree(geoData);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		String lat = jsonNode.get(0).get("lat").asText();
		String lon = jsonNode.get(0).get("lon").asText();
		Koordinaten koordinaten = new Koordinaten(lat, lon);

		System.out.println("---------------------------------------------------");
		System.out.println("Latitude: " + koordinaten.getLat());
		System.out.println("Longitude: " + koordinaten.getLon());
		System.out.println("---------------------------------------------------");
	}

	private static String getUrl(Adresse adresse) {
		String strasse = adresse.getStrasse();
		int hausnummer = adresse.getHausnummer();
		String stadt = adresse.getStadt();
		String bundesland = adresse.getBundesland();
		String postleitzahl = adresse.getPlz();
		String land = adresse.getLand();

		return String.format(
				"https://geocode.maps.co/search?street=%s+%d&city=%s&state=%s&postalcode=%s&country=%s",
				strasse, hausnummer, stadt, bundesland, postleitzahl, land
		);
	}
}
