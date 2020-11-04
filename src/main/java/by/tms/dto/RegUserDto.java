package by.tms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegUserDto {
    private String userName;
    private String userLogin;
    private String userPassword;
    private int userAge;

    private String telephoneNumber;
    private String telephoneProvider;
    private String telephoneCategory;

    private String addressCity;
    private String addressStreet;
    private int addressHouse;
    private int addressFlat;
    private String addressCategory;
}
