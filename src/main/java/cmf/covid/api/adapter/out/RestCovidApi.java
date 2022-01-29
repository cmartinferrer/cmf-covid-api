package cmf.covid.api.adapter.out;

import cmf.covid.api.application.domain.Status;
import cmf.covid.api.application.port.outcoming.CovidApi;
import cmf.covid.api.dto.ReportDto;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Output adapter responsible for getting COVID information from an external API.
 *
 * @author Cristian Martín Ferrer on 2021-09-18
 * @see <a href="https://documenter.getpostman.com/view/10808728/SzS8rjbc">covid19 api</a>
 */
@Component
@RequiredArgsConstructor
public class RestCovidApi implements CovidApi {

  private static final String URL = "https://api.covid19api.com/live/country/";

  private final RestTemplate restTemplate;
  private final CovidApiMapper mapper;

  @Override
  public List<Status> findStatusByCountry(String country) {
    val response = restTemplate
        .getForEntity(URL + country, ReportDto[].class)
        .getBody();

    return mapper.toDomain(Arrays.asList(response));
  }

  @Mapper
  public interface CovidApiMapper {

    List<Status> toDomain(List<ReportDto> response);
  }
}
