package cmf.covid.api.application.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Status {

  private final String country;
  private final String province;
  private final Integer confirmed;
  private final Integer recovered;
  private final Integer active;
  private final Integer deaths;
  private final String date;
}
