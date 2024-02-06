package com.training.OnlineTraining.service;

import com.training.OnlineTraining.OnlineTrainingApplication;
import com.training.OnlineTraining.dto.MeasurementDTO;
import com.training.OnlineTraining.model.Contract;
import com.training.OnlineTraining.model.Measurement;
import com.training.OnlineTraining.utils.TestDTOUtils;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = OnlineTrainingApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class,
		FlywayTestExecutionListener.class
})
public class MeasurementTest {

	@Autowired
	private MeasurementService measurementService;

	@Autowired
	private ContractService contractService;

	private MeasurementDTO measurementDTO;
	//
	private Measurement measurement;

	private Contract contract;

	private int numberOfMeasurementsInDatabaseBeforeTest = 0;

	@BeforeEach
	public void setUp(){
		numberOfMeasurementsInDatabaseBeforeTest = measurementService.countMeasurements();

		contract = contractService.getAllContracts().get(0);
		measurementDTO = TestDTOUtils.getMeasurementDTO(contract.getId());
	}
	@Test
	public void testCreateMeasurement()  {
		measurementDTO.setBodyWeight(70.5);
		measurement = measurementService.createMeasurement(measurementDTO);

		assertNotNull(measurement);
		assertEquals(70.5,measurement.getBodyWeight());
	}

	@Test
	public void testGetAllMeasurements()  {
		measurement = measurementService.createMeasurement(measurementDTO);

		MeasurementDTO measurementDTO2 = TestDTOUtils.getMeasurementDTO(contract.getId());
		measurementService.createMeasurement(measurementDTO2);

		MeasurementDTO measurementDTO3 = TestDTOUtils.getMeasurementDTO(contract.getId());
		measurementService.createMeasurement(measurementDTO3);

		List<Measurement> measurementList = measurementService.getAllMeasurements();
		assertEquals(numberOfMeasurementsInDatabaseBeforeTest + 3, measurementList.size());
	}

	@Test
	public void testGetMeasurementById() {
		measurementDTO.setBodyWeight(70.5);
		measurement = measurementService.createMeasurement(measurementDTO);

		MeasurementDTO retrievedMeasurement = measurementService.getMeasurementById(measurement.getId());

		assertNotNull(retrievedMeasurement);
		assertEquals(70.5, retrievedMeasurement.getBodyWeight());
	}

	@Test
	public void testUpdateMeasurement()  {
		measurementDTO.setBodyWeight(70.5);
		measurement = measurementService.createMeasurement(measurementDTO);

		MeasurementDTO updatedMeasurementDTO = TestDTOUtils.getMeasurementDTO(contract.getId());
		updatedMeasurementDTO.setBodyWeight(72.0);
		MeasurementDTO updatedMeasurement = measurementService.updateMeasurement(measurement.getId(), updatedMeasurementDTO);

		assertNotNull(updatedMeasurement);
		assertEquals(72.0, updatedMeasurement.getBodyWeight());
	}

	@Test
	public void testDeleteMeasurement() {
		measurement = measurementService.createMeasurement(measurementDTO);

		measurementService.deleteMeasurement(measurement.getId());

		assertEquals(numberOfMeasurementsInDatabaseBeforeTest, measurementService.getAllMeasurements().size());
	}

}