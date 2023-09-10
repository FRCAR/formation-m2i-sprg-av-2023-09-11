package com.bigcorp.batch.virement.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.bigcorp.batch.virement.bean.DemandeVirement;

/**
 * Mappe un FieldSet avec une instance de DemandeVirement. Utilisé par un
 * ItemReader
 */
public class DemandeVirementFieldSetMapper implements FieldSetMapper<DemandeVirement> {

	public DemandeVirement mapFieldSet(FieldSet fieldSet) throws BindException {
		DemandeVirement demandeVirement = new DemandeVirement();

		demandeVirement.setAccountFrom(fieldSet.readString("accountFrom"));
		demandeVirement.setAccountTo(fieldSet.readString("accountTo"));
		try {
			int amount = fieldSet.readInt("amount");
			demandeVirement.setAmount(amount);
		} catch (IllegalArgumentException iae) {
			throw new BindException(fieldSet, "amount");
		}

		demandeVirement.setLabel(fieldSet.readString("label"));

		try {
			LocalDateTime date = LocalDateTime.parse(fieldSet.readString("date"),
					DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			demandeVirement.setDate(date);
		} catch (DateTimeParseException dtpe) {
			throw new BindException(fieldSet, "date");
		}
		return demandeVirement;
	}

}
