package de.htw.fb4.bilderplattform.view.validator;

import java.util.Map;

import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

/**
 * 
 * @author konitzer
 *
 */
public class RegistrationValidator extends AbstractValidator {
	
	public void validate(ValidationContext ctx) {
		// all the bean properties
		Map<String, Property> beanProps = ctx.getProperties(ctx.getProperty()
				.getBase());
		// first let's check the passwords match
		validatePasswords(ctx, (String) beanProps.get("password").getValue(),
				(String) ctx.getValidatorArg("retypedPassword"));
		validateEmail(ctx, (String) beanProps.get("email").getValue());
	}

	private void validatePasswords(ValidationContext ctx, String password,
			String retype) {
		System.out.println("password = " + password);
		System.out.println("retype = " + retype);
		
		if (password == null || retype == null || (!password.equals(retype))) {
			this.addInvalidMessage(ctx, "password",
					"Ihre Passw�rter stimmen nicht �berein!");
		}
	}

//	private void validateAge(ValidationContext ctx, int age) {
//		if (age <= 0) {
//			this.addInvalidMessage(ctx, "age", "Your age should be > 0!");
//		}
//	}

	private void validateEmail(ValidationContext ctx, String email) {
		if (email == null || !email.matches(".+@.+\\.[a-z]+")) {
			this.addInvalidMessage(ctx, "email", "Bitte geben Sie eine g�ltige Email Adresse ein!");
		}
	}
}