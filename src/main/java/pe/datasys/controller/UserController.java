package pe.datasys.controller;

import java.net.URI;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pe.datasys.commons.PaginationModel;
import pe.datasys.dto.ChangePasswordDTO;
import pe.datasys.dto.UserDTO;
import pe.datasys.model.User;
import pe.datasys.service.IUserService;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService service;
    private final ModelMapper mapper;
    private final PasswordEncoder bcrypt;
    private final AuthenticationManager authenticationManager;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> lista = service.findAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Integer id) {
        User obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @GetMapping("/findOneByUsername/{username}")
    public ResponseEntity<UserDTO> findOneByUsername(@PathVariable("username") String username) {
        User obj = service.findOneByUsername(username);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PostMapping("/paginate")
    public ResponseEntity<Page<UserDTO>> paginate(@RequestBody PaginationModel pagination) {
        Page<UserDTO> pag = service.paginate(pagination.getPageNumber(), pagination.getRowsPerPage(),
                pagination.getFilters(), pagination.getSorts());
        return new ResponseEntity<>(pag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO dto) {
        String password = bcrypt.encode(dto.getPassword());
        dto.setPassword(password);
        User obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdUser())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody UserDTO dto) {
        User obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PostMapping("changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO dto) throws Exception {

        try {
            User user = service.findOneByUsername(dto.getUsername());
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), dto.getPasswordActual()));
            service.changePassword(dto.getPasswordNuevo(), user.getUsername());

            return new ResponseEntity<>(convertToDto(user), HttpStatus.OK);

        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private UserDTO convertToDto(User obj) {
        return mapper.map(obj, UserDTO.class);
    }

    private User convertToEntity(UserDTO dto) {
        return mapper.map(dto, User.class);
    }
}
