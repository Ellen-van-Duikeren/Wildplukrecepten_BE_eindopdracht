package novi.nl.wildplukrecepten.services;

import novi.nl.wildplukrecepten.dto.inputDto.UserAccountInputDto;
import novi.nl.wildplukrecepten.dto.outputDto.UserAccountOutputDto;
import novi.nl.wildplukrecepten.exceptions.RecordNotFoundException;
import novi.nl.wildplukrecepten.models.UserAccount;
import novi.nl.wildplukrecepten.repositories.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public List<UserAccountOutputDto> getAllUserAccounts() {
        List<UserAccount> userAccounts = userAccountRepository.findAll();
        List<UserAccountOutputDto> userAccountOutputDtos = new ArrayList<>();
        for (UserAccount userAccount : userAccounts) {
            UserAccountOutputDto userAccountOutputDto = transferToDto(userAccount);
            userAccountOutputDtos.add(userAccountOutputDto);
        }
        return userAccountOutputDtos;
    }

    // GetMapping by id, function for getting a userAccount by id
    public UserAccountOutputDto getUserAccount(Long id) {
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(id);
        if (!userAccountRepository.existsById(id)) {
            throw new RecordNotFoundException("No userAccount found with id: " + id + ".");
        } else {
            UserAccount userAccount1 = optionalUserAccount.get();
            return transferToDto(userAccount1);
        }
    }

    //     PostMapping, function for adding a userAccount
    public Long createUserAccount(UserAccountInputDto userAccountInputDto) {
        UserAccount newUserAccount = new UserAccount();
        newUserAccount = transferToUserAccount(userAccountInputDto);
        UserAccount savedUserAccount = userAccountRepository.save(newUserAccount);
        return savedUserAccount.getId();
    }

    //PutMapping, function for changing a (whole) userAccount
    public UserAccountOutputDto putUserAccount(Long id, UserAccountInputDto userAccountInputDto) {
        {
            if (userAccountRepository.findById(id).isPresent()) {
                UserAccount userAccount = userAccountRepository.findById(id).get();
                UserAccount userAccount1 = transferToUserAccount(userAccountInputDto);
                userAccount1.setId(userAccount.getId());
                userAccountRepository.save(userAccount1);
                return transferToDto(userAccount1);
            } else {
                throw new RecordNotFoundException("No userAccount found with id: " + id + ".");
            }
        }
    }

    // Patchmapping, function for changing parts of a userAccount
    public UserAccountOutputDto patchUserAccount(Long id, UserAccountInputDto userAccountInputDto) {
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(id);
        if (userAccountRepository.existsById(id)) {
            UserAccount userAccountToUpdate = optionalUserAccount.get();

            if (userAccountInputDto.getFirstname() != null) {
                userAccountToUpdate.setFirstname(userAccountInputDto.getFirstname());
            }
            if (userAccountInputDto.getLastname() != null) {
                userAccountToUpdate.setLastname(userAccountInputDto.getLastname());
            }
            if (userAccountInputDto.getEmailadress() != null) {
                userAccountToUpdate.setEmailadress(userAccountInputDto.getEmailadress());
            }

            UserAccount savedUserAccount = userAccountRepository.save(userAccountToUpdate);
            return transferToDto(savedUserAccount);
        } else {
            throw new RecordNotFoundException("No userAccount with id " + id);
        }
    }

    // DeleteMapping, function for deleting a userAccount
    public String deleteById(Long id) {
        if (userAccountRepository.existsById(id)) {
            Optional<UserAccount> deletedUserAccount = userAccountRepository.findById(id);
            UserAccount userAccount1 = deletedUserAccount.get();
            userAccountRepository.delete(userAccount1);
            return "UserAccount with id: " + id + " deleted.";
        } else {
            throw new RecordNotFoundException("No userAccount found with id: " + id + ".");
        }
    }


    // helper method from UserAccount to Dto
    private UserAccountOutputDto transferToDto(UserAccount userAccount) {
        UserAccountOutputDto userAccountOutputDto = new UserAccountOutputDto();
        userAccountOutputDto.setId(userAccount.getId());
        userAccountOutputDto.setFirstname(userAccount.getFirstname());
        userAccountOutputDto.setLastname(userAccount.getLastname());
        userAccountOutputDto.setEmailadress(userAccount.getEmailadress());
        return userAccountOutputDto;
    }

    //helper method from Dto to UserAccount
    public UserAccount transferToUserAccount(UserAccountInputDto userAccountInputDto) {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(userAccountInputDto.getId());
        userAccount.setFirstname(userAccountInputDto.getFirstname());
        userAccount.setLastname(userAccountInputDto.getLastname());
        userAccount.setEmailadress(userAccountInputDto.getEmailadress());
             return userAccount;
    }
}


