package com.detentionsystem;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GetterAndSetterTest {

	@Test
	void validate() {
		final List<PojoClass> classes = PojoClassFactory.getPojoClassesRecursively("com.detentionsystem", null);

		final Validator validator = ValidatorBuilder.create()
			.with(new SetterTester())
			.with(new GetterTester())
			.build();

		classes.forEach(pojoClass -> {
			try {
				validator.validate(pojoClass);
			} catch (final Exception ignored) {
			}
		});
	}
}