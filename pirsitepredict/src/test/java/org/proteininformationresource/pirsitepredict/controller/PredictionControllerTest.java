package org.proteininformationresource.pirsitepredict.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;

public class PredictionControllerTest {

	private PredictionController controller;
	private ExtendedModelMap model;

	@Before
	public void setUp() {
		controller = new PredictionController();
		model = new ExtendedModelMap();
	}

	@Test
	public void nullJobIdReturnsRetrieveFormWithRequiredError() {
		String view = controller.retrievePredictionResults(null, model);
		assertThat(view).isEqualTo("prediction/retrieveForm");
		assertThat(model.get("jobIdError")).isEqualTo("Job ID is required.");
	}

	@Test
	public void emptyJobIdReturnsRetrieveFormWithRequiredError() {
		String view = controller.retrievePredictionResults("", model);
		assertThat(view).isEqualTo("prediction/retrieveForm");
		assertThat(model.get("jobIdError")).isEqualTo("Job ID is required.");
	}

	@Test
	public void nonNumericJobIdReturnsRetrieveFormWithInvalidError() {
		String view = controller.retrievePredictionResults("abc123", model);
		assertThat(view).isEqualTo("prediction/retrieveForm");
		assertThat(model.get("jobIdError")).isEqualTo("Invalid Job ID.");
	}

	@Test
	public void tooShortNumericJobIdReturnsRetrieveFormWithInvalidError() {
		String view = controller.retrievePredictionResults("1234567", model);
		assertThat(view).isEqualTo("prediction/retrieveForm");
		assertThat(model.get("jobIdError")).isEqualTo("Invalid Job ID.");
	}
}
