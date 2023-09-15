package be.technifutur.backend.models.form;

import be.technifutur.backend.models.entity.User;
import be.technifutur.backend.validation.contraint.EmailUnique;
import be.technifutur.backend.validation.contraint.PhoneUnique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserForm {

    @NotBlank
    @Size(min = 2,max = 30)
    private String firstName;

    @NotBlank
    @Size(min = 2,max = 50)
    private String lastName;

    @NotNull
    private LocalDate birthDate;

    @NotBlank
    @PhoneUnique
    private String phoneNumber;

    @NotBlank
    @Size(min = 2,max = 30)
    private String username;

    @NotBlank
    @EmailUnique
    private String email;

    @NotBlank
    @Size(min = 6)
    @Pattern(regexp = "^(?=.*[!=@#|$%^&*()_+{}\\\\[\\\\]:;<>,.?~\\\\-]).*(?=.*[A-Z]).*(?=.*[0-9]).*$")
    private String password;

    public User toEntity(){
        User user = new User();
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setBirthDate(this.birthDate);
        user.setPhoneNumber(this.phoneNumber);
        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setPassword(this.password);
        return user;
    }
}
