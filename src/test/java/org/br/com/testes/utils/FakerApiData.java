package org.br.com.testes.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.br.com.testes.model.UsuarioRequest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class FakerApiData {
	private static final String FAKER_API_URL = "https://fakerapi.it/api/v1/custom?_quantity=1&firstName=firstName&lastName=lastName&phoneNumber=phone&emailAddress=email&password=password&fullName=name&addressLine1=streetAddress&addressLine2=streetAddress&city=city&stateRegion=state&zipCode=number&country=country&cardNumber=card_number&expirationDate=card_expiration";
	private static final String JSON_FILE_PATH = "src/test/resources/dados/login_data.json";

	private String id;
	private String createdAt;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String emailAddress;
	private String password;
	private String fullName;
	private String addressLine;
	private String addressLine2;
	private String city;
	private String stateRegion;
	private String zipCode;
	private String country;
	private String cardNumber;
	private String cardFullName;
	private String expiryDate;
	private String cvv;


	public FakerApiData() {
		generateFakeData();
	}

	public static UsuarioRequest gerarUsuarioRequest() {
		FakerApiData fakeData = new FakerApiData();
		return UsuarioRequest.builder()
				.nomeCompleto(fakeData.getFullName())
				.nomeUsuario(fakeData.getFirstName())
				.email(fakeData.getEmailAddress())
				.senha(fakeData.getPassword())
				.build();
	}

	private void generateFakeData() {
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpGet request = new HttpGet(FAKER_API_URL);
			String jsonResponse = httpClient.execute(request, response ->
					EntityUtils.toString(response.getEntity()));

			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(jsonResponse);
			JsonNode dataNode = rootNode.get("data").get(0);

			this.id = UUID.randomUUID().toString();
			this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			this.firstName = dataNode.get("firstName").asText();
			this.lastName = dataNode.get("lastName").asText();
			this.phoneNumber = dataNode.get("phoneNumber").asText();
			this.emailAddress = dataNode.get("emailAddress").asText();
			this.password = dataNode.get("password").asText();
			this.fullName = dataNode.get("fullName").asText();
			this.addressLine = dataNode.get("addressLine1").asText();
			this.addressLine2 = dataNode.get("addressLine2").asText();
			this.city = dataNode.get("city").asText();
			this.stateRegion = dataNode.get("stateRegion").asText();
			this.zipCode = dataNode.get("zipCode").asText();
			this.country = dataNode.get("country").asText();
			this.cardNumber = dataNode.get("cardNumber").asText();
			this.cardFullName = this.fullName;
			this.expiryDate = dataNode.get("expirationDate").asText();
			this.cvv = "123";

		} catch (IOException e) {
			throw new RuntimeException("Erro ao gerar dados fake: " + e.getMessage());
		}
	}

	public void salvarDadosEmJson() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);

			File jsonFile = new File(JSON_FILE_PATH);
			List<FakerApiData> dadosExistentes = new ArrayList<>();

			if (jsonFile.exists() && jsonFile.length() > 0) {
				dadosExistentes = mapper.readValue(jsonFile, mapper.getTypeFactory().constructCollectionType(List.class, FakerApiData.class));
			}

			dadosExistentes.add(this);
			mapper.writeValue(jsonFile, dadosExistentes);

		} catch (IOException e) {
			throw new RuntimeException("Erro ao salvar dados em JSON: " + e.getMessage());
		}
	}
}