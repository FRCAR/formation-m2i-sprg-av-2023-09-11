package com.bigcorp.batch.ressort.batch.validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

public class RessortParametersValidator implements JobParametersValidator {

	@Override
	public void validate(JobParameters parameters) throws JobParametersInvalidException {
		String outputResourceNameParameter = parameters.getString("output.resource.name");
		if (outputResourceNameParameter == null) {
			throw new JobParametersInvalidException("output.resource.name est obligatoire");
		}

	}

}
