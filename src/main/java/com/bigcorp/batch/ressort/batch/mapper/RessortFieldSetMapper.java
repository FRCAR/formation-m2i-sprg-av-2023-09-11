package com.bigcorp.batch.ressort.batch.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.bigcorp.batch.ressort.batch.Ressort;

public class RessortFieldSetMapper implements FieldSetMapper<Ressort> {

	public Ressort mapFieldSet(FieldSet fieldSet) throws BindException {
		Ressort ressort = new Ressort();

		ressort.setName(fieldSet.readString("name"));
		double length = fieldSet.readDouble("length");
//		if (length > 20) {
//			throw new BindException(ressort, "incorrect length > 5");
//		}
		ressort.setLength(length);
		ressort.setManufacturedDate(LocalDate.parse(fieldSet.readString("manufactured_date"),
				DateTimeFormatter.ISO_LOCAL_DATE));
		ressort.setSerialId(fieldSet.readInt("serial_id"));
		return ressort;
	}
}