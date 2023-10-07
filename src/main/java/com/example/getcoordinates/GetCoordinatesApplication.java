package com.example.getcoordinates;

import com.example.getcoordinates.Entity.Adresse;
import com.example.getcoordinates.Entity.Koordinaten;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@Getter
@AllArgsConstructor
public class GetCoordinatesApplication {

	public static void main(String[] args) throws JsonProcessingException {


		SpringApplication.run(GetCoordinatesApplication.class, args);
		String url = getUrl();


		WebClient.Builder builder = WebClient.builder();

		String geoData = builder.build()
				.get()
				.uri(url)
				.retrieve()
				.bodyToMono(String.class)
				.block();
		ObjectMapper objectMapper= new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(geoData);

		String lat = jsonNode.get(0).get("lat").asText();
		String lon = jsonNode.get(0).get("lon").asText();
		Koordinaten koordinaten = new Koordinaten(lat, lon);

		System.out.println("---------------------------------------------------");
		System.out.println("Latitude: " + koordinaten.getLat());
		System.out.println("Longitude: " + koordinaten.getLon());
		System.out.println("---------------------------------------------------");
	}

	private static String getUrl() {
		Adresse adresse = new Adresse("Leopoldstraße", 5, "Karlsruhe", "BW", "76133","DE");

		String strasse = String.valueOf(adresse.strasse);
		int hausnummer = adresse.hausnummer;
		String stadt = adresse.stadt;
		String bundesland = adresse.bundesland;
		String postleitzahl = adresse.plz;
		String land = adresse.land;

// Den URI-String mit den Adresse-Attributen füllen
        return String.format(
				"https://geocode.maps.co/search?street=%s+%d&city=%s&state=%s&postalcode=%s&country=%s",
				strasse, hausnummer, stadt, bundesland, postleitzahl, land
		);
	}

}
