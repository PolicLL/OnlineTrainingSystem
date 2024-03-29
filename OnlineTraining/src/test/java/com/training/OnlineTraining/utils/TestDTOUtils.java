package com.training.OnlineTraining.utils;

import com.training.OnlineTraining.dto.*;
import com.training.OnlineTraining.dto.WorkoutDTO;
import com.training.OnlineTraining.dto.WorkoutSessionDTO;
import com.training.OnlineTraining.model.Client;
import com.training.OnlineTraining.model.Coach;
import com.training.OnlineTraining.model.enums.Education;
import com.training.OnlineTraining.model.enums.WorkoutStatus;
import com.training.OnlineTraining.model.enums.WorkoutType;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class TestDTOUtils {

	public static WorkoutDTO getWorkoutDTO(UUID contractID){
		WorkoutDTO workoutDTO = new WorkoutDTO();

		workoutDTO.setDateOfWorkout(DateUtils.getTodayDate());
		workoutDTO.setContractId(contractID);

		workoutDTO.setOrdinalNumberOfWorkout(getRandomNumber());
		workoutDTO.setNumberOfExercises(getRandomNumber());
		workoutDTO.setWarmingUpTimeInSeconds(getRandomNumber());
		workoutDTO.setNumberOfSets(getRandomNumber());
		workoutDTO.setPauseBetweenSetsInSeconds(getRandomNumber());
		workoutDTO.setSelfRating(getRandomNumber());
		workoutDTO.setWorkoutStatus(WorkoutStatus.WAITING);
		workoutDTO.setNextWorkout(WorkoutType.PUSH);

		return workoutDTO;
	}

	public static WorkoutSessionDTO getWorkoutSessionDTO(UUID workoutId, UUID exerciseId){
		WorkoutSessionDTO workoutSessionDTO = new WorkoutSessionDTO(workoutId);

		workoutSessionDTO.setWorkoutId(workoutId);
		workoutSessionDTO.setExerciseId(exerciseId);

		workoutSessionDTO.setNumberOfReps(getRandomNumber());
		workoutSessionDTO.setPauseAfterExerciseInSeconds(getRandomNumber());
		workoutSessionDTO.setWeight(BigDecimal.valueOf(getRandomDecimalNumber()));

		return workoutSessionDTO;
	}

	private static int userNumber = 0;
	private static int clientNumber = 0;

	public static MeasurementDTO getMeasurementDTO(UUID contractId) {
		MeasurementDTO measurementDTO = new MeasurementDTO();
		measurementDTO.setContractId(contractId);
		measurementDTO.setMeasurementDate(DateUtils.getTodayDate());
		measurementDTO.setBodyWeight(getRandomDecimalNumber());
		measurementDTO.setBodyFat(getRandomDecimalNumber());
		measurementDTO.setWaistCircumference(getRandomDecimalNumber());
		measurementDTO.setChestCircumference(getRandomDecimalNumber());
		measurementDTO.setArmCircumference(getRandomDecimalNumber());
		measurementDTO.setLegCircumference(getRandomDecimalNumber());
		return measurementDTO;
	}

	public static UserDTO getUserDTO() {
		UserDTO userDto = new UserDTO();

		userDto.setFirstName("FirstName" + userNumber);
		userDto.setLastName("LastName" + userNumber);
		userDto.setEmail("Email" + userNumber);
		userDto.setStreet("Street" + userNumber);
		userDto.setCity("City" + userNumber);
		userDto.setCountry("Country" + userNumber);
		userDto.setPhoneNumber("PhoneNumber" +userNumber);
		userDto.setGender("Gender" + userNumber);
		userDto.setPassword("Password " + userNumber);
		userDto.setAge(50);

		++userNumber;

		return userDto;
	}

	public static UpdateClientDTO getUpdateClientDTO(){
		UpdateClientDTO updateClientDTO = new UpdateClientDTO();
		updateClientDTO.setMedicalCondition("Medical Condition");
		updateClientDTO.setInjuries("Injuries");
		updateClientDTO.setFirstName("First Name");
		updateClientDTO.setLastName("Last Name");
		updateClientDTO.setStreet("Street");
		updateClientDTO.setCity("City");
		updateClientDTO.setCountry("Country");
		updateClientDTO.setPhoneNumber("Phone Number");
		updateClientDTO.setGender("Gender");
		updateClientDTO.setAge(40);

		return updateClientDTO;
	}

	private static int getRandomNumber(){
		return new Random().nextInt(1000);
	}

	private static double getRandomDecimalNumber(){
		return new Random().nextDouble(1000.0);
	}

	public static ClientDTO getClientDTO() {
		ClientDTO clientDto = new ClientDTO();

		clientDto.setMedicalCondition("Medical condition " + clientNumber);
		clientDto.setInjuries("Injuries " + clientNumber);
		++clientNumber;

		return clientDto;
	}

	public static CoachDTO getCoachDTO() {
		CoachDTO coachDto = new CoachDTO();
		
		coachDto.setCoachUserDTO(null);
		coachDto.setYearsOfExperience(10.0);
		coachDto.setEducation(Education.BACHELORS);
		coachDto.setYearsOfExperience(20.0);
		coachDto.setMonthlyPrice(100.0);
		
		return coachDto;
	}


	public static UpdateCoachDTO getUpdateCoachDTO() {
		UpdateCoachDTO updateCoachDTO = new UpdateCoachDTO();
		updateCoachDTO.setYearsOfExperience(new BigDecimal("1.5")); // Set a sample value for BigDecimal
		updateCoachDTO.setEducation(String.valueOf(Education.BACHELORS));
		updateCoachDTO.setMonthlyPrice(new BigDecimal("1000.00")); // Set a sample value for BigDecimal
		updateCoachDTO.setFirstName("SampleFirstName");
		updateCoachDTO.setLastName("SampleLastName");
		updateCoachDTO.setStreet("SampleStreet");
		updateCoachDTO.setCity("SampleCity");
		updateCoachDTO.setCountry("SampleCountry");
		updateCoachDTO.setPhoneNumber("1234567890");
		updateCoachDTO.setGender("M");
		updateCoachDTO.setAge(25); // Set a sample value for Integer

		return updateCoachDTO;
	}

	public static ContractDTO getContractDTO(Coach coach, Client client) {
		ContractDTO contractDto = new ContractDTO();

		contractDto.setClient(client);
		contractDto.setCoach(coach);
		contractDto.setStartDate(DateUtils.getTodayDate());
		contractDto.setEndDate(DateUtils.getTodayDate());
		contractDto.setMonthlyPrice(100.00);

		return contractDto;
	}

}

