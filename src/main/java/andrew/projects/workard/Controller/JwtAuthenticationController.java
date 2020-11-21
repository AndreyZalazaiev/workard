package andrew.projects.workard.Controller;



import andrew.projects.workard.Config.JwtTokenUtil;
import andrew.projects.workard.Domain.User;
import andrew.projects.workard.Repos.UserRepo;
import andrew.projects.workard.Service.JwtUserDetailsService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	UserRepo userRepo;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) throws Exception {

		authenticate(user.getUsername(), user.getPassword());

		final UserDetails userDetails = jwtUserDetailsService
				.loadUserByUsername(user.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(token);
	}
	
	@PostMapping(value = "/register")
	public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {
		if(userRepo.findByUsername(user.getUsername()).isPresent())
		{
			return ResponseEntity.ok("Already registered");
		}
		return ResponseEntity.ok(jwtUserDetailsService.save(user));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	@GetMapping("/activate/{code}")
	public String activateAccount(@PathVariable String code) {
		val a = userRepo.findUserByActivationCode(code);
		if (a.isPresent()) {
			a.ifPresent(account -> account.setEmailConfirmation(null));
			userRepo.save(a.get());
			return "Activated";
		}
		return "Non existing code";
	}
}