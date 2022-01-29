package cmf.covid.api.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cmf.covid.api.dto.ReportDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@AutoConfigureMockMvc
class CovidStatusByCountryTest {

  private static final String COUNTRY = "spain";
  private static final String URL_TEMPLATE = "/cmf/api/covid/v1/status/country/{country}";
  private static final String JSON_FILE = "/covid-report-test.json";

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private RestTemplate restTemplate;

  @Test
  void givenValidRequest_whenCallCovidService_thenOk() throws Exception {
    when(restTemplate.getForEntity(anyString(), any()))
        .thenReturn(new ResponseEntity<>(getJson(), HttpStatus.OK));

    mvc.perform(get(URL_TEMPLATE, COUNTRY))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(20)))
        .andDo(print());

    verify(restTemplate).getForEntity(anyString(), any());
  }

  private ReportDto[] getJson() throws IOException {
    try (Reader reader = new InputStreamReader(this.getClass().getResourceAsStream(JSON_FILE))) {
      return objectMapper.readValue(reader, ReportDto[].class);
    } catch (IOException ioException) {
      throw ioException;
    }
  }

}
