package com.scm.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator
  implements ConstraintValidator<ValidFile, MultipartFile> { //accepted arguments are MultipartFile data

  private static final long MAX_FILE_SIZE = 1024 * 1024 * 2; // 2MB

  //all these can also be validated
  // type
  // height
  // width

  @Override
  public boolean isValid(
    MultipartFile file,
    ConstraintValidatorContext context
  ) {
    //it is optional to upload image so no need for this check
    // if (file == null || file.isEmpty()) {
    //   //   disabling default message
    //   context.disableDefaultConstraintViolation();
    //   context
    //     .buildConstraintViolationWithTemplate("File cannot be empty")
    //     .addConstraintViolation();
    //   return false;
    // }

    // file size check
    System.out.println("file size: " + file.getSize());

    if (file.getSize() > MAX_FILE_SIZE) {
      context.disableDefaultConstraintViolation();
      context
        .buildConstraintViolationWithTemplate(
          "File size should be less than 2MB"
        )
        .addConstraintViolation();
      return false;
    }

    // resolution check can be done too

    // try {
    // BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

    // if(bufferedImage.getHe)

    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    return true;
  }
}
